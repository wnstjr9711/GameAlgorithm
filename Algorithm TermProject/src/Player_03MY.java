import java.awt.*;

public class Player_03MY extends Player {
	boolean TwoXtwo = false; // ���� 2*2 ����
	double[][] weightMap = new double[SIZE_OF_BOARD][SIZE_OF_BOARD]; // ����ġ ��
	int[] s = {-1, 1}; // �̵� ��
	Point[][] weightPair = new Point[SIZE_OF_BOARD][SIZE_OF_BOARD]; // ����ġ ¦ ��Ī
	Point[] tempPair = new Point[2]; // ���� ���� ����ġ�� ����� �� ���� ���� �� possibleClean3�� ���� ���� ��ġ�� ��� �� ��ġ�� ���� 
	Point[][] myWeightMap = new Point[SIZE_OF_BOARD][SIZE_OF_BOARD]; // �� ���� ����ġ��
	public Player_03MY(int myNum, int	 SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }
	public Point nextPosition(Point lastPosition) {
		int last_x = (int)lastPosition.getX(), last_y = (int)lastPosition.getY(); //���� x, y
		int otherNum = myNum % 2 + 1;
		int perfect_x = last_x; // ���� x
		int perfect_y = last_y; // ���� y
		double weight = -99;
		
		if (TwoXtwo) { // 2 * 2�� ��������� ������ �� ȣ��
			for (int i = 0; i < 2; i++) {
				if (isIn(last_x + s[i], last_y) && lastPosition.equals(myWeightMap[last_x + s[i]][last_y]))
					weightMap[last_x + s[i]][last_y] = 0;
				else if (isIn(last_x, last_y + s[i]) && lastPosition.equals(myWeightMap[last_x][last_y + s[i]]))
					weightMap[last_x][last_y + s[i]] = 0;
			}
		}
		Blocked(last_x, last_y); // �� ����ġ ���� �������� Ȯ��
		for (int i = 0; i < 2; i++) { //�߽��� ������ �������� Ȯ��
			for (int j = 0; j < 2; j++) // �ش� ��ǥ�� �ٸ� �߽ɰ� ������ ���� �ʾҴ��� Ȯ��
				if(isIn(last_x + s[i], last_y + s[j]) && myWeightMap[last_x + s[i]][last_y + s[j]] != null) Blocked(last_x + s[i], last_y + s[j]);
			if (isIn(last_x + s[i], last_y) && myWeightMap[last_x + s[i]][last_y] != null) Blocked(last_x + s[i], last_y);
			if (isIn(last_x, last_y + s[i]) && myWeightMap[last_x][last_y + s[i]] != null) Blocked(last_x, last_y + s[i]);
		}
		Blocking(last_x, last_y); // ��� ���� 2���� 3�� ����� ��� ���� ����ġ
		Blocked2(last_x, last_y); // �� �밢�� ����ġ�� ���� ���
		weightMap[last_x][last_y] = 0; // ��밡 �� ��ġ�� ����ġ �ʱ�ȭ
		for (int i = 0; i < 2; i++) { // ��뵹�� possibleClean3 Ȯ��
			// ������ 0�̰� ��ĭ �� ���� ������� Ȯ�� Ȯ�� �� ������ ���� ���ݷ�Ʈ�� �Ǹ� ����� ���´�.
			if (isIn(last_x, last_y + 2 * s[i]) && map[last_x][last_y + s[i]] == 0 && map[last_x][last_y] == map[last_x][last_y + 2 * s[i]])
				weightMap[last_x][last_y + s[i]] += possibleClean3(last_x, last_y, last_x, last_y + s[i], otherNum);
			if (isIn(last_x + 2 * s[i], last_y) && map[last_x + s[i]][last_y] == 0 && map[last_x][last_y] == map[last_x + 2 * s[i]][last_y])
				weightMap[last_x + s[i]][last_y] += possibleClean3(last_x, last_y, last_x + s[i], last_y, otherNum);
		}
		
		for (int i = 0; i < SIZE_OF_BOARD; i++) { // ���ڰ� ��ġ ���鼭 ��ġ ����
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
		if (perfect_x == last_x) predictWeight(perfect_x, perfect_y); // pw�� ���ʿ��� �����̾��� ��� �ٽ� x�� ���ؼ� tempPair�� ���Է�(y�� ���� �ʾƵ� ���� ��)
		Point nextPosition = turnEnd(perfect_x, perfect_y); // ���� �� ���� ���� ����ġ ����
		return nextPosition;
	}
	public boolean isIn(int x, int y) { // �� ����Ȯ��
		if (x >= 0 && y >= 0 && x < SIZE_OF_BOARD && y < SIZE_OF_BOARD)
			return true;
		return false;
	}
	public void Blocking(int x, int y) { // ����� 2������ ����ġ = 3, �밢���� 4
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) { // �밢���� �ε��� �� ���� �밢�� ����ġ 4
				if (isIn(x + s[i], y + s[j])) {
					if (map[x + s[i]][y + s[j]] == map[x][y]) { // �밢�� �ε�
						if (map[x][y + s[j]] == 0 && map[x + s[i]][y] == 0) { // �ݴ� �밢���� 0�� ��
							weightMap[x][y + s[j]] += 4;
							weightMap[x + s[i]][y] += 4;
							weightPair[x][y + s[j]] = new Point(x + s[i], y);
							weightPair[x + s[i]][y] = new Point(x, y + s[j]);
						}
					}
				}
			}
			if (isIn(x, y + s[i])) { // ��, �� Ȯ��
				if (map[x][y + s[i]] == map[x][y]) { // 2�� Ȯ��
					for (int j = 0; j < 2; j++) {
						if (isIn(x + s[j], y)) {
							if (map[x + s[j]][y + s[i]] == 0 && map[x + s[j]][y] == 0) {// ������ �Ѵ� 0�� �� + �𼭸� Ȯ��
								weightMap[x + s[j]][y] += 3;
								weightMap[x + s[j]][y + s[i]] += 3;
								weightPair[x + s[j]][y] = new Point(x + s[j], y + s[i]);
								weightPair[x + s[j]][y + s[i]] = new Point(x + s[j], y);
							}// �Ʒ��� ���ڰ� ��������� ������ ������ �ֿ켱�� ����ġ�� �ش�.
							if (map[x + s[j]][y + s[i]] == map[x][y] && map[x + s[j]][y] == 0) weightMap[x + s[j]][y] = 99;
							if (map[x + s[j]][y] == map[x][y] && map[x + s[j]][y + s[i]] == 0) weightMap[x + s[j]][y + s[i]] = 99;
						}
					}
				}
			}
			if (isIn(x + s[i], y)) { // ��, �� Ȯ��
				if (map[x + s[i]][y] == map[x][y]) { // 2�� Ȯ��
					for (int j = 0; j < 2; j++) {
						if (isIn(x, y + s[j])) {
							if (map[x + s[i]][y + s[j]] == 0 && map[x][y + s[j]] == 0) {// ������ �Ѵ� 0�� ��
								weightMap[x][y + s[j]] += 3;
								weightMap[x + s[i]][y + s[j]] += 3;
								weightPair[x][y + s[j]] = new Point(x + s[i], y + s[j]);
								weightPair[x + s[i]][y + s[j]] = new Point(x, y + s[j]);
							}// ���� ����� ������ ����
							if (map[x + s[i]][y + s[j]] == map[x][y] && map[x][y + s[j]] == 0) weightMap[x][y + s[j]] = 99;
							if (map[x][y + s[j]] == map[x][y] && map[x + s[i]][y + s[j]] == 0) weightMap[x + s[i]][y + s[j]] = 99;
						}
					}
				}
			}
		}
	}
	public void Blocked(int x, int y) { // ���� 3 * 3 ����ġ�� ���� ���
		if (new Point(x, y).equals(myWeightMap[x][y])) { // 3 * 3�� �߽��� ����
			weightMap[x][y] -= 3.5;
			myWeightMap[x][y] = null;
		}
	}
	public void Blocked2(int x, int y) { // ���� �밢���� ���� ���
		if (myWeightMap[x][y] != null) {
			int dx = (int)myWeightMap[x][y].getX();
			int dy = (int)myWeightMap[x][y].getY();
			myWeightMap[dx][dy] = null;
			myWeightMap[x][y] = null; // ���� ��ġ�� �����ϴ� ���� ���� ��ġ�� ��� ����
			weightMap[dx][dy] -= 3.5;
			weightMap[x][y] -= 3.5;
		}
	}
	public void make2X2(int x, int y) { // �� ����ġ �� �����
		TwoXtwo = true;
		if (myWeightMap[x][y] != null) {// ����ġ �߽ɿ� �� ���� ���� ����
			for (int i = 0; i < 2; i++) { // �߽��� ���� ���·� ���� ����ġ�� �޾��� ��
				if (map[x + s[i]][y] == myNum) { // �������� 3���� ����
					for (int j = 0; j < 2; j++)
						myWeightMap[x + s[j]][y + s[i]] = new Point(x, y + s[i]);
				}
				else { // �������� 3���� ����
					for (int j = 0; j < 2; j++)
						myWeightMap[x + s[i]][y + s[j]] = new Point(x + s[i], y);
				}
			}
		}
	}
	public int possibleClean3(int x, int y, int check_x, int check_y, int who) { // ��뵹 Ȯ��, ���� Ȯ�� / 3*3 �ȿ��� ����� �� �ε� ��Ȳ�� ���� �� ����� ����ġ�� +10�̴�.
		// x, y ������ check_x, check_y�� ����ġ�� �� ��ġ
		int w = 10;
		// ��� ������ �߽����� 3 * 3 �簢���� ��뵹�� �ϳ��� ������ �߰� ����ġ�� �������� 
		if ((check_x == 1 || check_x == 6) && (check_y == 1 || check_y == 6)) return 0; // + ���� �𼭸��϶��� Ȯ��
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!isIn(check_x + s[i], check_y + s[j])) return 0;
				if (map[check_x + s[i]][check_y + s[j]] == who % 2 + 1) return 0; // �밢�� �𼭸� Ȯ��
			} // check ������ ��뵹�� ��� 0�� ����
			if (!isIn(check_x + s[i], check_y) || !isIn(check_x, check_y + s[i])) return 0;
			if (map[check_x + s[i]][check_y] == who % 2 + 1) return 0; // ��, �� Ȯ��
			if (map[check_x][check_y + s[i]] == who % 2 + 1) return 0; // ��, �� Ȯ��
		}
		return w; // possible �Ѱ����� �Ǵ�
	}
	public boolean isCleanCenter(int x, int y) { //�����Ѱ��� ��� ��� �ִ� ������ Ȯ�� 
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				if (!isIn(x + s[i], y + s[j])) return false;
				if (map[x + s[i]][y + s[j]] != 0) return false; // �밢�� �𼭸� Ȯ��
			}
			if (!isIn(x + s[i], y) || !isIn(x, y + s[i])) return false;
			if (map[x + s[i]][y] != 0) return false; // ��, �� Ȯ��
			if (map[x][y + s[i]] != 0) return false; // ��, �� Ȯ��
		}
		return true;
	}
	public double predictWeight(int x, int y) { // last_x, last_y �������� ���ڰ� ��ȸ�ϸ� ���� �� �� �ִ� ��ġ Ȯ��
		double pw = 0;
		if (!TwoXtwo) {
			for (int i = 0; i < SIZE_OF_BOARD; i++)
				if (weightMap[x][i] >= 10 && i != y || weightMap[i][y] >= 10 && i != x) return -10; // �� �� �ִ� ���ο� ����ġ�� 10���� ũ�ų� ���� ���� �ִٸ� -10�Ͽ� ����ġ ����
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
			for (int i = 0; i < 2; i++) { // ���� ���� Clean3�� �߽��� �� �� ���� x, y�� 1�� ����ġ�� ����(�������� ���� �� ��������)
				if (isCleanCenter(x, y + s[i])) pw += 0.25;
				if (isCleanCenter(x + s[i], y)) pw += 0.25;
			}
		}
		if (TwoXtwo) { // 2*2�� �Ǹ� �� �� ������ �δ� ���� ���� �켱���� ����
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
	public Point turnEnd(int x, int y) { // ���� �е� ���� ���� ��ȸ�ϸ� ��� ���� ����ġ ���� �� �� ����ġ ����
		Point np = new Point(x, y); 
		if (np.equals(myWeightMap[x][y])) { // 3 * 3�� �߽��� ������ �� ���� + ��ȿ���� Ȯ��
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++)
					weightMap[x + s[i]][y + s[j]] += 30; // �밢���� 2�� ����
				weightMap[x + s[i]][y] += 50;
				weightMap[x][y + s[i]] += 50;
			}
			make2X2(x, y); // ����ġ ����
		}
		if (np.equals(tempPair[0])) { // PossibleClean3�� ����� Point�� next�� �����Ǿ��� �� �ش� 3*3�� ���� ����ġ �߽� ����
			int tx = (int)tempPair[1].getX();
			int ty = (int)tempPair[1].getY();
			weightMap[tx][ty] = 3.5;
			myWeightMap[tx][ty] = tempPair[1];
		}
		if (myWeightMap[x][y] != null && !np.equals(myWeightMap[x][y])) {//�밢�� ����ġ�� ���� ������ ���(����ġ�� �ִµ� 3*3�� �߽ɽ��� �ƴѰ��)
			weightMap[(int)myWeightMap[x][y].getX()][(int)myWeightMap[x][y].getY()] += 80; // ����� �ݴ��ʿ� ���� ����ġ �Է�
			if (weightMap[x][y] >= 80) TwoXtwo = true; // 2*2�� �Ǿ���
		}
		for (int i = 0; i < 2; i++) { // ��� 2 * 2 ���� ��ġ�� ����ġ �ʱ�ȭ
			if (isIn(x + s[i], y) && (weightPair[x + s[i]][y] != null && weightPair[x + s[i]][y].equals(np))
								  || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x + s[i], y)))) { // 3��
				weightMap[x + s[i]][y] -= 3;
				weightPair[x + s[i]][y] = null;
			}
			if (isIn(x, y + s[i]) && (weightPair[x][y + s[i]] != null && weightPair[x][y + s[i]].equals(np))
					              || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x, y + s[i])))) { // ���� �����ϰ��ְų� ����ް��ִ� ���
				weightMap[x][y + s[i]] -= 3;
				weightPair[x][y + s[i]] = null;
			}
			for (int j = 0; j < 2; j++) { // �밢�� �ʱ�ȭ
				if (isIn(x + s[i], y + s[j]) && (weightPair[x + s[i]][y + s[j]] != null && weightPair[x + s[i]][y + s[j]].equals(np)) // ��� ����ġ 3�� �� ������ �밢�� ���� �ʿ� ����
										     || (weightPair[x][y] != null && weightPair[x][y].equals(new Point(x + s[i], y + s[j])))) {
					weightMap[x + s[i]][y + s[j]] -= 4;
					weightPair[x + s[i]][y + s[j]] = null;
				}
				if (isIn(x + s[i], y + s[j]) && map[x + s[i]][y + s[j]] == myNum && map[x + s[i]][y] == 0 && map[x][y + s[j]] == 0) { // �� �밢���� ����ġ �ֱ�
					myWeightMap[x + s[i]][y] = new Point(x, y + s[j]);
					myWeightMap[x][y + s[j]] = new Point(x + s[i], y);
					weightMap[x + s[i]][y] += 3.5;
					weightMap[x][y + s[j]] += 3.5;
				}
			}
		}
		weightPair[x][y] = null;
		weightMap[x][y] = 0; // ���� �� �� ����ġ �ʱ�ȭ		
		return np;
	}
}