package model;

import java.util.Collection;

public abstract class IndicLever {
    private String _name;
    private String _abreviation;
    private Collection<String> _infos;

    public IndicLever(String name, String abreviation, Collection<String> infos){
        _name=name;
        _abreviation = abreviation;
        _infos=infos;
    }

    //Accesseurs
    public String getName(){
        return _name;
    }

    public String getAbreviation() {
        return _abreviation;
    }

    public Collection<String> getInfos(){
        return _infos;
    }
}
