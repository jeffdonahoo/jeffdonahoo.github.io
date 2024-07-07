import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.*;

public class JiffyButton3 extends JFrame {
  private Messenger messenger;

  public JiffyButton3() {
    super("Jiffy Messenger");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    try {
      messenger = new Messenger();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Unable to connect", "Connect Error",
        JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }

    JButton sendButton = new JButton("Send Hello");
    getContentPane().add(sendButton);
    sendButton.addActionListener(   // Button event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            messenger.send("Hello");
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Unable to send", "Send Error",
              JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    );

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        messenger.close();
      }
    });
  }

  public static void main(String[] args) {
    JiffyButton3 msgr = new JiffyButton3();
    msgr.setVisible(true);
  }
}
