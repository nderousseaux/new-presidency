package model;

import java.util.Collection;

public abstract class IndicLever {
    private String _name;
    private Collection<String> _infos;

    public IndicLever(String name, Collection<String> infos){
        _name=name;
        _infos=infos;
    }

    //Accesseurs

    public String getName(){
        return _name;
    }
    public Collection<String> getInfos(){
        return _infos;
    }
}
