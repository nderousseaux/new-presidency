package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class IndicatorList {

    //Attributs
    private Collection<Indicator> _indicators;
    private static IndicatorList _instance;

    //Accesseurs
    public Indicator createIndicator(String name, double value, Collection<String> infos){
        Indicator i = new Indicator(name,value, infos);
        _indicators.add(i);
        return i;
    }
    public Collection<Indicator> getIndicators(){
        return _indicators;
    }
    public static IndicatorList getInstance(){
        if(_instance==null)
            _instance=new IndicatorList();
        return _instance;
    }
    private IndicatorList(){
        _indicators=new ArrayList<>();
    }




}
