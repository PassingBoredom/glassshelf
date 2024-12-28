package src.chess;

import src.chess.chessTile; 
import src.pieces.*;

import java.util.Arrays;

/**
 * The game class represents a chess game.
 * It contains methods to set up the game board, start the game, prompt players for their move,
 * validate player input and move their chess piece on the board.
 * @author Alan Wang
 * @author Andrew Svancara
 */
public class game {
	/**
     * The winner of the game
     */
	private String winner;
	
	/**
     * Counter for the white and black pieces
     * Tracks which player's turn it is
	 * Counter for the number of consecutive moves without captures or pawn moves
     */
	private int white_counter, black_counter, playerTurn = 0, tie = 0;
	
	private int[] whitePassantTurn = {-1, -1, -1}, blackPassantTurn = {-1, -1, -1}; 
	
	/**
     * The game board
     */
	private chessTile[][] board = new chessTile[8][8];
	
	/**
     * playerResign tracks if a player has resigned
	 * mate tracks if the game has reached a checkmate
	 * staleGame tracks if the game has reached a stalemate
     */
	private boolean playerResign = false, mate = false, staleGame = false;

	// private final String lazyCols[] = {"a","b","c","d","e","f","g","h"};
	// private final String lazyRows[] = {"8","7","6","5","4","3","2","1"};

	 /**
     * Array of column names to be used as reference
     */
	private final char lazyCols[] = {'a','b','c','d','e','f','g','h'};

	/**
     * Array of row names to be used as reference
     */
	private final char lazyRows[] = {'8','7','6','5','4','3','2','1'};

	/**
     * Sets up the initial game board with the pieces
     */
	public void makeBoard() { 
		// lazy lookup table 
		Piece blackPieces[] = {
			new Rook("Rook", "Black", 6),
			new Knight("Knight", "Black", 3),
			new Bishop("Bishop", "Black", 3),
			new Queen("Queen", "Black", 9),
			new King("King", "Black", 0),
			new Bishop("Bishop", "Black", 3),
			new Knight("Knight", "Black", 3),
			new Rook("Rook", "Black", 6)
		};
	
		Piece whitePieces[] = {
			new Rook("Rook", "White", 6),
			new Knight("Knight", "White", 3),
			new Bishop("Bishop", "White", 3),
			new Queen("Queen", "White", 9),
			new King("King", "White", 0),
			new Bishop("Bishop", "White", 3),
			new Knight("Knight", "White", 3),
			new Rook("Rook", "White", 6)
		};
		
		
		// init 
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = new chessTile(i,j);
			}
		}
		
		for(int i = 0; i < 8; i++) {
			Pawn blackPawn = new Pawn("Pawn", "Black", 1);
			Pawn whitePawn = new Pawn("Pawn", "White", 1);
			
			board[0][i].setPiece(blackPieces[i], true);
			board[1][i].setPiece((Piece)blackPawn, true);
			
			board[6][i].setPiece((Piece)whitePawn, true);
			board[7][i].setPiece(whitePieces[i], true);
		}
	}

	/*
	 * Initializes the starting states for each piece on the chess board.
	 */
	public void startBoard() {
		makeBoard(); 
		
		// System.out.println("Starting new game");	
		System.out.println();
		System.out.println("bR bN bB bQ bK bB bN bR 8"); 
		System.out.println("bp bp bp bp bp bp bp bp 7"); 
		System.out.println("   ##    ##    ##    ## 6"); 
		System.out.println("##    ##    ##    ##    5"); 
		System.out.println("   ##    ##    ##    ## 4"); 
		System.out.println("##    ##    ##    ##    3"); 
		System.out.println("wp wp wp wp wp wp wp wp 2"); 
		System.out.println("wR wN wB wQ wK wB wN wR 1"); 
		System.out.println("a  b  c  d  e  f  g  h   "); 
		System.out.println();
	}

	/**
	 * prompts the player to make a move.
	 */
	public void askMove() {
		switch(playerTurn % 2) {
			case 0:
				System.out.print("White to Move: ");
				break;
				
			case 1:
				System.out.print("Black to Move: ");
				break;
				
			default: 
				System.out.println("Spec dont play");
		}
		// playerTurn++;
	}
	
	/**
	 * Checks if the input is in proper format to make a move.
	 * @param original
	 * @return true if the infput is valid.
	 */
	private boolean checkInput(String original) {
		String minimal;
		
		minimal = original.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		if(tie == 0 && inputs[0].contains("draw")) return false;
			
		char[] location = inputs[0].toCharArray();
		char[] target = inputs[1].toCharArray();
		
		if(location.length != 2 || target.length != 2) {
			return false;
		}
		
		if(location[0] < 'a' || location[0] > 'h') return false; 
		if(location[1] < '0' || location[1] > '8') return false; 
		
		if(target[0] < 'a' || target[0] > 'h') return false; 
		if(target[1] < '0' || target[1] > '8') return false; 
		
		if(inputs.length == 3 && inputs[2] != null ) tie = inputs[2].contains("draw?") ? 1 : 0;

		return true;
	}
	
	/**
	 *Validates a move based on the selected piece. Also, makes sure that the player can only move their piece.
	 * @param str
	 * @return true if the move is valid.
	 */
	private boolean checkMove(String str) {
		String minimal;
		
		minimal = str.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		if(board[curr[0]][curr[1]].getPiece() == null) return false;
		
		Piece selectedPiece = board[curr[0]][curr[1]].getPiece();
		Piece enemyPiece = board[place[0]][place[1]].getPiece();
		
		// check that the player is trying to move their piece
		if(selectedPiece.getTeam().equals("White") && playerTurn % 2 != 0) return false;
		if(selectedPiece.getTeam().equals("Black") && playerTurn % 2 != 1) return false;

		// iterate the spaces to check that its a valid move that doesnt jump over pieces unless its a knight
		if(!selectedPiece.getName().equals("Knight")) {
			if(!isDirectPath(curr, place)) return false;
		}

		// TODO: Figure out how I'm going to add enpassant here. Pawn elim works but enpassant works on piece, null 
			// as opposed to be piece, piece
			// Pawn has unique elimination rules in that it must be diagonal
			// and it has the enpassant rule
		if(enemyPiece != null) {
			
			// checks that a piece can take the other piece 
			// also checks that they arent the same team
			if(!selectedPiece.validateElim(curr, place, playerTurn % 2, enemyPiece)) return false;
		} else { 
			
			// check piece moveset
			if(!selectedPiece.validateMoveset(curr, place, playerTurn % 2)) return false;
		}

		// implement fake move to test if the player would be put into check
			// if they are put into check, fail the move, 
			// if they are not put into check allow the move
		
		// updateBoard but locally
		// technically this could be moved into updateBoard and have a check call there but this call isnt made until everything is clear so 
		// cant really avoid duplicating the code 
		board[place[0]][place[1]].setPiece(selectedPiece, true);
		board[curr[0]][curr[1]].setPiece(null, false);
		if(isCheck(playerTurn % 2, getKingLocation(playerTurn % 2))) {
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(enemyPiece != null) board[place[0]][place[1]].setPiece(enemyPiece, true); 
			else board[place[0]][place[1]].setPiece(null, false);
			return false;
		} else { 
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(enemyPiece != null) board[place[0]][place[1]].setPiece(enemyPiece, true); 
			else board[place[0]][place[1]].setPiece(null, false);
		}

		return true;
	}
	
	/**
	 * Checks if there is a direct path between the current position and the destination position.
	 * @param current  position in [row, col] format.
	 * @param dest is the destination position in [row, col] format.
	 * @return True if there is a direct path.
	 */
	private Boolean isDirectPath(int[] current, int[] dest) {
		int[] pos = new int[2];
		
		int x_axis = dest[1] - current[1] == 0 ? 0 : (dest[1] - current[1] > 0 ? 1 : -1);
		int y_axis = dest[0] - current[0] == 0 ? 0 : (dest[0] - current[0] > 0 ? 1 : -1);
		
		pos[0] = current[0] + y_axis; 
		pos[1] = current[1] + x_axis;
		
		while(!Arrays.equals(pos, dest)) {
			if(pos[0] < 0 || pos[0] > 7 || pos[1] < 0 || pos[1] > 7) return false;
			if(board[pos[0]][pos[1]].hasPiece()) return false;
			pos[0] += y_axis;
			pos[1] += x_axis;
		}

		return true;
	}
	
	/**
	 * Returns the integer representation of the given character row lookup value.
	 * @param num
	 * @return the row index of the chessboard square. If it is invalid then returns -1.
	 */
	private int getRowLookup(char num) {
		
		for(int i = 0; i < lazyRows.length; i++) {
			if(num == lazyRows[i]) return i; 
		}
		
		return -1;
		
	}
	
	/**
	 * Returns the index of the given letter in the lazyCols array, representing the column of a chessboard.
	 * @param letter
	 * @return the column index of the chessboard square. If it is invalid then returns -1.
	 */
	private int getColLookup(char letter) {
		
		for(int i = 0; i < lazyCols.length; i++) {
			if(letter == lazyCols[i]) return i; 
		}
		
		return -1;
	}
	
	/**
	 * Checks if a player chooses to resign based on input.
	 * Sets playerResign to true and declares the other player as the winner.
	 * @param move
	 * @return true if the player is trying to resign.
	 */
	private boolean resign(String move) {
		String trim = move.replaceAll("\s*","");
		
		if(trim.contains("resign")) {
			playerResign = true; 
			winner = playerTurn % 2 == 0 ? "Black" : "White";
			return true;

		}
		return false;
	}

	/**
	 * Checks if there was a tie.
 	* @param move
 	*/
	private boolean tieGame() {
		return tie == 1 ? true : false;

	}

	/**
	 * Updates the game after a move.
	 * @param move
	 */
	public void updateMove(String move) {
		move = move.replaceAll("\s+", " ");

		if(resign(move)) {
			endgame();
			return;
		}
		
		if(!checkInput(move)) {
			System.out.println("Illegal Move, Try again");
			return;
		}
		
		if(tieGame()) {
			staleGame = true;
			return;
		}
		
		if(checkPassant(move)) {
			if(!confirmPassant(move)) {
				System.out.println("Illegal Move, Try again");
				return;
			}
			updatePassant(move);
			playerTurn++;
			printBoard();
			return;
		}
		
		
		if(checkCastle(move)) {
			if(!confirmCastle(move)) {
				System.out.println("Illegal Move, Try again");
				return;
			}
			playerTurn++;
			updateCastle(move); 
			printBoard();
			return;
		}
		
		if(checkPromotion(move)) {
			if(!confirmPromotion(move)) {
				System.out.println("Illegal Move, Try again");
				return;
			}
			playerTurn++;
			updateBoard(move);
			printBoard();
			return;
		}

		if(!checkMove(move)) {
			System.out.println("Illegal Move, Try again");
			return;
		}		
		
		updatePassantVal(move);
		
		// char[] arr = move.toCharArray();
		// char xTemp = arr[0];
		// char yTemp = arr[1];
		// System.out.printf("x axis: %c, y axis: %c\n", xTemp, yTemp);
		
		// for later implementations
		playerTurn++;
		// might need to have board take the move into its parameters 
		// need something to grab the location e2 and target e4
		// the code needs to see both to update all the pieces
		updateBoard(move);
		printBoard();
		if(isMate(playerTurn % 2)) {
			System.out.println("Checkmate");
			winner = playerTurn % 2 == 0 ? "Black" : "White";
			mate = true;
			return;
		}
		if(isCheck(playerTurn % 2, getKingLocation(playerTurn % 2))) System.out.println("Check");
		
		return;
	}
	
	/**
	 * Saves information about a Pawn's previus movement to determine if an En Passant is valid.
	 * @param move made by a player in chess notation (the player's input)
	 */
	private void updatePassantVal(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		if(playerTurn % 2 == 0) {
			if(board[curr[0]][curr[1]] == null || !board[curr[0]][curr[1]].getPiece().toString().equals("White Pawn")) return;
			if(curr[0] - place[0] != 2) return;
			whitePassantTurn[0] = playerTurn;
			whitePassantTurn[1] = place[0];
			whitePassantTurn[2] = place[1];
		} else {
			if(board[curr[0]][curr[1]] == null || !board[curr[0]][curr[1]].getPiece().toString().equals("Black Pawn")) return;
			if(curr[0] - place[0] != 2) return;
			blackPassantTurn[0] = playerTurn;
			blackPassantTurn[1] = place[0];
			blackPassantTurn[2] = place[1];
		}
	}
	
	/**
	 * Check if the move is a valid En Passant capture.
	 * @param move checks the move in algebraic notation
	 * @return True if the move is a valid En Passant.
 	 */
	private boolean checkPassant(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);

		if(Math.abs(curr[1] - place[1]) != 1) return false;
		if(board[curr[0]][curr[1]].getPiece() == null) return false;
		if(!board[curr[0]][curr[1]].getPiece().getName().equals("Pawn")) return false;
		if(board[place[0]][place[1]].hasPiece()) return false;
		
		if(playerTurn % 2 == 0) {
			if(!board[place[0]+1][place[1]].hasPiece() || !board[place[0]+1][place[1]].getPiece().toString().equals("Black Pawn")) return false;
		}
		if(playerTurn % 2 == 1) {
			if(!board[place[0]-1][place[1]].hasPiece() || !board[place[0]-1][place[1]].getPiece().toString().equals("White Pawn")) return false;
		}
	
		return true;
	} 
	
	/**
	 * Confirms that an en passant move is valid then proceeds.
	 * @param move The en passant move to be confirmed and executed.
	 * @return true if the move is valid and executed.
	 */
	private boolean confirmPassant(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		Piece selectedPiece = board[curr[0]][curr[1]].getPiece();
		Piece enemyPiece;
		
		if(playerTurn % 2 == 0) {
			enemyPiece = board[place[0] + 1][place[1]].getPiece();
		} else {
			enemyPiece = board[place[0] - 1][place[1]].getPiece();
		}

		// check that the player is trying to move their piece
		if(selectedPiece.getTeam().equals("White") && playerTurn % 2 != 0) return false;
		if(selectedPiece.getTeam().equals("Black") && playerTurn % 2 != 1) return false;
		
		// Force call Pawn elimination even en Passant has no piece on end tile
		if(!selectedPiece.validateElim(curr, place, playerTurn % 2, enemyPiece)) return false;
		
		if(playerTurn % 2 == 0) {
			if(playerTurn - blackPassantTurn[0] != 1) return false;
			if(blackPassantTurn[1] != place[0] + 1 || blackPassantTurn[2] != place[1]) return false;
		} else { 
			if(playerTurn - whitePassantTurn[0] != 1) return false;
			if(whitePassantTurn[1] != place[0] - 1 || whitePassantTurn[2] != place[1]) return false;
		}

		board[place[0]][place[1]].setPiece(selectedPiece, true);
		board[curr[0]][curr[1]].setPiece(null, false);
		if(playerTurn % 2 == 0) {
			board[place[0] + 1][place[1]].setPiece(null, false);
		} else {
			board[place[0] - 1][place[1]].setPiece(null, false);
		}
		if(isCheck(playerTurn % 2, getKingLocation(playerTurn % 2))) {
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(playerTurn % 2 == 0) {
				board[place[0] + 1][place[1]].setPiece(enemyPiece, true);
			} else {
				board[place[0] - 1][place[1]].setPiece(enemyPiece, true);
			}
			return false;
		} else { 
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(playerTurn % 2 == 0) {
				board[place[0] + 1][place[1]].setPiece(enemyPiece, true);
			} else {
				board[place[0] - 1][place[1]].setPiece(enemyPiece, true);
			}
		}
		return true;
	}		
	
	/**
	 * Updates the state of En-Passant.
	 * @param move in algebraic form.
	 */
	private void updatePassant(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		board[place[0]][place[1]].setPiece(board[curr[0]][curr[1]].getPiece(), true);
		board[curr[0]][curr[1]].setPiece(null, false);
		if(playerTurn % 2 == 0) {
			board[place[0] + 1][place[1]].setPiece(null, false);
		} else {
			board[place[0] - 1][place[1]].setPiece(null, false);
		}
	}
	
	/**
	 * Checks if a pawn move results in a promotion.
	 * @param move checks for promotion.
	 * @return True if the move results in a promotion.
	 */
	private boolean checkPromotion(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		if(board[curr[0]][curr[1]].getPiece() == null) return false;
		if(!board[curr[0]][curr[1]].getPiece().getName().equals("Pawn")) return false;
		
		if(playerTurn % 2 == 0 && place[0] == 0) return true;
		if(playerTurn % 2 == 1 && place[0] == 7) return true;
		
		return false;
	}
	
	/**
	 * Confirms if the player's promotion move is valid.
	 * @param move string input by the player.
	 * @return True if the promotion move is valid.
	 */
	private boolean confirmPromotion(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		char[] promo = {'Q'}; 
		if(inputs.length == 3 && inputs[2] != null) promo = inputs[2].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		Piece selectedPiece = board[curr[0]][curr[1]].getPiece();
		Piece enemyPiece = board[place[0]][place[1]].getPiece();
		
		// check that the player is trying to move their piece
		if(selectedPiece.getTeam().equals("White") && playerTurn % 2 != 0) return false;
		if(selectedPiece.getTeam().equals("Black") && playerTurn % 2 != 1) return false;

		// iterate the spaces to check that its a valid move that doesnt jump over pieces unless its a knight
		if(!selectedPiece.getName().equals("Knight")) {
			if(!isDirectPath(curr, place)) return false;
		}
		
		// Should always be a pawn and should also be 
		if(enemyPiece != null) {
			
			// checks that a piece can take the other piece 
			// also checks that they arent the same team
			if(!selectedPiece.validateElim(curr, place, playerTurn % 2, enemyPiece)) return false;
		} else { 
			
			// check piece moveset
			if(!selectedPiece.validateMoveset(curr, place, playerTurn % 2)) return false;
		}
		
		board[place[0]][place[1]].setPiece(selectedPiece, true);
		board[curr[0]][curr[1]].setPiece(null, false);
		if(isCheck(playerTurn % 2, getKingLocation(playerTurn % 2))) {
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(enemyPiece != null) board[place[0]][place[1]].setPiece(enemyPiece, true); 
			else board[place[0]][place[1]].setPiece(null, false);
			return false;
		} else { 
		
			if(playerTurn % 2 == 0) {
				switch(promo[0]) {
					case 'B':
						selectedPiece = new Bishop("Bishop", "White", 3);
						break;
						
					case 'R':
						selectedPiece = new Rook("Rook", "White", 6);
						break;
						
					case 'N':
						selectedPiece = new Knight("Knight", "White", 3);
						break;
						
					default: 
						selectedPiece = new Queen("Queen", "White", 9);
				}
			} else {
				switch(promo[0]) {
					case 'B':
						selectedPiece = new Bishop("Bishop", "Black", 3);
						break;
						
					case 'R':
						selectedPiece = new Rook("Rook", "Black", 6);
						break;
						
					case 'N':
						selectedPiece = new Knight("Knight", "Black", 3);
						break;
						
					default: 
						selectedPiece = new Queen("Queen", "Black", 9);
				}
			}
		
			board[curr[0]][curr[1]].setPiece(selectedPiece, true);
			if(enemyPiece != null) board[place[0]][place[1]].setPiece(enemyPiece, true); 
			else board[place[0]][place[1]].setPiece(null, false);
		}

		return true;
	}
	
	
	
	/**
	 * Checks if the given move is a castle.
	 * @param move
	 * @return true if the move was an attempt at a castle.
	 */
	private boolean checkCastle(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		if(board[curr[0]][curr[1]].getPiece() == null) return false;
		if(!board[curr[0]][curr[1]].getPiece().getName().equals("King")) return false;
		
		if(board[curr[0]][curr[1]].getPiece().getMoves() != 0) return false;
		
		if(inputs[0].equals("e1") && inputs[1].equals("g1")) return true;
		if(inputs[0].equals("e1") && inputs[1].equals("c1")) return true;
		if(inputs[0].equals("e8") && inputs[1].equals("g8")) return true;
		if(inputs[0].equals("e8") && inputs[1].equals("c8")) return true;

		return false;
	}
	
	/**
	 * flag for confirming a legal castle.
	 * @param move
	 * @return true if a player and can legally castle.
	 */
	private boolean confirmCastle(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);
		
		// WHITE KING SIDE
		if(inputs[0].equals("e1") && inputs[1].equals("g1")) {

			Piece whiteKing = board[curr[0]][curr[1]].getPiece();
			Piece whiteRook = board[7][7].getPiece();
			
			if(whiteKing == null || whiteRook == null) return false;
			if(!whiteKing.toString().equals("White King") || !whiteRook.toString().equals("White Rook")) return false;
			if(whiteKing.getMoves() != 0 || whiteRook.getMoves() != 0) return false;

			// Right side of King
			if(board[7][5].hasPiece()) return false;
			if(board[7][6].hasPiece()) return false;
			
			// Left side of King
			for(int i = 3; i > -1; i--) {
				if(board[7][i].hasPiece()) {
					switch(board[7][i].getPiece().getTeam()) {
						case "White":
							break;
						
						case "Black":
							if(board[7][i].getPiece().toString().equals("Black Rook")) return false;
							if(board[7][i].getPiece().toString().equals("Black Queen")) return false;
							break;
						
						default:
							System.out.println("Surely theres a better way");
					}
					break;
				}
			}			
			
			// Places where Knight / Pawn would prevent the castle
			for(int i = 3; i < 8; i++) {
				if(board[5][i].getPiece() != null && board[5][i].getPiece().toString().equals("Black Knight")) return false;
				if(board[6][i].getPiece() != null && board[6][i].getPiece().toString().equals("Black Pawn")) return false;
			}
			if(board[6][2].getPiece() != null && board[6][2].getPiece().toString().equals("Black Knight")) return false;
			
			
			// everywhere else
			int[] pos = {7,4};
			int[] end = {7,7};
			int otherIterator;
			while(!Arrays.equals(pos, end)) {
				
				// left diagonal
				otherIterator = pos[0]-1;
				for(int i = pos[1]-1; i > -1; i--) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[otherIterator][i].getPiece().toString().equals("Black Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator--;
				}
	
				// vertical
				for(int i = pos[0]-1; i > -1; i--) {
					if(board[i][pos[1]].hasPiece()) {
						switch(board[i][pos[1]].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[i][pos[1]].getPiece().toString().equals("Black Rook")) return false;
								if(board[i][pos[1]].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
				}
				
				// right diagonal
				otherIterator = pos[0]-1;
				for(int i = pos[1]+1; i < 8; i++) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[otherIterator][i].getPiece().toString().equals("Black Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator--;
				}
				
				pos[1]++;
			}
		} 
		
		// WHITE QUEEN SIDE 
		if(inputs[0].equals("e1") && inputs[1].equals("c1")) {
			Piece whiteKing = board[curr[0]][curr[1]].getPiece();
			Piece whiteRook = board[7][0].getPiece();
			
			if(whiteKing == null || whiteRook == null) return false;
			if(!whiteKing.toString().equals("White King") || !whiteRook.toString().equals("White Rook")) return false;
			if(whiteKing.getMoves() != 0 || whiteRook.getMoves() != 0) return false;

			// Left side of King
			if(board[7][1].hasPiece()) return false;
			if(board[7][2].hasPiece()) return false;
			if(board[7][3].hasPiece()) return false;
			
			// Right side of King
			for(int i = 5; i < 8; i++) {
				if(board[7][i].hasPiece()) {
					switch(board[7][i].getPiece().getTeam()) {
						case "White":
							break;
						
						case "Black":
							if(board[7][i].getPiece().toString().equals("Black Rook")) return false;
							if(board[7][i].getPiece().toString().equals("Black Queen")) return false;
							break;
						
						default:
							System.out.println("Surely theres a better way");
					}
					break;
				}
			}			
			
			// Places where Knight / Pawn would prevent the castle
			for(int i = 5; i > 0; i--) {
				if(board[5][i].getPiece() != null && board[5][i].getPiece().toString().equals("Black Knight")) return false;
				if(board[6][i].getPiece() != null && board[6][i].getPiece().toString().equals("Black Pawn")) return false;
			}
			if(board[6][6].getPiece() != null && board[6][6].getPiece().toString().equals("Black Knight")) return false;
			
			
			// everywhere else
			int[] pos = {7,4};
			int[] end = {7,0};
			int otherIterator;
			while(!Arrays.equals(pos, end)) {
				
				// left diagonal
				otherIterator = pos[0]-1;
				for(int i = pos[1]-1; i > -1; i--) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[otherIterator][i].getPiece().toString().equals("Black Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator--;
				}
	
				// vertical
				for(int i = pos[0]-1; i > -1; i--) {
					if(board[i][pos[1]].hasPiece()) {
						switch(board[i][pos[1]].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[i][pos[1]].getPiece().toString().equals("Black Rook")) return false;
								if(board[i][pos[1]].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
				}
				
				// right diagonal
				otherIterator = pos[0]-1;
				for(int i = pos[1]+1; i < 8; i++) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "White":
								break;
							
							case "Black":
								if(board[otherIterator][i].getPiece().toString().equals("Black Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("Black Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator--;
				}
				
				pos[1]++;
			}
			
		}
		
		// BLACK KING SIDE 
		if(inputs[0].equals("e8") && inputs[1].equals("g8")) {
			Piece BlackKing = board[curr[0]][curr[1]].getPiece();
			Piece BlackRook = board[0][7].getPiece();
			
			if(BlackKing == null || BlackRook == null) return false;
			if(!BlackKing.toString().equals("Black King") || !BlackRook.toString().equals("Black Rook")) return false;
			if(BlackKing.getMoves() != 0 || BlackRook.getMoves() != 0) return false;

			// Right side of King
			if(board[0][5].hasPiece()) return false;
			if(board[0][6].hasPiece()) return false;
			
			// left side of King
			for(int i = 3; i > -1; i--) {
				if(board[0][i].hasPiece()) {
					switch(board[0][i].getPiece().getTeam()) {
						case "Black":
							break;
						
						case "White":
							if(board[0][i].getPiece().toString().equals("White Rook")) return false;
							if(board[0][i].getPiece().toString().equals("White Queen")) return false;
							break;
						
						default:
							System.out.println("Surely theres a better way");
					}
					break;
				}
			}
			
			// Places where Knight / Pawn would prevent the castle
			for(int i = 3; i < 8; i++) {
				if(board[1][i].getPiece() != null && board[1][i].getPiece().toString().equals("White Pawn")) return false;
				if(board[2][i].getPiece() != null && board[2][i].getPiece().toString().equals("White Knight")) return false;
			}
			if(board[1][2].getPiece() != null && board[1][2].getPiece().toString().equals("White Knight")) return false;


			// everywhere else
			int[] pos = {0,4};
			int[] end = {0,7};
			int otherIterator;
			while(!Arrays.equals(pos, end)) {
				
				// left diagonal
				otherIterator = pos[0]+1;
				for(int i = pos[1]-1; i > -1; i--) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[otherIterator][i].getPiece().toString().equals("White Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator++;
				}
	
				// vertical
				for(int i = pos[0]+1; i < 8 ; i++) {
					if(board[i][pos[1]].hasPiece()) {
						switch(board[i][pos[1]].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[i][pos[1]].getPiece().toString().equals("White Rook")) return false;
								if(board[i][pos[1]].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
				}
				
				// right diagonal
				otherIterator = pos[0]+1;
				for(int i = pos[1]+1; i < 8; i++) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[otherIterator][i].getPiece().toString().equals("White Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator++;
				}
				
				pos[1]++;
			}
			
		}
		
		// BLACK QUEEN SIDE
		if(inputs[0].equals("e8") && inputs[1].equals("c8")) {
			Piece BlackKing = board[curr[0]][curr[1]].getPiece();
			Piece BlackRook = board[0][0].getPiece();
			
			if(BlackKing == null || BlackRook == null) return false;
			if(!BlackKing.toString().equals("Black King") || !BlackRook.toString().equals("Black Rook")) return false;
			if(BlackKing.getMoves() != 0 || BlackRook.getMoves() != 0) return false;

			// Left side of King
			if(board[0][1].hasPiece()) return false;
			if(board[0][2].hasPiece()) return false;
			if(board[0][3].hasPiece()) return false;
			
			// Right side of King
			for(int i = 5; i < 8; i++) {
				if(board[0][i].hasPiece()) {
					switch(board[0][i].getPiece().getTeam()) {
						case "Black":
							break;
						
						case "White":
							if(board[0][i].getPiece().toString().equals("White Rook")) return false;
							if(board[0][i].getPiece().toString().equals("White Queen")) return false;
							break;
						
						default:
							System.out.println("Surely theres a better way");
					}
					break;
				}
			}
			
			// Places where Knight / Pawn would prevent the castle
			for(int i = 5; i > 0; i--) {
				if(board[1][i].getPiece() != null && board[1][i].getPiece().toString().equals("White Pawn")) return false;
				if(board[2][i].getPiece() != null && board[2][i].getPiece().toString().equals("White Knight")) return false;
			}
			if(board[1][6].getPiece() != null && board[1][6].getPiece().toString().equals("White Knight")) return false;


			// everywhere else
			int[] pos = {0,4};
			int[] end = {0,0};
			int otherIterator;
			while(!Arrays.equals(pos, end)) {
				
				// left diagonal
				otherIterator = pos[0]+1;
				for(int i = pos[1]-1; i > -1; i--) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[otherIterator][i].getPiece().toString().equals("White Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator++;
				}
	
				// vertical
				for(int i = pos[0]+1; i < 8 ; i++) {
					if(board[i][pos[1]].hasPiece()) {
						switch(board[i][pos[1]].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[i][pos[1]].getPiece().toString().equals("White Rook")) return false;
								if(board[i][pos[1]].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
				}
				
				// right diagonal
				otherIterator = pos[0]+1;
				for(int i = pos[1]+1; i < 8; i++) {
					if(board[otherIterator][i].hasPiece()) {
						switch(board[otherIterator][i].getPiece().getTeam()) {
							case "Black":
								break;
							
							case "White":
								if(board[otherIterator][i].getPiece().toString().equals("White Bishop")) return false;
								if(board[otherIterator][i].getPiece().toString().equals("White Queen")) return false;
								break;
							
							default:
								System.out.println("Surely theres a better way");
						}
						break;
					}
					otherIterator++;
				}
				
				pos[1]++;
			}
		}
	
		return true;
	}
	
	/**
	 * Updates the board for a castle by moving the king and the rook to new positions.
	 * @param move
	 */
	private void updateCastle(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		// White Castle 
		if(inputs[0].equals("e1") && inputs[1].equals("g1")) {
			board[7][6].setPiece(board[7][4].getPiece(), true);
			board[7][5].setPiece(board[7][7].getPiece(), true);
			board[7][4].getPiece().incrementMoves();
			board[7][7].getPiece().incrementMoves();

			board[7][7].setPiece(null, false);
			board[7][4].setPiece(null, false);
			return;
		}
		
		if(inputs[0].equals("e1") && inputs[1].equals("c1")) {
			board[7][2].setPiece(board[7][4].getPiece(), true);
			board[7][3].setPiece(board[7][0].getPiece(), true);
			board[7][4].getPiece().incrementMoves();
			board[7][0].getPiece().incrementMoves();
			board[7][0].setPiece(null, false);
			board[7][4].setPiece(null, false);
			return;
		}
		
		// Black Castle 
		if(inputs[0].equals("e8") && inputs[1].equals("g8")) {
			board[0][6].setPiece(board[0][4].getPiece(), true);
			board[0][5].setPiece(board[0][7].getPiece(), true);
			board[0][4].getPiece().incrementMoves();
			board[0][7].getPiece().incrementMoves();
			board[0][7].setPiece(null, false);
			board[0][4].setPiece(null, false);
			return;
		}
		if(inputs[0].equals("e8") && inputs[1].equals("c8")) {
			board[0][2].setPiece(board[0][4].getPiece(), true);
			board[0][3].setPiece(board[0][0].getPiece(), true);
			board[0][4].getPiece().incrementMoves();
			board[0][0].getPiece().incrementMoves();
			board[0][0].setPiece(null, false);
			board[0][4].setPiece(null, false);
			return;
		}
		
	}
	
	/**
	 * Updates the board.
	 * @param move
	 */
	private void updateBoard(String move) {
		String minimal;
		
		minimal = move.trim().replaceAll("\s+", " ");
		String inputs[] = minimal.split(" ");
		
		char[] original = inputs[0].toCharArray();
		char[] next = inputs[1].toCharArray();
		
		int[] curr = new int[2];
		int[] place = new int[2];
			
		curr[0] = getRowLookup(original[1]);
		curr[1] = getColLookup(original[0]);
		
		place[0] = getRowLookup(next[1]);
		place[1] = getColLookup(next[0]);

		// FIXME: Check that im increasing the move counter when it updates
		// adding a new piece everytime I move one means i lose the number as it gets reset to 0
		Piece selected = board[curr[0]][curr[1]].getPiece();

		board[place[0]][place[1]].setPiece(selected, true);
		selected.incrementMoves();
		board[curr[0]][curr[1]].setPiece(null, false);
		
	}
	
	/**
	 * Prints the board. White spaces are "  " (empty strings)  and black spaces are "## ".
	 */
	private void printBoard() {
	
		System.out.println();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getPiece() != null) { 
					System.out.print(board[i][j].getPiece().boardString() + " ");
				} else if( (i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1) ) { 
					System.out.print("   ");
				} else { 
					System.out.print("## ");
				}
			}
			System.out.println(8 - i);
		}
		System.out.println("a  b  c  d  e  f  g  h   \n"); 

	}
	
	/**
	 * handles whether the current player's king is in check.
	 * @return true if the current player is in check.
	 */
	private boolean isCheck(int player, int[] location) {
		String playerTeam = player == 0 ? "White" : "Black";
		// int[] location = getKingLocation(player);
		int[] pos = location.clone(); 
		if(location[0] == -1 && location[1] == -1) return false;
		/*
			Need to check all directions and can return true at the first notice
			Multiple occurences dont really matter that much as the outcome is still the same 
			The player is in check 
			
			Directions that need to be checked: all of them 
			UL | U | UR
			L  | K | R 
			DL | D | DR 
			
			Unique case is Knight
			   | NW |    | NE | 
			WN |    |    |    | EN
			   |    | K  |    |   
			WS |    |    |    | ES
			   | SW |    | SE | 
			
			Another Slightly unique case is Pawn
			Holds the same property as bishop however depending on the team it can only check from either UL/UR or DL/DR
		*/
		
		// FIXME: Possible redundant code in each check. If its a piece of the same team break;
		// This implies that in order to get to the next if statement it has to be a different team
		// so the team check in each if is alrdy true and waste of performance
		
		// Up Left 
		pos[0] = location[0] - 1;
		pos[1] = location[1] - 1;
		// Unique Black Pawn check for (check/mate) for White King  
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) { 
			if(board[pos[0]][pos[1]].hasPiece() && board[pos[0]][pos[1]].getPiece().toString().equals("Black Pawn")) return true;
		}
		while(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) {
			if(!board[pos[0]][pos[1]].hasPiece()) { 
				pos[0] -= 1;
				pos[1] -= 1;					
				continue;
			}
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Bishop")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
			pos[0] -= 1;
			pos[1] -= 1;
		}

		// Up
		pos[0] = location[0] - 1;
		pos[1] = location[1];
		for(int i = pos[0]; i > -1; i--) {
			if(!board[pos[0]][pos[1]].hasPiece()) continue;
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Rook")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
		}
		
		// Up Right
		pos[0] = location[0] - 1;
		pos[1] = location[1] + 1;
		// Unique Black Pawn check for (check/mate) for White King 
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) { 
			if(board[pos[0]][pos[1]].hasPiece() && board[pos[0]][pos[1]].getPiece().toString().equals("Black Pawn")) return true;
		}
		while(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) {
			if(!board[pos[0]][pos[1]].hasPiece()) { 
				pos[0] -= 1;
				pos[1] += 1;					
				continue;
			}
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Bishop")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
			pos[0] -= 1;
			pos[1] += 1;
		}
		
		// Left 
		pos[0] = location[0];
		pos[1] = location[1] - 1;
		for(int i = pos[1]; i > -1; i--) {
			if(!board[pos[0]][pos[1]].hasPiece()) continue;
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Rook")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
		}
		
		// Right
		pos[0] = location[0];
		pos[1] = location[1] + 1;
		for(int i = pos[1]; i < 8; i++) {
			if(!board[pos[0]][pos[1]].hasPiece()) continue;
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Rook")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
		}
		
		// Down Left
		pos[0] = location[0] + 1;
		pos[1] = location[1] - 1;
		// Unique White Pawn check for (check/mate) for Black King 
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) { 
			if(board[pos[0]][pos[1]].hasPiece() && board[pos[0]][pos[1]].getPiece().toString().equals("White Pawn")) return true;
		}
		while(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) {
			if(!board[pos[0]][pos[1]].hasPiece()) { 
				pos[0] += 1;
				pos[1] -= 1;					
				continue;
			}
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Bishop")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
			pos[0] += 1;
			pos[1] -= 1;
		}
		
		// Down
		pos[0] = location[0] + 1;
		pos[1] = location[1];
		for(int i = pos[0]; i < 8; i++) {
			if(!board[pos[0]][pos[1]].hasPiece()) continue;
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Rook")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
		}
		
		// Down Right 
		pos[0] = location[0] + 1;
		pos[1] = location[1] + 1;
		// Unique White Pawn check for (check/mate) for Black King  
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) { 
			if(board[pos[0]][pos[1]].hasPiece() && board[pos[0]][pos[1]].getPiece().toString().equals("White Pawn")) return true;
		}
		
		while(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) {
			if(!board[pos[0]][pos[1]].hasPiece()) { 
				pos[0] += 1;
				pos[1] += 1;					
				continue;
			}
			if(board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam)) break;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Bishop")) return true;
			if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Queen")) return true;
			pos[0] += 1;
			pos[1] += 1;
		}
		
		// Knight checks
			// NW 
			pos[0] = location[0] - 2; 
			pos[1] = location[1] - 1;
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}
				
			// NE 
			pos[0] = location[0] - 2;
			pos[1] = location[1] - 1;
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}
			
			// EN
			pos[0] = location[0] - 1;
			pos[1] = location[1] + 2;	
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}
			
			// ES
			pos[0] = location[0] + 1;
			pos[1] = location[1] + 2;
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}
			
			// SE
			pos[0] = location[0] + 2;
			pos[1] = location[1] + 1;			
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}		
			
			// SW
			pos[0] = location[0] + 2;
			pos[1] = location[1] - 1;
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}

			// WS
			pos[0] = location[0] + 1;
			pos[1] = location[1] - 2; 
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}

			// WN
			pos[0] = location[0] - 1;
            pos[1] = location[1] - 2;
			if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8 && board[pos[0]][pos[1]].hasPiece()) {
				if(!board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam) && board[pos[0]][pos[1]].getPiece().getName().equals("Knight")) return true;
			}
			
		return false;
	}
	
	/**
	 * Gets the location of the king of the current player.
	 * @param play represents the player's team. (white or black)
	 * @return an array containing the row and column information about the King's location.
	 */
	private int[] getKingLocation(int play) {
		int[] loc = {-1,-1};
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				// White King
				if(board[i][j].hasPiece()) { 
					if(play == 0 && board[i][j].getPiece().toString().equals("White King")) { 
						loc[0] = i;
						loc[1] = j;
						return loc;
					}

					// Black King
					if(play == 1 && board[i][j].getPiece().toString().equals("Black King")) {
						loc[0] = i;
						loc[1] = j; 
						return loc;
					}			
				}		
			}
		}
		
		return loc;
	}
	
	
	/**
	 * Handles whether the current player's king is in check mate.
	 * @return true if the current player is in check mate.
	 */
	private boolean isMate(int player) {
		String playerTeam = player == 0 ? "White" : "Black";
		int[] location = getKingLocation(player); 
		int[] pos = location.clone();		
		/*
		 * Idea here is to call check all on the available titles to the king
		 * if they all turn out to be true (aka false to prevent a return false from being sent)
		 * then the king is in mate
		 * WE need to check the same things as the check directions
		 * And the King title itself which may be redundant but its another check ig
		 * UL | U | UR
		 * L  | K | R 
		 * DL | D | DR 
		 * 
		 */

		// Up Left
		pos[0] = location[0] - 1;
		pos[1] = location[1] - 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// UP 
		pos[0] = location[0] - 1;
		pos[1] = location[1];
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// Up Right
		pos[0] = location[0] - 1;
		pos[1] = location[1] + 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;
			
		// Left 
		pos[0] = location[0];
		pos[1] = location[1] - 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// Right 
		pos[0] = location[0];
		pos[1] = location[1] + 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// Down Left 
		pos[0] = location[0] + 1;
		pos[1] = location[1] - 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// Down
		pos[0] = location[0] + 1;
		pos[1] = location[1];
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;

		// Down Right
		pos[0] = location[0] + 1;
		pos[1] = location[1] + 1;
		if(pos[0] > -1 && pos[0] < 8 && pos[1] > -1 && pos[1] < 8) 
			if(!board[pos[0]][pos[1]].hasPiece() || !board[pos[0]][pos[1]].getPiece().getTeam().equals(playerTeam))
				if(!isCheck(player, pos)) return false;


		// King Tile for stalemate / checkmate checks
		if(isCheck(player, getKingLocation(player))) {
			return true; 
		}  
		// technically this would be stalemate but I don't think this is implemented
		
		return false;
	}
	
	/**
	 * Checks if the game has ended.
	 * @return true if the game has ended
	 */
	public boolean endgame() {
		if(playerResign || mate) return true;
		return false;
	}

	/**
	 * Returns the name of the winner. 
	 * @return a String representing the winner of the game. 
	 */
	public String getWinner() {
		return winner;
		// this will either be set in isMate() or endgame()
	}

	/**
	 * Handles if the game has ended in a draw or not.
	 * @return true if the game ended in a draw.
	 */
	public boolean getDraw() {
		return staleGame;
	}

}
