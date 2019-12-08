package model;

import java.util.Collection;
import java.util.HashMap;

public class Indicator extends IndicLever {
    private double _value;
    private double _maxValue;

    //Constructeur
    public Indicator(String name, String abreviation, double initValue, double maxValue, Collection<String> infos){
        super(name, abreviation, infos);
        _value=initValue;
        _maxValue=maxValue;
    }

    //Méthodes de classe
    public double getValue() {
        return _value;
    }
    
    public void setValue(double value) {
        if(value > _maxValue){
            _value = _maxValue;
        }
        else if(value < 0.0){
            _value = 0.0;
        }
        else{
            _value = value; 
        }
    }
    
    public double getMaxValue() {
        return _maxValue;
    }

    public String toString(){
        return super.getName();
    }
}
