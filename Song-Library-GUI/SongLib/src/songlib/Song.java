package songlib;

import java.util.StringTokenizer;

public class Song implements Comparable<Song>{

	String song, artist, album, year;
	
	public Song()
	{
		song = " ";
		artist = " ";
		album = " ";
		year = " ";
	}

	
	public Song(String song, String artist)
	{
		this.song = song;
		this.artist = artist;		
	}
	
	public Song(String song, String artist, String album)
	{
		this.song = song;
		this.artist = artist;
		this.album = album;
	}
	
	public Song(String song, String artist, String album, String year)
	{
		this.song = song;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}
	
	public void setSong(String s)
	{
		song = s;
	}
	
	public void setArtist(String a)
	{
		artist = a;
	}
	
	public void setAlbum(String a)
	{
		album = a;
	}
	
	public void setYear(String y)
	{
		year = y;
	}
	
	public String getSong()
	{
		return song;
	}
	
	
	public String getArtist()
	{
		return artist;
		
	}
	
	public String getAlbum()
	{
		return album;
	}
	
	public String getYear()
	{
		return year;
	}
	
	//This method takes a string of specific format i.e "song name; artist name; album name;", returns a new song
	public Song toSong(String s)
	{	
		StringTokenizer tk = new StringTokenizer(s, ";");
		//Take each consecutive token and set it to song,artist, album and year
		
		//if we got more than 1 token in the string
		if(tk.countTokens() > 1)
		{
			this.song = tk.nextToken();
			this.artist = tk.nextToken();
		}
		if(tk.countTokens() == 0)
		{
			return new Song(song,artist," "," ");
		}
		if(tk.countTokens() == 1)
		{
			if(s.contains(artist+";;"))
			{
				this.year = tk.nextToken();
				return new Song(song,artist," ", year);
			}
			else if(s.endsWith(";;"))
			{
				this.album = tk.nextToken();
				return new Song(song,artist,album, " ");
			}
		
		}
		else
		{
			this.album = tk.nextToken();
			this.year = tk.nextToken();
		}
		
		
		return new Song(song,artist,album,year);
	}
	
	//Format suitable for the SongList.txt file
	public String toString()
	{
		
		String str = song + ";" + artist + ";" + album + ";" + year + ";";
		
		return str;
	}
	
	

	@Override
	//Using just the String class' compareToIgnoreCase method 
	public int compareTo(Song s) {
	
		int result = this.song.compareToIgnoreCase(s.song);
		
		return result;
	}



}
