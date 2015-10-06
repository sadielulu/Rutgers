import java.io.*;
import java.net.*;

import javax.net.ssl.*;

	/**
		client sends to CLA and CTF , receives from CLA
		
		1. compile 
		2. java -Djavax.net.ssl.trustStore=mySrvKeystore -Djavax.net.ssl.trustStorePassword=123456 Client

	**/

public class Client {
	/**
	   user connects to CLA
	   sslsocket to send to CLA locahost, 12345, opens connection waits for random valid number
	   @throws IOException
	 */
	
	public static void connectToCLA() throws IOException{
	
		SSLSocketFactory clientf =(SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket clientSocket1 = (SSLSocket) clientf.createSocket("localhost",12345);

		//write to CLA
		PrintWriter out = new PrintWriter(clientSocket1.getOutputStream(), true);
		out.println("hello, validation number?");

		//read from CLA
		BufferedReader br = new BufferedReader(
                new InputStreamReader(clientSocket1.getInputStream())); 
		System.out.println(br.readLine()); //query of first and last name
		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		out.println(in.readLine()); //sends first and last name

		String string =null;		
		string = br.readLine(); //reads valid number
		System.out.println(string);	

		clientSocket1.close();
	}
	
	/**
	  user connects to CTF 
	  sslsocket send to ctf locahost,12346, sends vote + validation number
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public static void connectToCTF() throws UnknownHostException, IOException
	{
		SSLSocketFactory clientf =(SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket clientSocket2 = (SSLSocket) clientf.createSocket("localhost",12346);
		//write to CTF
		OutputStream os2 = clientSocket2.getOutputStream();
		
		//clientSocket2.close();
	}


	public static void main(String[]args)throws Exception{

		connectToCLA(); 
	
	}


	
}

