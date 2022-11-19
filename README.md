# Android-Kotlin-Study

안드로이드의 코틀린을 공부하기 위하여 정리한 문서입니다.

# 1. [Kotlin](https://kotlinlang.org/)이란?

안드로이드 및 웹 개발에서 Java를 대체할 목적으로 JetBrain에서 개발한 언어이다.
[![Logo](https://user-images.githubusercontent.com/79628706/202858867-8c5e189f-07f6-43a4-8003-529fd0b1a758.png)](https://github.com/JetBrains/kotlin)

### 특징은?

- Java의 몇몇 약점들을 개선하면서 기존 Java 가상머신과 호환이 될 수 있도록 개발되었다.
- Javascript 및 Swift와의 연동 개발도 가능하다.
- Null 처리에 대해 기본적인 안정성을 지니고 있다.
- 람다식의 문법을 사용하고 세미콜론을 생략한다.

# 2. 문법

Kotlin은 자료형을 추론하는 기능을 갖고 있기 때문에 명시적으로 자료형을 선언할 필요가 없다.

## 변수 선언

Kotlin에서는 var이라는 형식을 사용하여 변수를 선언할 수 있다.

```
var i = 10
var name = "승철"
var point = 3.3
```

위와 같은 형식으로 선언하며

```
var i : Int = 10
var name : String = "승철"
var point : Double = 3.3
```

위와 같은 형식으로 명시적인 선언도 가능하다.

## 상수 선언

Java에서의 final과 같은 역할을 수행한다.
상수는 val로 선언할 수 있다.

```
val num = 20
```

## Top Level 상수: Const

Java에서는 클래스를 생성하고 메인함수를 통해 사용한다. Kotlin은 클래스 생성을 필요로 하지 않는다.

```
const val num = 20
...
fun main() {...}
```

메인 바깥에서 위와 같은 형식으로 선언하여 컴파일타임 상수로 선언이 가능하다.
컴파잍타임 상수를 사용하게 되면 기본적으로 성능에서 유리한 점이 있다

## 형변환

```
var i = 10
var l = 20L // Long 타입으로 선언
l = i.toLong()
```

```
var name = "10"
var i
i = name.toInt()
```

위와 같은 방식으로 형변환을 할 수 있다.

## String 사용

```
 var name = "승철"
 print(name + "입니다.")
 print("$name 입니다.")
 print("${name + 2} 입니다.")
```

위와 같이 여러 형식으로 문자열을 표기할 수 있다.

## max와 min의 사용

```
 var i = 10
 var j = 20
 print(kotlin.math.max(i,j))
```

## random

```
 val randomNumber = Random
 .nextInt()
 .nextInt(0, 100)
 .nextDouble(0.0, 1.0)
```

## 입력을 받는 경우

```
 val reader = Scanner(System.'in')
 reader.nextInt()
 reader.next
```

Kotlin에서는 in이라는 키워드를 지원하지 않기 때문에 ''를 사용하여 감싸준다.

## 조건문

#### if문

```
var i = 15
if (i > 10) {
    print("i는 10보다 큽니다")
} else if (i > 20) {
    print("i는 20보다 큽니다")
} else {
    print("i는 10과 같거나 작습니다")
}
```

#### when문

자바의 조건문들과 유사하지만 더 강력한 조건들을 지정할 수 있다.

```
var i = 15
when {
    i > 10 -> {
        print("i는 10보다 큽니다")
    }
    i > 20 -> {
        print("i는 20보다 큽니다")
    }
    else -> {
        print("i는 10과 같거나 작습니다")
    }
}
```

위의 when문은 if문과 상호 변경이 가능한 형태

#### 그 외의 경우

- 조건문을 변수에 담아서 사용하는 경우

```
var i = 15
var result = when {
    i > 10 -> {
        "10보다 큼"
    }
    i > 5 -> {
        "5보다 큼"
    }
    else -> {
        "그 외의 경우"
    }
}
print(result)
```

조건문에 가까운 형태이지만 Java에서는 구동되지 않는 계산식 형식의 소스이다.

- 삼항 연산

```
val result = if (i > 10) true else false
```

## 반복문

#### for문

```
val items = listOf(1,2,3,4,5)

for(item in items) {
    print(item)
}
```

Java에서의 .forEach와 상호 변경이 가능하다.

```
for (i in 0..items.size - 1) {
    print(items[i])
}
```

위와 같은 형식으로도 사용 가능하나 코드의 가독성 자체가 떨어질 수 있다.

#### while

Java의 그것과 아예 같다고 보면 되겠다.

## List

```
val immuItems = listOf(1,2,3,4,5) //변경이 불가능한 형태의 리스트
val muItems = mutableListOf(1,2,3,4,5) //변경이 가능한 형태의 리스트

muItems.add(6)
muItems.remove(3)
```

## Array

```
val items = arrayOf(1,2,3)
items[0] = 10
items.set(0, 10)
```

## 예외처리

```
try {
    val item = itmes[4]
} catch (e: Exception) {
    print(e.message)
}
```

## Null Safty

```
var age = null //기본적인 Null 초기화
var name: String? = null //자료형을 명시한 형태의 Null 초기화
```

특정한 자료형 변수에 Null을 넣기 위해서 자료형 뒤에 ? 삽입

```
var name2: String = ""
name2 = name //오류발생
```

String?과 String은 서로 다른 자료형 취급이므로 오류가 발생한다.
따라서 아래와 같은 방법들로 바꾼 구문으로 사용해야한다.

1. if문을 사용한 Null 체크

```
if (name != null) {
    name2 = name
}
```

2. 강제적인 Null 제거

```
name2 = name!! // name이 Null값이 아니라고 단정 짓는 방법, 추천되는 방법은 아니다.
```

3. let 함수를 사용한 Null 체크

```
name?.let {
    name2 = name
}
```

## 함수

Kotlin에서 함수는 fun이라는 키워드를 이용하여 선언한다.

```
fun main() {
    print(sum(1, 2))
    print(sum(a = 3, c = 4, b = 5)) //입력 파라미터를 명시적으로 전달할 경우 순서가 달라도 사용 가능
    print(sub(6, 7))
}

fun sum(a: Int, b: Int, c: Int = 0) = a + b + c

fun sub(a: Int, b: Int) : Int {
    when a >= b -> {
        return a - b
    } else {
        return b - a
}
```

위처럼 sum 함수의 c: Int 기본 값을 정해놓은 경우엔 인자 2개만 전달해줘도 함수를 사용할 수 있다.

- Java에서처럼 메소드 오버로드를 사용하지 않아도 된다는 장점이 있다.

## Class

Java 문법처럼 Class 블록 안에 기본 생성자를 생성하지 않아도 된다. (Java처럼 사용도 가능하다)

```
fun main() {
    val seung = Person("SeungCheol", 23, "컴퓨터공학")
    val seung2 = Person("SeungCheol", 21, "관광컨벤션학")

    print(seung.name + seung.age)
    print(seung == seung2) // false, 객체의 Hash를 이용하여 비교하기 때문에 false 출력.
    print

    seung.age = 24
    seung.major = "컴퓨터정보공학" //오류발생, private로 된 값은 Getter 사용 불가


}
class Person(val name: String, var age: Int, private val major: String)
```

#### Data Class

위 예시와 달리 Hash를 이용한 비교가 아닌 값 자체의 비교를 사용하고 싶을 때 사용한다.

```
data class Person(val name: String, var age: Int, private val major: String)
```

#### 사용 예시

```
fun main() {
    val seung = BirthDay("승철", 1999, 6, 16)
    println(seung.cake)
    seung.cakeChange()
    println(seung.cake)
}
class BirthDay(val name: String, var year: Int, var month: Int, var day: Int) {
    var cake = "생크림 케이크"
        private set
        get() = " 기본 케이크 : $field"

    init{
        println("새로운 생일이 생성되었습니다.")
    }

    fun cakeChange() {
        cake = "초코 케이크"
    }
}
```

- init: Class가 새로 생성될 때마다 어떠한 기능을 넣고 싶을 때
- private set: 외부에서 set을 사용하고 싶지 않은 경우 (Setter 기능 제한)
- get() = "\$field": 외부에 값을 보내고 싶은 경우, 꼭 "$field"라는 이름으로 사용해야한다.(Getter 기능)
