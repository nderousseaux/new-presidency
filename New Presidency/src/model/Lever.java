package model;

import java.util.ArrayList;

public class Lever extends IndicLever {

    //Attributs

    private double _budget;
    private double _maxBudget;
    private double _minBudget;
    private String _category;

    //Constructeur
    public Lever(String name, String abreviation, String category, double initBudget, double minBudget, double maxBudget, ArrayList<String> infos){
        super(name, abreviation, infos);

        _category = category;
        _budget=initBudget;
        _minBudget=minBudget; 
        _maxBudget=maxBudget;
    }

    //Accesseurs
    public double getBudget(){
        return _budget;
    }

    public void setBudget(double budget){
        if(budget > _maxBudget){
            throw new IllegalArgumentException("try to set Lever budget with a value superior to the Lever's max value");
        }
        else if(budget < _minBudget){
            throw new IllegalArgumentException("try to set Lever budget with a value inferior to the Lever's min value");
        }
        else{
            _budget=budget;   
        }
    }


    public double getMinBudget() {
        return _minBudget;
    }

    public double getMaxBudget() {
        return _maxBudget;
    }


    public void setMinBudget(double minBudget) {
        _minBudget = minBudget;
    }

    public void setMaxBudget(double maxBudget) {
        _maxBudget = maxBudget;
    }

    public String getCategory() {
        return _category;
    }
}
