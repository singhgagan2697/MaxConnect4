package hw4;

import java.util.*;

/**
 * This is the AiPlayer class.  It simulates a minimax player for the max
 * connect four game.
 * 
 * @author Gaganjeet Singh
 *
 */

public class AiPlayer 
{

	private int maxDepth;
	private int maxPlayer;	//will be given value in minimaxDecision
    
	/**
     * Constructor takes in the depth level as an argument 
     * and sets its limited depth search limit to that depth
     *
     */
	
    public AiPlayer(int depth){
    	this.maxDepth = depth;
    }
   
    /**
     * 
     * @param currentGame
     * Only needs to know the current Game being played
     * Assumes that it is the computer's turn to play
     * @return integer - column to be played by the computer
     * the main function that is called. Will return the best move for max to make
     * when min is also using minimax. 
     */
    
    int minimaxDecision(GameBoard currentGame) {
    	
    	//setting the alpha value to negative infinity and beta to infinity
    	//in accordance to the alpha-beta pruning algorithm
    	int alpha = Integer.MIN_VALUE;
    	int beta = Integer.MAX_VALUE;
    	
    	//setting the maxValue to negative infinity
    	//will change to any higher value found
    	int maxValue = Integer.MIN_VALUE;
    	
    	//setting the maxPlayer as the player who turn it is
    	this.maxPlayer = currentGame.getCurrentTurn();
    	
    	//column choice. Initialized as 0, but will update when
    	//maxValue is updated
    	int playChoice = 0;
    	
    	//looks through all the columns and for each valid move
    	//gets the utility value from min successors. 
    	for(int i = 0; i < 7; i++) {
    		if(currentGame.isValidPlay(i)) {
    			
    			//initially playing the piece to the board
    			//will remove piece after playing one instance of min child
    			//using this approach to keep constant space complexity
    			currentGame.playPiece(i);
    			int minResult = minValue(currentGame, 1, alpha, beta);
    			
    			//updating maxValue and playChoice if the newly found result
    			//from minValue was better than previous choices
    			if(minResult > maxValue) {
    				maxValue = minResult;
    				playChoice = i;
    			}
    			currentGame.removePiece(i);
    		}
    	}
    	
    	//returns the best choice. Will return 0 if none found, although that will not happen
    	//because maxValue is negative infinity and the worst score for max is a 0
    	return playChoice;
    }
    
    
    /**
     * 
     * @param currentGame
     * current board being played
     * @param depth
     * current depth of the tree
     * @param alpha
     * @param beta
     * @return integer utility value. Will return the value from eval function
     * The min part of the minimax algorithm. Calculates the best move for the min (opponent) to make
     * will return the smallest possible score for max 
     */
    int minValue(GameBoard currentGame, int depth, int alpha, int beta) { 	
    	
    	//if we reach the depth limit or if the piece count has hit the max
    	//return the utility from eval
    	//second argument needed because piece count can hit 42 while running the tree
    	if(depth >= maxDepth || currentGame.getPieceCount() == 42) {
    		//returns the score for max player
    		return eval(currentGame);
    	}
    	
    	//setting the min value to infinity
    	//will be updated when a smaller value is found
    	int minValue = Integer.MAX_VALUE;
    	
    	//looks through all the columns and for each valid move
    	//gets the utility value from max successors. 
    	for(int i = 0; i < 7; i++) {
    		if(currentGame.isValidPlay(i)) {
    			
    			//initially playing the piece to the board
    			//will remove piece after playing one instance of min child
    			//using this approach to keep constant space complexity
    			currentGame.playPiece(i);
    			minValue = Math.min(minValue, maxValue(currentGame, depth+1, alpha, beta));
        		
    			//pruning the rest of the options when we find a value that is smaller than alpha
    			//pruned because min will choose the smaller value and max will then not choose this branch
    			if(minValue <= alpha) {
        			currentGame.removePiece(i);
        			return minValue;
        		}
    			
    			//updating beta if minValue is smaller
        		beta = Math.min(beta, minValue);
        		currentGame.removePiece(i);
    		}
    	}
    	return minValue;
    }
    
    /**
     * 
     * @param currentGame
     * current board
     * @param depth
     * current depth of the tree
     * @param alpha
     * @param beta
     * @return integer - utility value from max
     * The max part of the minimax algorithm. Calculates the best move for the max(computer) to make
     * will return the largest possible score for max
     */
    int maxValue(GameBoard currentGame, int depth, int alpha, int beta) {   
    	
    	//if we reach the depth limit or if the piece count has hit the max
    	//return the utility from eval
    	//second argument needed because piece count can hit 42 while running the tree
    	if(depth >= maxDepth || currentGame.getPieceCount() == 42) {
    		return eval(currentGame);
    	}
    	
    	//setting maxValue to negative infinity
    	//will update when a bigger value is found
    	int maxValue = Integer.MIN_VALUE;
    	
    	//looks through all the columns and for each valid move
    	//gets the utility value from min successors. 
    	for(int i = 0; i < 7; i++) {
    		if(currentGame.isValidPlay(i)) {
    			
    			//initially playing the piece to the board
    			//will remove piece after playing one instance of min child
    			//using this approach to keep constant space complexity
    			currentGame.playPiece(i);
    			maxValue = Math.max(maxValue, minValue(currentGame, depth+1, alpha, beta));
    			
    			//pruning the rest of the branches if max is bigger than beta
    			//max will choose the bigger value and then the min parent will not choose this branch
        		if(maxValue >= beta) {
        			currentGame.removePiece(i);
        			return maxValue;
        		}
        		currentGame.removePiece(i);
        		
        		//updating alpha if max is bigger
        		alpha = Math.max(alpha, maxValue);
    		}
    	}
    return maxValue;
    }
    
    /**
     * 
     * @param currentGame
     * current game board
     * @return integer utility value
     * utility is just the score of the current game
     * for the max player
     */
    int eval(GameBoard currentGame) {
    	return currentGame.getScore(maxPlayer);
    }
}