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
  
  
  git clone https://github.com/who-hoo/musinsa-test.git
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

### docker 사용

- install
  ```bash
  // 도커 설치(되어있다면 생략 가능)
  curl -fsSL https://get.docker.com/ | sudo sh
  docker pull zjunghoo/musinsa-test
  ```
- execute
  ```bash
  docker images // image id 확인
  docker run -it -d -p 8080:8080 [doker images로 확인한 image id]
  ```

## 2. API Doc

| 기능               | API                                             | 비고                                                       |
| ------------------ | ----------------------------------------------- | ---------------------------------------------------------- |
| 카테고리 등록      | POST /api/product/category                                |                                                            |
| 카테고리 수정      | PATCH /api/product/category/{id}                          |                                                            |
| 카테고리 삭제      | DELETE /api/product/category/{id} 			|  |
| 전체 카테고리 조회 | GET /api/product/category                                 |                                                            |
| 특정 카테고리 조회 | GET /api/product/category/{id}                            | 해당 상품 카테고리의 하위 상품 카테고리 전체 조회 가능             |

## 3. API 요청 및 응답 예시(JSON)

### 카테고리 등록

- POST http://localhost:8080/api/categories
- request body
  ```json
  {
    "kor_name": "반려동물 장난감",
    "parent_category_id": 191
  }
  ```
- response
  ```json
  {
    "category_id": 192,
    "category_name": "반려동물 장난감",
    "category_english_name": null,
    "parent_category_id": 191,
    "sub_categories": []
  }
  ```

### 카테고리 수정

- PATCH http://localhost:8080/api/categories/188
- request body
  ```json
  {
    "kor_name": "티셔츠",
    "eng_name": null,
    "parent_category_id": 2,
    "sub_category_id_list": [23, 25]
  }
  ```
- response
  ```json
  {
    "category_id": 188,
    "category_name": "티셔츠",
    "category_english_name": null,
    "parent_category_id": 2,
    "sub_categories": [
      {
        "category_id": 23,
        "category_name": "반소매 티셔츠",
        "category_english_name": null,
        "parent_category_id": 188,
        "sub_categories": []
      },
      {
        "category_id": 25,
        "category_name": "긴소매 티셔츠",
        "category_english_name": null,
        "parent_category_id": 188,
        "sub_categories": []
      }
    ]
  }
  ```

### 카테고리 삭제

- DELETE http://localhost:8080/api/categories/1?withSubCategories=false : 상위 카테고리만 삭제(하위 카테고리는 루트 카테고리로 변경)
- DELETE http://localhost:8080/api/categories/2?withSubCategories=true : 하위 카테고리 함께 삭제

### 카테고리 조회

- GET http://localhost:8080/api/categories/1
- response
  ```json
  {
    "categories": [
      {
        "category_id": 1,
        "category_name": "상의",
        "category_english_name": "Top",
        "parent_category_id": null,
        "sub_categories": [
          {
            "category_id": 24,
            "category_name": "피케/카라 티셔츠",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 26,
            "category_name": "맨투맨/스웨트셔츠",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 27,
            "category_name": "민소매 티셔츠",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 28,
            "category_name": "후드 티셔츠",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 29,
            "category_name": "셔츠/블라우스",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 30,
            "category_name": "니트/스웨터",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          },
          {
            "category_id": 31,
            "category_name": "기타 상의",
            "category_english_name": null,
            "parent_category_id": 1,
            "sub_categories": []
          }
        ]
      },
      {
        "category_id": 2,
        "category_name": "아우터",
        "category_english_name": "Outer",
        "parent_category_id": null,
        "sub_categories": [
          {
            "category_id": 32,
            "category_name": "후드 집업",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 33,
            "category_name": "블루종/MA-1",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 34,
            "category_name": "레더/라이더스 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 35,
            "category_name": "무스탕/퍼",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 36,
            "category_name": "트러커 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 37,
            "category_name": "슈트/블레이저 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 38,
            "category_name": "카디건",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 39,
            "category_name": "아노락 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 40,
            "category_name": "플리스/뽀글이",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 41,
            "category_name": "트레이닝 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 42,
            "category_name": "스타디움 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 43,
            "category_name": "환절기 코트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 44,
            "category_name": "겨울 싱글 코트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 45,
            "category_name": "겨울 더블 코트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 46,
            "category_name": "겨울 기타 코트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 47,
            "category_name": "롱패딩/롱헤비 아우터",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 48,
            "category_name": "숏패딩/숏헤비 아우터",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 49,
            "category_name": "패딩 베스트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 50,
            "category_name": "베스트",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 51,
            "category_name": "사파리/헌팅 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 52,
            "category_name": "나일론/코치 재킷",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 53,
            "category_name": "기타 아우터",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": []
          },
          {
            "category_id": 188,
            "category_name": "티셔츠",
            "category_english_name": null,
            "parent_category_id": 2,
            "sub_categories": [
              {
                "category_id": 23,
                "category_name": "반소매 티셔츠",
                "category_english_name": null,
                "parent_category_id": 188,
                "sub_categories": []
              },
              {
                "category_id": 25,
                "category_name": "긴소매 티셔츠",
                "category_english_name": null,
                "parent_category_id": 188,
                "sub_categories": []
              }
            ]
          }
        ]
      },
      {
        "category_id": 3,
        "category_name": "바지",
        "category_english_name": "Pants",
        "parent_category_id": null,
        "sub_categories": [
          {
            "category_id": 54,
            "category_name": "데님 팬츠",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 55,
            "category_name": "코튼 팬츠",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 56,
            "category_name": "슈트 팬츠/슬랙스",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 57,
            "category_name": "트레이닝/조거 팬츠",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 58,
            "category_name": "숏 팬츠",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 59,
            "category_name": "레깅스",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 60,
            "category_name": "점프 슈트/오버올",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          },
          {
            "category_id": 61,
            "category_name": "기타 바지",
            "category_english_name": null,
            "parent_category_id": 3,
            "sub_categories": []
          }
        ]
      },
      {
        "category_id": 4,
        "category_name": "원피스",
        "category_english_name": "Onepiece",
        "parent_category_id": null,
        "sub_categories": [
          {
            "category_id": 62,
            "category_name": "미니 원피스",
            "category_english_name": null,
            "parent_category_id": 4,
            "sub_categories": []
          },
          {
            "category_id": 63,
            "category_name": "미디 원피스",
            "category_english_name": null,
            "parent_category_id": 4,
            "sub_categories": []
          },
          {
            "category_id": 64,
            "category_name": "맥시 원피스",
            "category_english_name": null,
            "parent_category_id": 4,
            "sub_categories": []
          }
        ]
      }
    ]
  }
  ```
