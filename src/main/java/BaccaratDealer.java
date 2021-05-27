import java.util.ArrayList;
import java.util.Collections;

public class BaccaratDealer {
    public ArrayList<Card> deck;
    public String[] cardSuite = {"clubs" , "diamonds", "spades", "hearts"};
    public int[] cardValue = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

    public void generateDeck() { //setting double layered for loop to populate a deck with all 52 cards
        this.deck = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                this.deck.add(new Card(cardSuite[i], cardValue[j]));
            }
        }
        shuffleDeck();
    }

    public ArrayList<Card> dealHand() { //get the first two cards in deck and then remove the two cards from deck
        ArrayList<Card> hand = new ArrayList<Card>();
        hand.add(deck.get(0));
        hand.add(deck.get(1));
        deck.remove(0);
        deck.remove(0);
        return hand;
    }

    public Card drawOne() {
        Card cardDrawn = deck.get(0);
        deck.remove(0);
        return cardDrawn;
    }

   public void shuffleDeck() {
       Collections.shuffle(deck);
   }

   public int deckSize() {
        return deck.size();
   }






}
