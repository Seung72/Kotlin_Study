# 2. ViewBinding

## ViewBinding을 사용하는 이유?

- **Null Safe**: *findViewById*와는 다르게 뷰를 직접 참조하지 않으므로 Null상태로 강제로 할당되었을 때 나타나는 *NullPointerException*을 방지할 수 있다.

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
