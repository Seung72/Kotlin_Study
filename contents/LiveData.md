# 15. LiveData

```
java.lang.Object
    ↳ android.arch.lifecycle.LiveData<T>
```

`LiveData`는 주어진 생명 주기 내에서 감시(observed)할 수 있는 데이터 홀더 클래스이다. 즉 `Observer`는 `LifecycleOwner`와 쌍으로 추가될 수 있으며 `LifecycleOwner`가 활성화 상태인 경우에 래핑된 데이터의 수정에 대한 알림을 제공한다.

### bulid.gradle(:app)

```Groovy
dependencies {
    ...
    implementation 'androidx.appcompat:appcompat:1.6.0'
    ...
}
```

- 대부분의 프로젝트에는 기본적으로 추가되어 있고, 종속성에 해당 구문이 추가되어 있는지만 확인하면 된다.

이번 예제는 사용자가 버튼을 눌렀을 때 `EditText`에 중복된 값이 입력되어 있을 경우 버튼을 몇 번 눌렀는지 알려주는 간단한 프로그램을 작성했다.

### layout xml

activity_main.xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_liveText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/et_liveText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_liveText" />

    <Button
        android:id="@+id/btn_apply"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="값 변경"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/et_liveText" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Activity

MainActivity.kt

```Kotlin
class MainActivity : AppCompatActivity() {

    // viewBinding 사용
    private lateinit var binding: ActivityMainBinding

    // MutableLiveData<Type> 형식으로
    private var liveData: MutableLiveData<String> = MutableLiveData()
    private var cnt = 0
    private var staticStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // liveData.value로 값을 변경할 시 동작된다.
        liveData.observe(this, Observer {
            // 변경된 값(it)을 textView에 보여준다.
            binding.tvLiveText.text = it
        })

        binding.btnApply.setOnClickListener{
            val userText = binding.etLiveText.text.toString()
            if (userText != "") {
                if (staticStr == userText) liveData.value = userText + " 중복 횟수 ${++cnt}"
                else {
                    cnt = 0
                    staticStr = userText
                    liveData.value = userText + " 중복 횟수 ${++cnt}"
                }
            } else {
                cnt = 0
                liveData.value = "아무 값도 입력되어 있지 않습니다."
            }
        }
    }
}
```

---

참고
[구글 공식문서](https://developer.android.com/reference/androidx/lifecycle/LiveData?authuser=1),
[팀노바 & Stickode 개발자 블로그](https://stickode.tistory.com/194)
