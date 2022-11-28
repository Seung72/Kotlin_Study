# 2. ViewBinding

ViewBinding 기능 사용 시 뷰와 상호작용하는 코드를 쉽게 작성할 수 있다.
모듈에서 사용 설정된 ViewBinding은 각 XML 레이아웃 파일의 바인딩 클래스를 생성한다.
바인딩 클래스의 인스턴스에는 상응하는 레이아웃에 ID가 있는 모든 뷰의 직접 참조가 포함된다.

### vs findViewById

- **Null Safe**: *findViewById*와는 다르게 뷰의 직접 참조를 생성하기 때문에 *NullPointerException*을 방지할 수 있다.

- **Type safety**: 각 바인딩 클래스에 있는 필드 유형이 XML 파일에서 참조하는 뷰와 일치한다. 즉, 클래스 변환 예외가 발생할 위험이 없다.

- **코드의 단순화**: XML 레이아웃마다 바인딩 클래스를 자동으로 생성하기 때문에 코드를 단순화할 수 있다.

## ViewBinding 활성화

build.gradle(Module: 앱이름) 파일에서 작성

```
android {
    ...
    buildTypes {
        buildFeatures {
            viewBinding true // 뷰 바인딩 사용
        }
        ...
    }
}
```

위와 같이 선언하고 이후 Sync Gradle 진행

## 일반적 사용

```
class MainActivity : AppCompatActivity() {

    //ActivityXXXBinding 규칙으로 객체를 불러와 전역 변수 선언
    private var mBinding: ActivityMainBinding? = null

    //Null 체크 사용하지 않고 바로 할당과 재 선언
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        //root 메서드를 이용하여 레이아웃의 최상위 위치 뷰(constraint등)의
        //인스턴스를 활용하여 생성된 뷰를 액티비티에 표시
        setContentView(binding.root)

        //바인딩 변수를 선언하여 레이아웃의 뷰를 사용할 수 있다.
        binding.tvTitle.text = "안녕하세요!"
    }

    override fun onDestroy() {

        // 액티비티가 종료될 때 binding class 인스턴스 참조를 정리
        mBinding = null
        super.onDestroy()
    }
}
```

## Fragment에서의 사용

```
class MainFragment : Fragment() {

    //액티이비와 같은 방식으로 선언한다.
    private var mBinding: FragmentMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //위와 다르게 layoutInflater를 사용하지 않고 inflater 인자를 가져온다
        mBinding = FragementMainBinding.inflate(inflater, container, false)
        binding.tvTitle.text = "프레그먼트1"
        return binding.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

```

---

참조
[구글 공식문서](https://developer.android.com/topic/libraries/view-binding)
