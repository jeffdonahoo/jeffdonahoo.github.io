

/**
 * <p>Title: </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

public class PingPong implements Runnable{
  public PingPong() {
  }
  public void run()
  {
    while(true)
    {
      try{
        //send ping
        java.net.InetSocketAddress addr=FreeOxy.m_neighbors.getOneNeighbor();
        java.net.InetSocketAddress host2send=FreeOxy.m_neighbors.getOneNeighbor();
        if (host2send==null)
          host2send=new java.net.InetSocketAddress(
              java.net.InetAddress.getLocalHost(),
              FreeOxy.m_msgPort);
        if(addr!=null)
        {
          MsgPingPong pingMsg = new MsgPingPong
              (Common.MSG_PING, host2send, System.currentTimeMillis());

          Common.println("PingPong-run(): " + pingMsg.toString());

          UDPServer.sendTo(addr, pingMsg.toByteArray());
        }

        FreeOxy.saveNeighbor();
        Thread.sleep(Common.SLEEP);


      }
      catch(java.lang.InterruptedException ex)
      {
        Common.println("PingPong-run(): PingPong Thread stopped.");
        return;
      }
      catch(Exception e)
      {
        System.out.println("PingPong-run():"+e);
      }
    }
  }
}