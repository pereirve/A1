package ca.sheridancollege.project;


import java.util.ArrayList;

public class HumanPlayer extends Player{
    private ArrayList<GameCard> hand = new ArrayList<>();
    private int total_cards;
    private int score;
    public HumanPlayer(String playerName) {
        super(playerName);
    }



    private String player_color;


    @Override

    public void play(){
        System.out.println(this.getName()+ " is playing,");
    }



    public ArrayList<GameCard> getHand(){

        return this.hand;
    }

    public void setHand(GameCard card){

        this.hand.add(card);
    }

    public GameCard viewDealerCard(){
        return this.hand.getFirst();
    }
    public int getScore() {
        return score;
    }

    public void resetHand(){
        this.hand.clear();
    }
    public void setScore(int score) {
        score = score;
    }

    public void printCurrentHand(){
        System.out.println(this.getName()+"'s hand: "+this.getHand());
    }

    public int getTotal_cards() {
        return total_cards;
    }

    public void setTotal_cards(int total_cards) {
        this.total_cards = total_cards;
    }

    public String getPlayer_color() {
        return player_color;
    }

    public void setPlayer_color(String player_color) {
        this.player_color = player_color;
    }

}
