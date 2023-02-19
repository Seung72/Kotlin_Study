# 14. dataBinding

`dataBinding`은 `Layout`의 UI 구성요소를 선언적 형식을 사용하여 데이터 소스에 결합한다.

`dataBinding`에는 `viewBinding`또한 포함되어 있다. 하지만 `findViewById`를 대체하기 위한 용도로 쓴다면 `viewBinding`만 쓰는 것이 퍼포먼스와 용량 측면에서 더 유리하다.

## DataBinding을 사용하는 이유

- `findViewById` 없이 `Layout`과 데이터를 바인딩한다.
- MVVM 패턴 구현을 위해 주로 사용된다.
- 양방향 바인딩을 지원한다.
  - 옵저버 패턴을 적용하여 실시간으로 데이터를 업데이트할 수 있다.

## build.gradle (:app)

```Groovy
plugins {
    ...
    id 'kotlin-kapt'
}
android {
    dataBinding {
        enabled = true
    }
//    또는 이렇게 입력해도 괜찮다.
//    buildFeatures {
//        dataBinding true
//    }
}
dependencies {
    ...
    kapt 'com.android.databinding:compiler:3.1.4'
}
```

## layout xml

activity_main.xml

```Xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="welcomeText"
            type="com.cholee.e12_databinding.GreetingsText" />
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".MainActivity">

        <TextView
            android:id="@+id/userName"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{@string/greetings(welcomeText.firstText, welcomeText.secondText)}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </LinearLayout>
</layout>
```

XML에서 `dataBinding`을 사용하기 위한 방법은 다음과 같다.

- 상위 레이아웃을 `layout`으로 변경한다.
- `data`에서 `variable`을 생성한다.
- 해당 xml에서 사용할 이름을 `name`에 작성한다.
- 어떤 [`data class`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/%EB%AC%B8%EB%B2%95.md#data-class)를 참조할 것인지 패키지명.클래스 이름을 작성한다.
- 데이터를 입힐 곳에 @{이름.변수} 형식으로 입력한다.

## Class

GreetingsText.kt

```kotlin
data class GreetingsText(
    val firstText: String,
    val secondText: String
)
```

- xml에 사용할 변수명과 타입을 명시해준다.

## Activity

MainActivity.kt

```Kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        with(binding) {
            welcomeText = GreetingsText("Hello", "dataBinding!")
        }
    }
}
```

- [`viewBinding`]()과 같이 `lateinit`를 사용하여 바인딩 객체의 초기화를 연기할 수 있다.
- `data class`의 클래스와 전달할 인자, xml에서 작성한 데이터 이름을 사용한다.

추가로, 현재 이 예시에서는 `with`함수 블럭을 사용하여 객체 이름을 반복하지 않도록 만들었다. `binding`으로 사용할 객체가 많아지게 될 때 블럭 안에서 선언하면 조금 더 간결한 코드를 작성할 수 있다.

---

참조
[구글 공식문서](https://developer.android.com/jetpack/androidx/releases/databinding?hl=ko),
[개복치개발자](https://philosopher-chan.tistory.com/1308)
