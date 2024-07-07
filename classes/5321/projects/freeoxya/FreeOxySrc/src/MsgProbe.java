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

public class MsgProbe {

//
// MSG_TYPE + TTL + FANOUT + len + URL
//
//
  public InetSocketAddress m_source=null;
  public byte m_type=Common.MSG_PROBE;
  public byte m_ttl=Common.MAX_TTL;
  public byte m_fanout=Common.MAX_FAN;
  public byte[] m_url;
  public int m_port;
  public Callback m_callback;

  public MsgProbe(byte[] url, int port)
  {
    m_url=(byte[])url.clone();
    m_port=port;
  }

  public MsgProbe(InetSocketAddress s) {
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
    tmpStream.writeByte(m_ttl);
    tmpStream.writeByte(m_fanout);
    tmpStream.writeInt(m_url.length);
    tmpStream.write(m_url);
    tmpStream.writeInt(m_port);
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
    m_ttl=in.readByte();
    m_fanout=in.readByte();
    int len=in.readInt();
    m_url=new byte[len];
    if(in.read(m_url)!=len)
      throw new ExtException("MsgProbe-readFrom:wrong URL length",0);
    m_port=in.readInt();
  }



  public byte[] toByteArray() throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    DataOutputStream tmpStream=new DataOutputStream(tmp);
    writeTo(tmpStream);
    tmp.flush();
    return tmp.toByteArray();
  }

  public boolean decTTL()
  {
    m_ttl--;
    if(m_ttl<0)
      return false;
    return true;
  }
}
