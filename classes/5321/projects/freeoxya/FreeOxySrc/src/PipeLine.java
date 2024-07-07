
/**
 * <p>Title: Send data from a stream to another</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class PipeLine implements Runnable{
  Socket m_in=null;
  Socket m_out=null;
  InputStream m_input;
  OutputStream m_output;

  public PipeLine(Socket in, Socket out) throws Exception{
    m_in=in;
    m_out=out;
    m_input=in.getInputStream();
    m_output=out.getOutputStream() ;
  }

  public PipeLine(InputStream inS, OutputStream outS)
  {
    m_input=inS;
    m_output=outS;
  }

  public void run()
  {
    InputStream inStream;
    OutputStream outStream;

    //Common.println("PipleLine-run(): " +
    //                     m_input.getInetAddress().getHostAddress() + ":"
    //                    + m_input.getPort() + " ---> " +
    //                     m_output.getInetAddress().getHostAddress()+":"
    //                     +m_output.getPort());



    byte[] recvBuf=new byte[Common.RECV_BUFSIZE];

    int actualLen=-1;
    try{
      inStream=m_input;
      outStream=m_output;
      //FileOutputStream log=new FileOutputStream("request."+m_in.getPort());
      while ( (actualLen = inStream.read(recvBuf)) >= 0) {
        //String aStr=new String(recvBuf);
        //System.out.println(actualLen);
        if(actualLen>0)
        {
          outStream.write(recvBuf, 0, actualLen);
          outStream.flush();
        }
        //if(m_in!=null)
        //{
        //  if(m_in.getInetAddress().isLoopbackAddress())
         //   Common.print(Common.byte2String(recvBuf));

        //}
        //if (m_in.getInetAddress().isLoopbackAddress())
        //{
          //Common.println("<<<<<<<<<local connection " + m_in);
          //log.write(recvBuf,0,actualLen);
        //}

      }
      //m_out.shutdownOutput();
      //m_output.close();
      //if (m_out!=null)
      //{
        m_out.close();
        m_in.close();
        //m_out.shutdownOutput();
       // System.out.println("PipeLine-run():"+m_in+" -> "+m_out+" Closed ");
      //}
    }
    //catch(java.net.SocketException exp)
    //{
    //  System.out.println("PipeLine-run():"+m_in.toString()
    //                     +" -> "+m_out.toString()+" Exception "+exp);
    //}
    catch(Exception e)
    {
      System.out.println("PipeLine-run():"+m_in+" -> "+m_out+" "+e);
      try{
        m_in.close();
        m_out.close();
      }
      catch(Exception e1){
        ;
      }
    }
  }
}