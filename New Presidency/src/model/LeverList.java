package model;

import java.util.ArrayList;
import java.util.Collection;

public class LeverList {

    //Attributs
    private Collection<Lever> _levers;


    public LeverList(){
        _levers=new ArrayList<>();
    }

    //Accesseurs

    public Collection<Lever> getLevers(){
        return _levers;
    }

    public Lever createLever(String name, String abreviation, String category, Double initBudget, Double minBudget, Double maxBudget, ArrayList<String> infos){
        Lever l = new Lever(name, abreviation, category, initBudget, minBudget, maxBudget,infos);
        _levers.add(l);
        return l;
    }

    public Lever getLever(String name){
        Lever retour=null;
        for (Lever l:_levers) {
            if( l.getName().equals(name)){
                retour=l;
            }
        }
        return retour;
    }
    
    public Lever getLeverByAbreviation(String abreviation){
        Lever retour=null;
        for (Lever l:_levers) {
            if(l.getAbreviation().equals(abreviation)){
                retour=l;
            }
        }
        return retour;
    }

}
