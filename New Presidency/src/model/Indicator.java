package model;

public class Indicator {

    //Attributs
    private String _name;
    private double _value;

    //Constructeurs
    private Indicator(String name, double initValue){
        _name=name;
        _value=initValue;
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

    public static Indicator createIndicator(String name, double value){
        Indicator i = new Indicator(name,value);
        List_Indicators.add(i);
        return i;
    }


}
