# 3. TextView

TextView는 사용자에게 텍스트를 표시하는 사용자 인터페이스 요소이다.

```
kotlin.Any
    ↳android.view.View
        ↳android.widget.TextView
```

## layout xml 파일 생성

activity_main.xml

```XML
<LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      android:layout_width="match_parent"
      android:layout_height="match_parent">
     <TextView
         android:id="@+id/tvTitle"
         android:layout_height="wrap_content"
         android:layout_width="wrap_content"
         android:text="Hello TextView!" />
  </LinearLayout>
```

## findViewById 사용

MainActivity.kt

```KOTLIN
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)

          var textView = findViewById(R.id.tvTitle) as TextView
          textView.setText("Welcome to My Application!")
      }
  }

```

문법적인 차이를 제외하고 Java에서 사용하는 방식과 동일하다.
`(TextView)` 형식을 이용한 타입변환은 `as` 키워드로 대체한다.
`var textView:TextView = findViewById(R.id.tvTitle)`와 같이 변수에 타입을 선언할 수도 있다.

## viewBinding 사용

```KOTLIN
class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvTitle.text = "안녕하세요!"
    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}

```

`.setText()`를 `.text = ""`으로 바꿔서 사용할 수 있다.

---

참조
[구글 공식문서](https://developer.android.com/reference/kotlin/android/widget/TextView),
[홍드로이드](https://www.youtube.com/watch?v=IaXhn_I_ziY&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=1)
