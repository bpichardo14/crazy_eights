/** Player.java
*   Author: Brayan Pichardo
*   UNI: byp2104
*
*   Player class as part of Crazy Eights
*   To be used with Game, Card, Deck classes
*
*/

import java.util.ArrayList;
import java.util.Scanner;

class Player{
    
    private ArrayList<Card> hand; // the player's hand
    private Scanner input;   

    public Player(){
        // your code here
        hand = new ArrayList<Card>(); 
        input = new Scanner(System.in);     
    }

    // Adds a card to the player's hand
    public void addCard(Card c){
        hand.add(c); 
    }
   
    // Covers all the logic regarding a human player's turn
    // public so it may be called by the Game class
    public Card playsTurn(Deck deck){ 
        String announce = "\n"+"Type 'draw' to draw a card, or type number next"+
        " to the left of the card in your hand that you wish to play"+"\n";

        String draw = input.nextLine();
        //checks if the player wants to draw again
        while ("draw".equals(draw)&& deck.canDeal()){
          hand.add(deck.deal()); 
          updateHand(); 
          System.out.println(announce);
          draw = input.nextLine(); 
        }

        //checks the reason for leaving the loop
        if (!deck.canDeal()){
          return null; 
        }
        
        //picks a card to play
        int userChoice = Integer.parseInt(draw);  
        Card temp = hand.get(userChoice-1);
        return playsCard(temp);
    }

    // Accessor for the players hand
    public ArrayList<Card> getHand(){
        return hand; 
    }

    // Returns a printable string representing the player's hand
    public String handToString(){ 
        String description = "";
        for (int i=0;i<hand.size();i++){
            description += i+1 + "\t" + hand.get(i)+"\n"; 
        }
        return description; 
    }
    
    //returns the played card and removes it from the hand
    public Card playsCard(Card c){ 
        //hand.remove(c);
        System.out.print("** You played the " + c.toString() +" **"+"\n"); 
        return c;
    }
    
    //Shows the updated hand to the player
    public void updateHand(){
        System.out.println("\n"+"Your cards are now: "+"\n"
        +handToString()); 
    }

} // end
