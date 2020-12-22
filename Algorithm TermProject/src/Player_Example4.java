import java.awt.*;

public class Player_Example4 extends Player {
    public Player_Example4(int myNum, int SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }

    public Point nextPosition(Point lastPosition) {
        Point nextPosition;

        int last_point_x=(int)lastPosition.getX();
        int last_point_y=(int)lastPosition.getY();


        nextPosition =go_test(last_point_x,last_point_y);


        return nextPosition;
    }
    public Point go_test(int last_x, int last_y) {
        int check_left=10, check_right=10, check_top=10, check_bottom=10;
        int choose_x,choose_y;

        if(last_y!=0)check_left=map[last_x][(last_y-1)];
        if(last_y!=7)check_right=map[last_x][(last_y+1)];

        if(last_x!=0)check_top=map[last_x-1][last_y];
        if(last_x!=7)check_bottom=map[(last_x+1)][last_y];


        if(check_left==0) {
            choose_x=last_x;
            choose_y=last_y-1;
            return new Point(choose_x,choose_y);
        }
        else if(check_right==0) {
            choose_x=last_x;
            choose_y=last_y+1;
            return new Point(choose_x, choose_y);
        }
        else if(check_bottom==0) {
            choose_x=last_x+1;
            choose_y=last_y;
            return new Point(choose_x, choose_y);
        }
        else if(check_top==0) {
            choose_x=last_x-1;
            choose_y=last_y;
            return new Point(choose_x, choose_y);
        }
        else {
            for(int i=0;i<8;i++) {
                if(map[last_x][i]==0) {
                    choose_y=i;
                    return new Point(last_x,choose_y);

                }
            }
            for(int i=0;i<8;i++) {
                if(map[i][last_y]==0) {
                    choose_x=i;
                    return new Point(choose_x,last_y);

                }

            }
            for(int i=0;i<8;i++) {
                for(int j=0;j<8;j++) {
                    if(map[i][j]==0){
                        choose_x=i;
                        choose_y=j;
                        return new Point(choose_x, choose_y);}
                }
            }


        }
        return new Point(choose_x=4,choose_y=4);
    }
}