import java.io.IOException;
import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class JiffyMess extends JFrame {
  private JTextArea conversation;
  private JTextField xmitText;
  private Messenger messenger;
  private String name;
  private final static String APPNAME = "Jiffy Messenger";

  public JiffyMess() {
    super(APPNAME);
    setSize(200, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Set up communication endpoint
    try {
      messenger = new Messenger();
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Unable to connect", "Connect Error",
        JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }

    //****************************************
    // Construct frame contents
    //****************************************

    // Conversation area
    conversation = new JTextArea(8, Messenger.MAXMESSAGE);
    conversation.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(conversation);
    getContentPane().add(scrollPane, BorderLayout.CENTER);

    // Chat entry area
    JPanel panel = new JPanel(new BorderLayout());

    xmitText = new JTextField(Messenger.MAXMESSAGE);   // Message
    panel.add(xmitText, BorderLayout.CENTER);

    JButton sendButton = new JButton("Send");         // Button
    panel.add(sendButton, BorderLayout.EAST);

    sendButton.addActionListener(   // Button event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          try {
            messenger.send(name + "> " + xmitText.getText().trim());
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Unable to send", "Send Error",
              JOptionPane.ERROR_MESSAGE);
          }
          xmitText.setText("");
        }
      }
    );

    getContentPane().add(panel, BorderLayout.SOUTH);  // Add panel to frame

    //****************************************
    // Construct menu
    //****************************************
    JMenuBar menuBar = new JMenuBar();  // Create menubar

    JMenu file = new JMenu("File");     // Create file menu

    JMenuItem changeName = new JMenuItem("Change Name...");  // Change Name...
    file.add(changeName);
    changeName.addActionListener(   // Change Name... event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          newName();
        }
      }
    );

    file.addSeparator();

    JMenuItem exit = new JMenuItem("Exit");               // Exit
    file.add(exit);
    exit.addActionListener(   // Exit event handler
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          exit();
        }
      }
    );

    menuBar.add(file);
    setJMenuBar(menuBar);

    //************************************************
    // Handle application start and stop
    //************************************************
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        exit();
      }
      public void windowOpened(WindowEvent e) {
        newName();
      }
    });
  }

  private void exit() {
    try {
      messenger.send(name + " left");
      messenger.close();
    } catch (Exception exception) {}
  }

  private void newName() {
    name = JOptionPane.showInputDialog("Enter Name: ");
    if ((name == null) || (name.length() < 1)) {
      name = "Unknown";
    }
    try {
      messenger.send(name + " entered");
      setTitle(APPNAME + " - " + name);
    } catch (IOException e1) {
      JOptionPane.showMessageDialog(null, "Unable to send", "Send Error",
        JOptionPane.ERROR_MESSAGE);
    }
  }

  public static void main(String[] args) {
    JiffyMess msgr = new JiffyMess();
    msgr.setVisible(true);
  }
}
