// Student Name: Danny Vo
// 		Section: 5
// 	  Student #: 501119407


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;					//importing the necessary stuff for maps, scanning, files, arraylists, and exceptions
import java.util.Scanner;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore 
{
		private ArrayList<AudioContent> contents; 
		
		public AudioContentStore()
		{
			
			try {
				getContentList();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}

			//////////////////////////////////////////////////////////////////// MAPS /////////////////////////////////////////////////

			searchTitle();
			searchArtist();
			searchGenre();
		}



		Map<String, Integer> searchTitle()
		{

			Map<String, Integer> search = new HashMap<String, Integer>();		// <titles, index>

			for(int i=0; i<contents.size(); i++)								//iterates through each content 
			{								
				search.put(contents.get(i).getTitle(), i);						//creating a spot in the map for a title and its index
			}
			return search;

		}





		Map<String, List<Integer>> searchArtist() throws ClassCastException
		{

			Map<String, List<Integer>> search = new HashMap<String, List<Integer>>();		// <artists/authors, listofindexes>
			for(int i=0; i<contents.size(); i++)
			{
				List<Integer> indexes = new ArrayList<>();								//List of integers (indexes)
				if(contents.get(i).getType().equalsIgnoreCase(Song.TYPENAME))			//Adding all artists in SONGS
				{			
					Song songcontent = (Song) contents.get(i);							//casting the audio content to song to use getArtist()					
					if(!search.containsKey(songcontent.getArtist()))
					{																	//If the key is not yet in the map we create a spot for it <artist, list<int>>
						search.put(songcontent.getArtist(), indexes);			
					}
					search.get(songcontent.getArtist()).add(i+1);							//Add the index of the current/existing artist into the corresponding list																					
				}
				if(contents.get(i).getType().equalsIgnoreCase(AudioBook.TYPENAME))		//Adding all authors for AUDIOBOOKS
				{						
					AudioBook AudioBookcontent = (AudioBook) contents.get(i);			//casting the audio content to AudioBook to use getAuthor()
					if(!search.containsKey(AudioBookcontent.getAuthor()))
					{																	//If the key is not yet in the map we create a spot for it <author, list<int>>
						search.put(AudioBookcontent.getAuthor(), indexes);			
					}
					search.get(AudioBookcontent.getAuthor()).add(i+1);					//Adds index of current/existing author into the corresponding list																					
				}				
			}
			return search;
		}




		Map<Song.Genre, List<Integer>> searchGenre() throws ClassCastException{

			Map<Song.Genre, List<Integer>> search = new HashMap<Song.Genre, List<Integer>>();		// <titles, index>

			for(int i=0;i<contents.size();i++)
			{

				List<Integer> Gindexlist = new ArrayList<>();

				if(!contents.get(i).getType().equalsIgnoreCase(AudioBook.TYPENAME))			//grabbing all contents that are SONGS
				{

					Song genrecontent = (Song) contents.get(i);
					
					
					if(!search.containsKey(genrecontent.getGenre()))
					{																	//If the key is not yet in the map we create a spot for it <author, list<int>>
						search.put(genrecontent.getGenre(), Gindexlist);			
					}

					search.get(genrecontent.getGenre()).add(i+1);	

				}

			}
			
			return search;
		}


		private ArrayList<AudioContent> getContentList() throws FileNotFoundException {		//lets compiler know that code will throw this exception

			contents = new ArrayList<AudioContent>();


			File storetxt = new File("store.txt");						//opening file and creating scanner
			Scanner scanner = new Scanner(storetxt);
			
			while(scanner.hasNextLine())
			{
				String title, id,  type, artistORauthor, compORnarr, genre;		//initializing variables
				String lyrics = "";
				ArrayList<String> chaptitles = new ArrayList<String>();
				ArrayList<String> chapters = new ArrayList<String>();
				int year, length, linesTOread; 


				type = scanner.nextLine();										// reading all necessary info
				id = scanner.nextLine();        								
				title = scanner.nextLine();
				year = scanner.nextInt();
				length = scanner.nextInt();
				scanner.nextLine();
				artistORauthor = scanner.nextLine();
				compORnarr = scanner.nextLine();
				
				
				if(type.equalsIgnoreCase(Song.TYPENAME)){						// if the type is song, get linescount and lyrics then add new Song object to contents
					genre = scanner.nextLine();
					linesTOread = scanner.nextInt();							// reading how many lines to read for lyrics
					scanner.nextLine();

					for(int i = 0; i<linesTOread; i++){
						lyrics += scanner.nextLine()+"\n";
					}
					Song create = new Song(title, year, id, Song.TYPENAME, lyrics, length, artistORauthor, compORnarr, Song.Genre.valueOf(genre), lyrics);
					contents.add(create);
					System.out.println("Loading "+ Song.TYPENAME);
				}

				else if(type.equalsIgnoreCase(AudioBook.TYPENAME)){				// if type is audiobook (diff format than song) grab linecount, chaptitles, then chapters
					linesTOread = scanner.nextInt();							// reading chapter titles first
					scanner.nextLine();

					for(int i = 0; i<linesTOread; i++){
						chaptitles.add(scanner.nextLine());
					}

					while(scanner.hasNextInt()){								// getting line count then looping over the count grabbing each chapter line
						linesTOread = scanner.nextInt();
						scanner.nextLine();
	
						for(int i = 0; i<linesTOread; i++){
							chapters.add(scanner.nextLine());
						}
					}
					AudioBook create = new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, artistORauthor, compORnarr, chaptitles, chapters);
					contents.add(create);
					System.out.println("Loading "+ AudioBook.TYPENAME);
				}
				
				}
				scanner.close();
				return contents;
			}
		
		
		public AudioContent getContent(int index)
		{
			if (index < 1 || index > contents.size())
			{
				return null;
			}
			return contents.get(index-1);
		}
		
		public void listAll()
		{
			for (int i = 0; i < contents.size(); i++)
			{
				int index = i + 1;
				System.out.print("" + index + ". ");
				contents.get(i).printInfo();
				System.out.println();
			}
		}
		
		private ArrayList<String> makeHPChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("The Riddle House");
			titles.add("The Scar");
			titles.add("The Invitation");
			titles.add("Back to The Burrow");
			return titles;
		}
		
		private ArrayList<String> makeHPChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("In which we learn of the mysterious murders\r\n"
					+ " in the Riddle House fifty years ago, \r\n"
					+ "how Frank Bryce was accused but released for lack of evidence, \r\n"
					+ "and how the Riddle House fell into disrepair. ");
			chapters.add("In which Harry awakens from a bad dream, \r\n"
					+ "his scar burning, we recap Harry's previous adventures, \r\n"
					+ "and he writes a letter to his godfather.");
			chapters.add("In which Dudley and the rest of the Dursleys are on a diet,\r\n"
					+ " and the Dursleys get letter from Mrs. Weasley inviting Harry to stay\r\n"
					+ " with her family and attend the World Quidditch Cup finals.");
			chapters.add("In which Harry awaits the arrival of the Weasleys, \r\n"
					+ "who come by Floo Powder and get trapped in the blocked-off fireplace\r\n"
					+ ", blast it open, send Fred and George after Harry's trunk,\r\n"
					+ " then Floo back to the Burrow. Just as Harry is about to leave, \r\n"
					+ "Dudley eats a magical toffee dropped by Fred and grows a huge purple tongue. ");
			return chapters;
		}
		
		private ArrayList<String> makeMDChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Loomings.");
			titles.add("The Carpet-Bag.");
			titles.add("The Spouter-Inn.");
			return titles;
		}
		private ArrayList<String> makeMDChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("Call me Ishmael. Some years ago never mind how long precisely having little\r\n"
					+ " or no money in my purse, and nothing particular to interest me on shore,\r\n"
					+ " I thought I would sail about a little and see the watery part of the world.");
			chapters.add("stuffed a shirt or two into my old carpet-bag, tucked it under my arm, \r\n"
					+ "and started for Cape Horn and the Pacific. Quitting the good city of old Manhatto, \r\n"
					+ "I duly arrived in New Bedford. It was a Saturday night in December.");
			chapters.add("Entering that gable-ended Spouter-Inn, you found yourself in a wide, \r\n"
					+ "low, straggling entry with old-fashioned wainscots, \r\n"
					+ "reminding one of the bulwarks of some condemned old craft.");
			return chapters;
		}
		
		private ArrayList<String> makeSHChapterTitles()
		{
			ArrayList<String> titles = new ArrayList<String>();
			titles.add("Prologue");
			titles.add("Chapter 1");
			titles.add("Chapter 2");
			titles.add("Chapter 3");
			return titles;
		}
		
		private ArrayList<String> makeSHChapters()
		{
			ArrayList<String> chapters = new ArrayList<String>();
			chapters.add("The gale tore at him and he felt its bite deep within\r\n"
					+ "and he knew that if they did not make landfall in three days they would all be dead");
			chapters.add("Blackthorne was suddenly awake. For a moment he thought he was dreaming\r\n"
					+ "because he was ashore and the room unbelieveable");
			chapters.add("The daimyo, Kasigi Yabu, Lord of Izu, wants to know who you are,\r\n"
					+ "where you come from, how ou got here, and what acts of piracy you have committed.");
			chapters.add("Yabu lay in the hot bath, more content, more confident than he had ever been in his life.");
			return chapters;
		}
		
		
}
