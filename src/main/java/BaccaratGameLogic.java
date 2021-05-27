import java.util.ArrayList;

public class BaccaratGameLogic {

    public int handTotal(ArrayList<Card> hand) {
        int handTotal = 0;
        for (Card card : hand) { //summing up the card values
            handTotal += card.value;
        }

        return handTotal % 10; //mod 10 to get value we want
    }

    public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
        int playerValue = handTotal(hand1);
        int dealerValue = handTotal(hand2);

        int playerDifference = 9 - playerValue;
        int dealerDifference = 9 - dealerValue;
        if (playerDifference < dealerDifference) { //difference between 9 and player value is less than dealer. player wins
            return "Player";
        }
        if (playerDifference > dealerDifference) { //dealer has lower difference. dealer wins
            return "Banker";
        }
        else {
            return "Draw";
        }
    }

    public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
        int dealerValue = handTotal(hand);

        if (dealerValue <= 2) { //dealer value 2 or less results in drawing a card
            return true;
        }
        else if (dealerValue >= 7) { //dealer value 7 or above results in no draw
            return false;
        }

        else {
            if (dealerValue == 3) {
                if (playerCard == null) { //player did not draw
                    return true;
                }

                if ( playerCard.value == 8) {
                    return false;
                }
                else {
                    return true;
                }
            }//end dealerValue = 3

            else if (dealerValue == 4) {
                if (playerCard == null) { //player did not draw
                    return true;
                }

                if ( playerCard.value == 0 || playerCard.value == 1 || playerCard.value == 8 || playerCard.value == 9){
                    return false;
                }
                else {
                    return true;
                }
            } //end dealerValue = 4

            else if (dealerValue == 5) {
                if (playerCard == null) { //player did not draw
                    return true;
                }

                if ( playerCard.value == 0 || playerCard.value == 1 || playerCard.value == 2 || playerCard.value == 3
                        || playerCard.value == 8 || playerCard.value == 9) {
                    return false;
                }

                else {
                    return true;
                }
            }//end dealerValue = 5

            else  {
                if (playerCard != null) {
                    if (playerCard.value == 6 || playerCard.value == 7) {
                        return true;
                    }

                    return false;
                }

                else { //player did not draw and banker value is 6
                    return false;
                }
            } //end dealerValue = 6

        } //end else

    }

    public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
        int total = handTotal(hand);
        if (total < 6) {
            return true;
        }
        return false;
    }

    public boolean isNatural (ArrayList<Card> hand) {
        int sum = handTotal(hand);
        if (sum == 8 || sum == 9) {
            return true;
        }
        return false;
    }

}
