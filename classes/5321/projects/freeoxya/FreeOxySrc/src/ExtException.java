/**
 * <p>Title: ExtException</p>
 * <p>Description: Extend exception</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

public class ExtException extends Exception{
  String m_msg;
  int m_err;
  public ExtException(String msg,int errCode) {
    m_msg="Exception:" + msg;
    m_err=errCode;
  }
  public String toString(){
    return "Error Code-"+m_err+m_msg ;
  }
}
