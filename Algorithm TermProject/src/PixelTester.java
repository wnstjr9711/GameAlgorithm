import java.awt.*;
import java.util.ArrayList;

public class PixelTester{
	final public static PixelEnv env = new PixelEnv(true);
	public static Point lastPos;
	public static Point action;

	public static Player p1, p2;

	public static int delay = 100;
	public static double startTime;
	public static double endTime;
	public static double time;

	public static ArrayList<Object> return_list = null;

	public static void main(String[] args) throws InterruptedException {
		p1 = new Player_03MY(1, PixelEnv.SIZE_OF_BOARD, env.map);
		p2 = new Player_Example(2, PixelEnv.SIZE_OF_BOARD, env.map);

		// Game Reset
		lastPos = env.reset();

		// Game Start
		while (true) {

			// Player1
			if (env.turn == 1) {
				startTime = System.nanoTime();
				action = p1.nextPosition(lastPos);
				endTime = System.nanoTime();
				time = (endTime - startTime) / 1000000.0;

				return_list = env.step(action, time);

				p1.setCurrentPosition(action);
				System.out.println("Player 1 return: " + action);
				lastPos = action;
			}
			Thread.sleep(delay);

			// Check Done
			if ((boolean)return_list.get(0)) {
				env.printMap();
				System.out.println(return_list.get(1));
				break;
			}

			// Player2
			if (env.turn == 2) {
				startTime = System.nanoTime();
				action = p2.nextPosition(lastPos);
				endTime = System.nanoTime();
				time = (endTime - startTime) / 1000000.0;

				return_list = env.step(action, time);

				p2.setCurrentPosition(action);
				System.out.println("Player 2 return: " + action);
				lastPos = action;
			}
			Thread.sleep(delay);

			// Check Done
			if ((boolean)return_list.get(0)) {
				env.printMap();
				System.out.println(return_list.get(1));
				break;
			}
		}
	}
}
