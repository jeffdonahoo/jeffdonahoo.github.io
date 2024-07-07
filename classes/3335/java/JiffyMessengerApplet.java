import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class JiffyMessengerApplet extends JApplet {
  private JTextArea conversation;
  private JTextField xmitText;
  private String name;
  private boolean done = false;

  public void init() {
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
          conversation.append(name + "> " + xmitText.getText().trim() + "\n");
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

    menuBar.add(file);
    setJMenuBar(menuBar);
    newName();
  }

  private void newName() {
    name = JOptionPane.showInputDialog("Enter Name: ");
    if ((name == null) || (name.length() < 1)) {
      name = "Unknown";
    }
    conversation.append(name + " entered\n");
  }
}