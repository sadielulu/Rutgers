package chess;

/**
 * 
 * @author Oskar Bero
 *
 */
public class Pawn implements Piece {
	
	private String name = "p";
	private int moveCount = 0;
	private int cur_file, cur_rank;
	private int color; //Used to check direction of movement for pawn, 0 = w, 1 = b
	
	
	public Pawn (Boolean tf, int cur_file, int cur_rank){
		
		if(tf == true){
			this.name = "w" +name;
			color = 0; //white
		}
		else {
			this.name = "b" +name;
			color = 1; //black
		}
				
		//get the starting position for this instance of a pawn
		this.cur_file = cur_file;
		this.cur_rank = cur_rank;
	}

	@Override
	public boolean canMove(int file, int rank) {	
		//If there is a difference in files and there is a piece present at the desired destination
		if(file != cur_file)
		{
			if(Math.abs(file - cur_file) == 1) //check if there is a piece there and if were moving only 1 file
			{		
				if(Board.checkSpace(file, rank))
				{
					if(color == 0 && (rank - cur_rank == 1)) //white and moving up
					{
						return true;
					}
					else if(color == 1 && (cur_rank - rank == 1)) //black and moving down
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				else if(enpassant(file, rank))//if the move is a valid enpassant return true
				{
					//System.out.println("Enpassant SUCCESS");
					return true;
				}
			}
			return false;
		}
		
		//the column is not changing so if there is a piece directly in the path this cant move
		if(Board.checkSpace(file, rank))
		{
			//System.out.println(" there is a piece there");
			return false;
		}
			 
		
		if(color == 0)//white .. can only move for positive rank
		{
			if(moveCount == 0)
			{
				if((rank - cur_rank) == 2)
				{
					//System.out.println("First move for this pawn");
					return true; //return true 
				}
			}
			if((rank - cur_rank) == 1)
			{
				//System.out.println("White Pawn cur_file =: " + cur_file + " new file: " + file + "cur Rank: " + cur_rank + " rank: " + rank);
				return true; //return true 
			}
			else
			{
				return false;
			}
		}
		else if(color == 1) //black .. move for negative rank
		{
			
			if(moveCount == 0)
			{
				if((cur_rank - rank) == 2)
				{
					return true; //return true 
				}
			}
			if((cur_rank - rank) == 1) 
			{
				//System.out.println("White Pawn cur_file =: " + cur_file + " new file: " + file + "cur Rank: " + cur_rank + " rank: " + rank);
				return true; //return true S
			}
			else
			{
				return false;
			}
		}
		return false;
	}

	@Override
	public void movePiece(int x, int y) {	
		cur_file = x;
		cur_rank = y;
		moveCount ++;
	}
	
	@Override
	public String drawPiece() {		
		return name+" ";
	}

	@Override
	public int getColor() {
		return color;
	}
	
	public boolean enpassant(int file, int rank)
	{
		//System.out.println("enpassant method called");
		Piece newPiece;
		if(color == 0)
		{
			newPiece = null;
			if(rank-1 > 0 && rank-1 < 7)
				newPiece = Board.board[file][rank-1];
			else
				return false;
			if(newPiece != null) //check if its not null
			{
				//the piece were passing is a pawn after its first move (for black pawn it means the rank is 5 - or 4 in the array)
				if(newPiece.drawPiece().charAt(1) == 'p' && newPiece.getMovecount() == 1 && newPiece.getRank() == 4)
				{
					Board.board[file][rank-1] = null;	//clear the enemy piece off the board 
					return true;
				}
			}
		}
		else if(color == 1)
		{
			newPiece = Board.board[file][rank+1];
			if(newPiece != null) //check if its not null
			{
				//the piece were passing is a pawn after its first move (for white pawn it means the rank is 4 (3 in the array)
				if(newPiece.drawPiece().charAt(1) == 'p' && newPiece.getMovecount() == 1 && newPiece.getRank() == 3)
				{
					Board.board[file][rank+1] = null;//destroy enemy piece, Board sees this as move to empty space
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param piece - character describing the piece that a pawn is gonna be promoted to 
	 * @param x
	 * @param y
	 * @param newx
	 * @param newy
	 * @return
	 */
	public static boolean promotion(char piece, Piece p, int new_file, int new_rank)
	{
		//p is an rinstance of Pawn checked by Game
		return false;

	}
	
	@Override
	public int getMovecount()
	{
		return moveCount;
	}
	
	@Override
	public void getPieceName()
	{
		System.out.println(name);	
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
