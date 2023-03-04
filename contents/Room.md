# 17. Room

`Room`은 [`SQLite`](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase)를 완벽히 활용하면서 더 견고한 데이터베이스 액세스를 가능하게 한다.

#### 구성요소

- Database: `Annotation` 표시를 통해 `RoomDatabase`를 상속 받는 추상클래스이다. `Room.databaseBuilder` 런타임을 통해 인스턴스를 얻을 수 있다. 또한 데이터베이스의 Entity와 Dao를 정의한다.

- Entity: `Annotation` 표시를 통해 [Data Class](https://github.com/Seung72/Kotlin_Study/blob/main/contents/%EB%AC%B8%EB%B2%95.md#data-class)를 데이터베이스 필드로 표시한다. 각 항목에 대하여 데이터베이스 테이블이 생성된다.

- Dao: `Annotation` 표시를 통해 클래스 또는 인터페이스를 데이터 액세스 개체(**D**ata **A**ccess **O**bject)로 표시한다. `Dao`는 데이터베이스에 접근하는 메서드의 정의를 담당하는 Room의 주요 구성요소이다

#### 예시 설명

스마트폰의 이름과 OS명, 버전을 입력 받으면 리스트에 순서대로 뿌려주는 예제이다.

## build.gradle (:app)

```Groovy
plugins {
    ...
    id 'com.google.devtools.ksp' version '1.8.0-1.0.8'
}

android {
    ...
    viewBinding {
        enabled = true
    }
}

dependencies {
    def room_version = "2.5.0"

    implementation "androidx.room:room-runtime:$room_version"
    annotationProcessor "androidx.room:room-compiler:$room_version"
    ksp "androidx.room:room-compiler:$room_version"
    ...
}
```

## build.gradle(:project)

```groovy
plugins {
    ...
    id 'org.jetbrains.kotlin.android' version '1.8.0' apply false
}
```

- 내 IDE 환경은 1.7.X 버전의 코틀린이 기본값으로 되어있어 어떤 이유에서 빌드가 되지 않았다.
  - 23년 3월 4일 기준 최신버전인 1.8.0을 사용하니 문제 없이 빌드됐다.

## layout Xml

activity_main.xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Smart Phone List"
        android:textSize="20sp"
        android:textColor="@color/black"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.1" />

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_devices"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_margin="16dp"
        android:elevation="5dp"
        app:layout_constraintBottom_toTopOf="@+id/btn_move_add_act"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_title" />

    <Button
        android:id="@+id/btn_move_add_act"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:text="Input"
        android:textSize="20sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

item_devices.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp"
    android:padding="8dp"
    android:background="@color/sky">

    <TextView
        android:id="@+id/tv_uId"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="uid"
        android:textColor="@color/white"
        android:layout_margin="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_device_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:text="name"
        android:textColor="@color/white"
        android:textSize="24sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toEndOf="@+id/tv_uId"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_os"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:text="os"
        android:textSize="20sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/tv_version"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_version"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="8dp"
        android:text="version"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- `RecyclerView`에 사용할 `item`에 대한 `layout`이다

activity_add.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".AddActivity">

    <EditText
        android:id="@+id/et_name"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="24dp"
        android:hint="Device Name"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/et_os"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="24dp"
        android:hint="OS"
        app:layout_constraintTop_toBottomOf="@+id/et_name"
        tools:layout_editor_absoluteX="24dp" />

    <EditText
        android:id="@+id/et_version"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="24dp"
        android:hint="Build Version"
        app:layout_constraintTop_toBottomOf="@+id/et_os"
        tools:layout_editor_absoluteX="24dp" />

    <Button
        android:id="@+id/btn_add_list"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:layout_margin="24dp"
        android:textSize="20sp"
        android:text="ADD"
        app:layout_constraintTop_toBottomOf="@+id/et_version" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- 아이템을 추가하는 화면에 대한 `layout`이다.

## Class

Device.kt

```Kotlin
@Entity(tableName = "devices")
data class Device(

    @PrimaryKey(autoGenerate = true) val uid: Long?,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "os") var os: String,
    @ColumnInfo(name = "version") var version: Int

) {
    constructor(): this(null, "", "", 0)
}
```

- 데이터 클래스를 생성한 후 어노테이션 `@Entity`를 꼭 사용해 주어야 한다.
  - `(tableName = String)`으로 테이블 명을 명시적으로 지정할 수 있다. 기본 값으로는 클래스 이름이 들어간다.
- `@PrimaryKey`를 통해 기본키를 지정해줄 수 있다.
  - `(autoGenerate = Boolean)`으로 자동으로 생성시킬 수 있다.
- `@ColumnInfo`으로 데이터베이스의 컬럼에 해당하는 아이템을 생성할 수 있다.
  - `(name = String)`테이블과 마찬가지로 컬럼명을 명시할 수 있다. 기본 값은 변수명이다.
- `constructor()`로 외부에서 어떤 형식으로 사용될 것인지 지정한다.

DeviceDao.kt

```Kotlin
@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices")
    fun getAll(): List<Device>

    @Insert(onConflict = REPLACE)
    fun insert(device: Device)

    @Query("DELETE FROM devices")
    fun deleteAll()

}
```

- 인터페이스를 생성한 후 어노테이션 `@Dao`를 명시하여 사용한다.
- `@Query()`로 해당 함수가 어떤 기능을 수행할지 지정한다.
- `@Insert`, `@Delete`를 사용하여 데이터 삽입, 삭제 함수에 대응한다.
  - `@Insert`의 `(onConflict = )`는 중복된 데이터가 들어왔을 때에 대한 행동을 지정할 수 있다.
    - REPLACE: 덮어쓰기
    - ABORT: 처리 중단
    - ROLLBACK: 이전으로 되돌리기
    - FAIL: 실패
    - IGNORE: 무시하기

AppDatabase.kt

```Kotlin
// 사용할 데이터 클래스, SQLite 버전 지정
// 버전은 테이블에 대한 변화가 있을 때 Migration을 하며 버전을 올릴 수 있다.
@Database(entities = [Device::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun deviceDao() : DeviceDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        // 데이터베이스 이름
                        "app-database"
                    ).build()
                }
            }
            return instance
        }
        fun destroyInstance() { instance = null }
    }
}
```

- `companion object`와 `synchronized`를 이용하여 싱글톤 패턴으로 데이터베이스 인스턴스를 생성하는 함수를 만들어준다.
- `Room.databaseBuilder`안에 `context.application`을 가져와야 한다. 일반 액티비티 `context`를 받아오는 경우에 해당 액티비티가 파괴되거나 정지하는 등의 사이클이 발생하는 경우 문제가 생길 수 있기 때문이다.

`Room`을 사용하기 위한 기본적인 세팅은 거의 완료되었다. `RecyclerView`에 데이터를 표시할 것이기 때문에 어댑터를 생성해줘야 한다.

DeviceAdapter.kt

```Kotlin
class DeviceAdapter(val context: Context, val devices: List<Device>):
    RecyclerView.Adapter<DeviceAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_devices, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return devices.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(devices[position])
    }

    inner class Holder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {
        val uIdTv = itemView?.findViewById<TextView>(R.id.tv_uId)
        val nameTv = itemView?.findViewById<TextView>(R.id.tv_device_name)
        val osTv = itemView?.findViewById<TextView>(R.id.tv_os)
        val versionTv = itemView?.findViewById<TextView>(R.id.tv_version)

        fun bind(device: Device) {
            uIdTv?.text = device.uid.toString()
            nameTv?.text = device.name
            osTv?.text = device.os
            versionTv?.text = device.version.toString()
        }
    }
}
```

## Activity

```Kotlin
class MainActivity : AppCompatActivity() {

    // viewBinding
    private lateinit var binding: ActivityMainBinding
    private var db: AppDatabase? = null
    private var deviceList = listOf<Device>()

    lateinit var adapter: DeviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // db 인스턴스 받아오기
        db = AppDatabase.getInstance(applicationContext)!!

        // Room은 메인스레드로 접근이 불가능하기 때문에 별개의 스레드로 접근한다.
        val r = Runnable {
            try {
                // 리스트 불러오기
                deviceList = db?.deviceDao()?.getAll()!!

                adapter = DeviceAdapter(this, deviceList)
                adapter.notifyDataSetChanged()

                with(binding.rvDevices) {
                    adapter = adapter
                    layoutManager = LinearLayoutManager(this)
                    setHasFixedSize(true)
                }

            } catch (e: Exception) {
                Log.e("DB error", e.toString())
            }
        }

        val thread = Thread(r)
        thread.start()

        binding.btnMoveAddAct.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        db = null
        super.onDestroy()
    }
}
```

AddActivity.kt

```Kotlin
class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        db = AppDatabase.getInstance(this)
        val view = binding.root
        setContentView(view)

        val write = Runnable {
            // 새로운 Device를 생성하여 값을 대입한다.
            val newDevice = Device()
            newDevice.name = binding.etName.text.toString()
            newDevice.os = binding.etOs.text.toString()
            newDevice.version = binding.etVersion.text.toString().toInt()
            db?.deviceDao()?.insert(newDevice)
        }

        binding.btnAddList.setOnClickListener {
            val thread = Thread(write)
            thread.start()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        super.onDestroy()
    }
}
```

- 아이템 추가를 위한 액티비티이다.

---

참조
[구글 공식문서 1](https://developer.android.com/jetpack/androidx/releases/room?hl=ko&authuser=1),
[구글 공식문서 2](https://developer.android.com/reference/androidx/room/package-summary?authuser=1),
[Dev Blog by Yena](https://blog.yena.io/studynote/2018/09/08/Android-Kotlin-Room.html)
