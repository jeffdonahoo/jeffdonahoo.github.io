/**
 * <p>Title: Host in FreeOxy</p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.net.InetSocketAddress;

public class Neighbor implements Comparable{
  public InetSocketAddress m_host;
  public int m_satisfaction;
  public long m_time;

  public Neighbor(InetSocketAddress host) throws Exception{
    m_host=host;
    m_satisfaction=Common.DEFAULT_SAT;
    m_time=System.currentTimeMillis();
  }


  public String toString()
  {
    return new String(m_host.toString()+" SAT "+m_satisfaction+" TIME "+m_time);
  }

  public int compareTo(Object other)
  {
    if (other==null)
      return -1;
    Neighbor aHost=(Neighbor)other;
    return (this.m_host.equals(aHost.m_host))?0:-1;
  }

  public boolean equals(Object other)
  {
    if (compareTo(other)==0)
      return true;
    else
      return false;
  }

}