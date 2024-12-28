/*
 * @author Alan Wang netid: aw795
 */

package src.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
// import javafx.scene.Node;
// import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Optional;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import src.app.songDetails;
import src.app.songDetailsComparator;

public class OverhaulController {

	@FXML Button Add;
	@FXML Button Edit;
	@FXML Button Delete;
	@FXML Button Save;
	
	@FXML Label SD_Album;
	@FXML Label SD_Title;
	@FXML Label SD_Artist;
	@FXML Label SD_Year;
	
	@FXML Label DD_Album;
	@FXML Label DD_Title;
	@FXML Label DD_Artist;
	@FXML Label DD_Year;
	
	@FXML TextField Field_Zero;
	@FXML TextField Field_One;
	@FXML TextField Field_Two;
	@FXML TextField Field_Tri;
	
	@FXML Tooltip Album_Tip;
	@FXML Tooltip Title_Tip;
	@FXML Tooltip Artist_Tip;
	@FXML Tooltip Year_Tip;
	
	@FXML ListView<songDetails> dataList;
	@FXML ImageView imageView;

	private ObservableList<songDetails> songList;
	private File dataPSV;
	private Bounds boundsInScreen;
	private ArrayList<songDetails> objectSongList = new ArrayList<songDetails>();
	
	public void makeError(String title, String header, String content, double xCord, double yCord) {
		Alert error = new Alert(AlertType.ERROR);
		error.setTitle(title);
		error.setHeaderText(header);
		error.setContentText(content);
		error.setX(xCord);
		error.setY(yCord);
		error.showAndWait();
	}
	
	public Optional<ButtonType> makeConfirmation(String title, String header, String content, double xCord, double yCord) {
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle(title);
		confirm.setHeaderText(header);
		confirm.setContentText(content);
		confirm.setX(xCord);
		confirm.setY(yCord);
		return confirm.showAndWait();
	}

	public void addAction(ActionEvent e) {

		boundsInScreen = dataList.localToScreen(Add.getBoundsInLocal());
		
		double xLoc = boundsInScreen.getMinX() + (dataList.getWidth() / 4);
		double yLoc = boundsInScreen.getMinY();
		
		boolean titleCheck = Field_Zero.getText().isEmpty();
		boolean artistCheck = Field_One.getText().isEmpty();
		boolean yearCheck = Field_Two.getText().isEmpty();
		boolean albumCheck = Field_Tri.getText().isEmpty();
		String songTitle = "";
		String artist = "";
		String year = "";
		String album = "";
		String[] entry = new String[4];
		songDetails songEntry = new songDetails();

		if (titleCheck || artistCheck) {
			makeError("Error", "Missing Information", "Title and Artist must be filled out", xLoc, yLoc);
			return; // way to just stop the code as i haven't added the rest of the conditions
		}

		if (!titleCheck) songTitle = Field_Zero.getText().trim();
		if (!artistCheck) artist = Field_One.getText().trim();
		if (!yearCheck) year = Field_Two.getText().trim();
		if (!albumCheck) album = Field_Tri.getText().trim();

		if (songTitle.contains("|") || artist.contains("|") || album.contains("|")) {
			makeError("Error", "Illegal Character Found", "There can't be a '|' in any of the fields", xLoc, yLoc);
			return; // way to just stop the code as i haven't added the rest of the conditions
		}

		// handling all inputs as strings... might have to change this later
		System.out.printf("Title: %s - Artist: %s \t Year: %s Album: %s \n", songTitle, artist, year, album);

		entry[0] = songTitle;
		entry[1] = artist;
		entry[2] = year;
		entry[3] = album;

		songEntry.setTitle(songTitle);
		songEntry.setArtist(artist);
		songEntry.setYear(year);
		songEntry.setAlbum(album);
		
		// String for confirmation content text
		String songConfirmationDetails = "";
		songConfirmationDetails = "Do you want to add song: \n";
		songConfirmationDetails += "Title: " + songTitle + "\n";
		songConfirmationDetails += "Artist: " + artist + "\n";
		songConfirmationDetails += "Year: " + year + "\n";
		songConfirmationDetails += "Album: " + album;
		
		Optional<ButtonType> option = makeConfirmation("Confirm Song Entry", "Confirm Entry", songConfirmationDetails, xLoc, yLoc);

		if (option.get() == ButtonType.OK) {
			if(!checkDuplicate(songEntry)) {
				sortCollection(songEntry);
				sortDisplay(songEntry);
				resetDisplay(songEntry);
				// saveSong(songEntry);
				regenerateFile();
			} else {
				makeError("Error", "Duplicate Song Entry","Can't enter a duplicate song", xLoc, yLoc);
				
			}
		} else {
			System.out.println("Canceled song entry");
		}

	}

	public void editAction(ActionEvent e) {

		boundsInScreen = dataList.localToScreen(Add.getBoundsInLocal());
		
		double xLoc = boundsInScreen.getMinX() + (dataList.getWidth() / 4);
		double yLoc = boundsInScreen.getMinY();

		boolean titleCheck = Field_Zero.getText().isEmpty();
		boolean artistCheck = Field_One.getText().isEmpty();
		boolean yearCheck = Field_Two.getText().isEmpty();
		boolean albumCheck = Field_Tri.getText().isEmpty();
		String songTitle = "";
		String artist = "";
		String year = "";
		String album = "";
		if (!titleCheck) songTitle = Field_Zero.getText().trim();
		if (!artistCheck) artist = Field_One.getText().trim();
		if (!yearCheck) year = Field_Two.getText().trim();
		if (!albumCheck) album = Field_Tri.getText().trim();

		if (titleCheck || artistCheck) {
			makeError("Error", "Missing Information", "Title and Artist must be filled out", xLoc, yLoc);
			updateFieldText();
			return; // way to just stop the code as i haven't added the rest of the conditions
		}

		songDetails song = dataList.getSelectionModel().getSelectedItem();

		if(song == null) {
			makeError("Error", "No song selected", "No song found for editing", xLoc, yLoc);
			return;
		}
		
		if (songTitle.contains("|") || artist.contains("|") || album.contains("|")) {
			makeError("Error", "Illegal Character Found", "There can't be a '|' in any of the fields", xLoc, yLoc);
			updateFieldText();
			return; // way to just stop the code as i haven't added the rest of the conditions
		}

		// String for confirmation content text
		String songConfirmationDetails = "";
		songConfirmationDetails = "Do you want to edit song to: \n";
		songConfirmationDetails += "Title: " + songTitle + "\n";
		songConfirmationDetails += "Artist: " + artist + "\n";
		songConfirmationDetails += "Year: " + year + "\n";
		songConfirmationDetails += "Album: " + album;

		Optional<ButtonType> option = makeConfirmation("Confirm Song Edit", "Confirm Edit", songConfirmationDetails, xLoc, yLoc);

		if (option.get() == ButtonType.OK) {
			songDetails oldSong = new songDetails(song.getTitle(), song.getArtist(), song.getYear(), song.getAlbum());

			songDetails temp = new songDetails(songTitle, artist, year, album);

			int songIndex = getObjectIndex(oldSong); 
			checkIndex(songIndex);
			objectSongList.remove(songIndex);

			if(!checkDuplicate(temp)) {
				sortCollection(temp);
				resetDisplay(song);

				song.setTitle(songTitle);
				song.setArtist(artist);
				song.setYear(year);
				song.setAlbum(album); 

				updateFieldText();
				regenerateFile();
			} else {
				sortCollection(oldSong);

				song.setTitle(oldSong.getTitle());
				song.setArtist(oldSong.getArtist());
				song.setYear(oldSong.getYear());
				song.setAlbum(oldSong.getAlbum()); 

				makeError("Error", "Duplicate Song Entry", "Can't enter a duplicate song", xLoc, yLoc);
			}
		} else {
			System.out.println("Canceled song edit");
		}
	}

	public void saveAction(ActionEvent e) {
		
		boundsInScreen = dataList.localToScreen(Add.getBoundsInLocal());
		
		double xLoc = boundsInScreen.getMinX() + (dataList.getWidth() / 4);
		double yLoc = boundsInScreen.getMinY();
		
		
		System.out.println("Force File Save");

		Optional<ButtonType> option = makeConfirmation("Confirmation", "Confirm Save", "Do you want to save all the current songs?", xLoc, yLoc);

		if (option.get() == ButtonType.OK) {
			regenerateFile();
		} else {
			System.out.println("Canceled Force Save function");
		}
		
	}

	public int getObjectIndex(songDetails target) {
		ListIterator<songDetails> songIterator = objectSongList.listIterator();
		int songIndex = 0;

		while (songIterator.hasNext()) {
			songIndex = songIterator.nextIndex();
			songDetails songObject = songIterator.next();

			if (target.getLowerTitle().equals(songObject.getLowerTitle()) && target.getLowerArtist().equals(songObject.getLowerArtist())) {
				return songIndex;
			}
		}	
		return -1;
	}

	public int getSongIndex(songDetails target) {
		ListIterator<songDetails> songIterator = songList.listIterator();
		int songIndex = 0;

		while (songIterator.hasNext()) {
			songIndex = songIterator.nextIndex();
			songDetails songObject = songIterator.next();

			if (target.getLowerTitle().equals(songObject.getLowerTitle()) && target.getLowerArtist().equals(songObject.getLowerArtist())) {
				return songIndex;
			}
		}	
		return -1;
	}

	public void checkIndex(int idx) { 
		if(idx < 0 || idx > objectSongList.size() || idx > songList.size() ) { 
			System.out.println("BOOM");
			System.exit(0);
		}
	}

	public void deleteAction(ActionEvent e) {

		boundsInScreen = dataList.localToScreen(Add.getBoundsInLocal());
		
		double xLoc = boundsInScreen.getMinX() + (dataList.getWidth() / 4);
		double yLoc = boundsInScreen.getMinY();

		songDetails song = dataList.getSelectionModel().getSelectedItem();

		if(song == null) {
			makeError("Error", "No song selected", "No song found for deletion", xLoc, yLoc);
			return;
		}

		String songTitle = song.getTitle();
		String artist = song.getArtist();
		String year = song.getYear();
		String album = song.getAlbum();

		// String for confirmation content Text
		String songConfirmationDetails = "";
		songConfirmationDetails = "Do you want to delete the song: \n";
		songConfirmationDetails += "Title: " + songTitle + "\n";
		songConfirmationDetails += "Artist: " + artist + "\n";
		songConfirmationDetails += "Year: " + year + "\n";
		songConfirmationDetails += "Album: " + album;
		
		Optional<ButtonType> option = makeConfirmation("Confirm Song Deletion", "Confirm Deletion", songConfirmationDetails, xLoc, yLoc);

		if (option.get() == ButtonType.OK) {
			int songIndex = getSongIndex(song);
			songDetails udex = updateSelect(songIndex);

			checkIndex(songIndex);
			songList.remove(songIndex);
			regenerateFile();

			if(udex != null) {
				dataList.getSelectionModel().select(udex);
				updateFieldText();
			}

		} else {
			System.out.println("Canceled song delete");
		}

	}

	public songDetails updateSelect(int idx) {
		if(idx + 1 <= songList.size()-1) {
			return songList.get(idx + 1); 
		} else if(idx - 1 >= 0) {
			return songList.get(idx-1);
		} else { 
			return null;
		}
	}


	public void parsefile() {

		try {
			dataPSV = new File("./data/songs.txt");
			Scanner myReader = new Scanner(dataPSV);
			while (myReader.hasNextLine()) {
				String songInformation = myReader.nextLine();
				if (songInformation.isEmpty())
					continue;
				validateEntry(songInformation);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public void validateEntry(String line) {
		String[] LineSplit = line.split(Pattern.quote("|"));
		if(LineSplit.length < 2) {
			System.out.println("Illegal entry found in session file\nPlease fix and restart");
			System.exit(0);
		}

		if (LineSplit.length > 4) {
			System.out.println("Illegal entry found in session file\nPlease fix and restart");
			System.exit(0);

		} else {
			String[] cleanEntry = new String[4];
			songDetails song = new songDetails();

			switch(LineSplit.length) {
				case 2: 
					song.setTitle(LineSplit[0].trim());
					song.setArtist(LineSplit[1].trim());
					song.setYear("");
					song.setAlbum("");
					cleanEntry[0] = LineSplit[0].trim();
					cleanEntry[1] = LineSplit[1].trim();
					cleanEntry[2] = "";
					cleanEntry[3] = "";
					break; 
				case 3: 
					song.setTitle(LineSplit[0].trim());
					song.setArtist(LineSplit[1].trim());
					song.setYear(LineSplit[2].trim());
					song.setAlbum("");
					cleanEntry[0] = LineSplit[0].trim();
					cleanEntry[1] = LineSplit[1].trim();
					cleanEntry[2] = LineSplit[2].trim();
					cleanEntry[3] = "";
					break;
				default: 
					song.setTitle(LineSplit[0].trim());
					song.setArtist(LineSplit[1].trim());
					song.setYear(LineSplit[2].trim());
					song.setAlbum(LineSplit[3].trim());
					cleanEntry[0] = LineSplit[0].trim();
					cleanEntry[1] = LineSplit[1].trim();
					cleanEntry[2] = LineSplit[2].trim();
					cleanEntry[3] = LineSplit[3].trim();
					break; 
			}

			// internalSongList.add(cleanEntry);

			int test = 0;
			if(song.getYear() != "" || song.getYear() != "" ) { 
				char[] characters = song.getYear().toCharArray();
				for(Character str : characters) { 
				try { 
					if(!Character.isDigit(str)) { 
						System.out.println("Invalid Year Input from file");
						System.exit(0);

					}
					if(test < 0) {
						System.out.println("Invalid Year Input from file");
						System.exit(0);
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid Year Input from file");
					// e.printStackTrace();	
					System.exit(0);
				}
			}
			}



			if(!checkDuplicate(song)) {
				objectSongList.add(song);
				Collections.sort(objectSongList, new songDetailsComparator());
			} else {
				System.out.println("Duplicate Found in session file\nPlease fix and restart");
				System.exit(0);
			}

		}
	}

	public void start(Stage mainStage) {

		// since this method somehow automatically starts
		// going to have file parser call here
		parsefile();

		// create an ObservableList
		// from an ArrayList
		// songList = FXCollections.observableArrayList(internalSongList);
		songList = FXCollections.observableArrayList(objectSongList);

		dataList.setItems(songList);

		SD_Album.setVisible(false);
		SD_Title.setVisible(false);
		SD_Artist.setVisible(false);
		SD_Year.setVisible(false);
		
		DD_Album.setVisible(false);
		DD_Title.setVisible(false);
		DD_Artist.setVisible(false);
		DD_Year.setVisible(false);


		// set listener for the items
		dataList.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updateFieldText());

		// select the first item
		if(!songList.isEmpty()) dataList.getSelectionModel().select(0);

		Field_Two.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.matches("\\d*")) return;
			Field_Two.setText(newValue.replaceAll("[^\\d]", ""));
		});
		
	}


	public boolean checkDuplicate(songDetails input) {
		String inputTitle = input.getTitle().toLowerCase();
		String inputArtist = input.getArtist().toLowerCase();

		if (objectSongList.isEmpty()) {
			return false;
		}

		for (songDetails item : objectSongList) {
			if (inputTitle.equals(item.getLowerTitle()) && inputArtist.equals(item.getLowerArtist())) {
				return true;
			}
		}

		return false;

	}

	public void resetDisplay(songDetails song) {
		dataList.getSelectionModel().select(null);

		ListIterator<songDetails> songIterator = songList.listIterator();
		int songIndex = 0;

		while (songIterator.hasNext()) {
			songIndex = songIterator.nextIndex();
			songDetails songObject = songIterator.next();

			if (song.getTitle().equals(songObject.getTitle()) && song.getArtist().equals(songObject.getArtist())) {
				dataList.scrollTo(songIndex);
				dataList.getSelectionModel().select(songIndex);
				return;
			}
		}
	}

	public void sortDisplay(songDetails song) { 
		songList.add(song);
		Collections.sort(songList, new songDetailsComparator());
	}

	public void sortCollection(songDetails input) {
		objectSongList.add(input);
		Collections.sort(objectSongList, new songDetailsComparator());
	}

	public void updateFieldText() {
		// "Title - Artist"
		songDetails match = dataList.getSelectionModel().getSelectedItem();
		
		String title = "";
		String artist = ""; 
		String year = ""; 
		String album = "";

		// ERROR caused by update from edit 
		// Leaving it as a null catch in case the program decides to blow up
		// Too Bad! 
		if(match == null) {
			Field_Zero.setText("");
			Field_One.setText("");
			Field_Two.setText("");
			Field_Tri.setText("");
			
			SD_Album.setVisible(false);
			SD_Title.setVisible(false);
			SD_Artist.setVisible(false);
			SD_Year.setVisible(false);
			
			DD_Album.setVisible(false);
			DD_Title.setVisible(false);
			DD_Artist.setVisible(false);
			DD_Year.setVisible(false);
			
			DD_Album.setText("");	
			DD_Title.setText("");	
			DD_Artist.setText("");	
			DD_Year.setText("");		
			
			Album_Tip.uninstall(DD_Album, Album_Tip); 	
			Title_Tip.uninstall(DD_Title, Title_Tip); 	
			Artist_Tip.uninstall(DD_Artist, Artist_Tip); 	
			Year_Tip.uninstall(DD_Year, Year_Tip); 		
			
			return;
		}


		String matchTitle = match.getTitle().trim().toLowerCase();
		String matchArist = match.getArtist().trim().toLowerCase();

		String[] temp = new String[4];
		for (int i = 0; i < songList.size(); i++) {
			String songListTitle = songList.get(i).getTitle().trim().toLowerCase();
			String songListArtist = songList.get(i).getArtist().trim().toLowerCase(); 

			System.out.printf("Match Title: %s \t Match Artist: %s \n", matchTitle, matchArist);
			System.out.printf("item Title: %s \t item Artist: %s \n", songListTitle, songListArtist);

			if (matchTitle.equals(songListTitle) && matchArist.equals(songListArtist)) {
				temp[0] = songList.get(i).getTitle().trim();
				temp[1] = songList.get(i).getArtist().trim();
				temp[2] = songList.get(i).getYear().trim();
				temp[3] = songList.get(i).getAlbum().trim();
				
				
				title 	= songList.get(i).getTitle().trim();
				artist 	= songList.get(i).getArtist().trim();
				year	= songList.get(i).getYear().trim();
				album 	= songList.get(i).getAlbum().trim();
				
				
				break;
			}

		}

		Field_Zero.setText(temp[0]);
		Field_One.setText(temp[1]);
		Field_Two.setText(temp[2]);
		Field_Tri.setText(temp[3]);
		
		SD_Album.setVisible(true);
		SD_Title.setVisible(true);
		SD_Artist.setVisible(true);
		SD_Year.setVisible(true);
		
		DD_Album.setVisible(true);
		DD_Title.setVisible(true);
		DD_Artist.setVisible(true);
		DD_Year.setVisible(true);
		
		DD_Album.setText(album);
		DD_Title.setText(title);
		DD_Artist.setText(artist);
		DD_Year.setText(year);
		

		Album_Tip.setText(DD_Album.getText());
		Title_Tip.setText(DD_Title.getText());
		Artist_Tip.setText(DD_Artist.getText());
		Year_Tip.setText(DD_Year.getText());
		
		if (!album.isEmpty()) 	Album_Tip.install(DD_Album, Album_Tip); 	else Album_Tip.uninstall(DD_Album, Album_Tip); 	
		if (!title.isEmpty()) 	Title_Tip.install(DD_Title, Title_Tip); 	else Title_Tip.uninstall(DD_Title, Title_Tip); 	
		if (!artist.isEmpty()) 	Artist_Tip.install(DD_Artist, Artist_Tip); 	else Artist_Tip.uninstall(DD_Artist, Artist_Tip); 	
		if (!year.isEmpty()) 	Year_Tip.install(DD_Year, Year_Tip); 		else Year_Tip.uninstall(DD_Year, Year_Tip); 		
		
		Album_Tip.setShowDelay(Duration.seconds(0));
		Title_Tip.setShowDelay(Duration.seconds(0));
		Artist_Tip.setShowDelay(Duration.seconds(0));
		Year_Tip.setShowDelay(Duration.seconds(0));

		
	}


	public void regenerateFile() {
		try {
			// BufferedWriter myWriter = new BufferedWriter(new FileWriter(dataPSV, true));
			BufferedWriter myWriter = new BufferedWriter(new FileWriter(dataPSV));

			for(songDetails song : songList) {
				myWriter.write(song.toFile());
				myWriter.newLine();
			}

			myWriter.close();

		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

}
