package games.kingsvalley;

import java.util.ArrayList;

import games.kingsvalley.KVBoard;
import games.kingsvalley.KVRole;
import iialib.games.algs.IHeuristic;

public class KVHeuristics {
	/**
	 * Heuristique du point de vue du joueur vertical.
	 * @param board le plateau a evaluer
	 * @param role le joueur qui a le trait
	 * @return l'evaluation heuristique de (board,role)
	 */
	
	public static IHeuristic<KVBoard,KVRole>  hBlue = (board,role) -> {
		
		//si le joueur dont on adopte le point de vue (AMI) a perdu -> - inf
        int[] tabhB = new int[4], tabhW = new int[4], tabh;
        int h = 0;
        ArrayList<KVMove> allPossibleMovesB = new ArrayList<>();
        ArrayList<KVMove> allPossibleMovesW = new ArrayList<>();
        
        allPossibleMovesB = board.possibleMoves(KVRole.BLUE);
        tabh = board.getHeuristics();
        //System.out.println(tabh.length);
        for(int i = 0; i < tabh.length; i++)
        	tabhB[i] = tabh[i];
        tabhB[2] += board.IsROIBlock(KVRole.BLUE);
        
        allPossibleMovesW = board.possibleMoves(KVRole.WHITE);
        tabh = board.getHeuristics();
        for(int i = 0; i < tabh.length; i++)
        	tabhW[i] = tabh[i];
        tabhW[2] += board.IsROIBlock(KVRole.WHITE);
        
        String b = "",w = "";
        for(int i = 0; i<4; i++) {
        	b += tabhB[i];
        	w += tabhW[i];
        }
        System.out.println(b);
    	System.out.println(w);
        
        
        //Si notre roi(roi bleu) est bloque ou le roi ennemi est dans le centre
    	//L'ennmi gagner du jeu, donc l'heuristique = infini negatif
        if (board.IsROIBlock(KVRole.BLUE) == 8 || board.IsROICenter(KVRole.WHITE)) {
        	return IHeuristic.MIN_VALUE;
        }
        
        //Si notre roi(roi bleu) est dans le centre ou le roi ennemi est bloque
    	//On gagne du jeu, donc l'heuristique = infini positif
        else if (board.IsROIBlock(KVRole.WHITE) == 8 || board.IsROICenter(KVRole.BLUE)) {
        	return IHeuristic.MAX_VALUE;
        }
        else {
        	
        	//  Au debut du jeu, on choisi donner la priorite a etablir la voie 
        	//permettant le roi d'entre au centre par les soldats
        	
        	if(MyChallenger.TEMPSTOTAL > 460) {
        	//Si notre roi a la possibilite d'entrer au centre, on le choisi
    		h += tabhB[1] * 1000;
    		
	    	//Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
	    	h -= tabhW[1] * 1000;
	    	
	    	//Si notre roi peut-etre bloque, ne choisi pas
	    	if(tabhB[2] == 7) {
	    		h -= tabhB[2] * 1000;
	    	}else {
	    		//notre roi est bloque
	        	h -= tabhB[2] * 10;
			}
	    	//Si on peut bloquer le roi ennemi, on le choisi
	    	if(tabhW[2] == 7) {
	    		h += tabhW[2] * 1000;
	    	}else {
	    		//le roi ennemi est bloque
	        	h += tabhW[2] * 10;
			}
	    	//nos soldats sont pres du centre
	    	h += tabhB[0] * 100;
	    	
	    	//soldats sont aussi pres du centre, mais pas tres critique
	    	//car on peut change rien dans ce coup(c'est pas notre coup)
	    	
	    	//les autres strategies
	    	//la possibilite de nos pions d'entrer pres du centre
	    	h += tabhB[3] * 100;
	    	
	    	//la possibilite de soldats ennemis d'entrer pres du centre
	    	h -= tabhW[3];
        	}
        	//en fin du jeu, on donne la priorite de bloquer le roi ennemi
        	else if (MyChallenger.TEMPSTOTAL < 20) {
    	    	//Si notre roi a la possibilite d'entrer au centre, on le choisi
        		h += tabhB[1] * 1000;
        		
    	    	//Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
    	    	h -= tabhW[1] * 1500;
    	    	
    	    	//Si notre roi peut-etre bloque, ne choisi pas
    	    	if(tabhB[2] == 7) {
    	    		h -= tabhB[2] * 1000;
    	    	}else {
    	    		//notre roi est bloque
    	        	h -= tabhB[2] * 10;
    			}
    	    	//Si on peut bloquer le roi ennemi, on le choisi
    	    	if(tabhW[2] == 7) {
    	    		h += tabhW[2] * 1500;
    	    	}else {
    	    		//le roi ennemi est bloque
    	        	h += tabhW[2] * 100;
    			}
    	    	//nos soldats sont pres du centre
    	    	h += tabhB[0] * 100;
    	    	
    	    	//soldats sont aussi pres du centre, mais pas tres critique
    	    	//car on peut change rien dans ce coup(c'est pas notre coup)
    	    	
    	    	//les autres strategies
    	    	//la possibilite de nos pions d'entrer pres du centre
    	    	h += tabhB[3] * 100;
    	    	
    	    	//la possibilite de soldats ennemis d'entrer pres du centre
    	    	h -= tabhW[3];
			}else {

	        	//Si notre roi a la possibilite d'entrer au centre, on le choisi
	    		h += tabhB[1] * 1000;
	    		
		    	//Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
		    	h -= tabhW[1] * 1000;
		    	
		    	//Si notre roi peut-etre bloque, ne choisi pas
		    	if(tabhB[2] == 7) {
		    		h -= tabhB[2] * 1000;
		    	}else {
		    		//notre roi est bloque
		        	h -= tabhB[2] * 10;
				}
		    	//Si on peut bloquer le roi ennemi, on le choisi
		    	if(tabhW[2] == 7) {
		    		h += tabhW[2] * 1000;
		    	}else {
		    		//le roi ennemi est bloque
		        	h += tabhW[2] * 10;
				}
		    	//nos soldats sont pres du centre
		    	h += tabhB[0] * 100;
		    	
		    	//soldats sont aussi pres du centre, mais pas tres critique
		    	//car on peut change rien dans ce coup(c'est pas notre coup)
		    	
		    	//les autres strategies
		    	//la possibilite de nos pions d'entrer pres du centre
		    	h += tabhB[3] * 10;
		    	
		    	//la possibilite de soldats ennemis d'entrer pres du centre
		    	h -= tabhW[3];
			  }     	
        }
        //System.out.println(h);
		return h;
        //return allPossibleMovesB.size() - allPossibleMovesW.size();
    };
    
    /**
	 * Heuristique du point de vue du joueur horizontal.
	 * @param board le plateau a evaluer
	 * @param role le joueur qui a le trait
	 * @return l'evaluation heuristique de (board,role)
	 */
	public static IHeuristic<KVBoard,KVRole> hWhite = (board,role) -> {
		//si le joueur dont on adopte le point de vue (AMI) a perdu -> - inf
		
        int[] tabhB = new int[4], tabhW = new int[4], tabh;
        int hB, hW, h=0;
        ArrayList<KVMove> allPossibleMovesB = new ArrayList<>();
        ArrayList<KVMove> allPossibleMovesW = new ArrayList<>();
        
        allPossibleMovesB = board.possibleMoves(KVRole.BLUE);
        tabh = board.getHeuristics();
        for(int i = 0; i < tabh.length; i++)
        	tabhB[i] = tabh[i];
        tabhB[2] += board.IsROIBlock(KVRole.BLUE);
        
        allPossibleMovesW = board.possibleMoves(KVRole.WHITE);
        tabh = board.getHeuristics();
        for(int i = 0; i < tabh.length; i++)
        	tabhW[i] = tabh[i];
        tabhW[2] += board.IsROIBlock(KVRole.WHITE);
        
        
        /*String b = "",w = "";
        for(int i = 0; i<4; i++) {
        	b += tabhB[i];
        	w += tabhW[i];
        }
        //System.out.println(b);
    	System.out.println(w);*/
        
        //Si roi enemi(roi bleu) est bloque ou notre roi est dans le centre
        //On gagne du jeu, donc l'heuristique = infini positif
        if (board.IsROIBlock(KVRole.BLUE) == 8 || board.IsROICenter(KVRole.WHITE)) {
        	return IHeuristic.MAX_VALUE;
        }
        
        
        //Si notre roi est bloque ou le roi ennemi est dans le centre
        //L'ennemi gagner du jeu, donc l'heuristique = infini negatif
        else if (board.IsROIBlock(KVRole.WHITE) == 8 || board.IsROICenter(KVRole.BLUE)) {
        	return IHeuristic.MIN_VALUE;
        }
        else {
        	
        	if(MyChallenger.TEMPSTOTAL > 460) {
            	//Si notre roi a la possibilite d'entrer au centre, on le choisi
        		h += tabhW[1] * 1000;
        		
    	    	//Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
    	    	h -= tabhB[1] * 1000;
    	    	
    	    	//Si notre roi peut-etre bloque, ne choisi pas
    	    	if(tabhW[2] == 7) {
    	    		h -= tabhW[2] * 1000;
    	    	}else {
    	    		//notre roi est bloque
    	        	h -= tabhW[2] * 10;
    			}
    	    	//Si on peut bloquer le roi ennemi, on le choisi
    	    	if(tabhB[2] == 7) {
    	    		h += tabhB[2] * 1000;
    	    	}else {
    	    		//le roi ennemi est bloque
    	        	h += tabhB[2] * 10;
    			}
    	    	//nos soldats sont pres du centre
    	    	h += tabhW[0] * 100;
    	    	
    	    	//soldats sont aussi pres du centre, mais pas tres critique
    	    	//car on peut change rien dans ce coup(c'est pas notre coup)
    	    	
    	    	//les autres strategies
    	    	//la possibilite de nos pions d'entrer pres du centre
    	    	h += tabhW[3] * 100;
    	    	
    	    	//la possibilite de soldats ennemis d'entrer pres du centre
    	    	h -= tabhB[3];
            	}
            	//en fin du jeu, on donne la priorite de bloquer le roi ennemi
            	else if (MyChallenger.TEMPSTOTAL < 20) {
            		//Si notre roi a la possibilite d'entrer au centre, on le choisi
            		h += tabhW[1] * 1000;
        	    	//Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
        	    	h -= tabhB[1] * 1500;
        	    	//Si notre roi peut-etre bloque, ne choisi pas
        	    	if(tabhW[2] == 7) {
        	    		h -= tabhW[2] * 1000;
        	    	}else {
        	    		//notre roi est bloque
        	        	h -= tabhW[2] * 10;
        			}
        	    	//Si on peut bloquer le roi ennemi, on le choisi
        	    	if(tabhB[2] == 7) {
        	    		h += tabhB[2] * 1500;
        	    	}else {
        	    		//le roi ennmi est bloque
        	        	h += tabhB[2] * 100;
        			}
        	    	//nos soldats sont pres du centre
        	    	h += tabhW[0] * 100;
        	    	//soldats sont aussi pres du centre, mais pas tres critique
        	    	//car on peut change rien dans ce coup(c'est pas notre coup)
        	    	
        	    	//les autres strategies
        	    	//la possibilite de nos pions d'entrer pres du centre
        	    	h += tabhW[3];
        	    	
        	    	//la possibilite de soldats ennemis d'entrer pres du centre
        	    	h -= tabhB[3];
    			}else {
    		         //Si notre roi a la possibilite d'entrer au centre, on le choisi
    			      h += tabhW[1] * 1000;
    			      
    			      //Si le roi ennmi peut-etre entre au centre, on ne le choisi pas
    			      h -= tabhB[1] * 1000;
    			      
    			      //Si notre roi peut-etre bloque, ne choisi pas
    			      if(tabhW[2] == 7) {
    			       h -= tabhW[2] * 1000;
    			      }else {
    			       //notre roi est bloque
    			          h -= tabhW[2] * 10;
    			   }
    			      //Si on peut bloquer le roi ennemi, on le choisi
    			      if(tabhB[2] == 7) {
    			       h += tabhB[2] * 1000;
    			      }else {
    			       //le roi ennemi est bloque
    			          h += tabhB[2] * 10;
    			   }
    			      //nos soldats sont pres du centre
    			      h += tabhB[0] * 100;
    			      
    			      //soldats sont aussi pres du centre, mais pas tres critique
    			      //car on peut change rien dans ce coup(c'est pas notre coup)
    			      
    			      //les autres strategies
    			      //la possibilite de nos pions d'entrer pres du centre
    			      h += tabhW[3] * 10;
    			      
    			      //la possibilite de soldats ennemis d'entrer pres du centre
    			      h -= tabhB[3];
    			         }
        }
        //System.out.println(h);
		return h;
		//return allPossibleMovesB.size() - allPossibleMovesW.size();
    };
}
