import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

public class FreeOxyUI extends JDialog {
  JMenuBar jMenuBar = new JMenuBar();
  JMenu jFileMenu = new JMenu();
  JMenuItem jMenuItemExit = new JMenuItem();
  JMenu jNodeMenu = new JMenu();
  JMenuItem jMenuItemStart = new JMenuItem();
  JMenuItem jMenuItemStop = new JMenuItem();
  JMenuItem jMenuItemAddNeighbor = new JMenuItem();
  JCheckBoxMenuItem jMenuItemNoLocalSrv = new JCheckBoxMenuItem();
  boolean noLocalServe = false;
  public FreeOxyUI() throws HeadlessException {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {

    /*
     Start all threads here, as part of the UI init
     */

    this.setDefaultCloseOperation(3);
    this.setJMenuBar(jMenuBar);
    this.setModal(false);
    this.setResizable(true);
    this.setTitle("FreeOxy");
    this.setBounds(0,0,100,100);
    jMenuItemExit.setToolTipText("Close the FreeOxy application");
    jMenuItemExit.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });
    jMenuItemExit.setText("Exit");
    jFileMenu.setText("File");
    jNodeMenu.setText("Node");
    jMenuItemStart.setToolTipText("Start the FreeOxy proxy service");
    jMenuItemStart.setText("Start");
    jMenuItemStart.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        // call function  to start Node threads
        /*
            However, first check to make sure threads aren't already running
        */

       MessageBox mb = new MessageBox();

       try{
         FreeOxy.startThreads();
       }catch(Exception ex)
       {
         System.out.println("FreeOxyUI-startThreads failed:"+e);
         mb.ask("FreeOxy Node start error!");
       }

        mb.ask("FreeOxy Node start successfully!");
      }
    });
    jMenuItemStop.setToolTipText("Stop the FreeOxy proxy service");
    jMenuItemStop.setText("Stop");
    jMenuItemStop.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        // call function  to stop Node threads
        /*
             First check to make sure threads are running
         */
        MessageBox mb = new MessageBox();



        try{
          FreeOxy.stopThreads();
        }catch(Exception ex)
        {
          System.out.println("FreeOxyUI-stopThreads failed:"+e);
          mb.ask("Stop FreeOxy Node failed");
        }

        mb.ask("FreeOxy Node stopped.");


      }
    });
    jMenuItemAddNeighbor.setToolTipText("Add host information to the FreeOxy neighbor list");
    jMenuItemAddNeighbor.setText("Add neighbor");
    final FreeOxyUI me = this;
    jMenuItemAddNeighbor.addActionListener( new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        // invoke dialog box to add neighbor info
        AddNeighbor node = new AddNeighbor(me);
      }
    });
    jMenuItemNoLocalSrv.setToolTipText("Set NoLocalSrv to prevent this node from attempting to contact the www directly");
    jMenuItemNoLocalSrv.setText("NoLocalSrv");
    jMenuItemNoLocalSrv.addActionListener( new ActionListener() {
      final JCheckBoxMenuItem thisItem = jMenuItemNoLocalSrv;
      public void actionPerformed(ActionEvent e)
      {
        // negate noLocalServe
        me.noLocalServe = !me.noLocalServe;
        Common.PROXY_ALL=!Common.PROXY_ALL;
        // negate the setting of this menu item
        thisItem.setSelected(me.noLocalServe);
      }
    });
    jMenuBar.add(jFileMenu);
    jMenuBar.add(jNodeMenu);
    jFileMenu.add(jMenuItemExit);
    jNodeMenu.add(jMenuItemStart);
    jNodeMenu.add(jMenuItemStop);
    jNodeMenu.add(jMenuItemAddNeighbor);
    jNodeMenu.add(jMenuItemNoLocalSrv);
    this.addWindowListener( new WindowAdapter() {
      public void windowClosing (WindowEvent e) {

        e.getWindow().dispose();

      }
      public void windowClosed( WindowEvent e) {

        System.exit(0);
      }
    });
    this.show();
  }
  public void addHost( String ip, int port ) {
    String val = ip;
    val += "\n";
    val += String.valueOf((int)port);
    MessageBox mb = new MessageBox();
    try{
      FreeOxy.m_neighbors.addByHand(ip, port);
    }catch(Exception e)
    {
      mb.ask("Add neighbor:\nFailed!");
    }

    mb.ask("Add neighbor:\n"+val);

    Common.println("neighbor list size:"+FreeOxy.m_neighbors.size());
  }
  public static void main( String[] args)
  {
    new FreeOxyUI();
  }
}