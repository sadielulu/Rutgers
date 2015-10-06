	import java.net.*;
import java.io.*;
import java.util.*;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

	public class CTF extends Thread {
		
		private ArrayList<Integer> idList;
		private int[] voteTally;
		private Hashtable<Integer, Integer> successfulVotes;
		private Hashtable<Integer, Boolean> alreadyVoted;
		Random randomGen;
		private SSLServerSocket sslServerSocket;
		
	//	public static void main(String args[]) throws IOException{
/**
		      SSLServerSocketFactory sslserversocketfactoryCLA =
	                    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	            SSLServerSocket sslserversocketCLA =
	                    (SSLServerSocket) sslserversocketfactoryCLA.createServerSocket(3333);
	            SSLSocket sslsocketCLA = (SSLSocket) sslserversocketCLA.accept();
	            
	            OutputStream outputstream = sslsocketCLA.getOutputStream();
	   	        OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream);
	   	        BufferedWriter bufferedwriter = new BufferedWriter(outputstreamwriter);
	   	        InputStream inputstream = sslsocketCLA.getInputStream();
	   	        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	   	        BufferedReader bufferedreader = new BufferedReader(inputstreamreader); //take these lines out and it runs
	   	        System.out.println(bufferedreader.readLine());							//take these lines out and it runs but doesnt read from CLA, problem
	   	        System.out.println("CLA Connection accepted from: "+sslsocketCLA.getInetAddress());
	   	        
**/
		//}
		
		public CTF () throws IOException {
			randomGen = new Random();
			successfulVotes = new Hashtable<Integer, Integer>();
			alreadyVoted = new Hashtable<Integer, Boolean>();
			voteTally = new int[5];
			sslServerSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket(3333);
		}
		
	/*
		//open socket for CLA
		try {
			SSLServerSocketFactory CTFServerf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			SSLServerSocket CTFServer1 = (SSLServerSocket) CTFServerf.createServerSocket(12345);
			SSLSocket CTFSocket1 = (SSLSocket) CTFServer1.accept();

			//From CLA
			ObjectInputStream CLAis = (ObjectInputStream) CTFSocket1.getInputStream();
			InputStreamReader CLAisr = new InputStreamReader(CLAis);
			BufferedReader CLAbr = new BufferedReader(CLAisr);

			//To CLA
			OutputStream CLAos = CTFSocket1.getOutputSteam();
			OutputStreamWriter CLAosw = new OutputStreamWriter(CLAos);
			BufferedWriter CLAbw = new BufferedWriter(CLAosw);

			System.out.println("Connection established with CLA. Address: " + CTFServer1.getInetAddress());

			//open socket for Voter
			SSLServerSocketFactory VoterServerf = (SSLServerSocketFactory) SSLServerSocketFactor.getDefault();
			SSLServerSocket VoterServer1 = (SSLServerSocket) VoterServerf.createServerSocket(12345);
			SSLSocket VoterSocket1 = (SSLSocket) VoterServer1.accept();

			//From Voter
			ObjectInputStream Voteris = (ObjectInputStream) VoterSocket1.getInputStream();
			InputStreamReader Voterisr = new InputStreamReader(Voteris);
			BufferedReader Voterbr = new BufferedReader(Voterisr);

			//To Voter
			OutputStream Voteros = VoterSocket1.getOutputSteam();
			OutputStreamWriter Voterosw = new OutputStreamWriter(Voteros);
			BufferedWriter Voterbw = new BufferedWriter(Voterosw);
		}
	*/
		//Open for CLA
	

		public boolean boot() {
			String serverName = "localhost";
			String temp = "3333";
			SSLSocketFactory CTFServerf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			int port = Integer.parseInt(temp);
			try {
				SSLSocket CTFSocket1 = (SSLSocket) CTFServerf.createSocket("localhost", 3333);
				System.out.println("Connecting to: " + serverName + "on port: " + port);
				System.out.println("Connected to: " + CTFSocket1.getRemoteSocketAddress());
				OutputStream CTFos = CTFSocket1.getOutputStream();
				DataOutputStream CTFdos = new DataOutputStream(CTFos);
				//Write two bytes of length information to the output stream, followed by the modified UTF-8 representation of every character in the string
				CTFdos.writeUTF("CTF");
			} catch (IOException e) {
				System.out.println("CLA Server not running");
				return false;
			}
			return true;
		}

		public void run() {
			boot();
			while(true) {
				try {
					System.out.println("Waiting for client on port: " + sslServerSocket.getLocalPort());
					Socket server = sslServerSocket.accept();
					System.out.println("Connected to: " + server.getRemoteSocketAddress());
					DataInputStream in = new DataInputStream(server.getInputStream());
					String input = new String();
					input = in.readUTF();
					if (input.equals("CLA")) {
						ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
						Object obj = new Object();
						try {
							obj = ois.readObject();
							ArrayList<Integer> obj2 = (ArrayList<Integer>) obj;
							idList = obj2;
							for(int x = 0; x < idList.size(); x++) {
								alreadyVoted.put(idList.get(x), false);
							}
							System.out.println(idList.toString());
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}		
					}

					else if (input.equals("done")) {
						System.out.println("Votes: " + voteTally[0] + "|"
								+ voteTally[1] + "|" + voteTally[2] + "|"
								+ voteTally[3] + "|" + voteTally[4] + "|");
						System.out.println("All users: "
								+ alreadyVoted.toString());
						System.out.println("closing");
						break;
					}
					else {
						StringTokenizer strtok = new StringTokenizer(input, ",");
						int claID = Integer.parseInt(strtok.nextToken().replaceAll(
								"\\s", ""));
						int userID = Integer.parseInt(strtok.nextToken()
								.replaceAll("\\s", ""));
						int voteNum = Integer.parseInt(strtok.nextToken()
								.replaceAll("\\s", ""));
						System.out.println("claID: " + claID + " userID: " + userID
								+ " voteNum: " + voteNum);
						boolean registeredUser = false;
						for (int x = 0; x < idList.size(); x++) {
							if (claID == idList.get(x)) {
								registeredUser = true;
								break;
							}
						}
						DataOutputStream out = new DataOutputStream(server.getOutputStream());
						if (registeredUser) {
							if (alreadyVoted.get(claID) == false) {
								if (successfulVotes.containsKey(userID) == false) {
									voteTally[voteNum]++;
									alreadyVoted.put(claID, true);
									successfulVotes.put(userID, voteNum);
									out.writeUTF("success");
								}
								else {
									out.writeUTF("that user ID already exists");
								}
							}
							else {
								out.writeUTF("that validation ID already voted");
							}
						}
						else {
							out.writeUTF("not a registered validation number");
						}
						System.out.println("Votes: " + voteTally[0] + "|"
								+ voteTally[1] + "|" + voteTally[2] + "|"
								+ voteTally[3] + "|" + voteTally[4] + "|");
						System.out.println("Users that voted: " + alreadyVoted.toString());
						System.out.println("Successful Votes: "
								+ successfulVotes.toString());
						System.out.println(input);
					}
					server.close();
				} catch (SocketTimeoutException s) {
					System.out.println("Socket timed out!");
					break;
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
			}
		}	




					
		public static void main(String[] args) {
			try{	
				Thread t = new CTF();
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

			/**
		 * user can vote again before the time is up or threshold is met but it replaces his last vote so he cant vote again
		 * @param name
		 * @param lastName
		 * @param ID
		 * @param validN
		 * @param vote
		 */

		public boolean checkIfVotedAlready(String name, String lastName,int ID, int validN, String vote){
			
			for(int i=0; i<CLA.trustedUsers.size(); i++)
			{
				if (CLA.trustedUsers.get(i).name.equalsIgnoreCase(name)&& CLA.trustedUsers.get(i).lastName.equalsIgnoreCase(lastName))
				{
					CLA.trustedUsers.get(i).setID(ID);
					CLA.trustedUsers.get(i).setValidNum(validN);
					CLA.trustedUsers.get(i).setVote(vote);
					return true;
				}

			}
			return false; //did not vote , not even in list 
		}
		
		//whats the point of this if users have to get a valid number to vote in the interface, in the first place?
		//more authentication?
		public boolean canVote(int validNum, String name, String lastName){
			
			for(int i=0; i<CLA.trustedUsers.size(); i++)
			{
				if(CLA.trustedUsers.get(i).getValidNum()==validNum)
				{
					return false; //cant vote 
				}
			}
			return false; //can vote 
		}
		
		//list is going to have = ID numbers and who the user(ID numbers) voted for , people cant determine who has voted for who
		//posted at end , so people cant copy, and users can see their vote has been counted 
		
	}

