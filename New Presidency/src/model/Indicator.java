package model;

import java.util.Collection;
import java.util.HashMap;

public class Indicator extends IndicLever {
    private double _value;
    private HashMap<Lever, Double> _effects;

    //Constructeurs
    public Indicator(String name, double initValue,  HashMap<Lever, Double> effects, Collection<String> infos){
        super(name,infos);
        _effects = effects;
        _value=initValue;
    }

    //Méthodes de classe

    public double getValue() {
        return _value;
    }
    public void setValue(double value) {
        _value = value;
    }

    public HashMap<Lever, Double> getEffects() {
        return _effects;
    }
    public String toString(){
        return super.getName();
    }
}
