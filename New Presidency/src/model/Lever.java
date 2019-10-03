package model;

import java.util.HashMap;

public class Lever {

    //Attributs
    private String _name;
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;
    private Object HashMap;

    //Constructeur
    private Lever(String name, HashMap<Indicator,Integer> effects, Integer initBudget){
        _name=name;
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
    public String getName(){
        return _name;
    }
    /*public String getEffectName(Integer index) {
        Set listEffects = _effects.keySet();
        System.out.println(listEffects);
        return listEffects;
    }*/


    //Méthodes de classe
    public static Lever createLever(String name, HashMap<Indicator,Integer> effects, Integer initBudget){
        Lever l = new Lever(name,effects,initBudget);
        List_Levers.addLever(l);
        return l; //a voir si c'est bon... Le retour de l'objet m'est assez étrange
    }
}
