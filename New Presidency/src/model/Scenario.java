package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


public class Scenario extends IndicLever {


    private HashMap<String, Double> _start;
    private HashMap<String, Double> _victory;
    private HashMap<String, Double> _defeat;

    public Scenario(String name, String abreviation, HashMap<String, Double> start, HashMap<String, Double> victory, HashMap<String, Double> defeat, Collection<String> infos){
        super(name, abreviation, infos);
        _defeat=defeat;
        _victory = victory;
        _start = start;
    }

    public HashMap<String, Double> getStart() {
        return _start;
    }

    public HashMap<String, Double> getVictory() {
        return _victory;
    }

    public HashMap<String, Double> getDefeat() {
        return _defeat;
    }
}
