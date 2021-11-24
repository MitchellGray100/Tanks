package piece;

import piece.Piece.Player;
import piece.Piece.PowerUpType;

public class PieceFactory {
	
	public Brick getBrick()
	{
		return new Brick();
	}
	
	public Bullet getBullet(Player player)
	{
		return new Bullet(player);
	}
	
	public PowerUp getPowerUp(PowerUpType power)
	{
		return new PowerUp(power);
	}
	
	public Tank getTank(Player player)
	{
		return new Tank(player);
	}
	
}
