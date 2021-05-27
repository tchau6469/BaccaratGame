import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;


public class BaccaratGameLogicTest {
    BaccaratGameLogic gameLogic = new BaccaratGameLogic();
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;

    @BeforeEach
    void init() {
        playerHand = new ArrayList<>();
        bankerHand = new ArrayList<>();
    }

    @Test
    void testGameLogicConstructor() {  //testing constructor
        assertEquals("BaccaratGameLogic", gameLogic.getClass().getName(), "constructor did not work. it is not a baccaratgamelogic class");
    }

    @Test
    void testWhoWon1() { //testing regular functionality

        playerHand.add(new Card("clubs", 4));
        playerHand.add(new Card("clubs", 2));

        bankerHand.add(new Card("clubs", 2));
        bankerHand.add(new Card("clubs", 1));
        assertEquals("Player", gameLogic.whoWon(playerHand, bankerHand), "player is supposed to win here. 6 > 3. method SUCKS");

    }

    @Test
    void testWhoWon2() { //testing for natural hands and draws

        playerHand.add(new Card("clubs", 5));
        playerHand.add(new Card("clubs", 4));

        bankerHand.add(new Card("clubs", 4));
        bankerHand.add(new Card("clubs", 4));
        assertEquals("Player", gameLogic.whoWon(playerHand, bankerHand), "player is supposed to win here. 9 > 8. even tho both are natural, 9 > 8");
        bankerHand.remove(1);
        bankerHand.add(new Card("clubs", 5));
        assertEquals("Draw", gameLogic.whoWon(playerHand, bankerHand), "string returned supposed to be draw. both are natural 9's, so its a draw");
    }

    @Test
    void testHandTotal1() {
        playerHand.add(new Card("clubs", 5));
        playerHand.add(new Card("clubs", 4));
        assertEquals(9, gameLogic.handTotal(playerHand),"method does not work. does not compute total correctly");
    }

    @Test
    void testHandTotal2() { //testing  for handtotal > 9
        playerHand.add(new Card("clubs", 5));
        playerHand.add(new Card("clubs", 4));
        playerHand.add(new Card("clubs", 5));
        assertEquals(4, gameLogic.handTotal(playerHand),"method does not get rid of most significant int if value exceeds 9. git gud");

    }

    @Test
    void testEvaluateBankerDraw1() { //checking for null card passed as parameter
        bankerHand.add(new Card("clubs", 2));
        bankerHand.add(new Card("clubs", 2));
        assertEquals(true, gameLogic.evaluateBankerDraw(bankerHand, null), "method does not check take into account that card parameter is null. if handtotal of banker is 4 and player did not draw, banker should draw" );
    }

    @Test
    void testEvaluateBankerDraw2() {
        bankerHand.add(new Card("clubs", 2));
        bankerHand.add(new Card("clubs", 2));
        assertEquals(false, gameLogic.evaluateBankerDraw(bankerHand, new Card("clubs", 0)), "incorrect logic if banker draws or not. check chart again" );
    }

    @Test
    void testEvaluatePlayerDraw1() { //checking where the cutoff is
        playerHand.add(new Card("clubs", 5));
        playerHand.add(new Card("clubs", 1));
        assertEquals(false, gameLogic.evaluatePlayerDraw(playerHand), "player does not draw card at handtotal 6. it is NOT INCLUSIVE" );
    }

    @Test
    void testEvaluatePlayerDraw2() {
        playerHand.add(new Card("clubs", 7));
        playerHand.add(new Card("clubs", 1));
        assertEquals(false, gameLogic.evaluatePlayerDraw(playerHand), "player does not draw card at handtotal 7" );
        playerHand = new ArrayList<>();
        playerHand.add(new Card("clubs", 2));
        playerHand.add(new Card("clubs", 1));
        assertEquals(true, gameLogic.evaluatePlayerDraw(playerHand), "player does draw at handtotal 3" );
    }

}
