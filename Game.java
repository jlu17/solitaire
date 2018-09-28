import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

class Game {

  // Constants
  public static final String[] suits = {"D", "C", "H", "S"};
  public static final String[] ranks = {"A ", "2 ", "3 ", "4 ", "5 ", "6 ", "7 ", "8 ", "9 ", "10", "J ", "Q ", "K "};

  // Array of tableau piles.
  static ArrayList<Card> allCards;
  static ArrayList<Card> restOfCards;
  static TableauPile[] tableau;
  static FoundationPile[] foundation;

  static boolean gameOver;

  public static void main(String[] args) {
    Scanner reader = new Scanner(System.in);
    printIntro();

    String play = reader.next();
    println("");
    if (play.equals("n")) {
      println("Goodbye! Thanks.");
    } else {
      println("Starting the game...");

      initializeCards();
      initializeFoundation();

      dealDeck();

      String operation = "";
      gameOver = false;

      while (gameOver == false) {
        printOtherPile();
        printTableauPiles();
        printOperations();

        handleOperation(reader, reader.next());
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

    // printAllCards();
  }

  public static void initializeFoundation() {
    foundation = new FoundationPile[4];
    for (int i = 0; i < foundation.length; i++) {
      foundation[i] = new FoundationPile();
    }
  }

  public static void printAllCards() {
    println("");
    for (Card card : allCards) {
      System.out.print(card.getRank() + ", " + card.getSuit() + " || ");
    }

    println("");
  }

  public static void dealDeck() {
    tableau = new TableauPile[7];

    int pileLength = 1;

    for (int i = 0; i < 7; i++) {
      TableauPile pile = new TableauPile();
      tableau[i] = pile;

      for (int j = 0;  j < pileLength; j++) {
        // System.out.println("allCards length: " + allCards.size());
        pile.addCard(allCards.remove(0));
      }
      pile.flipEnd();
      pileLength++;
    }

    restOfCards = allCards;
  }

  public static void printOtherPile() {
    println("");
    println("Hand                    Foundation cards");
    println("+---+  +---+        +---+ +---+ +---+ +---+");
    String vars = "|   |  |   |       ";
    for (int i = 0; i < foundation.length; i++) {
      if (!foundation[i].isEmpty()) {
        Card lastCard = foundation[i].getLast();
        vars += " |" + ranks[lastCard.getRank()] + suits[lastCard.getSuit()] + "|";
      } else {
        vars += " |   |";
      }
    }
    println(vars);
    println("+---+  +---+        +---+ +---+ +---+ +---+");
    println("");
  }

  // Prints out all of the piles in the tableau.
  public static void printTableauPiles() {
    String[] lines = {"", "", "", "", "", "", ""};

    //TODO: get the length of the longest pile within tableau

    for (TableauPile pile : tableau) {
      Card[] cards = pile.toArray();

      for (int i = 0; i < 7; i++) {
        if (i >= cards.length) {
          lines[i] += "      |";
        } else if (cards[i].isFaceUp()) {
          lines[i] += " " + ranks[cards[i].getRank() - 1] + " " + suits[cards[i].getSuit()] + " |";
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

  public static void printOperations() {
    println("");
    println("Choose an operation:");
    println("1 - Move from hand to tableau pile");
    println("2 - Move from tableau to foundation pile");
    println("3 - Move from one tableau pile to the other");
    println("4 - Move from hand to foundation pile");
    println("5 - Draw hand");
    println("Please enter a number 1-5.");
    println("Q to quit, R to restart");
    println("");
  }

  public static void handleOperation(Scanner reader, String input) {
    switch (input) {
      case "1":
        println("Which pile would you like to move this to? Please list a number.");
        String pileInput = reader.next();
        if (true) {

        }
        break;
      case "2":
        println("Type the number of the pile you want to move from: ");
        int pToFOrigin = reader.nextInt() - 1;
        println("Type the number of the pile you want to move to: ");
        int pToFDestination = reader.nextInt() - 1;

        if (pToFOrigin < 0 || pToFDestination < 0 || pToFOrigin > tableau.length || pToFDestination > foundation.length) {
          println("Invalid pile or foundation number. Try again!");
        } else {
          TableauPile p1 = tableau[pToFOrigin];
          FoundationPile f1 = foundation[pToFDestination];

          if (f1.canAccept(p1.getLast())) {
            f1.addCard(p1.pop());
            p1.flipEnd();
          } else {
            println("You cannot move from " + pToFOrigin + 1 + " to " + pToFDestination + 1);
          }
        }

        break;
      case "3":
        println("Type the number of the pile you want to move from: ");
        int pToPOrigin = reader.nextInt() - 1;
        println("Type the number of the pile you want to move to: ");
        int pToPDestination = reader.nextInt() - 1;

        if (pToPOrigin < 0 || pToPDestination < 0 || pToPOrigin > tableau.length || pToPDestination > tableau.length) {
          println("Invalid pile number. Try again!");
        } else {
          TableauPile p1 = tableau[pToPOrigin];
          TableauPile p2 = tableau[pToPDestination];

          if (p2.canAccept(p1.getLast())) {
            p2.addCard(p1.pop());
            p1.flipEnd();
          } else {
            println("You cannot move from " + pToPOrigin + " to " + pToPDestination);
          }
        }
        break;
      case "4":
        println("Type the number of the foundation you want to move to: ");
        int foundation = reader.nextInt();
        println("You cannot move the card to " + foundation + ". Try again!");
        break;
      case "5":
        break;
      case "q":
        println("Thanks for playing!");
        gameOver = true;
        break;
      default:
        println("That is not a valid move. Try again!");
        break;
    }
  }

  public static void drawHand() {

  }
}
