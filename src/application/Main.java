package application;
	
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
	private boolean tankOneMoveUp;
	private boolean tankOneMoveRight;
	private boolean tankOneMoveDown;
	private boolean tankOneMoveLeft;
	private boolean tankTwoMoveUp;
	private boolean tankTwoMoveRight;
	private boolean tankTwoMoveDown;
	private boolean tankTwoMoveLeft;
//	private Piece tankOne = new Piece(100,800, 100, 100, "tank", Color.GREEN);
//	private Piece tankTwo = new Piece(800,100, 100, 100, "tank", Color.GREEN);
	public Parent createContent(Stage primaryStage)
	{
		root.setPrefSize(1000, 1000);
//		root.getChildren().add(tankOne);
//		root.getChildren().add(tankTwo);
		for(int r = 0; r < 10; r++)
		{
//			root.getChildren().add(new Piece(r*100,0,100,100, "brick", Color.MAROON));
//			root.getChildren().add(new Piece(r*100,900,100,100, "brick", Color.MAROON));
//
//			root.getChildren().add(new Piece(0,r*100,100,100, "brick", Color.MAROON));
//			root.getChildren().add(new Piece(900,r*100,100,100, "brick", Color.MAROON));
		}
		for(int r = 1; r < 9; r++)
		{
			for(int c = 1; c < 9; c++)
			{
				switch(controller.getSquarePiece(r, c).getType())
				{
				case BRICK:
					root.getChildren().add(new Piece(r*100,c * 100,100,100, "brick", Color.MAROON));
					break;
				case BULLET:
					break;
				case POWERUP:
					root.getChildren().add(new Piece(r*100,c * 100,100,100, "powerup", Color.LIME));
					break;
				case TANK:
					root.getChildren().add(new Piece(r*100,c * 100,100,100, "brick", Color.GREEN));
					break;
				default:
					break;
				
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
//		timer.start();
		return root;
	}
	private void update()
	{
	
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
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
