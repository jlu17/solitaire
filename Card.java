class Card {

  // Game-specific characteristics

  // Card characteristics
  private int suit;
  private int rank;
  private boolean faceUp;

  private static final int red = -1;
  private static final int black = 1;

  private static final int diamond = 0;
  private static final int club = 1;
  private static final int heart = 2;
  private static final int spade = 3;

  // Constructor
  Card (int suit, int rank) {
    this.suit = suit;
    this.rank = rank;
    this.faceUp = false;
  }

  // This method returns the suit of the card.
  public int getSuit() {
    return suit;
  }

  // This method returns the rank of the card.
  public int getRank() {
    return rank;
  }

  public boolean isFaceUp() {
    return faceUp;
  }

  public void flip() {
    this.faceUp = true;
  }

  public int getColor() {
    if (getSuit() == spade || getSuit() == club) {
      return black;
    } else {
      return red;
    }
  }
}
