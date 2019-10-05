package model;

import java.util.Collection;

public class Indicator extends Informative_Object{

    //Attributs
    private String _name;
    private double _value;
    private Collection<String> _infos;

    //Constructeurs
    private Indicator(String name, double initValue,Collection<String> infos){
        _name=name;
        _value=initValue;
        _infos=infos;
    }

    //Accesseurs
    public double getValue() {
        return _value;
    }
    public void setValue(double value) {
        _value = value;
    }
    public String getName(){
        return _name;
    }


    //Méthodes de classe

    public static Indicator createIndicator(String name, double value, Collection<String> infos){
        Indicator i = new Indicator(name,value,infos);
        List_Indicators.addIndicator(i);
        return i;
    }

    public Collection<String> getInfos(){
        return _infos;
    }

}
