import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.net.ssl.*;

	/**
		CLA sends to CTF and Client, receives from Client 
		
		CLA server produces lists, sends to CTF 
		sends random valid number to client, receives query from client, for a random valid number 

		to generate certificates : keytool -genkey -keystore mySrvKeystore -keyalg RSA

		1.compile
		2.java -Djavax.net.ssl.keyStore=mySrvKeystore -Djavax.net.ssl.keyStorePassword=123456 CLA

	**/

public class CLA {
	
	static ArrayList<Integer> listOfValidationNumbers= new ArrayList<>();
	static ArrayList<String> recipients= new ArrayList<>();
	static ArrayList<User> trustedUsers= new ArrayList<>();

	//final static int CLAServerPort =12345;
	//final static String keyStoreFile= "server.keystore"; //the filename
	//final static String pathToStores ="/tmp/ssl/server"; //the directory
	//final static String passwd= "123456"; //password
	
	//listens to user and can send random valid number to user 
	public static void listensForClient() throws IOException
	{
		SSLServerSocketFactory CLAServerf =(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		SSLServerSocket CLAServer1 = (SSLServerSocket) CLAServerf.createServerSocket(12345);
		SSLSocket CLASocket1=(SSLSocket)CLAServer1.accept();
		//read from client
		InputStream is =CLASocket1.getInputStream();
	       	InputStreamReader isr = new InputStreamReader(is);
        	BufferedReader br = new BufferedReader(isr);
        	String string= br.readLine();
	        String name = null, lastName = null;
	        if(string.equalsIgnoreCase("hello, validation number?"))
        	{
        		InputStream nis = CLASocket1.getInputStream();
    			BufferedReader reader = new BufferedReader(new InputStreamReader(nis));
    			PrintWriter out =new PrintWriter(CLASocket1.getOutputStream(), true);
	   		//gets first and last name
    			out.println("first name and last name: ");
    			String line = null;
    			String fullName = null;
	    		line = reader.readLine();
    			fullName=line;
    			System.out.println(line);   
    		
    			if(!fullName.equals(null))
    			{
    				//parses line = name, lastname 
    				for(int i =0; i <fullName.length();i++)
    				{	
    					if(fullName.charAt(i)== ' ')
    					{
    						name =fullName.substring(0,i-1);
    						break;
    					}
    					lastName =fullName.substring(i+1,fullName.length()-1);
    				}    		
    				//creates user and adds user to list
    				int a;
    				if(( a =checkIfVotedAlready(name,lastName))==-1)
    				{
    					int x=(int)(Math.random()*100);  
    	    				//write back to user
		   	    		while(ifValidationNumberExists(x))
    	    				{
    	            				x=(int)(Math.random()*100);        	
    	    				}
    	    				out.println(x+"");
	    				User u = new User(name,lastName, x);
        				recipients.add(fullName);
        				trustedUsers.add(u);
        				listOfValidationNumbers.add(x);
	    			}
    				else //its in list but can still change their vote
    				{
    					out.println("already in list, just type your validation number to CTF");			
	    				//start CTF
    					//shutdownCTF
    				}
    			}
	        }
        	else
        	{
    			//CLASocket1.close();
    			//return;
        	}
		//CLASocket1.close();
		//return;
	}
	/**
	 * sends list to CTF 
	 * @throws IOException
	 */
	public static void connectToCTF() throws IOException{
		//ObjectOutputStream objectOutput;

		SSLSocketFactory s2 =(SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket Socket2 = (SSLSocket) s2.createSocket("localhost",3333);
	        InputStream inputstream = Socket2.getInputStream();
        	InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
         	BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
 		PrintWriter out =new PrintWriter(Socket2.getOutputStream(), true);
 		out.println("can CTF see this");
		//SSLServerSocketFactory CLAServerf =(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		//SSLServerSocket CLAServer2 = (SSLServerSocket) CLAServerf.createServerSocket(12347);
		//SSLSocket CLASocket2=(SSLSocket)CLAServer2.accept();
		//InputStream is =CLASocket2.getInputStream();
		
		//write list to CTF
		//OutputStream os2 = Socket2.getOutputStream();
		//PrintWriter out2 =new PrintWriter(os2, true);

		//gets first and last name
		//out2.println("can CTF see this ");
		//converts validation list to bytes and sends to CTF
		
		//CLASocket.close();
	}
	public static int checkIfVotedAlready(String name, String lastName){
		for(int i=0; i<trustedUsers.size(); i++)
		{
			if (trustedUsers.get(i).name.equalsIgnoreCase(name)&& trustedUsers.get(i).lastName.equalsIgnoreCase(lastName))
			{
				return i;			
			}
		}
		return -1; //did not vote 
	}
	
	public static boolean ifValidationNumberExists(int x){
		for(int i =0 ; i< listOfValidationNumbers.size();i++)
		{
			Integer n =listOfValidationNumbers.get(i);
			return true;
		}
		return false;
	}
	public static void main(String[]args)throws Exception{
		listensForClient();
		connectToCTF(); //uncomment when CTF is set up
		//String trustFilename=pathToStores+"/" +keyStoreFile;
		//System.setProperty("javax.net.ssl.keystore",trustFilename);
		//System.setProperty("javax.net.ssl.keyStorePassword", passwd);
		//java -Djavax.net.ssl.keystore=/tmp/trustFilename
		//-Djavax.net.ssl.keystorePassword=123456 CLA
		//new CLA().initServer();
	}	
}
	
	

