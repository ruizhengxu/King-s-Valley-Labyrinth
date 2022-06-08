package games.kingsvalley;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.AlphaBeta;
import iialib.games.algs.algorithms.MiniMax;
import iialib.games.model.IChallenger;

public class MyChallenger implements IChallenger {

	private KVRole iRole;
	private KVRole otherRole;
	private KVBoard board = new KVBoard();
	private String move;
	
	/*
	public static long DEBUT = System.currentTimeMillis();
	public static long TEMPS_LIMITE_TOTAL = 3; */
	
	//public long DEBUT = System.currentTimeMillis();
	public static long TEMPSTOTAL = 480; 
	
	@Override
	public String teamName() {
		// TODO Auto-generated method stub
		//return null;
		return "HUANG_ZHOU";
	}

	@Override
	public void setRole(String role) {
		// TODO Auto-generated method stub
		if(role == "BLUE") {
			this.iRole = KVRole.BLUE;
			this.otherRole = KVRole.WHITE;
		}
		else{
			this.iRole = KVRole.WHITE;
			this.otherRole = KVRole.BLUE;
		}
	}

	@Override
	public void iPlay(String move) {
		// TODO Auto-generated method stub
		this.board = board.play(new KVMove(move), iRole);
	}

	@Override
	public void otherPlay(String move) {
		// TODO Auto-generated method stub
		this.board = board.play(new KVMove(move), otherRole);
		
	}

	/*
	@Override
	public String bestMove() {
		// TODO Auto-generated method stub
		
		return null ;
	}*/
	
	@Override
	public String bestMove() {
		long startTime = System.currentTimeMillis(); 
		// TODO Auto-generated method stub
		GameAlgorithm<KVMove, KVRole, KVBoard> algI;
		
		if(iRole == KVRole.BLUE)
			algI = new AlphaBeta<KVMove, KVRole, KVBoard>(iRole, otherRole, KVHeuristics.hBlue, 2);
		else
			algI = new AlphaBeta<KVMove, KVRole, KVBoard>(iRole, otherRole, KVHeuristics.hWhite, 2);
		
		/*
		if(iRole == KVRole.BLUE)
			algI = new MiniMax<KVMove, KVRole, KVBoard>(iRole, otherRole, KVHeuristics.hBlue, 2);
		else
			algI = new MiniMax<KVMove, KVRole, KVBoard>(iRole, otherRole, KVHeuristics.hWhite, 2);
		*/
		
		long endTime = System.currentTimeMillis(); 
		this.TEMPSTOTAL -= (endTime - startTime) / 1000;
		return algI.bestMove(board, iRole).toString();
	}
	
	
	

	@Override
	public String victory() {
		// TODO Auto-generated method stub
		return "Vous gagnez!";
		//return null;
	}

	@Override
	public String defeat() {
		// TODO Auto-generated method stub
		return "Vous avez perdu!";
		//return null;
	}

	@Override
	public String tie() {
		// TODO Auto-generated method stub
		return "Tie!";
		//return null;
	}

	@Override
	public String getBoard() {
		// TODO Auto-generated method stub
		return this.board.toString();
	}
	
	//≤‚ ‘”√
	public KVBoard getB() {
		// TODO Auto-generated method stub
		
		return this.board;
	}

	@Override
	public void setBoardFromFile(String fileName) {
		// TODO Auto-generated method stub
		
		try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            /*String str;
            while ((str = in.readLine()) != null) {
                
            }
            System.out.println(str);
			in.close()*/
			int poin = 0;  
			String strBoard = "";
			while((poin = in.read())!=-1 ){   
			     //System.out.println((char)ch);   
				if((char)poin == 'o' || (char)poin == 'O' || (char)poin == 'x' || (char)poin == 'X' || (char)poin == '+' || (char)poin == '-')
					strBoard = strBoard + (char)poin;
			  } 
			//System.out.println(strBoard);
			this.board = new KVBoard();
			board.setBoard(strBoard);
			//System.out.println(getBoard());
        } catch (IOException e) {
			e.getStackTrace();
        }
		
	}

	@Override
	public Set<String> possibleMoves() {
		// TODO Auto-generated method stub
		
		Set<String> possibleMoves = new HashSet<String>();
		
		for (KVMove tmp : board.possibleMoves(this.iRole)){
        	possibleMoves.add(tmp.move);
        }
		
		return possibleMoves;
	}

}
