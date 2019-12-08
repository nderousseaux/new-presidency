package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StateList {
    private Collection<State> _states;
    
    public void addState(State s){
        _states.add(s);
    }
    
    public State getState(int year){ //YOAN: idem, rebmplacer par des semestres?
        State selectedState = null;
        for(State s : _states){
            if(s.getYear()== year){
                selectedState = s;
            }
        }
        return selectedState;
    }
    
    public StateList(){
        _states=new ArrayList<>();
    }

    public Collection<State> getStates(){ return _states;}

    public State createState(int year, double remainingBudget, HashMap<String, Double> levers, HashMap<String, Double> indicators){
        State s = new State(year, remainingBudget, levers, indicators);
        _states.add(s);
        return s;
    }
}