class TableauPile extends Pile {

  TableauPile() {
    super();
    // Initialize pile of cards
  }

  public boolean canAccept(Card aCard) {
    int cardRank = aCard.getRank();
    if (super.isEmpty()) {
      return cardRank == 13;
    } else if (super.getLast().getRank() - cardRank == 1
               && aCard.getColor() * -1 == super.getLast().getColor()) {
      //This means that the one above is one over the current card and is a different color. Valid!
      return true;
    }
    else {
      return false;
    }
  }
}
