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

  // Returns the suit of the card.
  public int getSuit() {
    return suit;
  }

  // Returns the rank of the card.
  public int getRank() {
    return rank;
  }

  // Returns whether the card is shown or not.
  public boolean isFaceUp() {
    return faceUp;
  }

  // Sets the card's faceUp value to true.
  public void flip() {
    this.faceUp = true;
  }

  // Returns the card color:
  // - Black: spade, club
  // - Red: heard, diamond
  public int getColor() {
    if (getSuit() == spade || getSuit() == club) {
      return black;
    } else {
      return red;
    }
  }
}
