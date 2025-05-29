//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Cards gameCards;
    PlayerDetails player1;
    PlayerDetails player2;
    PlayerDetails[] players;

    private int pot = 0;

    Scanner scanner = new Scanner(System.in);


    public Game() {

        System.out.print("Enter Player1's name: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter Player2's name: ");
        String name2 = scanner.nextLine();

        gameCards = new Cards();
        player1 = new PlayerDetails(name1, gameCards);
        player2 = new PlayerDetails(name2, gameCards);
        players = new PlayerDetails[] {player1, player2};
    }


    public static ArrayList<String> getFlop(Cards deck) {
      ArrayList<String> flop = deck.getCards(5);
      return flop;
      //System.out.println(flop);
    }


    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void addPot(int amt){
        this.pot += amt;
    }


    public void printIntro() {

        System.out.println("");
        System.out.println("*----------------------------------*");
        System.out.println("     Welcome to Texas Holdem!");
        System.out.println("*----------------------------------*");
        System.out.println("");

        player1.printName();
        System.out.println(", do you want to see your cards? (y/n)");

        String p1Answ = scanner.nextLine();
        if (p1Answ.equals("y")) {
            player1.seeCards();
            for (int i = 0; i < 60; i++) {
                System.out.println("");
            }
            System.out.println("Scroll up to see your cards");
        }

        player2.printName();
        System.out.println(", do you want to see your cards? (y/n)");

        String p2Answ = scanner.nextLine();
        if (p2Answ.equals("y")) {
            player2.seeCards();
            for (int i = 0; i < 60; i++) {
                System.out.println("");
            }
            System.out.println("Scroll up to see your cards");
        }
    }


    public void ante() {

        System.out.println("Small bind of 5 coins is required!");
        System.out.println("*---*");


        player1.printName();
        System.out.println(", would you like to raise to 10 coins? (y/n)");
        String p1Ans = scanner.nextLine();
        if (p1Ans.equals("n")) {
                System.out.print("Balance: ");
                player1.subtractBet(5);
            System.out.println(player1.getBalance());

            System.out.println("*---*");

            player2.printName();
                System.out.println(", 5 coins are subtracted from your balance");
                System.out.print("Balance: ");
            player2.subtractBet(5);
            System.out.println(player2.getBalance());

            setPot(20);

        }
        else if (p1Ans.equals("y")) {
            System.out.print("Balance: ");
                player1.subtractBet(10);
            System.out.println(player1.getBalance());


            System.out.println("*---*");

            player2.printName();
            System.out.println(", 10 coins are subtracted from your balance");
            System.out.print("Balance: ");
                player2.subtractBet(10);
            System.out.println(player2.getBalance());

            setPot(30);

        }

    }


    /*
    public void player1CRF(int callAmount) {

        player1.printName();
        System.out.println(", would you like to (c)all, (r)aise, or (f)old?");
            String answ = scanner.nextLine();
            if (answ.equals("r")) {
                System.out.print("Enter your bet: ");
                int betAmount = scanner.nextInt();
                System.out.print("Coins: ");
                player1.subtractBet(betAmount);
                pot += betAmount;

            }
            else if (answ.equals("c")) {
                System.out.print("Coins: ");
                player1.subtractBet(callAmount);
                pot += callAmount;
            }
            else if (answ.equals("f")) {
                player1.printName();
                System.out.println("wins the game!");
            }
    }


    public void player2CRF(int callAmount) {

        player2.printName();
        System.out.println(", would you like to (c)all, (r)aise, or (f)old?");
            String answ = scanner.nextLine();
            if (answ.equals("r")) {
                System.out.print("Enter your bet: ");
                int betAmount = scanner.nextInt();
                System.out.print("Coins: ");
                player2.subtractBet(betAmount);
                pot += betAmount;

            }
            else if (answ.equals("c")) {
                System.out.print("Coins: ");
                player2.subtractBet(callAmount);
                pot += callAmount;
            }
            else if (answ.equals("f")) {
                player2.printName();
                System.out.println("wins the game!");
            }
        
    }
    */

    /**
     * Single round of betting
     * @return -1 if all call, -2 if fold, player# if someone wins (all others fold)
     */
    private int bettingRound() {
        int[] currentBets = new int[players.length];
        int currentMaxBet = 0;
        int calls = 0;
        int currentPlayer = 0;

        while (calls < players.length - 1) {

            int bet = players[currentPlayer].bet(currentBets[currentPlayer], currentMaxBet);
            addPot(bet);

            System.out.println("--");
            System.out.println("Pot: " + pot);
            System.out.print("Balance: ");
                System.out.println(players[currentPlayer].getBalance());

            if (bet < currentMaxBet - currentBets[currentPlayer]) {
                // fold, other player wins
                addPot(1);
                return (currentPlayer + 1) % players.length;
            } 
            else if (bet == currentMaxBet - currentBets[currentPlayer]){
                calls += 1;
                System.out.print("Balance: ");
                    System.out.println(players[currentPlayer].getBalance());
            }
            else {
                calls = 0;
            }

            currentBets[currentPlayer] += bet;

            if (currentBets[currentPlayer] > currentMaxBet){
                currentMaxBet = currentBets[currentPlayer];
            }

            currentPlayer += 1;
            currentPlayer %= players.length;

        }

        return -1;
    }


    public void playHand() {

        ArrayList<String> flop = Game.getFlop(gameCards);
        int round = 0;

        System.out.println("*---------------*");
        System.out.println("Round " + round + ": Ante");
        round++;

        ante();

        System.out.println(" ");
        System.out.println("Pot: " + pot);


        //call, raise, fold
        for (int i = 3; i <= flop.size(); i++){

            System.out.println(" ");
            System.out.println("*----------------------------------*");
            System.out.println(" ");
            System.out.println("Round " + round + ":");

            System.out.print("Flop: | ");
            for (int k = 0; k < i; k++) {
                System.out.print(flop.get(k) + " | ");
            }
            System.out.println(" ");
            System.out.println("*----------------------------------*");


            int winner = bettingRound();
            if (winner != -1){
                round++;
                players[winner].addWinnings(getPot());
                setPot(0);
                break;
            }
            else if (winner == -2) {
                players[winner].addWinnings(getPot());
            }

            round++;
        }
       
    }
  

    public void play() {
        printIntro();
        playHand();

        /*
        player1.seeCards();
        player1.printName();
        player2.seeCards();
        player2.printName();
        */
        
    } 

}
