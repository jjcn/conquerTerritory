package edu.duke.ece651.group4.RISK.server;

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

    public boolean isOnMove(){
        return getState().equals("M");
    }
    public boolean isOnAttack(){
        return getState().equals("A");
    }

    public boolean isFinishOneAction(){
        return getState().equals( "E") | getState().equals("Lose");
    }

    public boolean isQuit(){
        return getState().equals( "Quit");
    }
    public boolean isReadyToDoAction(){
        return !isFinishOneAction();
    }


    public void changeStateTo(String s){
        this.state = s;
    }
}
