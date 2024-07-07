

/**
 * <p>Title: </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.net.InetSocketAddress;

public class CachedURL {
  public String m_url;
  public InetSocketAddress m_address;
  public long m_timeStamp;
  public CachedURL(String url,InetSocketAddress addr,long time) {
    m_url=url;
    m_address=addr;
    m_timeStamp=time;
  }

}