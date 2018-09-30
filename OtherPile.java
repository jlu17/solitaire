class OtherPile extends Pile {
  OtherPile() {
    super();
  }

  // Flips and adds the card to the pile. Intended for the waste pile.
  public void addCard(Card c) {
    c.flip();
    super.cards.addLast(c);
  }
}
