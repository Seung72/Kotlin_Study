# 5.Intent

Intent는 애플리케이션 구성요소(컴포넌트) 간에 작업 수행을 위한 정보를 전달한다.

```
kotlin.Any
    ↳android.content.Intent
```

## layout xml 파일 생성

##### activity_main.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvSendMessage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="메세지 전달!!"
        android:textSize="36sp"
        android:textColor="@color/black"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn_intent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvSendMessage" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

##### activity_sub.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".SubActivity">

    <TextView
        android:id="@+id/tvGetMsg"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="SubActivity"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## Activity

##### MainActivity.kt

```
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnIntent.setOnClickListener{
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("msg", binding.tvSendMessage.text.toString())
            startActivity(intent)
            finish()
        }
    }
}
```

intent객체에 현재 Activity와 이동할 Activity를 초기화하고 `intent.putExtra("이름", 데이터)`를 이용하여 데이터를 다른 화면으로 전송한다.
이후 `startActivity(intent)`를 통하여 해당 Activity를 실행할 수 있고 `finish()`를 사용하여 현 Activity를 파괴할 수 있다.

##### SubActivity.kt

```
class SubActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.hasExtra("msg")) {
            binding.tvGetMsg.text = intent.getStringExtra("msg")
        }
    }
}
```

if문과 `intent.hasExtra("이름")`을 사용하여 해당 데이터 수신 여부를 확인하여 작업을 수행할 수 있다.
