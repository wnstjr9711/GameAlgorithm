import java.awt.*;
import java.util.Random;

public class Player_Example6 extends Player {
   public Player_Example6(int myNum, int SIZE_OF_BOARD, int[][] map) {
      super(myNum, SIZE_OF_BOARD, map);
   }

   public Point nextPosition(Point lastPosition) {
      Point nextPosition = new Point(0, 0);
      int last_x = (int) lastPosition.getX(), last_y = (int) lastPosition.getY(); // 마지막 x와 y 좌표

      int[] search1 = new int[8]; // 마지막 좌표의 주변 8칸의 상대 돌 상태
      int[] search2 = new int[8]; // 마지막 좌표의 주변 8칸의 우리 돌 상태

      int enemy; // 상대 돌
      
      int flag = 0;
      
      Random random = new Random();
      int xy;
      int index;

      if (myNum == 1) // 내가 파란색이면 상대는 빨간색
         enemy = 2;
      else // 내가 빨간색이면 상대는 파란색
         enemy = 1;

      // 마지막 좌표를 기준으로 주변 8칸의 상태 확인하기.
      // 1이면 상대 돌이 있음.
      if (last_x == 0) { // x가 0일 때는 위로 갈 수 없다.
         flag = 1;

         if (map[last_x][last_y - 1] == enemy)
            search1[1] = 1;

         if (map[last_x + 1][last_y - 1] == enemy)
            search1[2] = 1;

         if (map[last_x + 1][last_y] == enemy)
            search1[3] = 1;

         if (map[last_x + 1][last_y + 1] == enemy)
            search1[4] = 1;

         if (map[last_x][last_y + 1] == enemy)
            search1[5] = 1;
      } else if (last_x == 7) { // x가 7일 때는 아래로 갈 수 없다.
         flag = 2;

         if (map[last_x - 1][last_y - 1] == enemy)
            search1[0] = 1;

         if (map[last_x][last_y - 1] == enemy)
            search1[1] = 1;

         if (map[last_x][last_y + 1] == enemy)
            search1[5] = 1;

         if (map[last_x - 1][last_y + 1] == enemy)
            search1[6] = 1;

         if (map[last_x - 1][last_y] == enemy)
            search1[7] = 1;
      } else if (last_y == 0) { // y가 0일 때 왼쪽으로 갈 수 없다.
         flag = 3;

         if (map[last_x + 1][last_y] == enemy)
            search1[3] = 1;

         if (map[last_x + 1][last_y + 1] == enemy)
            search1[4] = 1;

         if (map[last_x][last_y + 1] == enemy)
            search1[5] = 1;

         if (map[last_x - 1][last_y + 1] == enemy)
            search1[6] = 1;

         if (map[last_x - 1][last_y] == enemy)
            search1[7] = 1;
      } else if (last_y == 7) { // y가 7일 때는 오른쪽으로 갈 수없다.
         flag = 4;

         if (map[last_x - 1][last_y - 1] == enemy)
            search1[0] = 1;

         if (map[last_x][last_y - 1] == enemy)
            search1[1] = 1;

         if (map[last_x + 1][last_y - 1] == enemy)
            search1[2] = 1;

         if (map[last_x + 1][last_y] == enemy)
            search1[3] = 1;

         if (map[last_x - 1][last_y] == enemy)
            search1[7] = 1;
      } else { // x와 y가 0 또느 7이 아닌 경우 ( 중앙 )
         
         
         if (map[last_x - 1][last_y - 1] == enemy)
            search1[0] = 1;

         if (map[last_x][last_y - 1] == enemy)
            search1[1] = 1;

         if (map[last_x + 1][last_y - 1] == enemy)
            search1[2] = 1;

         if (map[last_x + 1][last_y] == enemy)
            search1[3] = 1;

         if (map[last_x + 1][last_y + 1] == enemy)
            search1[4] = 1;

         if (map[last_x][last_y + 1] == enemy)
            search1[5] = 1;

         if (map[last_x - 1][last_y + 1] == enemy)
            search1[6] = 1;

         if (map[last_x - 1][last_y] == enemy)
            search1[7] = 1;
      }

      if (last_x == 0) { // x가 0일 때는 위로 갈 수 없다.

         if (map[last_x][last_y - 1] == myNum)
            search2[1] = 1;

         if (map[last_x + 1][last_y - 1] == myNum)
            search2[2] = 1;

         if (map[last_x + 1][last_y] == myNum)
            search2[3] = 1;

         if (map[last_x + 1][last_y + 1] == myNum)
            search2[4] = 1;

         if (map[last_x][last_y + 1] == myNum)
            search2[5] = 1;
      } else if (last_x == 7) { // x가 7일 때는 아래로 갈 수 없다.

         if (map[last_x - 1][last_y - 1] == myNum)
            search2[0] = 1;

         if (map[last_x][last_y - 1] == myNum)
            search2[1] = 1;

         if (map[last_x][last_y + 1] == myNum)
            search2[5] = 1;

         if (map[last_x - 1][last_y + 1] == myNum)
            search2[6] = 1;

         if (map[last_x - 1][last_y] == myNum)
            search2[7] = 1;
      } else if (last_y == 0) { // y가 0일 때 왼쪽으로 갈 수 없다.

         if (map[last_x + 1][last_y] == myNum)
            search2[3] = 1;

         if (map[last_x + 1][last_y + 1] == myNum)
            search2[4] = 1;

         if (map[last_x][last_y + 1] == myNum)
            search2[5] = 1;

         if (map[last_x - 1][last_y + 1] == myNum)
            search2[6] = 1;

         if (map[last_x - 1][last_y] == myNum)
            search2[7] = 1;
      } else if (last_y == 7) { // y가 7일 때는 오른쪽으로 갈 수없다.

         if (map[last_x - 1][last_y - 1] == myNum)
            search2[0] = 1;

         if (map[last_x][last_y - 1] == myNum)
            search2[1] = 1;

         if (map[last_x + 1][last_y - 1] == myNum)
            search2[2] = 1;

         if (map[last_x + 1][last_y] == myNum)
            search2[3] = 1;

         if (map[last_x - 1][last_y] == myNum)
            search2[7] = 1;
      } else { // x와 y가 0 또느 7이 아닌 경우 ( 중앙 )
         
         
         if (map[last_x - 1][last_y - 1] == myNum)
            search2[0] = 1;

         if (map[last_x][last_y - 1] == myNum)
            search2[1] = 1;

         if (map[last_x + 1][last_y - 1] == myNum)
            search2[2] = 1;

         if (map[last_x + 1][last_y] == myNum)
            search2[3] = 1;

         if (map[last_x + 1][last_y + 1] == myNum)
            search2[4] = 1;

         if (map[last_x][last_y + 1] == myNum)
            search2[5] = 1;

         if (map[last_x - 1][last_y + 1] == myNum)
            search2[6] = 1;

         if (map[last_x - 1][last_y] == myNum)
            search2[7] = 1;
      }

      while (true) {

         // ㄱ자 모양인지 검사하고 2x2 모양인 ㅁ이 될 수 없게 만들어 준다.
         if (search1[0] == 1 && search1[1] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search1[1] == 1 && search1[2] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search1[2] == 1 && search1[3] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search1[3] == 1 && search1[4] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search1[4] == 1 && search1[5] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search1[5] == 1 && search1[6] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search1[6] == 1 && search1[7] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search1[7] == 1 && search1[0] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search1[0] == 1 && search1[2] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search1[2] == 1 && search1[4] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search1[4] == 1 && search1[6] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search1[6] == 1 && search1[0] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         // ㄱ자를 만들어지는 것을 예방.
         if (search1[0] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search1[1] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search1[2] == 1) {
            if (flag != 1 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search1[3] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search1[4] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search1[5] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            } else if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search1[6] == 1) {
            if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search1[7] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         // ㄱ자 모양인지 검사하고 2x2 모양인 ㅁ이 될 수 없게 만들어 준다.
         if (search2[0] == 1 && search2[1] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search2[1] == 1 && search2[2] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search2[2] == 1 && search2[3] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search2[3] == 1 && search2[4] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search2[4] == 1 && search2[5] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search2[5] == 1 && search2[6] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search2[6] == 1 && search2[7] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search2[7] == 1 && search2[0] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search2[0] == 1 && search2[2] == 1) {
            if (map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search2[2] == 1 && search2[4] == 1) {
            if (map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search2[4] == 1 && search2[6] == 1) {
            if (map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search2[6] == 1 && search2[0] == 1) {
            if (map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         // ㄱ자를 만들어지는 것을 예방.
         if (search2[0] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search2[1] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search2[2] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }

         if (search2[3] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search2[4] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            }
         }

         if (search2[5] == 1) {
            if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            } else if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            }
         }

         if (search2[6] == 1) {
            if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 1 && map[last_x - 1][last_y] == 0) {
               nextPosition = new Point(last_x - 1, last_y);
               break;
            }
         }

         if (search2[7] == 1) {
            if (flag != 3 && map[last_x][last_y - 1] == 0) {
               nextPosition = new Point(last_x, last_y - 1);
               break;
            } else if (flag != 4 && map[last_x][last_y + 1] == 0) {
               nextPosition = new Point(last_x, last_y + 1);
               break;
            } else if (flag != 2 && map[last_x + 1][last_y] == 0) {
               nextPosition = new Point(last_x + 1, last_y);
               break;
            }
         }
         
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