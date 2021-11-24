package piece;

public interface Piece {
	

	enum Direction 
	{
		RIGHT,
		LEFT,
		UP,
		DOWN
	}
	
	enum PowerUpType
	{
		FASTSHOOT,
		FASTSPEED,
		FASTBULLET,
		SHIELD
	}
	
	enum Type {
		BRICK,
		BULLET,
		POWERUP,
		TANK
	}
	
	enum Player{
		ONE,
		TWO,
		NONE
	}
	
	/**
	 * Gets the Type of piece
	 *
	 * @return The Type of the piece
	 */
	Type getType();

	/**
	 * Sets the Type of piece
	 *
	 * @param pieceType The pieceType of the piece
	 */
	void setType(Type pieceType);
	
	
	/**
	 * Gets the Player of piece
	 *
	 * @return The Player of the piece
	 */
	Player getPlayer();

	/**
	 * Sets the Player of pieces
	 *
	 * @param playerType The playerType of the pieces
	 */
	void setPlayer(Player playerType);
	
}
