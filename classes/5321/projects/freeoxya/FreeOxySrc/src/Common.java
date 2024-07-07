/**
 * <p>Title: Constant and utility function </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */

import java.security.Key;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.util.Random;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

import java.security.MessageDigest;

import java.net.InetSocketAddress;

public class Common {




  public final static String CONFIG_FILE="FreeOxy.conf";//config file
  public final static boolean DEBUG=false;//turn on/off debug message
  public static boolean PROXY_ALL=false;

  public final static int PROB_A=1;
  public static boolean SSL=true;//manually turn on/off SSL channel
  public final static int RECV_BUFSIZE=1024;
  public static byte MAX_TTL=3;//max hops for message passing
  public static byte MAX_FAN=7;
  public static final int TIMEOUT=1000;//socket timeout
  public static int MAX_WIRE_LENGTH=1024;

  public static int PROBE_Q_LEN=100;


  public static int DEFAULT_SAT=100;


  public final static byte MSG_PING=20;
  public final static byte MSG_PONG=21;
  public final static byte MSG_PROBE=30;
  public final static byte MSG_RSP=31;

  private static Random m_random=new Random();//random number generator

  public static String[] CIPHER_SUITES={
      "SSL_DH_anon_WITH_RC4_128_MD5",
      "SSL_DH_anon_WITH_DES_CBC_SHA",
      "SSL_DH_anon_WITH_3DES_EDE_CBC_SHA",
      "SSL_DH_anon_EXPORT_WITH_RC4_40_MD5",
      "SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA"
  };//cipher suites for SSL


  public static String KEY_PROVIDER="SUN";
  public static String HASH_ALGO="SHA-1";


  public static final long SLEEP=13000;//sleep time for Explorer

  public Common() {
  }

  /**
   * Get a random integer
   * @param n scope of the intger
   * @return an integer
   */
  public static int getNextInt(int n)
  {
    return m_random.nextInt(n);
  }

  public static void print(String str)
  {
    if(DEBUG)
      System.out.print(str);
  }

  public static void println(String str)
  {
    if(DEBUG)
      System.out.println(str);
  }

  /**
   * Add a host to neighbor list
   * @param aHost
   */
  public static void addNeighbor(InetSocketAddress aHost) throws Exception
  {
    FreeOxy.m_neighbors.addNeighbor(aHost, Common.DEFAULT_SAT);

    //Common.println
        //("Common-addNeighbor(): Neighbor list size "+FreeOxy.m_neighbors.size());
  }



  public static boolean testURL(String protocol,String aHost,int port) throws Exception
  {
    java.net.HttpURLConnection connHttp=(java.net.HttpURLConnection)
        new java.net.URL(protocol,aHost,port,"").openConnection();
    int rtnCode=connHttp.getResponseCode();
    //Common.println("Common-testURL():"+rtnCode);
    //rtnCode/=100;
    connHttp.disconnect();

    return ((rtnCode/100==2)||(rtnCode==404))?true:false;
  }

  public static String bytes2String(byte[] data) throws Exception
  {
    ByteArrayInputStream tmp=new ByteArrayInputStream(data);
    DataInputStream tmpStream=new DataInputStream(tmp);
    //Common.show(data);
    return tmpStream.readUTF();
  }

  public static byte[] string2bytes(String data) throws Exception
  {
    ByteArrayOutputStream tmp=new ByteArrayOutputStream();
    DataOutputStream tmpStream=new DataOutputStream(tmp);
    tmpStream.writeUTF(data);
    tmpStream.flush();
    return tmp.toByteArray();
  }

  /**
   * Print out a byte array for test
   */
  public static void show(byte[] toShow) throws Exception
  {
    /*
    java.io.FileOutputStream log=new java.io.FileOutputStream("err.txt");

    log.write(toShow);

    log.close();

    System.out.print("*");
    if(toShow==null)
      System.out.print("null");
    else
    {
      for (int i=0;i<toShow.length;i++)
      {
        System.out.print("(");
        System.out.print(i);
        System.out.print(",");
        System.out.print(toShow[i]&0x000000FF);
        System.out.print(")");
      }
    }
    System.out.println("*");
        */
  }



  public static byte[] hash(byte[] data) throws Exception
  {
    if (data==null)
      return null;
    MessageDigest sha=
        MessageDigest.getInstance(Common.HASH_ALGO, Common.KEY_PROVIDER);
    return sha.digest(data);
    //return (byte[]) data.clone();
  }


  public static int getRandomInt()
  {
    int rtn=m_random.nextInt();
    return (rtn<0)?0-rtn:rtn;
  }


}