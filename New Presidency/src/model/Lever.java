package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lever extends IndicLever {

    //Attributs

    private double _budget;

    //Constructeur
    public Lever(String name, double initBudget,ArrayList<String> infos){
        super(name,infos);

        _budget=initBudget;
    }


    //Accesseurs
    public double getBudget(){
        return _budget;
    }
    public void setBudget(double budget){
        _budget=budget;
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
