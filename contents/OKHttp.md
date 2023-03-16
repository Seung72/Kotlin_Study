# 19. OKHttp

`OkHttp`는 `Http`기반의 네트워크 통신을 위해 만들어진 `Square`의 라이브러리이다.
기본적으로 `HttpURLConnection`과 `Apache HTTP Client`를 사용해 왔으나 다음과 같은 문제점들이 있다.

- 각 클라이언트의 비동기 처리 또는 백그라운드 스레드 메서드를 위해 상용구를 작성해야 했다.
- 요청과 연결 풀링을 취소하는 과정에서 제한 사항이 있다.

하지만 `OkHttp`는 추가 종속성을 필요로 하지 않고 Java의 소켓 상단에서 직접 작업하기 때문에 위 과정들을 생략할 수 있다는 장점이 있다.

이번 예제도 사실 저번 [`HttpURLConnection`](https://github.com/Seung72/Kotlin_Study/blob/main/contents/HttpURLConnection.md)과 유사한 예제이다. 줄어든 구문과 안정성에 대해서 생각해보자.

### AndroidManifest.xml

```Xml
...
<uses-permission android:name="android.permission.INTERNET"/>
...
```

- 소켓 통신을 사용하기 때문에 인터넷 권한은 기본적으로 설정해주어야 한다.

### build.gradle(:app)

```Groovy
dependencies {
    implementation 'com.squareup.okhttp3:okhttp:4.10.0'
    ...
}
```

- 2023년 3월 기준 현재 최신 버전은 4.10.0이다.

### layout Xml

activity_main.xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />
    </ScrollView>
</androidx.constraintlayout.widget.ConstraintLayout>
```

### Activity

MainActivity.kt

```Kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val url = "https://www.google.co.kr"

        getHtmlBody(url)
    }

    private fun getHtmlBody(url: String) {
        // 통신을 하기 위한 클라이언트 객체 생성
        val client = OkHttpClient()
        // 인자로 받은 주소로 객체 생성
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback {

            // 오류 발생 시 처리
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error", "onFailure: client.newCall.enqueue", )
            }

            // 응답이 response 변수 안에 담기게 된다.
            override fun onResponse(call: Call, response: Response) {
                // Ui 변경을 처리하기 위한 쓰레드 생성으로, 불필요하다면 없어도 된다.
                val thread = Thread() {
                    runOnUiThread {
                        binding.tv.text = response.body!!.string()
                    }
                }
                thread.start()
            }
        })
    }
}
```

---

참조
[Square's OkHttp](https://square.github.io/okhttp/),
[DigitalOcean](https://www.digitalocean.com/community/tutorials/okhttp-android-example-tutorial),
[Uni-Stack's Android](https://android-uni.tistory.com/2)
