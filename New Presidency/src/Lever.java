import java.util.HashMap;
import java.util.Map;

public class Lever {
    private String _name;
    private HashMap<Indicator,Integer> _effects;
    private Integer _budget;

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


    //Lister les leviers
    /*
    public void initDics(){

        com_dic.put(Indicator.FAME, 5);
    }

    final static Lever COMMUNICATION = new Lever("Communication",);*/
}
