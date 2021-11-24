package piece;

public abstract class AbstractPiece implements Piece {
	private Type type;
	private Player player;
	
	@Override
	public Type getType()
	{
		return type;
	}

	@Override
	public void setType(Type pieceType)
	{
		type = pieceType;
	}
	
	
	@Override
	public Player getPlayer()
	{
		return player;
	}

	@Override
	public void setPlayer(Player piecePlayer)
	{
		player = piecePlayer;
	}
	
	public AbstractPiece(Type pieceType, Player piecePlayer)
	{
		type = pieceType;
		player = piecePlayer;
	}
}
