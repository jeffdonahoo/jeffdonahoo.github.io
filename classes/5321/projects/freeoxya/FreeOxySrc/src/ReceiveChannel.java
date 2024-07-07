

/**
 * <p>Title: </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;

public class ReceiveChannel implements Runnable{
  Socket m_socket;
  Socket m_toSocket;
  static byte[] m_data;

  public ReceiveChannel(Socket aSocket, Socket toSocket) {
    m_socket=aSocket;
    m_toSocket=toSocket;
  }
  public void run()
  {
    try{
      DataInputStream inChannel = new DataInputStream(m_socket.getInputStream());
      ByteArrayOutputStream out=new ByteArrayOutputStream();
      DataOutputStream outChannel=new DataOutputStream(m_toSocket.getOutputStream());

      byte[] inByte=new byte[Common.RECV_BUFSIZE];
      if (Common.DEBUG)
        System.out.println("Receiving data from..."+m_socket.getInetAddress());
      while(inChannel.read(inByte)>0)
      {
        //System.out.print("("+inByte[0]+")");
        outChannel.write(inByte);
      }
      outChannel.flush();
    }catch(Exception e){
        System.out.println("ReceiveChannel-run():"+e);
    }
  }

  static public byte[] getData()
  {
    return m_data;
  }

}