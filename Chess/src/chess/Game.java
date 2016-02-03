package chess;

/**
 * 
 * @author Oskar Bero
 *
 */
public class Game {

	Board chessBoard;
	int round;
	/**
	 * This class parses the input from Chess' main class and passes
	 * it to the board to be executed
	 */
	public Game() {
		chessBoard = new Board(); //new board for a new game
		round = 1; //first round
	}
	
	/**
	 * Move parses the input string from user then passes the x,y and new_x, new_y to the board to perform
	 * bounds check and hopefully move the piece from (x,y) to (new_x, new_y).
	 * Checks if the correct piece is moved for the correct player's turn i.e white piece for odd turns and black for even turns
	 * @param input - string from user that describes desired move
	 * @return true if move is allowed, false otherwise
	 */
	public void move(String input)
	{
		//file = a-h (x), rank = 1-8 (y)4
		int file, rank; //Move from
		int new_file, new_rank; //Move to
		char promoPiece = 'Q'; //default promotion value
		
		if(input.length() < 5)
		{
			System.out.println("Invalid command");
			return;
		}
		
		file = getFile(input.charAt(0)); //if we get -1 the parse failed
		rank = getRank(input.charAt(1)); //if we get -1 the parse failed
		
			
		if(input.charAt(2) != ' ' || file == -1 || rank == -1) //if initial move is not followed by space OR x or y = -1
		{
			System.out.println("Invalid orgin for move command!");
			return;
		}
		
		//check for correct turn order
		if(Board.board[file][rank] != null)
		{
			if(round % 2 != 0) //if round is even
			{
				if(Board.board[file][rank].getColor() == 1)
				{
					//Trying to move your opponenet's piece
					System.out.println("You cannot move your opponent's piece.");
					return;
				}
			}
			else
			{
				if(Board.board[file][rank].getColor() == 0)
				{
					System.out.println("You cannot move your opponent's piece.");
					return;
				}
			}
			
		}

		new_file = getFile(input.charAt(3)); //if we get -1 the parse failed
		new_rank = getRank(input.charAt(4)); //if we get -1 the parse failed
		
		if(new_file == -1 || new_rank == -1) //check if we failed on the destination
		{
			System.out.println("Invalid destination for move command!");
			return;
		}

		if(input.length() == 7)
		{
			if(input.charAt(5) == ' ')
			{
				promoPiece = input.charAt(6);
			}
		}		
		if(new_rank == 7 || new_rank == 0)
		{
			if(chessBoard.promotion(promoPiece, file, rank, new_file, new_rank))
			{
				round++;
				chessBoard.drawBoard();
				return;
			}
		}
		if(chessBoard.move(file, rank, new_file, new_rank))
		{	
			round++;
		}
		
		chessBoard.drawBoard();
		
		return;
		
	}

	
	
	private int getFile(char x)
	{
		switch(x) //switch based on the first character of the input string ex. "e2 e4"
		{
			case 'a':
				return 0;
			case 'b':
				return 1;
			case 'c':
				return 2;
			case 'd':
				return 3;
			case 'e':
				return 4;
			case 'f':
				return 5;
			case 'g':
				return 6;
			case 'h':
				return 7;	
			default: //Error not valid first parameter of move
				return -1;
		}
	}
	
	private int getRank(char y)
	{
		String parse = "" + y; //make y into a string to be able to parse it
		int rank;			
		try
		{
			rank = Integer.parseInt(parse);  
			
			if(rank >= 0 && rank <= 8)
			{
				return rank-1; //Have to reverse the order for usage in the 2d array
			}
			else
			{
				return -1;
			}
		}
		catch(NumberFormatException e) //only thrown if the parse fails - get 0 back
		{
			return -1;
		}
	}
	
	public int getRound()
	{
		return round;
	}
}











