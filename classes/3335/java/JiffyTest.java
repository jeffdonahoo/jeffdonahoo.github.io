import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class JiffyTest extends JFrame {
  private Messenger messenger;
  private JTextField xmitText;

  public JiffyTest() {
    super("Jiffy Messenger");
    setSize(300, 400);

    try {
      messenger = new Messenger();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Unable to connect", "Connect Error",
        JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }

    JButton sendButton = new JButton("Send");
    getContentPane().add(sendButton);

    xmitText  = new JTextField();
    getContentPane().add(xmitText, "North");
    sendButton.addActionListener(   // Button event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            messenger.send(xmitText.getText().trim());
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
        System.exit(0);
      }
    });
  }

  public static void main(String[] args) {
    JiffyTest msgr = new JiffyTest();
    msgr.setVisible(true);
  }
}