import java.awt.*;

public class Player_Example5 extends Player {
	public Player_Example5(int myNum, int SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }

	public Point nextPosition(Point lastPosition) {
		Point nextPosition = new Point(0, 0);
		int last_x = (int)lastPosition.getX(), last_y = (int)lastPosition.getY();
		int [][] weight = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
		int continuity=0;//나의돌이 연속으로 있는 수
		int conv=0;//상대의 돌이연속으로 있는 수
		int max = 0;
		int x = 0, y = 0;
		boolean isFour = false;//내가 4개연속 완성했는가?
		boolean isSquare = false;//내가 네모모양 완성했는가?
		boolean isFourV = false;//상대가
		boolean isSquareV = false;


/*--------------------------------------------------------------------------------------------------------------------*/
//승리조건 완성 분석

		//행에대한 4개연속 분석
		for (int i = 0; i < SIZE_OF_BOARD; i++)
			for (int j = 0; j < SIZE_OF_BOARD - 3; j++)
				if (map[i][j] == 1 && map[i][j+1] == 1 && map[i][j+2] == 1 && map[i][j+3] == 1)
					isFour = true;
				else if (map[i][j] == 2 && map[i][j+1] == 2 && map[i][j+2] == 2 && map[i][j+3] == 2)
					isFourV = true;
		//열에대한 4개연속 분석
		for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
			for (int j = 0; j < SIZE_OF_BOARD; j++)
				if (map[i][j] == 1 && map[i+1][j] == 1 && map[i+2][j] == 1 && map[i+3][j] == 1)
					isFour = true;
				else if (map[i][j] == 2 && map[i+1][j] == 2 && map[i+2][j] == 2 && map[i+3][j] == 2)
					isFourV = true;
		//대각선 4개연속 분석
		for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
			for (int j = 0; j < SIZE_OF_BOARD - 3; j++)
				if (map[i][j] == 1 && map[i+1][j+1] == 1 && map[i+2][j+2] == 1 && map[i+3][j+3] == 1)
					isFour = true;
				else if (map[i][j] == 2 && map[i+1][j+1] == 2 && map[i+2][j+2] == 2 && map[i+3][j+3] == 2)
					isFourV = true;
		for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
			for (int j = 3; j < SIZE_OF_BOARD; j++)
				if (map[i][j] == 1 && map[i+1][j-1] == 1 && map[i+2][j-2] == 1 && map[i+3][j-3] == 1)
					isFour = true;
				else if (map[i][j] == 2 && map[i+1][j-1] == 2 && map[i+2][j-2] == 2 && map[i+3][j-3] == 2)
					isFourV = true;
		//네모완성에 대한 연속 분석
		for (int i = 0; i < SIZE_OF_BOARD - 1; i++)
			for (int j = 0; j < SIZE_OF_BOARD - 1; j++)
				if (map[i][j] == 1 && map[i][j+1] == 1 && map[i+1][j] == 1 && map[i+1][j+1] == 1)
					isSquare = true;
				else if (map[i][j] == 2 && map[i][j+1] == 2 && map[i+1][j] == 2 && map[i+1][j+1] == 2)
					isSquareV = true;

/*--------------------------------------------------------------------------------------------------------------------*/
//연속된 열과행에 대한 가중치 부여

		//player1: 1  player2: 2
		for (int i = 0; i < SIZE_OF_BOARD; i++) {
			//열고정 공격과 수비
			for (int j = 0; j < SIZE_OF_BOARD; j++) {    //열 탐색

				if ((j == 0 && i == 0) || (j == 0 && i == 7) || (j == 7 && i == 0) || (j == 7 && i == 7))
					continue; //꼭지점
				else if (map[j][i] == myNum) {    //나의돌이 있는경우
					continuity++;
					if (isFour) continue;
					if (continuity == 3) {
						if (j > 2) {
							weight[j - 3][i] = 10;
						}
						if (j < 7) {
							weight[j + 1][i] = 10;
						}
					}else if (continuity == 2) {
						if (j > 1) {
							weight[j - 2][i] = 4;
						}
						if (j < 7) {
							weight[j + 1][i] = 4;
						}
					} else if (continuity == 1) {
						for (int k = j - 1; k < j + 1; k++) {
							for (int q = i - 1; q < i + 1; q++) {
								if (k < 0 || q < 0 || k > 7 || q > 7) continue;
								weight[k][q]++;
							}
						}
					}
					conv = 0;
				} else if (map[j][i] == 0) {    //비어있는 경우
					conv = 0;
					continuity = 0;
				} else {//상대돌이 있는경우
					conv++;
					if(isFourV) continue;
					if (conv == 3) {
						if (j > 2) {
							weight[j - 3][i] = 8;
						}
						if (j < 7) {
							weight[j + 1][i] = 8;
						}
					} else if (conv == 2) {
						if (j > 1) {
							weight[j - 2][i] = 3;
						}
						if (j < 7) {
							weight[j + 1][i] = 3;
						}
					}
					else if (conv == 1) {
						for (int k = j - 1; k < j + 1; k++) {
							for (int q = i - 1; q < i + 1; q++) {
								if (k < 0 || q < 0 || k > 7 || q > 7) continue;
								weight[k][q]++;
							}
						}
					}
					continuity = 0;
				}
			}

			//연속 초기화
			conv = 0;
			continuity = 0;

			//행고정 공격과 수비
			for (int j = 0; j < SIZE_OF_BOARD; j++) {    //행 탐색
				if ((j == 0 && i == 0) || (j == 0 && i == 7) || (j == 7 && i == 0) || (j == 7 && i == 7))
					continue; //꼭지점
				else if (map[i][j] == myNum) {//나의돌이 놓여진 경우
					continuity++;
					if(isFour) continue;
					if (continuity == 3) {
						if (j > 2) {
							weight[i][j - 3] = 10;
						}
						if (j < 7) {
							weight[i][j + 1] = 10;
						}
					} else if (continuity == 2) {
						if (j > 1) {
							weight[i][j - 2] = 4;
						}
						if (j < 7) {
							weight[i][j + 1] = 4;
						}
					} else if (continuity == 1) {
						for (int k = i - 1; k < i + 1; k++) {
							for (int q = j - 1; q < j + 1; q++) {
								if (k < 0 || q < 0 || k > 7 || q > 7) continue;
								weight[k][q]++;
							}
						}
					}
					conv = 0;
				} else if (map[i][j] == 0) {    //비어있는
					conv = 0;
					continuity = 0;
				} else {//상대돌
					conv++;
					if(isFourV) continue;
					if (conv == 3) {
						if (j > 2) {
							weight[i][j - 3] = 8;
						}
						if (j < 7) {
							weight[i][j + 1] = 8;
						}
					} else if (conv == 2) {
						if (j > 1) {
							weight[i][j - 2] = 3;
						}
						if (j < 7) {
							weight[i][j + 1] = 3;
						}
					}
					else if (conv == 1) {
						for (int k = i - 1; k < i + 1; k++) {
							for (int q = j - 1; q < j + 1; q++) {
								if (k < 0 || q < 0 || k > 7 || q > 7) continue;
								weight[k][q]++;
							}
						}
					}
					continuity = 0;
				}
			}
		}
/*--------------------------------------------------------------------------------------------------------------------*/
//네모모양이 만들어지는 조건
		if(!isSquare||!isSquareV) {				//나의돌 조건
			for (int i = 0; i < SIZE_OF_BOARD ; i++) {
				if(isSquare) break;
				for (int j = 0; j < SIZE_OF_BOARD ; j++) {
					if((i<7&&j<7)) {
						if ((map[i][j] == 1 && map[i][j + 1] == 1) && (map[i + 1][j] == 1 || map[i + 1][j + 1] == 1)) {    // 가로 아래쪽
							if (map[i + 1][j] == 1) weight[i + 1][j + 1] = 10;
							else if (map[i + 1][j + 1] == 1) weight[i + 1][j] = 10;
						}
						if ((map[j][i] == 1 && map[j + 1][i] == 1) && (map[j][i + 1] == 1 || map[j + 1][i + 1] == 1)) { //  세로 오른쪽
							if (map[j][i + 1] == 1) weight[j + 1][i + 1] = 10;
							else if (map[j + 1][i + 1] == 1) weight[j][i + 1] = 10;
						}
					}
					if((i>0&&j<7)) {
						if ((map[i][j] == 1 && map[i][j + 1] == 1) && (map[i - 1][j] == 1 || map[i - 1][j + 1] == 1)) { // 가로  위쪽
							if (map[i - 1][j] == 1) weight[i - 1][j + 1] = 10;
							else if (map[i - 1][j + 1] == 1) weight[i - 1][j] = 10;
						}
						if ((map[j][i] == 1 && map[j + 1][i] == 1) && (map[j + 1][i - 1] == 1 || map[j][i - 1] == 1)) { //세로 왼쪽
							if (map[j + 1][i - 1] == 1) weight[j][i - 1] = 10;
							else if (map[j][i - 1] == 1 && j < 7) weight[j + 1][i - 1] = 10;
						}
					}
				}
			}
			for (int i = 0; i < SIZE_OF_BOARD ; i++) {		//상대 돌 조건 네모모양
				if(isSquareV) break;
				for (int j = 0; j < SIZE_OF_BOARD ; j++) {
					if((i<7&&j<7)) {
						if ((map[i][j] == 2 && map[i][j + 1] == 2) && (map[i + 1][j] == 2 || map[i + 1][j + 1] == 2)) { //가로 아래쪽
							if (map[i + 1][j] == 2) weight[i + 1][j + 1] = 8;
							else if (map[i + 1][j + 1] == 2) weight[i + 1][j] = 8;
						}
						if ((map[j][i] == 2 && map[j + 1][i] == 2) && (map[j][i + 1] == 2 || map[j + 1][i + 1] == 2)) { //세로 오른쪽
							if (map[j][i + 1] == 2) weight[j + 1][i + 1] = 8;
							else if (map[j + 1][i + 1] == 2) weight[j][i + 1] = 8;
						}
					}
					if((i>0&&j<7)) {
						if ((map[i][j] == 2 && map[i][j + 1] == 2) && (map[i - 1][j] == 2 || map[i - 1][j + 1] == 2)) { //가로 위쪽
							if (map[i - 1][j + 1] == 2) weight[i - 1][j] = 8;
							else if (map[i - 1][j] == 2) weight[i - 1][j + 1] = 8;
						}
						if ((map[j][i] == 2 && map[j + 1][i] == 2) && (map[j + 1][i - 1] == 2 || map[j][i - 1] == 2)) {    //세로 왼쪽
							if (map[j + 1][i - 1] == 2) weight[j][i - 1] = 8;
							else if (map[j][i - 1] == 2) weight[j + 1][i - 1] = 8;
						}
					}
				}
			}

		}
/*--------------------------------------------------------------------------------------------------------------------*/
//대각선 가중치고려

		if(!isFour||!isFourV) {
			for (int i = 0; i < SIZE_OF_BOARD - 3; i++) {
				for (int j = 0; j < SIZE_OF_BOARD - 3; j++) {
					if(!isFour) {
						if (map[i][j] == 1 && map[i + 1][j + 1] == 1 && map[i + 2][j + 2] == 1) {
							if (i != 0 && j != 0) {
								weight[i - 1][j - 1] = 10;
							}
							weight[i + 3][j + 3] = 8;
						} else if (map[i][j] == 1 && map[i + 1][j + 1] == 1) {
							if (i != 0 && j != 0) {
								weight[i - 1][j - 1] = 3;
							}
							weight[i + 2][j + 2] = 2;
						}
					}
					if(!isFourV) {
						if (map[i][j] == 2 && map[i + 1][j + 1] == 2 && map[i + 2][j + 2] == 2) {
							if (i != 0 && j != 0) {
								weight[i - 1][j - 1] = 8;
							}
							weight[i + 3][j + 3] = 8;
						} else if (map[i][j] == 2 && map[i + 1][j + 1] == 2) {
							if (i != 0 && j != 0) {
								weight[i - 1][j - 1] = 2;
							}
							weight[i + 2][j + 2] = 2;
						}
					}
				}
			}
			for (int i = 0; i < SIZE_OF_BOARD - 3; i++) {
				for (int j = 3; j < SIZE_OF_BOARD; j++) {
					if(!isFour) {
						if (map[i][j] == 1 && map[i + 1][j - 1] == 1 && map[i + 2][j - 2] == 1) {
							if (i != 0) {
								weight[i - 1][j + 1] = 10;
							}
							weight[i + 3][j - 3] = 10;
						} else if (map[i][j] == 1 && map[i + 1][j - 1] == 1) {
							if (i != 0) {
								if(j>6) continue;
								weight[i - 1][j + 1] = 3;
							}
							weight[i + 2][j - 2] = 3;
						}
					}
					if(!isFourV && j < 7) {
						if (map[i][j] == 2 && map[i + 1][j - 1] == 2 && map[i + 2][j - 2] == 2) {
							if (i != 0) {
								weight[i - 1][j + 1] = 8;
							}
							weight[i + 3][j - 3] = 8;
						} else if (map[i][j] == 2 && map[i + 1][j - 1] == 2) {
							if (i != 0) {
								if(j>6) continue;
								weight[i - 1][j + 1] = 2;
							}
							weight[i + 2][j - 2] = 2;
						}
					}
				}
			}
		}
/*--------------------------------------------------------------------------------------------------------------------*/

		for (int i = 0; i < SIZE_OF_BOARD; i++) {//놓을 곳을 결정
			if (map[last_x][i] == 0) {
				if (weight[last_x][i] >= max) {
					max = weight[last_x][i];
					x = last_x;
					y = i;
				}
			}
			if (map[i][last_y] == 0) {
				if (weight[i][last_y] >= max) {
					max = weight[i][last_y];
					x = i;
					y = last_y;
				}
			}
		}
		nextPosition = new Point(x, y);

/*--------------------------------------------------------------------------------------------------------------------*/

		return nextPosition;
	}
}

