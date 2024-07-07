/**
 * <p>Title: Pong message</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;

public class MsgPingPong {

//
// MSG_TYPE + len + ip + port + timestamp
//
//
  public InetSocketAddress m_host=null;
  public byte m_type;
  long m_time;

  public MsgPingPong(byte type,InetSocketAddress host, long time)
  //compose a new message
  {
    m_type=type;
    m_host=host;
    m_time=time;
  }


  public MsgPingPong(byte type)
  //to read a message
  {
    m_type=type;
  }

  /**
   * Write the message to a stream
   * @param out the stream to write
   * @throws java.lang.Exception
   */
  public void writeTo(DataOutputStream tmpStream) throws Exception
  {
    tmpStream.writeByte(m_type);
    byte[] host=m_host.getAddress().getAddress();
    tmpStream.writeInt(host.length);
    tmpStream.write(host);
    tmpStream.writeInt(m_host.getPort());
    tmpStream.writeLong(m_time);
    //Common.println("pinggggggggggggg"+m_time);
    tmpStream.flush();
  }


  public void readFrom(byte[] data)  throws Exception
  {
    ByteArrayInputStream tmp=new ByteArrayInputStream(data);
    DataInputStream tmpStream=new DataInputStream(tmp);
    readFrom(tmpStream);
  }

  /**
   * Read a Pong message from a stream
   * @param in the stream to read from
   * @throws java.lang.Exception
   */
  public void readFrom(DataInputStream in) throws Exception
  {
    m_type=in.readByte();
    int len=in.readInt();
    byte[] host=new byte[len];
    if(in.read(host)!=len)
      throw new ExtException("MsgResponse-readFrom:wrong IP length",0);
    int port=in.readInt();
    m_host=new InetSocketAddress(java.net.InetAddress.getByAddress(host),port);
    m_time=in.readLong();
  }


  public byte[] toByteArray() throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    DataOutputStream tmpStream=new DataOutputStream(tmp);
    writeTo(tmpStream);
    tmp.flush();
    return tmp.toByteArray();
  }

  public String toString()
  {
    return m_host+" @ "+m_time;
  }
}
