import java.io.*;
import java.net.*;
	public class Client 
	{
		static long startTime ;
    		public static void main(String[] args) throws IOException {
 	  	String hostName ;
 	  	int portNumber;
 	  	PrintWriter out=null;
 	  	BufferedReader in=null;
 	  	BufferedReader stdIn=null ;
 	  	Socket socket=null;
		//argument needs to be 2 args 
      		//  if (args.length >1)
        	//{
	  	//	hostName = args[0];
        	//	portNumber = Integer.parseInt(args[1]);
        	//}
        	//else 
        	//{
		//	return;
        	//}
        	try
        	{
			socket= new Socket("128.6.36.180",12345);
	      		out = new PrintWriter(socket.getOutputStream(),true);  //to write
	      		in =  new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read
			stdIn = new BufferedReader(new InputStreamReader(System.in)); //from user
			startTime = System.currentTimeMillis();
		}
		catch (IOException e) 
		{
			System.err.println("couldnt create anything");
	        	return;
	    	}
	     		//   String userInput;
			//while ((userInput= stdIn.readLine())!= null) { //keeps going until no more from user
			//	if (userInput.equals("#") || userInput.equals("$")){
					//# or $ exit program
					//PRINT TOTAL 
			//		out.close();
			//		in.close();
			//		stdIn.close();		
			//		socket.close();
			//		break;
			//	}
    		out.println(""+startTime); //DONT KNOW NEED TO CHECk
   	 	System.out.println("total time: " + in.readLine()); //Read from server & Print to user
		//}
		//Close all the sockets and buffers.
		out.close();
		in.close();
		stdIn.close();		
		socket.close();
	}
	
		
        

}
