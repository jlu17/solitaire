import java.util.ArrayDeque;

class Pile {

  ArrayDeque<Card> cards;

  Pile () {
    cards = new ArrayDeque<Card>();
  }

  public boolean isEmpty() {
    return cards.isEmpty();
  }

  public Card peek() {
    return cards.peek();
  }

  public Card pop() {
    return cards.pollLast();
  }

  public void addCard(Card c) {
    cards.addLast(c);
  }

  public void flipEnd() {
    if (!cards.isEmpty()) {
      cards.getLast().flip();
    }
  }

  public Card getLast() {
    return cards.getLast();
  }

  public Card[] toArray() {
    return cards.toArray(new Card[cards.size()]);
  }

}
