package src.pieces;

/**
 * The Queen class represents a Queen Piece.
 */
public class Queen extends Piece { 

	/**
	 * Each Queen Piece has a pre-defined value.
	 */
	private int value;

	/**
	 * Constructor for Queen with 3 arguments.
	 * @param name of a Queen Piece.
	 * @param team the Queen is on.
	 * @param value of a Queen Piece.
	 */
	public Queen(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}
	
	/**
	 * Getter method to return the point value of a Queen.
	 * @return point value of a Queen Piece.
	 */
	public int getValue() {
		return value;
	}	

	/**
	 * method to represent a Queen piece in string form.
	 * @return string representation of a Queen piece.
	 */
	public String boardString() { 
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(0,1);
	}
	
	/**
-     * validates the movement of a Queen Piece.
-     * @param location a Queen is starting from.
-     * @param move is the destination a Queen is being requested to move to.
-     * @param player making the move request.
-     * @return true if the move is valid.
-     */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		if(location[0] == move[0] || location[1] == move[1]) {
			// Not sure if anything is really needed here	
			int vert = Math.abs(location[0] - move[0]);
			int horz = Math.abs(location[1] - move[1]);
			
			if(vert > 7 || horz > 7) return false;
		}
		
		if(location[0] != move[0] && location[1] != move[1]) {
			if(Math.abs(location[0] - move[0]) != Math.abs(location[1] - move[1])) return false;
		}			
		
		
		// System.out.println("Queen Validation");
		return true;
	}

	/**
	 * Validates if a Queen can eliminate another piece.
	 * @param location The current location of the Queen on the board.
	 * @param move The desired destination for the Queen's move.
	 * @param player that is currently moving Queen.
	 * @param enemy piece to be eliminated.
	 * @return True if the elimination is legal.
	 */
	public boolean validateElim(int[] location, int[] move, int player, Piece enemy) {
		if(player == 0 && enemy.team.equals("White")) return false;
		if(player == 1 && enemy.team.equals("Black")) return false;
		if(!validateMoveset(location, move, player)) return false;
		
		return true;
	}
	



}