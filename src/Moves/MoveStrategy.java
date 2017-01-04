package Moves;
import Games.BoardGame;

public interface MoveStrategy {    
    public abstract Move selectMove(BoardGame game);
}