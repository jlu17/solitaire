import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestPiles {

  @Test
  public void createPile() {
    Pile p = new Pile();
    assertEquals(p.isEmpty(), true);
    Card c = new Card(0, 0);

    assertEquals(p.canAccept(c), false);
    p.addCard(c);

    assertEquals(p.isEmpty(), false);
    assertEquals(p.size(), 1);
    assertEquals(p.peek(), c);
    assertEquals(p.pop(), c);
  }

  // Testing multi length piles

  @Test
  public void multiplePiles() {
    Pile p1 = new Pile();
    Pile p2 = new Pile();

    Card c1 = new Card(0, 0);
    Card c2 = new Card(1, 1);
    Card c3 = new Card(2, 2);

    p1.addCard(c1);
    p2.addCard(c2);
    p1.addCard(c3);

    assertEquals(p1.size(), 2);

    p2.addCard(p1.pop());

    assertEquals(p2.size(), 2);
    assertEquals(p2.pop(), c3);
  }

  @Test
  public void specialPiles() {
    FoundationPile f = new FoundationPile();
    TableauPile t = new TableauPile();
    OtherPile o = new OtherPile();
    OtherPile o2 = new OtherPile();

    Card[] cards = new Card[3];

    for (int i = 1; i < 4; i++) {
      cards[i - 1] = new Card(0, i);
    }

    for (int i = 1; i < 4; i++) {
      o.addCard(cards[i - 1]);
      assertEquals(o.peek().getRank(), i);
      assertEquals(o.peek().getSuit(), 0);
    }

    assertEquals(o.size(), 3);
    assertEquals(f.canAccept(o.peek()), false);
    assertEquals(t.canAccept(o.peek()), false);

    o2.addCard(o.pop());
    o2.addCard(o.pop());

    assertEquals(o.size(), 1);
    System.out.println(o.peek().getRank());
    assertEquals(f.canAccept(o.peek()), true);
    f.addCard(o.pop());
    assertEquals(f.canAccept(o2.peek()), true);

    Card king = new Card(0, 13);
    assertEquals(t.canAccept(king), true);

    t.addCard(king);
    Card queen = new Card(1, 12);
    assertEquals(t.canAccept(queen), true);

  }

}
