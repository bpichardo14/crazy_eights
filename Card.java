/** Card.java
*   Author: Brayan Pichardo
*   UNI: byp2104
*   
*   Models a typical playing card
*/
class Card{
    
    private char suit;
    private int rank;

    // Initializes a card instance
    public Card(char suit, int rank){
        this.suit = suit; 
        this.rank = rank; 
    }

    // Accessor for suit
    public char getSuit(){
        return suit; 
    }
    
    // Accessor for rank
    public int getRank(){
        return rank; 
    }

    // Returns a human readable form of the card (eg. King of Diamonds)
    public String toString(){
        String description = rank + " of ";
        String[] suits={"Diamonds","Spades","Clubs","Hearts"};
        if (rank == 1)
            description = "Ace of ";
        if (rank == 11)
            description = "Jack of ";
        if (rank == 12)
            description = "Queen of ";
        if (rank == 13)
            description = "King of "; 
 
        for (int i=0;i<suits.length;i++){
            char char2= Character.toLowerCase(suits[i].charAt(0));
            if(char2 == suit){
                description += suits[i];
                return description;
            }
        }
        return null;  
    }
}