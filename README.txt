Gaganjeet Singh - 1001070011
Assignment 4

For this assignment, I used Java 1.6.

I would like to participate in the tournament
-------------------------------------------------------------


To compile the code:

javac *.java 
or 
javac maxconnet4.java GameBoard.java AiPlayer.java


To run the code:

java maxconnect4 [one-move] [input_file] [output_file] [depth]
or
java maxconnect4 [interactive] [input_file] [computer-next/human-next] [depth]
-------------------------------------------------------------

Code structure

The code is divided up into 3 classes, maxconnect4, GameBoard, and the AiPlayer.


maxconnect4:
This class is the main class, it reads user input and runs the game. 
The class only has two functions, main and exit_function.

exit_function:
Takes the value to exit the function then calls system.exit()

main:
Checks the arguments passed in. Then creates a GameBoard and an AiPlayer object. 
Runs the game_mode, if the game_mode entered is not supported then it exits. 
In interactive, it runs a while loop where it takes in user input and plays it.
Then gets the computer's turn and plays it. It continues to play until the board is full
In one-move mode, it just gets the next move the computer will make, plays it and shows the board


GameBoard:
It implements a two dimension array that represents a connect four gameboard
It keeps track of the player making the next play based on the number of pieces
on the game board. It provides all of the methods needed to implement the playing
of a max connect four game.

Constructor:
Takes in the inputFile name. Creates a new 2D int array. Opens the input file with 
BufferedReader and FileReader. Then using a nested for loop, it fills in the 2D array.
Then it reads the line one last time and stores that int as the currentTurn. 

There is another constructor that takes in a 2D array to make a game board
It also uses a nested for loop to create the game board

getScore:
Takes in the player and calculates the score for that player. Then returns that value

getCurrentTurn:
Returns the current players turn

printScores:
Prints the scores of player 1 and player 2

getPieceCount:
Returns the total number of pieces on the board

getGameBoard:
Returns a 2D array of current game board

isValidPlay:
Checks if the column passed in is a valid play to be made on the board

playPiece:
Takes in the column to be played and then plays the piece for current players turn

removePiece:
Takes in the column number and removes the top piece from that column

printGameBoard:
Prints the game board to System.out

printGameBoardToFile:
Takes in the file name and then prints the board to that file the same way the input file
is constructed.

exit_function:
Exits the function with given value through System.exit()


AiPlayer:
Simulates a minimax player for max for the connect four game. 

Constructor:
Sets the depth limit to the limit passed in. 

minimaxDecision:
Initializes and sets up everything to begin the minimax tree search.
It runs a for loop for all seven columns and get min value for each. Then selects the biggest one
and returns that column. 

minValue: 
Calculates the utility value that would be returned if min were to play at current state.

maxValue:
Calculates the utility value that would be returned if max were to play at current state.

eval:
Calculates the desirability of the current state of the game for max player. 
Returns the score of current game for max player.
For more details, check eval_explanation.txt
