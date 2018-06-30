package hw4;

import java.io.*;
import java.util.Scanner;

/**
 * @author Gaganjeet Singh
 * most of the maxConnect4.java and GameBoard.java code is witted by James Spargo, taken from the class website.
 * 
 * This class controls the game play for the Max Connect-Four game. 
 * To compile the program, use the following command from the maxConnectFour directory:
 * javac *.java
 *
 * the usage to run the program is as follows:
 *
 *  -- for interactive mode:
 * java MaxConnectFour interactive [ input_file ] [ computer-next / human-next ] [ search depth]
 *
 * -- for one move mode
 * java maxConnectFour.MaxConnectFour one-move [ input_file ] [ output_file ] [ search depth]
 * 
 * description of arguments: 
 *  [ input_file ]
 *  -- the path and filename of the input file for the game
 *  
 *  [ computer-next / human-next ]
 *  -- the entity to make the next move. either computer or human. can be abbreviated to either C or H. This is only used in interactive mode
 *  
 *  [ output_file ]
 *  -- the path and filename of the output file for the game.  this is only used in one-move mode
 *  
 *  [ search depth ]
 *  -- the depth of the minimax search algorithm
 *   
 */

public class maxconnect4
{
  public static void main(String[] args) 
  {
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");
      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
		
    // create and initialize the game board
    GameBoard currentGame = new GameBoard( input );
    
    // create the Ai Player. Enter the depthLevel for search into constructor
    AiPlayer calculon = new AiPlayer(depthLevel);
		
    //  variables to keep up with the game
    int playColumn = 99;				//  Calculon's choice of column to play

    //Interactive game mode. Plays turn by turn. 
    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
    	// Interactive Mode! (Now implemented)
    	System.out.print("\nMaxConnect-4 game\n");
    	System.out.print("game state before move:\n");
    	
    	//scanner to read user input
    	Scanner getUserMove = new Scanner(System.in);

    	//checking the argument for who to start
    	String turn = args[2].toString();
    	
    	//if its computer-next, then mapPlayer can be initialized as the current player's turn
    	//if not, then the human needs to make the move first and then we initialize maxPlayer
    	//maxPlayer is set when minimaxDecision is called.
    	if(turn.equalsIgnoreCase("human-next")) {
    		if(currentGame.getPieceCount() < 42) {
    			
    			//print the game board and score
    			currentGame.printGameBoard();
        		currentGame.printScores();
        		
        		//get user input and then make the move 
        		int userMove = getUserMove.nextInt();
        		
        		//keep asking for input until user enters the correct one
        		while(!currentGame.playPiece(userMove)){
        	    	currentGame.printGameBoard();
        	    	System.out.println("That is an invalid move, choose again");
        	    	userMove = getUserMove.nextInt();
        		}
        		
        		//saving board state into human.txt
        		currentGame.printGameBoardToFile("human.txt");
    		}
    	}
    	
    	//if the argument is neither human or computer, then computer is set to start first
    	else {
    		System.out.println("Invalid turn option. Computer playing first");
    	}
    	
		
		//Main while loop for interactive mode. Will break once we hit 42 piece counts (full board)
    	while(currentGame.getPieceCount() < 42) {
    		//printing board and the scores
    		currentGame.printGameBoard();
    		currentGame.printScores();
    		
    		int current_player = currentGame.getCurrentTurn();
    		
    		// AI plays the best solution
    		playColumn = calculon.minimaxDecision( currentGame );
    		
    		// play the piece
    		currentGame.playPiece( playColumn );
	        	
	        // display the current game board
	        System.out.println("move " + currentGame.getPieceCount() 
	                           + ": Player " + current_player
	                           + ", column " + playColumn);
	        currentGame.printGameBoard();
	    
	        // print the current scores
	        currentGame.printScores();
	        
	        //storing the computer move into a file called "computer.txt"
	        //this file only stores the game state after calculon makes a move
	        currentGame.printGameBoardToFile( "computer.txt" );
	        
	        //checks if calculon's move completed the board
	        if(currentGame.getPieceCount() >= 42) {
	        	break;
	        }
	        
	        //getting user input
	        System.out.println("Choose your move: ");
	        int userMove = getUserMove.nextInt();
	        
	        //setting a while loop until user selects a valid column to play
    	    while(!currentGame.playPiece(userMove)) {
    	    	currentGame.printGameBoard();
    	    	System.out.println("That is an invalid move, choose again");
    	    	userMove = getUserMove.nextInt();
    	    }
    	    
    	    //prints the board into a file called "human.txt"
    	    //this file only stores the game state after the human makes a move
    		currentGame.printGameBoardToFile("human.txt");
    	}
    	
    	//once the game has ended, we print the board and show the scores
    	currentGame.printGameBoard();
        currentGame.printScores();
    	System.out.println("No more moves");
    	
    	//closing the scanner used to read user input
    	getUserMove.close();
    	
    	//exiting the game
    	return;
    } 
			
    else if( !game_mode.equalsIgnoreCase( "one-move" ) ) 
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      return;
    }

    /////////////   one-move mode ///////////
    // get the output file name
    String output = args[2].toString();				// the output game file
    
    System.out.print("\nMaxConnect-4 game\n");
    System.out.print("game state before move:\n");
    
    //print the current game board
    currentGame.printGameBoard();
    // print the current scores
    currentGame.printScores();
    
    // ****************** this chunk of code makes the computer play
    if( currentGame.getPieceCount() < 42 ) 
    {
        int current_player = currentGame.getCurrentTurn();
	// AI play - random play
	playColumn = calculon.minimaxDecision( currentGame );
	
	// play the piece
	currentGame.playPiece( playColumn );
        	
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() 
                           + ": Player " + current_player
                           + ", column " + playColumn);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();
    
        // print the current scores
        currentGame.printScores();
        
        currentGame.printGameBoardToFile( output );
    } 
    else 
    {
	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    }
	
    //************************** end computer play
    return;
    
} // end of main()
	
  /**
   * This method is used when to exit the program prematurely.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
} // end of class connectFour