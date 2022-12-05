# 8. NavigationView

`NavigationView`는 보통`DrawerLayout`안에 배치되며, Application에서 사용하는 표준 탐색 메뉴다. 메뉴의 내용은 메뉴 리소스 파일을 생성하여 사용할 수 있다.

```
java.lang.Object
   ↳android.view.View
 	   ↳android.view.ViewGroup
 	 	   ↳android.widget.FrameLayout
 	 	 	   ↳com.google.android.material.navigation.NavigationView
```

### bulid.gradle(:app) 설정

```
dependencies {
    ...
    implementation 'com.google.android.material:material:1.7.0'
    ...
}
```

위와 같이 입력해주어야 사용할 수 있다.

### Menu 파일 생성

##### 1. 폴더 생성

Resource type이 menu인 폴더 생성

##### 2. 메뉴 아이콘으로 사용할 이미지 Drawable에 추가

나는 Vector Asset을 통해 이미지를 불러왔다.

##### 3. 메뉴 xml 파일 생성

navi_menu.xml

```
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <group android:checkableBehavior="single">
        <item android:id="@+id/notification"
            android:icon="@drawable/ic_baseline_notifications_24"
            android:title="알림"/>
        <item android:id="@+id/share"
            android:icon="@drawable/ic_baseline_share_24"
            android:title="공유"/>
        <item android:id="@+id/settings"
            android:icon="@drawable/ic_baseline_settings_24"
            android:title="설정"/>
    </group>
</menu>
```

## layout xml 파일

activity_main.xml

```
<androidx.drawerlayout.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/layout_drawer"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ImageView
            android:id="@+id/btn_navi"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_marginStart="8dp"
            android:layout_marginTop="8dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:srcCompat="@drawable/ic_baseline_menu_24" />
    </androidx.constraintlayout.widget.ConstraintLayout>

    <com.google.android.material.navigation.NavigationView
        android:id="@+id/naviView"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        app:menu="@menu/navi_menu"/>

</androidx.drawerlayout.widget.DrawerLayout>
```

일반적으로 `NavigationView`는 `Drawerlayout`안 생성하여 동작하게 한다.
이후에 `constraintlayout`을 생성하고 `ImageView`나 `Button`등의 요소를 이용하여 메뉴로 사용할 버튼 생성한다. `material.navigation.NavigationView`를 이용하여 네비게이션 뷰를 생성하고 기본적으로 숨기기 위해 `layout_gravity="start"`로 지정하여 xml상의 안보이는 영역으로 이동시킨다.

## Activity

MainActivity.kt

```
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnNavi.setOnClickListener{
            binding.layoutDrawer.openDrawer(GravityCompat.START)
        }

        binding.naviView.setNavigationItemSelectedListener(this)
    }
```

`btnNavi`에 `setOnClickListener`를 지정하여 클릭 시 `layoutDrawer`가 `openDrawer`할 수 있도록 한다. 여기서 `GravityCompat.START`를 사용하여 왼쪽에서 오른쪽으로 요소가 이동할 수 있게 지정(왼쪽에 요소가 있다고 지정하고 open 기능이 가운데로 이동하게 구현되는 것 같다)한다. 오른쪽에서 메뉴가 나오게 하고 싶다면 xml과 해당 부분에서 `END`로 변경해주어야 한다.

```
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId)
        {
            R.id.notification -> Toast.makeText(applicationContext, "알림", Toast.LENGTH_SHORT).show()
            R.id.share -> Toast.makeText(applicationContext, "공유", Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(applicationContext, "설정", Toast.LENGTH_SHORT).show()
        }
        binding.layoutDrawer.closeDrawers()
        return true
    }
```

메뉴 안의 `item`들이 클릭 됐을 때의 상호작용 요소를 지정해준다. `closeDrawers()`를 설정해주지 않으면 메뉴 개체가 선택됐을 때 메뉴가 닫히지 않는다. 또한 반환을 요구하기 때문에 `return true`까지 작성해준다.

```
    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)){
            binding.layoutDrawer.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }
}
```

뒤로가기 버튼을 눌렀을 때의 행동을 지정할 수 있으며, `isDrawerOpen()`을 이용해 `GravityCompat.START`의 요소가 열려있는 상태인지 확인하고 열려 있을 경우 `closeDrawer()`가 작동되게 구현한다.

---

참조
[구글 공식문서](https://developer.android.com/reference/com/google/android/material/navigation/NavigationView?hl=en),
[홍드로이드](https://www.youtube.com/watch?v=ALTFLXKiPUY&list=PLC51MBz7PMywN2GJ53aF0UO5fnHGjW35a&index=6)
