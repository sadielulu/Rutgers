package chess;

public class Rook implements Piece{

	/**
	 * 
	 * @author Silvia Carbajal
	 *
	 */
	private String name ="R" ;
	private int color,moveCount;
	int x,y;

	public Rook (Boolean tf, int cur_file, int cur_rank)
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
		moveCount = 0;
	}
	
	@Override
	public boolean canMove(int newX, int newY) {
		int oldX=x;
		int oldY=y;
		
		int c=Board.board[x][y].getColor();
	
		
		if(newX==x&&newY!=y||newY==y && newX!=x)  
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
							//System.out.println("something is in the space, not null");
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
					//		System.out.println("same color cant");
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
							//System.out.println("same color cant");
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
						//	System.out.println("same color cant");
							return false;
						}
					}	
				}
			}	
		}
			return false;
	}

	@Override
	public void movePiece(int newx, int newy) {
		x=newx;
		y=newy;
		moveCount++;
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
}
