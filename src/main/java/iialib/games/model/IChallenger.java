package iialib.games.model;

import java.util.Set;

public interface IChallenger {
	
	// Attention :
	// Il n'y a pas forcement besoin de constructeurs dans la classe MyChallenger;
	// toute fois si vous decidez d'en ecrire, il faudra obligatoirement qu'une version
	// sans argument soit presente :
	// public MyChallenger()
	
	// ---------------------- Fonctions pour le tournoi ---------------------

    /**
     * L'arbitre vous demande le nom de votre equipe.
     * @return le nom de votre equipe sous la forme "Nom1 - Nom2 - Nom3"
     */
    String teamName();

    /**
     * L'arbitre vous signale votre role au debut de la partie.
     * Vous pouvez preparer votre representation interne du plateau a ce moment.
     * @param role le role qui vous est assigne ("BLACK" ou "WHITE")
     */
    void setRole(String role);

    /**
     * L'arbitre vous signale qu'il valide le dernier coup que vous lui avez communique.
     * Vous pouvez jouer ce coup dans votre representation interne du plateau a ce moment.
     * @param move le coup que vous venez de jouer, sous la forme "B7-B2"
     */
    void iPlay(String move);

    /**
     * L'arbitre vous signale qu'il valide le dernier coup que l'adversaire lui avez communique.
     * Vous pouvez jouer ce coup dans votre representation interne du plateau a ce moment.
     * @param move le coup que l'adversaire viens de jouer, sous la forme "B7-B2"
     */
    void otherPlay(String move);

    // La fonction bestMove n'est a implementer que pour la partie 3 du devoir.
    // Pour la partie 2, vous pouvez la laisser sous la forme :
    // public String bestMove() {
    //     return null;
    // }
    /**
     * L'arbitre vous demande le coup que vous souhaitez jouer.
     * Choisissez bien.
     * @return le coup que vous comptez jouer (et que vous soumettez a la validation de l'arbitre), sous la forme "B7-B2"
     */
    String bestMove();

    /**
     * L'arbitre vous signale que vous avez gagne.
     * @return un petit message ou une banniere de victoire.
     */
    String victory();

    /**
     * L'arbitre vous signale que vous avez perdu.
     * @return un petit message ou une banniere de defaite.
     */
    String defeat();

    /**
     * L'arbitre vous signale que la partie s'est soldee par une egalite.
     * @return un petit message ou une banniere de partie nulle.
     */
    String tie();
    
    // ---------------------- Fonctions pour les tests ---------------------
    
    /**
     * Vous devez renvoyer une chaine de caracteres decrivant l'etat du plateau.
     * @return la chaine representant le plateau (voir le sujet pour le format utilise)
     */
    String getBoard();
    
    /** 
     * Vous devez mettre a jour votre representation interne selon l'etat du plateau decrit dans un fichier texte.
     * @param fileName le nom du fichier a lire (voir le sujet pour le format utilise)
     */
    void setBoardFromFile(String fileName);
    
    /**
     * Vous devez renvoyer l'ensemble des coups possibles pour vous (en l'etat actuel de votre representation interne).
     * @return l'ensemble de coups possibles pour votre joueur (sous la forme "B7-B2")
     */
    Set<String> possibleMoves();
    
}
