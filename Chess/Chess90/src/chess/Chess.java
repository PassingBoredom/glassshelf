package src.chess;

import java.util.Scanner; 
import src.chess.game;

/*
 * This class represents a program for playing a game of chess.
 */
public class Chess {

	/**
	 *The main method of the program.
	 *@param args from the command line.
	 */
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		boolean gameOver = false; 
		String input; 

		game active = new game();
		active.startBoard(); 
		
		while(!gameOver) {
			active.askMove();
			input = kb.nextLine();
			
			active.updateMove(input);
			// active.updateBoard();
			// active.isCheck();
			// active.isMate();
			
			if(active.getDraw() || active.endgame()) gameOver = true;
		}
		// Maybe we print the winner here?
		String result = active.getDraw() ? "draw" : active.getWinner() + " Wins";
		System.out.println(result);
		
		kb.close();
	}

}
