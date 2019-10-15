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

    public State createState(Integer year, Integer remainingBudget, double iTauxReu, double iSatPers,
          double iSatEtu, Integer lDotRecForm, Integer lDotSpeForm, Integer lPrime,
          Integer lImmo, Integer lSubAssoEtu){
        State s=new State(year,remainingBudget,iTauxReu,iSatPers,iSatEtu,lDotRecForm,lDotSpeForm,lPrime,lImmo,lSubAssoEtu);
        _states.add(s);
        return s;
    }
}