import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class DealerTest {
    BaccaratDealer theDealer;
    @BeforeEach
    void init () {
        theDealer = new BaccaratDealer();
        theDealer.generateDeck();
    }

    @Test
    void testDealerConstructor() { //testing constructor
        assertEquals("BaccaratDealer", theDealer.getClass().getName(), "did not initialize proper BaccaratDealer");
    }

    @Test
    void testGenerateDeckShuffle () {

        Card card = theDealer.deck.get(0); //IF TEST TAILS, RUN AGAIN. DUE TO CHANCE, THE TEST MIGHT FAIL
        assertNotEquals(1, card.value, "card at beginning is still an ace. try test again if failed because the first card CAN be an ace still, though unlikely it is an ace twice");
        assertNotEquals("ace", card.suite, "card at beginning is still an ace of clubs. try test again because the first card CAN be an ace still, though unlikely it is an ace twice");
    }

    @Test
    void testGenerateDeckSize() {

        assertEquals(52, theDealer.deck.size(), "generated deck is valid. the deck size is wrong");
    }


    @Test
    void testDealHand1() { //testing hand size after cards dealt
        ArrayList<Card> hand = theDealer.dealHand();
        assertEquals(2, hand.size(), "method dealHand does not deal 2 cards and return an arraylist of cards");
    }

    @Test
    void testDealHand2() {  //checking deck size after cards dealt
        assertEquals(52, theDealer.deck.size(), "deck is not generated correctly");
        theDealer.dealHand();
        assertEquals(50, theDealer.deck.size(), "after dealing 2 cards, the deck is not the decksize - 2");
    }

    @Test
    void testDrawOne1() { //checking decksize after a combo of 2 and 1 cards dealt
        theDealer.dealHand();
        theDealer.drawOne();
        assertEquals(49, theDealer.deck.size(), "method drawOne does not remove a card from the deck when one card is drawn");
    }

    @Test
    void testDrawOne2() {
        assertNotEquals(theDealer.drawOne(), theDealer.drawOne(), "card drawn is the same when trying to call method drawOne twice");
    }

    @Test
    void testShuffleDeck1() {  //IF TEST TAILS, RUN AGAIN. DUE TO CHANCE, THE TEST MIGHT FAIL
        Card card = theDealer.deck.get(0);
        theDealer.shuffleDeck();
        assertNotEquals(card, theDealer.deck.get(0), "shuffledeck method does not work. the first card is still the same. if this test fails, try running again. slight chance they will be the same");
    }

    @Test
    void testShuffleDeck2() {
        theDealer.shuffleDeck();
        assertEquals(52, theDealer.deck.size(), "after shuffling deck, there are cards added/missing");
    }

    @Test
    void testDeckSize1() {
        assertEquals(52, theDealer.deckSize(), "method does not return correct number");
    }

    @Test
    void testDeckSize2() {
        theDealer.dealHand();
        theDealer.drawOne();
        assertEquals(49, theDealer.deckSize(), "method does not return correct decksize after modifying deck");

    }


}
