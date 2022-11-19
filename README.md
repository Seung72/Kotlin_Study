# Android-Kotlin-Study

안드로이드의 코틀린을 공부하기 위하여 정리한 문서입니다.

# 1. Kotlin이란?

안드로이드 및 웹 개발에서 Java를 대체할 목적으로 JetBrain에서 개발한 언어이다.

### 특징은?

- Java의 몇몇 약점들을 개선하면서 기존 Java 가상머신과 호환이 될 수 있도록 개발됨
- Javascript 및 Swift와의 연동 개발도 가능
- Null 처리에 대해 기본적인 안정성을 지니고 있다
- 람다식의 문법 사용, 세미콜론 생략

# 2. 기본 문법

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

## Top Level 상수 Const

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
muItems.remover(3)
```

### Array

```
val items = arrayOf(1,2,3)
items[0] = 10
items.set(0, 10)
```
