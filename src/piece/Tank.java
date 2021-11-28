package piece;

public class Tank extends AbstractPiece {

	private boolean shield = false;
	private double shootSpeedMultiplier;
	private double bulletSpeedMultiplier;
	private double tankSpeedMultiplier;

	public Tank(Player piecePlayer) {

		super(Type.TANK, piecePlayer);
		setBulletSpeedMultiplier(1);
		setTankSpeedMultiplier(1);
		setShootSpeedMultiplier(1);
		if (piecePlayer == Player.ONE) {
			setDirection(Direction.UP);
		} else {
			setDirection(Direction.DOWN);
		}

	}

	public boolean hasShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
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
