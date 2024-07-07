class Hello {

  public native void printGreeting();

  static {
    System.loadLibrary("hello");
  }

  public static void main(String[] args) {
    Hello hello = new Hello();
    hello.printGreeting();
  }
}
