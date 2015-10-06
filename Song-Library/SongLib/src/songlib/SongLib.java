package songlib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;

import songlib.SongLibGui.ListListener;

/**
 * 
 * @author Silvia Carbajal
 * @author Oskar Bero
 *
 */

public class SongLib {

	static SongLib songLib;
	static ArrayList<Song> songList = new ArrayList<Song>();
	//Song s;
	SongLibGui sl;



	public SongLib(String name) throws IOException
	{	
		sl=new SongLibGui(name);
		//load songs from storage 		
	}

	//changed it to take a list argument so it can stay static
	public static void addSong(Song s, DefaultListModel<String> list) {
		//add song to list
		for(int i =0; i <list.size();i++){
			Song ss=songList.get(i);
				if(s.getSong().equals(ss.getSong())&&s.getArtist().equals(ss.getArtist())){
 						SongLibGui.errorMessage();
						return;
				}
			}
		
		if(!(songList.contains(s)))
		{
			//add to array list
			songList.add(s);
			//alphabetize 
			Collections.sort(songList);
			//add to list
			list.add(songList.indexOf(s), s.getSong());
		}
	}
	
	public static void deleteSong(Song s, DefaultListModel<String> list) {
		//delete song from list 
		if(songList.contains(s))
		{
			list.removeElement( s.getSong());
			songList.remove(s);
			
			Collections.sort(songList);
			//this should already remove it from the list since the reference in the array is removed		
			
		}
	}
	
	

	
	public static void storeList(Storage save) throws IOException
	{
		save = new Storage(songList);
		
		save.storeSongs();
		
	}
	
	public static void getList(DefaultListModel<String> list) throws IOException
    {
        Storage save = new Storage(songList);
        
        save.getSongs();
        
        for(int i = 0; i < songList.size(); i++)
        {
            list.add(i, (songList.get(i).getSong()));
        }
    }
	
	public static Song getSelectedSong() {

		Song getSong = null;	
		int y=	SongLibGui.getSelectedSongIndex();
		if(y!=-1)
		{
			getSong=songList.get(y);
		}	
		return getSong;
		
	}
	
	
	public static void main(String[] args) throws IOException
	{
		songLib	= new SongLib("song library");
		
		
	}


	
}
