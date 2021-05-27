public class Card {
    public String suite;
    public int value;
    public String name;

    public Card(String theSuite, int theValue) {
        suite = theSuite;
        value = theValue;
        //naming the cards based on value
        if (value == 1) { name = "ace"; } //ace
        if (value == 2) { name = "2"; }
        if (value == 3) { name = "3"; }
        if (value == 4) { name = "4"; }
        if (value == 5) { name = "5"; }
        if (value == 6) { name = "6"; }
        if (value == 7) { name = "7"; }
        if (value == 8) { name = "8"; }
        if (value == 9) { name = "9"; }
        if (value == 10) { name = "10"; value = 0; }
        if (value == 11) { name = "jack"; value = 0; } // jack
        if (value == 12) { name = "queen"; value = 0; } //queen
        if (value == 13) { name = "king"; value = 0; } //king


    }




}
