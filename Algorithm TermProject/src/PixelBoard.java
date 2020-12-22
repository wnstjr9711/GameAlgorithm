import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;

public class PixelBoard extends JComponent{
	private int [][] map;
	private Point currentPos;
	private int winner;
	public double p1Time, p2Time;
	public boolean end;

	public PixelBoard(int[][] map) {
		this.map = map;
		this.end = false;
	}

	public void update(int[][] map, Point currentPos, double p1Time, double p2Time) {
		this.map = map;
		this.currentPos = currentPos;
		this.p1Time = p1Time;
		this.p2Time = p2Time;
	}

	public void gameEnd(int winner) {
		this.winner = winner;
		this.end = true;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		BufferedImage bgimg = null, redDolimg = null, blueDolimg = null, player1img = null, player2img = null, arrow = null, arrow2 = null;
		int numberOfblueDol = 0, numberOfredDol = 0;
		String snumberOfblueDol, snumberOfredDol, p1TimeString, p2TimeString;
		Font font = new Font("Helvetica", Font.BOLD, 16);

		try {
			bgimg = ImageIO.read(new File("./images/board.png"));
			redDolimg = ImageIO.read(new File("./images/redDol.png"));
			blueDolimg = ImageIO.read(new File("./images/blueDol.png"));
			player1img = ImageIO.read(new File("./images/player1.jpg"));
			player2img = ImageIO.read(new File("./images/player2.jpg"));
			arrow = ImageIO.read(new File("./images/arrow.png"));
			arrow2 = ImageIO.read(new File("./images/arrow2.png"));
		} catch (IOException e) {
			System.out.println("IO Error");
		}

		g2.setFont(font);
		g2.drawImage(bgimg, 0, 0, 590, 590, this);

		for ( int i = 0; i < PixelEnv.SIZE_OF_BOARD; i++)
			for ( int j = 0; j < PixelEnv.SIZE_OF_BOARD; j++) {
				if (map[i][j] == 1 ) {
					g2.drawImage(blueDolimg, 85 + (54 * j) - 3, 85 + (54 * i) - 1, this);
					numberOfblueDol++;
				} else if (map[i][j] == 2 ) {
					g2.drawImage(redDolimg, 85 + (54 * j) - 3, 85 + (54 * i) - 1, this);
					numberOfredDol++;
				}
			}
		snumberOfblueDol = Integer.toString(numberOfblueDol);
		snumberOfredDol = Integer.toString(numberOfredDol);

		g2.drawImage(arrow, 25 , (int)(currentPos.getX() * 54) + 80 , this);
		g2.drawImage(arrow2, (int)(currentPos.getY() * 54) + 80 , 515 , this);

		g2.setColor(Color.blue);
		g2.drawRect(605, 10, 160, 130);
		g2.drawImage(player1img, 625, 20, 120, 80, this);
		g2.drawString("Player1: ", 628, 124);
		g2.drawImage(blueDolimg, 696, 105, 25, 25, this);
		g2.drawString(snumberOfblueDol, 725, 124);

		g2.setColor(Color.red);
		g2.drawRect(605, 400, 160, 130);
		g2.drawImage(player2img, 625, 410, 120, 80, this);
		g2.drawString("Player2: ", 628, 514);
		g2.drawImage(redDolimg, 696, 495, 25, 25, this);
		g2.drawString(snumberOfredDol, 725, 514);

		p1TimeString = String.format("%.3f ms", p1Time);
		p2TimeString = String.format("%.3f ms", p2Time);

		g2.setColor(Color.black);
		g2.drawString("Cumulative Time: ", 615, 160);
		g2.drawString(p1TimeString, 615, 180);
		g2.drawString("Cumulative Time: ", 615, 550);
		g2.drawString(p2TimeString, 615, 570);

		if (end)
			switch (winner) {
				case 1:
					g2.drawString("Player " + winner + " Win!", 636, 245);
					g2.drawImage(player1img, 625, 255, 120, 80, this);
					break;
				case 2:
					g2.drawString("Player " + winner + " Win!", 636, 245);
					g2.drawImage(player2img, 625, 255, 120, 80, this);
					break;
				case 3:
					if (p1Time < p2Time) {
						g2.drawString("Draw! Player 1 Win!", 613, 245);
						g2.drawImage(player1img, 625, 255, 120, 80, this);
					} else {
						g2.drawString("Draw! Player 2 Win!", 613, 245);
						g2.drawImage(player2img, 625, 255, 120, 80, this);
					}
			}
	}
}