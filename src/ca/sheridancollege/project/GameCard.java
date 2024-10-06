package ca.sheridancollege.project;

public class GameCard extends Card {
    private String card_value;
    private String name;
    private String card_suit;
    private String[] suits = {
            "\u001B[31m" + '\u2665' + "\u001B[0m",
            "\u001B[31m" + '\u2666' +"\u001B[0m",
             "\u2663",
             "\u2660"
    };
    private String[] cardValues = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    public GameCard() {

    }
    public GameCard(String value, String suit) {
        this.card_suit = suit;
        this.card_value = value;
    }


    @Override
    public String toString() {
        return card_value +" "+ card_suit;
    }

    public String getCardValue() {
        return card_value;
    }

    public String getCardSuit() {
        return card_suit;
    }


    public String[] getSuits() {
        return suits;
    }

    public String[] getValues() {
        return cardValues;
    }

    public String getName() {
        return this.name ;
    }
}
