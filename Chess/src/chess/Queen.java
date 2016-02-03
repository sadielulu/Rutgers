package chess;

public class Queen implements Piece {

	/**
	 * 
	 * @author Silvia Carbajal
	 *
	 */
	private String name ="Q" ;
	private int color;
	int x,y;
	
	public Queen (Boolean tf, int cur_file, int cur_rank)
	{
		if (tf== true)
		{
			this.name = "w" +name;
			color = 0;
		}
		else 
		{
			this.name = "b" +name;
			color = 1;
		}
		x = cur_file;
		y = cur_rank;
	}

	@Override
	public boolean canMove(int newX, int newY) {
		int	col = Math.abs(newX-x);
		int row = Math.abs(newY-y);
		
		int c=Board.board[x][y].getColor();

		int oldX=x;
		int oldY=y;
		
		if (col == row)  //moves diagonally 
		{
			{
				if(newY<oldY)//if newy is less than old y 
				{
					//check if x is increasing or decreasing
					if(oldX<newX)
					{
						oldX++;
						oldY--;
						while(oldX<newX)
						{	//x is inc y is dec
							if(Board.checkSpace(oldX, oldY)) 
							{
								//System.out.println("something is in the space, not null");
								return false;
							}
							oldX++;
							oldY--;
							//3,3 to 6,0 >
							//check 4,2 5,1 6,0
						}
						if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
						{
							return true ;
						}
						else //if there is a piece
						{ 
							if (c!= Board.board[newX][newY].getColor())
							{
								return true;
							}
							else
							{
								//System.out.println("same color cant");
								return false;
							}
						}
					}	
					else if (oldX>newX)
					{
						oldX--;
						oldY--;
						while(oldX>newX)
						{
							if(Board.checkSpace(oldX, oldY)) 
							{
								//System.out.println("something is in the space, not null");
								return false;
							}
							oldX--;
							oldY--;
							//x is dec and y is dec
							// 3,3 to 0,0 <
							//check 33,22,11,00
						}
						if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
						{
							return true ;
						}
						else
						{ //if there is a piece
							if (c!= Board.board[newX][newY].getColor())
							{
								return true;
							}
							else
							{
							//	System.out.println("same color cant");
								return false;
							}
						}
					}
				}	

				
				
				
				else if(newY>oldY)//if newy is more than old y 
				{
					//check if x is incresing or decreasing
					if(oldX<newX)
					{	
						oldX++;
						oldY++;
						while(oldX<newX)
						{
							
							if(Board.checkSpace(oldX, oldY)) 
							{
								//System.out.println("something is in the space, not null");
								return false;
							}
							oldX++;
							oldY++;
							//x is inc and y is inc
							//3,3 to 5,5
							//check 3,3 4,4 5,5, 
						}
						if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
						{
							return true ;
						}
						else
						{ //if there is a piece
							if (c!= Board.board[newX][newY].getColor())
							{
								return true;
							}
							else
							{
						//		System.out.println("same color cant");
								return false;
							}
						}
					}
					else if(oldX>newX)
					{
						oldX--;
						oldY++;
						while(oldX>newX)
						{
							if(Board.checkSpace(oldX, oldY)) 
							{
			
							//	System.out.println("something is in the space, not null");
								return false;
								//x is dec and y is inc
								//3,3 to 1,5
								// 3,3 2,4 1,5
							}
							oldX--;
							oldY++;
						}
						if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
						{
							return true ;
						}
						else
						{ //if there is a piece
							if (c!= Board.board[newX][newY].getColor())
							{
								return true;
							}
							else
							{
							//	System.out.println("same color cant");
								return false;
							}
						}
					}
				}
			}				
		}
		
		else if(newX==x&&newY!=y ||newY==y && newX!=x) //moves up and down and sideways only
		{
			if(newY==oldY) //moves up and down
			{
				if(oldX<newX) //check if x is smaller than new x or bigger
				{
					oldX++;
					while(oldX<newX)
					{
						if(!Board.checkSpace(oldX, oldY)) 
						{
							oldX++;	//old x ++ goes up
						}
						else
						{
							//System.out.println("something is in the way, not null path");
							return false;
						}
					}
					//checks destination space
					if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
					{
						return true ;
					}
					else
					{ //if there is a piece
						if (c!= Board.board[newX][newY].getColor())
						{
							return true;
						}
						else
						{
							//System.out.println("same color cant");
							return false;
						}
					}
				}
				else if(oldX>newX)
				{
					oldX--;
					while(oldX>newX)
					{
						if(!Board.checkSpace(oldX, oldY)) 
						{
							oldX--;	//newx ++goes up
						}
						else
						{
						//	System.out.println("something is in the space, not null");
							return false;
						}
					}
					//checks destination space
					if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
					{
						return true ;
					}
					else
					{ //if there is a piece
						if (c!= Board.board[newX][newY].getColor())
						{
							return true;
						}
						else
						{
							//System.out.println("same color cant");
							return false;
						}
					}
				}
			}
			else if(newX==oldX) //moves sideways only
			{
				if(oldY<newY)//check if y is smaller or bigger
				{
					oldY++;
					while(oldY<newY)
					{
						if(!Board.checkSpace(oldX, oldY))
						{
							oldY++;	//old x ++ goes up
						}
						else
						{
							//System.out.println("something is in the way, not null");
							return false;
						}
					}
					//checks destination space
					if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
					{
						return true ;
					}
					else
					{ //if there is a piece
						if(c!= Board.board[newX][newY].getColor())
						{ 
							return true;
						}
						else
						{
						//	System.out.println("same color cant");
							return false;
						}
					}
				}
				else if(oldY>newY)//check if y is smaller or bigger
				{
					oldY--;
					while(oldY>newY)
					{
						if(!Board.checkSpace(oldX, oldY))
						{
							oldY--;	//newx ++goes up
						}
						else
						{
						//	System.out.println("something is in the space, not null");
							return false;
						}
					}
					//checks destination space
					if(!Board.checkSpace(oldX, oldY))//if there isnt a piece  on the destination 
					{
						return true ;
					}
					else
					{ //if there is a piece
						if (c!= Board.board[newX][newY].getColor())
						{
							return true;
						}
						else
						{
							//System.out.println("same color cant");
							return false;
						}
					}	
				}
			}	
		}
		return false;
	}

	@Override
	public void movePiece(int newx, int newy) 
	{
		x=newx;
		y=newy;
	}
	@Override
	public int getFile(){
		return x;
	}
	@Override
	public int getRank(){
		return y;
	}

	@Override
	public String drawPiece() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return 0;
	}
}
