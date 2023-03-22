# Blog Search Service

## Requirements
- Java17

## Jar 파일 위치
[deploy/search-blog-api-1.0.0.jar](/deploy/blog-search-api-1.0.0.jar)

## 실행 방법
```
java -jar deploy/blog-search-api-1.0.0.jar
```

## 빌드 방법
```
./gradlew clean build
```

결과 Jar 파일은 `blog-search-api/build/libs` 디렉토리에 생성됩니다.

## API 스펙
### 1. 블로그 검색
```
GET /v1/blog/documents/search
```

#### Request - Query Parameters
| Name  | Type    | Description                                                | Required |
|-------|---------|------------------------------------------------------------|----------|
| query | String  | 검색을 원하는 질의어                                                | O        |
| page  | Integer | 결과 페이지 번호, 1~50 사이의 값, 기본 값 1                              | X        |
| size  | Integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10                       | X        |
| sort  | String  | 결과 문서 정렬 방식, ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 ACCURACY | X        |

#### Response
| Name                       | Type | Description                                         |
|----------------------------|------|-----------------------------------------------------|
| header.isSuccessful        | Boolean | 요청 성공 여부                                            |
| header.resultCode          | Integer | 요청 실패 코드, 성공시 0, 보다 자세한 사항은 하단의 `Result Code` 항목 참고 |
| header.resultMessage       | String | 요청 실패 메시지, 성공시 공백("")                               |
| documents.contents[].title | String | 블로그 글 제목                                            |
| documents.contents[].contents | String | 블로그 글 내용                                            |
| documents.contents[].url | String | 블로그 글 URL                                           |
| documents.contents[].blogName | String | 블로그 이름                                              |
| documents.contents[].datetime | Datetime | 블로그 글 작성 시간(yyyy-MM-ddTHH:mm:ssZ), UTC              | 
| documents.page | Integer | 검색된 페이지 번호                                          |
| documents.size | Integer | 검색된 문서 수                                            | 
| docuemnts.sort | String | 검색에 사용된 정렬 방식                                       |
| documents.totalCount | Long | 검색된 전체 문서 수                                         |

#### Sample
##### Request
```
curl -v -X GET "http://localhost:8080/v1/blog/documents/search?query=hello&page=1&size=5&sort=RECENCY" 
```

##### Response
```json
{
  "header": {
    "isSuccessful": true,
    "resultCode": 0,
    "resultMessage": ""
  },
  "documents": {
    "contents": [
      {
        "title": "[항해99 - 마지막] 벌써 수료라니",
        "contents": "그리고 앞으로도 살아가면서 수많은 시작과 끝을 계속해서 맞이하고 보내게 될 것이다. 매 순간의 끝에서 아쉬움이 남지 않도록 새로운 시작을, 최선을 다해 맞이하고 최선을 다해 보내야겠다는 이 다짐이 지속되기를 바라면서 모두의 새로운 시작을 응원하며 항해99의 마지막 회고록을 마친다. 또, 가보자고~! <b>Hello</b>...",
        "url": "http://iotrue.tistory.com/81",
        "blogName": "this.blog = Level ++",
        "datetime": "2023-03-22T12:09:55Z"
      },
      {
        "title": "app.config에서 서비스 주입",
        "contents": "base.factory(&#39;Foo&#39;, function() { console.log(&#34;Foo&#34;); var Foo = function(name) { this.name = name; }; Foo.prototype.<b>hello</b> = function() { return &#34;<b>Hello</b> from factory instance &#34; + this.name; } return Foo; }) base.service(&#39;serviceFoo&#39;, function() { this.<b>hello</b> = function() { return &#34;Service says <b>hello</b>...",
        "url": "http://goodsource.tistory.com/968",
        "blogName": "goodsource",
        "datetime": "2023-03-22T12:09:52Z"
      },
      {
        "title": "jQuery AJAX 요청이 IE에서 실패함",
        "contents": "적절하게 설정해야 합니다. 정적으로 생성된 JSON 및 IE의 주요 문제 중 하나는 대표적인 &#34;Commas&#34;입니다. 예를 들어 IE에서 오류가 발생합니다. { &#34;one&#34;:&#34;<b>hello</b>&#34;, &#34;two&#34;:&#34;hi&#34;, } 마지막 쉼표를 적어 둡니다. {{SITE_URL}} 청크 증여는 어떤 내용입니까?브라우저의 소스코드 뷰에서 코드를 확인해 보세요.{{SITE _URL...",
        "url": "http://goodsource.tistory.com/967",
        "blogName": "goodsource",
        "datetime": "2023-03-22T12:09:40Z"
      },
      {
        "title": "TIL) 2주차 3일",
        "contents": "인자에 디폴트 값을 지정해주면 된다. (name = &#34;디폴트&#34;) __name__==&#39;__main__&#39;란? 파이썬 내의 내장변수 혹은 글로벌 변수 __name__에는 모듈의 이름(ex: <b>hello</b>.py)을 담는다. but 그 파일 안에서 함수를 실행하면 __main__이라는 값이 담기게되고, 새로운 파일(ex: prac.py)에서 모듈을 import해서 가지고 오는 경우엔...",
        "url": "http://hanilcome.tistory.com/9",
        "blogName": "개발 일지",
        "datetime": "2023-03-22T12:07:59Z"
      },
      {
        "title": "웹개발 종합반 3주차 개발일지",
        "contents": "이해할수 있는데 Python 문법을 100101 변환할수있게 만드는 프로그램이다. -Sparta 안에 pythonprac 폴더 만든후 vs code studio 실행 →그리고 새파일에서 <b>hello</b>.py 파일 만든후 터미널 실행.→기본프로필 편집에서 Git bash 로 바꾸고 새 터미널 실행→ print(&#39;hi&#39;) 출력 - Python은 변수 선언 할때에도 let a =2...",
        "url": "http://kms1561.tistory.com/3",
        "blogName": "JUNG'S CODING",
        "datetime": "2023-03-22T12:02:32Z"
      }
    ],
    "page": 1,
    "size": 5,
    "sort": "recency",
    "totalCount": 931174
  }
}
```

### 2. 인기 검색어 목록
```
GET /v1/keywords/top10
```

#### Request - Query Parameters

#### Response
| Name                       | Type | Description           |
|----------------------------|------|-----------------------|
| header.isSuccessful        | Boolean | 요청 성공 여부              |
| header.resultCode          | Integer | 요청 실패 코드, 성공시 0, 보다 자세한 사항은 하단의 `Result Code` 항목 참고     |
| header.resultMessage       | String | 요청 실패 메시지, 성공시 공백("") |
| top10[].name | String | 검색된 키워드 이름            |
| top10[].count | Integer | 키워드가 검색된 수            |

#### Sample
##### Request
```
curl -v -X GET "http://localhost:8080/v1/keywords/top10"
```

##### Response
```json
{
  "header": {
    "isSuccessful": true,
    "resultCode": 0,
    "resultMessage": ""
  },
  "top10": [ 
    {
      "name": "hello",
      "count": 5
    },
    {
      "name": "world",
      "count": 3
    }
  ]
}
```

### Result Code
| HTTP Status Code | Code | Description |
|----|---|-------------|
| 200 | 0 | 성공          | 
| 400 | -3 | 잘못된 요청 파라미터 |
| 500 | -1 | 검색 요청 실패    | 
| 500 | -2 | 검색 응답 읽기 실패 |
| 500 | -9999 | API 서버 에러 | 

## 참고 사항
- 패키지 구조는 [Package-by-feature](http://www.javapractices.com/topic/TopicAction.do?Id=205) 구조를 따릅니다.
- Gradle buildSrc 방식 멀티 모듈 구성
  - `blog-search-service` 루트 프로젝트
    - `buildSrc`: Gradle 의존성을 정리한 스크립트 관리(`buildSrc/src/main/groovy` 디렉토리 참고)
      - `io.supercheetos.java.gradle`: 일반적인 Java 빌드 스크립트
      - `io.supercheetos.spring.gradle`: 범용 Spring 의존성 빌드 스크립트
      - `io.supercheetos.spring-app.gradle`: Spring WebMVC 같은 애플리케이션 개발에 필요한 의존성 빌드 스크립트
    - `model`: 다른 곳에서 공유해서 사용할 수 있는 모델 클래스(DTO 등)을 담당하는 프로젝트
    - `blog-search-api`: 실제 API 서버를 구현한 프로젝트, model 프로젝트에 의존
- 테스트 코드는 `@SpringBootTest`와 `JSONAssert` 기능을 주로 사용했습니다.
- Circuit Breaker를 사용하여 Kakao 블로그 검색에 실패할 경우 Naver 블로그 검색을 시도하도록 구현하였습니다. 
- 검색된 키워드가 많더라도 빠르게 Top10을 조회할 수 있도록 키워드의 count 필드에 내림차순 인덱스를 적용했습니다.
- 키워드 별로 검색된 횟수의 정확도를 높이기 위해 로직에 비관적 락을 적용하였습니다. 
- spring validation을 통해서 파라미터, 설정값 검증합니다.
- `example` 디렉토리에는 간단한 예제 코드([Intellj HTTP Client](https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html#converting-curl-requests))가 있습니다.
- 더 개선한다면 Kafka를 사용해도 좋을 것 같습니다.