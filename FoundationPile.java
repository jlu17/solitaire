class FoundationPile extends Pile {
  FoundationPile() {
    super();
  }

  // Returns if the card in question is allowed to be placed on top of the
  // card in the foundation pile. (If 1 greater than current card + same suit).
  public boolean canAccept(Card aCard) {
    int cardRank = aCard.getRank();
    if (super.isEmpty()) {
      return cardRank == 1;
    } else if (cardRank - super.peek().getRank() == 1
               && aCard.getSuit() == super.peek().getSuit()) {
      //This means that the one above is one over the current card and is a different color. Valid!
      return true;
    }
    else {
      return false;
    }
  }
}
