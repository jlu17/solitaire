import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

class Game {

  // Constants
  public static final String[] suits = {"D", "C", "H", "S"};
  // Did not zero index ranks so that it's less confusing in the code.
  // A = 1, ..., K = 13
  public static final String[] ranks = {"", "A ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K "};

  // Array of tableau piles.
  static ArrayList<Card> allCards;
  static OtherPile handPile;
  static OtherPile wastePile;
  static TableauPile[] tableau;
  static FoundationPile[] foundation;

  static boolean gameOver;
  static boolean won;

  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    printIntro();

    // Whether the player wants to start the game or not.
    String play = reader.next();
    println("");
    if (play.equals("n") || play.equals("N")) {
      println("Goodbye! Thanks.");
    } else {
      println("Starting the game...");

      initializeCards();
      initializeFoundation();

      dealDeck();

      String operation = "";
      gameOver = false;
      won = false;

      // Plays the actual game!
      while (!gameOver) {
        printOtherPile();
        printTableauPiles();
        printOperations();

        handleOperation(reader, reader.next());
        gameOver = isGameOver();
      }
      if (won) {
        printOtherPile();
        printTableauPiles();
        println("=================");
        println("You've completed the game!");
        println("=================");
      }
    }
  }

  // Game-related functions

  // Makes my life a little easier
  public static void println(String s) {
    System.out.println(s);
  }

  // This function handles all of the intro text. Edit intro here!
  public static void printIntro() {
    println("");
    println("");
    println("Made by: Jennifer Lu");
    println("");
    println("");
    println("Welcome to Solitaire! Here are the rules: ");
    println("===RULES TO BE PLACED HERE===");
    println("");
    println("Ready to play? (Y/n)");
  }

  // Creates an arraylist with all of the cards, then shuffles them.
  public static void initializeCards() {
    allCards = new ArrayList<>();

    // Going through A-K
    // REMINDER: A = 1, J = 11, Q = 12, K = 13
    for (int rank = 1; rank < 14; rank++) {
      // Going through suits
      // REMINDER: Diamond = 0, Club = 1, Heart = 2, Spade = 3
      for (int suit = 0; suit < 4; suit++) {
        allCards.add(new Card(suit, rank));
      }
    }

    Collections.shuffle(allCards);
    println(Integer.toString(allCards.size()));
    // printAllCards();
  }

  // For testing purposes (debugging).
  public static void printAllCards() {
    for (Card card : allCards) {
      println(ranks[card.getRank()] + " " + suits[card.getSuit()]);
    }
  }

  // Initializes the four foundation piles.
  public static void initializeFoundation() {
    foundation = new FoundationPile[4];
    for (int i = 0; i < foundation.length; i++) {
      foundation[i] = new FoundationPile();
    }
  }

  // This method creates the seven tableau piles and puts the rest of the cards into
  public static void dealDeck() {
    tableau = new TableauPile[7];

    // pileLength increments by one for every column.
    int pileLength = 1;

    for (int i = 0; i < 7; i++) {
      TableauPile pile = new TableauPile();
      tableau[i] = pile;

      for (int j = 0;  j < pileLength; j++) {
        pile.addCard(allCards.remove(0));
      }

      // Last item in pile can be revealed to the user.
      pile.flipEnd();
      pileLength++;
    }

    handPile = new OtherPile();
    wastePile = new OtherPile();

    // Adds the rest of the cards to the hand pile.

    for (Card c : allCards) {
      handPile.addCard(c);
    }
  }

  // Prints the hand/waste piles and the foundation piles.
  public static void printOtherPile() {
    // This part is purely for aesthetic purposes.
    println("");
    println("Hand                    Foundation cards");
    println("+---+  +---+        +---+ +---+ +---+ +---+");

    // Hand pile is X if there's more cards to be drawn, empty if not.
    String otherPileString = "";
    if (handPile.isEmpty()) {
      otherPileString += "|   |  |";
    } else {
      otherPileString += "| X |  |";
    }

    // Waste pile has card info if drawn.
    if (wastePile.isEmpty()) {
      otherPileString += "   |       ";
    } else {
      Card wasteCard = wastePile.peek();
      otherPileString += ranks[wasteCard.getRank()] + suits[wasteCard.getSuit()] + "|       ";
    }

    // Foundation cards have card info if added.
    for (int i = 0; i < foundation.length; i++) {
      if (!foundation[i].isEmpty()) {
        Card lastCard = foundation[i].peek();
        otherPileString += " |" + ranks[lastCard.getRank()] + suits[lastCard.getSuit()] + "|";
      } else {
        otherPileString += " |   |";
      }
    }
    println(otherPileString);
    println("+---+  +---+        +---+ +---+ +---+ +---+");
    println("");
  }

  // Prints out all of the piles in the tableau.
  public static void printTableauPiles() {

    // Either uses 7 rows or the maximum length in one pile, whichever is larger.
    int rows = 7;
    for (TableauPile pile : tableau) {
      if (pile.size() > rows) {
        rows = pile.size();
      }
    }

    String[] lines = new String[rows];
    Arrays.fill(lines, "");

    // Adds an equal length string with the pile info to each row, representing a column.
    for (TableauPile pile : tableau) {
      Card[] cards = pile.toArray();

      for (int i = 0; i < rows; i++) {
        if (i >= cards.length) {
          lines[i] += "      |";
        } else if (cards[i].isFaceUp()) {
          lines[i] += " " + ranks[cards[i].getRank()] + " " + suits[cards[i].getSuit()] + " |";
        } else {
          lines[i] += "   X  |";
        }
      }
    }

    println("Pile 1|Pile 2|Pile 3|Pile 4|Pile 5|Pile 6|Pile 7");
    for (String line : lines) {
      println(line);
    }
  }

  // Prints out all of the options the user can do.
  public static void printOperations() {
    println("");
    println("Choose an operation:");
    println("1 - Hand --> tableau pile");
    println("2 - Tableau --> foundation pile");
    println("3 - One tableau --> other tableau pile");
    println("4 - Multiple in one tableau --> other tableau pile");
    println("5 - Hand --> foundation pile");
    println("6 - Foundation --> tableau pile");
    println("7 - Draw hand");
    println("Please enter a number 1-6.");
    println("Q to quit");
    println("");
  }

  // Acts based on the user intention.
  public static void handleOperation(Scanner reader, String input) {
    switch (input) {
      // Hand --> tableau
      case "1":
        println("Which pile would you like to move this to? Please list a number.");
        int hToPDestination = reader.nextInt() - 1;
        if (hToPDestination < 0 || hToPDestination >= tableau.length) {
          println("Invalid pile number. Try again!");
        } else if (wastePile.isEmpty()) {
          println("Invalid because waste pile is empty. Draw a hand first!");
        } else {
          TableauPile p1 = tableau[hToPDestination];
          addToPile(p1, wastePile);
        }
        break;

      // Tableau --> foundation
      case "2":
        println("Type the number of the pile you want to move from: ");
        int pToFOrigin = reader.nextInt() - 1;
        println("Type the number of the pile you want to move to: ");
        int pToFDestination = reader.nextInt() - 1;

        if (pToFOrigin < 0 || pToFDestination < 0 || pToFOrigin >= tableau.length || pToFDestination >= foundation.length) {
          println("Invalid pile or foundation number. Try again!");
        } else {
          TableauPile p1 = tableau[pToFOrigin];
          FoundationPile f1 = foundation[pToFDestination];

          addToPile(f1, p1);
        }

        break;

      // Tableau --> tableau (single card)
      case "3":
        println("Type the number of the pile you want to move from: ");
        int pToPOrigin = reader.nextInt() - 1;
        println("Type the number of the pile you want to move to: ");
        int pToPDestination = reader.nextInt() - 1;

        if (pToPOrigin < 0 || pToPDestination < 0 || pToPOrigin >= tableau.length || pToPDestination >= tableau.length) {
          println("Invalid pile number. Try again!");
        } else {
          TableauPile p1 = tableau[pToPOrigin];
          TableauPile p2 = tableau[pToPDestination];

          addToPile(p2, p1);
        }
        break;

      // Tableau --> tableau (multiple cards)
      case "4":
        println("Type the number of the pile you want to move from: ");
        int pToPOriginMultiple = reader.nextInt() - 1;
        println("Type how many cards to transfer: ");
        int num = reader.nextInt();
        println("Finally, type the number of the pile you want to move to:");
        int pToPDestinationMultiple = reader.nextInt() - 1;

        if (pToPOriginMultiple < 0 || pToPDestinationMultiple < 0 || pToPOriginMultiple >= tableau.length || pToPDestinationMultiple >= tableau.length) {
          println("Invalid pile number. Try again!");
        } else if (num < 0 || num > tableau[pToPOriginMultiple].size()) {
          println("Invalid number of cards to transfer.");
        } else {
          TableauPile p1 = tableau[pToPOriginMultiple];
          TableauPile p2 = tableau[pToPDestinationMultiple];

          addToPile(p2, p1, num);
        }
        break;

      // Hand --> foundation
      case "5":
        println("Type the number of the foundation you want to move to: ");
        int hToFDestination = reader.nextInt() - 1;
        if (hToFDestination < 0 || hToFDestination >= foundation.length) {
          println("Invalid foundation number. Try again!");
        } else if (wastePile.isEmpty()) {
          println("Draw a hand first.");
        } else {
          FoundationPile f1 = foundation[hToFDestination];

          addToPile(f1, wastePile);
        }
        break;

      // Foundation --> tableau (last case resort)
      case "6":
        println("Type the number of the foundation pile you want to move from: ");
        int fToPOrigin = reader.nextInt() - 1;
        println("Type the number of the pile you want to move to:");
        int fToPDestination = reader.nextInt() - 1;

        if (fToPOrigin < 0 || fToPDestination < 0 || fToPOrigin >= foundation.length || fToPDestination >= tableau.length) {
          println("Invalid pile or foundation number. Try again!");
        } else {
          FoundationPile f1 = foundation[fToPOrigin];
          TableauPile p2 = tableau[fToPDestination];

          addToPile(p2, f1);
        }
        break;

      // Draw hand (aka move card from hand to waste pile)
      case "7":
        drawHand();
        break;

      // Quit
      case "q":
      case "Q":
        println("Thanks for playing!");
        gameOver = true;
        break;

      // Anything else
      default:
        println("That is not a valid move. Try again!");
        break;
    }
  }

  // This moves single cards from one pile to the other.
  public static void addToPile (Pile destPile, Pile originPile) {
    Card c = originPile.peek();
    if (destPile.canAccept(c)) {
      destPile.addCard(originPile.pop());
    } else {
      String cardString = ranks[c.getRank()] + " " + suits[c.getSuit()];
      println("You cannot move " + cardString + " to the pile.");
    }
  }

  // This moves multiple cards from one pile to the other (preserving order).
  public static void addToPile (Pile destPile, Pile originPile, int num) {
    // Creates temporary stack to preserve order when added to the other pile.
    // Also created to check if head of pile can be added in a valid way.
    Pile tempPile = new Pile();
    for (int i = 0; i < num; i++) {
      tempPile.addCard(originPile.pop());
    }

    Card c = tempPile.peek();
    if (!c.isFaceUp()) {
      println("You cannot move cards that are not face up.");

      // Inefficient - adds the cards back to the original pile.
      while (!tempPile.isEmpty()) {
        originPile.addCard(tempPile.pop());
      }
    } else if (destPile.canAccept(c)) {
      // Adds the cards to the new pile.
      while (!tempPile.isEmpty()) {
        destPile.addCard(tempPile.pop());
      }
    } else {
      // Adds cards back to the original pile.
      while (!tempPile.isEmpty()) {
        originPile.addCard(tempPile.pop());
      }
      String cardString = ranks[c.getRank()] + " " + suits[c.getSuit()];
      println("You cannot move " + cardString + " and the rest of the cards to the pile.");
    }

  }

  // Moves card from hand pile to waste pile, flipping it, too.
  public static void drawHand() {
    if (handPile.isEmpty() && wastePile.isEmpty()) {
      println("Cannot draw - both the hand and waste piles are empty!");
    } else if (!handPile.isEmpty()) {
      wastePile.addCard(handPile.pop());
    } else {
      println("Hand pile is empty. Moving waste pile to hand pile and redrawing.");
      while (!wastePile.isEmpty()) {
        handPile.addCard(wastePile.pop());
      }
      wastePile.addCard(handPile.pop());
    }
  }

  // Checks if all of the piles are empty by the end of the round.
  public static boolean isGameOver() {
    if (gameOver) {
      return gameOver;
    }

    // Checks if the tableau piles are all empty.
    for (TableauPile tp : tableau) {
      if (!tp.isEmpty()) {
        return false;
      }
    }

    // All tableau piles are empty, check if hand and waste are too.
    if (wastePile.isEmpty() && handPile.isEmpty()) {
      won = true;
      return true;
    } else {
      return false;
    }
  }
}
