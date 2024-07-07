/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.util.Iterator;
import java.util.Vector;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;


public class UDPServer implements Runnable{

  int m_port;
  static DatagramSocket m_socket;
  static Vector m_pendingProbe=new Vector();
//  MsgProbe[] m_pendingProbe=new MsgProbe[Common.PROBE_Q_LEN];

  public UDPServer(int port) throws Exception{
    m_port=port;
    m_socket=new DatagramSocket(m_port);
  }

  static public void sendTo(InetSocketAddress dest,byte[] data) throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    new UDPacket(data).writeTo(tmp);
    DatagramPacket aPacket=new DatagramPacket
        (tmp.toByteArray(),tmp.toByteArray().length,dest.getAddress(),dest.getPort());
    m_socket.send(aPacket);
  }

  public void run()
  {
    while(true)
    {
      try{
        DatagramPacket udPacket = new DatagramPacket
            (new byte[Common.MAX_WIRE_LENGTH], Common.MAX_WIRE_LENGTH);
        m_socket.receive(udPacket);
        InetSocketAddress source=
            new InetSocketAddress(udPacket.getAddress(),udPacket.getPort());

        UDPacket aPacket=new UDPacket(source);
        aPacket.readFrom(udPacket.getData());
        receivePacket(aPacket);
        if (Thread.interrupted())
        {
          Common.println("UDPServer-run(): UDP Server Thread stopped.");
          return;
        }
      }catch(Exception e)
      {
        System.out.println("UDPServer-run: "+e);
        e.printStackTrace();
      }
    }
  }

  void receivePacket(UDPacket aPacket) throws Exception
  {
    byte msgType=aPacket.getData()[0];

    Common.println
        ("UDPServer-receivePacket(): From "+aPacket.getAddress()+" MSG-"+msgType);

    switch(msgType)
    {
      case Common.MSG_PING:
        //receive a list of hosts

        MsgPingPong pingMsg=new MsgPingPong(Common.MSG_PING);
        pingMsg.readFrom(aPacket.getData());

        //
        //
        //
        //
        //
        //

        //send pong
        InetSocketAddress oneNeighbor=FreeOxy.m_neighbors.getOneNeighbor();
        if(oneNeighbor==null)
        {
          oneNeighbor=new InetSocketAddress(java.net.InetAddress.getLocalHost(),
                                            FreeOxy.m_msgPort);
        }
        MsgPingPong pongMsg = new MsgPingPong
            (Common.MSG_PONG, oneNeighbor, pingMsg.m_time);
        sendTo(aPacket.getAddress(), pongMsg.toByteArray());
        //Common.println("ping copy to pong"+pongMsg.m_time);
        //add to the neighbor list
        if(!FreeOxy.m_neighbors.checkAddress(pingMsg.m_host))
          FreeOxy.m_neighbors.addNeighbor(pingMsg.m_host,Common.DEFAULT_SAT);

        break;
      case Common.MSG_PONG:
        //receive a pong
        //update neighbor list
        //receive a list of hosts
        //send pong
        MsgPingPong ponMsg=new MsgPingPong(Common.MSG_PONG);
        ponMsg.readFrom(aPacket.getData());


        //caculate RTT
        long rtt=System.currentTimeMillis()-ponMsg.m_time;

        Common.println("UDPServer-receivePacket(): "+aPacket.getAddress()+" RTT: "+rtt);


        //add to the neighbor list
        if(!FreeOxy.m_neighbors.checkAddress(ponMsg.m_host))
          FreeOxy.m_neighbors.addNeighbor(ponMsg.m_host,Common.DEFAULT_SAT);


        break;
      case Common.MSG_PROBE:
        //receive a request for a url
        //send response or forward
        MsgProbe aMsg=new MsgProbe(aPacket.getAddress());
        aMsg.readFrom(aPacket.getData());
        //check local connection
        if(Common.testURL("http",Common.bytes2String(aMsg.m_url),aMsg.m_port))
        //can be served locally
        {
          MsgResponse msg2send=new MsgResponse(
              new InetSocketAddress(java.net.InetAddress.getLocalHost(),FreeOxy.m_msgPort),
              Common.hash(aMsg.m_url));
          sendTo(aMsg.m_source,msg2send.toByteArray());

          break;
        }
        //check local cache
        java.net.Socket proxy=null;
        if((proxy=FreeOxy.getCachedConnection(aMsg.m_url))!=null)
        {
          MsgResponse msg2send=new MsgResponse(
              new InetSocketAddress(proxy.getInetAddress(),proxy.getPort()),
              Common.hash(aMsg.m_url));
          proxy.close();
          sendTo(aMsg.m_source,msg2send.toByteArray());
          break;
        }
        //else forward
        if(!aMsg.decTTL())
          break;

        m_pendingProbe.add(aMsg);

        //should send to fanout num of neighbors with high satisfaction
        //probe makes satisfaction value dec

        java.util.List nodes=FreeOxy.m_neighbors.getAddress(aMsg.m_fanout);
        for (int i=0;i<nodes.size();i++)
        {
          sendTo((InetSocketAddress)nodes.get(i), aMsg.toByteArray());
          FreeOxy.m_neighbors.dec((InetSocketAddress)nodes.get(i));
        }

        break;
      case Common.MSG_RSP:
        //receive a response
        MsgResponse rMsg=new MsgResponse(aPacket.getAddress());
        rMsg.readFrom(aPacket.getData());
        MsgResponse myMsg=new MsgResponse(
            new InetSocketAddress(java.net.InetAddress.getLocalHost(),FreeOxy.m_msgPort),
            rMsg.m_url);

        //Common.show(rMsg.toByteArray());
        //check the source of response inc its satisfaction
        FreeOxy.m_neighbors.inc(rMsg.m_source);

        //check its corresponding probe
        synchronized (m_pendingProbe)
        {
          Iterator it = m_pendingProbe.iterator();
          while (it.hasNext()) {
            MsgProbe pMsg = (MsgProbe) it.next();
            if (java.util.Arrays.equals(rMsg.m_url, Common.hash(pMsg.m_url))) {
              if (pMsg.m_source == null)
                pMsg.m_callback.response(rMsg.m_source);
              else
              // forward response to pMsg.m_source
              {
                //
                //
                //
                //
                //
                //
                if (oracle())
                  sendTo(pMsg.m_source, myMsg.toByteArray());

                sendTo(pMsg.m_source, rMsg.toByteArray());
                CachedURL url = new CachedURL
                    (Common.bytes2String(pMsg.m_url),
                     rMsg.m_proxy,
                     System.currentTimeMillis());

                FreeOxy.m_URLCache.put(url.m_url, url); //put into cache

                Common.println
                    ("UDPServer-receivePacket(): URL Cache size " +
                     FreeOxy.m_URLCache.size());
              }
              it.remove();
            }
          }
        }

        break;
    }
  }


  static public void sendProbe(MsgProbe aMsg, Callback cb) throws Exception
  {
    // Keep track of probe
    aMsg.m_callback = cb;
    m_pendingProbe.add(aMsg);
    // Send probe message to a set of neighbors


    java.util.List nodes=FreeOxy.m_neighbors.getAddress(aMsg.m_fanout);

    for (int i=0;i<nodes.size();i++)
    {
      Common.println(((InetSocketAddress)nodes.get(i)).toString());
      sendTo((InetSocketAddress)nodes.get(i), aMsg.toByteArray());
      FreeOxy.m_neighbors.dec((InetSocketAddress)nodes.get(i));
    }

  }


  boolean oracle()
  //decide to send modified reponse message or not
  {
    return ((Common.getRandomInt()%100)<Common.PROB_A)?true:false;
  }
}
