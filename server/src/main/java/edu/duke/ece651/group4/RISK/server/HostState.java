package edu.duke.ece651.group4.RISK.server;

import java.util.HashSet;

public class HostState{
    HashSet<PlayerState> statsOfPlayers;
    String hostState;

    public HostState(){
        hostState = null;
        statsOfPlayers = new HashSet<PlayerState>();
    }



    public void addOnePlayerState(PlayerState s){
        statsOfPlayers.add(s);
    }

    public boolean isReadyToUpdate(){
        for (PlayerState s: statsOfPlayers){
            if(s.isReadyToDoAction()){
                return false;
            }
        }
        return true;
    }

    public boolean isFinishUpdate(){
        return hostState.equals("finishUpdateMap") ;
    }

    public boolean isAllPlayersDoneOneTurn(){
        for(PlayerState s: statsOfPlayers){
            if(!s.isFinishOneAction()){
                return false;
            }
        }
        return false;
    }

    public boolean isEndGame(){
        int num = statsOfPlayers.size();
        int numLosers = 0;
        for(PlayerState s: statsOfPlayers){
            if(s.isLose()){
                numLosers += 1;
            }
        }
        return numLosers == (num-1);
    }

    public void changeStateTo(String s){
        this.hostState = s;
    }

    public boolean isALlThreadsQuit(){
        for(PlayerState s: statsOfPlayers){
            if(!s.isQuit()){
                return false;
            }
        }
        return true;
    }
//    public boolean isEndGame(){
//        return hostState.equals("endGame");
//    }

}
