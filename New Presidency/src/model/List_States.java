package model;

import java.util.ArrayList;
import java.util.Collection;

public class List_States{
    private Collection<State> _states = new ArrayList<>();
    
    public void addState(State s){
        _states.add(s);
    }
    
    public State getState(int year){ //YOAN: idem, remplacer par des semestres?
        State selectedState = null;
        for(State s : _states){
            if(s.getYear()== year){
                selectedState = s;
            }
        }
        return selectedState;
    }
    
    
}