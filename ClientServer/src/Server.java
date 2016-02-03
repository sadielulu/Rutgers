import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(12345);
	//Socket socket.bind(new InetSocketAddress("128.6.36.180", 12345));
	Socket socket=server.accept();
        long time = System.currentTimeMillis();
	//read from client
	InputStream is =socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String string= br.readLine();
       	long startTime= Long.parseLong(string, 10);  //CONVERT from STRING TO long START TIME
        long total = time - startTime;
    	//write back to user
    	PrintWriter out =new PrintWriter(socket.getOutputStream(), true);
    	out.println(""+total);
    	//InputStream nis = socket.getInputStream();
    	//BufferedReader reader = new BufferedReader(new InputStreamReader(nis));
    }
} 
