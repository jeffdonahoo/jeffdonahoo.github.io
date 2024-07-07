public class IntegerArray {

  public static void main(String[] args) {
    Integer[][] arr = new Integer[3][];
    for (int row = 0; row < 3; row++) {
      arr[row] = new Integer[row];
      for (int col = 0; col < row; col++) {
        arr[row][col] = new Integer(row + col);
      }
    }

    Integer[][] arr2 = {{}, {new Integer(1)}, {new Integer(2), new Integer(3)}};
  }
}