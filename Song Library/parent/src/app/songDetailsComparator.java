/*
 * @author Alan Wang netid: aw795
 */

package src.app;

import java.util.*;
// import java.io.*;
// import java.lang.*;

public class songDetailsComparator implements Comparator<songDetails> { 

	@Override
	public int compare(songDetails o1, songDetails o2) { 
		int title = o1.getLowerTitle().compareTo(o2.getLowerTitle());
			if(title == 0) {
				int artist = o1.getLowerArtist().compareTo(o2.getLowerArtist());
				return artist;
			} 
			return title;
	}
}