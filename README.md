README for Solitaire
=====

By Jennifer Lu

## How to run: #####

Open terminal. Type:
```
javac Game.java
java Game
```
Type "Y" in response to the prompt to actually start the game.

**To run tests:**

To test Card.java:
```
javac -cp .:junit-4.10.jar:hamcrest-core-1.3.jar TestCard.java
java -cp .:junit-4.10.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestCard
```

To test Piles.java (and its extended classes):
```
javac -cp .:junit-4.10.jar:hamcrest-core-1.3.jar TestPiles.java
java -cp .:junit-4.10.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestPiles
```
Both test files should pass.

## Design choices: #####

I decided to use Java for its ArrayDeque data structure. I wanted to imitate the piles through the stack data structure and used ArrayDeque to imitate a stack by "polling" from the end of the queue for a FILO structure.

The entire game is run on the Card class and the Pile class. The Card class represents every card in the game, with rank, suit, and faceUp values to imitate the qualities of an actual card. The Pile class is what I used to imitate the stack structure, and I extended the TableauPile, FoundationPile, and OtherPile classes from the Pile class because they all exhibit very similar behavior with minor differences. The OtherPile represents both the hand and the waste piles because drawing hands moves cards from the hand to the waste piles.

I kept most of the game action within the Game class, creating FoundationPile, TableauPile, OtherPile, and Card objects when necessary to run the game.

Specifics:
* I decided to use an ArrayList to initialize all of the cards because it was easier to shuffle the cards that way rather than with ArrayDeques.
* I decided to add an empty string to the 0th index of ranks to avoid off-by-one indexing errors with ranks.
* I used JUnit and its assertEquals method to test my Card and Pile classes (along with their extended classes). I did not test Game.java because many of the methods required user input, so I tested these by playing the game multiple times.


Things that can be improved:
* Further modularizing the code in Game.java. A lot of code was repeated, especially with the individual operations, so that can be combined into cleaner methods. Also, the Game.java file is very long.
* Speed. I could have used something faster than an ArrayDeque to store the Cards by using arrays in Python (for easier splicing and indexing). I was just immediately drawn to Java because of its inherent OOP nature. Additionally, I used a lot of for loops and could definitely cut down on those.
* Variable/method names. I did not have a particularly standardized way of naming my method/variables, so that could be improved.
* More comprehensive tests including those that test Game.java.
