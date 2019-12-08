package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class IndicatorList {

    //Attributs
    private Collection<Indicator> _indicators;


    public IndicatorList(){
        _indicators=new ArrayList<>();
    }

    //Accesseurs
    public Indicator createIndicator(String name, String abreviation, double value, double maxValue, Collection<String> infos){
        Indicator i = new Indicator(name, abreviation, value, maxValue, infos);
        _indicators.add(i);
        return i;
    }
    public Collection<Indicator> getIndicators(){
        return _indicators;
    }
    public Indicator getIndicator(String name){
        Indicator result = null;
        for(Indicator i : _indicators){
            if(i.getName().equals(name)){
                result = i;
            }
        }
        return result;
    }
    
    public Indicator getIndicatorByAbreviation(String abreviation){
        Indicator result = null;
        for(Indicator i : _indicators){
            if(i.getAbreviation().equals(abreviation)){
                result = i;
            }
        }
        return result;
    }
    
}
