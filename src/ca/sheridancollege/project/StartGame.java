package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class StartGame extends Game{
    ArrayList<HumanPlayer> players = new ArrayList<>();
    Scanner in = new Scanner(System.in);
    Random rand = new Random();
    private String RED ="\u001B[31m";
    private String RESET ="\u001B[0m";
    private String YELLOW ="\u001B[33m";
    private String BLUE ="\u001B[34m";

    public StartGame(String name) {
        super(name);
    }

    public void pause(){
        try {
            Thread.sleep(1000); // Pause 1 sec
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //
        }
    }

    public int dealer_logic(HumanPlayer dealer, Deck deck) {
        System.out.println(dealer.getPlayer_color()+"Dealer's hand" + dealer.getHand()+ RESET +"\n----------------\n");

        try {
            Thread.sleep(1000); // Pause 1 sec
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); //
        }

        int total = whats_total(dealer);
        if (total <=16 ){
            dealer.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size())));
            System.out.println(dealer.getPlayer_color()+"Dealer got a new card."+RESET);
            pause();
            System.out.println(dealer.getPlayer_color()+"Dealer's hand: "+RESET + dealer.getHand());
            pause();
            total = whats_total(dealer);
            System.out.println(dealer.getPlayer_color()+"Dealer's total is " + total+RESET);
            pause();
            return total;

        }
        return total;

    }


    public int whats_total(HumanPlayer player) {
        int total = 0;
        int aceCount =0;
        for (GameCard c : player.getHand()) {
                try{
                total+= Integer.parseInt(c.getCardValue());
                }
                catch(NumberFormatException e){
                    if (c.getCardValue().equals("J") || (c.getCardValue().equals("Q")) || (c.getCardValue().equals("K"))) {
                        total = total + 10;
                    }
                    else if (c.getCardValue().equals("A")) {
                        total = total + 11;
                        aceCount++;
                    }
                }
        }

        while (total > 21 && aceCount > 0) {
            total = total - 10;
            aceCount--;
        }
        return total;
    }

    public Deck build_deck (){
        GameCard card = new GameCard();
        String[] values = card.getValues();
        String[] suits = card.getSuits();
        ArrayList<GameCard> cards = new ArrayList<>();
        Deck deck = new Deck(52);
        for (int i = 0; i<values.length; i++){
            for (int j=0;j<suits.length;j++){
                deck.addCards(new GameCard(values[i], suits[j]));

            }

        }

        return deck;
    }



    public void set_up_game_cards(int num_of_players){
        Deck deck = build_deck();
        play(deck);
    }

    public void set_up_game_names(int num_of_players){
        int cont = 0;
        System.out.println("You have selected " + YELLOW + num_of_players + RESET + " Players\nPlease enter your players names.\n");
        while (cont < num_of_players){
            System.out.print(YELLOW+ "name" +(cont+1) +RESET +": ");
            String name = in.nextLine();
            HumanPlayer player = new HumanPlayer(name);
            if (cont == 0){
                player.setPlayer_color(RED);
                System.out.println(RED + player.getName() + ": Will be RED." +RESET);
                pause();
            }
            else {
                player.setPlayer_color(BLUE);
                System.out.println(BLUE + player.getName() + ": Will be BLUE."+RESET);
                pause();
            }

            this.players.add(player);
            cont+=1;

        }


        System.out.println(YELLOW + "Dealer: Will be YELLOW."+RESET);
        set_up_game_cards(num_of_players);

    }



    @Override
    public void play(Deck deck) {
        HumanPlayer dealer = new HumanPlayer("Dealer");
        dealer.setPlayer_color(YELLOW);
        int round = 1;
        Random rand = new Random();
        int cont = 0;
        boolean stillPLaying = true;


        while (stillPLaying) {
            System.out.println(YELLOW+"Round "+ round+ "\n----------------\n"+RESET );

            //clear hands
            for(HumanPlayer player : players){
                player.resetHand();
            }
            dealer.resetHand();

            //give a new hand
            while (cont < 2) {
                for (HumanPlayer player : players) {
                    player.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size()-1)));

                }


                cont += 1;


            }
            //two cards for the dealer

            dealer.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size() - 1)));
            dealer.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size() - 1)));

            System.out.println(dealer.getPlayer_color()+ "Dealer's first card is "+RESET + dealer.getHand().getFirst());

            for (HumanPlayer player : players) {
                System.out.println(player.getPlayer_color() + player.getName() + "'s turn. Score " + player.getScore() + "\n--------------------"+RESET);
                pause();
                System.out.println(player.getPlayer_color() +player.getName() + "'s hand: "+RESET + player.getHand());
               pause();
                System.out.println(player.getPlayer_color() +player.getName() + "'s total is " + whats_total(player)+RESET);
                pause();
                System.out.println(player.getPlayer_color() +player.getName()+RESET +YELLOW+ ", Would you like to get another card?"+RESET);
                pause();
                System.out.print(YELLOW+"> "+RESET);
                String answer = in.nextLine();
                while( answer.toLowerCase().equals("yes")) {
                    player.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size() - 1)));
                    System.out.println(player.getPlayer_color() + player.getName() + "'s hand: "+RESET + player.getHand());
                    pause();
                    System.out.println(player.getPlayer_color() + player.getName() + "'s total is " + whats_total(player)+RESET + "\n--------------------");
                    pause();
                    player.setTotal_cards(whats_total(player));
                    if (player.getTotal_cards() >= 21 ) {
                        break;
                    }
                    System.out.println(player.getPlayer_color() + player.getName() + RESET+YELLOW+ ", Would you like to get another card?"+RESET);
                    pause();
                    System.out.print(YELLOW+"> "+RESET);
                    answer = in.nextLine();
                }
                if (answer.toLowerCase().equals("no")){
                    System.out.println(player.getPlayer_color() +player.getName() + "'s total is " + whats_total(player) +RESET+ "\n--------------------");
                    pause();
                    player.setTotal_cards(whats_total(player));
                }

            }


            System.out.println(YELLOW+"Dealer's turn."+RESET);
            pause();
            dealer.setTotal_cards(dealer_logic(dealer, deck));
            System.out.println("Dealer's total is " + dealer.getTotal_cards());
            pause();

            for (HumanPlayer player : players) {
                if( player.getTotal_cards() == 21 && dealer.getTotal_cards() != 21){
                    System.out.println(player.getName() + " got a Blackjack! ---> " + player.getTotal_cards() + " score");
                    player.setScore(player.getScore() + 1);
                }

                else if (player.getTotal_cards() > dealer.getTotal_cards() && player.getTotal_cards() < 21) {
                    System.out.println(player.getName() + " has won this round ---> " + player.getTotal_cards() + " score");
                    player.setScore(player.getScore() + 1);
                }else if(player.getTotal_cards() == dealer.getTotal_cards()){
                    System.out.println(player.getName() + " has a push with the dealer! No one wins ---> " + player.getTotal_cards());
                }
                else if (player.getTotal_cards() < dealer.getTotal_cards() && dealer.getTotal_cards() > 21) {
                    System.out.println(player.getName() + " has won this round ---> " + player.getTotal_cards() + " score");
                    player.setScore(player.getScore() + 1);
                }
                else {
                    System.out.println(player.getName() + " has busted! Dealer wins --->" + player.getTotal_cards() + " score");
                }
            }

            System.out.println(YELLOW+"Would you like to play another round ?\n---------------\n"+RESET);
            System.out.print(YELLOW+"> "+RESET);
            String ans = in.nextLine();
            if (ans.toLowerCase().equals("no")) {
                stillPLaying = false;
            }
            else {
                round+=1;
                cont = 0;
            }
        }

    }




    @Override
    public void declareWinner() {

    }



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        StartGame game = new StartGame("\u001B[31m"+"B"+"\u001B[0m"+"\u001B[34m"+"lackjack"+"\u001B[0m" +"\u001B[31m"+" G"+"\u001B[0m"+"\u001B[34m"+"ame"+"\u001B[0m");
        System.out.println("\u001B[31m"+ "W"+"\u001B[0m"+game.BLUE+"elcome to "+game.RESET+ game.getName() +"\n--------------------------"+ game.RESET);
        System.out.println("How many players are going to play.\n1.One Player\n2.Two Players\n3.Exit\n---------------------------");
        System.out.print(game.YELLOW + "Enter your choice: "+ game.RESET);
        int opt =  in.nextInt();
        switch (opt) {
            case 1:
                game.set_up_game_names(1);
                break;
            case 2:
                game.set_up_game_names(2);

                break;

        }


    }


}