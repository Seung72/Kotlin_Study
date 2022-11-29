# 4. EditText & Button

### EditText

EditText는 텍스트를 입력하고 수정하기 위한 사용자 인터페이스 요소이다.

```
kotlin.Any
    ↳android.view.View
        ↳android.widget.TextView
            ↳android.widget.EditText
```

### Button

사용자가 탭하거나 클릭하여 작업을 수행할 수 있는 사용자 인터페이스 요소이다.

```
kotlin.Any
    ↳android.view.View
        ↳android.widget.TextView
            ↳android.widget.Button
```

## layout xml 파일 생성

activity_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
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
        android:layout_marginBottom="32dp"
        android:text="입력될 위치"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/et_id"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:ems="10"
        android:hint="텍스트를 입력하세요"
        android:inputType="textPersonName"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_title" />

    <Button
        android:id="@+id/btn_getText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="16dp"
        android:text="텍스트 입력"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/et_id" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Activity

MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() =  mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetText.setOnClickListener {
            binding.tvTitle.text = binding.etId.text.toString()
        }
    }
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}
```

버튼 클릭 시 TextView에 EditText에 입력된 값이 입력되는 예제이다.
TextView와 같이 값을 가져오거나 입력할때 .text 형식으로 입력할 수 있다.

---

참조
[구글 공식문서 (EditText)](https://developer.android.com/reference/kotlin/android/widget/EditText), [구글 공식문서 (Button)](https://developer.android.com/s/results/?q=button), [홍드로이드](https://www.youtube.com/watch?v=J-PsYQlgPWw&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=2)
