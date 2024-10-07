package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class StartGame extends Game{
    ArrayList<HumanPlayer> players = new ArrayList<>();
    Scanner in = new Scanner(System.in);
    Random rand = new Random();
    private String RED ="\u001B[31m";
    private String PURLPLE ="\u001B[35m";
    private String RESET ="\u001B[0m";
    private String YELLOW ="\u001B[33m";
    private String BLUE ="\u001B[34m";
    private String GREEN ="\u001B[32m";
    private String CYAN = "\u001B[36m";

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

    public int dealer_logic(HumanPlayer dealer, Deck deck,ArrayList<HumanPlayer> players) {
        System.out.println(dealer.getPlayer_color()+"Dealer's hand"+ RESET  + dealer.getHand()+"\n----------------\n");
        pause();
        int total = whats_total(dealer);
        boolean less_than_player=false;

        while (total <=17 ) {
            for(HumanPlayer player : players) {
                if (player.getTotal_cards()>=total && player.getTotal_cards()<21){
                    less_than_player=true;
                }

            }
            if(less_than_player){
                dealer.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size())));
                System.out.println(dealer.getPlayer_color()+"Dealer got a new card."+RESET);
                pause();
                System.out.println(dealer.getPlayer_color()+"Dealer's hand: "+RESET + dealer.getHand());
                pause();
                total = whats_total(dealer);
                pause();
                break;
            }
                else {
                total = whats_total(dealer);

                pause();
                break;
            }


        }
        System.out.println(dealer.getPlayer_color()+"Dealer's total is " + total+RESET);
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



    public void set_up_game_cards(){
        Deck deck = build_deck();
        play(deck);
    }

    public void set_up_game_names(int num_of_players){
        int cont = 0;
        System.out.println(PURLPLE+"You have selected "+RESET + num_of_players + " Player"+(num_of_players>1? "s": "" )+ " \n"+PURLPLE+"Please enter your player"+(num_of_players>1? "s ": " " )+"name"+(num_of_players>1? "s": "" )+".\n"+RESET);
        pause();
        while (cont < num_of_players){
            System.out.print(YELLOW+ "name" +(cont+1) +RESET +": ");
            String name = in.nextLine();
            HumanPlayer player = new HumanPlayer(name);
            if (cont == 0){
                player.setPlayer_color(RED);
                System.out.println(RED + player.getName() + ": Will be RED." +RESET);
                pause();
            }
            else if (cont == 1) {
                player.setPlayer_color(BLUE);
                System.out.println(BLUE + player.getName() + ": Will be BLUE."+RESET);
                pause();
            }
            else if (cont == 2) {
                player.setPlayer_color(GREEN);
                System.out.println(GREEN + player.getName() + ": Will be GREEN."+RESET);
                pause();
            }
            else {
                player.setPlayer_color(CYAN);
                System.out.println(CYAN + player.getName() + ": Will be CYAN."+RESET);
                pause();
            }
            this.players.add(player);
            cont+=1;

        }


        System.out.println(YELLOW + "Dealer: Will be YELLOW."+RESET);
        set_up_game_cards();

    }

    public void exit_animation(){
        String message = "Exiting";
        StringBuilder dots = new StringBuilder("...");

        for (int i = 0; i < 6; i++) {

            System.out.print("\r" + message + dots);
            pause();
            dots.append(".");
            if (dots.length() > 3) {
                dots.setLength(1);
            }
        }

        System.out.println("\nExited.");
    }

    public void rules(){
        System.out.println("\n" +
                "                                                                    .__                 \n" +
                "                                                       _______ __ __|  |   ____   ______\n" +
                "                                                       \\_  __ \\  |  \\  | _/ __ \\ /  ___/\n" +
                "                                                       |  | \\/  |  /  |_\\  ___/ \\___ \\ \n" +
                "                                                       |__|  |____/|____/\\___  >____  >\n" +
                "                                                                              \\/     \\/ \n");
        System.out.println("                                                Welcome to Blackjack!");
        System.out.println("                                       The objective of the game is to beat the dealer's hand without going over 21.");
        System.out.println("                                       Card values are as follows:");
        System.out.println("                                        - Face cards (K, Q, J) are worth 10 points.");
        System.out.println("                                        - Number cards are worth their face value.");
        System.out.println("                                        - Aces are worth 1 or 11 points, whichever benefits the player.");
        System.out.println("                                       Gameplay:");
        System.out.println("                                        - Both the player and the dealer are dealt two cards. The player’s cards are face-up.");
        System.out.println("                                        - The player can choose to 'Hit' to take another card, or 'Stand' to keep their current hand.");
        System.out.println("                                        - The dealer must hit until their total is at least 17.");
        System.out.println("                                        - If the player or dealer exceeds 21 points, they 'Bust' and lose.");
        System.out.println("                                        - The player wins if their hand is higher than the dealer's without busting, or if the dealer busts.");
        System.out.println("                                        Good luck!");

    }



    @Override
    public void play(Deck deck) {
        HumanPlayer dealer = new HumanPlayer("Dealer");
        dealer.setPlayer_color(YELLOW);
        int round = 1;
        Random rand = new Random();
        int cont = 0;
        boolean stillPLaying = true;
        boolean exit_flag = true;


        while (stillPLaying) {
            System.out.println(PURLPLE+"Round: "+RESET+ round+ "\n----------------\n" );
            System.out.println(PURLPLE+"Cards in the deck: "+RESET+deck.getSize());

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
                pause();
                System.out.println(player.getPlayer_color() + player.getName() + "'s turn. Score " + player.getScore() + "\n--------------------" + RESET);
                pause();
                System.out.println(player.getPlayer_color() + player.getName() + "'s hand: " + RESET + player.getHand());
                pause();
                System.out.println(player.getPlayer_color() + player.getName() + "'s total is " + whats_total(player) + RESET);
                pause();
                if(player.getTotal_cards()==21){
                    continue;
                }
                System.out.println(player.getPlayer_color() + player.getName() + RESET + PURLPLE + ", Would you like to get another card? (STAND/ HIT)" + RESET);
                pause();
                System.out.print(PURLPLE + "> " + RESET);
                String answer = in.nextLine();

                while (!answer.toLowerCase().equals("stand") && !answer.toLowerCase().equals("hit")) {
                    System.out.println(player.getPlayer_color() + player.getName() + RESET + PURLPLE + ", Would you like to get another card? (STAND/ HIT)" + RESET);
                    pause();
                    System.out.print(PURLPLE + "> " + RESET);
                    answer = in.nextLine();
                }

                while (answer.toLowerCase().equals("hit")) {

                        System.out.println(player.getPlayer_color() + player.getName() + " decided to hit " + RESET);
                        player.setHand(deck.getAndPop(rand.nextInt(deck.getCards().size() - 1)));
                        System.out.println(player.getPlayer_color() + player.getName() + "'s new hand: " + RESET + player.getHand());
                        pause();
                        System.out.println(player.getPlayer_color() + player.getName() + "'s new total is " + whats_total(player) + RESET + "\n--------------------");
                        pause();
                        player.setTotal_cards(whats_total(player));
                        if (player.getTotal_cards() >= 21) {
                            break;
                        }
                        answer= "";
                        while (!answer.toLowerCase().equals("stand") && !answer.toLowerCase().equals("hit")) {
                        System.out.println(player.getPlayer_color() + player.getName() + RESET + PURLPLE + ", Would you like to get another card? (STAND/ HIT)" + RESET);
                        pause();
                        System.out.print(PURLPLE + "> " + RESET);
                        answer = in.nextLine();
                    }

                    }
                    if (answer.toLowerCase().equals("stand")) {
                        System.out.println(player.getPlayer_color() + player.getName() + " decided to stand " + RESET);
//                    System.out.println(player.getPlayer_color() +player.getName() + "'s total is " + whats_total(player) +RESET+ "\n--------------------");
                        pause();
                        player.setTotal_cards(whats_total(player));
                    }


            }


            System.out.println(YELLOW+"Dealer's turn."+RESET);
            pause();
            dealer.setTotal_cards(dealer_logic(dealer, deck, players));
            pause();

            for (HumanPlayer player : players) {
                if( player.getTotal_cards() == 21 && dealer.getTotal_cards() != 21){
                    System.out.println(player.getPlayer_color() + player.getName() + " got a Blackjack! ---> " + player.getTotal_cards() + " score"+ RESET);
                    player.setScore(player.getScore() + 1);
                }

                else if (player.getTotal_cards() > dealer.getTotal_cards() && player.getTotal_cards() < 21) {
                    System.out.println(player.getPlayer_color() + player.getName() + " has won this round ---> " + player.getTotal_cards() + " score" + RESET);
                    player.setScore(player.getScore() + 1);
                }else if(player.getTotal_cards() == dealer.getTotal_cards() && player.getTotal_cards() <=21) {
                    System.out.println(player.getPlayer_color() + player.getName() + " has a push with the dealer! No one wins ---> " + player.getTotal_cards() + " score" + RESET);
                }
                else if (player.getTotal_cards() < dealer.getTotal_cards() && dealer.getTotal_cards() > 21) {
                    System.out.println(player.getPlayer_color() +player.getName() + " has won this round ---> " + player.getTotal_cards() + " score" + RESET);
                    player.setScore(player.getScore() + 1);
                }

                else {
                    System.out.println(player.getPlayer_color() + player.getName() + " has busted! Dealer wins --->" + player.getTotal_cards() + " score"+ RESET);
                }
            }

            while(exit_flag){
            System.out.println(PURLPLE+"Would you like to play another round ? (Yes / No)\n---------------\n"+RESET);
            pause();
            System.out.print(PURLPLE+"> "+RESET);
            String ans= in.nextLine();
            if (ans.toLowerCase().equals("no")) {
                stillPLaying = false;
                exit_flag = false;
                break;
            }
            else if (ans.toLowerCase().equals("yes")) {
                round+=1;
                cont = 0;
                exit_flag = true;
                break;
            }
            else {
                continue;
            }
            }
        }
        }






    @Override
    public void declareWinner() {

    }



    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean menu_flag = true;
        StartGame game = new StartGame("\n" +
                "                       ▀█████████▄   ▄█          ▄████████  ▄████████    ▄█   ▄█▄      ▄█    ▄████████  ▄████████    ▄█   ▄█▄ \n" +
                "                         ███    ███ ███         ███    ███ ███    ███   ███ ▄███▀     ███   ███    ███ ███    ███   ███ ▄███▀ \n" +
                "                         ███    ███ ███         ███    ███ ███    █▀    ███▐██▀       ███   ███    ███ ███    █▀    ███▐██▀   \n" +
                "                        ▄███▄▄▄██▀  ███         ███    ███ ███         ▄█████▀        ███   ███    ███ ███         ▄█████▀    \n" +
                "                       ▀▀███▀▀▀██▄  ███       ▀███████████ ███        ▀▀█████▄        ███ ▀███████████ ███        ▀▀█████▄    \n" +
                "                         ███    ██▄ ███         ███    ███ ███    █▄    ███▐██▄       ███   ███    ███ ███    █▄    ███▐██▄   \n" +
                "                         ███    ███ ███▌    ▄   ███    ███ ███    ███   ███ ▀███▄     ███   ███    ███ ███    ███   ███ ▀███▄ \n" +
                "                       ▄█████████▀  █████▄▄██   ███    █▀  ████████▀    ███   ▀█▀ █▄ ▄███   ███    █▀  ████████▀    ███   ▀█▀ \n" +
                "                                    ▀                                   ▀         ▀▀▀▀▀▀                            ▀         \n");

        System.out.println(game.RED + game.getName()+game.RESET +"\n");

        while(menu_flag) {
            System.out.println( "                                            ------------------------------------------------------\n" +

                    "                                           |                                                      |\n                                           |" +
                    "                                                      |\n" +
                    "                                           |        _____   ____   ____  __ __                    |\n" +
                    "                                           |       /     \\_/ __ \\ /    \\|  |  \\                   |\n" +
                    "                                           |       |  Y Y  \\  ___/|   |  \\  |  /                  |\n" +
                    "                                           |       |__|_|  /\\___  >___|  /____/                   |\n" +
                    "                                           |            \\/     \\/     \\/                          |\n" +
                    "                                           |                                                      |");
            System.out.println("                                           |                                                      |\n" +
                    "                                           |               1. Single Player                       |\n" +
                    "                                           |               2. Multiplayer                         |\n" +
                    "                                           |               3. How to play                         |\n" +
                    "                                           |               4. Exit                                |\n" +
                    "                                            ------------------------------------------------------");
            System.out.print(game.PURLPLE + "\nEnter your choice: " + game.RESET);
            int opt = in.nextInt();
            switch (opt) {
                case 1:
                    game.set_up_game_names(1);
                    break;
                case 2:
                    int quantity = 0;
                    while (quantity < 2 || quantity > 4) {
                        System.out.println(game.PURLPLE+ "How many players do you want? (2-4)"+game.RESET);
                        System.out.print(game.PURLPLE+"> "+game.RESET);
                        quantity = in.nextInt();
                        if (quantity < 2 || quantity > 4) {
                            System.out.println(game.PURLPLE+"Invalid number of players. Please choose between 2 and 4."+game.RESET);
                        }
                    }
                    game.set_up_game_names(quantity);
                    break;


                    case 3:
                        game.rules();
                        break;
                    case 4:
                        menu_flag = false;
                        game.exit_animation();
                        System.exit(0);
                        break;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }


    }


}