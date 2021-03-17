package edu.duke.ece651.group4.RISK.server;

/*
* This class handles the state of players
* Player State:
* 1. Ready: ready to receive the message from the client
* 2. Lose: lose the game, this time, host will only send the world to each player but will not do any actions
*   from them
* 3. EndOneTurn: Finish one turn of the game, will wait for host to update the world
* 4. Quit: quit the game. If we find the winner, each thread will send winner message to each client and close the thread
* */
public class PlayerState{

    String state;
    public PlayerState(String startState){
        state = startState;
    }


    public String getState() {
        return state ;
    }

    public boolean isLose() {
        return getState().equals("Lose") ;
    }

    public boolean isQuit(){
        return getState().equals( "Quit");
    }

    public boolean isDoneOneTurn(){
        return getState().equals( "EndOneTurn") | isLose() | isQuit();
    }

    public void changeStateTo(String s){
        this.state = s;
    }
}
