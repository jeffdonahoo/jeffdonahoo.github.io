import javax.swing.JFrame;
import javax.swing.JButton;

public class JiffyButton0 extends JFrame {

  public JiffyButton0() {
    super("Jiffy Messenger");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JButton sendButton = new JButton("Send Hello");
    getContentPane().add(sendButton);
  }

  public static void main(String[] args) {
    JiffyButton0 msgr = new JiffyButton0();
    msgr.setVisible(true);
  }
}
