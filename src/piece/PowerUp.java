package piece;

public class PowerUp extends AbstractPiece {

	private PowerUpType powerUpType;

	public PowerUp(PowerUpType power) {
		super(Type.POWERUP, Player.NONE);
		setPowerUpType(power);
	}

	public PowerUpType getPowerUpType() {
		return powerUpType;
	}

	public void setPowerUpType(PowerUpType powerUpType) {
		this.powerUpType = powerUpType;
	}

}
