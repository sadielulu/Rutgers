package chess;

import java.util.Scanner;

/**
 * 
 * @author Oskar Bero
 * @author Silvia Carabajal
 *
 */
public class Chess {
	public static void main(String[]args){
		Game game = new Game();	//The fresh game board will be drawn by Board's constructor 
	
		
		//USER INPUT STARTS
		boolean exit = false;
		boolean draw = false;
		int check = 0;
		Scanner scanner = new Scanner(System.in);
	
		String line;
		while(exit != true)
		{ 
		//when Checkmate is printed, game ends
		//results printed
		//with winner printed at end "black/white wins"
			
			//( round number )
			if(game.getRound()%2 != 0)
			{
				//if (game.chessBoard.checkmate(1))
				//{
					//end game
					//exit=true;
					//return;
				//}				
				Piece p = game.chessBoard.getKingPos(0); //my color 
				int f =p.getFile();
				//System.out.println(f);
				int r =p.getRank();
				//System.out.println(r);
				check = game.chessBoard.check(f,r); //check for check 
				if(check == 1)//check NOT mate
				{
					System.out.println("CHECK!");
					System.out.print("(" + game.round + ")White's turn:");
					//line = scanner.nextLine();
					
				}
				else if(check == 2) //checkmate
				{
					System.out.println("Black Wins");
					exit = true;
				}
				else
				{
					System.out.print("(" + game.round + ")White's turn:");
				}
			}
			else
			{

				Piece p =game.chessBoard.getKingPos(1); //my color 
				int f =p.getFile();
				int r =p.getRank();
				check = game.chessBoard.check(f, r); // which better
				if(check == 1)//check NOT mate
				{
					//check have to limit movement options ?
					System.out.println("Check!");
					System.out.print("(" + game.round + ")Black's turn:");
					
				}
				else if(check == 2) //checkmate
				{
					System.out.println("White Wins");
					exit = true;
				}
				else //move on
				{
					System.out.print("(" + game.round + ")Black's turn:");
				}
				
			}
			//take turns black's moves and white's move 
			line = scanner.nextLine();
			if (line.equals(null))
				//error check
			if(line.charAt(2)!=' ')
			{
				System.out.println("error, needs to be in the format FileRank FileRank");	
				return;
			}
			if(line.length() == 11) //move command plus draw ex. "g1 f3 draw?"
			{
				draw = true;
			}
			if(line.equals("draw") && draw == true) //if a question has been asked and answer is "draw"
			{
				System.out.println("It's a draw!");
				exit = true;
				return;
			}
			if(line.equals("resign"))
			{
				exit = true; //
			}
			else //parse the move check for check;
			{	
				
				game.move(line);
				line = null;
			}
			//do error checks
			//else illegal move
		}
		scanner.close();
	}
}