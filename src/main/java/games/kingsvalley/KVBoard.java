package games.kingsvalley;

import java.util.ArrayList;
import java.util.Iterator;

import games.dominos.DominosBoard;
import games.dominos.DominosRole;

import iialib.games.model.IBoard;
import iialib.games.model.Score;

public class KVBoard implements IBoard<KVMove, KVRole, KVBoard> {
	private static int DEFAULT_GRID_SIZE = 7;

	// --------- Class Attribute ---------

	public static int GRID_SIZE = DEFAULT_GRID_SIZE;
	private enum SQUARE {
		EMPTY, POIN_BLANC, ROI_BLANC, POIN_BLEU, ROI_BLEU;
	};
	

	// ---------------------- Attributes ---------------------

	private final SQUARE[][] boardGrid;
	private int[] heuristics;
	
	public KVBoard() {
		
		//Afin de se souvenir des facteurs heuristiques
		heuristics = new int[]{0,0,0,0};
		
		boardGrid = new SQUARE[GRID_SIZE][GRID_SIZE];
		//Pion et Roi dans la premiere range
		for (int j = 0; j < GRID_SIZE; j++) {
			if(j == 3)
				boardGrid[0][j] = SQUARE.ROI_BLEU;
			else
				boardGrid[0][j] = SQUARE.POIN_BLANC;
		}
		
		//Vide
		for (int i = 1; i < GRID_SIZE - 1; i++)
			for (int j = 0; j < GRID_SIZE; j++)
				boardGrid[i][j] = SQUARE.EMPTY;
		
		//Pion et Roi dans la derniere range
		for (int j = 0; j < GRID_SIZE; j++) {
			if(j == 3)
				boardGrid[GRID_SIZE - 1][j] = SQUARE.ROI_BLANC;
			else
				boardGrid[GRID_SIZE - 1][j] = SQUARE.POIN_BLEU;
		}
	}
	private KVBoard(SQUARE[][] other) {
		boardGrid = new SQUARE[GRID_SIZE][GRID_SIZE];
		for (int i = 0; i < GRID_SIZE; i++)
			System.arraycopy(other[i], 0, boardGrid[i], 0, GRID_SIZE);
	}

	@Override
	public ArrayList<KVMove> possibleMoves(KVRole playerRole) {
		// TODO Auto-generated method stub
		
		if (playerRole == KVRole.BLUE) {
			if(MyChallenger.TEMPSTOTAL == 480)
				return allMoves(SQUARE.POIN_BLEU, SQUARE.POIN_BLEU);
			else
				return allMoves(SQUARE.POIN_BLEU, SQUARE.ROI_BLEU);
		} else {
			if(MyChallenger.TEMPSTOTAL == 480)
				return allMoves(SQUARE.POIN_BLANC, SQUARE.POIN_BLANC);
			else
				return allMoves(SQUARE.POIN_BLANC, SQUARE.ROI_BLANC);
		}
		//return allMoves(playerRole);
	}

	@Override
	public KVBoard play(KVMove move, KVRole playerRole) {
		// TODO Auto-generated method stub
		
		SQUARE[][] newGrid = copyGrid();
		if(isValidMove(move, playerRole)) {
			String m = move.move;
			int xa, ya, xc, yc;
	
			//Convertit la chaîne en index de tableau correspondant
			xa = 7 - Integer.parseInt(m.substring(1, 2));
			ya = (m.substring(0, 1)).compareTo("A");
			xc = 7 - Integer.parseInt(m.substring(4, 5));
			yc = (m.substring(3, 4)).compareTo("A");
			
			newGrid[xc][yc] = newGrid[xa][ya];
			newGrid[xa][ya] = SQUARE.EMPTY;
			
			//isGameOver?
			if(isGameOver())
				System.out.println("******GameOver******\n");
				
			return new KVBoard(newGrid);
		}
		else {
			//Si le deplacement n'est pas valide, afficher l'invite
			System.out.println("***" + move.move + " Invalide***\n");
			return new KVBoard(newGrid);
		}
	}

	@Override
	public boolean isValidMove(KVMove move, KVRole playerRole) {
		// TODO Auto-generated method stub
		String m = move.move;
		int xa, ya, xc, yc;
		boolean valide = false;
		boolean valideRole = false;

		xa = 7 - Integer.parseInt(m.substring(1, 2));
		ya = (m.substring(0, 1)).compareTo("A");
		xc = 7 - Integer.parseInt(m.substring(4, 5));
		yc = (m.substring(3, 4)).compareTo("A");
		
		//Idees d'algorithmes
		/**
		 * 1. determiner s'il y a une piece d'echecs, est-ce que l'echec est correspond au playerRole?
		 * 2. Si oui, determiner le direction
		 * 		  Si xa=xc,
		 * 			 quand yc est minimal, on veut aller a la gauche;
		 *           quand yc est maximal, on veut aller au droit.
		 * 		  Si ya=yc,
		 * 			 quand xc est minimal, on veut aller en haut;
		 *           quand xc est maximal, on veut aller en bas.
		 * 		  Si tout <>,
		 * 			  Si la difference entre xa,xc et la difference entre ya,yc n'est pas egale, return false
		 * 			  Si non:
		 *                   xa > xc, ya > yc En haut a gauche
		 * 					 xa > xc, ya < yc En haut a droite
		 * 					 xa < xc, ya > yc En bas a gauche
		 * 					 xa < xc, ya < yc En bas a droite
		 * 3. Ensuite, jugez si la piece d'echecs va dans cette direction jusqu'a ce que 
		 * la grille avant la position cible soit vide et qu'il y ait une piece d'echecs 
		 * ou une limite derriere la position cible. 
		*/
		//System.out.println("    *1 " + m + (boardGrid[xa][ya] == SQUARE.EMPTY));
		//S'il n'y a pas de piece mobile sur la grille que vous souhaitez deplacer, return false
		if( boardGrid[xa][ya] == SQUARE.EMPTY 
				|| (xa == xc && ya == yc)
				|| (playerRole == KVRole.WHITE && (boardGrid[xa][ya] != SQUARE.POIN_BLANC && boardGrid[xa][ya] != SQUARE.ROI_BLANC))
				|| (playerRole == KVRole.BLUE && (boardGrid[xa][ya] != SQUARE.POIN_BLEU && boardGrid[xa][ya] != SQUARE.ROI_BLEU))
				//les soldats ne peuvent pas arreter au centre
				|| ((xc == 3 && yc == 3) && (boardGrid[xa][ya] == SQUARE.POIN_BLANC || boardGrid[xa][ya] == SQUARE.POIN_BLEU))
				){
			//System.out.println("    1 " + valide);
			return valide;
		}
		else {
			
			if (xa == xc) {
				
				//a la gauche
				if (yc < ya) {
					int y = ya - 1;
					valide = true;
					while (y != yc && valide) {
						valide = valide && boardGrid[xc][y] == SQUARE.EMPTY;
						y--;
					}
					//System.out.println("    2 " + valide + (yc == 0 || boardGrid[xc][yc - 1] != SQUARE.EMPTY));
					return valide && (yc == 0 || boardGrid[xc][yc - 1] != SQUARE.EMPTY);
				}
				
				//au droit
				else {
					int y = ya + 1;
					valide = true;
					while (y != yc && valide) {
						valide = valide && boardGrid[xc][y] == SQUARE.EMPTY;
						y++;
					}
					//System.out.println("    3 " + valide + (yc + 1 == GRID_SIZE  || boardGrid[xc][yc + 1] != SQUARE.EMPTY));
					return valide && (yc + 1 == GRID_SIZE  || boardGrid[xc][yc + 1] != SQUARE.EMPTY);
				}
			}
			
			else if (ya == yc) {
				
				//en haut
				if (xc < xa) {
					
					int x = xa - 1;
					valide = true;
					while (x != xc && valide) {
						valide = valide && boardGrid[x][yc] == SQUARE.EMPTY;
						x--;					
					}
					//System.out.println("    4 " + valide +  (xc == 0 || boardGrid[xc - 1][yc] != SQUARE.EMPTY));
					return valide && (xc == 0 || boardGrid[xc - 1][yc] != SQUARE.EMPTY);
				}
				
				//en bas
				else {
					int x = xa + 1;
					valide = true;
					while (x != xc && valide) {
						valide = valide && boardGrid[x][yc] == SQUARE.EMPTY;
						x++;
					}
					//System.out.println("    5 " + valide + (xc + 1 == GRID_SIZE || boardGrid[xc + 1][yc] != SQUARE.EMPTY));
					return valide && (xc + 1 == GRID_SIZE || boardGrid[xc + 1][yc] != SQUARE.EMPTY);
				}
			}
			
			else {
				if(Math.abs(xa - xc) != Math.abs(ya - yc))
					return valide;
				else {
					//En haut a gauche
					if (xc < xa && yc < ya) {
						int y = ya - 1;
						int x = xa - 1; 
						valide = true;
						while (x != xc && valide) {
							valide = valide && boardGrid[x][y] == SQUARE.EMPTY;
							x--;
							y--;
						}
						//System.out.println("    6 " + valide + (xc == 0 || yc == 0 || boardGrid[xc - 1][yc - 1] != SQUARE.EMPTY));
						return valide && (xc == 0 || yc == 0 || boardGrid[xc - 1][yc - 1] != SQUARE.EMPTY);
					}
					
					//En haut a droite
					if (xc < xa && yc > ya) {
						int y = ya + 1;
						int x = xa - 1; 
						valide = true;
						while (x != xc && valide) {
							valide = valide && boardGrid[x][y] == SQUARE.EMPTY;
							x--;
							y++;
						}	
						//System.out.println("(" + xc + "," + yc + ")");
						//System.out.println("    7 " + valide + (xc == 0 || yc + 1 == GRID_SIZE || boardGrid[xc - 1][yc + 1] != SQUARE.EMPTY));
						return valide && (xc == 0 || yc + 1 == GRID_SIZE || boardGrid[xc - 1][yc + 1] != SQUARE.EMPTY);
					}
					
					//En bas a gauche
					if (xc > xa && yc < ya) {
						int y = ya - 1;
						int x = xa + 1; 
						valide = true;
						while (x != xc && valide) {
							valide = valide && boardGrid[x][y] == SQUARE.EMPTY;
							x++;
							y--;
						}
						//System.out.println("    8 " + valide + (xc + 1 == GRID_SIZE || yc == 0 || boardGrid[xc + 1][yc - 1] != SQUARE.EMPTY));
						return valide && (xc + 1 == GRID_SIZE || yc == 0 || boardGrid[xc + 1][yc - 1] != SQUARE.EMPTY);
					}
					
					//En bas a droite
					if (xc > xa && yc > ya) {
						
						int y = ya + 1;
						int x = xa + 1;
						valide = true;
						while (x != xc && valide) {
							valide = valide && boardGrid[x][y] == SQUARE.EMPTY;
							x++;
							y++;
						}
						//System.out.println("    9 " + valide + (xc + 1 == GRID_SIZE || yc + 1 == GRID_SIZE || boardGrid[xc + 1][yc + 1] != SQUARE.EMPTY));
						return valide && (xc + 1 == GRID_SIZE || yc + 1 == GRID_SIZE || boardGrid[xc + 1][yc + 1] != SQUARE.EMPTY);
					}	
				}	
			}
		}
		//System.out.println("(" + m + valide + ")");
		return valide;
	}

	@Override
	public boolean isGameOver() {
		// TODO Auto-generated method stub

		/**
		 *  a. Si le temps total du jeu est ecoule, le jeu est termine
 		 *	b. Le jeu se termine lorsque la piece d'echecs roi du joueur n'a aucun moyen d'aller
 		 *  c. Le jeu se termine lorsque la piece d'echecs roi du joueur atteint un point central
		 * */
		
		return (boardGrid[3][3] == SQUARE.ROI_BLANC || boardGrid[3][3] == SQUARE.ROI_BLEU) 
				|| (IsROIBlock(KVRole.BLUE) == 8 || IsROIBlock(KVRole.WHITE) == 8
				|| MyChallenger.TEMPSTOTAL <= 0);
		
	}

	@Override
	public ArrayList<Score<KVRole>> getScores() {
		ArrayList<Score<KVRole>> scores = new ArrayList<Score<KVRole>>();
		if(this.isGameOver()) {
			/*
			if (boardGrid[3][3] == SQUARE.ROI_BLEU || IsROIBlock(KVRole.WHITE)) {*/
			if (boardGrid[3][3] == SQUARE.ROI_BLEU || IsROIBlock(KVRole.WHITE) == 8) {
				scores.add(new Score<KVRole>(KVRole.WHITE,Score.Status.LOOSE,0));
				scores.add(new Score<KVRole>(KVRole.BLUE,Score.Status.WIN,1));
			}
			else {
				scores.add(new Score<KVRole>(KVRole.BLUE,Score.Status.WIN,1));
				scores.add(new Score<KVRole>(KVRole.WHITE,Score.Status.LOOSE,0));
			}			
		}
		else {
			
		}
		return scores;
	}
	
	/** ********************************************************************************************************* */
	
	private SQUARE[][] copyGrid() {
		SQUARE[][] newGrid = new SQUARE[GRID_SIZE][GRID_SIZE];
		for (int i = 0; i < GRID_SIZE; i++)
			System.arraycopy(boardGrid[i], 0, newGrid[i], 0, GRID_SIZE);
		return newGrid;
	}
	
	
	//Pour verifier si le roi est bloque
		public int IsROIBlock(KVRole role) {
			//combien de directions sont bloques
			int cpt = 0;
			
			SQUARE amiR, amiP, enemyR, enemyP;
			if(role == KVRole.WHITE) {
				enemyR = SQUARE.ROI_BLEU;
				enemyP = SQUARE.POIN_BLEU;
				amiR = SQUARE.ROI_BLANC;
				amiP = SQUARE.POIN_BLANC;
			}else {
				enemyR = SQUARE.ROI_BLANC;
				enemyP = SQUARE.POIN_BLANC;
				amiR = SQUARE.ROI_BLEU;
				amiP = SQUARE.POIN_BLEU;
			}
			
			for (int x = 0; x < GRID_SIZE; x++) {
				for (int y = 0; y < GRID_SIZE; y++) {
					if(boardGrid[x][y] == amiR) {
						//up
						if(x - 1 < 0 || boardGrid[x - 1][y] == enemyP || boardGrid[x - 1][y] == enemyR)
							cpt ++;
						//down
						if(x + 1 == GRID_SIZE || boardGrid[x + 1][y] == enemyP || boardGrid[x + 1][y] == enemyR)
							cpt ++;
						//left
						if(y - 1 < 0 || boardGrid[x][y - 1] == enemyP || boardGrid[x][y - 1] == enemyR)
							cpt ++;
						//right
						if(y + 1 == GRID_SIZE || boardGrid[x][y + 1] == enemyP || boardGrid[x][y + 1] == enemyR)
							cpt ++;
						//UL
						if((x - 1 < 0 || y - 1 < 0) || boardGrid[x - 1][y - 1] == enemyP || boardGrid[x - 1][y - 1] == enemyR)
							cpt ++;
						//UR
						if((x - 1 < 0 || y + 1 == GRID_SIZE) || boardGrid[x - 1][y + 1] == enemyP || boardGrid[x - 1][y + 1] == enemyR)
							cpt ++;
						//DL
						if((x + 1 == GRID_SIZE || y - 1 < 0) || boardGrid[x + 1][y - 1] == enemyP || boardGrid[x + 1][y - 1] == enemyR)
							cpt ++;
						//DR
						if((x + 1 == GRID_SIZE || y + 1 == GRID_SIZE) || boardGrid[x + 1][y + 1] == enemyP || boardGrid[x + 1][y + 1] == enemyR)
							cpt ++;
					}
				}
			}
				
			return cpt;
					
			
		}
	
	
	//public ArrayList<KVMove> allMoves(KVRole role) {
	public ArrayList<KVMove> allMoves(SQUARE P, SQUARE RP) {
		ArrayList<KVMove> allPossibleMoves = new ArrayList<>();
		String move;
		//Initialiser l'heuristics
		this.heuristics = new int[]{0,0,0,0};
		SQUARE amiR; 
		SQUARE amiP;
		SQUARE enemyR; 
		SQUARE enemyP;
		
		//if(role == KVRole.WHITE) {
		if(P == SQUARE.POIN_BLANC) {
			enemyR = SQUARE.ROI_BLEU;
			enemyP = SQUARE.POIN_BLEU;
			amiR = SQUARE.ROI_BLANC;
			amiP = SQUARE.POIN_BLANC;
		}else {
			enemyR = SQUARE.ROI_BLANC;
			enemyP = SQUARE.POIN_BLANC;
			amiR = SQUARE.ROI_BLEU;
			amiP = SQUARE.POIN_BLEU;
		}
		
		for (int i = 0; i < GRID_SIZE; i++) { 			// lines
			for (int j = 0; j < GRID_SIZE ; j++) { 	// columns
				if ((boardGrid[i][j] == P) || (boardGrid[i][j] == RP)) { //Trouvez le pion du cote designe
				//if (flag) {
					//Pour verifier si le POIN est pres du centre
					if(boardGrid[i][j] == amiP
							&&i >= GRID_SIZE / 2 - 1 && i <= GRID_SIZE / 2 + 1
							&& j >= GRID_SIZE / 2 - 1 && j <= GRID_SIZE / 2 + 1
							&& !(i == GRID_SIZE / 2 && i == GRID_SIZE / 2)) {
							this.heuristics[0] ++;
					}
					
					//Si la piece est sur le bord superieur ou si le carre au-dessus de la piece n'est pas vide, alors il ne peut pas monter.
					boolean up = !(i == 0 || boardGrid[i - 1][j] != SQUARE.EMPTY);
					//SSi la piece est sur le bord inferieur ou si le carre en dessous de la piece n'est pas vide, alors il ne peut pas descendre.
					boolean down = !(i == GRID_SIZE - 1 || boardGrid[i + 1][j] != SQUARE.EMPTY);
					//Ce qui suit est similaire a up et down
					boolean left = !(j == 0 || boardGrid[i][j - 1] != SQUARE.EMPTY);
					boolean right = !(j == GRID_SIZE - 1 || boardGrid[i][j + 1] != SQUARE.EMPTY);
					
					boolean upleft = !(i == 0 || j == 0 || boardGrid[i - 1][j - 1] != SQUARE.EMPTY) ;
					boolean upright = !(i == 0 || j == GRID_SIZE - 1 || boardGrid[i - 1][j + 1] != SQUARE.EMPTY);
					boolean downleft = !(i == GRID_SIZE - 1 || j == 0 || boardGrid[i + 1][j - 1] != SQUARE.EMPTY);
					boolean downright = !(i == GRID_SIZE - 1 || j == GRID_SIZE - 1 || boardGrid[i + 1][j + 1] != SQUARE.EMPTY);
					
					//Developpez a partir de la place ou se trouve la piece d'echecs, 
					//en recherchant le point le plus eloigne que vous pouvez aller dans la direction correspondante
					for (int a = 1; a < GRID_SIZE ; a++) {
					
						/**
						 * 1.Determiner si l'index est hors limites
						 * 2.s'il est encore possible d'aller dans cette direction
						 * 3.Si la grille en cours d'extension pour le moment est vide 
						 *   et que la grille suivante n'est pas vide ou est un board, 
						 * 4.ajoutez le deplacement a cette grille a allPossibleMoves.*/
						if(i - a >= 0 && up && boardGrid[i - a][j] == SQUARE.EMPTY
								&& (i - a == 0 || boardGrid[i - a - 1][j] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)(j + 65) + (7 - (i - a)); 
							up = false;
							
							//le roi est move
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//la possibilite de roi est bloque
								if(i - a == 0 || boardGrid[i - a - 1][j] == enemyP || boardGrid[i - a - 1][j] == enemyR)
									this.heuristics[2] ++;
								//PnbROIBlock(i - a - 1, j, enemyP, enemyR);
								
								//move vers centre utile
								if(i - a == 3 && j == 3)
									this.heuristics[1] ++;
							}
							//soldtat est move
							else {
								//move vers centre pas utile
								if(!(i - a == 3 && j == 3)) {
									allPossibleMoves.add(new KVMove(move));
									PnbPOINBaroundC(i - a, j);
								}
							}
						}
							
							
						if(j - a >= 0 
								&& upleft 
								&& boardGrid[i - a][j - a] == SQUARE.EMPTY 
								&& ((i - a == 0 || j - a == 0) || boardGrid[i - a - 1][j - a - 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j - a) + 65) + (7 - (i - a)); 
							upleft = false;
							//allPossibleMoves.add(new KVMove(move));
							
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								
								//PnbROIBlock(i - a - 1, j - a - 1, enemyP, enemyR);
								if(i - a == 0 || j - a == 0 || boardGrid[i - a - 1][j - a - 1] == enemyP || boardGrid[i - a - 1][j - a - 1] == enemyR)
									this.heuristics[2] ++;
								
								if(i - a == 3 && j - a == 3)
									this.heuristics[1] ++;
							}
							else {
								if(!(i - a == 3 && j - a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									PnbPOINBaroundC(i - a, j - a);
								}
							}
						}
						
						if(j + a <= GRID_SIZE - 1 
								&& upright 
								&& boardGrid[i - a][j + a] == SQUARE.EMPTY 
								&& ((i - a == 0 || j + a == GRID_SIZE - 1) || boardGrid[i - a - 1][j + a + 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j + a) + 65) + (7 - (i - a)); 
							upright = false;
							//allPossibleMoves.add(new KVMove(move));
							
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//PnbROIBlock(i - a - 1, j + a + 1, enemyP, enemyR);
								if(i - a == 0 || j + a == GRID_SIZE - 1 || boardGrid[i - a - 1][j + a + 1] == enemyP || boardGrid[i - a - 1][j + a + 1] == enemyR)
									this.heuristics[2] ++;
								if(i - a == 3 && j + a == 3)
									this.heuristics[1] ++;
							}
							else {
								if(!(i - a == 3 && j + a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									//possibilite des nos soldats pres du centre
									PnbPOINBaroundC(i - a, j + a);
								}
							}
						}
						
						if(i + a <= GRID_SIZE - 1 
								&& down 
								&& boardGrid[i + a][j] == SQUARE.EMPTY 
								&& (i + a == GRID_SIZE - 1 || boardGrid[i + a + 1][j] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)(j + 65) + (7 - (i + a)); 
							down = false;
							//allPossibleMoves.add(new KVMove(move));
							
							//le roi est move
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//possibilite de roi est bloque
								//PnbROIBlock(i + a + 1, j, enemyP, enemyR);
								if(i + a == GRID_SIZE - 1 || boardGrid[i + a + 1][j] == enemyP || boardGrid[i + a + 1][j] == enemyR)
									this.heuristics[2] ++;
								//move vers centre utile
								if(i + a == 3 && j == 3)
									this.heuristics[1] ++;
							}
							//les soldats move
							else {
								//move vers centre pas utile
								if(!(i + a == 3 && j == 3)) {
									allPossibleMoves.add(new KVMove(move));
									//possibilite des nos soldats pres du centre
									PnbPOINBaroundC(i + a, j);
								}
							}
						}
						
						if(j - a >= 0 
								&& downleft 
								&& boardGrid[i + a][j - a] == SQUARE.EMPTY 
								&& ((i + a == GRID_SIZE - 1 || j - a == 0) || boardGrid[i + a + 1][j - a - 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j - a) + 65) + (7 - (i + a)); 
							downleft = false;
							//allPossibleMoves.add(new KVMove(move));
							
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//PnbROIBlock(i + a + 1, j - a - 1, enemyP, enemyR);
								if(i + a == GRID_SIZE - 1 || j - a == 0 || boardGrid[i + a + 1][j - a - 1] == enemyP || boardGrid[i + a + 1][j - a - 1] == enemyR)
									this.heuristics[2] ++;
								if(i + a == 3 && j - a == 3)
									this.heuristics[1] ++;
							}
							else {
								if(!(i + a == 3 && j - a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									PnbPOINBaroundC(i + a, j - a);
								}
							}
						}
						
						if(j + a <= GRID_SIZE - 1 
								&& downright 
								&& boardGrid[i + a][j + a] == SQUARE.EMPTY 
								&& ((i + a == GRID_SIZE - 1 || j + a == GRID_SIZE - 1) || boardGrid[i + a + 1][j + a + 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j + a) + 65) + (7 - (i + a)); 
							downright = false;
							//allPossibleMoves.add(new KVMove(move));
							
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//PnbROIBlock(i + a + 1, j + a + 1, enemyP, enemyR);
								if(i + a == GRID_SIZE - 1 || j + a == GRID_SIZE - 1 || boardGrid[i + a + 1][j + a + 1] == enemyP || boardGrid[i + a + 1][j + a + 1] == enemyR)
									this.heuristics[2] ++;
								if(i + a == 3 && j + a == 3)
									this.heuristics[1] ++;
							}
							else {
								if(!(i + a == 3 && j + a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									PnbPOINBaroundC(i + a, j + a);
								}
							}
						}
						
						if(j - a >= 0 
								&& left 
								&& boardGrid[i][j - a] == SQUARE.EMPTY 
								&& (j - a == 0 || boardGrid[i][j - a - 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j - a) + 65) + (7 - i); 
							left = false;
							//allPossibleMoves.add(new KVMove(move));
							
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//PnbROIBlock(i, j - a - 1, enemyP, enemyR);
								if( j - a == 0 || boardGrid[i][j - a - 1] == enemyP || boardGrid[i][j - a - 1] == enemyR)
									this.heuristics[2] ++;
								if(i == 3 && j - a == 3)
									this.heuristics[1] ++;
							}
							else {
								if(!(i == 3 && j - a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									PnbPOINBaroundC(i, j - a);
								}
							}
						}
						
						if(j + a <= GRID_SIZE - 1 
								&& right 
								&& boardGrid[i][j + a] == SQUARE.EMPTY 
								&& (j + a == GRID_SIZE - 1 || boardGrid[i][j + a + 1] != SQUARE.EMPTY)) {
							move = "" + (char)(j + 65) + (7 - i) + "-" + (char)((j + a) + 65) + (7 - i); 
							right = false;
							//allPossibleMoves.add(new KVMove(move));
							
							//le roi est move
							if(boardGrid[i][j] == amiR) {
								allPossibleMoves.add(new KVMove(move));
								//la possibilite de roi est bloque
								//PnbROIBlock(i, j + a + 1, enemyP, enemyR);
								if( j + a == GRID_SIZE - 1 || boardGrid[i][j + a + 1] == enemyP || boardGrid[i][j + a + 1] == enemyR)
									this.heuristics[2] ++;
								//move vers centre utile
								if(i == 3 && j + a == 3)
									this.heuristics[1] ++;
							}
							//les soldats move
							else {
								//move vers centre pas utile
								if(!(i == 3 && j + a == 3)) {
									allPossibleMoves.add(new KVMove(move));
									//possibilites de nos soldats pres du centre
									PnbPOINBaroundC(i, j + a);
								}
							}
						}	
					}
				}
			}
		}
		return allPossibleMoves;
	}
	
	
	//La possibilite de roi est bloque
	public void PnbROIBlock(int i, int j, int x, int y, SQUARE enemyP, SQUARE enemyR) {
		if(boardGrid[x][y] == enemyP || boardGrid[x][y] == enemyR)
			this.heuristics[2] ++;
	}
	
	//La possibilite de nos soldats d'entrer pres du centre
	public void PnbPOINBaroundC(int x, int y) {
		if(x >= GRID_SIZE / 2 - 1 && x <= GRID_SIZE / 2 + 1
				&& y >= GRID_SIZE / 2 - 1 && y <= GRID_SIZE / 2 + 1
				&& !(x == GRID_SIZE / 2 && y == GRID_SIZE / 2)) 
			this.heuristics[3] ++;
	}
	
	
	
	public String toString() {
		//Bordure superieure
		StringBuilder retstr = new StringBuilder(new String("    A B C D E F G    \n  .................   \n"));
		
		for(int i = 0; i < KVBoard.GRID_SIZE; i++) {
			//Bordure gauche
			retstr.append((KVBoard.GRID_SIZE-i) + " : ");
			
			//Corps principal de la planche
			for(int j = 0; j < KVBoard.GRID_SIZE; j++) {
				if (i == 3 && j == 3 && boardGrid[i][j] == SQUARE.EMPTY)
					retstr.append("+ ");
				else if (boardGrid[i][j] == SQUARE.EMPTY)
					retstr.append("- ");
				else if (boardGrid[i][j] == SQUARE.POIN_BLANC) 
					retstr.append("o ");
				else if (boardGrid[i][j] == SQUARE.ROI_BLANC) 
					retstr.append("O ");
				else if (boardGrid[i][j] == SQUARE.POIN_BLEU) 
					retstr.append("x ");
				else if (boardGrid[i][j] == SQUARE.ROI_BLEU) 
					retstr.append("X ");
			} 
			//Bordure droite
			retstr.append( ": " + (KVBoard.GRID_SIZE-i) + "\n");
		}
		
		//Bordure inferieure
		retstr.append( "  .................   \n    A B C D E F G    \n");
		return retstr.toString();
	}
	
	
	public void setBoard(String strBosrd) {
		//System.out.println(strBosrd);
		for(int i = 0; i < GRID_SIZE; i++) {
			for(int j = 0; j < GRID_SIZE; j++) {
				switch (strBosrd.substring(i*GRID_SIZE + j, i*GRID_SIZE + (j+1))) {
				case "-":
					boardGrid[i][j] = SQUARE.EMPTY;
					break;
				case "+":
					boardGrid[i][j] = SQUARE.EMPTY;
					break;
				case "o":
					boardGrid[i][j] = SQUARE.POIN_BLANC;
					break;
				case "O":
					boardGrid[i][j] = SQUARE.ROI_BLANC;
					break;
				case "x":
					boardGrid[i][j] = SQUARE.POIN_BLEU;
					break;
				case "X":
					boardGrid[i][j] = SQUARE.ROI_BLEU;
					break;
				}
			}
		}
	}
	
	//retourne si c'est notre roi est dans le centre
	public boolean IsROICenter(KVRole role){
		if(role == KVRole.WHITE)
			return boardGrid[3][3] == SQUARE.ROI_BLANC;
		else
			return boardGrid[3][3] == SQUARE.ROI_BLEU;
	}
	
	//nombre de nos soldats qui sont pres du centre
	/*public int nbPOINaroundC(KVRole role) {
		int nb = 0;
		int gridAroundC = GRID_SIZE / 2 - 1;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(i != 1 || j != 1)
					if(boardGrid[gridAroundC + i][gridAroundC + j] == SQUARE.POIN_BLANC)
						nb ++;
					else if (boardGrid[gridAroundC + i][gridAroundC + j] == SQUARE.POIN_BLEU)
						nb --;
			}
		}
		if(role == KVRole.WHITE)
			return nb;
		else
			return -nb;
	}*/

	public int[] getHeuristics() {
		return heuristics;
	}
	
	
}