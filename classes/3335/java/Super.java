class Super {
  void A() { System.out.println("A in Super");}
  void B() { System.out.println("B in Super");}
  public static void main(String[] args) {
    Super x = new Sub();
    Super y;
    Super z = new Super();

    x.B(); z.B(); x.A();
    ((Sub)x).C();
  }
}

class Sub extends Super {
  void B() { System.out.println("B in Sub");}
  void C() { System.out.println("C in Sub");}
}