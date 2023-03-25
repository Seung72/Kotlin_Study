# 20. Retrofit

`Retrofit`은 `HTTP API`를 자바의 인터페이스로 바꿔주는 역할을 하는 라이브러리다.
서버와 통신하는 코드를 더 쉽게 작성할 수 있도록 도와준다.

- 해당 예제는 `jsonplaceholder`에서 테스트용으로 제공하는 `API`를 사용하여 받아온 정보를 화면에 보여주는 예제이다.

### 구성요소

- DTO(Data Transfer Object): 받아온 데이터들을 담아줄 데이터 클래스
- Service: 어노테이션을 이용하여 `HTTP Method` 4가지를 사용
- Client: 통신에 사용될 인스턴스 객체

### AndroidManifest.xml

```Xml
<uses-permission android:name="android.permission.INTERNET"/>
```

- 네트워크 소켓 통신을 필요로 하기 때문에 필수적인 권한을 작성한다.

### build.gradle(:app)

```Groovy
android {
    ...
    viewBinding {
        enabled true
    }
}
dependencies {
    def retrofit_version = "2.9.0"

    implementation "com.squareup.retrofit2:retrofit:$retrofit_version"
    implementation "com.squareup.retrofit2:converter-gson:$retrofit_version" // 역직렬화를 위한 의존성
    implementation "com.squareup.retrofit2:converter-scalars:$retrofit_version"
    implementation "com.google.code.gson:gson:$retrofit_version"
}
```

### layout Xml

```Xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tv_UserId"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="User"
        android:layout_margin="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_Id"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:text="ID"
        app:layout_constraintEnd_toStartOf="@+id/tv_UserId"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_title"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="title"
        android:layout_margin="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_Id" />

    <TextView
        android:id="@+id/tv_body"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="body"
        android:layout_margin="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tv_title" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

### Class

##### JsonPlaceDTO.kt

```Kotlin
data class JsonPlaceDTO(
    @Expose // Json으로 부터 역직렬화 됨을 명시
    @SerializedName("userId") // Json에서 해당 이름을 가진 멤버가 대입됨을 명시
    var userId: String,

    @Expose
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("title")
    var title: String,

    @Expose
    @SerializedName("body")
    var body: String
)
```

- `@Expose` 어노테이션은 Json으로 받아온 직렬화된 데이터들을 사용할 수 있는 상태의 데이터, 즉 역직렬화된 데이터의 형태로 사용함을 명시한다.
- `@SerializedName()`: 지정한 이름을 Json에서 사용한다는 것을 명시한다.

##### Service.kt

```Kotlin
interface Service {
    @GET("/posts")
    fun get() : Call<JsonObject>

    @GET("/posts/{path}")
    fun getPath(@Path("path") path: String) : Call<JsonPlaceDTO>

    @POST("/posts")
    fun post() : Call<JsonPlaceDTO>
}
```

##### RetroClient.kt

```Kotlin
object RetroClient {
    private var instance: Retrofit? = null
    fun getInstance(): Retrofit {
        instance?.let {
            return it
        } ?: run {
            instance = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                // 응답을 변환할 방식 (Gson으로 변경)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return instance!!
        }
    }
}
```

- `baseUrl()`: root url을 설정한다.
- `addConverterFactory()`: 응답을 변환할 방식을 지정한다.
  - `GsonConverterFactory.create()`: Json 구조의 데이터를 Gson 형태로 역직렬화를 진행

### Activity

MainActivity.kt

```Kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        val service = RetroClient.getInstance().create(Service::class.java) // 클라이언트 인스턴스 생성
        val call = service?.getPath("2") // 요청을 서버에 전송하고 해당 변수에 반환
        setContentView(view)

        call?.enqueue(object : Callback<JsonPlaceDTO> {
            override fun onResponse(call: Call<JsonPlaceDTO>, response: Response<JsonPlaceDTO>) {
                if(response.isSuccessful) {
                    response.body()?.let {
                        binding.tvId.text = "현재 아이디: " + it.id
                        binding.tvUserId.text = it.userId + "님"
                        binding.tvTitle.text = "제목: " + it.title
                        binding.tvBody.text = "본문: " + it.body
                    }
                }
            }
            override fun onFailure(call: Call<JsonPlaceDTO>, t: Throwable) {
                runOnUiThread{ binding.tvBody.text = "네트워크에 문제가 있습니다."}
                Log.e("onFailure-0", "onFailure: ${t.message}", )
            }
        })
    }
}
```

- `enqueue`: 요청을 서버에 전송하고 비동기 처리하는 콜백
  - `onResponse`: 응답에 성공하면 실행되는 콜백
  - `onFailure`: 응답에 실패하면 실행되는 콜백

---

참고
[Square Open Source](https://square.github.io/retrofit/),
[JeepChief](https://velog.io/@jeep_chief_14/Retrofit%EC%9C%BC%EB%A1%9C-Rest-API%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%B4%EB%B3%B4%EC%9E%90),
[배움이 즐거운 개발자](https://galid1.tistory.com/501)
