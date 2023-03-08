# 18. HttpURLConnection

```Java
java.lang.Object
   ↳ java.net.URLConnection
 	   ↳ java.net.HttpURLConnection
```

`HttpURLConnection`은 HTTP 관련 기능을 제공하는 객체이다. `Retrofit`, `OKHttp`와 같은 라이브러리의 등장으로 인해 잘 쓰이지는 않지만 결국 위 라이브러리들도 `HttpURLConnection`에서 파생되어 나오기 때문에 전체적인 구조를 이해하면 좋다.

예제는 특정 웹의 `Body` 부분을 `TextView`로 가져오는 간단한 프로그램을 구현했다.

### AndroidManifest.xml

```Xml
...
<uses-permission android:name="android.permission.INTERNET"/>
```

- 인터넷 통신을 사용하기 때문에 따로 권한을 지정해준다.

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
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/tv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="불러오는 중입니다.."
            android:textColor="@color/black"
            android:textSize="30sp"
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv)

        var responseBody: String

        val url = "https://developer.android.com/reference/java/net/HttpURLConnection"
        val requestMethod = "GET"
        val httpConnectThread = Thread() {
            responseBody = httpUrlConnection(url, requestMethod)
            runOnUiThread {
                tv.textSize = 10F
                tv.text = responseBody
            }
        }
        httpConnectThread.start()
    }

    private fun httpUrlConnection(url: String, requestMethod: String): String {
        val url = URL(url)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = requestMethod
        var connectionCode = connection.responseCode

        if(connectionCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val reader = InputStreamReader(inputStream)
            val bufferedReader = BufferedReader(reader)
            val response = StringBuffer()

            var inputLine: String?
            var responseBody: String?

            while (bufferedReader.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }

            reader.close()
            connection.disconnect()
            responseBody = response.toString()

            Log.d("Connection Success!", responseBody)
            return responseBody

        } else {
            Log.e("Connection Error..", connectionCode.toString())
            return "Connection Error"
        }
    }
}
```

- `httpUrlConnection` 함수
  - 웹의 주소와 요청 방식을 인자로 받아온다.
  - `val connection = url.openConnection() as HttpURLConnection`: `url.openConnection()`을 `HttpURLConnection`타입으로 변경하여 변수로 저장한다.
  - `connection.requestMethod = requestMethod`: 받아온 인자인 "GET"으로 지정한다.
  - `if(connectionCode == HttpURLConnection.HTTP_OK)`: 연결이 정상적으로 연결되었을 때 실행된다.
    - `val inputStream = connection.inputStream ...`: 네트워크 소켓을 통해 데이터를 읽기 위해서 `inputStream`과 `inputStreamReader()`를 사용한다.
    - `val bufferedReader = BufferedReader(reader)`: 데이터를 라인 단위로 읽어오기 위해 사용한다.
    - 이후 while문을 통해 읽어온 데이터를 한줄씩 `response`에 추가한다.
    - 사용이 끝났을 때, close(), disconnect()함으로 메모리, 네트워크 낭비를 방지한다.
    - 결과를 반환한다.
- `onCreate()`
  - 함수에 인자를 전달하고 실행하는 역할이다.
  - 네트워크 통신은 메인스레드 환경에서 제한되기 때문에 별도의 쓰레드를 사용한다.
  - 쓰레드 환경에서 UI를 업데이트 하기 위해서 `runOnUiThread{...}`블록을 만들어 제어한다.
