package src.pieces;

/**
 * The abstract class Piece represents the basic form of all Piece objects.
 */
public abstract class Piece {

	/**
	 * The name of the piece.
	 */

	protected String name;

	/** 
	 * The team color of the piece. 
	 */
	protected String team;

	/** 
	 * The number of moves made by the piece. 
	 */
	protected int nMoves = 0;
	
	/**
	 * Constructs an empty Piece object.
 	 */
	public Piece() {
		this.name = ""; 
		this.team = "";
	}
	
	/**
	 * Constructs a Piece object with a given name and team.
	 * @param name The name of the piece.
	 * @param team The team color of the piece.
 	 */
	public Piece(String name, String team) {
		this.name = name; 
		this.team = team;
	}
	
	/**
	 * Sets the name of the piece.
	 * @param name The name of the piece.
 	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the team color of the piece.
	 * @param team The team color of the piece.
 	 */
	public void setTeam(String team)  {
		this.team = team;
	}	
	
	/**
	 * Gets the name of the piece.
	 * @return The name of the piece.
 	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the team color of the piece.
	 * @return The team color of the piece.
 	 */
	public String getTeam() {
		return team;
	}
	
	/**
	 * Gets the number of moves made by the piece.
	 * @return The number of moves made by the piece.
 	 */
	public int getMoves() {
		return nMoves;
	} 
	
	/**
	 * Increments the number of moves made by the piece by one.
 	 */
	public void incrementMoves() {
		this.nMoves += 1;
	}
	
	/**
	 * Returns a string representation of the piece, in the format "team name".
	 * @return A string representation of the piece.
 	 */
	public String toString() {
		return team + " " + name;
	}
	
	/**
	 * Returns a string representation of the piece for displaying on a chess board.
	 * @return A string representation of the piece for displaying on a chess board.
 	 */
	public abstract String boardString(); 

	/**
	 * Validates the proposed move based on the rules for this type of piece.
	 * @param location The current location of the piece.
	 * @param move The proposed new location for the piece.
	 * @param player The index of the current player.
	 * @return True if the move is valid, false otherwise.
 	 */
	public abstract boolean validateMoveset(int[] location, int[] move, int player);
	
	/**
	 * Validates the attempt at elimination based on that Piece's ruleset. 
	 * @param location the current Piece started at for the current turn.
	 * @param move is the intended destination of the Piece.
	 * @param player 0 or 1 where 0 is White and 1 is Black.
	 * @param enemy piece that will be eliminated.
	 * @return True if the elimination move is valid.
 	 */
	public abstract boolean validateElim(int[] location, int[] move, int player, Piece enemy);
}