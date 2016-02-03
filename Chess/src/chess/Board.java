package chess;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Oskar Bero
 * @author Silvia Carbajal
 */
public class Board {
	static Piece [][]  board;
	boolean flag=false;

	 public Board()
	 {
		board = new Piece [8][8];
		initialize();
	 }
	
	public void initialize()
	{
		board[0][1]=new Pawn(true, 0, 1);
		board[1][1]=new Pawn(true, 1, 1);
		board[2][1]=new Pawn(true, 2, 1);
		board[3][1]=new Pawn(true, 3, 1);
		board[4][1]=new Pawn(true, 4, 1);
		board[5][1]=new Pawn(true, 5, 1);
		board[6][1]=new Pawn(true, 6, 1);
		board[7][1]=new Pawn(true, 7, 1);
		
		board[0][0]=new Rook(true, 0, 0);
		board[1][0]=new Knight(true, 1, 0);
		board[2][0]=new Bishop(true, 2, 0);
		board[3][0]=new Queen(true, 3, 0);
		board[4][0]=new King(true, 4, 0);
		board[5][0]=new Bishop(true, 5, 0);
		board[6][0]=new Knight(true, 6, 0);
		board[7][0]=new Rook(true, 7, 0);
		
		//pawns across the rank of 6 / file 0-7 (a-h)
		board[0][6]=new Pawn(false, 0, 6);
		board[1][6]=new Pawn(false, 1, 6);
		board[2][6]=new Pawn(false, 2, 6);
		board[3][6]=new Pawn(false, 3, 6);
		board[4][6]=new Pawn(false, 4, 6);
		board[5][6]=new Pawn(false, 5, 6);
		board[6][6]=new Pawn(false, 6, 6);
		board[7][6]=new Pawn(false, 7, 6);
		
		board[0][7]=new Rook(false, 0, 7);
		board[1][7]=new Knight(false, 1, 7);
		board[2][7]=new Bishop(false, 2, 7);
		board[3][7]=new Queen(false, 3, 7);
		board[4][7]=new King(false,4,7);
		board[5][7]=new Bishop(false, 5, 7);
		board[6][7]=new Knight(false, 6, 7);
		board[7][7]=new Rook(false, 7, 7);

		//Draw the fresh board
		for(int j = 7; j >= 0; j--)
		{
			for(int i = 0; i < 8; i++){

				if(board[i][j] == null)
				{
					if(i%2 != 0 && j%2 != 0) //if both i and j are odd
					{
						System.out.print("## ");
					}
					else if(i%2 == 0 && j%2 == 0)
					{
						System.out.print("## ");
					}
					else
					{
						System.out.print("   ");
					}
				}
				else //not null print out content
				{
					System.out.print(board[i][j].drawPiece());
				}
				if(i == 7) //reached the end of line 
				{
					System.out.println(" " + (j+1));
				}
			}
			
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	
	/**
	 * Board.move() takes origin and destination of a piece on the board
	 * then calls the given game piece's move command.
	 * @param x - File of the piece to be moved
	 * @param y - Rank of the piece to be moved
	 * @param newx - Destination File
	 * @param newy - Destination Rank
	 * @return true if move is allowed, false otherwise
	 */
	public boolean move(int file, int rank, int new_file , int new_rank)
	{		
		Piece curPiece = board[file][rank];
		//if(flag==true)
		//{
			//while(curPiece.drawPiece().charAt(1)!='K')
			//{
				//return false;
			//}
			//move
			//flag =false;
	//	}
		
		
		if(curPiece != null) //check if (x,y) contains a chess piece
		{
			/*Just checking what were asking the chess piece to do
			System.out.print("Asking to move ");
			System.out.print(" from ( " + file + ", " + rank + " )");
			System.out.println(" to ( " + new_file + ", " + new_rank + " )");
			System.out.print("The piece you asked for is "); 
			board[file][rank].getPieceName();
			System.out.println();
			 */
			if(curPiece.canMove(new_file, new_rank))
			{
				if(board[new_file][new_rank] !=  null) //aka the player is capturing an opposing piece
				{
					//Check color of the moving piece and the target piece					
					if(board[new_file][new_rank].getColor() != curPiece.getColor())
					{
						//
						
						//System.out.println("Moving to capture!");
						board[file][rank].movePiece(new_file, new_rank); //change the piece

						//then make the destination space equal the piece being moved
						board[new_file][new_rank] = board[file][rank];
						//clear the previous location to have no pieces 
						board[file][rank] = null;
						return true;
					}
					else //pieces are same color cant do the move
					{
						//System.out.println("A piece of your color is already at there");
						return false;
					}
				}
				else
				{
					//System.out.println("Moving to empty space");
					//save new location for the piece	
					board[file][rank].movePiece(new_file, new_rank);

					//move the piece to the new place on the board, empty the last space 
					board[new_file][new_rank] = board[file][rank];
					board[file][rank] = null;

					return true;
				}
			}
			else 
			{
				System.out.println("Illegal move, try again");
				return false;
			}
		}
		else
		{
			System.out.println("Illegal move, try again");
			//Error, origin was null aka there was no piece at (x,y) to be moved
			return false;
		}
	}
	
	public boolean promotion(char newPiece,int cur_file,int cur_rank, int file, int rank)
	{
		boolean color; //for new Piece creation when a pawn is promoted
		Piece temp = board[cur_file][cur_rank]; //temporary piece for local operationsRS
		if(rank == 7)
			color = true; //white as per Piece constructors
		else
			color = false; //black as per Piece constructors
		
		//for whatever reason the instanceOf method wasn't doing it , this works though
		if(temp.drawPiece().charAt(1) == 'p')//pawn reached the last rank for its color
		{
			board[cur_file][cur_rank] = null;
			switch(newPiece)
			{
				case 'R':
					temp = new Rook(color, rank, file);
					break;
				case 'B':
					temp = new Bishop(color, rank, file);
					break;
				case 'N':
					temp = new Knight(color, rank, file);
					break;
				default:
					temp = new Queen(color, rank, file);
					break;
			}
			//put the new piece on the board
			board[file][rank] = temp;
			return true;
		}
		return false;	
	}
	
	
	/**
	 * Check the space (by file and rank) to see if a Piece occupies given spot
	 * @param file - the column of the spot to be checked
	 * @param rank - the row of the spot to be checked
	 * @return true if there is a piece in the way, and false otherwise
	 */
	public static boolean checkSpace(int file, int rank)
	{
		if(board[file][rank] != null) // a piece is indeed in this spot
		{
			//System.out.println("Space checked has a piece");
			return true;
		}
		else
		{
			return false;
		}		
	}
	
	/**
	 * 	//called before each players move
	 * @param color-opposing turn's color
	 * @param kingX-current turn's king x position
	 * @param kingY-current turn's king y position
	 */
	public int check(int kingX, int kingY)
	{	
		boolean mate = false;
		//3 ways to prevent a checkmate
		boolean move = false;
		boolean capture = false;
		boolean intercept = false; 
		Piece king = board[kingX][kingY];
				
		Piece attacker = canBeCaptured(king.getColor(),kingX, kingY); //find a piece that can attack the king
		
		//if that piece is not null meaning check is happenning
		if(attacker != null)
		{
			//check if that attacker can be captured by your piece
			if(canBeCaptured(attacker.getColor(),attacker.getFile(), attacker.getRank()) != null)
			{
			//	System.out.println("Enemy piece can be captured ");
				//not mate but still check
				capture = true;
			}		
			//else if king can move somewhere safe -> move = true;
			if(canMoveKingSafely(king))
			{
			//	System.out.println("King can move to safety");
				move = true;
			}
			//if cant be captured, can it be intercepted? -> intercept = true;
			if(interceptPath(attacker, king))
			{
			//	System.out.println("Friendly piece can cover");
				intercept = true;
			}
			//check 
			if(!move && !capture && !intercept)
			{
				//checkmate
				return 2; 
			}
			if(move || capture || intercept)
			{
				//check 
				return 1;
			}
		}
			
		
		//no check
		return 0;
		
	/* Silvia's check algorithm 
		for(int j = 7; j >= 0; j--)	// check array for existing pieces in board for color
		{
			for(int i = 0; i < 8; i++)
			{
				if(checkSpace(i,j))//has a piece
				{ 
					if(board[i][j].getColor()==color) //check if piece is same as color
					{
						if(board[i][j].canMove(kingX, kingY))//if same check if each piece can move to kings in one move
						{	//if yes for any return check 
							//flag=true;
							System.out.println("Check");
							
							return true;
						}
					}
				}
			}
		}*/
	}
	
	public boolean interceptPath(Piece attacker, Piece king)
	{
		int x = 0;
		int y = 0;
		List<int[]> validXY = new ArrayList<int[]>();
		int[] tuple = new int[2];
		//This is the area we want to check paths in
		int changeInX = king.getFile() - attacker.getFile();
		int changeInY = king.getRank() - attacker.getRank();
		
		//get all valid coordinates for attacker to move
		for(x = 0; x < 8; x++)
		{
			for(y = 0; y < 8; y++)
			{
				if(attacker.canMove(x, y))
				{
					//save x y pair
					tuple[0] = x;
					tuple[1] = y;
					//add the pair to valid moves
					validXY.add(tuple);
				}
			}
		}
		
		//get pieces from the board
		for(Piece[] array: board)
		{
			//check each piece of your  color for being on a valid path
			for(Piece p: array)
			{
				//if piece has opposite color of the attacker and is Not the king
				if(p != null && p.getColor() != attacker.getColor() && p.drawPiece().charAt(1) != 'K')
				{			
					//check if the piece can travel through a path thats also valid for attacker
					for(int[] paths: validXY)
					{
						if(p.canMove(paths[0], paths[1])) //if piece can move to a valid path of the attacker 
						{
							//Then check which direction relative to the attacker is the piece in and if it is between the attacker and king - true
							if(changeInX < 0)//
							{
								//piece can move to a path between kings file and attackers file
								if(paths[0] < attacker.getFile() && paths[0] > king.getFile())
								{
									if(changeInY < 0)//piece can move left and DOWN
									{
										if(paths[1] < attacker.getRank() && paths[1] > king.getRank())
										{
											return true;
										}
									}
									else //left and up 
									{
										if(paths[1] > attacker.getRank() && paths[1] < king.getRank())
										{
											return true;
										}
									}
								}
							}
							else //change in x >= 0
							{
								//if p is between the attacker and the king
								if(paths[0] > attacker.getFile() && paths[0] < king.getFile() || (changeInX == 0))
								{
									if(changeInY < 0)//king is to the right and down of our attacker
									{
										//paths 1 cant = attacker or king
										if(paths[1] < attacker.getRank() && paths[1] > king.getRank())
										{
											return true;
										}
									}
									else //king is right and up (or right and staying same) change in y > 0 cant equal 0 wouldnt be in the path
									{
										if(paths[1] > attacker.getRank() && paths[1] < king.getRank())
										{
											return true;
										}
									}
								}
							}
						}//end check of X coordinate
					}//end loop on valid paths
				}//end if(not king, opposite color and not null)
			}//end for(Piece p : array)
		}//end for(Piece [] array : board)
						
						return false;
	}
	
	
	/**
	 * 
	 * @param King piece 
	 * @return true if king can be moved safely out of check, false otherwise
	 */
	public boolean canMoveKingSafely(Piece king) //mycolor
	{
		//check if any are less than 0 or more than 7 
		//check 2 on y  
		//x-1 and x+1

		//check 3 on y+1
		//x-1 and x+1 and x

		//check 3 on y-1
		//x-1 and x+1 and x
		//System.out.println("checkinggggg");
		//int c=0;
		boolean ch=false,ch1=false,ch2=false,ch3=false;
		//Piece p =getKingPos(color);
		
		int x= king.getFile();
		int y= king.getRank();
		//System.out.println(x);
		//System.out.println(y);

		if((x-1) >= 0 && (x-1) <= 7)
		{
			if(canBeCaptured(king.getColor(),x-1,y) != null)
			{	
				ch=true;
				//System.out.println("a");
			}
		}
		if(x+1 >= 0 && x+1 <= 7)
		{
			if(canBeCaptured(king.getColor(),x+1,y) != null)
			{
				ch1=true;
			//	System.out.println("b");

			}
		}
								
		if(y-1 >= 0 && y-1 <= 7)
		{
			if(canBeCaptured(king.getColor(),x+1,y-1) != null && canBeCaptured(king.getColor(),x,y-1) != null &&canBeCaptured(king.getColor(),x-1,y-1) != null)
			{
				ch2=true;
			//	System.out.println("c");

			}
		}	
													
		if(y+1 >= 0 && y+1 <= 7)
		{
			if (canBeCaptured(king.getColor(),x-1,y+1) != null && canBeCaptured(king.getColor(),x+1,y+1)  != null && canBeCaptured(king.getColor(),x,y+1) != null)
			{
				ch3=true;
			//	System.out.println("d");

			}
		}
		
		if(ch&&ch1&&ch2&&ch3)
		{
		//	System.out.println("checkmate");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Check if a specific piece can be captured by the opponent 
	 * @param file of the piece to check for capture
	 * @param rank of the piece to check for capture
	 * @return return the attacker if the piece can be captured , nulls otherwise
	 */
	public Piece canBeCaptured(int color, int file, int rank)
	{
		
		
		for(Piece[] array: board)
		{
			for(Piece p: array)
			{
				if(p != null)
				{
					//check for pieces that can move to the target location AND have opposite color
					if(p.canMove(file,rank) && (p.getColor() != color))
					{
						//System.out.println("King can be captured at " +file + ", " + rank + "by enemy Piece: " + p.drawPiece());
						return p;
					}
				}
			}
		}
		
		//no pieces can capture the specified piece;
		return null;
	}

	public Piece getKingPos(int color)
	{	
		for(int j = 7; j >= 0; j--)	// check array for existing pieces in board for color
		{
			for(int i = 0; i < 8; i++)
			{
				if(checkSpace(i,j))//has a piece
				{
				if(board[i][j].drawPiece().charAt(1)=='K')
					{		
						if (board[i][j].getColor()==color)
						{
							return board[i][j]; 
							
						}
					}
				}
		}	}
		return null;
	}
	
	public void drawBoard()
	{		
		System.out.println();
		for(int j = 7; j >= 0; j--)
		{
			for(int i = 0; i < 8; i++){

				if(board[i][j] == null)
				{
					if(i%2 != 0 && j%2 != 0) //if both i and j are odd
					{
						System.out.print("## ");
					}
					else if(i%2 == 0 && j%2 == 0) //if both i and j are even
					{
						System.out.print("## ");
					}
					else
					{
						System.out.print("   ");
					}
				}
				else //not null print out content
				{
					System.out.print(board[i][j].drawPiece());
				}
				if(i == 7) //reached the end of line 
				{
					System.out.println(" " + (j+1));
				}
			}
			
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
		
	}

}