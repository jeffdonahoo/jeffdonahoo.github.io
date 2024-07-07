/*************************************************
*
*  Author: Lin Cai
*  Assignment: Program 1
*  Class: 4321-03, Spring 2003
*  Date: 02/17/2003
*
*  This is tokenizer for
*  Java version
*************************************************/

import java.io.*;	//for DataInputStream, DataOutputStream
import java.util.*; //for Vector

public class MyTokenizer
{
	private DataInputStream streamIn;	//input stream
	private DataOutputStream streamOut;	//output stream

	public MyTokenizer(InputStream ain, OutputStream aout)
	//constructor
	{
		if (ain!=null) streamIn=new DataInputStream(ain);
		if (aout!=null) streamOut=new DataOutputStream(aout);
	}

	public void putToken(String toPut) throws IOException, InterruptedIOException
	//write string to output stream
	//toPut: string to output
	{
		if (toPut!=null)
		//check if the string is valid
		{
			byte[] tmpBytes=toPut.getBytes();	//get bytes from string
			try
			{
				streamOut.write(tmpBytes,0,tmpBytes.length);	//write
				streamOut.flush();	//flush
			}
			catch(IOException e)
			{
				throw e;
			}
		}
	}

	public String getNextToken(String delimiters) throws IOException,InterruptedIOException
	//get token from input stream
	//delimiters: an array of delimiters
	{
		if (delimiters!=null)
		//check the delimiters
		{
			byte[] tmpBytes=delimiters.getBytes();	//get bytes
			byte byteRead;
			StringBuffer vRtn=new StringBuffer(10);	//store token into string buffer
			try
			{
				read_data:	//label for break out from nested loops
				while(true)
				//read until meets delimiters or error
				{
					byteRead=streamIn.readByte();	//read one byte
					for (int i=0;i<tmpBytes.length;i++)
					//compare with delimiters
						if (byteRead==tmpBytes[i]) break read_data;
					vRtn.append((char)byteRead); //append to string buffer
				}
			}
			catch(IOException e)
			{
				throw e;
			}
			String strRtn=vRtn.toString();	//convert to string
			return strRtn;
		}
		else
		//if the delimiters is null, return null
			return null;
	}
}