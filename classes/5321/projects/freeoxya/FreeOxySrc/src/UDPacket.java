/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

import java.net.InetSocketAddress;


public class UDPacket {
  InetSocketAddress m_address;

  //
  //
  //
  // udp_packet = version + message
  //
  //
  //
  byte m_version=1;
  byte[] m_msg=null;

  public UDPacket(InetSocketAddress addr) {
    m_address=addr;
  }

  public UDPacket(byte[] msg)
  {
    m_msg=msg;
  }

  public void readFrom(byte[] in) throws Exception
  {
    m_version=in[0];
    m_msg=new byte[in.length-1];
    for(int i=1;i<in.length;i++)
    {
      m_msg[i-1]=in[i];
    }
  }

  public void writeTo(OutputStream out) throws Exception
  {
    DataOutputStream tmpStream=new DataOutputStream(out);
    tmpStream.writeByte(m_version);
    tmpStream.write(m_msg);
    tmpStream.flush();
  }

  public byte[] toByteArray() throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    writeTo(tmp);
    return tmp.toByteArray();
  }

  public InetSocketAddress getAddress() throws Exception
  {
    if(m_address==null)
      throw new ExtException("UDPacket-getAddress(): No address",0);
    else
      return m_address;
  }

  public byte[] getData() throws Exception
  {
    if(m_msg==null)
      throw new ExtException("UDPacket-getData(): No message data",0);
    else
      return m_msg;
  }

}
