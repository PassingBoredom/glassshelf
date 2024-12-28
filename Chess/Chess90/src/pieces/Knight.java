package src.pieces;

/**
 * The Knight class represents a Knight chess Piece.
 */
public class Knight extends Piece { 

	/**
	 * Each Knight has a point value.
	 */
	private int value;

	/**
	 * Constructs a new Knight with the given name, team, and value.
	 * @param name the name of the piece
	 * @param team the team the piece belongs to ("Black" or "White")
	 * @param value the point value of the piece
 	 */
	public Knight(String name, String team, int value) {
		super(name, team);
		this.value = value;
	}

	/**
	 * Returns the point value of the Knight.
	 * @return the value of the Knight
 	 */
	public int getValue() {
		return value;
	}	
	
	/**
	 * Returns a string representation of the Knight, in the format "[team][first letter of name]".
	 * @return the string representation of the Knight
 	 */
	public String boardString() {
		return team.toLowerCase().substring(0,1) + name.toUpperCase().substring(1,2);
	}

	/**
	 * Validates whether a given move is valid for the Knight based on the move's start and end locations.
	 * @param location the starting location of the move
	 * @param move the ending location of the move
	 * @param player the team the Knight belongs to
	 * @return true if the move is valid, false otherwise
	 */
	public boolean validateMoveset(int[] location, int[] move, int player) {
		if(Math.abs(location[0] - move[0]) != 2 && 
			Math.abs(location[1] - move[1]) != 1 && 
			Math.abs(location[1] - move[1]) != 2 && 
			Math.abs(location[0] - move[0]) != 1) {
			return false;
		}

		// System.out.println("Knight Validation");
		return true;
	}

	/**
	 * Validates whether a given move eliminates an enemy piece and whether the move is valid for the Knight.
	 * @param location the starting location of the move
	 * @param move the ending location of the move
	 * @param player the team the Knight belongs to
	 * @param enemy the piece being eliminated
	 * @return true if the move is valid and eliminates an enemy piece, false otherwise
 	 */
	public boolean validateElim(int[] location, int[] move, int player, Piece enemy) {
		if(player == 0 && enemy.team.equals("White")) return false;
		if(player == 1 && enemy.team.equals("Black")) return false;
		if(!validateMoveset(location, move, player)) return false;
		
		return true;
	}


}