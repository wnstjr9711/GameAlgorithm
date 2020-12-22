import java.awt.*;
import java.util.Random;

public class Player_Example6 extends Player {
   public Player_Example6(int myNum, int SIZE_OF_BOARD, int[][] map) {
      super(myNum, SIZE_OF_BOARD, map);
   }

   public Point nextPosition(Point lastPosition) {
      Point nextPosition = new Point(0, 0);
      int last_x = (int) lastPosition.getX(), last_y = (int) lastPosition.getY(); // ������ x�� y ��ǥ

      int[] search1 = new int[8]; // ������ ��ǥ�� �ֺ� 8ĭ�� ��� �� ����
      int[] search2 = new int[8]; // ������ ��ǥ�� �ֺ� 8ĭ�� �츮 �� ����

      int enemy; // ��� ��
      
      int flag = 0;
      
      Random random = new Random();
      int xy;
      int index;

      if (myNum == 1) // ���� �Ķ����̸� ���� ������
         enemy = 2;
      else // ���� �������̸� ���� �Ķ���
         enemy = 1;

      // ������ ��ǥ�� �������� �ֺ� 8ĭ�� ���� Ȯ���ϱ�.
      // 1�̸� ��� ���� ����.
      if (last_x == 0) { // x�� 0�� ���� ���� �� �� ����.
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
      } else if (last_x == 7) { // x�� 7�� ���� �Ʒ��� �� �� ����.
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
      } else if (last_y == 0) { // y�� 0�� �� �������� �� �� ����.
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
      } else if (last_y == 7) { // y�� 7�� ���� ���������� �� ������.
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
      } else { // x�� y�� 0 �Ǵ� 7�� �ƴ� ��� ( �߾� )
         
         
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

      if (last_x == 0) { // x�� 0�� ���� ���� �� �� ����.

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
      } else if (last_x == 7) { // x�� 7�� ���� �Ʒ��� �� �� ����.

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
      } else if (last_y == 0) { // y�� 0�� �� �������� �� �� ����.

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
      } else if (last_y == 7) { // y�� 7�� ���� ���������� �� ������.

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
      } else { // x�� y�� 0 �Ǵ� 7�� �ƴ� ��� ( �߾� )
         
         
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

         // ���� ������� �˻��ϰ� 2x2 ����� ���� �� �� ���� ����� �ش�.
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

         // ���ڸ� ��������� ���� ����.
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

         // ���� ������� �˻��ϰ� 2x2 ����� ���� �� �� ���� ����� �ش�.
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

         // ���ڸ� ��������� ���� ����.
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