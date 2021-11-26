package application;
	
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Controller.Controller;
import Controller.ControllerImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import piece.PowerUp;
import piece.Tank;


public class Main extends Application {
	private final double BASE_TANK_SPEED = 2.5;
	private final double BASE_BULLET_SPEED = 7;
	private Controller controller = new ControllerImpl();
	private Pane root = new Pane();
	private Piece tankOne;
	private Piece tankTwo;
	private Piece powerUp;
	private LinkedList<Piece> brickList = new LinkedList<Piece>();
	private boolean tankOneMoveUp;
	private boolean tankOneMoveRight;
	private boolean tankOneMoveDown;
	private boolean tankOneMoveLeft;
	private boolean tankTwoMoveUp;
	private boolean tankTwoMoveRight;
	private boolean tankTwoMoveDown;
	private boolean tankTwoMoveLeft;
	private boolean tankOneShoot;
	private boolean tankTwoShoot;
	private double t = 0;
	private double tankOneBulletTimer = 1;
	private double tankTwoBulletTimer = 1;
	private Text gameOverText = new Text();
	
	public Parent createContent(Stage primaryStage)
	{
		gameOverText.setText("");
		gameOverText.setTextAlignment(TextAlignment.CENTER);
		gameOverText.setFont(new Font(50));
		gameOverText.setFill(Color.YELLOW);
		gameOverText.setStroke(Color.BLACK);
		gameOverText.setTranslateX(300);
		gameOverText.setTranslateY(450);
		for(int r = 0; r < 10; r++)
		{
			for(int c = 0; c < 10; c++)
			{
				if(controller.getSquarePiece(r, c) != null)
				{
					switch(controller.getSquarePiece(r, c).getType())
					{
					case BRICK:
						Piece temp = new Piece(r*100,c * 100,100,100, "brick", Color.MAROON,r,c);
						brickList.add(temp);
						root.getChildren().add(temp);
						break;
					case BULLET:
						break;
					case POWERUP:
						powerUp = new Piece(r*100+25,c * 100+25,50,50, "powerup", null,r,c);
						root.getChildren().add(powerUp);
						break;
					case TANK:
						if(controller.getSquarePiece(r, c).getPlayer() == piece.Piece.Player.ONE)
						{
							tankOne = new Piece(r*100,c * 100+50,50,50, "tankOne", Color.GREEN,r,c);
							root.getChildren().add(tankOne);
						}
						else
						{
							tankTwo = new Piece(r*100+50,c * 100,50,50, "tankTwo", Color.GREEN,r,c);
							root.getChildren().add(tankTwo);
						}
						break;
					default:
						break;
				
					}
				}
			}
		}

		
		root.setPrefSize(1000, 1000);
		
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				if(gameOverText.getText().equals(""))
					update();
				else
				{

					stop();
				}
			}
		};
		timer.start();
		
		
		return root;
	}
	
	private List<Piece> pieces() {
		return root.getChildren().stream().map(n -> (Piece)n).collect(Collectors.toList());
	}
	
	private void update()
	{	
			t+=.016;
			if(tankTwo.dead || tankOne.dead)
			{
				if(!root.getChildren().contains(gameOverText))
					root.getChildren().add(gameOverText);
				if(tankTwo.dead)
				{
					
					gameOverText.setText("Tank One Won!\nPress SPACE to restart");
				}
				else
				{
					gameOverText.setText("Tank Two Won!\nPress SPACE to restart");
				}
			}
			if(!tankTwo.dead && !tankOne.dead)
			{	
				pieces().forEach(s -> {
				
				switch(s.type)
				{
				case "tankOneBullet":
					for(Piece piece: brickList)
					{
						if(s.getBoundsInParent().intersects(piece.getBoundsInParent()))
						{
							s.dead = true;
						}
					}
					if(s.getBoundsInParent().intersects(tankTwo.getBoundsInParent()))
					{
						if(((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).hasShield())
						{
							((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShield(false);
							tankTwo.border.setFill(Color.GREEN);
						}
						else
						{
							tankTwo.dead = true;
						}
						s.dead = true;
					}
					switch(s.direction)
					{
					case DOWN:
						s.moveDown();
						break;
					case LEFT:
						s.moveLeft();
						s.setRotate(90);
						break;
					case RIGHT:
						s.moveRight();
						s.setRotate(90);
						break;
					case UP:
						s.moveUp();
						break;
					default:
						break;
					
					}
					break;
				case "tankTwoBullet":
					if(s.getBoundsInParent().intersects(tankOne.getBoundsInParent()))
					{
						if(((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).hasShield())
						{
							((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).setShield(false);
							tankOne.border.setFill(Color.GREEN);
						}
						else
						{
							tankOne.dead = true;
						}
						s.dead = true;
					}
					for(Piece piece: brickList)
					{
						
						if(s.getBoundsInParent().intersects(piece.getBoundsInParent()))
						{
							s.dead = true;
						}
						
					}
					switch(s.direction)
					{
					case DOWN:
						s.moveDown();
						break;
					case LEFT:
						s.moveLeft();
						s.setRotate(90);
						break;
					case RIGHT:
						s.moveRight();
						s.setRotate(90);
						break;
					case UP:
						s.moveUp();
						break;
					default:
						break;
					
					}
					break;
				case "tankOne":
					tankOneBulletTimer-=.01 * ((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).getShootSpeedMultiplier();
					if(tankOneBulletTimer < 0)
					{
						tankOneBulletTimer = 0;
					}
						if(tankOneShoot)
						{
							shoot(tankOne);
						}
					
						if(tankOneMoveRight)
						{
							if(!tankOneMoveUp && !tankOneMoveDown)
							{
								controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.RIGHT);
								for(Piece piece: brickList)
								{
									if(tankOne.getBoundsInParent().intersects(piece.r * 100, piece.c * 100+5, 10, 90))
									{
										s.moveLeft();
									}
								}
								s.moveRight();
							}
							if(tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX(), tankTwo.getTranslateY()+5, 10, 40))
							{
								s.moveLeft();
							}
						}
						if(tankOneMoveLeft)
						{
							if(!tankOneMoveUp && !tankOneMoveDown)
							{
								controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.LEFT);
								for(Piece piece: brickList)
								{
									if(tankOne.getBoundsInParent().intersects(piece.r * 100+90, piece.c * 100+5, 10, 90))
									{
										s.moveRight();
									}
								}
								if(tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX()+40, tankTwo.getTranslateY()+5, 10, 40))
								{
									s.moveRight();
								}
								s.moveLeft();
							}
						}
						if(tankOneMoveUp)
						{
//							if(!tankOneMoveLeft && !tankOneMoveRight)
							{
								controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.UP);
								for(Piece piece: brickList)
								{
									if(tankOne.getBoundsInParent().intersects(piece.r * 100+5, piece.c * 100+90, 90, 10))
									{
										s.moveDown();
									}
								}
								if(tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX()+5, tankTwo.getTranslateY()+40, 40, 10))
								{
									s.moveDown();
								}
								s.moveUp();
							}
						}
						if(tankOneMoveDown)
						{
//							if(!tankOneMoveLeft && !tankOneMoveRight)
							{
								controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.DOWN);
								for(Piece piece: brickList)
								{
									if(tankOne.getBoundsInParent().intersects(piece.r * 100+5, piece.c * 100, 90, 10))
									{
										s.moveUp();
									}
								}
								if(tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX()+5, tankTwo.getTranslateY(), 40, 10))
								{
									s.moveUp();
								}
								s.moveDown();
							}
						}
						switch((controller.getSquarePiece(tankOne.r, tankOne.c)).getDirection())
						{
						case DOWN:
							tankOne.setRotate(180);
							break;
						case LEFT:
							tankOne.setRotate(270);
							break;
						case RIGHT:
							tankOne.setRotate(90);
							break;
						case UP:
							tankOne.setRotate(0);
							break;
						default:
							break;
						
						}
					break;
				case "tankTwo":
					tankTwoBulletTimer-=.01 *((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).getShootSpeedMultiplier();
					if(tankTwoBulletTimer < 0)
					{
						tankTwoBulletTimer = 0;
					}
					if(tankTwoShoot)
					{
						shoot(tankTwo);
					}
					if(tankTwoMoveRight)
					{
						if(!tankTwoMoveUp && !tankTwoMoveDown)
						{
							controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.RIGHT);
							for(Piece piece: brickList)
							{
								if(tankTwo.getBoundsInParent().intersects(piece.r * 100, piece.c * 100+5, 10, 90))
								{
									s.moveLeft();
								}
							}
							if(tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX(), tankOne.getTranslateY()+5, 10, 40))
							{
								s.moveLeft();
							}
							s.moveRight();
						}
					}
					if(tankTwoMoveLeft)
					{
						if(!tankTwoMoveUp && !tankTwoMoveDown)
						{
							controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.LEFT);
							for(Piece piece: brickList)
							{
								if(tankTwo.getBoundsInParent().intersects(piece.r * 100+90, piece.c * 100+5, 10, 90))
								{
									s.moveRight();
								}
							}
							if(tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX()+40, tankOne.getTranslateY()+5, 10, 40))
							{
								s.moveRight();
							}
							s.moveLeft();
						}
					}
					if(tankTwoMoveUp)
					{
//						if(!tankTwoMoveLeft && !tankTwoMoveRight)
						{
							controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.UP);
							for(Piece piece: brickList)
							{
								if(tankTwo.getBoundsInParent().intersects(piece.r * 100+5, piece.c * 100+90, 90, 10))
								{
									s.moveDown();
								}
							}
							if(tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX()+5, tankOne.getTranslateY()+40, 40, 10))
							{
								s.moveDown();
							}
							s.moveUp();
						}
					}
					if(tankTwoMoveDown)
					{
//						if(!tankTwoMoveLeft && !tankTwoMoveRight)
						{
							controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.DOWN);
							for(Piece piece: brickList)
							{
								if(tankTwo.getBoundsInParent().intersects(piece.r * 100+5, piece.c * 100, 90, 10))
								{
									s.moveUp();
								}
							}
							if(tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX()+5, tankOne.getTranslateY(), 40, 10))
							{
								s.moveUp();
							}
							s.moveDown();
						}
					}
					switch(controller.getSquarePiece(tankTwo.r, tankTwo.c).getDirection())
					{
					case DOWN:
						tankTwo.setRotate(180);
						break;
					case LEFT:
						tankTwo.setRotate(270);
						break;
					case RIGHT:
						tankTwo.setRotate(90);
						break;
					case UP:
						tankTwo.setRotate(0);
						break;
					default:
						break;
					
					}
					break;
					
				}
			});}
			
			if(tankTwo.getBoundsInParent().intersects(powerUp.getBoundsInParent()))
			{
				powerUp.dead = true;
				switch(((PowerUp)controller.getSquarePiece(powerUp.r, powerUp.c)).getPowerUpType())
				{
				case FASTBULLET:
					((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).setBulletSpeedMultiplier(1.5);
					break;
				case FASTSHOOT:
					((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShootSpeedMultiplier(1.5);
					break;
				case FASTSPEED:
					((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).setTankSpeedMultiplier(1.5);
					break;
				case SHIELD:
					((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShield(true);
					tankTwo.border.setFill(Color.SKYBLUE);
					break;
				default:
					break;
				
				}
						
					
						
			}
			if(tankOne.getBoundsInParent().intersects(powerUp.getBoundsInParent()))
			{
				powerUp.dead = true;
				switch(((PowerUp)controller.getSquarePiece(powerUp.r, powerUp.c)).getPowerUpType())
				{
				case FASTBULLET:
					((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).setBulletSpeedMultiplier(1.5);
					break;
				case FASTSHOOT:
					((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).setShootSpeedMultiplier(1.5);
					break;
				case FASTSPEED:
					((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).setTankSpeedMultiplier(1.5);
					break;
				case SHIELD:
					((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).setShield(true);
					tankOne.border.setFill(Color.SKYBLUE);
					break;
				default:
					break;
				}
			}
			
			
			
			if(gameOverText.getText().equals(""))
			{
				root.getChildren().removeIf(n -> {
					Piece s = (Piece) n;
					return s.dead;
				});
			}
			if(t > 2) {
				t = 0;
			}
		
	}
	
	private void shoot(Piece who)
	{
		if(who.type.equals("tankOne"))
		{
			if(tankOneBulletTimer < 1)
			{
				tankOneBulletTimer=1.4;
		
				Piece s = new Piece((int)who.getTranslateX() + 25, (int)who.getTranslateY() + 10, 5, 20,who.type+"Bullet" , Color.BLACK,0,0);
				s.direction = controller.getSquarePiece(who.r, who.c).getDirection();
				root.getChildren().add(s);
			}
		}
		else if(tankTwoBulletTimer < 1)
		{
			tankTwoBulletTimer=1.4;
		
			Piece s = new Piece((int)who.getTranslateX() + 25, (int)who.getTranslateY() + 10, 5, 20,who.type+"Bullet" , Color.BLACK,0,0);
			s.direction = controller.getSquarePiece(who.r, who.c).getDirection();
			root.getChildren().add(s);
		}
	}
	
	public class Piece extends StackPane {
		boolean dead = false;
		final String type;
		Rectangle border;
		Rectangle indication;
		piece.Piece.Direction direction;
		int r;
		int c;
		Piece(int x, int y, int w, int h, String type, Color color, int r, int c)
		{
			indication = new Rectangle(10,5,Color.BLACK);
			indication.setTranslateY(this.getTranslateY()-25);
			border = new Rectangle(w,h,color);
			getChildren().addAll(border);
			direction = controller.getSquarePiece(r, c).getDirection();
			if(type.equals("tankOne") || type.equals("tankTwo"))
			{
				getChildren().add(indication);
			}
			if(type.equals("powerup"))
			{
				switch(((PowerUp)controller.getSquarePiece(r, c)).getPowerUpType())
				{
				case FASTBULLET:
					border.setFill(Color.GOLD);
					break;
				case FASTSHOOT:
					border.setFill(Color.RED);
					break;
				case FASTSPEED:
					border.setFill(Color.LIME);
					break;
				case SHIELD:
					border.setFill(Color.SKYBLUE);
					break;
				default:
					break;
				
				}
			}
			this.type = type;
			setTranslateX(x);
			setTranslateY(y);
			this.r = r;
			this.c = c;
		}
		void moveLeft() {
			if(type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateX(getTranslateX() - (BASE_TANK_SPEED * ((Tank)controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if(type.equals("tankOneBullet"))
				setTranslateX(getTranslateX() - BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if(type.equals("tankTwoBullet"))
				setTranslateX(getTranslateX() - BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}
		void moveRight()
		{
			if(type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateX(getTranslateX() +(BASE_TANK_SPEED * ((Tank)controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if(type.equals("tankOneBullet"))
				setTranslateX(getTranslateX() + BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if(type.equals("tankTwoBullet"))
				setTranslateX(getTranslateX() + BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}
		void moveUp() {
			if(type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateY(getTranslateY() -(BASE_TANK_SPEED * ((Tank)controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if(type.equals("tankOneBullet"))
				setTranslateY(getTranslateY() - BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if(type.equals("tankTwoBullet"))
				setTranslateY(getTranslateY() - BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
				
		}
		void moveDown()
		{
			if(type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateY(getTranslateY() + (BASE_TANK_SPEED * ((Tank)controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if(type.equals("tankOneBullet"))
				setTranslateY(getTranslateY() + BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if(type.equals("tankTwoBullet"))
				setTranslateY(getTranslateY() + BASE_BULLET_SPEED * ((Tank)controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}
	}
	@Override
	public void start(Stage primaryStage) {

		
		//blackKnightImage = new Image(new FileInputStream("src/Black Knight.png"));
		//primaryStage.getIcons().add(blackKnightImage);
		
		//titleScene = new Scene(createTitleContent(primaryStage));
		
		Scene scene = new Scene(createContent(primaryStage));
		primaryStage.setScene(scene);
		primaryStage.show();
		//primaryStage.setScene(titleScene);
		primaryStage.setMaxHeight(1000);
		primaryStage.setMaxWidth(1000);
		primaryStage.setMinHeight(1000);
		primaryStage.setMinWidth(1000);
		primaryStage.setHeight(1000);
		primaryStage.setWidth(1000);
		scene.setOnKeyPressed(e -> {
			switch(e.getCode())
			{
			case A:
				tankOneMoveLeft = true;
				break;
			case D:
				tankOneMoveRight = true;
				break;
			case W:
				tankOneMoveUp = true;
				break;
			case S:
				tankOneMoveDown = true;
				break;
			case J:
				tankTwoMoveLeft = true;
				break;
			case L:
				tankTwoMoveRight = true;
				break;
			case I:
				tankTwoMoveUp = true;
				break;
			case K:
				tankTwoMoveDown = true;
				break;
			case C:
				tankOneShoot = true;
				break;
			case N:
				tankTwoShoot = true;
				break;
			}
		});
		scene.setOnKeyReleased( e-> {
			switch(e.getCode())
			{
			case A:
				tankOneMoveLeft = false;
				break;
			case D:
				tankOneMoveRight = false;
				break;
			case W:
				tankOneMoveUp = false;
				break;
			case S:
				tankOneMoveDown = false;
				break;
			case J:
				tankTwoMoveLeft = false;
				break;
			case L:
				tankTwoMoveRight = false;
				break;
			case I:
				tankTwoMoveUp = false;
				break;
			case K:
				tankTwoMoveDown = false;
				break;
			case C:
				tankOneShoot = false;
				break;
			case N:
				tankTwoShoot = false;
				break;
			case SPACE:
				if(tankOne.dead || tankTwo.dead)
				{
					tankOne.dead = false;
					tankTwo.dead = false;
					tankOneMoveUp = false;
					tankOneMoveRight = false;
					tankOneMoveDown = false;
					tankOneMoveLeft = false;
					tankTwoMoveUp = false;
					tankTwoMoveRight = false;
					tankTwoMoveDown = false;
					tankTwoMoveLeft = false;
					tankOneShoot = false;
					tankTwoShoot = false;
					t = 0;
					tankOneBulletTimer = 1;
					tankTwoBulletTimer = 1;
					root = new Pane();
					controller = new ControllerImpl();
					brickList = new LinkedList<Piece>();
					gameOverText.setText("");
					scene.setRoot((createContent(primaryStage)));
					
					
					
					break;
				}
			}
		});
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
