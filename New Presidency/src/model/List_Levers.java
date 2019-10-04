package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class List_Levers {

    //Attributs
    private static Collection<Lever> _levers=new ArrayList<>();

    //Accesseurs

    static public void addLever(Lever lever){
        _levers.add(lever);
    }

    static public ArrayList<Lever> getLevers(){
        return (ArrayList)_levers;
    }

}
