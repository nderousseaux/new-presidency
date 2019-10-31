package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lever extends IndicLever {

    //Attributs

    private double _budget;
    private double _maxBudget; 

    //Constructeur
    public Lever(String name, double initBudget, double maxBudget, ArrayList<String> infos){
        super(name,infos);

        _budget=initBudget;
        _maxBudget=maxBudget;
    }


    //Accesseurs
    public double getBudget(){
        return _budget;
    }
    public void setBudget(double budget){
        _budget=budget;
    }
    public double getMaxBudget() {
        return _maxBudget;
    }

    /*public String getEffectName(double index) {
        Set listEffects = _effects.keySet();
        System.out.println(listEffects);
        return listEffects;
    }*/


    //Méthodes de classe


    public void addToBudget(double val){
        setBudget(getBudget()+val);
    }

    public void removeFromBudget(double val){
        setBudget(getBudget()-val);
    }
}
