             // Student Name: Danny Vo
// 		Section: 5
// 	  Student #: 501119407

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI extends Library
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your mylibrary
		AudioContentStore store = new AudioContentStore();
		
		// Create my music mylibrary
		Library mylibrary = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			try{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				mylibrary.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				mylibrary.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				mylibrary.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				mylibrary.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				mylibrary.listAllPlaylists(); 
			}
			// Download audiocontent (song/audiobook/podcast) from the store 
			// Specify the index of the content
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				int indexFrom = 0;
				int indexTo = 0;
				
				System.out.print("From Store Content #: ");
				
				if (scanner.hasNextInt())
				{
					indexFrom = scanner.nextInt();
					scanner.nextLine();

					System.out.print("To Store Content #: ");
				}
				else{
					throw new InvalidInputException("Invalid Input");		//if the input is smth other than integer throw exception
				}


				if (scanner.hasNextInt())
					{
						
						indexTo = scanner.nextInt();
						scanner.nextLine(); 									
					}
					
				else
				{
					throw new InvalidInputException("Invalid Input");		//if the input is smth other than integer throw exception
				}
				
				
				for(int i=indexFrom; i<=indexTo; i++){
					AudioContent content = store.getContent(i);
					if(indexFrom < 0 || store.getContent(indexTo)==null)
					{
						throw new AudioContentNotFoundException("Content Not Found in Store");
					}
						
					try {																		// doing try and catch inside the download for loop so it can catch
						mylibrary.download(content);											// multiple exceptions and print out the message
					} 
					catch (AudioContentAlreadyDownloadedException e) {
						System.out.println(e.getMessage());
					}
					catch (InvalidInputException e) {
						System.out.println(e.getMessage());
					}
					
							
				}
				
									
			}


			// Get the *library* index (index of a song based on the songs list)
			// of a song from the keyboard and play the song 
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				int index = 0;

				System.out.print("Song Number: ");						//grabs the next integer input from user
				if(scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				
				mylibrary.playSong(index);									// plays song with the index, throws exception
						
			}


			// Print the table of contents (TOC) of an audiobook that
			// has been downloaded to the library. Get the desired book index
			// from the keyboard - the index is based on the list of books in the library
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				int index = 0;
				System.out.print("Audio Book Number: ");
				if (scanner.hasNextInt()){										//checks that the input will be an integer
					index = scanner.nextInt();
					scanner.nextLine(); 										//consumes \n
				}
				
			
				mylibrary.printAudioBookTOC(index-1);							// prints TOC of book and throws exception

			}

			// Similar to playsong above except for audio book
			// In addition to the book index, read the chapter 
			// number from the keyboard - see class Library
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{
				int book_index = 0;
				int book_chap = 0;
				System.out.print("Audio Book Number: "); 				//grabbing keyboard input for book index
				if(scanner.hasNextInt()){
					book_index = scanner.nextInt();						//assigning book_index variable to the first input by user
				}
			

				System.out.print("Chapter: ");						//grab user input for chapter number
				if(scanner.hasNextInt()){
					book_chap = scanner.nextInt();
					scanner.nextLine();									//consumes \n
				}

				mylibrary.playAudioBook(book_index, book_chap);			// checks if the inputted book index and chapter number are valid 
																		// within the audiobook list and chapters of that audio book
																		// if not throw exception

			}


			// Print the episode titles for the given season of the given podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				
			}
			// Similar to playsong above except for podcast
			// In addition to the podcast index from the list of podcasts, 
			// read the season number and the episode number from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				
			}
			// Specify a playlist title (string) 
			// Play all the audio content (songs, audiobooks, podcasts) of the playlist 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				System.out.print("Playlist Title: ");		//gets playlist title from user and plays the whole playlist
				String title = "";
				if(scanner.hasNext()){
					title=scanner.nextLine();
				}
				mylibrary.playPlaylist(title);				//plays playlist, if playlist not found throws exception
			}

			// Specify a playlist title (string) 
			// Read the index of a song/audiobook/podcast in the playist from the keyboard 
			// Play all the audio content 
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				System.out.print("Playlist Title: ");
				String title = "";									//get title from user
				if(scanner.hasNext()){
					title=scanner.nextLine();
				}

				System.out.print("Content Number: ");
				int content_number = 0;
				if(scanner.hasNextInt()){							//get index from user
					content_number = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.playPlaylist(title, content_number);		//plays a specific content in playlist given title and index, if eitherplaylist or content not found
			}														// throw exception


			// Delete a song from the list of songs in mylibrary and any play lists it belongs to
			// Read a song index from the keyboard
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				System.out.print("Library Song #: ");				//gets song index
				int index = 0;
				if(scanner.hasNextInt()){
					index=scanner.nextInt();
					scanner.nextLine();
				}
				
					mylibrary.deleteSong(index);                 // deletes song from index, if index invalid throw exception
			
														
			}
			// Read a title string from the keyboard and make a playlist
			// see class Library for the method to call
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				System.out.print("Playlist Title: ");		//ask for playlist title
				String title = "";
				if(scanner.hasNext()){
					title = scanner.nextLine();
				}


				mylibrary.makePlaylist(title);					// makes playlist, if it already exists, throw exception
							
				
				
			}
			// Print the content information (songs, audiobooks, podcasts) in the playlist
			// Read a playlist title string from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				System.out.print("Playlist Title: ");						//ask for playlist title
				String title = "";
				if(scanner.hasNextLine()){
					title = scanner.nextLine();
				}
				mylibrary.printPlaylist(title);					// prints playlist, if playlist not found, throw exception
				
				
			}
			// Add content (song, audiobook, podcast) from mylibrary (via index) to a playlist
			// Read the playlist title, the type of content ("song" "audiobook" "podcast")
			// and the index of the content (based on song list, audiobook list etc) from the keyboard
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				System.out.print("Playlist Title: ");								//gets playlist title
				String title = "";
				if(scanner.hasNext()){
					title = scanner.nextLine();
				}
				System.out.println();
				String type = "";
				System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");	//gets type of content
				if(scanner.hasNext()){
					type = scanner.nextLine();
				}
				System.out.println();
				int index = 0;
				System.out.print("Library Content #: ");							//get index of the content to be added
				if(scanner.hasNextInt()){	
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.addContentToPlaylist(type, index, title);					//adds content to pl, if type, index, or title invalid then throw exception
				
				
				
			}
			// Delete content from play list based on index from the playlist
			// Read the playlist title string and the playlist index
		  // see class Library for the method to call
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				String title = "";
				int index =0;
				System.out.print("Playlist Title: ");								//gets title from user
				if(scanner.hasNext()){
					title = scanner.nextLine();
				}
				System.out.print("playlist Content #: ");							//gets index of content from user
				if(scanner.hasNextInt()){
					index = scanner.nextInt();
					scanner.nextLine();
				}
				mylibrary.delContentFromPlaylist(index, title);						//deletes content at index in playlist with the title, if either invalid throw exception
			}
			
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				mylibrary.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				mylibrary.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				mylibrary.sortSongsByLength();
			}


			////////////////////////////////////////////////////////////////////////////////// SEARCH & DOWNLOAD ///////////////////////////////////////////////////////////////////////////////

			else if (action.equalsIgnoreCase("SEARCH")){
				String title = "";
				System.out.print("Title: ");

				if(scanner.hasNext()){
					title = scanner.nextLine();									//user input
				}

				if(store.searchTitle().get(title) == null)						//if title does not exists in the keys, throw exception
					{						
						throw new AudioContentNotFoundException(title + " Not Found");
					}

				
				try{
					
					int index = store.searchTitle().get(title)+1;				//trys out code anyways, which prints the title
					System.out.print("" + index + ". ");
					store.getContent(index).printInfo();
					System.out.println();
				}
					
				
				catch(AudioContentNotFoundException e)						//prints out if there is an error
				{
					System.out.println(e.getMessage());
				}
				
			}


			else if (action.equalsIgnoreCase("SEARCHA")){
				String artist = "";
				System.out.print("Artist: ");

				if(scanner.hasNext()){
					artist = scanner.nextLine();										//getting user input for artist name
				}

				if(store.searchArtist().get(artist) == null){
					throw new AudioContentNotFoundException(artist +" Not Found");	//throw exception is artist is not found in keys
				}

				try {
					List<Integer> indexlist = store.searchArtist().get(artist);			//getting our list of indexes for the artist
					for(int index : indexlist){											//iterating through the indexes and printing out the info
						System.out.print(index + ". ");
						store.getContent(index).printInfo();
						System.out.println();
					}
				}

				catch(AudioContentNotFoundException e)
				{
					System.out.println(e.getMessage());									//if artist was null print a error
				}
			}


			else if (action.equalsIgnoreCase("SEARCHG")){
				String genre = "";
				System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

				if(scanner.hasNext()){
					genre = scanner.nextLine();
				}

				if(store.searchGenre().get(genre) == null){
					throw new AudioContentNotFoundException(genre +" Not Found");					//throw exception is artist is not found in keys
				}
				
				try {
					List<Integer> indexlist = store.searchGenre().get(Song.Genre.valueOf(genre));		//getting our list of indexes for the artist

					for(int index : indexlist){															//iterating through the indexes and printing out the info
						System.out.print(index + ". ");
						store.getContent(index).printInfo();
						System.out.println();
					}

				} catch (AudioContentNotFoundException e) {											//prints the exception message
					System.out.println(e.getMessage());
				}
				
			}




			else if (action.equalsIgnoreCase("DOWNLOADA")){
				String artist = "";
				System.out.print("Artist Name: ");

				if(scanner.hasNext()){
					artist = scanner.nextLine();
				}

				

				List<Integer> indexlist = store.searchArtist().get(artist);		//getting our list of indexes for the artist
				if(indexlist == null){											//checking if our artist is valid
					throw new AudioContentNotFoundException(artist+ " Not Found");
				}

			
				for(int index: indexlist){

					AudioContent content = store.getContent(index);
					try{
							
					mylibrary.download(content);			// doing try and catch inside the download for loop so it can catch ultiple exceptions and print out the message
					}
					catch(AudioContentAlreadyDownloadedException e){
							System.out.println(e.getMessage());
					}
					catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
				}
				
				}
				
				
			}



			else if (action.equalsIgnoreCase("DOWNLOADG")){
				String genre = "";
				System.out.print("Genre: ");

				if(scanner.hasNext()){
					genre = scanner.nextLine();
				}

				if(store.searchGenre().get(genre) == null){				//checking if genre is valid
					throw new AudioContentNotFoundException(genre + " Not Found");
				}

				
				List<Integer> indexlist = store.searchGenre().get(Song.Genre.valueOf(genre));
			
				for(int index: indexlist){

					AudioContent content = store.getContent(index);

					try{
					mylibrary.download(content);			// doing try and catch inside the download for loop so it can catch ultiple exceptions and print out the message
					}
					catch(AudioContentAlreadyDownloadedException e){
						System.out.println(e.getMessage());
					}
					catch(AudioContentNotFoundException e){
						System.out.println(e.getMessage());
					}
				}
				
				
			}	
				
		}
				
		

		catch (AudioContentAlreadyDownloadedException e) {		//catching all exceptions thrown by the code in try block which holds all if statements
			System.out.println(e.getMessage());
		 }
		 catch (AudioContentNotFoundException e) {
			System.out.println(e.getMessage());
		 }
		 catch (PlaylistAlreadyExistsException e) {
			System.out.println(e.getMessage());
		 }
		 catch (PlaylistNotFoundException e) {
			System.out.println(e.getMessage());
		 }
		 catch (PlaylistActionException e) {
			System.out.println(e.getMessage());
		 }
		 catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		 }

		 System.out.print("\n>");
	


}
}
}