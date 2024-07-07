import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.net.Inet4Address;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

public class AddNeighbor extends JDialog {
  GridBagLayout layout = new GridBagLayout();
  JLabel jLabelAddN = new JLabel();
  JLabel jLabelIP = new JLabel();
  JLabel jLabelPort = new JLabel();
  JTextField jTextFieldIP = new JTextField();
  JTextField jTextFieldPort = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  Component component1;
  String ipString = "";
  int port = 0;
  FreeOxyUI callback = null;
  public AddNeighbor(FreeOxyUI foUI) throws HeadlessException {
    callback = foUI;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    Dimension textFieldSize = new Dimension(200,21);
    component1 = Box.createHorizontalStrut(8);
//    jTextFieldPort.setNextFocusableComponent(jTextFieldIP);
    jTextFieldPort.setHorizontalAlignment(SwingConstants.LEFT);
    jTextFieldPort.setMaximumSize(new Dimension(2147483647, 2147483647));
    jTextFieldPort.setMinimumSize((Dimension)textFieldSize.clone());
    jLabelAddN.setHorizontalAlignment(SwingConstants.LEFT);
    jLabelAddN.setText("Add a neighbor");
    jLabelAddN.setVerticalAlignment(SwingConstants.TOP);
    jLabelAddN.setVerticalTextPosition(SwingConstants.TOP);
    this.getContentPane().setLayout(layout);
    jLabelIP.setText("IP Address");
    jLabelPort.setText("Port");
    jTextFieldIP.setMinimumSize((Dimension)textFieldSize.clone());
//    jTextFieldIP.setNextFocusableComponent(jTextFieldPort);
    jTextFieldIP.setToolTipText("Internet address of neighbor to add");
    jTextFieldIP.setHorizontalAlignment(SwingConstants.LEFT);
    this.getContentPane().setBackground(SystemColor.control);
    jButtonOK.setText("Add Host");
    jButtonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        // read values from IP and Port fields
        Inet4Address ip;
        int port = 0;
        try {
          String s = jTextFieldIP.getText();
          if( s.length() < 7 ) {
            MessageBox mb = new MessageBox();
            mb.askOkay("Please enter a valid IP");
          }
          ip = (Inet4Address) Inet4Address.getByName(s);
          port = (int) Integer.parseInt(jTextFieldPort.getText());
        }
        catch( java.net.UnknownHostException uhe) {
          MessageBox mb = new MessageBox();
          String s = jTextFieldIP.getText();
          s += " is not a valid IP -- please make corrections";
          mb.askOkay(s);
          return;
        }
        catch( java.lang.NumberFormatException nfe ) {
          MessageBox mb = new MessageBox();
          mb.askOkay("Please correct the error in the port number");
          return;
        }
        ipString = ip.getHostAddress();
        setHost(ipString,port);
      }
    });
    jButtonCancel.setText("Cancel");
    jButtonCancel.addActionListener(new KillDialog(this));
    this.getContentPane().add(jButtonOK,           new GridBagConstraints(1, 4, 3, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jButtonCancel,          new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jLabelIP,          new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jLabelPort,          new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(component1,      new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jLabelAddN,     new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 13), 0, 0));
    this.getContentPane().add(jTextFieldPort,     new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 20, 0));
    this.getContentPane().add(jTextFieldIP,    new GridBagConstraints(4, 2, 2, 1, 0.0, 0.0
            ,GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 32), 99, 0));
    this.setBounds(75,75,300,200);
    this.show();
  }
  private void setHost( String ip, int port ) {
    callback.addHost(ip,port);
    this.dispose();
  }

  class KillDialog implements ActionListener {
    JDialog toKill;
    KillDialog( JDialog dlg ) {
      toKill = dlg;
    }
    public void actionPerformed( ActionEvent e )
    {
      toKill.dispose();
    }
  }
}

