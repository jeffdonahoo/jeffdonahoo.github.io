import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;

public class JiffyButton2 extends JFrame {
  private Messenger messenger;

  public JiffyButton2() throws IOException {
    super("Jiffy Messenger");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    messenger = new Messenger();

    JButton sendButton = new JButton("Send Hello");
    getContentPane().add(sendButton);
    sendButton.addActionListener(new ButtonAction());

    addWindowListener(new WindowAction());
  }

  class ButtonAction implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      try {
        messenger.send("Hello");   // Messenger in enclosing class
      } catch (IOException e) {
        System.exit(1);
      }
    }
  }

  class WindowAction extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      messenger.close();
    }
  }

  public static void main(String[] args) throws IOException {
    JiffyButton2 msgr = new JiffyButton2();
    msgr.setVisible(true);
  }
}
