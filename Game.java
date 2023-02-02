/** Game.java
*   Author: Brayan Pichardo
*   UNI: byp2104
*   
*   Game class for playing crazy eights in commandline
*   To be used with Player, Card, Deck classes
*
*/

import java.util.Scanner;
import java.util.ArrayList;

class Game{

  private char currentSuit; // need in case an 8 is played
  private Card faceup; 
  private Scanner input;
  private Player p1;
  private ArrayList<Card> compHand;
  private Deck cards;
  
  // sets up the Game object for play
  public Game(){

    input = new Scanner(System.in);
    p1 = new Player(); 
    compHand = new ArrayList<Card>(); 
    cards = new Deck();

    faceup = cards.deal(); 
    currentSuit = faceup.getSuit();
    cards.shuffle(); 

    directions();
    faceupInformation();
    updateSuitMessage(currentSuit);  
    initialDeal();  
  
  }
  // Plays a game of crazy eights. 
  // Returns true to continue playing and false to stop playing
  public boolean play(){

    //checks if players can play another round
    while (!endGame()){

      //player plays card
      Card temp = p1.playsTurn(cards); 

      //checks if the player played the correct card
      faceup = filterPlayerCard(temp); //a change of suit can happen in here
      if (faceup == null||p1.getHand().size()==0){
        return playAgain(); 
      }

      //computer plays a card 
      faceup = computerTurn();
      if (faceup == null || compHand.size()==0){
        return playAgain(); 
      }

      generalInformation(); 
    } 

   return playAgain(); 
  }

  /* Naive computer player AI that does one of two actions:
      1) Plays the first card in their hand that is a valid play
      2) If no valid cards, draws until they can play

      You may choose to use a different approach if you wish but
      this one is fine and will earn maximum marks
    */
  private Card computerTurn(){

    //plays the card
    Card temp = playCard();

    //make sure that the card did not return null  
    if (temp != null){
      currentSuit = temp.getSuit();
      System.out.print("** The computer played the "+temp.toString()+" **"+"\n");
    }

    //checks if the card is special 
    checkSpecial(temp);  

    //end of method 
    return temp; //this could return null 

  }

/**************************Additional Methods***********************/

  private void directions(){
    String description;
    description = "\n\n"
    +"Welcome to Crazy Eights! You'll start with 7 cards."
    +"\nYour job is to match a card in your hand with the up card."
    +"\nYou can match it by suit or rank." 
    +"\nIf you play an 8, you can switch the active suit."
    +"\nIf you run out of cards, you win!"
    +"\nIf you make it through the whole deck then whoever has"
    +"\nthe fewest cards left wins!"
    +"\nGood luck!"+"\n"; 
    System.out.println(description); 
  }

  //takes in a players card and shows the current suit
  private void updateSuitMessage(char c){
    String suitDescription = "The current suit is "; 
    if (c=='c') suitDescription+="Clubs";
    if (c=='d') suitDescription+="Diamonds";   
    if (c=='h') suitDescription+="Hearts"; 
    if (c=='s') suitDescription+="Spades";
    System.out.println(suitDescription);  
  } 

  //displays information about the current faceup card
  private void faceupInformation(){
    System.out.println("\n"+"**The up card is the " + faceup+" **");  
  }

  //prints out information useful to the player 
  public void generalInformation(){
    faceupInformation(); 
    updateSuitMessage(currentSuit);
    System.out.println("\n"+"Type 'draw' to draw a card, or type the number "+
    "next to the card in your hand that you wish to play"); 
    System.out.println("\n"+"Your cards are:\n"+p1.handToString());
  }

  //deals the first set of cards to p1 and computer player
  private void initialDeal(){
    //deals cards
    for (int i=0;i<7;i++){
      p1.addCard(cards.deal());
      compHand.add(cards.deal());  
    }
    //show players hand 
    System.out.println("\n"+"Type 'draw' to draw a card, or type the number "+
    "next to the card in your hand that you wish to play");
    System.out.println("\n"+"Your cards are:\n"+p1.handToString()); 
  }

  //method for checking if player is behaving
  private Card filterPlayerCard(Card c){
    Card temp = c;
    //checks if the player drew to the end of the deck
    if (temp == null){
      System.out.println("You drew to the end of the deck");
      return null; 
    }

    //checks if wrong card was played & allows player to choose new card
    while (canItBePlayed(temp)==false){
      System.out.println("You cannot play this card, please try again! \n");
      generalInformation(); 
      temp = p1.playsTurn(cards); 
    }
    
    //updates currentsuit after after p1 plays 
    currentSuit = temp.getSuit(); 

    //checks if the player plays an 8
    humanCheckSpecial(temp); 
  
    //remove card from deck after playing it 
    p1.getHand().remove(temp);
    return temp; 
  }

  //checks for the crazy eight card for the human player
  private void humanCheckSpecial(Card c){
    if (c.getRank() == 8){
      System.out.println("Choose a suit to set! (c,h,d,s)");
      currentSuit = input.next().charAt(0); 
      updateSuitMessage(currentSuit);  
    }
  } 

  //plays a card for computer 
  private Card playCard(){

    Card temp = null;
    
    for (int i=0;i<compHand.size();i++){
      if (canItBePlayed(compHand.get(i))==true){
        temp =  compHand.get(i);
        //update currentSuit
        currentSuit = compHand.get(i).getSuit(); 
        //removes card after playing 
        compHand.remove(temp);
        break;   
      }
    }

    //if comp still hasn't found a car, draw
    if (temp ==null){
      temp = drawnCard(); 
    }
    return temp; //return null
  }

  //method that allows the computer draw
  private Card drawnCard(){
    //draws an initial card 
    Card temp = cards.deal();

    //checks the card
    while (temp != null && cards.canDeal()==true && canItBePlayed(temp)==false){
      System.out.println("The computer drew a card"); 
      temp = cards.deal(); //this could be null
    }

    //checks if the computer drew to the end of deck; 
    if (temp==null){
      System.out.println("The computer drew to the end of deck"); 
    } 

    return temp; 
  }

  //checks if a card can be played 
  private boolean canItBePlayed(Card c){
    if (c == null) return false; 
    if (c.getSuit()==currentSuit||c.getRank()==faceup.getRank()||c.getRank()==8){ 
      return true; 
    }
    return false; 
  }

  //checks for an 8 & picks a random suit for the computer 
  private void checkSpecial(Card c){
    if (c.getRank() == 8){
      currentSuit = Deck.suits[(int)Math.random()*4];
      System.out.println("**** Computer changed the suit ****");
      updateSuitMessage(currentSuit); 
    }
  }

  //checks for all of the conditions that could end the game
  private boolean endGame(){
    if (cards.canDeal() == false ||compHand.size()==0|| p1.getHand().size()==0){
      return true; 
    }
    return false; 
  }

  //declares the winner at the end of the game 
  private void declareWinner(){
    if (!cards.canDeal()){
      System.out.println("We've reached the end of the deck!"); 
    }
    String description = "You have "+p1.getHand().size()+" cards left";
    description += "\nThe computer has "+compHand.size()+" cards left";
    if (p1.getHand().size()<compHand.size()){
      description += "\nYou win!"; 
    }
    else{
      description += "\nYou Loose!";
    }
    System.out.print(description);
  }

  //asks the player if they would like to play again
  private boolean playAgain(){
    declareWinner(); 
    System.out.println("\n"+"Do you want to keep playing (y/n)?");
    return input.next().charAt(0) == 'y'; 
  }

} //end
