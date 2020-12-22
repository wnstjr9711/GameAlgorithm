import java.awt.Point;

public abstract class Player {
	public int myNum;
	public int SIZE_OF_BOARD;
	public int[][] map;
	public Point currentPosition;
  
	protected Player(int myNum, int SIZE_OF_BOARD, int[][] map) {
		this.myNum = myNum;
		this.SIZE_OF_BOARD = SIZE_OF_BOARD;
		this.map = map;
		this.currentPosition = new Point();
		this.currentPosition.setLocation(0, 0);
	}
	
	void setCurrentPosition(Point currentPosition) {
		this.currentPosition.setLocation(currentPosition);
	}

	// abstract method!!!
	abstract Point nextPosition(Point lastPosition);
}
