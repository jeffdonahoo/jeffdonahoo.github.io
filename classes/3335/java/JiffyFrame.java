import javax.swing.JFrame;

public class JiffyFrame extends JFrame {

  public JiffyFrame() {
    super("Jiffy Messenger");
    setSize(300, 400);         // Default size is 0, 0
  }

  public static void main(String[] args) {
    JiffyFrame msgr = new JiffyFrame();
    // Default HIDE_ON_CLOSE
    msgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    msgr.setVisible(true);    // Default is invisible
  }
}
