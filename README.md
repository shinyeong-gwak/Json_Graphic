1. **GUI 실행 환경 설치**

   GUI를 실행할 Host PC에 설치되어야할 프로그램은 다음과 같다.

   Windows : Xming / 사이트에서 직접 다운로드 및 설치

   <http://www.straightrunning.com/XmingNotes/>


![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 001](https://github.com/user-attachments/assets/2786f326-bce3-4927-b7bd-c05d26777be5)
Mac OS : xquartz / 터미널에서 직접 아래의 명령어를 입력하면 설치 가능

   이후 재부팅 필요

- 시뮬레이터가 설치된 Linux

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 002](https://github.com/user-attachments/assets/a7ee5e9a-c4db-4b28-9f2f-a6011ad0358a)


Java 설치

  Ssh 설정 변경 – 해당 설정 앞에 #이 있다면 지우기, 설정을 다음과 같게 변경

`		`i를 입력하면 수정 모드 변경, 



















![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 003](https://github.com/user-attachments/assets/39aa11ec-5fcd-45dc-b2b7-afe12ba811b3)
![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 004](https://github.com/user-attachments/assets/258af937-48f7-4857-8086-71b9511073ae)
![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 005](https://github.com/user-attachments/assets/fe85459a-b41b-43ca-80d8-2abfbdd8c7ff)


esc 클릭 후 :wq 입력 – 저장 후 나가기 됨.

이 후 재접속 시 ssh -X옵션을 통해 접속








1. ![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 006](https://github.com/user-attachments/assets/5f6e713c-13e2-4f63-927f-bbf3e1b851a1)
**GUI 실행**


1. **실행 방법**
- File의 Open을 이용해 json 파일을 불러올 수 있다.

![텍스트, 스크린샷이(가) 표시된 사진

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 007](https://github.com/user-attachments/assets/4248d768-de47-41fb-9173-118d69b45f07)

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 008](https://github.com/user-attachments/assets/218c754e-615e-4380-b29e-99188d7b8f41)


![텍스트, 스크린샷, 도표, 소프트웨어이(가) 표시된 사진

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 009](https://github.com/user-attachments/assets/c6412007-8a85-4820-8b4c-8f870fc677e0)

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 010](https://github.com/user-attachments/assets/05a4eecb-801d-42ef-ba0d-a67b083ce8e7)
**   : station 노드

![Aspose Words 22f62f65-cc84-493f-9f8d-83ca85439a8d 011](https://github.com/user-attachments/assets/001ef896-3fc3-4b68-bbc4-e54cea6d86f4)
**   : AP 노드

노드의 색이 진하게 표시될수록 여러 노드가 해당 위치에 겹쳐있다.

그림에 표시된 번호에 따른 쓰임은 다음과 같다.

1. Open 된 json 파일의 경로를 알 수 있다.
1. 오른쪽의 좌표 패널을 확대/축소할 수 있다.
1. 현재 설정된 json 파일을 볼 수 있다.
1. 현재 작업중인 json 파일을 이용해 시뮬레이터를 실행할 수 있다.
1. 도트를 클릭하면 노드에 관련된 정보를 왼쪽 패널에서 확인할 수 있다.



1. **추가된 설정파일 문법** 

|프로퍼티(데이터)|내용|
| :-: | :-: |
|name|랜덤 설정할 구간의 이름|
|xMin|나올 수 있는 가장 작은 x 값|
|xMax|나올 수 있는 가장 큰 x 값|
|yMin|나올 수 있는 가장 작은 y 값|
|yMax|나올 수 있는 가장 큰 y 값|

- 랜덤 노드 위치 지정

  Seed와 regions 배열을 이용하여 랜덤 구역을 설정한다.















































  ![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.012.png)동일한 x , y의 범위, 시드, 노드의 개수를 사용하면 같은 값의 랜덤 노드 위치를 가진다.

- 랜덤 노드 사용






























  ![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.013.png)![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.014.png)location 값을 number 배열이 아닌 region 객체의 name 파라미터로 설정 시 적용 가능하다.


- 커스텀 AC 설정

|프로퍼티(데이터)|내용||
| :-: | :-: | :- |
|name|커스텀 Access Category의 이름||
|refAC|타입 : BK / BE / VI / VO||
|CWmin|contention window의 최솟값||
|CWmax|contention window의 최댓값||
|AIFSN|||
|MaxTXOP\_ms|||











































![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.015.png)

- Channel width 지정

|프로퍼티(데이터)|내용|
| :-: | :-: |
|channelWidth|채널 폭의 넓이 (Mhz)|
|channel|가용 채널 번호의 인덱스|





















































![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.016.png)
