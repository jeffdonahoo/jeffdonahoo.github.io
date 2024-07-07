
/**
 * <p>Title: </p>
 * <p>Description: CSI 5321Program 1</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Baylor CS</p>
 * @authors: Lin Cai,Jerry Knight,Ashish Vashishta,Jeff Wilson
 * @version 1.0
 */
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import java.util.Vector;

public class MaintainNeighborList  {

  InetSocketAddress neighborSocketAddress ;
  int satisfactionValue ;
  Map neighborListMap ;
  private final int defaultSatisfactionValue = Common.DEFAULT_SAT ;
  private final int minimumThresholdValue = 90 ;

  /**
   * Constructor
   */
  public MaintainNeighborList() {
    neighborListMap = new HashMap();
  }


 public void addByHand(String host, int port) throws Exception
 {
   addNeighbor(new InetSocketAddress(host,port),Common.DEFAULT_SAT);
 }


  /**
   *
   * @param neighbor
   * @param value
   */
  public synchronized void addNeighbor(InetSocketAddress neighbor, int value)
      throws Exception
  {
    if (neighbor.getAddress().equals(java.net.InetAddress.getLocalHost()))
      return;
    if ( !neighborListMap.containsKey(neighbor) )
    {
      //if a neigbor is not there then put it in with a value of 100(default)
      neighborListMap.put(neighbor,new Integer(defaultSatisfactionValue)) ;
    }
    else {
      //check if the value is less than the minimum threshold in that case do
      //not add to the map.instead remove that neighbor.
      if (value < minimumThresholdValue)
      {
        neighborListMap.remove(neighbor);
      }
      else{
        neighborListMap.put(neighbor, new Integer(value));
      }
    }
  }

  public synchronized int getValue(InetSocketAddress neighbor)
  {
    Object rtn=neighborListMap.get(neighbor);
    if (rtn==null)
      return 0;
    return ((Integer)rtn).intValue() ;
  }

  /**
   * gets the list of nodes with high satisfaction value among available nodes.
   * @param number - how many nodes do you want in that list, depends on the fanout.
   * @return the list of high satisfaction value nodes.
   */
  public synchronized List getAddress(int number)
  {
    //see if number is not greater than tne size of the neighborListMap
    if (number > neighborListMap.size() ){
      number = neighborListMap.size() ;
    }
    Set anEntrySet = neighborListMap.entrySet() ;
    List list = new Vector(anEntrySet);
    Collections.sort(list, new CompareValues());
    return extractAddressList(list, number);
  }

  /**
   * returns a sublist from a sorted list
   * @param alist
   * @param number
   * @return
   */
  private Vector extractAddressList (List alist , int number)
  {
    Vector returnList = new Vector();
    for ( int i = 0 ; i < number ; i++ )
    {
      returnList.add( ((Map.Entry)alist.get(i)).getKey() );
    }
    return returnList ;
  }

  /**
   * Inner class
   */
  class CompareValues implements Comparator{

    public int compare(Object o1, Object o2) {
      Integer intOne = (Integer)((Map.Entry)o1).getValue();
      Integer intTwo = (Integer)((Map.Entry)o1).getValue();
      //guess compareTo gives ascending order so do in reverse order.
      return intTwo.compareTo(intOne);
    }

    public boolean equals(Object obj){
      return obj instanceof CompareValues;
    }
  }



  public int size()
  {
    return neighborListMap.size();
  }

  public synchronized void inc(InetSocketAddress addr) throws Exception
  {

    int tempValue = getValue(addr);
    addNeighbor(addr,tempValue + 1) ;
  }

  public synchronized void dec(InetSocketAddress addr) throws Exception
  {
    int tempValue = getValue(addr);
    addNeighbor(addr,tempValue - 1) ;
  }

  public synchronized boolean checkAddress(InetSocketAddress iSocketAddress)
  {
    return ( neighborListMap.containsKey(iSocketAddress));
  }

  public synchronized InetSocketAddress getOneNeighbor()
  {

    Set aSet=neighborListMap.entrySet();
    List list = new Vector(aSet);
    if (list.size()==0)
      return null;
    int index=Common.getRandomInt() % list.size() ;
    InetSocketAddress rtn=(InetSocketAddress)((Map.Entry)list.get(index)).getKey();
    return new InetSocketAddress(rtn.getHostName(),rtn.getPort());
//    Common.println("size "+aSet.size()+" index "+index);
//    return (InetSocketAddress)aSet.toArray()[1];
  }

  public void toStream(java.io.OutputStream out) throws Exception
  {
    //java.io.DataOutputStream outStream=new java.io.DataOutputStream(out);
    MyTokenizer config=new MyTokenizer(null,out);
    java.util.Set list=neighborListMap.keySet();
    Iterator it=list.iterator();
    while(it.hasNext())
    {
      InetSocketAddress r=(InetSocketAddress)it.next();
      config.putToken(r.getHostName()+" "+Integer.toString(r.getPort())+"\r");
    }
  }

}