package Games;

public enum GameStatus {
	ILLEGAL ("Illegal move!"), TIE ("It's a tie!"), X_WON ("Player won!"), O_WON ("Computer won"), 
	ONGOING (""), NO_MOVE (""), GAME_MOVE (""), GAME_OVER ("");
	
	private String description;
	private GameStatus (String asString){
		this.description = asString;
	}
	
	public String toString() {
		return description;
	}
}