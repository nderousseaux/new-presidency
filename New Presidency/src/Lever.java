import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Lever {

    //Variables statiques
    static Lever COMMUNICATION;

    //Attributs
    private String _name;
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;
    private Object HashMap;

    //Constructeur
    private Lever(String name, HashMap<Indicator,Integer> effects, Integer initBudget){
        _name=name;
        _effects=effects;
        _budget=initBudget;
        G_Lever.addLever(this);
    }

    //Accesseurs
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
    /*public String getEffectName(Integer index) {
        Set listEffects = _effects.keySet();
        System.out.println(listEffects);
        return listEffects;
    }*/


    //Méthodes de classe
    public static void initLever(){
        HashMap<Indicator,Integer> dicoCom= new HashMap<Indicator,Integer>();
        dicoCom.put(Indicator.FAME,10);
        COMMUNICATION=new Lever("Communication",dicoCom,0);
    }
}
