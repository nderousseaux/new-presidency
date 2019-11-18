package model;

import java.util.ArrayList;
import java.util.Collection;

public class StateList {
    private Collection<State> _states;
    private static StateList _instance;
    
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
    
    private StateList(){
        _states=new ArrayList<>();
    }

    public static StateList getInstance(){
        if(_instance==null)
            _instance=new StateList();
        return _instance;
    }

    public Collection<State> getStates(){ return _states;}

    public State createState(int year, double remainingBudget, double iTauxReu, double iSatPers,
          double iSatEtu, double lDotRecForm, double lDotRecRech, double lDotSpeForm, double lPrime,
          double lImmo, double lSubAssoEtu){
        State s=new State(year,remainingBudget,iTauxReu,iSatPers,iSatEtu,lDotRecForm,lDotSpeForm,lDotRecRech,lPrime,lImmo,lSubAssoEtu);
        _states.add(s);
        return s;
    }
}