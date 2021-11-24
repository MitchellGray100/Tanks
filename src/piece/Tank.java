package piece;

public class Tank extends AbstractPiece{
	
	private Direction direction;
	private boolean shield = false;
	private double shootSpeedMultiplier;
	private double bulletSpeedMultiplier;
	private double tankSpeedMultiplier;
	
	public Tank(Player piecePlayer) 
	{
		
		super(Type.TANK, piecePlayer);
		setBulletSpeedMultiplier(1);
		setTankSpeedMultiplier(1);
		if(piecePlayer == Player.ONE)
		{
			direction = Direction.UP;
		}
		else
		{
			direction = Direction.DOWN;
		}
		
	}

	public boolean hasShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public double getBulletSpeedMultiplier() {
		return bulletSpeedMultiplier;
	}

	public void setBulletSpeedMultiplier(double bulletSpeedMultiplier) {
		this.bulletSpeedMultiplier = bulletSpeedMultiplier;
	}

	public double getTankSpeedMultiplier() {
		return tankSpeedMultiplier;
	}

	public void setTankSpeedMultiplier(double tankSpeedMultiplier) {
		this.tankSpeedMultiplier = tankSpeedMultiplier;
	}

	public double getShootSpeedMultiplier() {
		return shootSpeedMultiplier;
	}

	public void setShootSpeedMultiplier(double shootSpeedMultiplier) {
		this.shootSpeedMultiplier = shootSpeedMultiplier;
	}

}
