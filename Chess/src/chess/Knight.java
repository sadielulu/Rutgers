package chess;

/**
 * 
 * @author Silvia Carbajal
 *
 */
public class Knight implements Piece {
	private String name ="N" ;
	private int color,moveCount;
	int x,y;

	public Knight(Boolean tf, int cur_file, int cur_rank)
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
		moveCount = 0;
		x = cur_file;
		y = cur_rank;		
	}

	@Override
	public boolean canMove(int newx, int newy) {

		int col;
		int row;

		col = Math.abs(newx-x);
		row = Math.abs(newy-y);

		if (col == 2 && row == 1)
		{ // |__
			return true;
		} 
		else if (col == 1 && row == 2) 
		{ //L
			return true;
		}
		return false;
	}

	@Override
	public void movePiece(int newx, int newy) {
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
