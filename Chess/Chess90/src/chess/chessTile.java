package src.chess;

import src.pieces.Piece;

/**
 * This class represents a tile on a chess board.
 */
public class chessTile {
	private Piece piece; 
	private boolean isOccupied; 
	private int xCord, yCord; 

	/**
     * Creates a new ChessTile object with no piece and invalid coordinates.
     */
	public chessTile() {
		this.piece = null;
		this.xCord = -1;
		this.yCord = -1;
		this.isOccupied = false;
	}

	/**
     * Creates a new ChessTile object with no piece and the given coordinates.
     * @param xCord The x-coordinate of the tile
     * @param yCord The y-coordinate of the tile
     */
	public chessTile(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
		this.piece = null;
		this.isOccupied = false;
		
	}


	/**
     * Creates a new ChessTile object with the given piece and coordinates.
     * @param piece The piece on the tile
     * @param xCord The x-coordinate of the tile
     * @param yCord The y-coordinate of the tile
     */
	public chessTile(Piece piece, int xCord, int yCord) {
		this.piece = piece;
		this.xCord = xCord; 
		this.yCord = yCord;
		this.isOccupied = true;
		
	}
	

	/**
     * Sets the occupied status of the tile.
     * @param bool True if the tile is occupied, false otherwise
     */
	public void setOccupied(boolean bool) {
		this.isOccupied = bool;
	}
	

	/**
     * Sets the piece on the tile and confirms that the tile is occupied.
     * @param piece The piece to put on the tile
     * @param confirm True if the tile is confirmed to be occupied, false otherwise
     */
	public void setPiece(Piece piece, boolean confirm) {
		this.piece = piece;
		this.isOccupied = confirm;
	}


	/**
     * Removes the piece from the tile and sets the occupied status to false.
     */
	public void removePiece() {
		this.piece = null; 
		this.isOccupied = false;
	}

	/**
     * Gets the piece on the tile.
     * @return The piece on the tile
     */
	public Piece getPiece() {
		return piece; 
	}	
	
	/**
     * Gets the x-coordinate of the tile.
     * @return The x-coordinate of the tile
     */
	public int getX() { 
		return xCord;
	}
	
 	/**
     * Gets the y-coordinate of the tile.
     * @return The y-coordinate of the tile
     */
	public int getY() {
		return yCord;
	}

	/**
     * Checks if the tile is occupied by a piece.
     * @return True if the tile is occupied, false otherwise
     */
	public boolean hasPiece() {
		return isOccupied;
	}



}
