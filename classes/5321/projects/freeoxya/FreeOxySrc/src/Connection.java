/**
 * <p>Title: Handle a connection</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.io.DataInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLSocket;


public class Connection implements Runnable, Callback{
  Socket m_conn;
  Request m_req=null;

  /**
   * Constructor
   * @param aConn the socket of the server
   */
  public Connection(Socket aConn) {
    m_conn=aConn;
  }

  /**
   * Run function of the thread
   */
  public void run()
  {

    Socket aConn=m_conn;
    try {

      if (Common.DEBUG)
        Common.println("\n\nConnection-run():Handling client " +
                           aConn.getInetAddress().getHostAddress() + "-"
                           + aConn.getPort() + " with thread id " +
                           Thread.currentThread().getName()+
                           "@local:"+aConn.getLocalPort());
      connProcess(aConn);

    }
    catch (Exception e) {
      System.out.println("Connection-run():" + e);
      e.printStackTrace();
    }
    //Common.println("Connection-run(): Thread "+Thread.currentThread().getName()
    //               +" finished.");
  }

  /**
   * Process a connection
   * @param aSocket a client's socket
   * @throws java.lang.Exception
   */
  void connProcess(Socket aSocket) throws Exception
  {
    int state=0;
    int lineLen=0;
    boolean firstByte=true;
    try{
      //prepare the stream
      ByteArrayOutputStream outStream=new ByteArrayOutputStream();
      DataOutputStream out=new DataOutputStream(outStream);
      DataInputStream aConn = new DataInputStream(aSocket.getInputStream());
      byte[] onebyte=new byte[1];
      while(aConn.read(onebyte)==1)
      {
        lineLen++;
        out.writeByte(onebyte[0]);
        if ((onebyte[0]==13)&&(state==0))
        {
          state = 1;
          continue;
        }
        if ((onebyte[0]==10)&&(state==1))
        {
          state=0;
          //get a whole line
          out.flush();
          if (lineLen==2)
          //empty line end one request
          {
            //add a request to the request list

            //synchronized (FreeOxy.m_requestList) {
              m_req=new Request(aSocket, outStream.toByteArray());
              m_req.m_ttl=0;
              //FreeOxy.m_requestList.add(aRequest);
              m_req.m_request=noKeepAlive(m_req.m_request);
              dispatch(m_req);
              //FreeOxy.m_requestList.notify();
            //}

            return;
            //outStream.reset();//empty the buffer
          }
          lineLen=0;
          continue;
        }
      }
    }catch(Exception e)
    {
      throw new Exception(e);
    }
  }


  public void dispatch(Request aReq) throws Exception
  {
    Common.println("Connection-dispatch():"+ aReq.toString());

    //aReq.m_request=noKeepAlive(aReq.m_request);
    //Common.println("11111111111111111111111111111111");
    //Common.show(aReq.m_request);



    if(Common.PROXY_ALL)
    {
      //forward all
      Common.println("Connection-dispatch():Forward "+ aReq.toString());
      MsgProbe aMsg=new MsgProbe(Common.string2bytes(aReq.m_host),aReq.m_hostPort);
      UDPServer.sendProbe(aMsg,this);
      return;
    }
    else
    {
      if(Common.testURL("http",aReq.m_host,aReq.m_hostPort))
      //can be served locally
      {

        //send request to www
         Socket wwwServer=new Socket(aReq.m_host, aReq.m_hostPort);
         wwwServer.getOutputStream().write(aReq.m_request);
         new Thread(new PipeLine(wwwServer,aReq.m_client)).start();
         return;
      }

      Socket proxy=null;
      if((proxy=FreeOxy.getCachedConnection(aReq.m_host))!=null)
      {
        proxy.getOutputStream().write(aReq.m_request);
        new Thread(new PipeLine(proxy,m_req.m_client)).start();
        return;
      }
      Common.println
          ("Connection-dispatch():Cann't proxy locally, forward "+ aReq.toString());
      MsgProbe aMsg=new MsgProbe(Common.string2bytes(aReq.m_host),aReq.m_hostPort);
      UDPServer.sendProbe(aMsg,this);
    }
  }


  byte[] noKeepAlive(byte[] req) throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    DataOutputStream tmpStream=new DataOutputStream(tmp);
    for(int i=0;i<req.length;i++)
    {
      // look for "Connection:"
      if((req[i]=='k'||req[i]=='K'))
      {
        if( req[i+1] == 'e' &&
            req[i+2] == 'e' &&
            req[i+3] == 'p' &&
            req[i+4] == '-' &&
            (req[i+5]=='a'||req[i+5]=='A') &&
            req[i+6] == 'l' &&
            req[i+7] == 'i' &&
            req[i+8] == 'v' &&
            req[i+9] == 'e')
        {
          // skip rest of line
          i+=10;
        }
      }
      tmpStream.writeByte(req[i]);
    }
    return tmp.toByteArray();
  }




  public void response(Object rtn) throws Exception
  {
    InetSocketAddress proxy=(InetSocketAddress)rtn;
    //Common.println("@@@ @@@  @@@ Connection-response():"+m_req+" proxied by "+proxy);

    SSLSocket ssl= (SSLSocket)SSLSocketFactory.getDefault().
        createSocket(proxy.getAddress(), proxy.getPort());
    ssl.setEnabledCipherSuites(Common.CIPHER_SUITES);
    ssl.getOutputStream().write(m_req.m_request);
    //Common.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    //Common.show(m_req.m_request);
    new Thread(new PipeLine(ssl,m_req.m_client)).start();
  }


}