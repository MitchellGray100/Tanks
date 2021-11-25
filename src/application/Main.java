package application;
	
import java.util.List;
import java.util.stream.Collectors;

import Controller.Controller;
import Controller.ControllerImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Main extends Application {
	private Controller controller = new ControllerImpl();
	private Pane root = new Pane();
	private Piece tankOne;
	private Piece tankTwo;
	private boolean tankOneCollision = false;
	private boolean tankTwoCollision = false;
	private boolean tankOneMoveUp;
	private boolean tankOneMoveRight;
	private boolean tankOneMoveDown;
	private boolean tankOneMoveLeft;
	private boolean tankTwoMoveUp;
	private boolean tankTwoMoveRight;
	private boolean tankTwoMoveDown;
	private boolean tankTwoMoveLeft;
	private double t = 0;
	public Parent createContent(Stage primaryStage)
	{
		root.setPrefSize(1000, 1000);

		for(int r = 0; r < 10; r++)
		{
			for(int c = 0; c < 10; c++)
			{
				if(controller.getSquarePiece(r, c) != null)
				{
					switch(controller.getSquarePiece(r, c).getType())
					{
					case BRICK:
						root.getChildren().add(new Piece(r*100,c * 100,100,100, "brick", Color.MAROON));
						break;
					case BULLET:
						break;
					case POWERUP:
						root.getChildren().add(new Piece(r*100,c * 100,100,100, "powerup", Color.SKYBLUE));
						break;
					case TANK:
						if(controller.getSquarePiece(r, c).getPlayer() == piece.Piece.Player.ONE)
						{
							tankOne = new Piece(r*100,c * 100+50,50,50, "tankOne", Color.GREEN);
							root.getChildren().add(tankOne);
						}
						else
						{
							tankTwo = new Piece(r*100+50,c * 100,50,50, "tankTwo", Color.GREEN);
							root.getChildren().add(tankTwo);
						}
						break;
					default:
						break;
				
					}
				}
			}
		}
		AnimationTimer timer = new AnimationTimer()
		{
			@Override
			public void handle(long now)
			{
				update();
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
			pieces().forEach(s -> {
				switch(s.type)
				{
				case "bullet":
					
					break;
				case "tankOne":
					if(!tankOneCollision)
					{
						if(tankOneMoveRight)
						{
							s.moveRight();
						}
						if(tankOneMoveLeft)
						{
							s.moveLeft();
						}
						if(tankOneMoveUp)
						{
							s.moveUp();
						}
						if(tankOneMoveDown)
						{
							s.moveDown();
						}
					}
					break;
				case "tankTwo":
					if(!tankTwoCollision)
					{
						if(tankTwoMoveRight)
						{
							s.moveRight();
						}
						if(tankTwoMoveLeft)
						{
							s.moveLeft();
						}
						if(tankTwoMoveUp)
						{
							s.moveUp();
						}
						if(tankTwoMoveDown)
						{
							s.moveDown();
						}
					}
					break;
					
				}
			});
			
			root.getChildren().removeIf(n -> {
				Piece s = (Piece) n;
				return s.dead;
			});
			
			if(t > 2) {
				t = 0;
			}
		
	}
	
	private static class Piece extends Rectangle {
		boolean dead = false;
		final String type;
		Piece(int x, int y, int w, int h, String type, Color color)
		{
			super(w,h,color);
			this.type = type;
			setTranslateX(x);
			setTranslateY(y);
		}
		void moveLeft() {
			setTranslateX(getTranslateX() - 2.5);
		}
		void moveRight()
		{
			setTranslateX(getTranslateX() +2.5);
		}
		void moveUp() {
			setTranslateY(getTranslateY() - 2.5);
		}
		void moveDown()
		{
			setTranslateY(getTranslateY() + 2.5);
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
//		primaryStage.setMaxHeight(1000);
//		primaryStage.setMaxWidth(1000);
//		primaryStage.setMinHeight(1000);
//		primaryStage.setMinWidth(1000);
//		primaryStage.setHeight(1000);
//		primaryStage.setWidth(1000);
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
			case SPACE:
//				shoot = true;
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
			case SPACE:
//				shoot = false;
				break;
			
			}
		});
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
