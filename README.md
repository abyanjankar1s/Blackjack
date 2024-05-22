# Blackjack
Object Oriented Programming: Designing Blackjack card game.


Design Blackjack


Background: 
Blackjack is a popular card game played in casinos. The goal is to get a hand with a value as close to 21 as possible without going over.

The player is dealt (or draws) two cards and can choose to draw more or stop. The dealer is dealt two cards as well, but only one is visible to the player. The player wins if their hand is closer to 21 than the dealer's hand. But if they go over 21 they automatically lose. If the player and dealer have the same value, it's a tie.


Requirements:
Obviously this isn't a test of how well you know blackjack. You want to clarify the requirements with the interviewer, because they might have completely different requirements in mind.


1. How many cards are in a deck? what kind? etc.
2. Is the deck refilled after each round?
3. How many players?
4. Are we implementing gambling or score-keeping?


Basics: 

Only two players, including the dealer
Only one deck of cards, which is refilled after each round
We are implementing a gambling system for the non-dealer player
Cards
There are 52 cards in a deck
Each card has a suit (hearts, spades, diamonds, clubs) and there are 13 cards in each suit
A card could be numbered (2-10) and have the same point value
a face (jack, queen, king) and have a point value of 10
an ace and have a point value of 1 or 11, whichever is better for the player


Game Round
To start each round, both the dealer and the player are dealt two cards
One of the dealer's cards is hidden from the player
The player can choose to draw one more card until they go over 21 or decide to stop
If they go over 21, they lose and the round is over
The dealer will draw until they have a hand value of N or more, where N is the player's hand value
If the dealer goes over 21, the player wins
At the end, if the dealer's hand value is greater than the player's, the dealer wins

Gambling
A player can start with an arbitrary amount of money
The player can bet as much money as they have
The dealer will never run out of money


Design

A Card will have a Suit and a Value
The suit is irrelevant for blackjack, but if our game had a UI it would be useful information.
Similarly, a ten, jack, queen, and king all have the same value, so we won't distinguish between them.
But these points would be worth clarifying. You don't want to overengineer a solution, but you also don't want to make major assumptions without clarifying with your interviewer.
A Deck will have an array of Cards
The deck will be responsible for shuffling and popping (drawing) Cards
A Player could be either the Dealer or the User, and since both will a Hand, the Player can be an abstract class
The Player will also be responsible for making a move (drawing or stopping)
The UserPlayer will also have a Balance and be able to place a Bet


DEMO:
Welcome to BlackJack!
Enter a bet amount: 500
Player hand: 
CLUBS, 7
HEARTS, 10
Dealer's first card: 
CLUBS 9
Draw card? [YES/NO] n
Dealer Final score: 26
Player Final score: 17
Player wins
Player Balance: 1500
Enter a bet amount: 
