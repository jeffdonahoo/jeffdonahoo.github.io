public class ProgEx2 {

  public static void main(String args[]) {

    int nums[] = createArray(args);
    sortArray(nums);
    printArray(nums);
  }

  public static int [] createArray(String nums[]) {
    int rtn[] = new int[nums.length];
    for (int i = 0; i < nums.length; i++)
      rtn[i] = Integer.parseInt(nums[i]);
    return rtn;
  }

  public static void sortArray(int nums[]) {
    for (int iteration = 0; iteration < nums.length; iteration++)
      for (int elmt = 0; elmt < nums.length-1; elmt++)
        if (nums[elmt] > nums[elmt+1]) {
          int tmp = nums[elmt];
          nums[elmt] = nums[elmt+1];
          nums[elmt+1] = tmp;
        }
  }

  public static void printArray(int nums[]) {
    for (int elmt = 0; elmt < nums.length; elmt++)
      System.out.println(nums[elmt]);
  }
}
