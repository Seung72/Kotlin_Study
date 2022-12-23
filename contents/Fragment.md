# 12. Fragment

`Fragment`는 재사용성과 모듈성을 가진 요소로써, 단일화면이나 화면 일부의 UI를 관리하는데 적합하다.

- [`NavigationView`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/NavigationView.md), `Viewpager2` 등과 같은 일부 [`Android Jetpack`](https://developer.android.com/jetpack/androidx/versions?authuser=1&hl=ko) 라이브러리와 호환되도록 설계되었다.
- `Fragment`는 다양하게 활용되고 있으며 해당 예제는 간단한 구현을 위한 구성이고, 공식 문서만 하더라도 분량이 방대한 편이다. `Fragment`에서 원하는 기능이 있다면 구현 방법에 대해 따로 공부가 필요할 것이다.

## layout xml

#### activity_main.xml

```XML
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="16dp"
        android:orientation="horizontal">
        <Button
            android:id="@+id/btn_fragment1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="fragment1"/>
        <Button
            android:id="@+id/btn_fragment2"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="fragment2"/>
        <Button
            android:id="@+id/btn_fragment3"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="fragment3"/>
    </LinearLayout>
    <FrameLayout
        android:id="@+id/main_frame"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_margin="16dp"/>
</LinearLayout>
```

- 위 파일은 베이스가 된 레이아웃을 제외하고 `LinearLayout`과 `FrameLayout`으로 나눈 구조이고 `LinearLayout`엔 `Button`들이 구성되어 있고 이와 상호작용하면 `FrameLayout`에서 그에 상응하는 `Fragment`를 화면에 출력하는 예제이다.

#### frag1.xml

```XML
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="프래그먼트1"
        android:textColor="@color/black"
        android:textSize="32sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

- 편의상 frag2.xml, frag3.xml 파일은 위와 같은 형식을 취하고 있다고 가정하고 패스하겠다.

## Class

Fragment1.kt

```KOTLIN
class Fragment1 : Fragment() {
    private var mBinding: Frag1Binding? = null
    private val binding get() = mBinding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    mBinding = Frag1Binding.inflate(layoutInflater)
    return binding.root
    }
}
```

- 클래스 파일에 `Fragment`를 [상속](https://github.com/Seung72/Kotlin_Study/blob/main/contents/%EB%AC%B8%EB%B2%95.md#extands)하여 사용한다.
- `Fragment`에서 [`ViewBinding`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/viewBinding.md) 사용 시 위와 같은 형식으로 작성해야한다.
- 개인적으로 `mBinding`에 [`Null Safety`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/%EB%AC%B8%EB%B2%95.md#null-safety)로 초기화하는 방식보단 바로 `binding`에 초기화하는 방식을 선호했지만, `Fragment`에선 `get()` 사용해야하고, `get()`은 Null의 허용이 되어있지 않으면 오류가 발생하는듯하다.
- `Fragment2.kt`, `Fragment3.kt`도 위와 같은 형식이기 때문에 패스한다.

## Activity

MainActivity.kt

```KOTLIN
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setFrag(0)
        binding.btnFragment1.setOnClickListener {
            setFrag(0)
        }
        binding.btnFragment2.setOnClickListener {
            setFrag(1)
        }
        binding.btnFragment3.setOnClickListener {
            setFrag(2)
        }
    }
    private fun setFrag(fragNum : Int) {
        val ft = supportFragmentManager.beginTransaction()
        when(fragNum) {
            0 -> {
                ft.replace(R.id.main_frame, Fragment1()).commit()
            }
            1 -> {
                ft.replace(R.id.main_frame, Fragment2()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, Fragment3()).commit()
            }
        }
    }
}
```

- 주요 함수로 `setFrag`를 생성하여 Java의 `switch`문과 같은 역할을 수행하는 `when`문을 이용하여 해당하는 인자를 전달 받았을 때 변수 `ft`에 `supportFragmentManager.beginTransaction()`+`.replace(프레임ID, 코틀린_클래스).commit()`을 통해 해당 화면으로 변경되게끔 사용할 수 있다.

---

참조
[구글 공식문서](https://developer.android.com/guide/fragments/create),
[홍드로이드](https://www.youtube.com/watch?v=BT206iXW9bk)
