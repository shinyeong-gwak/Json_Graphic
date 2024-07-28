1. **GUI 실행 환경 설치**

   GUI를 실행할 Host PC에 설치되어야할 프로그램은 다음과 같다.

   Windows : Xming / 사이트에서 직접 다운로드 및 설치

   <http://www.straightrunning.com/XmingNotes/>



   ![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.001.png)Mac OS : xquartz / 터미널에서 직접 아래의 명령어를 입력하면 설치 가능

   이후 재부팅 필요

- 시뮬레이터가 설치된 Linux



  ![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.002.png)Java 설치

  Ssh 설정 변경 – 해당 설정 앞에 #이 있다면 지우기, 설정을 다음과 같게 변경

`		`i를 입력하면 수정 모드 변경, 






















![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.003.png)![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.004.png)![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.005.png)		esc 클릭 후 :wq 입력 – 저장 후 나가기 됨.

이 후 재접속 시 ssh -X옵션을 통해 접속








1. ![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.006.png)**GUI 실행**


1. **실행 방법**
- File의 Open을 이용해 json 파일을 불러올 수 있다.

![텍스트, 스크린샷이(가) 표시된 사진

자동 생성된 설명](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.007.png)![텍스트, 전자제품, 스크린샷, 소프트웨어이(가) 표시된 사진

자동 생성된 설명](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.008.png)

![텍스트, 스크린샷, 도표, 소프트웨어이(가) 표시된 사진

자동 생성된 설명](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.009.png)

![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.010.png)**   : station 노드

![](Aspose.Words.22f62f65-cc84-493f-9f8d-83ca85439a8d.011.png)   : AP 노드

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
