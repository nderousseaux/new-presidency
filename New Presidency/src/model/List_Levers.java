package model;

import java.util.ArrayList;
import java.util.Collection;

public class List_Levers {

    //Attributs
    private static ArrayList<Lever> _levers=new ArrayList<>();

    //Accesseurs

    static public void addLever(Lever lever){
        _levers.add(lever);
    }

    static public ArrayList<Lever> getLevers(){
        return _levers;
    }

}
