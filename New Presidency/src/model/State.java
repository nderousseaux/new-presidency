package model;

public class State{
    //attributs (i devant pour indicateurs et l devant pour leviers)
    private List_States historical;
    private int _year; // ni un indicateur ni un levier YOAN: Remplacer les ann�es par des semestres?
    private float _iBudget; // YOAN : Le budget de quoi? Total? Du coup pas un indicateur?
    private float _lRegistrationFees;
    private int _iStudentNumber;
    private float _iStudentSatisfaction;
    
    //constructeur
    public State(int year, float iBudget, float lRegistrationFees, int iStudentNumber, float iStudentSatisfaction){
        _year = year;
        _iBudget = iBudget;
        _lRegistrationFees = lRegistrationFees;
        _iStudentNumber = iStudentNumber;
        _iStudentSatisfaction = iStudentSatisfaction;
    }
    
    //accesseur
    //geteur
    public int getYear() {
        return _year;
    }

    public float getIBudget() {
        return _iBudget;
    }

    public float getLRegistrationFees() {
        return _lRegistrationFees;
    }

    public float getIStudentNumber() {
        return _iStudentNumber;
    }

    public float getIStudentSatisfaction() {
        return _iStudentSatisfaction;
    }

    //seteur

    public void setYear(int year) {
        _year = year;
    }

    public void setIBudget(float iBudget) {
        _iBudget = iBudget;
    }

    public void setLRegistrationFees(float lRegistrationFees) {
        _lRegistrationFees = lRegistrationFees;
    }

    public void setIStudentNumber(int iStudentNumber) {
        _iStudentNumber = iStudentNumber;
    }

    public void setIStudentSatisfaction(float _iStudentSatisfaction) {_iStudentSatisfaction = _iStudentSatisfaction;
    }

    //m�thodes
    //passage vers l'�tat suivant (c'est � dire vers l'ann�e suivante)
    public State generateNextState(){
        int nextYear = this._year + 1;
        float nextRegistrationFees = _lRegistrationFees;
        float nextStudentSatisfaction = generateNextStudentSatisfaction();
        int nextStudentNumber = generateNextStudentNumber(nextStudentSatisfaction);
        float nextBudget = _iBudget + (nextStudentNumber * nextRegistrationFees);
        //voir si on ajoute une somme qui est re�ue chaque ann�e du gouvernement
        
        State nextState = new State(nextYear,nextBudget,nextRegistrationFees,nextStudentNumber,nextStudentSatisfaction);
        return nextState; 
    }
    
    //calcul de certains indicateurs
    public float generateNextStudentSatisfaction(){
        float nextStudentSatisfaction = _iStudentSatisfaction;
        State previousState = historical.getState(_year - 1);
        float previousRegistrationFees = previousState.getLRegistrationFees();
        //si les frais d'inscription ont augment�s
        if(_lRegistrationFees > previousRegistrationFees){
            nextStudentSatisfaction = _iStudentSatisfaction * (0 - (_lRegistrationFees/previousRegistrationFees));
            // dans ce cas la satisfaction baisse proportionellement � l'augmentation des frais d'inscriptions
            //on peut proc�der autrement, � discuter
        }
        //si les frais d'inscriptions ont diminu�s
        if(_lRegistrationFees < previousRegistrationFees){
            nextStudentSatisfaction = _iStudentSatisfaction * (previousRegistrationFees/_lRegistrationFees);
            // dans ce cas la satisfaction augmente proportionellement � la diminution des frais d'inscriptions
            //on peut proc�der autrement, � discuter
        }
        return nextStudentSatisfaction;
    }
    
    public int generateNextStudentNumber(float nextStudentSatisfaction){
        int nextStudentNumber = _iStudentNumber;
        // formule pas du tout valide : exemple dan ce cas si on a 100 �tudiants � l'an 0
        //et que la satisfaction est de 80% , � l'an 1 on aura 180 �tudiants
        //deuxi�mement , si la satisfaction est de 40% , on aura 60 �tudiants � l'an 1
        //mais si la satisfaction est de 1% on aura 99 �tudiants � l'an 1
        // BREF j'ai juste mis des formules pour l'exemple
        if(nextStudentSatisfaction > 0.5){
            nextStudentNumber += nextStudentSatisfaction * _iStudentNumber;
        }
        if(nextStudentSatisfaction < 0.5){
            nextStudentNumber -= nextStudentSatisfaction * _iStudentNumber;
        }
        return nextStudentNumber;
    }
}