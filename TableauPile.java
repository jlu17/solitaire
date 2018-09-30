class TableauPile extends Pile {

  TableauPile() {
    super();
    // Initialize pile of cards
  }

  // Returns if the card in question is allowed to be placed on top of the
  // card in the foundation pile. (If 1 less than current card + same color).
  public boolean canAccept(Card aCard) {
    int cardRank = aCard.getRank();
    if (super.isEmpty()) {
      return cardRank == 13;
    } else if (super.peek().getRank() - cardRank == 1
               && aCard.getColor() * -1 == super.peek().getColor()) {
      //This means that the one above is one over the current card and is a different color. Valid!
      return true;
    }
    else {
      return false;
    }
  }

  public Card pop() {
    Card c = super.cards.pollLast();
    super.flipEnd();
    return c;
  }
}
