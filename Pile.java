import java.util.ArrayDeque;

class Pile {

  // Using an arraydeque in a stack format for more versatility.
  ArrayDeque<Card> cards;

  // Constructor
  Pile () {
    cards = new ArrayDeque<Card>();
  }

  // Returns whether the deque is empty or not.
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  // Returns the head of the deque "stack".
  public Card peek() {
    return cards.getLast();
  }

  // Removes and returns the head of the "stack"
  public Card pop() {
    return cards.pollLast();
  }

  // Adds card to the front of the "stack"
  public void addCard(Card c) {
    cards.addLast(c);
  }

  // Sets the faceUp value for the front of the "stack" to be true.
  // Intended for the tableau piles.
  public void flipEnd() {
    if (!cards.isEmpty()) {
      cards.getLast().flip();
    }
  }

  // Returns length of the "stack".
  public int size() {
    return cards.size();
  }

  // Returns a Card array from the bottom to the top of the stack.
  public Card[] toArray() {
    return cards.toArray(new Card[cards.size()]);
  }

  // Default canAccept is set to false for OtherPile (with no canAccept method).
  // Overriden by FoundationPile and TableauPile, which are meant to take cards.
  public boolean canAccept(Card aCard) {
    return false;
  }

}
