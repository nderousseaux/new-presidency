package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lever extends IndicLever {

    //Attributs
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;

    //Constructeur
    Lever(String name, HashMap<Indicator,Integer> effects, Integer initBudget, ArrayList<String> infos){
        super(name,infos);
        _effects=effects;
        _budget=initBudget;
    }


    //Accesseurs
    public Integer getBudget(){
        return _budget;
    }
    public void setBudget(Integer budget){
        _budget=budget;
    }
    public HashMap<Indicator,Integer> getEffects(){
        return _effects;
    }
    /*public String getEffectName(Integer index) {
        Set listEffects = _effects.keySet();
        System.out.println(listEffects);
        return listEffects;
    }*/


    //Méthodes de classe


    public void addToBudget(Integer val){
        setBudget(getBudget()+val);
    }

    public void removeFromBudget(Integer val){
        setBudget(getBudget()-val);
    }
}
