package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ScenarioList {

    //Attributs
    private Collection<Scenario> _scenarios;


    public ScenarioList(){
        _scenarios=new ArrayList<>();
    }

    //Accesseurs
    public Scenario createScenario(String name, String abreviation, HashMap<String, Double> depart, HashMap<String, Double> victoire, HashMap<String, Double> defaite, Collection<String> infos){
        Scenario s = new Scenario(name, abreviation, depart, victoire, defaite, infos);
        _scenarios.add(s);
        return s;
    }

    public Collection<Scenario> getScenarios(){
        return _scenarios;
    }

    public Scenario getScenario(String name){
        Scenario result = null;
        for(Scenario s : _scenarios){
            if(s.getName().equals(name)){
                result = s;
            }
        }
        return result;
    }
    
    public Scenario getScenarioByAbreviation(String abreviation){
        Scenario result = null;
        for(Scenario s : _scenarios){
            if(s.getAbreviation().equals(abreviation)){
                result = s;
            }
        }
        return result;
    }
    
}
