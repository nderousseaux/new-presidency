package model;

public class State{
    //attributs (i devant pour indicateurs et l devant pour leviers)
    private Integer _year;
    private Integer _remainingBudget;
    private double _iTauxReu;
    private double _iSatPers;
    private double _iSatEtu;
    private Integer _lDotRecForm;
    private Integer _lDotSpeForm;
    private Integer _lPrime;
    private Integer _lImmo;
    private Integer _lSubAssoEtu;
    
    //constructeur
    State(Integer year, Integer remainingBudget, double iTauxReu, double iSatPers,
                 double iSatEtu, Integer lDotRecForm, Integer lDotSpeForm, Integer lPrime,
                 Integer lImmo, Integer lSubAssoEtu){
        _year = year;
        _remainingBudget=remainingBudget;
        _iTauxReu=iTauxReu;
        _iSatPers=iSatPers;
        _iSatEtu=iSatEtu;
        _lDotRecForm=lDotRecForm;
        _lDotSpeForm=lDotSpeForm;
        _lPrime=lPrime;
        _lImmo=lImmo;
        _lSubAssoEtu=lSubAssoEtu;
    }

    public Integer getYear(){return _year;}
    public Integer getRemainingBudget(){return _remainingBudget;}
    public double getITauxReu(){return _iTauxReu;}
    public double getISatEtu(){return _iSatEtu;}
    public double getISatPers(){return _iSatPers;}
    public Integer getLDotRecForm(){return _lDotRecForm;}
    public Integer getLDotSpeForm(){return _lDotSpeForm;}
    public Integer getLPrime(){return _lPrime;}
    public Integer getLImmo(){return _lImmo;}
    public Integer getLSubAssoEtu(){return _lSubAssoEtu;}
}