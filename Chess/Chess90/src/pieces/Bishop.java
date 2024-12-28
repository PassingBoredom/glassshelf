package src.pieces;

/**
 *The Bishop class represents a Bishop chess Piece.
 */
public class Bishop extends Piece {

	/**
	 *All Bishops have a point value.
	 */
	private int value;

	/**
	 *Constructor for the Bishop class with 3 arguments.
	 *@param name of the Bishop.
	 *@param team the Bishop is on.
	 *@param value of a Bishop Piece.
	 */
	public Bishop(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}

	/**
	 * Getter method for the point value of a Bishop Piece.
	 *@return the point value of a Bishop.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns a string representation of the Bishop.
	 *@return a string representation of the Bishop.
	 */
	public String boardString() {
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(0,1);
	}

	/**
	 *Validates if a given move is valid for the Bishop piece.
	 *@param location the Bishop starts at during this turn.
	 *@param move is the intended destination of the Bishop.
	 *@param player currently trying to make a move.
	 *@return True if the move is valid.
	 */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		if(location[0] == move[0]) return false;
		if(location[1] == move[1]) return false;
		if(Math.abs(location[0] - move[0]) != Math.abs(location[1] - move[1])) return false;

		// System.out.println("Bishop Validation");
		return true;
	}

	/**
	 *Validates if a given elimination move is valid for the bishop piece.
	 *@param location of the starting point of the Bishop Piece trying to move.
	 *@param move is the location where an elimination will occur.
	 *@param player attempting an elimination.
	 *@param enemy piece that will be eliminated.
	 *@return True if the elimination is valid.
	 */
	public boolean validateElim(int[] location, int[] move, int player, Piece enemy) {
		if(player == 0 && enemy.team.equals("White")) return false;
		if(player == 1 && enemy.team.equals("Black")) return false;
		if(!validateMoveset(location, move, player)) return false;

		return true;
	}

}
