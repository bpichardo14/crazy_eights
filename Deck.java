/** Deck.java
*   Author: Brayan Pichardo
*   UNI: byp2104
*   
*   Models a typical deck of playing cards
*   To be used with Card class
*
*/
import java.lang.Math; 
class Deck{

    private Card[] deck; // contains the cards to play with
    private int top; // controls the "top" of the deck to deal from. top of deck is 0
    public static final char[] suits = {'c','d','h','s'}; 
    public static final int[] rank = {1,2,3,4,5,6,7,8,9,10,11,12,13}; 

    // constructs a default Deck
    public Deck(){
        top = 0;
        deck = new Card[52]; 
        int pos = 0; // keeps track of where in the deck we insert the card. 
        for (int i=0;i<4;i++){
            for (int j=0;j<13;j++){
                Card temp = new Card(suits[i],rank[j]); //creates an object  
                deck[pos] = temp; 
                pos++; 
            }
        }
    }

    // Deals the top card off the deck
    public Card deal(){
        top++;
        Card topCard = deck[top-1]; //gets the top card of the deck
        return topCard; 

    }

    // returns true provided there is a card left in the deck to deal
    public boolean canDeal(){
        if ( top >= deck.length) return false; 
        return true; 
    }

    // Shuffles the deck
    public void shuffle(){
        Card temp;
        for (int i=0;i<100;i++){
            int index1 = (int)(Math.random()*52); 
            int index2 = (int)(Math.random()*52);
            temp = deck[index1];
            deck[index1] = deck[index2];
            deck[index2] = temp; 
        }
    }

    // Returns a string representation of the whole deck
    public String toString(){
       String report = "This is my deck of cards: " + "\n\n"; 
       for (Card cards: deck)
           report += (cards + "\n");
    
        return report; 
    } 
}