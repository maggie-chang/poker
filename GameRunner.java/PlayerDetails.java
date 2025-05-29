import java.util.ArrayList;
import java.util.Scanner;

public class PlayerDetails{
    
    private String name;
    private ArrayList<String> hand;
    private int balance = 100;

    Scanner scanner = new Scanner(System.in);
    

    public PlayerDetails(String player, Cards deck) {
      // get player1 name
      this.name = player;

      // Setup player cards
      this.hand = deck.getCards(2);

      //System.out.println(cards.arrDeck);
    }
    
    // see player's cards
    public void seeCards() {
      System.out.println(hand);
    }

    // print player name
    public void printName() {
      System.out.print(name);
    }

    // returns amount of coins player has
    public void setBalance(int balance) {
      this.balance = balance;
    }

    public int getBalance() {
      return balance;
    }

    /**
     * Single bet during a round of betting
     * @param currentMaxBet Current amount bet this round so far
     * @param totalBet Current max wager
     * @return -2 if fold, totalBet - currentBet if call, > if raise
     */
    public int bet(int currentBet, int currentMaxBet) {
      System.out.println(" ");
      System.out.println("~~~ Player " + name + " ~~~");
      System.out.println("You have bet " + currentBet + " this round.");
      System.out.println("The current wager is " + currentMaxBet + ".");
      System.out.println("Call: " + (currentMaxBet-currentBet) + " | Fold: -2" + " | Raise: >" + currentMaxBet);
      System.out.print("Your bet (enter your wager): ");
      int amt = Integer.parseInt(scanner.nextLine());
      subtractBet(amt);
      if (amt == -2) {
        return -2;
      }
      return amt;
    }

    // subtracts the amount player bets
    public void subtractBet(int betAmount) {
      int currAmount = getBalance();
      int newAmount = currAmount - betAmount;
      setBalance(newAmount);
    }

    //
    public void addWinnings(int winnings) {
      int currAmount = getBalance();
      int newAmount =  currAmount + winnings;
      setBalance(newAmount);
    }
}
