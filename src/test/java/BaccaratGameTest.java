import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class BaccaratGameTest {

    BaccaratGameLogic gameLogic; //creating data members that is required
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    boolean bettedOnPlayer; //booleans to see what the player betted on to evaluate winnings
    boolean bettedOnBanker;
    boolean bettedOnDraw;
    double currentBet;

    @BeforeEach
    void init() { //in this test, since its not possible to create a BaccaratGame object, i copy-pasted its evaluateWinnings method to this test file and replicated its data members
        gameLogic = new BaccaratGameLogic();
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
    }

    @Test
    void testEvaluateWinnings1() { //test for correct returned double value when draw occurs
        bettedOnPlayer = false; //these settings required for a drew to happen
        bettedOnBanker = false;
        bettedOnDraw = true;
        currentBet = 10;
        playerHand.add(new Card("clubs", 3 )); //playerhand and bankerhand has same value
        playerHand.add(new Card("clubs", 3 ));
        bankerHand.add(new Card("clubs", 3 ));
        bankerHand.add(new Card("clubs", 3 ));

        assertEquals(80, evaluateWinnings(playerHand, bankerHand), "evaluateWinnings did not return 8 times the winnings");
    }

    @Test
    void testEvaluateWinnings2() { //test for correct returned double value when draw occurs
        bettedOnPlayer = true; //these settings required for a drew to happen
        bettedOnBanker = false;
        bettedOnDraw = false;
        currentBet = 10;
        playerHand.add(new Card("clubs", 3 )); //playerhand and bankerhand has same value
        playerHand.add(new Card("clubs", 3 ));
        bankerHand.add(new Card("clubs", 3 ));
        bankerHand.add(new Card("clubs", 5 ));

        assertEquals(-10, evaluateWinnings(playerHand, bankerHand), "evaluateWinnings did not return the negative bet amount");
        bettedOnBanker = true;
        bettedOnPlayer = false;
        assertEquals(9.5, evaluateWinnings(playerHand, bankerHand), "evaluateWinnings did not return correct when betting on banker and winning");
    }

    private double evaluateWinnings(ArrayList<Card> playerHand, ArrayList<Card> bankerHand){ //same method in BaccaratGame class
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
}
