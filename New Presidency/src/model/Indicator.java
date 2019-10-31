package model; 

import java.util.Collection;
import java.util.HashMap;

public class Indicator extends IndicLever {
    private double _value;
    private double _maxValue;

    //Constructeurs
    public Indicator(String name, double initValue, double maxValue, Collection<String> infos){
        super(name,infos);
        _value=initValue;
        _maxValue=maxValue;
    }

    //Méthodes de classe

    public double getValue() {
        return _value;
    }
    public void setValue(double value) {
        if(value > 100.0){
            _value = 100.0;
        }
        if(value < 0.0){
            _value = 0.0;
        }
        _value = value;
    }
    public double getMaxValue() {
        return _maxValue;
    }


    public String toString(){
        return super.getName();
    }
}
