/**
 * SYST 17796 Project Base code.
 * Students can modify and extend to implement their game.
 * Add your name as an author and the date!
 */
package ca.sheridancollege.project;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A concrete class that represents any grouping of cards for a Game. HINT, you might want to subclass this more than
 * once. The group of cards has a maximum size attribute which is flexible for reuse.
 */
public class Deck {

    //The group of cards, stored in an ArrayList
    private ArrayList<GameCard> cards = new ArrayList<>();
    private int size;//the size of the grouping

    public Deck(int size) {
        this.size = size;
    }

    /**
     * A method that will get the group of cards as an ArrayList
     *
     * @return the group of cards.
     */
    public ArrayList<GameCard> getCards() {
        return cards;
    }

    public void addCards(GameCard card) {
        this.cards.add(card);
    }
    public GameCard getCard(int index) {
        return cards.get(index);

    }
    public GameCard getAndPop(int index){
        return cards.remove(index);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * @return the size of the group of cards
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the max size for the group of cards
     */
    public void setSize(int size) {
        this.size = size;
    }

}//end class
