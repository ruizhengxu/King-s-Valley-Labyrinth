package games.kingsvalley;

import iialib.games.model.IMove;

public class KVMove implements IMove {
    
    public final String move;
    
    KVMove(String move){
        this.move = move;
    }

    @Override
    public String toString() {
        return move;
    }
}
