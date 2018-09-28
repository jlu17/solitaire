class FoundationPile extends Pile {
  FoundationPile() {
    super();
  }

  public boolean canAccept(Card aCard) {
    int cardRank = aCard.getRank();
    if (super.isEmpty()) {
      return cardRank == 1;
    } else if (cardRank - super.getLast().getRank() == 1
               && aCard.getSuit() == super.getLast().getSuit()) {
      //This means that the one above is one over the current card and is a different color. Valid!
      return true;
    }
    else {
      return false;
    }
  }
}
