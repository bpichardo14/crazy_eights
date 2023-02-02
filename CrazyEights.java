/** CrazyEights.java
*   
*   Do not modify this one
*   
*   Plays a game of crazy eights in commandline
*   Keeps playing while the game play method returns true
*
*/

class CrazyEights{
    public static void main(String[] args){
        boolean keepPlaying = true;

        while(keepPlaying){
            Game g = new Game();
            keepPlaying = g.play(); 
        }
    }
}