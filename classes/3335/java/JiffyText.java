import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class JiffyText extends JFrame {
  private JTextArea conversation;
  private JTextField xmitText;
  private Messenger messenger;
  private boolean done = false;

  public JiffyText() {
    super("Jiffy Messenger");
    setSize(200, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    try {
      messenger = new Messenger();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Unable to connect", "Connect Error",
        JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }

    conversation = new JTextArea(8, Messenger.MAXMESSAGE);
    conversation.setEditable(false);
    getContentPane().add(conversation, "Center");

    xmitText = new JTextField(Messenger.MAXMESSAGE);
    getContentPane().add(xmitText, "North");

    JButton sendButton = new JButton("Send");         // Button
    getContentPane().add(sendButton, "South");

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

    new Thread(
      new Runnable() {
        public void run() {
          recvMessages();
        }
      }
    ).start();
  }

  private void recvMessages() {
    for (;;) {
      try {
        String msg = messenger.recv();
        conversation.append(msg + "\n");
      } catch (IOException e) {
        if (done) {
          return;    // Received error due to normal termination
        } else {
          JOptionPane.showMessageDialog(null, "Unable to receive",
            "Receive Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }

  public static void main(String[] args) {
    JiffyText msgr = new JiffyText();
    msgr.setVisible(true);
  }
}