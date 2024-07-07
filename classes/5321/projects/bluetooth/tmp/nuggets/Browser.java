/* -*- mode: java; c-basic-indent: 3; indent-tabs-mode: t -*- */

import javax.bluetooth.*;
import com.rococosoft.impronto.util.*;
//import com.rococosoft.impronto.util.swing.SDPTree;
import javax.microedition.io.*;
import com.rococosoft.impronto.util.BluetoothConstants;
import java.io.*;
import java.security.*;

public class Browser
{
    private final static UUID uuid = UUIDUtil.generateUUID("nuggets");  //unique identifier for the nuggets service

    public static void main(String[] args)
    {	
	L2CAPConnectionNotifier service = null;  //the callback utility
	L2CAPConnection con = null;  //the currently accepted connection
	String serviceURL = "btl2cap://localhost:"+uuid.toString()+  //build a connection string (url style) for nuggets
                ";name=nuggets";
	
	try {
	    System.out.println(serviceURL);
	    service = (L2CAPConnectionNotifier)Connector.open(serviceURL);  //equivalent to socket.listen()
	    //This also creates a service record for nuggets on the device if one doesn't already exist
	    //...used when other devices service-scan this device
            
	    while(true)  //server loop
		{
		    byte[] hash = new byte[16];
		    con = (L2CAPConnection)(service.acceptAndOpen());  //equivalent to socket.accept()
		    FileInputStream fIn = null;
		    FileOutputStream fOut = null;
		    
		    /*
		      Attempts to open the file and read the first 16 bytes (which should be the hash).  If the first 16 bytes aren't
		      the hash, they will (very likely) be different from the transmitted hash and overwritten with the transmitted
		      hash.  If the file doesn't exist, hash contains many 0, which is still (very likely) different from the
		      transmitted hash.
		    */
		    try {
			fIn = new FileInputStream("nugget" + RemoteDevice.getRemoteDevice(con).getFriendlyName(false));
			fIn.read(hash, 0, 16);
			fIn.close();
		    }
		    catch (IOException e) {
		    }

		    /*
		      Begin receiving the nugget.  First comes a 4 byte length, then a 16 byte hash, then the actual nugget data
		    */

		    int MTU = con.getReceiveMTU();
		    byte[] buf = new byte[MTU];
		    StringBuffer nugget = new StringBuffer();
		    
		    int remaining = 0;
		    if (con.receive(buf) != 4) {  //receive the 4 byte length
			System.out.println("Bad length specification");
			con.close();  //if that fails, whack this connection and continue (listen for a new one)
			continue;
		    }
		    else {  //make an actual int out of it... the java microedition doesn't have any useful functions
			    //for this, so I did it myself
			remaining = buf[0] + (1 << 8) * buf[1] + (1 << 16) * buf[2] + (1 << 24) * buf[3];
		    }
		    System.out.println("length is " + remaining);
		    
		    byte[] hashBuf = new byte[16];  //receive the 16 byte hash
		    if (con.receive(hashBuf) != 16) {
			System.out.println("Bad hash");  //if that fails, whack this connection and continue (listen for a new one)
			con.close();
			continue;
		    }
		    else {
			if (hash != null) {
			    boolean needNugget = false;
			    for(int i = 0; i < 16; i++) {     //compare the hash from the file with the transmitted hash
				if (hash[i] != hashBuf[i]) {  //if they ever differ, we know we need to receive the whole
				    needNugget = true;        //nugget...
				}
			    }
			    if (!needNugget) {
				System.out.println("already have nugget");
				con.close();                  //...if they don't, we must already have this nugget
				continue;                     //close the connection and listen for a new one
			    }
			}
		    }

		    while(remaining > 0)  //receive the actual nugget
			{
			    int len = con.receive(buf);  //len is always = buf.length().  This is not good...
			                                                                  //(thank you impronto)
			    if (len > 0) {                  //but it's nothing a ternary operator can't fix
				nugget.append(new String(buf, 0, (len > remaining) ? remaining : len));
			    }
			    remaining -= len;
			}
		    System.out.println("nugget received: " + nugget.toString());
		    con.send(new byte[0]);  //send an empty packet to signal receipt of the nugget
		                            //the client will hold the connection open until it receives this packet
		    try { //attempt to write the hash & the actual nugget to a file
			fOut = new FileOutputStream("nugget" + RemoteDevice.getRemoteDevice(con).getFriendlyName(false));
			
			fOut.write(hashBuf);
			fOut.write(nugget.toString().getBytes());			    
			fOut.close();
		    }
		    catch (Exception e) {
			System.out.println("new FileOutputStream, would you please stand up? *BOOM*");
			continue;
		    }

		    if(con != null)  //close the connection
			con.close();
		}
	} catch (IOException e) {
	    e.printStackTrace();
	}	
    }
}
