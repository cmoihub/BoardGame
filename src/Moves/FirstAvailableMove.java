package Moves;
import java.util.*;
import Games.BoardGame;

public class FirstAvailableMove implements MoveStrategy {
    public Move selectMove(BoardGame game) {
        List<Move> moves = game.generateMoves();
        if (moves.isEmpty()) return null;
        else return moves.get(0);
    }
}