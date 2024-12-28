package src.pieces;

/**
 * Represents a pawn piece in a game of chess.
 * @author Alan Wang
 * @author Andrew Svancara
 */
public class Pawn extends Piece { 

	/**
	 * Every Pawn is assigned a point value.
	 */
	private int value;

	/**
	 * Every pawn is potentially tracked for an En-Passant.
	 */
	private boolean passant; 

	/**
	 * Constructor for Pawn with 3 arguments.
	 * @param name of a Pawn Piece.
	 * @param team name of the Pawn.
	 * @param value of a Pawn Piece.
	 */
	public Pawn(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}

	/**
	 * Getter for the value of a Pawn.
	 * @return the value of the pawn.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Gets the string representation of the pawn for use on a chess board.
	 * @return the string representation of the pawn on the board.
	 */
	public String boardString() { 
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(0,1);
	}

	/**
	 * Validates whether a given move is valid for the pawn.
	 * @param location the current location of the pawn.
	 * @param move the desired location of the pawn.
	 * @param player the player code for the current player.
	 * @return true if the move is valid, false otherwise.
	 */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		switch(player) {
			case 0: // WHITE
				if(location[0] - move[0] <= 0) return false;
				if(nMoves == 0) {
					if(location[0] - move[0] > 2) return false;
					if(location[1] != move[1]) return false;
					break;
				}
				if(location[0] - move[0] != 1) return false;
				// if(Math.abs(location[1] - move[1]) > 1) return false;
				if(location[1] != move[1]) return false;
				break;

			case 1: // BLACK
				if(move[0] - location[0] <= 0) return false;
				if(nMoves == 0) {
					if(move[0] - location[0] > 2) return false;
					if(move[1] != location[1]) return false;
					break; 
				}
				if(move[0] - location[0] != 1) return false;
				if(move[1] != location[1]) return false;
				// if(Math.abs(move[1] - location[1]) > 1) return false;
				break;
			
			default: 
				System.out.println("Invalid player code");
				return false;
		}

		// System.out.println("Pawn Validation");
		return true;
	}
	
	/**
	 * Validates if a Pawn is allowed to eliminate another Piece.
	 * @param location the pawn is currently at.
	 * @param move symbolizes the destination of a Pawn. 
	 * @param player currently trying to move the Pawn.
	 * @param enemy that is being attacked by the current player.
	 * @return True if the elimination move is valid.
	 */
	public boolean validateElim(int[] location, int[] move, int player, Piece enemy) {
		if(player == 0 && enemy.team.equals("White")) return false;
		if(player == 1 && enemy.team.equals("Black")) return false;
	
		if(location[0] == move[0]) return false;
		if(Math.abs(location[1] - move[1]) != 1) return false; 
		
		return true;
	}
	
	
	/**
	 * a special method of capturing in chess that occurs when a pawn captures.
	 * a horizontally adjacent enemy pawn that has just made an initial two-square advance.
	 * @return false.
	 */
	private boolean checkEnPassant() {
		return false;
	}
	
	
	
	
}