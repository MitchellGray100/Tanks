package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import Controller.Controller;
import Controller.ControllerImpl;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import piece.PowerUp;
import piece.Tank;

public class Main extends Application {
	private static final Integer STARTTIME = 15;
	private final double BASE_TANK_SPEED = 2.5;
	private final double BASE_BULLET_SPEED = 7;
	private Controller controller = new ControllerImpl();
	Scene scene;
	private Pane root = new Pane();
	private Piece tankOne;
	private Piece tankTwo;
	private Piece powerUp;
	private LinkedList<Piece> brickList = new LinkedList<Piece>();
	private boolean countDownOver = false;
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
	private boolean tankOneMoveUpHeld;
	private boolean tankOneMoveRightHeld;
	private boolean tankOneMoveDownHeld;
	private boolean tankOneMoveLeftHeld;
	private boolean tankTwoMoveUpHeld;
	private boolean tankTwoMoveRightHeld;
	private boolean tankTwoMoveDownHeld;
	private boolean tankTwoMoveLeftHeld;
	private boolean twoPlayers;
	private boolean startGameBoolean = false;
	private boolean onTitleScreen = true;
	private boolean muted = false;
	private double t = 0;
	private double tankOneBulletTimer = 1;
	private double tankTwoBulletTimer = 1;
	private Text gameOverText = new Text();
	private Image tank;
	private Image bullet;
	private Image bullets;
	private Image wheel;
	private Image rocket;
	private Image pileOfTires;
	private Image shield;
	private Image shieldedTank;
	private Image titleScreenNoButton;
	private Image gif3;
	private Image gif2;
	private Image gif1;
	private String gifSelector;
	private AnimationTimer animationTimer;
	private Timer gifTimer;
	private Media powerUpSound;
	private Media tankShootSound;
	private Media tankExplosionSound;
	private Media musicSound;
	private Media countDownLowSound;
	private Media countDownHighSound;
	private Media buttonSound;
	private MediaPlayer powerUpPlayer;
	private MediaPlayer tankOneShootPlayer;
	private MediaPlayer tankTwoShootPlayer;
	private MediaPlayer tankExplosionPlayer;
	private MediaPlayer musicPlayer;
	private MediaPlayer countDownLowPlayer;
	private MediaPlayer countDownHighPlayer;
	private MediaPlayer buttonPlayer;
	ArrayList<Character> playerOneControls = new ArrayList<Character>();
	ArrayList<Character> playerTwoControls = new ArrayList<Character>();

	public Parent createTitleScene(Stage primaryStage) {
		Pane titleScreen = new Pane();
		ImageView titleScreenImage = new ImageView(titleScreenNoButton);
		TitleButton titleButton = new TitleButton(0, primaryStage);
		titleButton.setTranslateX(500);
		titleButton.setTranslateY(500);
		titleButton.setText("Two Players");
		TitleButton titleButton2 = new TitleButton(1, primaryStage);
		titleButton2.setTranslateX(515);
		titleButton2.setTranslateY(750);
		titleButton2.setText("One Player");
		titleButton2.setScaleX(1.08);

		titleScreen.getChildren().addAll(titleScreenImage, titleButton, titleButton2);
		return titleScreen;
	}

	private class TitleButton extends Button {
		int button;

		public TitleButton(int button, Stage primaryStage) {
			this.setFont(new Font(72));
			this.button = button;
			this.setStyle("-fx-focus-color: #093f03;");
			this.setOnMouseClicked(event -> {
				buttonPlayer.stop();
				buttonPlayer.setMute(false);
				buttonPlayer.play();
				onTitleScreen = false;
				if (button == 0) {
					twoPlayers = true;
				} else if (button == 1) {
					twoPlayers = false;
				}

				scene.setRoot(createContent(primaryStage));
				primaryStage.setScene(scene);
				primaryStage.show();
			});
			this.setOnKeyReleased(event -> {
				switch (event.getCode()) {
				case SPACE:
					buttonPlayer.stop();
					buttonPlayer.setMute(false);
					buttonPlayer.play();
					onTitleScreen = false;
					if (button == 0) {
						twoPlayers = true;
					} else if (button == 1) {
						twoPlayers = false;
					}

					scene.setRoot(createContent(primaryStage));
					primaryStage.setScene(scene);
					primaryStage.show();
				}
			});
		}
	}

	public Parent createContent(Stage primaryStage) {
		gameOverText.setText("");
		gameOverText.setTextAlignment(TextAlignment.CENTER);
		gameOverText.setFont(new Font(50));
		gameOverText.setFill(Color.YELLOW);
		gameOverText.setStroke(Color.BLACK);
		gameOverText.setTranslateX(300);
		gameOverText.setTranslateY(450);

		MediaPlayer tempSound = new MediaPlayer(countDownLowSound);
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				if (controller.getSquarePiece(r, c) != null) {
					switch (controller.getSquarePiece(r, c).getType()) {
					case BRICK:
						Piece temp = new Piece(r * 100, c * 100, 100, 100, "brick", pileOfTires, r, c);
						brickList.add(temp);
						root.getChildren().add(temp);
						break;
					case BULLET:
						break;
					case POWERUP:
						powerUp = new Piece(r * 100 + 25, c * 100 + 25, 50, 50, "powerup", null, r, c);
						root.getChildren().add(powerUp);
						break;
					case TANK:
						if (controller.getSquarePiece(r, c).getPlayer() == piece.Piece.Player.ONE) {
							tankOne = new Piece(r * 100, c * 100 + 50, 50, 50, "tankOne", tank, r, c);
							root.getChildren().add(tankOne);
						} else {
							tankTwo = new Piece(r * 100 + 50, c * 100, 50, 50, "tankTwo", tank, r, c);
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

		ImageView gifView = new ImageView(gif3);
		gifView.setTranslateX(400);
		gifView.setTranslateY(400);
		gifView.setScaleX(4);
		gifView.setScaleY(4);
		gifTimer = new Timer();
		tempSound.play();
		TimerTask playAnimation2 = new TimerTask() {
			@Override
			public void run() {
				gifView.setImage(gif2);
				tempSound.stop();
				tempSound.play();

			}

		};
		TimerTask playAnimation1 = new TimerTask() {
			@Override
			public void run() {
				countDownHighPlayer.stop();
				gifView.setImage(gif1);
				tempSound.stop();
				tempSound.play();
			}
		};

		TimerTask startGame = new TimerTask() {

			@Override
			public void run() {
				tempSound.stop();
				gifView.setImage(null);
				countDownHighPlayer.play();
				startGameBoolean = true;
				powerUpPlayer.stop();
				countDownLowPlayer.stop();
				powerUpPlayer.setMute(false);
				countDownLowPlayer.setMute(false);
				countDownHighPlayer.setMute(false);

				tempSound.dispose();

			}

		};
		tankTwo.setRotate(180);
		root.getChildren().add(gifView);
		gifTimer.schedule(playAnimation2, 1000);
		gifTimer.schedule(playAnimation1, 2000);
		gifTimer.schedule(startGame, 3000);

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (gameOverText.getText().equals("")) {
					if (startGameBoolean) {
						if (root.getChildren().contains(gifView)) {
							root.getChildren().remove(gifView);
						}

						update();
					}
				} else {

					stop();
				}
			}
		};
		animationTimer.start();

		return root;
	}

	private List<Piece> pieces() {
		return root.getChildren().stream().map(n -> (Piece) n).collect(Collectors.toList());
	}

	private void update() {
		musicPlayer.play();
		t += .016;
		if (tankTwo.dead || tankOne.dead) {
			if (!root.getChildren().contains(gameOverText))
				root.getChildren().add(gameOverText);
			if (tankTwo.dead) {

				gameOverText.setText("Tank One Won!\nPress SPACE to restart");
			} else {
				gameOverText.setText("Tank Two Won!\nPress SPACE to restart");
			}
		}
		if (!tankTwo.dead && !tankOne.dead) {
			pieces().forEach(s -> {

				switch (s.type) {
				case "tankOneBullet":
					for (Piece piece : brickList) {
						if (s.getBoundsInParent().intersects(piece.getBoundsInParent())) {
							s.dead = true;
						}
					}
					if (s.getBoundsInParent().intersects(tankTwo.getBoundsInParent())) {
						tankExplosionPlayer.stop();
						tankExplosionPlayer.play();
						if (((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).hasShield()) {
							((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShield(false);
							tankTwo.imageView.setImage(tank);
						} else {
							tankTwo.dead = true;
						}
						s.dead = true;
					}
					switch (s.direction) {
					case DOWN:
						s.moveDown();

						s.setRotate(180);
						break;
					case LEFT:
						s.moveLeft();
						s.setRotate(270);
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
					if (s.getBoundsInParent().intersects(tankOne.getBoundsInParent())) {
						tankExplosionPlayer.stop();
						tankExplosionPlayer.play();
						if (((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).hasShield()) {
							((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).setShield(false);
							tankOne.imageView.setImage(tank);
						} else {
							tankOne.dead = true;
						}
						s.dead = true;
					}
					for (Piece piece : brickList) {

						if (s.getBoundsInParent().intersects(piece.getBoundsInParent())) {
							s.dead = true;
						}

					}
					switch (s.direction) {
					case DOWN:
						s.moveDown();

						s.setRotate(180);
						break;
					case LEFT:
						s.moveLeft();
						s.setRotate(270);
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
					tankOneMoveLeft = false;
					tankOneMoveRight = false;
					tankOneMoveUp = false;
					tankOneMoveDown = false;
					if (playerOneControls.size() > 0) {
						for (Character chars : playerOneControls) {
							System.out.print((Character) chars);
						}
						System.out.println();
						switch (playerOneControls.get(playerOneControls.size() - 1)) {
						case 'a':
							tankOneMoveLeft = true;
							break;
						case 'd':
							tankOneMoveRight = true;
							break;
						case 'w':
							tankOneMoveUp = true;
							break;
						case 's':
							tankOneMoveDown = true;
						}
					}
					tankOneBulletTimer -= .01
							* ((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).getShootSpeedMultiplier();
					if (tankOneBulletTimer < 0) {
						tankOneBulletTimer = 0;
					}
					if (tankOneShoot) {
						shoot(tankOne);
					}

					if (tankOneMoveRight) {
						if (!tankOneMoveUp && !tankOneMoveDown) {
							controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.RIGHT);
							for (Piece piece : brickList) {
								if (tankOne.getBoundsInParent().intersects(piece.r * 100, piece.c * 100 + 5, 10, 90)) {
									s.moveLeft();
								}
							}
							s.moveRight();
						}
						if (tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX(), tankTwo.getTranslateY() + 5,
								10, 40)) {
							s.moveLeft();
						}
					}
					if (tankOneMoveLeft) {
						if (!tankOneMoveUp && !tankOneMoveDown) {
							controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.LEFT);
							for (Piece piece : brickList) {
								if (tankOne.getBoundsInParent().intersects(piece.r * 100 + 90, piece.c * 100 + 5, 10,
										90)) {
									s.moveRight();
								}
							}
							if (tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX() + 40,
									tankTwo.getTranslateY() + 5, 10, 40)) {
								s.moveRight();
							}
							s.moveLeft();
						}
					}
					if (tankOneMoveUp) {
//							if(!tankOneMoveLeft && !tankOneMoveRight)
						{
							controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.UP);
							for (Piece piece : brickList) {
								if (tankOne.getBoundsInParent().intersects(piece.r * 100 + 5, piece.c * 100 + 90, 90,
										10)) {
									s.moveDown();
								}
							}
							if (tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX() + 5,
									tankTwo.getTranslateY() + 40, 40, 10)) {
								s.moveDown();
							}
							s.moveUp();
						}
					}
					if (tankOneMoveDown) {
//							if(!tankOneMoveLeft && !tankOneMoveRight)
						{
							controller.getSquarePiece(tankOne.r, tankOne.c).setDirection(piece.Piece.Direction.DOWN);
							for (Piece piece : brickList) {
								if (tankOne.getBoundsInParent().intersects(piece.r * 100 + 5, piece.c * 100, 90, 10)) {
									s.moveUp();
								}
							}
							if (tankOne.getBoundsInParent().intersects(tankTwo.getTranslateX() + 5,
									tankTwo.getTranslateY(), 40, 10)) {
								s.moveUp();
							}
							s.moveDown();
						}
					}
					switch ((controller.getSquarePiece(tankOne.r, tankOne.c)).getDirection()) {
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

					tankTwoMoveLeft = false;
					tankTwoMoveRight = false;
					tankTwoMoveUp = false;
					tankTwoMoveDown = false;
					if (playerTwoControls.size() > 0) {

						switch (playerTwoControls.get(playerTwoControls.size() - 1)) {
						case 'j':
							tankTwoMoveLeft = true;
							break;
						case 'l':
							tankTwoMoveRight = true;
							break;
						case 'i':
							tankTwoMoveUp = true;
							break;
						case 'k':
							tankTwoMoveDown = true;
						}
					}
					tankTwoBulletTimer -= .01
							* ((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).getShootSpeedMultiplier();
					if (tankTwoBulletTimer < 0) {
						tankTwoBulletTimer = 0;
					}
					if (tankTwoShoot) {
						shoot(tankTwo);
					}
					if (tankTwoMoveRight) {

						tankTwoMoveUp = false;
						tankTwoMoveDown = false;
						controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.RIGHT);
						for (Piece piece : brickList) {
							if (tankTwo.getBoundsInParent().intersects(piece.r * 100, piece.c * 100 + 5, 10, 90)) {
								s.moveLeft();
							}
						}
						if (tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX(), tankOne.getTranslateY() + 5,
								10, 40)) {
							s.moveLeft();
						}
						s.moveRight();

					}
					if (tankTwoMoveLeft) {
						tankTwoMoveUp = false;
						tankTwoMoveDown = false;
						controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.LEFT);
						for (Piece piece : brickList) {
							if (tankTwo.getBoundsInParent().intersects(piece.r * 100 + 90, piece.c * 100 + 5, 10, 90)) {
								s.moveRight();
							}
						}
						if (tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX() + 40,
								tankOne.getTranslateY() + 5, 10, 40)) {
							s.moveRight();
						}
						s.moveLeft();

					}
					if (tankTwoMoveUp) {
//						
						tankTwoMoveLeft = false;
						tankTwoMoveRight = false;
						controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.UP);
						for (Piece piece : brickList) {
							if (tankTwo.getBoundsInParent().intersects(piece.r * 100 + 5, piece.c * 100 + 90, 90, 10)) {
								s.moveDown();
							}
						}
						if (tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX() + 5,
								tankOne.getTranslateY() + 40, 40, 10)) {
							s.moveDown();
						}
						s.moveUp();

					}
					if (tankTwoMoveDown) {
						tankTwoMoveLeft = false;
						tankTwoMoveRight = false;
						controller.getSquarePiece(tankTwo.r, tankTwo.c).setDirection(piece.Piece.Direction.DOWN);
						for (Piece piece : brickList) {
							if (tankTwo.getBoundsInParent().intersects(piece.r * 100 + 5, piece.c * 100, 90, 10)) {
								s.moveUp();
							}
						}
						if (tankTwo.getBoundsInParent().intersects(tankOne.getTranslateX() + 5, tankOne.getTranslateY(),
								40, 10)) {
							s.moveUp();
						}
						s.moveDown();

					}
					if (!twoPlayers) {
						tankTwoMoveUp = false;
						tankTwoMoveDown = false;
						tankTwoMoveLeft = false;
						tankTwoMoveRight = false;
						switch (controller.getAIMove(tankTwo.getTranslateX() + 25, tankTwo.getTranslateY() + 25,
								tankOne.getTranslateX(), tankOne.getTranslateY())) {
						case UP:
							if ((tankTwo.getTranslateX() % 100 <= 75 && tankTwo.getTranslateX() % 100 >= 50)
									|| (tankTwo.getTranslateX() % 100 >= 25 && tankTwo.getTranslateX() % 100 <= 50)) {
								tankTwoMoveLeft = true;
//								System.out.println("UP DEBUG UP");
							} else {
//								System.out.println("TranslateX = " + tankTwo.getTranslateX());
//								System.out.println("TranslateY = " + tankTwo.getTranslateY());
								tankTwoMoveUp = true;
							}
							break;
						case DOWN:
							if ((tankTwo.getTranslateX() % 100 <= 75 && tankTwo.getTranslateX() % 100 >= 50)
									|| (tankTwo.getTranslateX() % 100 >= 25 && tankTwo.getTranslateX() % 100 <= 50)) {
								tankTwoMoveLeft = true;
//								System.out.println("DOWN DEBUG DOWN");
							} else {
//								System.out.println("TranslateX = " + tankTwo.getTranslateX());
//								System.out.println("TranslateY = " + tankTwo.getTranslateY());
								tankTwoMoveDown = true;
							}
							break;
						case LEFT:
							if (tankTwo.getTranslateY() % 100 >= 75 && tankTwo.getTranslateY() % 100 <= 100
									|| (tankTwo.getTranslateY() % 100 >= 0 && tankTwo.getTranslateY() % 100 <= 25)) {
								tankTwoMoveDown = true;
//								System.out.println("LEFT DEBUG LEFT");
							} else {
//								System.out.println("TranslateX = " + tankTwo.getTranslateX());
//								System.out.println("TranslateY = " + tankTwo.getTranslateY());
								tankTwoMoveLeft = true;
							}
							break;
						case RIGHT:
							if (tankTwo.getTranslateY() % 100 >= 75 && tankTwo.getTranslateY() % 100 <= 100
									|| (tankTwo.getTranslateY() % 100 >= 0 && tankTwo.getTranslateY() % 100 <= 25)) {
//								System.out.println("RIGHT DEBUG RIGHT");
								tankTwoMoveDown = true;
							} else {
//								System.out.println("TranslateX = " + tankTwo.getTranslateX());
//								System.out.println("TranslateY = " + tankTwo.getTranslateY());
								tankTwoMoveRight = true;
							}
							break;
						case NONE:
							break;
						default:

						}
						shoot(tankTwo);
					}
					switch (controller.getSquarePiece(tankTwo.r, tankTwo.c).getDirection()) {
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
			});
		}

		if (tankTwo.getBoundsInParent().intersects(powerUp.getBoundsInParent())) {
			if (!powerUp.dead) {
				powerUpPlayer.play();
				powerUp.dead = true;
				switch (((PowerUp) controller.getSquarePiece(powerUp.r, powerUp.c)).getPowerUpType()) {
				case FASTBULLET:
					((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).setBulletSpeedMultiplier(1.5);
					break;
				case FASTSHOOT:
					((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShootSpeedMultiplier(1.5);
					break;
				case FASTSPEED:
					((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).setTankSpeedMultiplier(1.5);
					break;
				case SHIELD:
					((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).setShield(true);
					tankTwo.imageView.setImage(shieldedTank);
					break;
				default:
					break;
				}
			}

		}
		if (tankOne.getBoundsInParent().intersects(powerUp.getBoundsInParent())) {
			if (!powerUp.dead) {
				powerUpPlayer.play();
				powerUp.dead = true;
				switch (((PowerUp) controller.getSquarePiece(powerUp.r, powerUp.c)).getPowerUpType()) {
				case FASTBULLET:
					((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).setBulletSpeedMultiplier(1.5);
					break;
				case FASTSHOOT:
					((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).setShootSpeedMultiplier(1.5);
					break;
				case FASTSPEED:
					((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).setTankSpeedMultiplier(1.5);
					break;
				case SHIELD:
					((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).setShield(true);
					tankOne.imageView.setImage(shieldedTank);
					break;
				default:
					break;
				}

			}
		}

		if (gameOverText.getText().equals("")) {
			root.getChildren().removeIf(n -> {
				Piece s = (Piece) n;
				return s.dead;
			});
		}
		if (t > 2) {
			t = 0;
		}

	}

	private void shoot(Piece who) {
		if (who.type.equals("tankOne")) {
			if (tankOneBulletTimer < 1) {
				tankOneShootPlayer.stop();
				tankOneShootPlayer.play();
				tankOneBulletTimer = 1.4;

				Piece s = new Piece((int) who.getTranslateX() + 18, (int) who.getTranslateY() + 10, 5, 20,
						who.type + "Bullet", bullet, 0, 0);
				s.direction = controller.getSquarePiece(who.r, who.c).getDirection();
				root.getChildren().add(s);
			}
		} else if (tankTwoBulletTimer < 1) {
			tankTwoShootPlayer.stop();
			tankTwoShootPlayer.play();
			tankTwoBulletTimer = 1.4;

			Piece s = new Piece((int) who.getTranslateX() + 18, (int) who.getTranslateY() + 10, 5, 20,
					who.type + "Bullet", bullet, 0, 0);
			s.direction = controller.getSquarePiece(who.r, who.c).getDirection();
			root.getChildren().add(s);
		}

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {

			}

		};
	}

	public class Piece extends StackPane {
		boolean dead = false;
		final String type;
//		Rectangle border;
//		Rectangle indication;
		ImageView imageView;
		piece.Piece.Direction direction;
		int r;
		int c;

		Piece(int x, int y, int w, int h, String type, Image image, int r, int c) {
			imageView = new ImageView();
			imageView.setImage(image);
//			indication = new Rectangle(10,5,Color.BLACK);
//			indication.setTranslateY(this.getTranslateY()-25);
//			border = new Rectangle(w,h,color);
			getChildren().addAll(imageView);
			direction = controller.getSquarePiece(r, c).getDirection();
			if (type.equals("tankOne") || type.equals("tankTwo")) {

				imageView.setImage(tank);
			}
			if (type.equals("powerup")) {
				imageView.setScaleX(2);
				imageView.setScaleY(2);
				switch (((PowerUp) controller.getSquarePiece(r, c)).getPowerUpType()) {
				case FASTBULLET:
					imageView.setImage(rocket);
					break;
				case FASTSHOOT:
					imageView.setImage(bullets);
					break;
				case FASTSPEED:
					imageView.setImage(wheel);
					break;
				case SHIELD:
					imageView.setImage(shield);
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
			if (type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateX(getTranslateX()
						- (BASE_TANK_SPEED * ((Tank) controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if (type.equals("tankOneBullet"))
				setTranslateX(getTranslateX() - BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if (type.equals("tankTwoBullet"))
				setTranslateX(getTranslateX() - BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}

		void moveRight() {
			if (type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateX(getTranslateX()
						+ (BASE_TANK_SPEED * ((Tank) controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if (type.equals("tankOneBullet"))
				setTranslateX(getTranslateX() + BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if (type.equals("tankTwoBullet"))
				setTranslateX(getTranslateX() + BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}

		void moveUp() {
			if (type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateY(getTranslateY()
						- (BASE_TANK_SPEED * ((Tank) controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if (type.equals("tankOneBullet"))
				setTranslateY(getTranslateY() - BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if (type.equals("tankTwoBullet"))
				setTranslateY(getTranslateY() - BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());

		}

		void moveDown() {
			if (type.equals("tankOne") || type.equals("tankTwo"))
				setTranslateY(getTranslateY()
						+ (BASE_TANK_SPEED * ((Tank) controller.getSquarePiece(r, c)).getTankSpeedMultiplier()));
			else if (type.equals("tankOneBullet"))
				setTranslateY(getTranslateY() + BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankOne.r, tankOne.c)).getBulletSpeedMultiplier());
			else if (type.equals("tankTwoBullet"))
				setTranslateY(getTranslateY() + BASE_BULLET_SPEED
						* ((Tank) controller.getSquarePiece(tankTwo.r, tankTwo.c)).getBulletSpeedMultiplier());
		}
	}

	@Override
	public void start(Stage primaryStage) throws FileNotFoundException {

		titleScreenNoButton = new Image(new FileInputStream("src/images/TitleScreenNoButton.png"));
		pileOfTires = new Image(new FileInputStream("src/images/PileOfTires.png"));
		bullet = new Image(new FileInputStream("src/images/Bullet.png"));
		bullets = new Image(new FileInputStream("src/images/Bullets.png"));
		rocket = new Image(new FileInputStream("src/images/Rocket.png"));
		shield = new Image(new FileInputStream("src/images/Shield.png"));
		wheel = new Image(new FileInputStream("src/images/Wheel.png"));
		tank = new Image(new FileInputStream("src/images/Tank.png"));
		shieldedTank = new Image(new FileInputStream("src/images/ShieldedTank.png"));
		gif3 = new Image(new FileInputStream("src/images/3.gif"));
		gif2 = new Image(new FileInputStream("src/images/2.gif"));
		gif1 = new Image(new FileInputStream("src/images/1.gif"));
		powerUpSound = new Media(new File("src/images/PowerUpSound.mp3").toURI().toString());
		tankExplosionSound = new Media(new File("src/images/TankExplosion.mp3").toURI().toString());
		tankShootSound = new Media(new File("src/images/TankShoot.mp3").toURI().toString());
		musicSound = new Media(new File("src/images/BlueSky.mp3").toURI().toString());
		countDownLowSound = new Media(new File("src/images/CountDownLow.mp3").toURI().toString());
		countDownHighSound = new Media(new File("src/images/CountDownHigh.mp3").toURI().toString());
		buttonSound = new Media(new File("src/images/Button.mp3").toURI().toString());
		root.setCache(true);

		powerUpPlayer = new MediaPlayer(powerUpSound);
		tankExplosionPlayer = new MediaPlayer(tankExplosionSound);
		tankOneShootPlayer = new MediaPlayer(tankShootSound);
		tankTwoShootPlayer = new MediaPlayer(tankShootSound);
		musicPlayer = new MediaPlayer(musicSound);
		musicPlayer.setVolume(.5);
		countDownLowPlayer = new MediaPlayer(countDownLowSound);
		countDownHighPlayer = new MediaPlayer(countDownHighSound);
		buttonPlayer = new MediaPlayer(buttonSound);
		powerUpPlayer.play();
		buttonPlayer.play();
		countDownLowPlayer.play();
		countDownHighPlayer.play();
		powerUpPlayer.setMute(true);
		buttonPlayer.setMute(true);
		countDownLowPlayer.setMute(true);
		countDownHighPlayer.setMute(true);
		musicPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				musicPlayer.stop();
				musicPlayer.play();
			}
		});
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.getIcons().add(tank);
		// blackKnightImage = new Image(new FileInputStream("src/Black Knight.png"));
		// primaryStage.getIcons().add(blackKnightImage);

		// titleScene = new Scene(createTitleContent(primaryStage));

		scene = new Scene(createTitleScene(primaryStage));
		primaryStage.setScene(scene);
		primaryStage.show();
		// primaryStage.setScene(titleScene);
		primaryStage.setMaxHeight(1000);
		primaryStage.setMaxWidth(1000);
		primaryStage.setMinHeight(1000);
		primaryStage.setMinWidth(1000);
		primaryStage.setHeight(1000);
		primaryStage.setWidth(1000);
		primaryStage.setTitle("Tanks 😎");
		scene.setOnKeyPressed(e -> {

			switch (e.getCode()) {
			case A:
				if (!playerOneControls.contains((Character) 'a')) {
					playerOneControls.add((Character) 'a');
				}
				break;
			case D:
				if (!playerOneControls.contains((Character) 'd')) {
					playerOneControls.add((Character) 'd');
				}
				break;
			case W:
				if (!playerOneControls.contains((Character) 'w')) {
					playerOneControls.add((Character) 'w');
				}
				break;
			case S:
				if (!playerOneControls.contains((Character) 's')) {
					playerOneControls.add((Character) 's');
				}
				break;
			case LEFT:
				if (!playerTwoControls.contains((Character) 'j')) {
					playerTwoControls.add((Character) 'j');
				}
				break;
			case RIGHT:
				if (!playerTwoControls.contains((Character) 'l')) {
					playerTwoControls.add((Character) 'l');
				}
				break;
			case UP:
				if (!playerTwoControls.contains((Character) 'i')) {
					playerTwoControls.add((Character) 'i');
				}
				break;
			case DOWN:
				if (!playerTwoControls.contains((Character) 'k')) {
					playerTwoControls.add((Character) 'k');
				}
				break;
			case C:
				tankOneShoot = true;
				break;
			case PERIOD:
				if (twoPlayers)
					tankTwoShoot = true;
				break;
			}
		});
		scene.setOnKeyReleased(e -> {

			switch (e.getCode()) {
			case A:
				playerOneControls.remove((Character) 'a');
				break;
			case D:
				playerOneControls.remove((Character) 'd');
				break;
			case W:
				playerOneControls.remove((Character) 'w');
				break;
			case S:
				playerOneControls.remove((Character) 's');
				break;
			case LEFT:
				if (twoPlayers) {
					playerTwoControls.remove((Character) 'j');
				}
				break;
			case RIGHT:
				if (twoPlayers) {
					playerTwoControls.remove((Character) 'l');
				}
				break;
			case UP:
				if (twoPlayers) {
					playerTwoControls.remove((Character) 'i');
				}
				break;
			case DOWN:
				if (twoPlayers) {
					playerTwoControls.remove((Character) 'k');
				}
				break;
			case C:
				tankOneShoot = false;
				break;
			case PERIOD:
				if (twoPlayers)
					tankTwoShoot = false;
				break;
			case SPACE:
				if (tankOne.dead || tankTwo.dead) {

					powerUpPlayer.stop();
					tankExplosionPlayer.stop();
					tankOneShootPlayer.stop();
					tankTwoShootPlayer.stop();
					countDownLowPlayer.stop();
					countDownHighPlayer.stop();
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
					startGameBoolean = false;
					t = 0;
					tankOneBulletTimer = 1;
					tankTwoBulletTimer = 1;
					gifTimer.cancel();
					animationTimer.stop();
					root = new Pane();
					gameOverText.setText("");
					controller = new ControllerImpl();
					brickList = new LinkedList<Piece>();
					scene.setRoot((createContent(primaryStage)));
				}
				break;

			case ESCAPE:

				tankExplosionPlayer.stop();
				powerUpPlayer.stop();
				tankOneShootPlayer.stop();
				tankTwoShootPlayer.stop();
				countDownLowPlayer.stop();
				countDownHighPlayer.stop();
				buttonPlayer.stop();
				if (onTitleScreen) {
					primaryStage.close();
					Platform.exit();
					System.exit(0);
				}
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
				startGameBoolean = false;
				t = 0;
				tankOneBulletTimer = 1;
				tankTwoBulletTimer = 1;
				gifTimer.cancel();
				animationTimer.stop();
				gameOverText.setText("");
				root = new Pane();
				controller = new ControllerImpl();
				brickList = new LinkedList<Piece>();
				onTitleScreen = true;
				scene.setRoot((createTitleScene(primaryStage)));
				break;
			}

		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}
