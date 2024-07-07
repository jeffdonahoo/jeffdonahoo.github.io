import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.*;

public class JiffyButton1 extends JFrame {
  private Messenger messenger;

  public JiffyButton1() throws IOException {
    super("Jiffy Messenger");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    messenger = new Messenger();

    JButton sendButton = new JButton("Send Hello");
    getContentPane().add(sendButton);
    sendButton.addActionListener(new ButtonAction(messenger));

    addWindowListener(new WindowAction(messenger));
  }

  public static void main(String[] args) throws IOException {
    JiffyButton1 msgr = new JiffyButton1();
    msgr.setVisible(true);
  }
}

class ButtonAction implements ActionListener {
  private Messenger messenger;

  public ButtonAction(Messenger messenger) {
    this.messenger = messenger;
  }

  public void actionPerformed(ActionEvent event) {
    try {
      messenger.send("Hello");
    } catch (IOException e) {
      System.exit(1);
    }
  }
}

class WindowAction implements WindowListener {
  private Messenger messenger;

  public WindowAction(Messenger messenger) {
    this.messenger = messenger;
  }


  public void windowClosing(WindowEvent e) {
    messenger.close();
  }

  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
}
