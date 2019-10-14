package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LeverList {

    //Attributs
    private Collection<Lever> _levers;
    private static LeverList _instance;

    //Accesseurs

    public Collection<Lever> getLevers(){
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

    public Lever createLever(String name, Integer initBudget, ArrayList<String> infos){
        Lever l = new Lever(name,initBudget,infos);
        _levers.add(l);
        return l;
    }

}
