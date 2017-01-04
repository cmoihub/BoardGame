package Games;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Othello.GraphicalOthello;
import TicTacToe.GraphicalTicTacToe;

public class GraphicalBoardGame {
	private JFrame gameFrame;
	public GraphicalBoardGame() {
		gameFrame = new JFrame();	
		}
	
	public void start(){
		Object [] games = {"Othello", "Tic Tac Toe"};
 	  String chosenGame = (String) JOptionPane.showInputDialog(gameFrame, "Choose a game", null, JOptionPane.PLAIN_MESSAGE,null,games,"Othello");
 	  //gameFrame.dispose();
 	  if (chosenGame == "Othello"){
 		  GraphicalOthello othello = new GraphicalOthello();
 		  othello.setupGUI();
 	  }
 	  else if (chosenGame == "Tic Tac Toe"){
 		  GraphicalTicTacToe ticTacToe = new GraphicalTicTacToe();
 		  ticTacToe.setupGui();
 	  }  
 	  else
 		  start();
 	  gameFrame.dispose();
 	  }
	
	
	public static void main(String args[]){
		GraphicalBoardGame game = new GraphicalBoardGame();
		game.start();
	}
}