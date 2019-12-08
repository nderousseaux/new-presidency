package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lever extends IndicLever {

    //Attributs

    private double _budget;
    private double _maxBudget;
    private String _category;

    //Constructeur
    public Lever(String name, String abreviation, String category, double initBudget, double maxBudget, ArrayList<String> infos){
        super(name, abreviation, infos);

        _category = category;
        _budget=initBudget;
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
        else{
            _budget=budget;   
        }
    }
    public double getMaxBudget() {
        return _maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        _maxBudget = maxBudget;
    }

    public String getCategory() {
        return _category;
    }
}
