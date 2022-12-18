# 13. Coroutine

`Coroutine`은 **비동기**적인 실행 코드를 간소화하기 위해 사용하는 동시 실행 설계 패턴이다.
안드로이드에서의 `Coroutine`은 기본 스레드와 별도로 실행되어 장기적인 실행 작업을 관리하는 데에 도움이 된다.

### 동기? 비동기?

`Coroutine`의 사용에 앞서 동기와 비동기에 대한 개념에 대한 이해가 필요하다.
프로그래밍에서의 **동기**의 개념은 어떠한 코드 블럭에 대해 순차적으로 실행이 되는 경우이다.
A라는 함수와 B라는 함수가 순서대로 작동된다고 했을 때, B함수는 A함수가 종료될 때까지 실행되지 않는다. 이러한 개념과 반대되는 개념이 **비동기**라고 할 수 있다.
비동기 코드는 B라는 함수가 A라는 함수가 끝나지 않더라도 별개로 실행할 수 있게 해준다.
`Coroutine`은 공식적으로 안드로이드의 비동기 프로그래밍에 권장되는 방안이기 때문에 공부할 필요가 있다.

### Scope

`Coroutine`의 제어 범위는 다음과 같이 나눌 수 있다.

- `GlobalScope`: 프로그램의 어디서나 제어와 동작이 가능한 범위이다.
- `CoroutineScope`: `Dispatcher`를 지정하여 제어와 동작이 가능하다.
  - `Dispatchers.Default`: 백그라운드 동작
  - `Dispatchers.IO`: I/O에 최적화 된 동작
  - `Dispatchers.Main`: 메인스레드 동작

### launch와 async과 그 외

- `launch`: 반환값이 없는 `Job`객체
  - `join()`: `Job`의 실행이 끝날 때까지 대기
- `async`: `Deferred<T>`라는 값을 반환하는 객체
  - `await()`: 작업 완료를 기다리고 T타입 객체의 결과 값 반환
- 공통 메소드
  - `delay()`: `milisecond`만큼 루틴을 잠시 대기시키는 함수
  - `cancel()`: `Coroutine`을 정지시키는 역할수행, 주로 `delay()`가 사용된 이후 종료
- 공통 함수
  - `runBlocking{}`: 블럭 안의 `Coroutine`이 끝나기 전까지 메인 루틴을 대기시킨다.
  - `withTimeoutOrNull(){}`: `milisecond`만큼의 시간 내에 수행되면 결과를 반환하고 아닌 경우 Null을 반환하는 함수

⚠ `join`, `await`, `runBlocking` 등의 사용은 안드로이드에선 ANR이 유도될 수 있다.

## build.gradle (:app)

```
dependencies {
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9'
    ...
    }
```

- `OkHttp` 라이브러리를 사용할 경우 추가해야하는 구문이다.
- `Retrofit`이라는 라이브러리도 많이 사용되고 있으며, 가능하면 추후에 다뤄보겠다.

## layout xml

activity_main.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <TextView
        android:id="@+id/tv_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

## Activity

MainActivity.kt

```
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        coroutine()
        getHtml()
    }

    fun coroutine() {
        // 동기 실행, (Main = MainThread)
        CoroutineScope(Dispatchers.Main).launch {

            // 비동기 실행, (Default = BackgroundThread)
            val html = CoroutineScope(Dispatchers.Default).async {
                getHtmlStr()
            }.await() // val로 값을 받아오기 위해 .await() 사용
            // getHtmlStr() <- 오류 발생
            binding.tvText.text = html
        }
    }
    fun getHtmlStr() : String {
        val client = OkHttpClient.Builder().build()
        val req = Request.Builder().url("https://www.naver.com").build()
        client.newCall(req).execute().use {
            response -> return if(response.body != null) {
                response.body!!.string()
        }
            else {
                "요청한 페이지에 문제가 있습니다."
        }
        }
    }
    // 비동기 방식으로 구현하고 CallBack 함수로 받기
    fun getHtml() {
        val client = OkHttpClient.Builder().build()
        val req = Request.Builder().url("https://www.naver.com").build()
        client.newCall(req).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
            }
            override fun onResponse(call: Call, response: Response) {
                CoroutineScope(Dispatchers.Main).launch {
                    binding.tvText.text = response.body!!.string()
                }
            }
        })
    }
}
```

- 메인에서 실행될 `CouroutineScope.launch`에선 값을 지정해줘야하는 변수들에 대해서 지정을 해주고
- 백그라운드로 실행될 `CoroutineScope.async`에선 네트워크 통신과 관련된 구문들을 사용해주는게 권장된다.
- `getHtml()`함수처럼 함수 안에서의 `enqueue`와 `callback`을 이용한 `coroutine`사용도 가능해진다.

정리하며, `coroutine`은 사용용도가 정말 다양하고 여태 그래왔지만 이 문서에 써 있는 것은 극히 일부다. 더 궁금해지고 필요한 때가 오면 공부하게 될 거 같다!

---

참조
[구글 공식문서](https://developer.android.com/kotlin/coroutines),
[센치한개발자](https://www.youtube.com/watch?v=yIdFRXHawYc),
[테크과학! DiMo](https://www.youtube.com/watch?v=Lpieg1zrKdg&t=35s),
[codechacha](https://codechacha.com/ko/android-coroutine/#4-launch-async%EB%A1%9C-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EC%8B%A4%ED%96%89)
