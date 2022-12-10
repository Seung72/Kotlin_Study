# 10. WebView

`WebView`는 웹을 보여주는 `View`이다.

```
kotlin.Any
    ↳android.view.View
        ↳android.view.ViewGroup
            ↳android.widget.AbsoluteLayout
                ↳android.webkit.WebView
```

## manifests 설정

AndroidManifest.xml

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET"/>
    <application
        ...
        android:usesCleartextTraffic="true"
        android:theme="@style/Theme.AppCompat.Light.NoActionBar"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                ...
            </intent-filter>
            ...
        </activity>
    </application>
</manifest>
```

- `<uses-permission android:name="android.permission.INTERNET"/>`은 해당 어플리케이션에 인터넷 사용권한을 지정하는 구문이다. 사용하지 않을 시 `ERR_CACHE_MISS`와 같은 오류가 발생한다.

- `android:usesCleartextTraffic="true"`은 HTTP와 같은 네트워크 트래픽을 사용하는지 여부를 나타낸다. HTTPS에서는 DownloadManager, MediaPlayer와 같은 플랫폼 구성요소와 간섭되는 경우에 `ERR_CLEARTEXT_NOT_PERMITTED`오류가 발생할 수도 있다.

## layout xml 파일

activity_main.xml

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <WebView
        android:id="@+id/webView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginStart="0dp"
        android:layout_marginTop="0dp"
        android:layout_marginEnd="0dp"
        android:layout_marginBottom="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</androidx.constraintlayout.widget.ConstraintLayout>
```

- 웹 뷰에서 네비게이션 뷰 같은 부가 요소들을 보여주기 위해서 따로 margin 등의 크기를 조절하여 공간을 조절할 수 있다.

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

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.loadUrl("https://www.naver.com")
    }
```

- `binding.webView.settings.javaScriptEnabled = true`: 웹뷰에서 자바스크립트 활성화 여부를 지정한다.
- `binding.webView.webViewClient = WebViewClient()`: 웹뷰 사용 시 클라이언트를 사용할 수 있도록 할당한다.
- `binding.webView.webChromeClient = WebChromeClient()`: 웹뷰 사용 시 크롬 클라이언트를 사용할 수 있도록 할당한다.
- `binding.webView.loadUrl("https://www.naver.com")`: 해당 URL을 Load하도록 지정한다.

```
    override fun onBackPressed() {
        if(binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
```

- 웹뷰 상에서 뒤로가는 상호작용을 취했을 때 뒤로 갈 수 있는지 확인한 다음 뷰가 종료되지 않고 이전 화면으로 이동할 수 있게 한다.

---

참조
[구글 공식문서](https://developer.android.com/reference/kotlin/android/webkit/WebView),
[홍드로이드](https://www.youtube.com/watch?v=z0ha16-oz7I&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=8)
