package model;

public class State{
    //attributs (i devant pour indicateurs et l devant pour leviers)
    private List_States historical;
    private int _year; // ni un indicateur ni un levier YOAN: Remplacer les années par des semestres?
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

    //méthodes
    //passage vers l'état suivant (c'est à dire vers l'année suivante)
    public State generateNextState(){
        int nextYear = this._year + 1;
        float nextRegistrationFees = _lRegistrationFees;
        float nextStudentSatisfaction = generateNextStudentSatisfaction();
        int nextStudentNumber = generateNextStudentNumber(nextStudentSatisfaction);
        float nextBudget = _iBudget + (nextStudentNumber * nextRegistrationFees);
        //voir si on ajoute une somme qui est reçue chaque année du gouvernement
        
        State nextState = new State(nextYear,nextBudget,nextRegistrationFees,nextStudentNumber,nextStudentSatisfaction);
        return nextState; 
    }
    
    //calcul de certains indicateurs
    public float generateNextStudentSatisfaction(){
        float nextStudentSatisfaction = _iStudentSatisfaction;
        State previousState = historical.getState(_year - 1);
        float previousRegistrationFees = previousState.getLRegistrationFees();
        //si les frais d'inscription ont augmentés
        if(_lRegistrationFees > previousRegistrationFees){
            nextStudentSatisfaction = _iStudentSatisfaction * (0 - (_lRegistrationFees/previousRegistrationFees));
            // dans ce cas la satisfaction baisse proportionellement à l'augmentation des frais d'inscriptions
            //on peut procéder autrement, à discuter
        }
        //si les frais d'inscriptions ont diminués
        if(_lRegistrationFees < previousRegistrationFees){
            nextStudentSatisfaction = _iStudentSatisfaction * (previousRegistrationFees/_lRegistrationFees);
            // dans ce cas la satisfaction augmente proportionellement à la diminution des frais d'inscriptions
            //on peut procéder autrement, à discuter
        }
        return nextStudentSatisfaction;
    }
    
    public int generateNextStudentNumber(float nextStudentSatisfaction){
        int nextStudentNumber = _iStudentNumber;
        // formule pas du tout valide : exemple dan ce cas si on a 100 étudiants à l'an 0
        //et que la satisfaction est de 80% , à l'an 1 on aura 180 étudiants
        //deuxièmement , si la satisfaction est de 40% , on aura 60 étudiants à l'an 1
        //mais si la satisfaction est de 1% on aura 99 étudiants à l'an 1
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