/**
 * <p>Title: A Request from the client</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.net.Socket;

public class Request {
  Socket m_client;
  String m_host;
  int m_hostPort;
  byte[] m_request;
  byte m_ttl;

  public Request(Socket client,byte[] aRequest) throws Exception{
    m_request=(byte[])aRequest.clone();
    m_client=client;
    getAHost(m_request);
    m_ttl=0;
  }

  /**
   * Get the dest host from the request
   * @param request
   * @return
   * @throws java.lang.Exception
   */
  void getAHost(byte[] request) throws Exception
  {
    String strHost="www.google.com";
    int port=80;
    boolean isHost=false;
    boolean isPort=false;
    int hostB=-1;
    int hostE=-1;
    int portB=-1;
    int portE=-1;
    for(int i=0;i<request.length;i++)
    {
      if (request[i]=='/')
      {
        if ((request[i-1]=='/')&&(request[i-2]==':'))
        {
          hostB = i + 1;
          isHost = true;
          continue;
        }
      }
      if(isHost && request[i]==':')
      {
        isHost=false;
        isPort=true;
        portB=i+1;
        hostE=i-1;
        continue;
      }
      if(isHost && request[i]=='/')
      {
        hostE=i-1;
        break;
      }
      if(isPort && request[i]=='/')
      {
        portE=i-1;
        break;
      }
    }
    if((hostB>0)&&(hostE>0))
      strHost=new String(request,hostB,hostE-hostB+1);
    if((portB>0)&&(portE>0))
      port=Integer.parseInt(new String(request,portB,portE-portB+1));
    m_host=strHost;
    m_hostPort=port;
  }

  public String toString()
  {
    return new String("< "+
        m_client.getInetAddress().getHostAddress() + ":" + m_client.getPort()
        +" sent a HTTP request to "
        +m_host+ ":" + m_hostPort
        +" "+m_request.length)+" bytes >";
  }
}