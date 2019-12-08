package model;

import java.util.HashMap;

public class State{
    //attributs
    private int _year;
    private double _remainingBudget;
    private HashMap<String, Double> _levers;
    private HashMap<String, Double> _indicators;
    
    //constructeur
    public State(Integer year, double remainingBudget, HashMap<String, Double> levers, HashMap<String, Double> indicators){
        _year = year;
        _remainingBudget=remainingBudget;
        _levers = levers;
        _indicators = indicators;
    }

    public Integer getYear(){return _year;}

    public Double getRemainingBudget(){return _remainingBudget;}
    
    public Double getLever(String abreviation) throws IllegalArgumentException{
        if(!_levers.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        return _levers.get(abreviation);
    }

    public Double getIndicator(String abreviation) throws IllegalArgumentException{
        if(!_indicators.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        return _indicators.get(abreviation);
    }

    public HashMap<String, Double> getLevers(){
        return _levers;
    }

    public HashMap<String, Double> getIndicators() {
        return _indicators;
    }

    public void setLever(String abreviation, Double value) throws IllegalArgumentException{
        if(!_levers.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        _levers.replace(abreviation, value);
    }
}
