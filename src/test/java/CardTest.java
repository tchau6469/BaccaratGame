import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CardTest {
    @Test
    void testCardConstructor() { //testing constructor
        Card card = new Card("clubs", 1);
        assertEquals("Card", card.getClass().getName(), "constructor does not make a Card object");
        assertEquals("clubs", card.suite, "constructor did not work properly; card suite of new card is not correct");
        assertEquals(1, card.value, "constructor did not work properly; card value of new card is not correct");

    }

}
