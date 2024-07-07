

/**
 * <p>Title: </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */


import java.net.ServerSocket;

public class TCPServer implements Runnable{

  ServerSocket m_server;

  public TCPServer(ServerSocket sr) {
    m_server=sr;
  }

  public void run()
  {
    int id=0;
    while(true)
    {
      try{
        new Thread
            (new Connection(m_server.accept()), new Integer(id).toString()).
            start();
        id++;
      }
      catch(java.net.SocketException ex)
      {
        System.out.println("TCPServer-run(): TCP Server Thread stopped.");
        return;
      }
      catch(Exception e)
      {
        System.out.println("TCPServer-run():"+e);
      }
    }
  }
}