package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LeverList {

    //Attributs
    private static ArrayList<Lever> _levers=new ArrayList<>();

    //Accesseurs

    static public ArrayList<Lever> getLevers(){
        return _levers;
    }

    public static Lever createLever(String name, HashMap<Indicator,Integer> effects, Integer initBudget, ArrayList<String> infos){
        Lever l = new Lever(name,effects,initBudget,infos);
        _levers.add(l);
        return l;
    }

}
