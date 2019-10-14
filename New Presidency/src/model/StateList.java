package model;

import java.util.ArrayList;
import java.util.Collection;

public class StateList {
    private Collection<State> _states;
    private static StateList _instance;
    
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
    
    private StateList(){
        _states=new ArrayList<>();
    }

    public static StateList getInstance(){
        if(_instance==null)
            _instance=new StateList();
        return _instance;
    }

    public Collection<State> getStates(){ return _states;}
}