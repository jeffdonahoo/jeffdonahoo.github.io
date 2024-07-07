/**
 * <p>Title: A FreeOxy Server</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.util.Vector;
import java.util.Hashtable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetSocketAddress;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.EOFException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;



public class FreeOxy {

  //public static Vector m_requestList=new Vector();
  //public static boolean m_secure=false;
  public static MaintainNeighborList m_neighbors=new MaintainNeighborList();
  public static Hashtable m_URLCache=new Hashtable();

  public static int m_port;//port for browser conncection
  public static int m_msgPort;//port for control message and ssl connection

  public static Thread m_UDPServer;
  public static Thread m_TCPServer;
  public static Thread m_SSLServer;
  public static Thread m_PPServer;

  public static ServerSocket m_TCPSocket;
  public static ServerSocket m_SSLSocket;



  public static void main(String[] args)
  {
    if ((args.length!=3)&&(args.length!=4))
    //check out command line
    {
      System.out.println("Usage: FreeOxy <port> <msg port> <maxthreads> [<TTL>]");
      return;
    }

    try{
      //initialize the neighbor list from FreeOxy.conf
      init();

      //get the server's well-known port
      m_port=Integer.parseInt(args[0]);
      //get ssl port
      m_msgPort=Integer.parseInt(args[1]);
      //get the size of thread pool
      //int numServer=Integer.parseInt(args[2]);
      //set the max TTL to control the message forward
      if(args.length==4)
      {
        Common.MAX_TTL=Byte.parseByte(args[3]);
      }

      new FreeOxyUI();
      //startThreads();





    }
    catch(Exception e)
    //catch IO exception
    {
      System.out.println("FreeOxy-main():"+e);
    }
  }


  static void stopThreads() throws Exception
  {
    if(m_UDPServer!=null)
      m_UDPServer.interrupt();
    if(m_PPServer!=null)
      m_PPServer.interrupt();
    if(m_TCPServer!=null)
      m_TCPServer.interrupt();
    if(m_SSLServer!=null)
      m_SSLServer.interrupt();
    if(m_TCPSocket!=null)
    {
      m_TCPSocket.close();
    }
    if(m_SSLSocket!=null)
    {
      m_SSLSocket.close();
    }

  }

  static void startThreads() throws Exception
  {
    //create a new socket to listen
    ServerSocket s=new ServerSocket(m_port);
    //set to reuse this address
    s.setReuseAddress(true);
    //create SSL Server
    SSLServerSocket ssl =(SSLServerSocket)SSLServerSocketFactory.getDefault().
        createServerSocket(m_msgPort);
    ssl.setEnabledCipherSuites(Common.CIPHER_SUITES);
    ssl.setReuseAddress(true);

    //create a UDP server
    m_UDPServer=new Thread(new UDPServer(m_msgPort));
    //create a TCP server
    m_TCPServer=new Thread(new TCPServer(s));
    m_TCPSocket=s;
    //create a SSL server
    m_SSLServer=new Thread(new TCPServer(ssl));
    m_SSLSocket=ssl;
    //create a pingpong thread
    m_PPServer=new Thread(new PingPong());


    m_UDPServer.start();
    m_PPServer.start();
    m_TCPServer.start();
    m_SSLServer.start();

  }


  /**
   * Read the start neighbor list from FreeOxy.conf
   * @throws java.lang.Exception
   */
  static void init()
  {
    try{
      String token;
      FileInputStream configFile=new FileInputStream(Common.CONFIG_FILE);
      MyTokenizer config=new MyTokenizer(configFile,null);
      try{
        while (true) {
          //read host
          String hostName = config.getNextToken(" :");
          //read port
          String hostPortStr=config.getNextToken("\r\n");
          int hostPort = Integer.parseInt(hostPortStr);
          //Host aHost=new Host(hostName,hostPortalse);
          Common.addNeighbor
              (new InetSocketAddress(java.net.InetAddress.getByName(hostName),hostPort));
        }

      }catch(EOFException e)
      //read to the end of file
      {
        if(FreeOxy.m_neighbors.size()==0)
        //no neighbor found
        {
          throw new Exception("FreeOxy runs without any neighbor around!!!"+
                              "\nIf you register your IP on www.FreeOxy.com,"+
                              "one of our FreeOxy server will send you the latest server list!"+
                              "Good Luck!!!");
        }
      }
    }catch(Exception e)
    {
      System.out.println("FreeOxy-init():"+e);
    }
  }


  public static Socket getCachedConnection(String url) throws Exception
  {
    return getCachedConnection(Common.string2bytes(url));
  }

  public static Socket getCachedConnection(byte[] url) throws Exception
  {
    String urlString=Common.bytes2String(url);
    CachedURL cache=(CachedURL)m_URLCache.get(urlString);
    if(cache==null)
      return null;
    else
    {
      SSLSocket ssl= (SSLSocket)SSLSocketFactory.getDefault().
          createSocket(cache.m_address.getAddress(),cache.m_address.getPort());
      ssl.setEnabledCipherSuites(Common.CIPHER_SUITES);
      return ssl;
    }
  }

  public static void saveNeighbor()
  {
    try{
      FileOutputStream configFile = new FileOutputStream(Common.CONFIG_FILE);
      m_neighbors.toStream(configFile);
      configFile.close();
    }catch(Exception e)
    {
      System.out.println("FreeOxy-saveNeighbor():"+e);
    }
  }


}