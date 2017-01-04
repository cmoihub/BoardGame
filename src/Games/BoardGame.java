package Games;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Moves.*;
import Othello.GraphicalOthello;
public abstract class BoardGame{
    private int size;
    private MoveStrategy strategy;
    protected GameStatus gameStatus;
    protected char[][] grid;
    protected char turn;
    
    public BoardGame(int size) {
        this(size, new FirstAvailableMove());
    }
    public BoardGame(int size, MoveStrategy strategy) {
        gameStatus = GameStatus.ONGOING;
        this.size = size;
        this.strategy = strategy;
        grid = new char[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = '_';
            }
        }
        turn = 'x';
    }
    public BoardGame(BoardGame b) {
        gameStatus = b.gameStatus;
        size = b.size;
        strategy = b.strategy;
        grid = new char[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = b.grid[i][j];
            }
        }
        turn = b.turn;
    }
    
    protected abstract void determineWinner();
    public abstract int getMinScore();
    public abstract int evaluateMove(Move m);
    protected abstract boolean canPlay(Move m);
    public abstract GameStatus play(Move m);
    protected abstract void loop();
    protected void computerGetsInput(Scanner input){
   	 System.out.println("Enter 'x' if you want to start");
       if (!(input.nextLine().equals("x"))) {
           System.out.println("OK, I'll start then");
           gameStatus = machinePlay();
           toggleTurn();
       }
    }
    
    protected void endIfMoveIsInvalid(Move move){
   	 if (move == null) {
          System.out.println("Bye bye!");
          return;
      }
    }
    
    protected void switchToComputer(){
   	 System.out.println("Computer's turn: ");
       toggleTurn();
    }
    
    protected void enterYourMove(){
   	 System.out.println("Enter your move: <row, col> or quit <q>");
    }
    
    protected void showWhoWon(GameStatus gameStatus){
   	 determineWinner();
       displayStatus(gameStatus);
       System.out.println("Bye bye!");
    }
    
    public void toggleTurn() {
       if (turn == 'x') 
      	 turn = 'o';
       else 
      	 turn = 'x';
   }
   
   protected char getTurn() {
  	 return turn;
   }
    
    protected void setMoveStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }
    
    public List<Move> generateMoves() {
        List<Move> moves = new ArrayList<Move>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                Move newMove = new Move(row,column);
                if (canPlay(newMove)) 
                    moves.add(newMove);
            }
        }
        return moves;
    }
    protected void displayStatus(GameStatus s) {
        switch (s) {
            case ILLEGAL: 
            	JOptionPane.showMessageDialog(null, s.toString());
            	System.out.println(s.toString()); 
            	break;
            default:
            	System.out.println(s.toString());
            	print();
            	break;
        }
    }
    
    protected GameStatus machinePlay() {        
        Move move = strategy.selectMove(this);
        if (move == null) return GameStatus.NO_MOVE; //game loop should prevent this from happening
        else return play(move);
    }
    
    protected Move getMove(String input) {
        int row, column;
        String[] smove = input.split("[, ]+");
        if (smove.length != 2)  
      	  return null;
        try {
            row = Integer.parseInt(smove[0]);
            column = Integer.parseInt(smove[1]);
        }
        catch (Exception e) {return null;}
        return new Move(row,column);
    }
   
    protected void print() {
        System.out.println(this);
    }
    
    public String toString() {
       String s = "";
       for (int row = 0; row < grid.length; row++) {
           for (int column = 0; column < grid.length; column++) {
               s += grid[row][column];
           }
           s += "\n";
       }
       return s;
   }
}