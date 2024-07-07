import java.io.IOException;
import javax.swing.*;
import java.awt.event.*;

public class JiffyField extends JFrame {
  private JTextField xmitText;
  private Messenger messenger;

  public JiffyField() {
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

    xmitText = new JTextField(Messenger.MAXMESSAGE);
    getContentPane().add(xmitText, "North");

    JButton sendButton = new JButton("Send");         // Button
    getContentPane().add(sendButton, "Center");

    sendButton.addActionListener(   // Button event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            messenger.send(xmitText.getText().trim());
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Unable to send", "Send Error",
              JOptionPane.ERROR_MESSAGE);
          }
          xmitText.setText("");
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
    JiffyField msgr = new JiffyField();
    msgr.setVisible(true);
  }
}
