package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Lever {

    //Attributs
    private String _name;
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;
    private ArrayList<String> _infos;

    //Constructeur
    private Lever(String name, HashMap<Indicator,Integer> effects, Integer initBudget, ArrayList<String> infos){
        _name=name;
        _effects=effects;
        _budget=initBudget;
        _infos=infos;
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
    public static Lever createLever(String name, HashMap<Indicator,Integer> effects, Integer initBudget, ArrayList<String> infos){
        Lever l = new Lever(name,effects,initBudget,infos);
        List_Levers.addLever(l);
        return l; //a voir si c'est bon... Le retour de l'objet m'est assez étrange
    }

    public void addToBudget(Integer val){
        setBudget(getBudget()+val);
    }

    public void removeFromBudget(Integer val){
        setBudget(getBudget()-val);
    }


}
