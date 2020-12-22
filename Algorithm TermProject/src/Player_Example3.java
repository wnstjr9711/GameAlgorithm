import java.awt.*;

public class Player_Example3 extends Player {
   public Player_Example3(int myNum, int SIZE_OF_BOARD, int[][] map) { super(myNum, SIZE_OF_BOARD, map); }

   public Point nextPosition(Point lastPosition) {
      Point nextPosition = new Point(0, 0);
      int last_x = (int) lastPosition.getX();
      int last_y = (int) lastPosition.getY();

      int[][] map2 = new int[SIZE_OF_BOARD][SIZE_OF_BOARD];

      for(int i = 0; i< SIZE_OF_BOARD; i++){
         for(int j = 0 ; j < SIZE_OF_BOARD; j++){
            if(map[i][j] == 1)
               if (map[i][j] == 1) {
                  map2[i][j] = 1;
                  check_num_1(i,j,map2);
               }
            if(map[i][j] == 2) {
                  map2[i][j] = 2;
                  check_num_2(i, j, map2);
               }
         }
      }

      int max = 3;
      int x = 0, y = 0;


      for (int i = 0; i < SIZE_OF_BOARD; i++) {
         if(map2[last_x][i] >= max && map2[last_x][i] > 2 && RULE_OVER(last_x,i)) {
            max = map2[last_x][i];
            x = last_x;
            y = i;
         }
         if(map2[i][last_y] >= max && map2[i][last_y] > 2 && RULE_OVER(i,last_y)) {
            max = map2[i][last_y];
            x = i;
            y = last_y;
         }
      }

      nextPosition.setLocation(x,y);

      return nextPosition;
   }

   public boolean is_ok(int x, int y){
      if (x >= 0 && y >= 0 && x < SIZE_OF_BOARD && y < SIZE_OF_BOARD)
         if(RULE_OVER(x,y))
            return true;
      return false;
   }

   public boolean RULE_OVER(int x, int y){
      if ( x == 0 || x >= SIZE_OF_BOARD -1 )
         if( y == 0 || y >= SIZE_OF_BOARD -1 )
            return false;
      return true;
   }


   public int[][] check_num_1(int i, int j, int[][] map2){
      if(is_ok(i-1,j-1) && map2[i-1][j-1] == 0 )
         map2[i-1][j-1] = 3;
      else if(is_ok(i-1,j-1) && map2[i-1][j-1] > 2 )
         map2[i-1][j-1] += 3;
      if(is_ok(i-1,j) && map2[i-1][j] == 0 )
         map2[i-1][j] = 3;
      else if(is_ok(i-1,j) && map2[i-1][j] > 2   )
         map2[i-1][j] += 3;
      if( is_ok(i-1,j+1) && map2[i-1][j+1] == 0 )
         map2[i-1][j+1] = 3;
      else if(is_ok(i-1,j+1) && map2[i-1][j+1] > 2   )
         map2[i-1][j+1] += 3;
      if(is_ok(i,j-1) && map2[i][j-1] == 0  )
         map2[i][j-1] = 3;
      else if(is_ok(i,j-1) && map2[i][j-1] > 2   )
         map2[i][j-1] += 3;
      if(is_ok(i,j+1) && map2[i][j+1] == 0  )
         map2[i][j+1] = 3;
      else if(is_ok(i,j+1) && map2[i][j+1] > 2   )
         map2[i][j+1] += 3;
      if(is_ok(i+1,j-1) && map2[i+1][j-1] == 0  )
         map2[i+1][j-1] = 3;
      else if(is_ok(i+1,j-1) && map2[i+1][j-1] > 2  )
         map2[i+1][j-1] += 3;
      if(is_ok(i+1,j) && map2[i+1][j] == 0 )
         map2[i+1][j] = 3;
      else if(is_ok(i+1,j) &&  map2[i+1][j] > 2)
         map2[i+1][j] += 3;
      if(is_ok(i+1,j+1) && map2[i+1][j+1] == 0 )
         map2[i+1][j+1] = 3;
      else if(is_ok(i+1,j+1) && map2[i+1][j+1] > 2 )
         map2[i+1][j+1] += 3;
      return map2;
   }
   public int[][] check_num_2(int i, int j, int[][] map2) {
      if (is_ok(i-1,j-1) && map2[i - 1][j - 1] == 0  )
         map2[i - 1][j - 1] = 4;
      else if (is_ok(i-1,j-1) && map2[i - 1][j - 1] > 2  )
         map2[i - 1][j - 1] += 4;
      if (is_ok(i-1,j) && map2[i - 1][j] == 0  )
         map2[i - 1][j] = 4;
      else if (is_ok(i-1,j) && map2[i - 1][j] > 2 )
         map2[i - 1][j] += 4;
      if (is_ok(i-1,j+1) && map2[i - 1][j + 1] == 0)
         map2[i - 1][j + 1] = 4;
      else if (is_ok(i-1,j+1) && map2[i - 1][j + 1] > 2  )
         map2[i - 1][j + 1] += 4;
      if (is_ok(i,j-1) &&  map2[i][j - 1] == 0 )
         map2[i][j - 1] = 4;
      else if (is_ok(i,j-1) && map2[i][j - 1] > 2  )
         map2[i][j - 1] += 4;
      if (is_ok(i,j+1) &&  map2[i][j + 1] == 0 )
         map2[i][j + 1] = 4;
      else if (is_ok(i,j+1) && map2[i][j + 1] > 2  )
         map2[i][j + 1] += 4;
      if (is_ok(i+1,j-1) && map2[i + 1][j - 1] == 0  )
         map2[i + 1][j - 1] = 4;
      else if (is_ok(i+1,j-1) && map2[i + 1][j - 1] > 2  )
         map2[i + 1][j - 1] += 4;
      if (is_ok(i+1,j) && map2[i + 1][j] == 0  )
         map2[i + 1][j] = 4;
      else if (is_ok(i+1,j) && map2[i + 1][j] > 2  )
         map2[i + 1][j] += 4;
      if (is_ok(i+1,j+1)  && map2[i + 1][j + 1] == 0 )
         map2[i + 1][j + 1] = 4;
      else if (is_ok(i+1,j+1)  &&  map2[i + 1][j + 1] > 2)
         map2[i + 1][j + 1] += 4;
      return map2;
   }
}