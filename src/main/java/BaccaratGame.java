import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;
import javafx.geometry.Insets;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;

import javafx.util.Duration;

public class BaccaratGame extends Application {

	private Text playerBoard;
	private Text bankerBoard;
	private TextField bet;
	private Button playButton;
	private Button betOnPlayer;
	private Button betOnBanker;
	private Button betOnDraw;
	private BorderPane pane1;
	Scene scene;

	Card cardDrawn;
	Image cardImage;
	String card;
	ArrayList<ImageView> IVList1, IVList2;
	HBox hbox1, hbox2, hbox3;
	Pane pane;
    Insets cardInset = new Insets(45, 0, 0, -100);
	Insets cardInset2 = new Insets(90, 0, 0, -100);
	HashMap<String, Scene> sceneMap;
	PauseTransition pause3sec = new PauseTransition(Duration.seconds(3));
	PauseTransition pauseAfterResult = new PauseTransition(Duration.seconds(1.5));
	PauseTransition pauseAfterPlayerDraw = new PauseTransition(Duration.seconds(2));
	PauseTransition pauseAfterBankerDraw = new PauseTransition(Duration.seconds(3));
	Image bannerImage = new Image("banner.png");
	ImageView bannerIV = new ImageView(bannerImage);
	Text showTotalWinnings;
	boolean bettedOnPlayer = false;
	boolean bettedOnBanker = false;
	boolean bettedOnDraw = false;
	Label resultLabel;
	String resultString;
	Text validInput = new Text("ENTER A VALID AMOUNT, DUMMY");
	MenuBar menuBar = new MenuBar();
	Menu menu = new Menu("Options");
	MenuItem menuItem1 = new MenuItem("Exit");
	MenuItem menuItem2 = new MenuItem("Fresh Start");

	ArrayList<Card> playerHand; // making arraylist of cards for playerhand and bankerhand
	ArrayList<Card> bankerHand;
	BaccaratDealer theDealer; //declaring the dealer and gamelogic variables
	BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	double currentBet;
	double totalWinnings;

	public double evaluateWinnings(){
		String result = gameLogic.whoWon(playerHand, bankerHand);
		if (result == "Player" && bettedOnPlayer == true) { //betted on player and player won
			return currentBet;
		}

		else if (result == "Banker" && bettedOnBanker == true) { //betted on banker and banker won. 5% commission
			return currentBet * 0.95;
		}

		else if (result == "Draw" && bettedOnDraw == true) { //betted on draw and draw happened
			return currentBet * 8;
		}

		else { //betted incorrectly and loses money
			return currentBet * -1;
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		System.out.print("plz give me A");

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		primaryStage.setTitle("*******BACCARAT TIME BABY!!!*********");

		playButton = new Button("PLAY"); //big play button on first scene
		pane = new Pane();

		bannerIV.setFitWidth(800); //settings for baccarat banner. very beautiful
		bannerIV.setFitHeight(100);
		bannerIV.setTranslateX(100);
		bannerIV.setTranslateY(0);

		menuItem1.setOnAction(e->primaryStage.close()); //menu item : close the gui
		menuItem2.setOnAction(e-> { //lambda expression for fresh start menu item
			totalWinnings = 0; //resets totalwinnings

			sceneMap.put("betScene", createBetScene(primaryStage));//overwrites current betScene


			pause3sec.stop(); //stops all of the pause transitions from play scene, in case they are still playing
			pauseAfterPlayerDraw.stop(); //same as above
			pauseAfterBankerDraw.stop();

			primaryStage.setScene(sceneMap.get("betScene"));
		});

		menu.getItems().addAll(menuItem1, menuItem2);
		menuBar.getMenus().add(menu);

		//ADD SCENES TO HASHMAP HERE
		sceneMap = new HashMap<String, Scene>();
		sceneMap.put("betScene", createBetScene(primaryStage));

		//ACTION FOR CLICKING PLAY BUTTON
		playButton.setOnAction(e->  {
			primaryStage.setScene(sceneMap.get("betScene"));
			primaryStage.show(); });

		playButton.setStyle("-fx-background-color: gold;" + "-fx-font-family: 'Britannic Bold' ;" + "-fx-font-size: 60;"); //settings for play button.
		playButton.setMinSize(300,150);
		playButton.setTranslateX(350);
		playButton.setTranslateY(275);
		pane.getChildren().addAll(playButton, bannerIV, menuBar); //adding everything to the pane

		BackgroundImage myBackground = new BackgroundImage(new Image("table.png", 1000, 700, true, true),
		BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		pane.setBackground(new Background(myBackground)); //background settings

		scene = new Scene(pane, 1000,700); //making a scene with the current pane
		sceneMap.put("playButtonScene", scene);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public Scene createBetScene(Stage primaryStage) { //returns betScene scene

		Pane tempPane = new Pane();

		MenuBar menubar2 = new MenuBar();
		Menu tempMenu = new Menu("Options");
		MenuItem menuItemInBet1 = new MenuItem("Exit");
		MenuItem menuItemInBet2 = new MenuItem("Fresh Start");
		tempMenu.getItems().addAll(menuItemInBet1, menuItemInBet2);
		menubar2.getMenus().add(tempMenu);

		menuItemInBet1.setOnAction(e-> primaryStage.close());
		menuItemInBet2.setOnAction(e-> {
			totalWinnings = 0;


			sceneMap.put("betScene", createBetScene(primaryStage));

			primaryStage.setScene(sceneMap.get("betScene"));
		});

		//STUFF FOR BETTING



		//START HANDLER ANON CLASS FOR BETTING BUTTON
		EventHandler<ActionEvent> buttonHandler = event -> {

					validInput.setTranslateX(10);
					validInput.setTranslateY(420);
					validInput.setStyle("-fx-font-family: 'Comic Sans MS';" + "-fx-font-size: 40;" + "-fx-fill: gold;" );

					if(tempPane.getChildren().contains(validInput)) { //IF SOMEONE IS SPAM CLICKING, THIS GETS RID OF CRASHING BY REMOVING THE TEXT
						tempPane.getChildren().remove(validInput);
					}

					if (bet.getText().isEmpty() == true || isDouble(bet.getText()) == false || Double.parseDouble(bet.getText()) <= 0) {
						tempPane.getChildren().addAll(validInput); //displays text that tells player to put in valid bet input

						return;
					}

					else { //bet input is valid
						//below checks for who the player betted on
						if (event.getSource() == betOnPlayer) {
							bettedOnPlayer = true;
							bettedOnBanker = false;
							bettedOnDraw = false;
						}
						 else if (event.getSource() == betOnBanker) {
							bettedOnPlayer = false;
							bettedOnBanker = true;
							bettedOnDraw = false;
						}
						 else {
							bettedOnPlayer = false;
							bettedOnBanker = false;
							bettedOnDraw = true;
							}


						sceneMap.put("playScene", createPlayScene(primaryStage) );
						primaryStage.setScene(sceneMap.get("playScene"));
					}

		}; //END EVENTHANDLER ANON CLASS


		showTotalWinnings = new Text(String.format("Total Winnings: $%.2f", totalWinnings ));
		showTotalWinnings.setTranslateX(10);
		showTotalWinnings.setTranslateY(630);
		showTotalWinnings.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");

		hbox3 = new HBox();

		bet = new TextField();

		betOnPlayer = new Button("Player"); //buttons for betting on player, banker, or draw
		betOnPlayer.setStyle("-fx-background-color: gold;" + "-fx-font-family: 'Britannic Bold' ;");
		betOnPlayer.setOnAction(buttonHandler);

		betOnBanker = new Button("Banker");
		betOnBanker.setStyle("-fx-background-color: gold;" + "-fx-font-family: 'Britannic Bold' ;");
		betOnBanker.setOnAction(buttonHandler);

		betOnDraw  = new Button("Draw");
		betOnDraw.setStyle("-fx-background-color: gold;" + "-fx-font-family: 'Britannic Bold' ;");
		betOnDraw.setOnAction(buttonHandler);

		hbox3.setSpacing(10);

		hbox3.getChildren().addAll(bet, betOnPlayer, betOnBanker, betOnDraw);

		hbox3.setTranslateX(10);
		hbox3.setTranslateY(640);

		//END STUFF FOR BETTING

		tempPane.getChildren().addAll(hbox3, showTotalWinnings, menubar2);

		BackgroundImage tempBackground = new BackgroundImage(new Image("table.png", 1000, 700, true, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		tempPane.setBackground(new Background(tempBackground));

		return new Scene(tempPane, 1000, 700);
	}

	boolean isDouble(String str) { //bool to check for valid bet input
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public Scene createPlayScene(Stage primaryStage) {

		Pane tempPane = new Pane();

			Button playAgainButton = new Button("Play Again?"); //making a playagain button
			playAgainButton.setTranslateX(600);
			playAgainButton.setTranslateY(600);
			playAgainButton.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");
			playAgainButton.setVisible(false);

			playAgainButton.setOnAction(event1-> {primaryStage.setScene(sceneMap.get("betScene")); //getting back to the betscene
			primaryStage.show();});

			currentBet = Double.parseDouble(bet.getText()); //getting currentbet value
			showTotalWinnings = new Text(String.format("Total Winnings: $%.2f", totalWinnings ));
			showTotalWinnings.setTranslateX(10);
			showTotalWinnings.setTranslateY(630);
			showTotalWinnings.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");

			Text showBet = new Text(String.format("Bet Amount: $%.2f", currentBet)); //shows bet amount on gui
			showBet.setTranslateX(10);
			showBet.setTranslateY(600);
			showBet.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");

			theDealer = new BaccaratDealer();
			theDealer.generateDeck();
			playerHand = theDealer.dealHand();
			bankerHand = theDealer.dealHand();

			playerBoard = new Text("Player"); //text to say player. placed above player cards
			playerBoard.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;");
			playerBoard.setTranslateX(80);
			playerBoard.setTranslateY(140);

			bankerBoard = new Text("Banker"); //just a text that says banker
			bankerBoard.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;");
			bankerBoard.setTranslateX(640);
			bankerBoard.setTranslateY(140);

			IVList1 = new ArrayList<>();
			for (Card cards : playerHand) { //CODE TO DISPLAY STARTING PLAYER HAND
				card = cards.name + "_of_" + cards.suite + ".png";
				cardImage = new Image(card);
				ImageView view1 = new ImageView(cardImage);
				view1.setFitHeight(250);
				view1.setFitWidth(250);
				view1.setPreserveRatio(true);
				view1.setSmooth(true);
				view1.setEffect(new DropShadow(80, Color.BLACK));
				IVList1.add(view1); //IVList is an arrayList of imageViews
			}

			IVList2 = new ArrayList<>(); //imageview list
			for (Card cards : bankerHand) { //CODE TO DISPLAY STARTING BANKER HAND
				card = cards.name + "_of_" + cards.suite + ".png";
				cardImage = new Image(card);
				ImageView view1 = new ImageView(cardImage);
				view1.setFitHeight(250);
				view1.setFitWidth(250);
				view1.setPreserveRatio(true);
				view1.setSmooth(true);
				view1.setEffect(new DropShadow(100, Color.BLACK));
				IVList2.add(view1);
			}

			hbox1 = new HBox(); //hbox for player cards
			hbox1.getChildren().addAll(IVList1);
			hbox1.setMargin(IVList1.get(1), cardInset); //making second card overlap with first card to look nice

			hbox2 = new HBox(); //hbox for banker cards
			hbox2.getChildren().addAll(IVList2);
			hbox2.setMargin(IVList2.get(1), cardInset);

			hbox1.setTranslateX(80);
			hbox1.setTranslateY(190);

			hbox2.setTranslateX(640);
			hbox2.setTranslateY(190);

			Text showPlayerValue = new Text("Value: " + gameLogic.handTotal(playerHand)); //text to display player value
			showPlayerValue.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;");
			showPlayerValue.setTranslateX(80);
			showPlayerValue.setTranslateY(180);

			Text showBankerValue = new Text("Value: " + gameLogic.handTotal(bankerHand)); //text to display banker value
			showBankerValue.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;");
			showBankerValue.setTranslateX(640);
			showBankerValue.setTranslateY(180);

			BackgroundImage tempBackground = new BackgroundImage(new Image("table.png", 1000, 700, true, true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
			tempPane.setBackground(new Background(tempBackground)); //setting background

			tempPane.getChildren().addAll(hbox1, hbox2, playerBoard, bankerBoard, showBet, showPlayerValue, showBankerValue, showTotalWinnings, playAgainButton, menuBar);

			pauseAfterResult.setOnFinished(event-> { playAgainButton.setVisible(true); //makes play again button visible
			});

			pauseAfterPlayerDraw.setOnFinished(event2-> { //event handler for banker draws
				bankerHand.add(theDealer.drawOne());
				card = bankerHand.get(2).name + "_of_" + bankerHand.get(2).suite + ".png";
				cardImage = new Image(card);
				ImageView view1 = new ImageView(cardImage);
				view1.setFitHeight(250);
				view1.setFitWidth(250);
				view1.setPreserveRatio(true);
				view1.setSmooth(true);
				view1.setEffect(new DropShadow(80, Color.BLACK));
				IVList2.add(view1); //IVList is an arrayList of imageViews
				hbox2.getChildren().add(IVList2.get(2));
				hbox2.setMargin(IVList2.get(2), cardInset2);
				Text showBankerValue2 = new Text("Value: " + gameLogic.handTotal(bankerHand));
				showBankerValue2.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;");
				showBankerValue2.setTranslateX(640);
				showBankerValue2.setTranslateY(180);
				tempPane.getChildren().remove(showBankerValue);
				tempPane.getChildren().add(showBankerValue2);
				pauseAfterBankerDraw.play();

			});

			pauseAfterBankerDraw.setOnFinished(event3-> {  //LAMBDA FUNCTION TO DETERMINE AND DISPLAY WINNINGS
				totalWinnings = totalWinnings + evaluateWinnings(); //evaluate the winnings after the cards have been drawn
				Text showTotalWinnings2 = new Text(String.format("Total Winnings: $%.2f", totalWinnings ));
				showTotalWinnings2.setTranslateX(10);
				showTotalWinnings2.setTranslateY(630);
				showTotalWinnings2.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");

				resultString = "Player Total: " + gameLogic.handTotal(playerHand) + " Banker Total: " + gameLogic.handTotal(bankerHand) +"\n";
				getResultString();

				resultLabel = new Label(resultString);
				resultLabel.setWrapText(true);
				resultLabel.setTranslateX(260);
				resultLabel.setTranslateY(100);
				resultLabel.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 20;" + "-fx-text-fill: gold;");

				tempPane.getChildren().remove(showTotalWinnings); //update the total winnings that is being shown
				tempPane.getChildren().addAll(showTotalWinnings2, resultLabel);

				sceneMap.put("betScene", createBetScene(primaryStage)); //overwrite current betScene with new one
				pauseAfterResult.play();

			});

			pause3sec.setOnFinished(e-> { //BEGIN LAMBDA pause4sec

				cardDrawn = null; //RESETTING cardDrawn TO NULL BEFORE DETERMINING DRAWS. VERY IMPORTANT. if not reset, it will use cardDrawn from previous match

				if (gameLogic.isNatural(playerHand) == true || gameLogic.isNatural(bankerHand) == true) { //if either player has a natural or both

					totalWinnings = totalWinnings + evaluateWinnings(); //evaluate the winnings after the cards have been drawn
					Text showTotalWinnings2 = new Text(String.format("Total Winnings: $%.2f", totalWinnings ));
					showTotalWinnings2.setTranslateX(10);
					showTotalWinnings2.setTranslateY(630);
					showTotalWinnings2.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 30;" + "-fx-fill: gold;");

					resultString = "Player Total: " + gameLogic.handTotal(playerHand) + " Banker Total: " + gameLogic.handTotal(bankerHand) +"\n";
					getResultString();

					resultLabel = new Label(resultString); //creating a label to display the result text
					resultLabel.setWrapText(true);
					resultLabel.setTranslateX(260);
					resultLabel.setTranslateY(100);
					resultLabel.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 20;" + "-fx-text-fill: gold;");

					tempPane.getChildren().remove(showTotalWinnings);

					tempPane.getChildren().addAll(showTotalWinnings2, resultLabel);

					sceneMap.put("betScene", createBetScene(primaryStage));//creating new betscene with updated winnings
					pauseAfterResult.play();
				} //END IF(NATURAL)

				else {// no natural, must go through card draw process
					if (gameLogic.evaluatePlayerDraw(playerHand) == true) { //if player has to draw a card
						cardDrawn = theDealer.drawOne();
						playerHand.add(cardDrawn);
						card = playerHand.get(2).name + "_of_" + playerHand.get(2).suite + ".png"; //setting up card image for drawn player card
						cardImage = new Image(card);
						ImageView view1 = new ImageView(cardImage);
						view1.setFitHeight(250);
						view1.setFitWidth(250);
						view1.setPreserveRatio(true);
						view1.setSmooth(true);
						view1.setEffect(new DropShadow(80, Color.BLACK));
						IVList1.add(view1); //IVList is an arrayList of imageViews
						hbox1.getChildren().add(IVList1.get(2)); //adding 3rd card image to hbox
						hbox1.setMargin(IVList1.get(2), cardInset2);
						Text showPlayerValue2 = new Text("Value: " + gameLogic.handTotal(playerHand));
						showPlayerValue2.setStyle("-fx-font-family: 'Britannic Bold';" + "-fx-font-size: 40;" + "-fx-fill: gold;"); //updated player value
						showPlayerValue2.setTranslateX(80);
						showPlayerValue2.setTranslateY(180);
						tempPane.getChildren().remove(showPlayerValue);//remove old player value, add new one
						tempPane.getChildren().add(showPlayerValue2);

						if (gameLogic.evaluateBankerDraw(bankerHand, cardDrawn)==true) { //player drew a card AND bank draws a card
							pauseAfterPlayerDraw.play();
						}

						else { //player draws and banker does not draw
							pauseAfterBankerDraw.play();
						}
					}

					else { //player does not draw

						if (gameLogic.evaluateBankerDraw(bankerHand, cardDrawn)==true) { //player does not draw AND BANKER DRAWS
							pauseAfterPlayerDraw.play();
						}

						else{ //nobody draws
							pauseAfterBankerDraw.play();
						}

					}

				}//END ELSE (NO NATURAL)

			}); //END LAMBDA FOR pause4sec

			pause3sec.play();

			return new Scene(tempPane, 1000, 700);

		}

		public void getResultString() {
			if (gameLogic.whoWon(playerHand, bankerHand) == "Player") { //player hand won
				resultString += "Player wins\n";
				if (bettedOnPlayer == true) {
					resultString += "Congrats! You bet on Player! You win!";
				}
				else if (bettedOnBanker == true) {
					resultString += "Sorry, you bet on Banker! You lose, loser!";
				}
				else {
					resultString += "Sorry, you bet on Draw! You lose, loser!";
				}
			}

			else if (gameLogic.whoWon(playerHand, bankerHand) == "Banker") { //banker hand won
				resultString += "Banker wins\n";
				if (bettedOnBanker == true) {
					resultString += "Congrats! You bet on Banker! You win!\n5% commission for betting banker tho :(";
				}
				else if (bettedOnPlayer == true) {
					resultString += "Sorry, you bet on Player! You lose, loser!";
				}
				else  {
					resultString += "Sorry, you bet on Draw! You lose, loser!";
				}
			}

			else { //draw
				resultString += "Draw!\n";
				if (bettedOnDraw == true) {
					resultString += "Congrats! You bet on Draw! You win!\nYou get 8 times the bet amount!!!";
				}
				else if (bettedOnBanker == true) {
					resultString += "Sorry, you bet on Banker! You lose, loser!";
				}
				else {
					resultString += "Sorry, you bet on Player! You lose, loser!";
				}
			}
		}

	}





