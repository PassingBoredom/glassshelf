package src.pieces;

/**
 * The King class represents a King Piece.
 */
public class King extends Piece { 

	/**
	 * Each King has a pre-defined value.
	 */
	private int value;

	/**
	 * Each king can participate in castling.
	 */
	private boolean castle;

	/**
	 * Constructs a King piece with 3 arguments.
	 * @param name of the piece.
	 * @param team the piece belongs to.
	 * @param value of the piece.
*/
	public King(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}
	
	/**
	 * getter metod to retrive the value of a King Piece.
	 * @return the point value of a King Piece.
	 */
	public int getValue() {
		return value;
	}	
	
	/**
	 * Gets a string representation of the piece for the board.
	 * @return a string representation of the piece for the board
	 */
	public String boardString() { 
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(0,1);
	}

	/**
	 * Validates whether the King can make the given move.
	 * @param location the current location of the piece.
	 * @param move the location the piece is moving to.
	 * @param player the player controlling the piece.
	 * @return True if the move is valid.
     */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		if(Math.abs(location[0] - move[0]) > 1) return false;
		if(Math.abs(location[1] - move[1]) > 1) return false;
	
		// castle ignored, handled in game
	
		// System.out.println("King Validation");
		return true;
	}
	
	/**
	 * Validates whether the King can eliminate the enemy piece.
	 * @param location is where a King piece started in that turn.
	 * @param move is the intended destination of the Piece.
	 * @param player attemping to make a move.
	 * @param enemy piece that will be eliminated.
	 * @return True if the elimination is valid.
	 */
	public boolean validateElim(int[] location, int[] move, int player, Piece enemy) {
		if(player == 0 && enemy.team.equals("White")) return false;
		if(player == 1 && enemy.team.equals("Black")) return false;
		if(!validateMoveset(location, move, player)) return false;
		
		return true;
	}
}