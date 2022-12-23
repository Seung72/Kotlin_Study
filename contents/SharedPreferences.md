# 9. SharedPreferences

```
android.content.SharedPreferences
```

`SharedPreferences`는 `getSharedPreferences`에 의해 반환된 데이터에 대해 접근하고 수정하기 위한 `Interface`이다.
이를 활용하여 간단한 앱 내의 데이터에 대해 DB처럼 활용할 수 있다.
데이터는 xml형태로 data/data/패키지명/shared_prefs/SharedPreference 경로에 저장된다.

앱 내에서 사용자의 설정을 저장한다거나, 이름을 저장하는 등의 활용이 가능하고, 해당 예제에선 `EditText`에서 액티비티가 파괴될 때 문자열을 저장할 수 있는 예제이다.

## layout xml 파일

activity_main.xml

```XML
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <EditText
        android:id="@+id/et_hello"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:ems="10"
        android:textSize="24sp"
        android:textColor="@color/black"
        android:inputType="textPersonName"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

```

## Activity

MainActivity.kt

```KOTLIN
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }
}
```

액티비티에 입력된 `EditText`를 액티비티가 파괴될 때 저장하고 액티비티가 실행될 때 불러오는 예제이다.
해당 예제에선 크게 두 가지의 함수로 기능을 나눌 수 있다.

- saveData: `EditText`의 내용을 특정 변수에 담고 저장한다.
- loadData: `EditText`에 저장된 내용 또는 없을 시 기본 값을 할당한다.

```KOTLIN
    private fun saveData() {
        val prefName = getSharedPreferences("pref", 0)
        val edit = prefName.edit()
        edit.putString("name", binding.etHello.text.toString())
        edit.apply()
    }
```

prefName에 `getSharedPreferences(String name, int mode)` 객체를 담아준다. 인자 정보는 다음과 같다.

- `name`: 해당 이름으로 xml파일이 생성됨, (생략 가능하나 생략 시 해당 액티비티에서만 사용할 수 있음)
- `mode`: 객체 획득 시 지정할 수 있으며, 아래의 모드 외에도 더 다양함.
  - `MODE_PRIVATE`: 해당 앱 내에서만 사용 가능
  - `MODE_WORLD_READABLE`: 외부 앱에서 읽기 가능
  - `MODE_WORLD_WRITEABLE`: 외부 앱에서 쓰기 가능

edit 변수에 prefName.edit()을 담아서 수정할 수 있게 변경한다.
이후 `putStirng(String name, String data)` 키, 값 형태로 값을 저장한다.

```KOTLIN
    private fun loadData() {
        val prefName = getSharedPreferences("pref", 0)
        binding.etHello.setText(prefName.getString("name","Guest"))
    }
```

같은 데이터 형식으로 prfName을 만들고 getString을 사용하여 키 name을 사용하여 맞는 값을 가져온다. 없을 시 Guest 라는 기본 문구가 출력된다.

---

참조
[구글 공식문서](https://developer.android.com/reference/android/content/SharedPreferences),
[홍드로이드](https://www.youtube.com/watch?v=cyqgR8VTC1Y&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=7),
[남산 아래 개발자들](http://devstory.ibksplatform.com/2017/12/android-sharedpreferences.html)
