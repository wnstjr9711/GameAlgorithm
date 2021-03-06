# GameAlgorithm

1. Basic Rule

![image](https://user-images.githubusercontent.com/67565800/104925547-57799980-59e2-11eb-9d38-21a2d076da91.png)

- 이번 프로젝트는 Pixel 게임으로 기준점의 열이나 행을 한 개만 변경하여서 이동하는 게임이다. 
- 이때 기준점은 상대돌을 기준으로 한다.
- Player1은 선공, 파란돌
- Player2은 후공, 빨간돌
- 앞선 높인 상태로 경기 시작!

2. 승리조건
- 가로 or 세로 or 대각선 4개 and 연속된 돌 and 2X2 모양의 돌

![image](https://user-images.githubusercontent.com/67565800/104925852-b17a5f00-59e2-11eb-9162-6b98400c15dc.png)
![image](https://user-images.githubusercontent.com/67565800/104925855-b3442280-59e2-11eb-8937-982270755af2.png)
                    

- 상대가 아래 조건을 달성할 경우 승리!
   1. 맵을 벗어난 위치 2. 코너의 위치 3. 이미 높여진 돌의 위치 4. 행과 열이 모두 바뀐 위치 4. 에러 발생 => 해당 조건을 하나라도 달성한 플레이어는 바로 패배.

3. 무승부 조건

![image](https://user-images.githubusercontent.com/67565800/104925965-de2e7680-59e2-11eb-8cfb-3494a5d92230.png)

- 모든 행과 열에 돌이 놓여져있을 경우 무승부로 처리
- 이때 각 플레이어의 수행 시간을 비교해서 적은 쪽에 판정승!
