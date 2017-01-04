package Othello;
import java.awt.*;
import javax.swing.*;
import Games.*;
import Moves.*;
import TicTacToe.GraphicalTicTacToe;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
/**
 * GUI for the othello class designed by Professor Babak Esfandiari
 * 
 * @author Craig Isesele
 * @version Assign5
 */
public class GraphicalOthello extends Othello implements ActionListener
{
   JFrame gameFrame;
   JButton[][] buttons;
   JPanel othelloPanel;
   JPanel movePanel;
   JButton greed, first, random;
   public static int size = 4;
   private BufferedImage player,computer,empty;
   public GraphicalOthello()
    {
        gameFrame = new JFrame(" Othello > Reversi? ");
        othelloPanel = new JPanel();
        movePanel = new JPanel();
        buttons = new JButton[SIZE][SIZE];
    }
   
   public void setupGUI(){
   	othelloPanel.setLayout(new GridLayout(SIZE, SIZE));
      movePanel.setLayout(new GridLayout(1,3));
      gameFrame.setLayout(new BorderLayout());
      setupImages();        
      for (int row = 0; row < SIZE; row++) {
          for (int column = 0; column < SIZE; column++) {
              String place = "" + grid[row][column];
              buttons[row][column] = new JButton (place);
              buttons[row][column].setBackground(Color.white);
              buttons[row][column].addActionListener(this);
              buttons[row][column].setActionCommand("" + row + column);
              othelloPanel.add(buttons[row][column]);
              if(grid[row][column] == 'x')
            	  replaceWithImage(player,row,column,buttons);
             else if(grid[row][column] == 'o')
            	 replaceWithImage(computer,row,column,buttons);
             else if(grid[row][column] == '_')
            	 replaceWithImage(empty,row,column,buttons);
             
             // fillGrid(row,column,size,player,computer,empty,buttons,grid);
          }
      }  
      createMoveButton("GreedyMove");
      createMoveButton("FirstAvailableMove");
      createMoveButton("RandomMove");
      setupGameFrame(gameFrame, othelloPanel, movePanel);
      start();
      gameFrame.setVisible(true);
   }
   
   private void setupGameFrame(JFrame gameFrame, JPanel othelloPanel, JPanel movePanel){
   	gameFrame.getContentPane().add(othelloPanel, BorderLayout.CENTER);
      gameFrame.getContentPane().add(movePanel,BorderLayout.NORTH);
      gameFrame.setPreferredSize(new Dimension(400, 400));
      gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gameFrame.pack();
   }
   
   private void createMoveButton(String move){
   	JButton moveButton = new JButton (move);
      moveButton.setActionCommand(move);        
      addButton(moveButton,this,movePanel);
   }
   
   private void addButton(JButton button, GraphicalOthello GO, JPanel panel)
   {
       button.addActionListener(GO);
       panel.add(button);
    }
   
   private void setupImages(){
   	try{
   		player = ImageIO.read(new File("black.jpg"));
   		}
      catch(IOException e){	
      }
      try{
      	computer = ImageIO.read(new File("white.jpg"));
      }
      catch(IOException e){	
      }
      try{
      	empty = ImageIO.read(new File("null.jpg"));
      	}
      catch(IOException e){
      }
   }
   
   private void replaceWithImage(BufferedImage image, int row, int column, JButton [][] button){
   	button[row][column].setIcon(new ImageIcon(image));
      button[row][column].setPreferredSize(new Dimension(size,size));
   }
    
   private void copyGrid()
    {
        for(int row = 0; row < SIZE ;row++){
            for(int column = 0 ; column < SIZE; column++){
                //String place = "" + grid[row][column];
                fillGrid(row,column,size,player,computer,empty,buttons,grid);
            }
        }
    }
    
   public void print()
    {
   	for(int row = 0; row < SIZE ;row++){
         for(int column = 0 ; column < SIZE; column++){
               fillGrid(row,column,size,player,computer,empty,buttons,grid);
           }
        }
    }
   
   private void fillGrid (int row, int column, int size, BufferedImage player, BufferedImage computer, BufferedImage empty, JButton [][] button, char[][] grid){
      // x is the player, o is the computer
      //'_' refers to the free spots
 	if(grid[row][column] == 'x')
  	  replaceWithImage(player,row,column,buttons);
   else if(grid[row][column] == 'o')
  	 replaceWithImage(computer,row,column,buttons);
   else if(grid[row][column] == '_')
  	 replaceWithImage(empty,row,column,buttons);
  }
    
   public void actionPerformed(ActionEvent event)
    {
     //JButton button = (JButton) event.getSource();
     String command = event.getActionCommand();
       if (command == "FirstAvailableMove") 
            setMoveStrategy(new FirstAvailableMove());
       else if (command == "RandomMove")
            setMoveStrategy(new RandomMove());
       else if (command == "GreedyMove")
            setMoveStrategy(new GreedyMove());
       else{       
       int r = Integer.parseInt(command.substring(0, 1));
       int c = Integer.parseInt(command.substring(1, 2));
       Move move = new Move(r, c);
       GameStatus status;
        if(canPlay(move)){
        status = play(move);
        playerPlaying();
        		} else{
      	 return;
      	 }
       
       status = GameStatus.ONGOING;
       while (status == GameStatus.ONGOING){
          if(!(generateMoves().isEmpty())){ 
            status = machinePlay();
            machinePlaying();    
            if(!(generateMoves().isEmpty())){  
                if(canPlay(move)){  
                status = play(move);
                playerPlaying();
              }
              else{ return;}
            }
            else if(!(generateMoves().isEmpty())) { 
                status = machinePlay();
                machinePlaying();
                determineWinner();
            }
            else { 
                status = GameStatus.GAME_OVER;
                gameOver();
            }  
          }
          else if(!(generateMoves().isEmpty())){ 
            if(canPlay(move))
            {  
              status = play(move);
              playerPlaying();
             } else{ return;}
          }
          else
          {
            status = GameStatus.GAME_OVER;
            gameOver();
          }
        }
    }
   } 
   
   private void playerPlaying(){
       copyGrid();
       toggleTurn();
    }
   
   private void machinePlaying(){    
        copyGrid();
        print();
        toggleTurn();
    }
   
   private void gameOver()
   {
       print();
       JOptionPane.showMessageDialog(gameFrame,determineGame() 
                + "","Game Over", JOptionPane.INFORMATION_MESSAGE);
       restart();
    }
   
   private void start()
    {
        int question = JOptionPane.showConfirmDialog(gameFrame,
            "Do you want to start Othello?","Start Game",
              JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);     
        if(question == JOptionPane.YES_OPTION)
      	  return;
        else if(question == JOptionPane.NO_OPTION){
            gameStatus = machinePlay();
            machinePlaying();
            return;
        }else{System.exit(0);}
    }
    
   private void restart()
    {
   	GraphicalBoardGame game = new GraphicalBoardGame();
   	game.start();
    }
    
   private String determineGame() {
        determineWinner();
        String str = "";
        if(gameStatus == GameStatus.X_WON)  return str = "Batman won!!!";
        else if (gameStatus == GameStatus.O_WON) return str = "Superman?!!!"; 
        else if (gameStatus == GameStatus.TIE)  return str = "I guess they tied the knot ;-)"; 
        else return str; 
    }
}