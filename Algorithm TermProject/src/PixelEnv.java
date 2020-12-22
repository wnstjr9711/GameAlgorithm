import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PixelEnv {
    final public static int SIZE_OF_BOARD = 8;
    public int [][] map = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];
    public Point currentPos = null, lastPos = null;
    public double p1Time = 0.0, p2Time = 0.0;
    public int turn = 0;

    public PixelBoard board = null;
    public boolean isActualEnv = false;

    public boolean done = false;
    public String info = "";
    public int winner = 0;
    public ArrayList<Object> return_list = new ArrayList<>();

    public PixelEnv(boolean isActualEnv) {
        this.isActualEnv = isActualEnv;

        JFrame frame = new JFrame();
        frame.setSize(800, 630);
        frame.setTitle("Pixel Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board = new PixelBoard(map);

        frame.add(board);
        frame.setVisible(true);
    }

    public PixelEnv(PixelEnv env) {
        // MCTS copied Env
        for (int i = 0; i < SIZE_OF_BOARD; i++)
            for (int j = 0; j < SIZE_OF_BOARD; j++)
                this.map[i][j] = env.map[i][j];

        this.currentPos = env.currentPos;
        this.lastPos = env.lastPos;
        this.turn = env.turn;
    }

    public ArrayList<Point> get_available_positions() {
        ArrayList<Point> positions = new ArrayList<>();

        int last_X = (int)lastPos.getX();
        int last_Y = (int)lastPos.getY();

        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            if (map[i][last_Y] == 0)
                positions.add(new Point(i, last_Y));

            if (map[last_X][i] == 0)
                positions.add(new Point(last_X, i));
        }

        return positions;
    }

    public double get_number_of_stones() {
        double n_stones = 0.0;

        for (int i = 0; i < SIZE_OF_BOARD; i++)
            for (int j = 0; j < SIZE_OF_BOARD; j++)
                if (map[i][j] != 0 && map[i][j] != -1)
                    n_stones += 1.0;

        return n_stones;
    }

    public Point reset() {
        for (int i = 0; i < SIZE_OF_BOARD; i++)
            for (int j = 0; j < SIZE_OF_BOARD; j++)
                map[i][j] = 0;

        map[0][0] = -1;
        map[0][7] = -1;
        map[7][0] = -1;
        map[7][7] = -1;
        map[4][3] = 1;
        map[3][4] = 2;

        done = false;
        info = "";
        winner = 0;
        return_list.clear();

        turn = 1;
        p1Time = 0;
        p2Time = 0;

        currentPos = new Point(3, 4);
        lastPos = currentPos;

        if (isActualEnv) {
            board.update(map, currentPos, p1Time, p2Time);
            board.repaint();
        }

        return currentPos;
    }

    public ArrayList<Object> step(Point action, double time) {
        done = false;
        info = "";
        winner = 0;
        return_list.clear();

        currentPos = action;

        if (isIndexError(currentPos)) {
            done = true;
            if (turn == 1) {
                info = "Index Error: Player 2 Win !!";
                winner = 2;
            } else {
                info = "Index Error: Player 1 Win !!";
                winner = 1;
            }
        }

        if (isOverlapError(currentPos)) {
            done = true;
            if (turn == 1) {
                info = "Overlap Error: Player 2 Win !!";
                winner = 2;
            } else {
                info = "Overlap Error: Player 1 Win !!";
                winner = 1;
            }
        }

        if (isXYRuleError(currentPos, lastPos)) {
            done = true;
            if (turn == 1) {
                info = "XYRule Error: Player 2 Win !!";
                winner = 2;
            } else {
                info = "XYRule Error: Player 1 Win !!";
                winner = 1;
            }
        }

        if (!done)
            map[(int)currentPos.getX()][(int)currentPos.getY()] = turn;

        if (turn == 1)
            p1Time += time;
        else
            p2Time += time;

        if (isVictory(currentPos)) {
            done = true;
            if (turn == 1) {
                info = "Four Stone: Player 1 Win !!";
                winner = 1;
            } else {
                info = "Four Stone: Player 2 Win !!";
                winner = 2;
            }
        }

        if (isDraw(currentPos)) {
            done = true;
            if (p1Time <= p2Time) {
                info = "Draw: Player 1 Win !!";
                winner = 1;
            } else {
                info = "Draw: Player 2 Win !!";
                winner = 2;
            }
        }

        if (isActualEnv) {
            board.update(map, currentPos, p1Time, p2Time);
            board.repaint();

            if (done) {
                board.gameEnd(winner);
                board.repaint();
            }
        }

        lastPos = currentPos;

        if (turn == 1)
            turn = 2;
        else
            turn = 1;

        //printMap();

        return_list.add(done);
        return_list.add(info);
        return_list.add(winner);

        return return_list;
    }

    public boolean isIndexError(Point position) {
        int x = (int)position.getX();
        int y = (int)position.getY();
        if (x < 0 || x >= SIZE_OF_BOARD || y < 0 || y >= SIZE_OF_BOARD)
            return true;

        if (position.equals(new Point(0,0))
                || position.equals(new Point(7,7))
                || position.equals(new Point(0,7))
                || position.equals(new Point(7,0)))
            return true;

        return false;
    }

    public boolean isOverlapError(Point position) {
        int x = (int)position.getX();
        int y = (int)position.getY();
        if (map[x][y] != 0)
            return true;

        return false;
    }

    public boolean isXYRuleError(Point currentPos, Point lastPos) {
        if (currentPos.getX() != lastPos.getX() && currentPos.getY() != lastPos.getY())
            return true;

        return false;
    }

    public boolean isFour(Point position) {
        int x = (int)position.getX();
        int y = (int)position.getY();
        int count1 = 0, count2 = 0;

        for (int i = x - 3; i <= x + 3; i++) {
            if (i < 0 || i >= SIZE_OF_BOARD)
                continue;

            if (map[i][y] == turn) {
                count1++;
                if (count1 == 4)
                    return true;
            } else
                count1 = 0;
        }

        for (int i = y - 3; i <= y + 3; i++) {
            if (i < 0 || i >= SIZE_OF_BOARD)
                continue;

            if (map[x][i] == turn) {
                count2++;
                if (count2 == 4)
                    return true;
            } else
                count2 = 0;
        }

        count1 = 0;
        for (int i = -3; i <= 3; i++) {
            if (x + i < 0 || y + i < 0 || x + i >= SIZE_OF_BOARD || y + i >= SIZE_OF_BOARD)
                continue;

            if (map[x + i][y + i] == turn) {
                count1++;
                if (count1 == 4)
                    return true;
            } else
                count1 = 0;
        }

        count2 = 0;
        for (int i = -3; i <= 3; i++) {
            if (x + i < 0 || y - i < 0 || x + i >= SIZE_OF_BOARD || y - i >= SIZE_OF_BOARD)
                continue;

            if (map[x + i][y - i] == turn) {
                count2++;
                if (count2 == 4)
                    return true;
            } else
                count2 = 0;
        }

        return false;
    }

    public boolean isVictory(Point position) {
        boolean isFour = false;
        boolean isSquare = false;

        for (int i = 0; i < SIZE_OF_BOARD; i++)
            for (int j = 0; j < SIZE_OF_BOARD - 3; j++)
                if (map[i][j] == turn && map[i][j+1] == turn && map[i][j+2] == turn && map[i][j+3] == turn)
                    isFour = true;

        for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
            for (int j = 0; j < SIZE_OF_BOARD; j++)
                if (map[i][j] == turn && map[i+1][j] == turn && map[i+2][j] == turn && map[i+3][j] == turn)
                    isFour = true;

        for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
            for (int j = 0; j < SIZE_OF_BOARD - 3; j++)
                if (map[i][j] == turn && map[i+1][j+1] == turn && map[i+2][j+2] == turn && map[i+3][j+3] == turn)
                    isFour = true;

        for (int i = 0; i < SIZE_OF_BOARD - 3; i++)
            for (int j = 3; j < SIZE_OF_BOARD; j++)
                if (map[i][j] == turn && map[i+1][j-1] == turn && map[i+2][j-2] == turn && map[i+3][j-3] == turn)
                    isFour = true;

        for (int i = 0; i < SIZE_OF_BOARD - 1; i++)
            for (int j = 0; j < SIZE_OF_BOARD - 1; j++)
                if (map[i][j] == turn && map[i][j+1] == turn && map[i+1][j] == turn && map[i+1][j+1] == turn)
                    isSquare = true;

        return isFour && isSquare;
    }

    public boolean isDraw(Point position) {
        // All x and y of the current position are occupied
        int x, y, count = 0;

        x = (int)position.getX();
        y = (int)position.getY();
        for (int j = 0; j < SIZE_OF_BOARD; j++)
            if (map[x][j] == 0)
                count++;
        for (int i = 0; i < SIZE_OF_BOARD; i++)
            if (map[i][y] == 0)
                count++;

        if (count == 0)
            return true;

        return false;
    }

    public void printMap() {
        System.out.println();
        for (int i = 0; i < SIZE_OF_BOARD; i++) {
            System.out.print("\t\t");
            for (int j = 0; j < SIZE_OF_BOARD; j++)
                if (map[i][j] == -1)
                    System.out.print("  ");
                else if (map[i][j] == 0)
                    System.out.print("  ");
                else if (map[i][j] == 1)
                    System.out.print(" 1");
                else if (map[i][j] == 2)
                    System.out.print(" 2");
            System.out.println();
        }
        System.out.println();
    }
}