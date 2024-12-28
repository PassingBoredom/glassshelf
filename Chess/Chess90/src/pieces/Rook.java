package src.pieces;

/**
 * The Rook class represents a rook piece.
 */
public class Rook extends Piece { 
	/**
	 * All rooks have a point value.
	 */
	private int value;

	/**
	 * All rooks can castle.
	 */
	private boolean castle;

	/**
	 * Constructor for a Rook object.
	 * @param name of a Rook piece.
	 * @param team the Rook is on.
	 * @param value of a Rook Piece.
	 */
	public Rook(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}
	
	/**
	 * This is a basic getter method to get the value of a Rook piece.
	 * @return value of a Rook piece.
	 */
	public int getValue() {
		return value;
	}	

	/**
	 * The boardString method represents a Rook piece in String form.
	 * @return String representation of a Rook Piece.
	 */
	public String boardString() { 
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(0,1);
	}
	
	/**
	 * Validates for each move a Rook Piece makes according to the rules of chess.
	 * @param location is where the Rook starts from for each turn.
	 * @param move is the desired destination for the rook's move.
	 * @param player that is currently moving a Rook Piece.
	 * @return True if the move is legal.
	 */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		if(location[0] != move[0] && location[1] != move[1]) return false;
		
		if(location[0] == move[0]) { 
			if(Math.abs(location[1] - move[1]) > 7) return false;
		}
		
		if(location[1] == move[1]) {
			if(Math.abs(location[0] - move[0]) > 7) return false;
		}

		// similar to King, castle shall be checked in game
			
		// System.out.println("Rook Validation");
		return true;
	}
	
	/**
	 * Validates if a Rook can eliminate another piece.
	 * @param location The current location of the rook on the board.
	 * @param move The desired destination for the rook's move.
	 * @param player that is currently moving Rook.
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