import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Lever {
    private String _name;
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;
    private Object HashMap;

    private Lever(String name, HashMap<Indicator,Integer> effects, Integer initBudget){
        _name=name;
        _effects=effects;
        _budget=initBudget;
        G_Lever.addLever(this);
    }

    public Integer getBudget(){
        return _budget;
    }

    public void setBudget(Integer budget){
        _budget=budget;
    }

    public HashMap<Indicator,Integer> getEffects(){
        return _effects;
    }

    public String getName(){
        return _name;
    }
/*
    public String getEffectName(Integer index) {
        Set listEffects = _effects.keySet();
        System.out.println(listEffects);
        return listEffects;
    }*/

    //Lister les leviers


    public static void initLever(){
        HashMap<Indicator,Integer> dicoFame= new HashMap<Indicator,Integer>();
        dicoFame.put(Indicator.FAME,10);
        COMMUNICATION=new Lever("Communication",dicoFame,0);
    }

    static Lever COMMUNICATION;
}
