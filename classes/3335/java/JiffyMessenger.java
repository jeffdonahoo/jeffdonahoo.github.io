import java.io.IOException;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class JiffyMessenger extends JFrame {
    private JTextArea conversation;
    private JTextField xmitText;
    private Messenger messenger;
    private String name;
    private final static String APPNAME = "Jiffy Messenger";
    private boolean done = false;

    public JiffyMessenger() {
	super(APPNAME);
	setSize(200, 300);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setIconImage(new ImageIcon("icon.gif").getImage());

	// Set up communication endpoint
	try {
	    messenger = new Messenger();
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, "Unable to connect",
		    "Connect Error", JOptionPane.ERROR_MESSAGE);
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

	xmitText = new JTextField(Messenger.MAXMESSAGE); // Message
	panel.add(xmitText, BorderLayout.CENTER);

	Action sendAction = new AbstractAction() {
	    public void actionPerformed(ActionEvent e) {
		try {
		    messenger.send(name + "> "
			    + xmitText.getText().trim());
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(null,
			    "Unable to send", "Send Error",
			    JOptionPane.ERROR_MESSAGE);
		}
		xmitText.setText("");
	    }

	    {
		putValue(Action.NAME, "Send");
		putValue(Action.SHORT_DESCRIPTION, "Sends message");
		putValue(Action.SMALL_ICON, new ImageIcon("send.gif"));
	    }
	};
	
	JButton sendButton = new JButton(sendAction); // Button
	panel.add(sendButton, BorderLayout.EAST);

	getContentPane().add(panel, BorderLayout.SOUTH); // Add panel to frame

	//****************************************
	// Construct menu
	//****************************************
	JMenuBar menuBar = new JMenuBar(); // Create menubar

	JMenu file = new JMenu("File"); // Create file menu

	JMenuItem changeName = new JMenuItem("Change Name..."); // Change Name...
	file.add(changeName);
	changeName.addActionListener( // Change Name... event handler
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			newName();
		    }
		});

	file.add(new JMenuItem(sendAction));
	
	file.addSeparator();

	JMenuItem exit = new JMenuItem("Exit"); // Exit
	file.add(exit);
	exit.addActionListener( // Exit event handler
		new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
			exit();
		    }
		});

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

	new Thread(new Runnable() {
	    public void run() {
		recvMessages();
	    }
	}).start();
    }

    private void exit() {
	try {
	    done = true;
	    messenger.send(name + " left");
	    messenger.close();
	} catch (Exception exception) {
	}
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

    private void recvMessages() {
	for (;;) {
	    try {
		String msg = messenger.recv();
		conversation.append(msg + "\n");
	    } catch (IOException e) {
		if (done) {
		    return; // Received error due to normal termination
		} else {
		    JOptionPane.showMessageDialog(null, "Unable to receive",
			    "Receive Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	}
    }

    public static void main(String[] args) {
	JiffyMessenger msgr = new JiffyMessenger();
	msgr.setVisible(true);
    }
}
