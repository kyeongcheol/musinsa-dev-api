# 무신사 상품 카테고리 API 개발 과제

- 작성일 : 2022.08.23
- 작성자 : 양경철, ykc90831@gmail.com
- 내용 : 무신사 백엔드 엔지니어 / 상품 카테고리 API 개발 과제 테스트

## 0. Spec

- Java 11
- Spring Boot JPA
- H2 Embedded
- Gradle 7.4.1


## 1. Install/Build (Centos7 기준)

### install
- JDK

 
  ```bash
  
  // 설치 가능한 JDK 버전 확인
  yum list java*openjdk*
  
  // JDK 설치
  yum install -y java-11-openjdk-devel.x86_64
	
  // JDK 설치 확인
  yum list installed | grep openjdk
  ```

- git


  ```bash
  
  // Git 설치
  yum -y install git
  
  // Git Clone
  git clone https://github.com/kyeongcheol/musinsa-dev-api.git
  ```

### build


  ```bash
	
  // Git Clone 받은 경로에서 진행
  cd /root/musinsa-dev-api
  chmod +x gradlew
  ./gradlew build
  
  // Build 파일 경로 이동
  cd build/libs
  
  // Build 파일 확인
  musinsa-dev-api-0.0.1-SNAPSHOT.jar
  ```

### execute
  
  ```bash
  
  // Git Clone 받은 경로에서 진행
  cd /root/musinsa-dev-api/build/libs
  java -jar musinsa-dev-api-0.0.1-SNAPSHOT.jar
  ```

## 2. API Doc

| 기능               | API                                             | 비고                                                       |
| ------------------ | ----------------------------------------------- | ---------------------------------------------------------- |
| 상품 카테고리 등록      | POST /api/product/category                                |                                                            |
| 상품 카테고리 수정      | PATCH /api/product/category/{id}                          |                                                            |
| 상품 카테고리 삭제      | DELETE /api/product/category/{id} 			|  |
| 전체 상품 카테고리 조회 | GET /api/product/category                                 |                                                            |
| 특정 상품 카테고리 조회 | GET /api/product/category/{id}                            | 해당 상품 카테고리의 하위 상품 카테고리 전체 조회 가능             |


### 상품 카테고리 등록

- Method : POST
- URL : http://localhost:8080/api/product/category
- request body
- 
  ```json
  {
    "name": "testCategory",
    "rootId": 1
  }
  ```
- response body

  ```json
  {
    "allProductCategoryRes": [
       {
        "id": 105,
        "name": "testCategory"
       }
    ]
  }
  ```
![image](https://user-images.githubusercontent.com/29423360/186091076-fd5756c5-1e07-466d-b193-b45cf10819a6.png)

![image](https://user-images.githubusercontent.com/29423360/186091135-efca81f6-16bb-4594-b635-1e1b6a6920f8.png)


### 상품 카테고리 수정

- Method : PATCH
- URL : http://localhost:8080/api/product/category/{id}
- request body

  ```json
  {
    "id": 105,
    "name": "testCategory1"
  }
  ```
  
- response body
  ```json
  {
    "id": 105,
    "name": "testCategory1"
  }
  ```

![image](https://user-images.githubusercontent.com/29423360/186091320-7b94ff22-9bff-490c-a26a-c4b2dcd9dcd0.png)

![image](https://user-images.githubusercontent.com/29423360/186091389-99469f49-bb8d-4ce8-886a-4018202096e8.png)


### 상품 카테고리 삭제

- Method : DELETE
- URL : http://localhost:8080/api/product/category/{id}
- request body

  ```json
  {
    "id": 105
  }
  ```
  
- response body
  ```json
  {
    "id": 105
  }
  ```
  
  ![image](https://user-images.githubusercontent.com/29423360/186090885-16598c71-f930-48f1-b4e0-38a9db01b211.png)


### 전체 상품 카테고리 조회

- Method : GET
- URL : http://localhost:8080/api/product/category
- response body

  ```json
  {
    "allProductCategoryRes": [
      {
        "id": 1,
        "name": "상의"
      },
      {
        "id": 2,
        "name": "아우터"
      },
     ...
    
      {
        "id": 101,
        "name": "스포츠가방"
      },
      {
        "id": 102,
        "name": "스포츠잡화"
      },
      {
        "id": 103,
        "name": "스포츠모자"
      },
      {
        "id": 104,
        "name": "캠핑용품"
      }
    ]
  }

  ```

![image](https://user-images.githubusercontent.com/29423360/186091610-b6df53b1-d5e6-43b4-9fe1-9c440e5f4ce1.png)

![image](https://user-images.githubusercontent.com/29423360/186091820-9f1f3101-9b5d-42ad-b187-9c44437b7df4.png)


### 특정 상품 카테고리 조회

- Method : GET
- URL : http://localhost:8080/api/product/category/{id}
- request body

 ```json
  {
    "id": 1  
  }
  ```

- response body

  ```json
  {
    "allProductCategoryRes": [
    {
      "id": 11,
      "name": "반소매 티셔츠"
    },
    {
      "id": 12,
      "name": "피케/카라 티셔츠"
    },
    ...
    ]
  }  
  ```
  
![image](https://user-images.githubusercontent.com/29423360/186091906-e1438e41-9f84-4881-8187-5dbd7145ca07.png)

![image](https://user-images.githubusercontent.com/29423360/186091943-e1dc9af5-30fa-4fd1-8029-c887c4d275a3.png)


## 4. 테스트 방법

### Junit Test
- Controller Test : src/test/java/com/musinsa/product/category/controller/test/ProductCategoryControllerTest.java
  - ProductCategoryControllerTest 선택 > 오른쪽 마우스 클릭 > Junit Test
- 테스트 결과 : Junit Console 에서 
  
### Gradle Test
- Controller Test : src/test/java/com/musinsa/product/category/controller/test/ProductCategoryControllerTest.java
  - ProductCategoryControllerTest 선택 > 오른쪽 마우스 클릭 > Gradle Test
- 단위 및 통합 테스트 진행
- 테스트 결과 : musinsa-dev-api\build\reports\tests\test\index.html
  - index.html 을 실행하면 Gradle Test 결과를 UI로 확인 가능

### Swagger-UI
- http://localhost:8080/swagger-ui/index.html 접속 후 API 단위 테스트 진행

## 5. 참고 URL
- H2 Console : http://localhost:8080/h2-console

![image](https://user-images.githubusercontent.com/29423360/186088528-d2c37371-f066-407f-90f4-484d5e0f852f.png)

- Swagger UI : http://localhost:8080/swagger-ui/index.html

![image](https://user-images.githubusercontent.com/29423360/186088469-2e14bc6a-efea-4773-af90-6e696c103a1d.png)
