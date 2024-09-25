// Student Name: Danny Vo
// 		Section: 5
// 	  Student #: 501119407



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/*
 * This class manages, stores, and plays audio content such as songs, podcasts and audiobooks. 
 */
public class Library
{
	private ArrayList<Song> 			songs; 
	private ArrayList<AudioBook> 	audiobooks;
	private ArrayList<Playlist> 	playlists; 
	
  //private ArrayList<Podcast> 	podcasts;
	
	// Public methods in this class set errorMesg string 
	// Error Messages can be retrieved from main in class MyAudioUI by calling  getErrorMessage()
	// In assignment 2 we will replace this with Java Exceptions
	
	
	

	public Library()
	{
		songs 			= new ArrayList<Song>(); 
		audiobooks 	= new ArrayList<AudioBook>(); 
		playlists   = new ArrayList<Playlist>();
	  //podcasts		= new ArrayList<Podcast>(); ;
	}
	/*
	 * Download audio content from the store. Since we have decided (design decision) to keep 3 separate lists in our library
	 * to store our songs, podcasts and audiobooks (we could have used one list) then we need to look at the type of
	 * audio content (hint: use the getType() method and compare to Song.TYPENAME or AudioBook.TYPENAME etc)
	 * to determine which list it belongs to above
	 * 
	 * Make sure you do not add song/podcast/audiobook to a list if it is already there. Hint: use the equals() method
	 * If it is already in a list, set the errorMsg string and return false. Otherwise add it to the list and return true
	 * See the video
	 */
	public void download(AudioContent content)
	{
		if(content.getType().equals(Song.TYPENAME)){		//checks if it is of a song type
			for(Song songlist: songs){						//checks if content is in the song list
				if(songlist.equals(content)){				//if it is true then set the error message and return false
					throw new AudioContentAlreadyDownloadedException("Song "+ content.getTitle()+" already downloaded");
				}
			}
			Song content_cast = (Song) content;				//if it hasnt returned false then add cast content to song and add it to song list, return true
			songs.add(content_cast);
			System.out.println("SONG "+content_cast.getTitle()+" Added to Library");
	
		}

		else if(content.getType().equals(AudioBook.TYPENAME)){		//checks if it is of a AudioBook type
			for(AudioBook AudioBooklist: audiobooks){						//checks if content is in the AudioBook list
				if(AudioBooklist.equals(content)){				//if it is true then set the error message and return false
					throw new AudioContentAlreadyDownloadedException("AudioBook "+ content.getTitle()+" already downloaded");
				
				}
			}

			AudioBook content_cast = (AudioBook) content;				//if it hasnt returned false then add cast content to AudioBook and add it to AudioBook list, return true
			audiobooks.add(content_cast);
			System.out.println("AUDIOBOOK "+content_cast.getTitle()+" Added to Library");
			
		}
		
	}
	
	// Print Information (printInfo()) about all songs in the array list
	public void listAllSongs()
	{
		for (int i = 0; i < songs.size(); i++)				//loop through each song and print out their indexes starting at 1 followed by their info
		{
			int index = i + 1;
			System.out.print("" + index + ". ");
			songs.get(i).printInfo();
			System.out.println();	
		}
	}
	
	// Print Information (printInfo()) about all audiobooks in the array list
	public void listAllAudioBooks()
	{
		for(int i =0; i<audiobooks.size();i++){						//loop through each audiobook and print out their indexes starting at 1. followed by their info
			int index = i+1;
			System.out.print(index+". ");
			audiobooks.get(i).printInfo();
			System.out.println();
		}
	}
	
  // Print Information (printInfo()) about all podcasts in the array list
	public void listAllPodcasts()
	{
		
	}
	
  // Print the name of all playlists in the playlists array list
	// First print the index number as in listAllSongs() above
	public void listAllPlaylists()
	{
		for(int i =0;i<playlists.size();i++){									//loop through all playlists and print their indexes at 1. followed by their titles
			System.out.println((i+1)+". " + playlists.get(i).getTitle());
		}
	}
	
  // Print the name of all artists. 
	public void listAllArtists()
	{
		// First create a new (empty) array list of string 
		// Go through the songs array list and add the artist name to the new arraylist only if it is
		// not already there. Once the artist arrayl ist is complete, print the artists names
		ArrayList<String> all_artists = new ArrayList<String>();			//create new arraylist containing all the artists

		for(Song curr_song: songs){											//looping through each song and add the artist to new arraylist if not in it yet
			if(!all_artists.contains(curr_song.getArtist())){
				all_artists.add(curr_song.getArtist());
			}
		}
		for(int i=0;i<all_artists.size();i++){								//prints the names with indexes
			System.out.println((i+1)+". "+all_artists.get(i));
		}
		
	}

	// Delete a song from the library (i.e. the songs list) - 
	// also go through all playlists and remove it from any playlist as well if it is part of the playlist
	public void deleteSong(int index)
	{
		if(index<1 || index>playlists.size()){
			throw new AudioContentNotFoundException("Song Not Found");						// throws exception 
		}
		else{
			songs.remove(songs.get(index-1));												//remove song from songlist

			for(int i =0;i<playlists.size();i++)
			{
				for(int j=0;j<playlists.get(i).getContent().size();j++)						//loop through playlist then loop through the contents of each playlist
				{
					if(playlists.get(i).getContent().get(j).equals(songs.get(index-1)))		//if a song equals the song we are removing, remove the song from playlists
					{
						playlists.get(i).getContent().remove(songs.get(index-1));
					}
				}
			}
		}
		
		
	}
	
  //Sort songs in library by year
	public void sortSongsByYear()
	{
		// Use Collections.sort() 
		Collections.sort(songs, new SongYearComparator());								//sorts song list by year
	}
  // Write a class SongYearComparator that implements
	// the Comparator interface and compare two songs based on year
	private class SongYearComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			if (s1.getYear() < s2.getYear()) return -1;									//comparator interface for sorting songs by year
			if (s1.getYear() > s2.getYear()) return  1;
			return 0;
		}
	}

	// Sort songs by length
	public void sortSongsByLength()
	{
	 // Use Collections.sort() 
	 	Collections.sort(songs, new SongLengthComparator());							//sorts song list by length
	}
  // Write a class SongLengthComparator that implements
	// the Comparator interface and compare two songs based on length
	private class SongLengthComparator implements Comparator<Song>
	{
		public int compare(Song s1, Song s2) {
			if (s1.getLength() < s2.getLength()) return -1;								//comparator interface for sorting songs by length
			if (s1.getLength() > s2.getLength()) return  1;
			return 0;
		}
	}

	// Sort songs by title 
	public void sortSongsByName()
	{
	  // Use Collections.sort()
		// class Song should implement the Comparable interface
		// see class Song code
		Collections.sort(songs);														//sorts song by name
	}

	
	
	/*
	 * Play Content
	 */
	
	// Play song from songs list
	public void playSong(int index)
	{
		if (index < 1 || index > songs.size())
		{
			throw new AudioContentNotFoundException("Song Not Found");	
		}
		else{
			songs.get(index-1).play();
		}
		
	}
	
	// Play podcast from list (specify season and episode)
	// Bonus
	public boolean playPodcast(int index, int season, int episode)
	{
		return false;
	}
	
	// Print the episode titles of a specified season
	// Bonus 
	public boolean printPodcastEpisodes(int index, int season)
	{
		return false;
	}
	
	// Play a chapter of an audio book from list of audiobooks
	public void playAudioBook(int index, int chapter)
	{
		if (index < 1 || index > audiobooks.size() )
		{
			throw new AudioContentNotFoundException("AudioBook Not Found");			//throws exception to be caught in myaudioui
		}
		else if (chapter > audiobooks.get(index-1).getNumberOfChapters() || chapter < 1){
			throw new AudioContentNotFoundException("Chapter Not Found");
		}
		else{
			audiobooks.get(index-1).selectChapter(chapter);						//sets current chapter then plays it
			audiobooks.get(index-1).play();
		}
		
	}
	
	// Print the chapter titles (Table Of Contents) of an audiobook
	// see class AudioBook
	public void printAudioBookTOC(int index)
	{
		if(index<0 || index>audiobooks.size()-1){
			throw new AudioContentNotFoundException("AudioBook Not Found");	
		}
		else{
			audiobooks.get(index).printTOC();									//prints toc of a specific audiobook at given index
		}
	}
	
  /*
   * Playlist Related Methods
   */
	
	// Make a new playlist and add to playlists array list
	// Make sure a playlist with the same title doesn't already exist
	public void makePlaylist(String title)
	{
		Playlist newplaylist = new Playlist(title);							//new playlist with given title		
		for(Playlist curr_playlist: playlists){								//loop through playlists to see if new playlists exists already, if so set errormsg
			if(curr_playlist.equals(newplaylist)){
				throw new PlaylistAlreadyExistsException("Playlist "+title+" Already Exists");	
			}
		}
		
		playlists.add(newplaylist);											//else add newplaylist
		
	}
	
	// Print list of content information (songs, audiobooks etc) in playlist named title from list of playlists
	public void printPlaylist(String title)
	{
		boolean flag = false;												//using a boolean flag to determine whether or not to throw exception
		for(int i =0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(title)){					//loop through the playlist list then if a playlist title matches the given title 
				playlists.get(i).printContents();							//print the contents of that playlist
				flag = true;
			}
		}
		
		if(!flag){
			throw new PlaylistNotFoundException("Playlist Not Found");
		}
		
	}
	
	// Play all content in a playlist
	public void playPlaylist(String playlistTitle)
	{
		boolean flag = false;												//using a boolean flag to determine whether or not to throw exception
		for(int i =0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){
				for(int j=0; j<playlists.get(i).getContent().size();j++){	//loops through playlists, then finds the given plalist and play all its contents
					playlists.get(i).getContent().get(j).play();
					System.out.println();
					flag = true;
				}
			}
		}
		if(!flag){
			throw new PlaylistNotFoundException("Playlist Not Found");
		}
	}
	
	// Play a specific song/audiobook in a playlist
	public void playPlaylist(String playlistTitle, int indexInPL)
	{
		boolean flag = false;														//using a boolean flag to determine whether or not to throw exception
		for(int i =0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){					//loops through playlists to find one that matches the passed playlisttitle
				if(indexInPL<1 || indexInPL>playlists.get(i).getContent().size()){
					throw new PlaylistNotFoundException("Content Not Found at #: "+ indexInPL);
				}
				else{
					System.out.println(playlistTitle);								//print out the title then play the content at the inde given
					playlists.get(i).getContent().get(indexInPL-1).play();
					flag = true;
				}
			}
		}
		if(!flag){
			throw new PlaylistNotFoundException("Playlist Not Found");		//throw exception if playlist was not found
		}
	}
	
	// Add a song/audiobook/podcast from library lists at top to a playlist
	// Use the type parameter and compare to Song.TYPENAME etc
	// to determine which array list it comes from then use the given index
	// for that list
	public void addContentToPlaylist(String type, int index, String playlistTitle)
	{
		boolean flag = false;													//using a boolean flag to determine whether or not to throw exception

		for(int i=0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(playlistTitle)){				//loops through playlists to find the playlist same as the passed pltitle
				if(type.equalsIgnoreCase(Song.TYPENAME)){						//checks if the givevn type is a song, if so add it to playlists
					if(index<1 || index>songs.size()){
						throw new AudioContentNotFoundException("Content Not Found at #: "+ index);		//if index is not valid in songs, throw exception
					}
					else{
						playlists.get(i).addContent(songs.get(index-1));
						System.out.println("Song Added");
						flag = true;
					}
				}
				else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){				//checks if type is an audiobook, if so add it to playlists
					if(index<1 || index>audiobooks.size()){
						throw new AudioContentNotFoundException("Content Not Found at #: "+ index);	//if index in audiobooks is not valid throw exception
					}
					else{
					playlists.get(i).addContent(audiobooks.get(index-1));
					System.out.println("AudioBook Added");
					flag = true;
					}
				}
			}
			
		}
		if(!flag){
			throw new PlaylistActionException("Playlist Not Found");	//throw exception if playlist was not found
		}
		
	}

  // Delete a song/audiobook/podcast from a playlist with the given title
	// Make sure the given index of the song/audiobook/podcast in the playlist is valid 
	public void delContentFromPlaylist(int index, String title)
	{
		boolean flag = false;													//using a boolean flag to determine whether or not to throw exception
		for(int i=0;i<playlists.size();i++){
			if(playlists.get(i).getTitle().equals(title)){						//loop through playlist and finds a matching playlist with title
				if(index<1 || index>playlists.get(i).getContent().size()){
					throw new AudioContentNotFoundException("Content Not Found at #: "+ index);		// if index is not valid then throw exception 
				}
				else{
					playlists.get(i).deleteContent(index);							//if it finds it, delete the content at the specific index
					System.out.println("Content At Index ("+index+") Deleted From Playlist "+title);
					flag = true;
				}
			}
		}
		if(!flag){
			throw new PlaylistActionException("Playlist Was Not Found");				//throw exception if playlist title was not found
		}
	}

	
}

class AudioContentAlreadyDownloadedException extends RuntimeException{
	AudioContentAlreadyDownloadedException(String message){
		super("AudioContentAlreadyDownloadedException: "+message);		// TA recommended me to add a comment about the exception + the message
	}
}

class AudioContentNotFoundException extends RuntimeException{
	AudioContentNotFoundException(String message){
		super("AudioContentNotFoundException: "+message);
	}
}

class PlaylistAlreadyExistsException extends RuntimeException{
	PlaylistAlreadyExistsException(String message){
		super("PlaylistAlreadyExistsException: "+message);
	}
}
class PlaylistNotFoundException extends RuntimeException{
	PlaylistNotFoundException(String message){
		super("PlaylistNotFoundException: "+message);
	}
}
class PlaylistActionException extends RuntimeException{
	PlaylistActionException(String message){
		super("PlaylistActionException: "+message);
	}
}
class InvalidInputException extends RuntimeException{
	InvalidInputException(String message){
		super("InvalidInputException: "+message);
	}
}