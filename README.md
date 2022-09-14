
# 매치기 서버

[전체 아키텍처 확인하기](https://github.com/voyage-fianl-team1)

## 📚 주요 기술 스택

[![My Skills](https://skillicons.dev/icons?i=github,githubactions,idea,java,mysql&pipeline=5)](https://skillicons.dev)
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/7f217b6e-761d-4904-bfb8-e3d46296322e/image_344.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T132928Z&X-Amz-Expires=86400&X-Amz-Signature=117497c91e03446e523b381031ab983538ea1c99c3ee8d5bdf013df22179655b&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22image%2520344.png%22&x-id=GetObject">
<img src="https://s3.us-west-2.amazonaws.com/secure.notion-static.com/3a6fc0f3-a297-4781-b34d-4f118fcb6732/Group_804.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=AKIAT73L2G45EIPT3X45%2F20220914%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220914T133008Z&X-Amz-Expires=86400&X-Amz-Signature=e51a542e24fc1e488a84b31c0bd6b5bb4b3fc8a65b26fe2b2e8361f6b63df4a3&X-Amz-SignedHeaders=host&response-content-disposition=filename%20%3D%22Group%2520804.png%22&x-id=GetObject" >
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

- **랭킹**

- **알람CRUD**

## 박윤주
- **CI/CD**

- **배포**

- **경기목록**

- **경기 CRUD**

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

