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

public class MsgResponse {

//
// MSG_TYPE + len + ip + port + len + URL
//
//
  public InetSocketAddress m_source=null;
  public InetSocketAddress m_proxy=null;
  public byte m_type=Common.MSG_RSP;
  public byte[] m_url;
  public Callback m_callback;

  public MsgResponse(InetSocketAddress proxy,byte[] url)
  //compose a new message
  {
    m_url=(byte[])url.clone();
    m_proxy=proxy;
  }

  public MsgResponse(InetSocketAddress s)
  //to read a message
  {
    m_source=s;
  }

  /**
   * Write the message to a stream
   * @param out the stream to write
   * @throws java.lang.Exception
   */
  public void writeTo(DataOutputStream tmpStream) throws Exception
  {
    tmpStream.writeByte(m_type);
    byte[] proxy=m_proxy.getAddress().getAddress();
    tmpStream.writeInt(proxy.length);
    tmpStream.write(proxy);
    tmpStream.writeInt(m_proxy.getPort());
    tmpStream.writeInt(m_url.length);
    tmpStream.write(m_url);
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
    byte[] proxy=new byte[len];
    if(in.read(proxy)!=len)
      throw new ExtException("MsgResponse-readFrom:wrong IP length",0);
    int port=in.readInt();
    m_proxy=new InetSocketAddress(java.net.InetAddress.getByAddress(proxy),port);
    len=in.readInt();
    m_url=new byte[len];
    if(in.read(m_url)!=len)
      throw new ExtException("MsgResponse-readFrom:wrong URL length",0);
  }


  public byte[] toByteArray() throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    DataOutputStream tmpStream=new DataOutputStream(tmp);
    writeTo(tmpStream);
    tmp.flush();
    return tmp.toByteArray();
  }
}
