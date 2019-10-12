package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LeverList {

    //Attributs
    private static Collection<Lever> _levers;
    private static LeverList _instance;

    //Accesseurs

    public static Collection<Lever> getLevers(){
        return _levers;
    }

    public static LeverList getInstance(){
        if(_instance==null)
            _instance=new LeverList();
        return _instance;
    }

    private LeverList(){
        _levers=new ArrayList<>();
    }

    public static Lever createLever(String name, HashMap<Indicator,Integer> effects, Integer initBudget, ArrayList<String> infos){
        Lever l = new Lever(name,effects,initBudget,infos);
        _levers.add(l);
        return l;
    }

}
