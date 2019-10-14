package model;

import java.util.Collection;
import java.util.HashMap;

public class Indicator extends IndicLever {
    private double _value;

    //Constructeurs
    public Indicator(String name, double initValue, Collection<String> infos){
        super(name,infos);
        _value=initValue;
    }

    //Méthodes de classe

    public double getValue() {
        return _value;
    }
    public void setValue(double value) {
        _value = value;
    }


    public String toString(){
        return super.getName();
    }
}
