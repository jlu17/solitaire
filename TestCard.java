import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestCard {
  @Test
  public void createCard() {
    Card a = new Card(0, 0);
    Card b = a;
    assertEquals(a.getSuit(), 0);
    assertEquals(a.getRank(), 0);
    assertequals(a, b);
  }
}
