package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class G_Lever {

    //Attributs
    private static Collection<Lever> _levers=new ArrayList<>();

    //Accesseurs
    static public void createLever(String name, HashMap<Indicator,Integer> effects, Integer initBudget){
        _levers.add(Lever.newLever(name, effects, initBudget));
    }
    static public Collection<Lever> getLevers(){
        return _levers;
    }

}
