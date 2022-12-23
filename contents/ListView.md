# 7. ListView

`ListView`는 `Item`들이 이전 `Item`의 아래에 배치되는 세로로 스크롤 가능한 `View` 모음이다.
최근엔 더 많은 기능을 지원하고 유연한 방식인 `RecyclerView`를 더 많이 사용하는 추세이나,
간단한 리스트를 구현하기엔 간편하기 때문에 종종 사용된다.
simple_list를 사용하는 예제 대신 Adapter클래스를 이용한 방법을 적어보았다.

```
kotlin.Any
    ↳android.view.View
        ↳android.view.ViewGroup
            ↳android.widget.AdapterView<android.widget.ListAdapter>
                ↳android.widget.AbsListView
                    ↳android.widget.ListView
```

## Layout xml 파일 작성

activity_main.xml

```XML
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">
    <ListView
        android:id="@+id/listView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginStart="24dp"
        android:layout_marginTop="24dp"
        android:layout_marginEnd="24dp"
        android:layout_marginBottom="24dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

list_item_user.xml

```XML
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <ImageView
        android:id="@+id/iv_profile"
        android:layout_width="70dp"
        android:layout_height="70dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/ic_android_24dp" />
    <TextView
        android:id="@+id/tv_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="8dp"
        android:text="Cholee"
        android:textColor="@color/black"
        app:layout_constraintStart_toEndOf="@+id/iv_profile"
        app:layout_constraintTop_toTopOf="@+id/iv_profile" />
    <TextView
        android:id="@+id/tv_greet"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="8dp"
        android:text="Hi"
        android:textColor="@color/black"
        app:layout_constraintBottom_toBottomOf="@+id/iv_profile"
        app:layout_constraintStart_toEndOf="@+id/iv_profile"
        app:layout_constraintTop_toBottomOf="@+id/tv_name" />
    <TextView
        android:id="@+id/tv_age"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginEnd="8dp"
        android:text="24"
        android:textColor="@color/black"
        app:layout_constraintBottom_toBottomOf="@+id/iv_profile"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## Activity

```KOTLIN
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var UserList = arrayListOf<User>(
        User(R.drawable.ic_android_24dp, "승철", "24", "코틀린을 배우고 있습니다!"),
        User(R.drawable.ic_android_24dp, "Cholee", "24", "별명 중에 하나입니다."),
        User(R.drawable.ic_android_24dp, "Seung__72", "24", "인스타그램 아이디입니다."),
        User(R.drawable.ic_android_24dp, "이승철", "24", "본명 입니다")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        simple_list_item을 사용하여 만드는 방법. 간단하나 여러 기능을 구현하기 어렵다.
//        val item = arrayOf("Apple Pie", "Banana Bread", "Cupcake", "Donut")
//        binding.listView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, item)

        val Adapter = UserAdapter(this, UserList)
        binding.listView.adapter = Adapter

        binding.listView.onItemClickListener = AdapterView.OnItemClickListener {parent, view, position, id ->
            val selectItem= parent.getItemAtPosition(position) as User
            Toast.makeText(this, "이름: "+ selectItem.name, Toast.LENGTH_SHORT).show()
        }
    }
}
```

UserList에 아이템으로 이미지파일과 간단한 소개를 넣고 각 아이템 클릭시 이름이 출력되는 예제이다.
UserAdapter.kt 클래스를 생성하여 `Adapter`에 변수로 선언하고 `listView.adapter`에 적용하여 불러올 수 있다.

## Class

#### User.kt

```KOTLIN
class User(val profile: Int, val name: String, val age: String, val greet: String)
```

<details>
<summary>Java Getter/Setter 보기</summary>
<div markdown="1">

```JAVA
public class User {
    private Int profile;
    private String name;
    private String age;
    private String greet;

    //getter
    public int getProfile() {
        return this.profile;
    }
    public String getName() {
        return this.name;
    }
    public String getAge() {
        return this.age;
    }
    public String getGreet) {
        return this.greet;
    }

    //setter
    public void setProfile() {
        this.profile = profile;
    }
    public void setName() {
        this.name = name;
    }
    public void setAge() {
        this.age = age;
    }
    public void setGreet() {
        this.greet = greet;
    }
}
```

</div>
</details>

- Java의 Getter/Setter와 같은 개념이다.
- Java와 비교했을 때 상당히 많은 생략이 가능하다.

#### UserAdapter.kt

```KOTLIN
// BaseAdapter를 상속 받아 implement를 자동 생성한다.
// UserList를 ArrayList형으로 선언하고 생성한 User를 담아서 사용한다.
class UserAdapter(val context: Context, val UserList: ArrayList<User>) : BaseAdapter() {
    override fun getCount(): Int {
        // 리스트뷰의 개수만큼 호출
        return UserList.size
    }

    override fun getItem(position: Int): Any {
        // UserList의 포지션에 맞는 아이템을 불러옴
        return UserList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        // view에 LayoutInflater를 이용하여 context로 부터 생성한 layout을 가져옴
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item_user, null)

        // list의 개체들 id 선언
        val profile = view.findViewById<ImageView>(R.id.iv_profile)
        val name = view.findViewById<TextView>(R.id.tv_name)
        val greet = view.findViewById<TextView>(R.id.tv_greet)
        val age = view.findViewById<TextView>(R.id.tv_age)

        // user에 UserList[위치] 지정하여 선언
        val user = UserList[position]

        // 선언된 id에 데이터 입력
        profile.setImageResource(user.profile)
        name.text = user.name
        greet.text = user.greet
        age.text = user.age

        // view 반환
        return view
    }
}
```

---

참조
[구글 공식문서](https://developer.android.com/reference/kotlin/android/widget/ListView),
[홍드로이드](https://www.youtube.com/watch?v=ao0Iqfhy0oo&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=5)
