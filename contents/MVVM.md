# 16. MVVM

`MVVM(Model-View-ViewModel)`은 `View`의 개발을 `Model`로부터 분리시켜서 `View`가 어느 특정한 모델 플랫폼에 종속되지 않도록 해준다. `View Model`은 값 변환기 역할을 하며, `Model`에 있는 데이터 객체를 반환하는 책임을 진다.
직접 연습해보니 클래스 구성 파일이나 코드 자체는 많아지는 편이지만 MainActivity의 코드 가독성이 증가하였고, 유지보수가 더 쉬워졌다.

#### 구성요소

- 모델(Model): 실제 상태 내용을 표현하거나 내용을 표현하는 데이터 접근 계층을 참조한다.
- 뷰(View): 사용자가 보는 것들에 대한 구조, 배치, 외관에 해당한다. 사용자와의 상호 작용을 수신하여 뷰와 뷰 모델의 연결을 정의하고 있는 [`Data Binding`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/dataBinding.md)을 통하여 뷰 모델로 전달한다.
- 뷰 모델(View Model): 뷰에 대한 추상화이다. `Binder`를 통하여 `View`에 연결된 속성과 `View` 사이의 통신을 자동화한다.
  - 바인더(Binder): `View Model`과 `View`의 동기화를 위해 상용 로직을 작성할 필요가 없게 해준다.

#### 단점

단순한 UI 작업에서 MVVM은 지나치게 과하다. 또한 응용 프로그램이 더 커짐에 따라서 뷰 모델을 폭 넓게 사용하기 어렵고, 메모리를 많이 점유할 수 있다. -MVVM 제작자 'John Gossman'

이번 예제는 사용자의 뷰에 사용자가 저장한 전화번호와 이름을 리스트형식으로 보여주는 어플리케이션이다. 다음과 같은 기능들이 사용된다.

- [RecyclerView](https://github.com/Seung72/Kotlin_Study/blob/main/contents/RecyclerView.md)
- [LiveData](https://github.com/Seung72/Kotlin_Study/blob/main/contents/LiveData.md)
- Room
- [DataBinding](https://github.com/Seung72/Kotlin_Study/blob/main/contents/dataBinding.md)
- Repository

Room과 Repository는 다음 번에 더 깊게 다뤄보겠다.

### build.gradle(:app)

```Groovy
apply plugin: 'kotlin-kapt'

android {
    dataBinding {
        enabled = true
    }
    viewBinding {
        enabled = true
    }
}

dependencies {
    def room_version = "2.5.0"

    // room
    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    kapt "androidx.room:room-compiler:$room_version"

    // liveData
    implementation 'android.arch.lifecycle:extensions:1.1.1'
    kapt 'android.arch.lifecycle:compiler:1.1.1'

    // viewModel
    implementation 'android.arch.lifecycle:viewmodel:1.1.1'
}
```

### gradle.properties

```Groovy
// 레거시 지원을 위한 코드인거 같다. 없을 때 오류가 발생했다.
android.enableJetifier=true
```

### Class

Contact.kt

```Kotlin
@Entity(tableName = "contact")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "number")
    var number: String,

    @ColumnInfo(name = "initial")
    var initial: Char
) {
    constructor() : this(null, "", "", '\u0000')
}
```

- `Room`에서 사용할 칼럼들을 정의하는 [`Data Class`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/%EB%AC%B8%EB%B2%95.md#data-class)이다. `@(Annotaion)`을 명시하여 `Entity`나 `ColumnInfo` 등을 정의한다.

ContactDao.kt

```Kotlin
@Dao
interface ContactDao {

    // LiveData 타입으로 반환해줌
    @Query("SELECT * FROM contact ORDER BY name ASC")
    fun getAll(): LiveData<List<Contact>>

    // onConflict: 중복된 데이터에 대한 처리
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)
}
```

- SQL문을 사용하는 DAO Interface에 해당한다. 역시 어노테이션을 사용하여 정의한다.

ContactDatabase.kt

```Kotlin
// SQLite 버전
@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if(INSTANCE == null) {
                synchronized(ContactDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ContactDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
```

- 데이터베이스 인스턴스를 생성하는 클래스이다. Room을 사용하기 때문에 `RoomDatabase`를 상속받는다.

ContactRepository.kt

```Kotlin
class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>>{
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.insert(contact)
            })
            thread.start()
        } catch (e: Exception) {
            Log.e("insert ERROR", e.toString())
        }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e: Exception) {
            Log.e("delete ERROR", e.toString())
        }
    }
}
```

아래의 클래스들을 초기화하는 역할을 수행하는 클래스이다.

- `Data Class` - `Contact`
- `Interface` - `ContactDao`
- `Abstract Class` - `ContactDatabase`

또한 Room을 메인스레드에서 접근하려고 하면 에러가 발생한다. 따라서 별도의 스레드를 생성해서 접근한다.

ContactViewModel.kt

```Kotlin
class ContactViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return this.contacts
    }

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }
}
```

뷰 모델은 액티비티가 깨진 경우에도 데이터를 유지하고 있어야 한다. 따라서 Context를 사용하기 위해서 `Application`을 인자로 받는다.

ContactAdapter.kt

```Kotlin
class ContactAdapter(
    val contactItemClick: (Contact) -> Unit,
    val contactItemLongClick: (Contact) -> Unit)
    : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
        private var contacts: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.bind(contacts[pos])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val number = itemView.findViewById<TextView>(R.id.tv_number)
        private val initial = itemView.findViewById<TextView>(R.id.tv_initial)

        fun bind(contact: Contact) {
            name.text = contact.name
            number.text = contact.number
            initial.text = contact.initial.toString()

            itemView.setOnClickListener {
                contactItemClick(contact)
            }
            itemView.setOnLongClickListener {
                contactItemLongClick(contact)
                true
            }
        }
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}
```

- 리스트 어댑터 클래스로 데이터를 뿌려주거나 갱신되는 경우, 클릭 이벤트에 대한 대응을 할 수 있다.

### layout Xml

item_contact.xml

```Xml
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/tv_initial"
            android:layout_width="50dp"
            android:layout_height="wrap_content"
            android:background="@color/green"
            android:textColor="@color/white"
            android:gravity="center"
            android:padding="4dp"
            android:text="L"
            android:textSize="30sp"
            android:layout_margin="16dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tv_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Seung"
            android:textSize="20sp"
            android:textStyle="bold"
            android:layout_margin="16dp"
            app:layout_constraintStart_toEndOf="@+id/tv_initial"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tv_number"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="123-123-123"
            android:layout_marginTop="4dp"
            android:layout_marginStart="16dp"
            app:layout_constraintStart_toEndOf="@+id/tv_initial"
            app:layout_constraintTop_toBottomOf="@+id/tv_name" />
    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.cardview.widget.CardView>
```

- RecyclerView에 사용할 리스트 아이템에 대한 레이아웃이다.

activity_main.xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_contact"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_margin="16dp"
        app:layout_constraintBottom_toTopOf="@+id/btn_add_contact"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:listitem="@layout/item_contact" />

    <Button
        android:id="@+id/btn_add_contact"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="추가"
        android:layout_margin="8dp"
        android:backgroundTint="@color/green"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- 연락처 리스트를 보여주고 추가 버튼을 통해 다른 화면으로 이동하는 뷰이다.

activity_add.xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".AddActivity"
    android:padding="50dp">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:text="연락처 입력"
        android:textColor="@color/black"
        android:textSize="30sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toTopOf="@+id/linearLayout"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <LinearLayout
        android:id="@+id/linearLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="96dp"
        android:gravity="center"
        android:orientation="horizontal"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/tv_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="4"
            android:gravity="center"
            android:text="이름"
            android:textColor="@color/black"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toStartOf="@+id/et_name"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <EditText
            android:id="@+id/et_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:inputType="textPersonName"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:gravity="center"
        android:orientation="horizontal"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/linearLayout">

        <TextView
            android:id="@+id/tv_number"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="4"
            android:textColor="@color/black"
            android:gravity="center"
            android:text="연락처"
            app:layout_constraintEnd_toStartOf="@+id/et_number"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tv_name" />

        <EditText
            android:id="@+id/et_number"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:inputType="phone"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/et_name" />
    </LinearLayout>

    <Button
        android:id="@+id/btn_add_contact"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="추가하기"
        android:textSize="14sp"
        android:textStyle="bold"
        android:backgroundTint="@color/green"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- 리스트에 아이템을 추가하기 위해서 이름과 연락처를 입력 받는 뷰이다.

### Activity

MainActicity.kt

```Kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val lm = LinearLayoutManager(this)
        val adapter = ContactAdapter({ contact -> // 일반 클릭
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, {contact -> // 롱 클릭
            deleteDialog(contact)
        })

        with(binding) {
            rvContact.adapter = adapter
            rvContact.layoutManager = lm
            rvContact.setHasFixedSize(true)
        }
        binding.btnAddContact.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        contactViewModel = ViewModelProvider(this@MainActivity).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>>{ contacts ->
            adapter.setContacts(contacts!!)
        })
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택한 연락처를 지우시겠습니까?")
            .setPositiveButton("지우기") {_, _ ->
                contactViewModel.delete(contact)
            }
            .setNegativeButton("취소") {_, _ -> }
        builder.show()
    }
}
```

- 빈 생성자가 있는 `ViewModel` 인스턴스를 받아오기 위해 `ViewModelProvider`를 사용한다.
- observe를 사용하여 LiveData로 생성된 Contact의 데이터 변경 시 갱신되도록 작성한다.
- 삭제 시 사용할 `Dialog` 기능도 작성한다.

AddActivity.kt

```Kotlin
class AddActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var binding: ActivityAddBinding
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        contactViewModel = ViewModelProvider(this@AddActivity).get(ContactViewModel::class.java)

        if(isContact(intent)) {
            with(binding) {
                etName.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
                etNumber.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            }
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        binding.btnAddContact.setOnClickListener{
            val name = binding.etName.text.toString().trim()
            val number = binding.etNumber.text.toString().trim()

            if (name.isEmpty() || number.isEmpty()) {

            } else {
                val initial = name[0]
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    private fun isContact(intent: Intent): Boolean {
        return (intent != null
                && intent.hasExtra(EXTRA_CONTACT_NAME)
                && intent.hasExtra(EXTRA_CONTACT_NUMBER)
                && intent.hasExtra(EXTRA_CONTACT_ID))
    }
    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}
```

연락처 추가를 위한 액티비티이다.

- MainActivity와 같이 ViewModelProvider를 통해 인스턴스를 받아온다.
- Intent 객체가 있으면 수정하고, 없다면 추가하는 역할을 한다.

---

참조
[구글 공식문서](https://developer.android.com/reference/android/arch/lifecycle/ViewModelProvider.AndroidViewModelFactory?authuser=1),
[Dev Blog by Yena](https://blog.yena.io/studynote/2019/03/27/Android-MVVM-AAC-2.html),
[위키백과](https://ko.wikipedia.org/wiki/%EB%AA%A8%EB%8D%B8-%EB%B7%B0-%EB%B7%B0%EB%AA%A8%EB%8D%B8)
