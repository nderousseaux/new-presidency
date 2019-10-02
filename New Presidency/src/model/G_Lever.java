package model;

import java.util.ArrayList;
import java.util.Collection;

public class G_Lever {

    //Attributs
    private static Collection<Lever> _levers=new ArrayList<>();

    //Accesseurs
    static void addLever(Lever lever){
        _levers.add(lever);
    }
    static public Collection<Lever> getLevers(){
        return _levers;
    }

}
