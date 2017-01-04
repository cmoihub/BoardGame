package TicTacToe;
/**
 * One-player version of Tic Tac Toe, against terrible AI 
 * @author babak
 * @version 0.0
 */
import java.util.*;
import Games.*;
import Moves.*;
public class TicTacToe extends BoardGame {
    public static int SIZE_OF_TICTACTOE_BOARD = 3;
    public TicTacToe(int size, MoveStrategy strategy) {
        super(size, strategy);
    }
    public TicTacToe(int size) {
        super(size);
    }
    public TicTacToe() {
        this(SIZE_OF_TICTACTOE_BOARD);
    }
    public TicTacToe(TicTacToe t) {
        super(t);
    }
    
    protected boolean canPlay(Move m) {
        int row = m.getRow(); 
        int col = m.getCol();
        if ((row < 0) || (row >= SIZE_OF_TICTACTOE_BOARD) || (col < 0) || (col >= SIZE_OF_TICTACTOE_BOARD)) return false;
        if (grid[row][col] != '_') return false;
        return true;
    }
    
    public GameStatus play(Move m) {
        if (!canPlay(m)) return GameStatus.ILLEGAL; 
        
        int row = m.getRow(); 
        int col = m.getCol();
        
        grid[row][col] = turn;
        if (checkRow(row) || checkCol(col) || checkFirstDiag() || checkSecondDiag()) {
            if (turn == 'x') 
            	return GameStatus.X_WON; 
            else 
            	return GameStatus.O_WON;
        }
        return GameStatus.ONGOING;
    }
        
    protected void loop() {
        //int status = ONGOING;
        Scanner userInput = new Scanner(System.in);
        Move move;
        computerGetsInput(userInput);
        
        while (gameStatus == GameStatus.ONGOING) {
           print();
           do {
               enterYourMove();
               move = getMove(userInput.nextLine());
               endIfMoveIsInvalid(move);
            }
           while (!(canPlay(move)));
           gameStatus = play(move);
           
           if (gameStatus == GameStatus.ONGOING) {
               print();
               switchToComputer();
               gameStatus = machinePlay();
           }           
           toggleTurn();
        }
        
        showWhoWon(gameStatus);
        userInput.close();
    }
    
    private boolean checkRow(int row) {
        for (int column = 0; column < SIZE_OF_TICTACTOE_BOARD; column++) {
            if (grid[row][column] != turn) return false;
        }
        return true;
    }
    
    private boolean checkCol(int row) {
        for (int column = 0; column < SIZE_OF_TICTACTOE_BOARD; column++) {
            if (grid[column][row] != turn) return false;
        }
        return true;
    }
    
    private boolean checkFirstDiag() {
        for (int i = 0; i < SIZE_OF_TICTACTOE_BOARD; i++) {
            if (grid[i][i] != turn) return false;
        }
        return true;
    }
    
    private boolean checkSecondDiag() {
        for (int i = 0; i < SIZE_OF_TICTACTOE_BOARD; i++) {
            if (grid[i][SIZE_OF_TICTACTOE_BOARD - i - 1] != turn) return false;
        }
        return true;
    }
    
    protected void determineWinner() {
        if (gameStatus == GameStatus.NO_MOVE) 
      	  gameStatus = GameStatus.TIE;
        //otherwise status is already clear, no need to do anything
    }
    
    public int evaluateMove(Move m) {
        TicTacToe next = new TicTacToe(this);
        GameStatus s = next.play(m);
        if ((turn == 'x') && (s == GameStatus.X_WON)) return 1;
        if ((turn == 'o') && (s == GameStatus.O_WON)) return 1;
        if ((turn == 'x') && (s == GameStatus.O_WON)) return -1;
        if ((turn == 'o') && (s == GameStatus.X_WON)) return -1;
        return 0;
    }
    
    public int getMinScore() {
   	 return -1;
    }
    
    public static void main(String[] args) {
        (new TicTacToe(SIZE_OF_TICTACTOE_BOARD)).loop();
    }
}