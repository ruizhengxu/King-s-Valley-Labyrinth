package games.kingsvalley;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;

public class KVGame extends AbstractGame<KVMove, KVRole, KVBoard> {

	KVGame(ArrayList<AIPlayer<KVMove, KVRole, KVBoard>> players, KVBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		MyChallenger I = new MyChallenger();
		
		System.out.println("teamName: " + I.teamName());
		
		I.setRole("BLUE");
		
		/*System.out.println("\n-----src/pinit.txt-----\n");
		I.setBoardFromFile("src/pinit.txt");
		System.out.println(I.getBoard());
		
		System.out.println("Move: A1-A6\n");
		I.iPlay("A1-A6");
		System.out.println(I.getBoard());
		
		I.iPlay("A1-A6");
		System.out.println(I.getBoard());
		
		System.out.println("\n****possibleMoves****\n");
		for (String temp : I.possibleMoves())
        	System.out.println(temp);*/
		
		System.out.println("\n-----src/p1.txt-----\n");
		I.setBoardFromFile("src/pinit.txt");
		System.out.println(I.getBoard());
	
		/*System.out.println("\n****possibleMoves****\n");
		for (String temp : I.possibleMoves())
        	System.out.println(temp);*/
		//System.out.println(I.getBoard());
		System.out.println("BLUE");
		
		System.out.println(I.bestMove());
		
		//System.out.println(I.victory());
		
		/*
		KVRole roleB = KVRole.BLUE;
		KVRole roleW = KVRole.WHITE;
		

		GameAlgorithm<KVMove, KVRole, KVBoard> algB = new MiniMax<KVMove, KVRole, KVBoard>(
				roleB, roleW, KVHeuristics.hBlue, 4); // Minimax depth 4

		GameAlgorithm<KVMove, KVRole, KVBoard> algW = new MiniMax<KVMove, KVRole, KVBoard>(
				roleW, roleB, KVHeuristics.hWhite, 2); // Minimax depth 2  */
		
		/*
		GameAlgorithm<KVMove, KVRole, KVBoard> algB = new MiniMax<KVMove, KVRole, KVBoard>(
				roleB, roleW, KVHeuristics.hBlue, 4); // Minimax depth 4

		GameAlgorithm<KVMove, KVRole, KVBoard> algW = new MiniMax<KVMove, KVRole, KVBoard>(
				roleW, roleB, KVHeuristics.hWhite, 2); // Minimax depth 2
		

		AIPlayer<KVMove, KVRole, KVBoard> playerB = new AIPlayer<KVMove, KVRole, KVBoard>(
				roleB, algB);

		AIPlayer<KVMove, KVRole, KVBoard> playerW = new AIPlayer<KVMove, KVRole, KVBoard>(
				roleW, algW);

		ArrayList<AIPlayer<KVMove, KVRole, KVBoard>> players = new ArrayList<AIPlayer<KVMove, KVRole, KVBoard>>();

		players.add(playerB); // First Player
		players.add(playerW); // Second Player
		

		KVGame game = new KVGame(players, I.getB());
		game.runGame();
		*/
	}

}