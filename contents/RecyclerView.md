# 11. RecyclerView

`RecyclerView`는 `ListView`를 대체할 수 있는 더 많은 데이터를 처리할 수 있는 조금 더 유연한 객체이다.

```
kotlin.Any
   ↳android.view.View
 	   ↳android.view.ViewGroup
 	 	   ↳androidx.recyclerview.widget.RecyclerView
```

### build.gradle(:app)

```
dependencies {
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    ...
}
```

- `RecyclerView`의 기능을 사용하기 위해서는 별도의 `implementation`을 해주어야 한다.

## layout xml

#### list_item.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginEnd="8dp"
        android:layout_marginBottom="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <ImageView
            android:id="@+id/iv_profile"
            android:layout_width="60dp"
            android:layout_height="60dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:srcCompat="@drawable/ic_baseline_man" />

        <TextView
            android:id="@+id/tv_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="24dp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp"
            android:text="Cholee"
            android:textColor="@color/black"
            android:textStyle="bold"
            app:layout_constraintBottom_toTopOf="@+id/tv_job"
            app:layout_constraintStart_toEndOf="@+id/iv_profile"
            app:layout_constraintTop_toTopOf="@+id/iv_profile" />

        <TextView
            android:id="@+id/tv_job"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="24dp"
            android:layout_marginTop="8dp"
            android:layout_marginBottom="8dp"
            android:text="Android Application Developer"
            android:textColor="@color/black"
            android:textStyle="bold"
            app:layout_constraintBottom_toBottomOf="@+id/iv_profile"
            app:layout_constraintStart_toEndOf="@+id/iv_profile"
            app:layout_constraintTop_toBottomOf="@+id/tv_name" />

        <TextView
            android:id="@+id/tv_age"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="8dp"
            android:text="24"
            android:textColor="#969696"
            android:textStyle="bold"
            app:layout_constraintBottom_toBottomOf="@+id/tv_name"
            app:layout_constraintStart_toEndOf="@+id/tv_name"
            app:layout_constraintTop_toTopOf="@+id/tv_name" />
    </androidx.constraintlayout.widget.ConstraintLayout>
</androidx.constraintlayout.widget.ConstraintLayout>
```

#### activity_main.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv_profile"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Class

#### Profiles.kt

```
class Profiles(val gender: Int, val name: String, val age: Int, val job: String)
```

- Java의 Getter/Setter에 해당하는 Kotlin 구문이다.

#### ProfileAdapter.kt

```
class ProfileAdapter(val profileList: ArrayList<Profiles>) : RecyclerView.Adapter<ProfileAdapter.CustomViewHolder>() {
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gender = itemView.findViewById<ImageView>(R.id.iv_profile)
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val age = itemView.findViewById<TextView>(R.id.tv_age)
        val job = itemView.findViewById<TextView>(R.id.tv_job)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = absoluteAdapterPosition
                val profile: Profiles = profileList.get(curPos)
                Toast.makeText(parent.context, "이름: ${profile.name}\n직무: ${profile.job}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.gender.setImageResource(profileList.get(position).gender)
        holder.name.text = profileList.get(position).name
        holder.age.text = profileList.get(position).age.toString()
        holder.job.text = profileList.get(position).job
    }

    override fun getItemCount(): Int {
        return profileList.size
    }
}
```

- 생성한 `Profiles`를 토대로 `ArrayList<>`를 생성하여 어댑터 클래스를 생성하는 예제이다.
- `ListView`와는 다르게 `BaseAdaper`가 아닌 `RecyclerView.Adapter<어댑터_클래스_명.뷰_홀더_이름>()`형식을 사용한다.
- `CustomViewHolder`라는 내부 클래스를 생성하여 xml파일 리스트 안의 각 id들을 지정한 변수를 생성한다.
- `onCreateViewHolder`에서`absoluteAdapterPosition`을 이용하여 현재 클릭한 포지션 값을 받아와서 입력된 값을 불러올 수 있게할 수 있다.
  - Legacy 지원으로 `adapterPosition`을 사용할 수도 있으나 `absoluteAdapterPosition`이 안드로이드에서 더 권장하는 사용 방법이 되었다.
- `onBindViewHolder`를 이용하여 `CustomViewHolder`에서 리스트에 지정한 변수들에 값을 불러오게 한다.
- `getItemCount`는 리스트의 크기를 불러오는 역할을 수행한다.

## Activity

MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val profileList = arrayListOf(
            Profiles(R.drawable.ic_baseline_man, "박정호", 34, "Dev Team Leader"),
            Profiles(R.drawable.ic_baseline_man, "이승철", 24, "Android Developer"),
            Profiles(R.drawable.ic_baseline_man, "심형석", 24, "Tax Accounting"),
            Profiles(R.drawable.ic_baseline_man, "김철수", 30, "Flutter Developer"),
            Profiles(R.drawable.ic_baseline_woman_24, "이영희", 25, "App QA Tester"),
            Profiles(R.drawable.ic_baseline_woman_24, "최지현", 31, "BackEnd Developer")
            )

        binding.rvProfile.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvProfile.setHasFixedSize(true)

        binding.rvProfile.adapter = ProfileAdapter(profileList)
    }
}
```

- `RecyclerView`의 형식을 layoutManager를 이용하여 지정해줄 수 있다. 형식은 다음과 같다.

  - `LinearLayoutManager.VERTICAL/HORIZENTAL`: `LinearLayout`의 세로/가로 보기 형식
  - `GridLayoutManager`: 그리드 형식의 보기, 가로/세로에 맞는 열과 행의 수를 지정한 보기 형식
  - `StaggeredGridLayoutManager`: 불규칙한 배열의 그리드 생성(인스타그램의 검색뷰와 유사해보인다)

- `setHasFixedSize()`는 View안의 아이템들에 대해 가변적으로 사용할지(false), 고정된 크기를 사용할지(true) 지정할 수 있다.
- 이후 `adapter`를 생성한 `ProfileAdapter`로 지정하고 현재 액티비티에서 생성한 `profileList`를 지정하여 연결해준다.

---

[구글 공식문서](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView?authuser=1&hl=ko),
[홍드로이드](https://www.youtube.com/watch?v=jsjYo-xy3EA&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=10),
[yunaaaas님 tistory](https://yunaaaas.tistory.com/)
