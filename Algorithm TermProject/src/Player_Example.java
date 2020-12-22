import java.awt.*;
import java.util.Random;

public class Player_Example extends Player {
	public Player_Example(int myNum, int SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }

	public Point nextPosition(Point lastPosition) {
		Point nextPosition;
		int last_x = (int)lastPosition.getX(), last_y = (int)lastPosition.getY();

		Random random = new Random();
		int xy;
		int index;
		while (true) {
			xy = random.nextInt(2);
			index = random.nextInt(SIZE_OF_BOARD);

			if (xy == 0)
				if (map[last_x][index] == 0) {
					nextPosition = new Point(last_x, index);
					break;
				}
			else
				if (map[index][last_y] == 0) {
					nextPosition = new Point(index, last_y);
					break;
				}
		}

		return nextPosition;
	}
}