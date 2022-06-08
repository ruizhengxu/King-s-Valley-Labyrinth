package iialib.games.algs.algorithms;

import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;

public class AlphaBeta<Move extends IMove,Role extends IRole,Board extends IBoard<Move,Role,Board>> implements GameAlgorithm<Move,Role,Board> {

	// Constants
	private final static int DEPTH_MAX_DEFAULT = 4;

	// Attributes
	private final Role playerMaxRole;
	private final Role playerMinRole;
	private int depthMax = DEPTH_MAX_DEFAULT;
	private IHeuristic<Board, Role> h;
	private int nbNodes;
	private int nbLeaves;
	
	int alpha = IHeuristic.MIN_VALUE;
	int beta = IHeuristic.MAX_VALUE;
	
	//Constructors
	public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
		this.playerMaxRole = playerMaxRole;
		this.playerMinRole = playerMinRole;
		this.h = h;
	}

	//
	public AlphaBeta(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMaxRole, playerMinRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */

	@Override
	
	public Move bestMove(Board board, Role playerRole) {
		long startTime = System.currentTimeMillis(); 
		System.out.println("[AlphaBeta]");
		
		this.nbNodes = 1;
		this.nbLeaves = 0;
		int max = IHeuristic.MIN_VALUE;
		int newVal;
		
		ArrayList<Move> allMoves = board.possibleMoves(playerRole);	
	    System.out.println("    * " + allMoves.size() + " possible moves");
		Move bestMove = (allMoves.size() == 0 ? null : allMoves.get(0));
		
		/*int max = minMax(board.play(allMoves.get(0), playerRole), 1);*/
		for (Move move : allMoves) {
			//if > 60s retourner bestMove courant
			if((System.currentTimeMillis() - startTime) / 1000 >= 60 )
				return bestMove;
			System.out.println("    * " + move);
			newVal = minMax(board.play(move, playerMaxRole), 1);
			//System.out.println("Le coup " + move + " a pour valeur minimax " + newVal);
			if (newVal > max) {
				max = newVal;
				bestMove = move;
			}
		}
		
		System.out.println("    * " + nbNodes + " nodes explored");
		System.out.println("    * " + nbLeaves + " leaves evaluated");
		System.out.println("Best value is: " + max);
		return bestMove;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "AlphaBeta(ProfMax=" + depthMax + ")";
	}

	/*
	 * PRIVATE METHODS ===============
	 */

	//TODO
	private int maxMin(Board board, int depth) {
		if(board.isGameOver()||depthMax == depth) {
			nbLeaves++;
			
			return h.eval(board, playerMaxRole);
		}
		else {
			nbNodes++;
			
			int val;
			ArrayList<Move> allMoves = board.possibleMoves(playerMaxRole);
			
			for(Move m:allMoves) {
				val = minMax(board.play(m,playerMaxRole),depth+1);
				alpha = Math.max(alpha,val);//Evaluation la plus favorable
				if(alpha >= beta)	//Coupe beta
					return beta;
			}
			return alpha;
		}
	}
	
	private int minMax(Board board, int depth) {

		if(board.isGameOver()||depthMax == depth) {
			nbLeaves++;
			
			return h.eval(board, playerMinRole);
		}
		else {
			
			nbNodes++;
			for(Move m:board.possibleMoves(playerMinRole)) {
				
				beta = Math.min(beta,maxMin(board.play(m,playerMinRole),depth+1));
				if(alpha >= beta)	//Coupe alpha
					return alpha;
			}
		}
		return beta;
	}
	
	
	
	
}
