## Bean Scope 학습 
### singleton Scope
+ 싱글톤 빈의 공유 인스턴스는 하나만이 관리됩니다. 특정 Bean을 요청할 경우 Spring container에서 `하나의 특정 인스턴스`로 반환합니다.
>Bean Scope가 singleton으로 지정이 된다면 단 하나만의 인스턴스가 생성됩니다.이 단일 인스턴스는 캐쉬로 저장이 되고 후속 요청에서는 캐시된 객체를 반환합니다.

![img.png](img.png)

Spring에서의 `Singleton Bean`과 GoF 싱글톤 패턴은 개념적으로 다릅니다. 쉽게 말하면, 두 가지 방식의 차이는 `객체의 생성 범위`와 `생성 방법`에 있습니다.

#### GoF 싱글톤 패턴
+ 하나의 클래스에 대해 정확히 하나의 인스턴스만 생성되도록 강제합니다.
이 클래스의 인스턴스는 `전역적`으로 하나만 존재하며, 여러 번 호출되더라도 같은 인스턴스가 반환됩니다.
즉, `클래스 로더` 단위에서 하나의 인스턴스를 공유하는 방식입니다.


#### Spring의 싱글톤 빈
+ `Spring 컨테이너 `내에서 하나의 빈에 대해 하나의 인스턴스만 존재하도록 보장합니다. 즉, Spring 컨테이너를 기준으로 하나의 인스턴스만 생성됩니다.
빈을 정의할 때 Spring에서 관리하며, Spring 컨테이너가 빈의 라이프사이클을 관리합니다.


#### 결론
+ GoF 싱글톤은 클래스 레벨에서 하나의 인스턴스만 존재하도록 강제하는 패턴이고, Spring 싱글톤은 Spring 컨테이너 내에서 하나의 빈에 대해 하나의 인스턴스만 존재하도록 보장합니다.

### prototype Scope
+ 새로운 요청이 올 때마다 새로운 인스턴스를 생성합니다. 일반적으로 Statefull Bean에는 prototype Scope을 사용해야하고, Stateless Bean에는 Singleton Scope를 사용해야 합니다.


![img_1.png](img_1.png)
#### prototype의 lifecycle
+ prototype은 다른 빈과 다르게 spring에서 lifecycle을 관리하지 않습니다. spring container는 prototype 객체를 인스턴스화하고 설정한 후 이를 클라이언트에게 전달하며, 이후 해당 인스턴스에 대한 기록을 유지하지 않습니다. 따라서 prototype의 경우에는 설정된 소멸 콜백 메소드가 호출되지 않습니다. 클라이언트 코드가 프로토타입 스코프 객체를 정리하고, 프로토타입 빈이 점유한 자원을 해제해야 합니다
+ Spring 컨테이너가 프로토타입 스코프 빈의 자원을 해제하도록 하려면, 해제해야 할 빈을 참조하는 커스텀 빈 post-processor를 사용하는 방법이 있습니다.

> 어떤 측면에서는  prototype-scoped bean이 java의 new 연산자를 대체합니다. 해당 시점 이후 모든 lifecycle의 관리는 클라이언트에서 처리해야 합니다.


### request Scope
모든 HttpRequest에 대해 새로운 Bean을 생성합니다. 이렇게 생성된 빈은 원하는 만큼 내부 상태를 변경해도 됩니다. 


#### 예제 코드

[코드로 바로 이동](/Users/hyungjunpark/IdeaProjects/BeanStudy/src/main/java/com/example/beanstudy/controller/request/RequestController.java)
```java
@RestController
@RequestMapping("/request")
@Scope(value = "request")
@Slf4j
public class RequestController {


    Thread thread = Thread.currentThread();
    private String test = "test1";

    @GetMapping("/test1")
    private String test1() throws InterruptedException {
        log.info("current thread name: {}, test1 method {}",thread.getName(), test);
        test = "test2";
        changeValue();
        return "requestScope Test!!";
    }


    private void changeValue() throws InterruptedException {
        Thread.sleep(500000);
        log.info("currentThreadName{}, changeValue test: {}", thread.getName() ,test);
    }
}
```
#### 기능 설명
+ http 요청이 올 경우 RequestController 내부의 상태를 변경합니다. 그리고 Thread.sleep을 시키고 다른 HTTP 요청을 보내서 변경된 상태를 공유하지 않는 것을 확인합니다.

### session Scope
+ 나의 HTTP 세션 동안에는 Bean의 내부 상태를 자유롭게 변경할 수 있습니다. 이렇게 변경한 상태는 해당 세션에만 적용되며, 다른 HTTP 세션에서 동일한 Bean 정의를 통해 생성된 인스턴스에는 영향을 미치지 않습니다. 즉, 각 HTTP 세션마다 독립적인 상태를 유지하게 됩니다.
+ 세션이 만료되거나 폐기되면 해당 세션에 종속된 인스턴스도 함께 폐기됩니다.

[코드로 바로 이동](/Users/hyungjunpark/IdeaProjects/BeanStudy/src/main/java/com/example/beanstudy/controller/session/SessionController.java)
```java
@RestController
@RequestMapping("/session")
@Scope(value = "session")
@Slf4j
public class SessionController {
    private String test = "origin";


    @GetMapping("/test1")
    public String test(HttpServletRequest request) {
        log.info("session id: {}, test {}", request.getSession().getId(), test);

        test = "change";
        return "Session scope";
    }
}
```

#### 기능 설명
+ 같은 세션일 경우 맨처음 origin을 log로 찍고 그 후로는 change를 출력합니다.
+  다른 브라우저를 열고 get 요청을 보내면 확인이 가능합니다. 

### application Scope
+ ServletContext당 싱글톤으로 생성한다.


[코드로 바로 이동](/Users/hyungjunpark/IdeaProjects/BeanStudy/src/main/java/com/example/beanstudy/controller/applicaton/ApplicationController.java)
```java
@RestController
@RequestMapping("/application")
@Scope(value = "application")
@Slf4j
public class ApplicationController {


    private final String key = "key";
    private String currentValue = "startValue";

    @GetMapping("/{newName}")
    public String change(HttpServletRequest request, @PathVariable(value = "newName") String newName) {
        log.info("current test value {}", currentValue);
        request.getServletContext().setAttribute(key, newName);
        currentValue =  (String) request.getServletContext().getAttribute(key);
        log.info("test {}", currentValue);
        return currentValue;
    }


    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        return (String) request.getServletContext().getAttribute(key);
    }
}

```

#### 기능 설명
+ servletContext의 Attribute값을 설정하고 이 값을 공유하는지 확인하는 코드입니다.


### webSocket
WebSocket 범위는 WebSocket 세션의 수명 주기와 연결되어 있으며 WebSocket 애플리케이션을 통한 STOMP에 적용됩니다

### 출처
[spring-docs/scope](https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html)