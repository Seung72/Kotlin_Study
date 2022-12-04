# 6. ImageView&Toast

### ImageView

Bitmap 이나 Drawable 리소스와 같은 이미지 리소스를 표시합니다. ImageView는 일반적으로 이미지에 색조를 적용하고 이미지 크기 조정을 처리하는 데 사용한다.

```
kotlin.Any
    ↳android.view.View
        ↳android.widget.ImageView
```

### Toast

```
kotlin.Any
    ↳android.widget.Toast
```

## layout xml 파일 생성

activity_main.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ImageView
        android:id="@+id/iv_profile"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/ic_android_black_24dp" />

    <Button
        android:id="@+id/btn_Toast"
        android:layout_width="70dp"
        android:layout_height="70dp"
        android:background="@drawable/ic_baseline_add_circle_24"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/iv_profile"
        app:layout_constraintVertical_bias="0.5" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

ImageView에 구애받지 않고 다른 뷰에서도 `android:background`로 지정하여 이미지를 사용할 수 있다.

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

        binding.btnToast.setOnClickListener{
            binding.ivProfile.setImageResource(R.drawable.ic_baseline_ads_click_24)

            Toast.makeText(this@MainActivity, "클릭하셨어요!", Toast.LENGTH_SHORT).show()
        }

    }
}
```

`this@MainActivity`를 이용하여 context를 얻을 수 있다.

---

참조
[구글 공식문서(ImageView)](https://developer.android.com/reference/kotlin/android/media/Image?hl=en)
[구글 공식문서(Toast)](https://developer.android.com/reference/kotlin/android/widget/Toast?hl=en)
[홍드로이드](https://www.youtube.com/watch?v=fmiwEfFrjsM&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=5)
