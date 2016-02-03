package chess;

/**
 * 
 * @author Oskar Bero
 *
 */
public class King implements Piece{
	private String name ="K" ;
	private int color, moveCount;
	private int cur_file, cur_rank;
	
	public King (Boolean tf, int file, int rank){
		if (tf== true){
			this.name = "w" +name;
			color = 0;
		}
		else {
			this.name = "b" +name;
			color = 1;
		}
		
		cur_file = file;
		cur_rank = rank;
		moveCount = 0;
	}

	@Override
	public boolean canMove(int new_file, int new_rank) {		
		int col = Math.abs(cur_file - new_file);
		int row = Math.abs(cur_rank - new_rank);
	
		if(moveCount == 0 && row == 0)
		{
			if(castling(new_file))
			{
				return true;
			}
		}
		if(col <= 1 && row <= 1)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * This function will return true if you can use castling to change the position of the king
	 * It also moves the rook piece and updates the board before returning to move the king
	 * @param new_file - the file that the king is gonna move to;
	 * @return true if the castling move can be done
	 */
	public boolean castling(int new_file)
	{
		if(new_file == 6) //castling king-side
		{
			if(!(Board.checkSpace(5, cur_rank) && Board.checkSpace(6, cur_rank))) //two spaces to the right of king are empty
			{
					if(Board.board[7][cur_rank].drawPiece().charAt(1) == 'R')
					{				
						if(Board.board[7][cur_rank].getMovecount() == 0)
						{//move the rook
							Board.board[7][cur_rank].movePiece(5, cur_rank);
							Board.board[5][cur_rank] = Board.board[7][cur_rank];
							Board.board[7][cur_rank] = null;
							return true;
						}
					}
					else
					{
						return false;
					}
				
			}
		}
		else if(new_file == 2) // castling queen-side
		{
			if(!(Board.checkSpace(3, cur_rank) && Board.checkSpace(2, cur_rank))) //two spaces to the left of king are empty
			{
				if(Board.board[0][cur_rank].drawPiece().charAt(1) == 'R') //rook on far left of the board
				{
					if(Board.board[0][cur_rank].getMovecount() == 0)
					{//move the rook
						//move the rook and get the king 
						Board.board[0][cur_rank].movePiece(3, cur_rank);
						Board.board[3][cur_rank] = Board.board[0][cur_rank];
					
						Board.board[0][cur_rank] = null; //cleared old 
						return true;
					}
				}
				else
				{
					return false;
				}
			}
			
		}		
		return false;
	}
	
	@Override
	public void movePiece(int x, int y) {
		cur_file = x;
		cur_rank = y;
		moveCount++;
		return;
	}

	@Override
	public String drawPiece() {
		return name+" ";
	}
	
	public void getPieceName()
	{
		System.out.println(name);	
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public int getMovecount() {
		return moveCount;
	}

	@Override
	public int getFile() {
		return cur_file;
	}

	@Override
	public int getRank() {
		return cur_rank;
	}

}
