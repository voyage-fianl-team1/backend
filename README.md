
# 매치기 서버

[전체 아키텍처 확인하기](https://github.com/voyage-fianl-team1)

## 📚 주요 기술 스택

[![My Skills](https://skillicons.dev/icons?i=idea,java,mysql&pipeline=5)](https://skillicons.dev)
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7f217b6e-761d-4904-bfb8-e3d46296322e/image_344.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T132928Z&X-Amz-Expires=86400&X-Amz-Signature=117497c91e03446e523b381031ab983538ea1c99c3ee8d5bdf013df22179655b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image%2520344.png%22&x-id=GetObject">
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/ac76cef9-7b9e-4e95-b653-37386dc4ff37/Group_806.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133057Z&X-Amz-Expires=86400&X-Amz-Signature=8ecc5394c1085a615e8d81036bf2fd1bed5289e6ad31a9d9fd94255348651209&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520806.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/aa8b56f6-9fdf-498b-908c-628fcabdf908/Group_807.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133123Z&X-Amz-Expires=86400&X-Amz-Signature=7a1f3cca9ec6d048fb331fb830a544c18e723547639a68ecf9eb6b7498c7cd80&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520807.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/9e15d795-b55f-4544-b02d-890a12229dff/Group_808.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133141Z&X-Amz-Expires=86400&X-Amz-Signature=9ff258158c7d0aefdd328995768951f97262bfc19c5af1b1c035d4b4ce2931db&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520808.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/67bc5760-acb2-4d13-8bdf-0b0d642fa2b0/Group_810.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133154Z&X-Amz-Expires=86400&X-Amz-Signature=f489926afe8746171e37c3024f69a96d66b0009d9b1930336865cab80a363b23&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520810.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7ad5fef3-e7ec-4f07-b353-f70218f00d45/Group_812.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133207Z&X-Amz-Expires=86400&X-Amz-Signature=afa853ab3b4a8ae321878ffb6749983048a3efafb86fabade083797d5e56031b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520812.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b1211d9b-5573-43e2-88dc-4ac1643894e3/image_341.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133223Z&X-Amz-Expires=86400&X-Amz-Signature=b766aa48eae596381c577b14c2777680e114d3b68a0996a0f9e45cdd47bc65c4&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image%2520341.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/895545a5-b5b3-4515-80dd-902973cf3e02/image_345.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133240Z&X-Amz-Expires=86400&X-Amz-Signature=b5407d1f26070a4885a6d7f0590ffbdcff5f71107a8559fcde32b21b0688a5c1&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image%2520345.png%22&x-id=GetObject" >
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/b68c5b7c-2198-4f71-add7-eba22a7cd7c3/image_346.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133250Z&X-Amz-Expires=86400&X-Amz-Signature=b7bc39875e5afff2e2c2d288b4c9cb86c0d37c9087130c5b335de387a63d70ae&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image%2520346.png%22&x-id=GetObject" >


## 💁 역할

## 신유근
- **회원가입 및 로그인(+카카오)**
  - Spring-Security와 jwt를 활용한 회원 관리 및 oAuth2를 활용한 카카오 소셜 로그인 
- **채팅 및 실시간 알림**
  - websocket을 활용한 실시간 채팅 및 알림
- **참가목록 및 경기결과**
  - qeurydsl을 활용한 각 종목 별 경기결과 구현
- **댓글**

## 박태웅
- **마이페이지**
  - jpql을 활용한 나의 글 및 경기목록 조회 
- **랭킹**
  
- **알람CRUD**
  - querydsl을 활용한 알림기능

## 박윤주
- **CI/CD**
  - Github Actions를 이용하여 빌드 후 압축하여 소스를 S3에 업로드하고 AWS CodeDepoy 실행하여 S3에 있는 코드를 EC2에 배포
- **배포**
- **경기목록**
  - querydsl을 활용하여 경기 종목별 필터링 후 조회, 원하는 조건 (최신 작성순, 조회순)으로 정렬 
- **경기 CRUD**
  - 경기 모집글 작성, 사진 업로드, 수정, 삭제, querydsl을 이용하여 위치기반 경기 조회

## 🌠 트러블 Shooting

### N+1 query 문제

#### 요구사항

FetchType을 lazy전략으로 데이터를 가져온 이후에 데이터의 하위entity를 조회 할 경우 발생

<img src="https://user-images.githubusercontent.com/99013391/190170567-b8897a87-3c67-45aa-8dee-8ce3af3852a9.png" width="40%">

#### 해결방안

(1)fetch join을 사용하여 해결  → 주체가 되는 entity와 fetch join이 된 entity 모두 영속화

(2)Entity Graph를 사용하여 해결

(3)Batch size를 조절하여 해결

(4)일반 join을 사용하여 해결 → 주체가 되는 entity만 영속화

FetchType을 lazy 전략으로 한 후 두 entity를 영속화 해야 할 경우 (1) 방법으로  문제 해결 

FetchType을 lazy 전략으로 한 후 Dto를 사용하여 검색 조건으로 entity를 사용하거나 영속화 없이 데이터 조회만 할 경우(4) 방법으로 문제 해결

<img src="https://user-images.githubusercontent.com/99013391/190170935-48d64627-20ae-4320-9f05-380fbae95c8e.png" width="40%">

```sql
explain select * from springdb.request where springdb.request.request_id = 291;
```
![Untitled](https://user-images.githubusercontent.com/99013391/191000130-6d287a78-7137-4c1c-b175-8aed4c52f61b.png)
![이미지](https://user-images.githubusercontent.com/99013391/191001247-a0b3537e-a070-4521-8c76-e891bfa29c1f.png)
```sql
explain select * from springdb.post where springdb.post.post_id = 198;
```
![Untitled (2)](https://user-images.githubusercontent.com/99013391/191000204-d97d4980-d864-4e0a-8f8b-017b498f650e.png)
![Untitled (3)](https://user-images.githubusercontent.com/99013391/191000218-42d47842-06f0-4cf1-925a-b2061434fb30.png)

```sql
explain select * from springdb.user where springdb.user.user_id = 99;
```
![Untitled (5)](https://user-images.githubusercontent.com/99013391/191000364-ce37bc59-d623-4d17-9421-5bf57b1f9a33.png)
![Untitled (6)](https://user-images.githubusercontent.com/99013391/191000373-c973b455-7641-4bd6-b87d-bd33dd7d2b0c.png)

```sql
explain select * from springdb.request as R join springdb.post as P  on P.post_id = R.post_id join springdb.user U on U.user_id = R.user_id  where R.request_id = 291;
```
![Untitled (7)](https://user-images.githubusercontent.com/99013391/191000395-f9a89d22-fb60-4c99-aeac-7594d9ed7841.png)
![Untitled (8)](https://user-images.githubusercontent.com/99013391/191000433-5fc8f215-22ab-4eef-a1bc-461ddb0bd919.png)

검색되는 rows 값은 3으로 동일하지만 N+1 쿼리 문제가 발생하는 쿼리들의 duration을 합쳐보면 0.00095175 , N+1 쿼리 문제를 해결한 쿼리는 duration은 0.00044975이다. 쿼리 속도가 2배 이상 개선된 것을 볼 수 있다.

### 소켓통신 인증 문제

#### 요구사항

StompJs는 최초연결시 header를 담는 공간을 제한해 두었기 떄문에, 연결시에 token을 보내 사용자를 인증할 수 없었다.

#### 해결방안

최초 연결이 아닌, 전송하는 메세지 마다는 header를 포함 할 수 있어서, 메시지 전송시마다 accessToken을 전달하여 인증을 구현하였다.

![image](https://user-images.githubusercontent.com/99013391/190171625-05d2a571-8a22-45c3-90a9-98b75cb1a8b9.png)

### 채팅방의 안 읽은 메시지 로직문제

#### 요구사항

유저마다 참여한 채팅방의 읽지 않은 메세지 갯수를 카운팅 하기 위해서, 채팅방 마다 마지막으로 접속한 시간을 저장해야 했다. 처음에는 클라이언트의 localstorage에 저장하려고 했으나 이 방법을 쓸 경우 사용자가 다른 브라우저를 사용할 경우 마지막으로 접속한 시간이 달라지는 문제가 발생했다.  

#### 해결방안

서버에서 User와 Room의 관계가 @ManytoMany로 설정을 하였는데 이 경우 다른 컬럼을 추가하지 못하기 때문에 User와 Room을 @ManytoOne의 관계로 변경하고 UserRoom Entity를 새롭게 만들어, 마지막 활동 시간을 저장하는 lastActive 컬럼을 추가하여 해결하였다.

<img src="https://user-images.githubusercontent.com/99013391/190172175-15ab9605-de06-4de6-ae38-5878f2a288e3.png" width="50%">

### 지도에서 내 근처 게시글 효율적으로 불러오기

#### 요구사항

내 위치 주변 특정 반경 내에 경기 데이터를 불러 올 때 무한스크롤을 이용해 지도 축소 시 무한스크롤 이용해데이터를 불러오려고 했으나 지도는 고정적으로 움직이지 않아 무한스크롤을 적용하기가 어려웠다. 그래서 지도를 사용하는 서비스를 확인해 어떤 방식으로 데이터를 불러오는지 확인해보기로 했다.

먼저 직방의 경우 지도에 마커 대신 클러스터 기능을 이용해 대략적인 정보만을 보여주었고 클러스터 또한 클라이언트가 아닌 서버에서 관리해주는 듯 했다.

네이버 부동산의 경우는 네트워크 탭에서 확인해 봤을 때 지도의 반경이 변함에 따라 반경 안에 데이터를 다시 불러오는 것으로 보여졌다. 직방의 방식은 백엔드의 코드를 대폭 변경해야 했고 네이버 부동산의 방식은 프론트 및 백엔드 모두 수정할 코드가 적었으며 사용자가 축소한 만큼의 데이터만 불러와 UX상 더 좋을 것 같다고 판단되어 반경 내에 데이터를 불러오는 방식으로 채택했다.

#### 해결방안

(1) 카카오맵 level 이용 (level: 지도 축소 및 확대 단위)

(2) 지도 끝단 북동쪽, 남서쪽의 위도,경도 이용

반경을 이용해서 데이터를 불러오는 방식은 2가지를 생각했다. (1)번을 통해 데이터를 불러온다면 현재 보고 있는 지도의 크기가 변경 될 때 각 level의 경계선 상에 있는 데이터도 불러오지 오지 못하며 사용자가 조금만 축소해서 레벨을 넘어가 버렸을 때 필요없는 데이터도 불러올 수 있어 (2)번 방법을 이용하여 해결했다.

