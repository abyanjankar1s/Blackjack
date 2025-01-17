import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to BlackJack!");
        UserPlayer player = new UserPlayer(1000, new Hand());
        Dealer dealer = new Dealer(new Hand());
        while(player.getBalance() > 0){
            new GameRound(player, dealer, new Deck()).play();
        }
    }
}

enum CardSuit {
    CLUBS, DIAMONDS, HEARTS, SPADES;
}

class Card {
    private final CardSuit suit;
    private final int value; 

    public Card(CardSuit suit, int value) {
        this.suit = suit;
        this.value = value;
    }
    public CardSuit getSuit() {
        return this.suit;
    }
    public int getValue(){
        return this.value;
    }
    public void print(){
        System.out.println(getSuit() + " "+ getValue());
    }
}

class Hand {
    private List<Card> cards;
    private int score;

    public Hand(){
        this.score = 0;
        this.cards = new ArrayList<>();
    }
    public void addCard(Card card){
        cards.add(card);
        if(card.getValue() == 1){
            score += (score + 11 <= 21) ? 11 : 1;
        } else {
            score += card.getValue();
        }
    }
    public int getScore(){
        return score;
    }
    public List<Card> getCards(){
        return cards;
    }
    public void print(){
        for(Card card: cards){
            System.out.println(card.getSuit() + ", "+ card.getValue());
        }
    }
}

class Deck {
    private List<Card> cards;
    private Random random = new Random();

    public Deck(){
        cards = new ArrayList<>();
        for(CardSuit suit: CardSuit.values()) {
            for(int value=1; value <= 13; value++){
                cards.add(new Card(suit, Math.min(value,10)));
            }
        }
    }
    public void print(){
        for(Card card: cards){
            card.print();
        }
    }
    public Card draw(){
        return cards.remove(cards.size() -1 );
    }
    public void shuffle(){
        for(int i =0; i<cards.size(); i++){
            int j = random.nextInt(51);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
}

abstract class Player {
    private Hand hand;

    public Player(Hand hand){
        this.hand = hand;
    }
    public Hand getHand(){
        return this.hand;
    }
    public void clearHand(){
        this.hand = new Hand();
    }
    public void addCard(Card card){
        this.hand.addCard(card);
    }
    abstract boolean makeMove();
}

class UserPlayer extends Player {
    static private Scanner input = new Scanner(System.in);
    private int balance;

    public UserPlayer(int balance, Hand hand){
        super(hand);
        this.balance = balance;
    }
    public int getBalance(){
        return balance;
    }
    public int placeBet(int amount){
        if(amount > balance){
            throw new Error("Insufficient funds");
        }
        balance -= amount;
        return amount; 
    }
    public void receiveWinnings(int amount){
        balance += amount;
    }
    //override makemove in player class
    public boolean makeMove(){
        if(this.getHand().getScore() > 21){
            return false;
        }
        //read the user input
        System.out.print("Draw card? [YES/NO] ");
        String move = input.nextLine();
        return move.equals("y");
    }
}

class Dealer extends Player {
    private int targetScore;

    public Dealer(Hand hand){
        super(hand);
        this.targetScore = 17;
    }
    public void updateTargetScore(int score){
        this.targetScore = score;
    }
    @Override
    public boolean makeMove(){
        return this.getHand().getScore() < this.targetScore;
    }
}

class GameRound {
    static private Scanner input = new Scanner(System.in);
    private UserPlayer player;
    private Dealer dealer;
    private Deck deck;

    public GameRound(UserPlayer player, Dealer dealer, Deck deck){
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
    }
    private int getBetUser(){
        System.out.print("Enter a bet amount: ");
        return input.nextInt();
    }
    private void dealInitialCards(){
        for(int i = 0; i < 2; i++){
            player.addCard(deck.draw());
            dealer.addCard(deck.draw());
        }
        System.out.println("Player hand: ");
        player.getHand().print();
        Card dealerCard = dealer.getHand().getCards().get(0);
        System.out.println("Dealer's first card: ");
        dealerCard.print();
    }
    private void cleanupRound(){
        player.clearHand();
        dealer.clearHand();
        System.out.println("Player Balance: "+player.getBalance());
    }
    public void play(){
        deck.shuffle();

        if(player.getBalance() <= 0){
            System.out.println("Player out of money.");
            return;
        }
        int userBet = getBetUser();
        player.placeBet(userBet);

        dealInitialCards();

        //user make move
        while(player.makeMove()){
            Card drawnCard = deck.draw();
            System.out.println("Player draws "+drawnCard.getSuit() + " "+ drawnCard.getValue());
            player.addCard(drawnCard);
            System.out. println("Player score: "+player.getHand().getScore());
        }
        if(player.getHand().getScore() > 21){
            System.out.println("Player Lost");
            cleanupRound();
            return;
        }

        //dealer makes moves
        dealer.updateTargetScore(player.getHand().getScore());
        while(dealer.makeMove()){
            dealer.addCard(deck.draw());
        }
        //determine winner
        int dealerScore = dealer.getHand().getScore();
        int playerScore = player.getHand().getScore();
        System.out.println("Dealer Final score: "+ dealerScore);
        System.out.println("Player Final score: "+ playerScore);
        if(dealerScore > 21 || playerScore > dealerScore){
            System.out.println("Player wins");
            player.receiveWinnings(userBet * 2);
        } else if(dealerScore > playerScore){
            System.out.println("Player loses.");
        } else {
            System.out.println("Game ends in draw.");
            player.receiveWinnings(userBet);
        }
        cleanupRound();
    }
}

