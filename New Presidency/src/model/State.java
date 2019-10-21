package model;

public class State{
    //attributs (i devant pour indicateurs et l devant pour leviers)
    private int _year;
    private double _remainingBudget;
    private double _iTauxReu;
    private double _iSatPers;
    private double _iSatEtu;
    private double _lDotRecForm;
    private double _lDotSpeForm;
    private double _lDotRecRech;
    private double _lPrime;
    private double _lImmo;
    private double _lSubAssoEtu;
    
    //constructeur
    public State(int year, double remainingBudget, double iTauxReu, double iSatPers,
                 double iSatEtu, double lDotRecForm, double lDotSpeForm, double lDotRecRech, double lPrime,
                 double lImmo, double lSubAssoEtu){
        _year = year;
        _remainingBudget=remainingBudget;
        _iTauxReu=iTauxReu;
        _iSatPers=iSatPers;
        _iSatEtu=iSatEtu;
        _lDotRecForm=lDotRecForm;
        _lDotSpeForm=lDotSpeForm;
        _lDotRecRech = lDotRecRech;
        _lPrime=lPrime;
        _lImmo=lImmo;
        _lSubAssoEtu=lSubAssoEtu;
    }

    public int getYear(){return _year;}
    public double getRemainingBudget(){return _remainingBudget;}
    public double getITauxReu(){return _iTauxReu;}
    public double getISatEtu(){return _iSatEtu;}
    public double getISatPers(){return _iSatPers;}
    public double getLDotRecForm(){return _lDotRecForm;}
    public double getLDotSpeForm(){return _lDotSpeForm;}
    public double getLPrime(){return _lPrime;}
    public double getLImmo(){return _lImmo;}
    public double getLSubAssoEtu(){return _lSubAssoEtu;}
    public double getLDotRecRech() {return _lDotRecRech;}

    public void setLDotRecForm(double _lDotRecForm) {
        this._lDotRecForm = _lDotRecForm;
    }

    public void setLDotSpeForm(double _lDotSpeForm) {
        this._lDotSpeForm = _lDotSpeForm;
    }

    public void setLDotRecRech(double _lDotRecRech) {
        this._lDotRecRech = _lDotRecRech;
    }

    public void setLPrime(double _lPrime) {
        this._lPrime = _lPrime;
    }

    public void setLImmo(double _lImmo) {
        this._lImmo = _lImmo;
    }

    public void setLSubAssoEtu(double _lSubAssoEtu) {
        this._lSubAssoEtu = _lSubAssoEtu;
    }
}
