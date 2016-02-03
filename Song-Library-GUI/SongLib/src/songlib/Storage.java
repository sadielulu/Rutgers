package songlib;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class Storage
{
	private ArrayList<Song> ar; //List to work with internally
	
	//This should work fine 
	public Storage(ArrayList<Song> ar) throws IOException
	{
		this.ar = ar;
	}

	//Storing a song in the file
	public void storeSongs()throws IOException
	{
		
		//Create a PrintWriter objects to store songs into Songlist.txt
		PrintWriter output = new PrintWriter("Songlist.txt");
		
		//Sort the Song objects in alphabetical order using compareTo<Song>
		Collections.sort(ar);
		
		//Store the songs element by element
		for(int i = 0; i < ar.size(); i++)
		{
			
			output.println(ar.get(i).toString());
		}
		
		//Close PrintWriter object
		output.close();
		

	}
	
	//GetSongs from file
	public void getSongs() throws IOException
	{
		File songFile = new File("Songlist.txt");
		Scanner inputFile = new Scanner(songFile);
	
		//While there are more lines in the songlist.txt file
		while(inputFile.hasNext())
		{
			/* This is a mess but ar.add adds to ArrayList<Song> 
			 * A new Song using toSong method to create a song obj from the tokens in the nextLine*/
			
			ar.add(new Song().toSong(inputFile.nextLine()));
		}
		
		//close inputFike
		inputFile.close();
		
	}
	

}
