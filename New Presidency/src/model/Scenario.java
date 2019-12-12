package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Scenario extends IndicLever {


    private HashMap<String, Double> _depart;
    private HashMap<String, Double> _victoire;
    private HashMap<String, Double> _defaite;

    public Scenario(String name, String abreviation, HashMap<String, Double> depart, HashMap<String, Double> victoire, HashMap<String, Double> defaite, Collection<String> infos){
        super(name, abreviation, infos);
        _defaite=defaite;
        _victoire = victoire;
        _depart = depart;
    }

    public HashMap<String, Double> get_depart() {
        return _depart;
    }

    public HashMap<String, Double> get_victoire() {
        return _victoire;
    }

    public HashMap<String, Double> get_defaite() {
        return _defaite;
    }
}
