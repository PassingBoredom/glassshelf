/*
 * @author Alan Wang netid: aw795
 */

package src.app;

// import java.io.*;
// import java.lang.*;
// import java.util.*;

public class songDetails {

	private String Title;
	private String Artist;
	private String Year;
	private String Album;

	private String sortTitle;
	private String sortArtist;
	private String sortYear;
	private String sortAlbum;

	public songDetails() {
		Title = null;
		Artist = null;
		Year = null;
		Album = null;
		sortTitle = null;
		sortArtist = null;
		sortYear = null;
		sortAlbum = null;

	}

	public songDetails(String title, String artist, String year, String album) {
		this.Title = title;
		this.Artist = artist;
		this.Year = year;
		this.Album = album;
		this.sortTitle = title.toLowerCase();
		this.sortArtist = artist.toLowerCase();
		this.sortYear = year.toLowerCase();
		this.sortAlbum = album.toLowerCase();

	}

	public String getTitle() {
		return Title;
	}

	public String getArtist() {
		return Artist;
	}

	public String getYear() {
		return Year;
	}

	public String getAlbum() {
		return Album;
	}

	public String getLowerTitle() {
		return sortTitle;
	}

	public String getLowerArtist() {
		return sortArtist;
	}

	public String getLowerYear() {
		return sortYear;
	}

	public String getLowerAlbum() {
		return sortAlbum;
	}



	public void setTitle(String title) {
		this.Title = title;
		this.sortTitle = title.toLowerCase();
	}

	public void setArtist(String artist) {
		this.Artist = artist;
		this.sortArtist = artist.toLowerCase();
	}

	public void setYear(String year) {
		this.Year = year;
		this.sortYear = year.toLowerCase();
	}

	public void setAlbum(String album) {
		this.Album = album;
		this.sortAlbum = album.toLowerCase();
	}

	public String toFile() {
		return Title + " | " + Artist + " | " + Year + " | " + Album;
	}

	public String toString() {
		return Title + " - " + Artist;
	}

}