import java.awt.*;

public class Player_03MY extends Player {
	boolean TwoXtwo = false; // 나의 2*2 여부
	double[][] weightMap = new double[SIZE_OF_BOARD][SIZE_OF_BOARD]; // 가중치 맵
	int[] s = {-1, 1}; // 이동 폭
	Point[][] weightPair = new Point[SIZE_OF_BOARD][SIZE_OF_BOARD]; // 가중치 짝 매칭
	Point[] tempPair = new Point[2]; // 나의 예상 가중치를 계산할 때 최종 선정 시 possibleClean3를 통해 나온 위치인 경우 그 위치를 저장 
	Point[][] myWeightMap = new Point[SIZE_OF_BOARD][SIZE_OF_BOARD]; // 내 유망 가중치맵
	public Player_03MY(int myNum, int	 SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }
	public Point nextPosition(Point lastPosition) {
		int last_x = (int)lastPosition.getX(), last_y = (int)lastPosition.getY(); //이전 x, y
		int otherNum = myNum % 2 + 1;
		int perfect_x = last_x; // 최적 x
		int perfect_y = last_y; // 최적 y
		double weight = -99;
		
		if (TwoXtwo) { // 2 * 2가 만들어지기 시작할 때 호출
			for (int i = 0; i < 2; i++) {
				if (isIn(last_x + s[i], last_y) && lastPosition.equals(myWeightMap[last_x + s[i]][last_y]))
					weightMap[last_x + s[i]][last_y] = 0;
				else if (isIn(last_x, last_y + s[i]) && lastPosition.equals(myWeightMap[last_x][last_y + s[i]]))
					weightMap[last_x][last_y + s[i]] = 0;
			}
		}
		Blocked(last_x, last_y); // 내 가중치 맵이 막혔는지 확인
		for (int i = 0; i < 2; i++) { //중심의 가지가 막혔는지 확인
			for (int j = 0; j < 2; j++) // 해당 좌표가 다른 중심과 연결이 되지 않았는지 확인
				if(isIn(last_x + s[i], last_y + s[j]) && myWeightMap[last_x + s[i]][last_y + s[j]] != null) Blocked(last_x + s[i], last_y + s[j]);
			if (isIn(last_x + s[i], last_y) && myWeightMap[last_x + s[i]][last_y] != null) Blocked(last_x + s[i], last_y);
			if (isIn(last_x, last_y + s[i]) && myWeightMap[last_x][last_y + s[i]] != null) Blocked(last_x, last_y + s[i]);
		}
		Blocking(last_x, last_y); // 상대 돌이 2돌과 3돌 만드는 경우 막는 가중치
		Blocked2(last_x, last_y); // 내 대각선 가중치도 막힌 경우
		weightMap[last_x][last_y] = 0; // 상대가 둔 위치의 가중치 초기화
		for (int i = 0; i < 2; i++) { // 상대돌의 possibleClean3 확인
			// 인접이 0이고 한칸 더 옆이 있을경우 확인 확인 후 양쪽이 유망 공격루트가 되면 가운데를 막는다.
			if (isIn(last_x, last_y + 2 * s[i]) && map[last_x][last_y + s[i]] == 0 && map[last_x][last_y] == map[last_x][last_y + 2 * s[i]])
				weightMap[last_x][last_y + s[i]] += possibleClean3(last_x, last_y, last_x, last_y + s[i], otherNum);
			if (isIn(last_x + 2 * s[i], last_y) && map[last_x + s[i]][last_y] == 0 && map[last_x][last_y] == map[last_x + 2 * s[i]][last_y])
				weightMap[last_x + s[i]][last_y] += possibleClean3(last_x, last_y, last_x + s[i], last_y, otherNum);
		}
		
		for (int i = 0; i < SIZE_OF_BOARD; i++) { // 십자가 위치 돌면서 위치 선정
			if (map[last_x][i] == 0 && i != last_y) {
				double pw = predictWeight(last_x, i);
				if (weightMap[last_x][i] + pw > weight) {
					weight = weightMap[last_x][i] + pw;
					perfect_x = last_x;
					perfect_y = i;
				}
			}
			if (map[i][last_y] == 0 && i != last_x) {
				double pw = predictWeight(i, last_y);
				if (weightMap[i][last_y] + pw > weight) {
					weight = weightMap[i][last_y] + pw;
					perfect_x = i;
					perfect_y = last_y;
				}
			}
		}
		if (perfect_x == last_x) predictWeight(perfect_x, perfect_y); // pw가 불필요한 갱신이었을 경우 다시 x에 대해서 tempPair값 재입력(y를 돌지 않아도 됐을 때)
		Point nextPosition = turnEnd(perfect_x, perfect_y); // 내가 둔 돌이 만든 가중치 갱신
		return nextPosition;
	}
	public boolean isIn(int x, int y) { // 판 내부확인
		if (x >= 0 && y >= 0 && x < SIZE_OF_BOARD && y < SIZE_OF_BOARD)
			return true;
		return false;
	}
	public void Blocking(int x, int y) { // 상대의 2돌막는 가중치 = 3, 대각선은 4
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) { // 대각선이 두돌일 때 사이 대각선 가중치 4
				if (isIn(x + s[i], y + s[j])) {
					if (map[x + s[i]][y + s[j]] == map[x][y]) { // 대각선 두돌
						if (map[x][y + s[j]] == 0 && map[x + s[i]][y] == 0) { // 반대 대각선이 0일 때
							weightMap[x][y + s[j]] += 4;
							weightMap[x + s[i]][y] += 4;
							weightPair[x][y + s[j]] = new Point(x + s[i], y);
							weightPair[x + s[i]][y] = new Point(x, y + s[j]);
						}
					}
				}
			}
			if (isIn(x, y + s[i])) { // 좌, 우 확인
				if (map[x][y + s[i]] == map[x][y]) { // 2돌 확인
					for (int j = 0; j < 2; j++) {
						if (isIn(x + s[j], y)) {
							if (map[x + s[j]][y + s[i]] == 0 && map[x + s[j]][y] == 0) {// 옆줄이 둘다 0일 때 + 모서리 확인
								weightMap[x + s[j]][y] += 3;
								weightMap[x + s[j]][y + s[i]] += 3;
								weightPair[x + s[j]][y] = new Point(x + s[j], y + s[i]);
								weightPair[x + s[j]][y + s[i]] = new Point(x + s[j], y);
							}// 아래는 ㄴ자가 만들어지면 마지막 공간에 최우선의 가중치를 준다.
							if (map[x + s[j]][y + s[i]] == map[x][y] && map[x + s[j]][y] == 0) weightMap[x + s[j]][y] = 99;
							if (map[x + s[j]][y] == map[x][y] && map[x + s[j]][y + s[i]] == 0) weightMap[x + s[j]][y + s[i]] = 99;
						}
					}
				}
			}
			if (isIn(x + s[i], y)) { // 상, 하 확인
				if (map[x + s[i]][y] == map[x][y]) { // 2돌 확인
					for (int j = 0; j < 2; j++) {
						if (isIn(x, y + s[j])) {
							if (map[x + s[i]][y + s[j]] == 0 && map[x][y + s[j]] == 0) {// 옆줄이 둘다 0일 때
								weightMap[x][y + s[j]] += 3;
								weightMap[x + s[i]][y + s[j]] += 3;
								weightPair[x][y + s[j]] = new Point(x + s[i], y + s[j]);
								weightPair[x + s[i]][y + s[j]] = new Point(x, y + s[j]);
							}// ㄴ자 만들면 무조건 막기
							if (map[x + s[i]][y + s[j]] == map[x][y] && map[x][y + s[j]] == 0) weightMap[x][y + s[j]] = 99;
							if (map[x][y + s[j]] == map[x][y] && map[x + s[i]][y + s[j]] == 0) weightMap[x + s[i]][y + s[j]] = 99;
						}
					}
				}
			}
		}
	}
	public void Blocked(int x, int y) { // 나의 3 * 3 가중치가 막힌 경우
		if (new Point(x, y).equals(myWeightMap[x][y])) { // 3 * 3의 중심을 막힘
			weightMap[x][y] -= 3.5;
			myWeightMap[x][y] = null;
		}
	}
	public void Blocked2(int x, int y) { // 나의 대각선이 막힌 경우
		if (myWeightMap[x][y] != null) {
			int dx = (int)myWeightMap[x][y].getX();
			int dy = (int)myWeightMap[x][y].getY();
			myWeightMap[dx][dy] = null;
			myWeightMap[x][y] = null; // 현재 위치가 연결하던 곳과 현재 위치를 모두 지움
			weightMap[dx][dy] -= 3.5;
			weightMap[x][y] -= 3.5;
		}
	}
	public void make2X2(int x, int y) { // 내 가중치 맵 만들기
		TwoXtwo = true;
		if (myWeightMap[x][y] != null) {// 가중치 중심에 내 돌이 들어온 상태
			for (int i = 0; i < 2; i++) { // 중심을 먹은 상태로 주위 가중치를 받았을 때
				if (map[x + s[i]][y] == myNum) { // 수직으로 3줄인 상태
					for (int j = 0; j < 2; j++)
						myWeightMap[x + s[j]][y + s[i]] = new Point(x, y + s[i]);
				}
				else { // 수평으로 3줄인 상태
					for (int j = 0; j < 2; j++)
						myWeightMap[x + s[i]][y + s[j]] = new Point(x + s[i], y);
				}
			}
		}
	}
	public int possibleClean3(int x, int y, int check_x, int check_y, int who) { // 상대돌 확인, 내돌 확인 / 3*3 안에서 가운데가 빈 두돌 상황이 됐을 때 가운데는 가중치가 +10이다.
		// x, y 기준점 check_x, check_y는 가중치를 줄 위치
		int w = 10;
		// 가운데 지점을 중심으로 3 * 3 사각형에 상대돌이 하나라도 있으면 추가 가중치를 주지않음 
		if ((check_x == 1 || check_x == 6) && (check_y == 1 || check_y == 6)) return 0; // + 판의 모서리일때도 확인
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!isIn(check_x + s[i], check_y + s[j])) return 0;
				if (map[check_x + s[i]][check_y + s[j]] == who % 2 + 1) return 0; // 대각선 모서리 확인
			} // check 지점이 상대돌인 경우 0을 리턴
			if (!isIn(check_x + s[i], check_y) || !isIn(check_x, check_y + s[i])) return 0;
			if (map[check_x + s[i]][check_y] == who % 2 + 1) return 0; // 상, 하 확인
			if (map[check_x][check_y + s[i]] == who % 2 + 1) return 0; // 좌, 우 확인
		}
		return w; // possible 한것으로 판단
	}
	public boolean isCleanCenter(int x, int y) { //인접한곳이 모두 비어 있는 점임을 확인 
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!isIn(x + s[i], y + s[j])) return false;
				if (map[x + s[i]][y + s[j]] != 0) return false; // 대각선 모서리 확인
			}
			if (!isIn(x + s[i], y) || !isIn(x, y + s[i])) return false;
			if (map[x + s[i]][y] != 0) return false; // 상, 하 확인
			if (map[x][y + s[i]] != 0) return false; // 좌, 우 확인
		}
		return true;
	}
	public double predictWeight(int x, int y) { // last_x, last_y 기준으로 십자가 순회하며 내가 둘 수 있는 위치 확인
		double pw = 0;
		if (!TwoXtwo) {
			for (int i = 0; i < SIZE_OF_BOARD; i++)
				if (weightMap[x][i] >= 10 && i != y || weightMap[i][y] >= 10 && i != x) return -10; // 둘 수 있는 라인에 가중치가 10보다 크거나 같은 값이 있다면 -10하여 가중치 리턴
			for (int i = 0; i < 2; i++) {
				if (isIn(x, y + 2 * s[i]) && map[x][y + s[i]] == 0 && myNum == map[x][y + 2 * s[i]] && possibleClean3(x, y, x, y + s[i], myNum) == 10) {
					tempPair[0] = new Point(x, y);
					tempPair[1] = new Point(x, y + s[i]);
					return 2;
				}
				if (isIn(x + 2 * s[i], y) && map[x + s[i]][y] == 0 && myNum == map[x + 2 * s[i]][y] && possibleClean3(x, y, x + s[i], y, myNum) == 10) {
					tempPair[0] = new Point(x, y);
					tempPair[1] = new Point(x + s[i], y);
					return 2;
				}
			}		
			for (int i = 0; i < 2; i++) { // 인접 돌이 Clean3의 중심이 될 때 현재 x, y는 1의 가중치를 가짐(막을돌이 없을 때 던져놓기)
				if (isCleanCenter(x, y + s[i])) pw += 0.25;
				if (isCleanCenter(x + s[i], y)) pw += 0.25;
			}
		}
		if (TwoXtwo) { // 2*2가 되면 내 돌 인접에 두는 것을 가장 우선으로 여김
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if (isIn(x + s[i], y + s[j]) && map[x + s[i]][y + s[j]] == myNum) pw += 1;
				}
				if (isIn(x + s[i], y) && map[x + s[i]][y] == myNum) pw += 1; 
				if (isIn(x, y + s[i]) && map[x][y + s[i]] == myNum) pw += 1;
			}
		}
		return pw;
	}
	public Point turnEnd(int x, int y) { // 내가 둔돌 기준 인접 순회하며 상대 공격 가중치 갱신 및 내 가중치 갱신
		Point np = new Point(x, y); 
		if (np.equals(myWeightMap[x][y])) { // 3 * 3의 중심을 가졌을 때 공격 + 유효한지 확인
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++)
					weightMap[x + s[i]][y + s[j]] += 30; // 대각선엔 2차 공격
				weightMap[x + s[i]][y] += 50;
				weightMap[x][y + s[i]] += 50;
			}
			make2X2(x, y); // 가중치 연결
		}
		if (np.equals(tempPair[0])) { // PossibleClean3를 통과한 Point가 next로 선정되었을 때 해당 3*3에 나의 가중치 중심 저장
			int tx = (int)tempPair[1].getX();
			int ty = (int)tempPair[1].getY();
			weightMap[tx][ty] = 3.5;
			myWeightMap[tx][ty] = tempPair[1];
		}
		if (myWeightMap[x][y] != null && !np.equals(myWeightMap[x][y])) {//대각선 가중치에 ㄴ을 형성한 경우(가중치가 있는데 3*3의 중심심이 아닌경우)
			weightMap[(int)myWeightMap[x][y].getX()][(int)myWeightMap[x][y].getY()] += 80; // 연결된 반대쪽에 높은 가중치 입력
			if (weightMap[x][y] >= 80) TwoXtwo = true; // 2*2가 되었음
		}
		for (int i = 0; i < 2; i++) { // 상대 2 * 2 유망 위치의 가중치 초기화
			if (isIn(x + s[i], y) && (weightPair[x + s[i]][y] != null && weightPair[x + s[i]][y].equals(np))
								  || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x + s[i], y)))) { // 3중
				weightMap[x + s[i]][y] -= 3;
				weightPair[x + s[i]][y] = null;
			}
			if (isIn(x, y + s[i]) && (weightPair[x][y + s[i]] != null && weightPair[x][y + s[i]].equals(np))
					              || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x, y + s[i])))) { // 내가 연결하고있거나 연결받고있는 경우
				weightMap[x][y + s[i]] -= 3;
				weightPair[x][y + s[i]] = null;
			}
			for (int j = 0; j < 2; j++) { // 대각선 초기화
				if (isIn(x + s[i], y + s[j]) && (weightPair[x + s[i]][y + s[j]] != null && weightPair[x + s[i]][y + s[j]].equals(np)) // 상대 가중치 3인 곳 막으면 대각선 막을 필요 없음
										     || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x + s[i], y + s[j])))) {
					weightMap[x + s[i]][y + s[j]] -= 4;
					weightPair[x + s[i]][y + s[j]] = null;
				}
				if (isIn(x + s[i], y + s[j]) && map[x + s[i]][y + s[j]] == myNum && map[x + s[i]][y] == 0 && map[x][y + s[j]] == 0) { // 내 대각선에 가중치 주기
					myWeightMap[x + s[i]][y] = new Point(x, y + s[j]);
					myWeightMap[x][y + s[j]] = new Point(x + s[i], y);
					weightMap[x + s[i]][y] += 3.5;
					weightMap[x][y + s[j]] += 3.5;
				}
			}
		}
		weightPair[x][y] = null;
		weightMap[x][y] = 0; // 내가 둔 곳 가중치 초기화		
		return np;
	}
}