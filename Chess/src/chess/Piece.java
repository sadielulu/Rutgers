package chess;

public interface Piece {

	/**
	 * 
	 * @author Silvia Carbajal
	 *
	 */
	
	/**
	 * determines whether a move is legal or not
	 * @param x which File to move the piece
	 * @param y which rank to move the piece to
	 * @return true if move is legal, false if its not legal
	 */
	boolean canMove(int x, int y);
	
	/**
	 * moves piece
	 * @param x - which File to move the piece to
	 * @param y - which Rank to move the piece to 
	 * @return true if the move can be executed, false otherwise
	 */
	void movePiece(int x, int y);

	/**
	 * draws the piece with its designated name
	 * @return piece's name
	 */
	String drawPiece();
	
	/**
	 * prints name
	 */
	public void getPieceName();
	
	/**
	 * Get the color of a given chesspiece
	 * @return 0 if color is white , 1 if color is black
	 */
	public int getColor();

	/**
	 * @return moveCount for the piece
	 */
	public int getMovecount();
	/**
	 * 
	 * @return Piece's file a-h represented as 0-7
	 */
	public int getFile();
	/**
	 * 
	 * @return rank represented as 0-7
	 */
	public int getRank();
}
