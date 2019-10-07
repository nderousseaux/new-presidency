package model;

public class State{
    //attributs (i devant pour indicateurs et l devant pour leviers)
    private List_States historical;
    private int _year; // ni un indicateur ni un levier
    private float _iBudget;
    private float _lRegistrationFees;
    private int _iStudentNumber;
    private float _iStudentSatisfaction;
    
    //constructeur
    public State(int year, float iBudget, float lRegistrationFees, int iStudentNumber, float iStudentSatisfaction){
        this._year = year;
        this._iBudget = iBudget;
        this._lRegistrationFees = lRegistrationFees;
        this._iStudentNumber = iStudentNumber;
        this._iStudentSatisfaction = iStudentSatisfaction;
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
        this._year = year;
    }

    public void setIBudget(float iBudget) {
        this._iBudget = iBudget;
    }

    public void setLRegistrationFees(float lRegistrationFees) {
        this._lRegistrationFees = lRegistrationFees;
    }

    public void setIStudentNumber(int iStudentNumber) {
        this._iStudentNumber = iStudentNumber;
    }

    public void setIStudentSatisfaction(float _iStudentSatisfaction) {
        this._iStudentSatisfaction = _iStudentSatisfaction;
    }

    //méthodes
    //passage vers l'état suivant (c'est à dire vers l'année suivante)
    public State generateNextState(){
        int nextYear = this._year + 1;
        float nextRegistrationFees = this._lRegistrationFees;
        float nextStudentSatisfaction = this.generateNextStudentSatisfaction();
        int nextStudentNumber = this.generateNextStudentNumber(nextStudentSatisfaction);
        float nextBudget = this._iBudget + (nextStudentNumber * nextRegistrationFees);
        //voir si on ajoute une somme qui est reçue chaque année du gouvernement
        
        State nextState = new State(nextYear,nextBudget,nextRegistrationFees,nextStudentNumber,nextStudentSatisfaction);
        return nextState; 
    }
    
    //calcul de certains indicateurs
    public float generateNextStudentSatisfaction(){
        float nextStudentSatisfaction = this._iStudentSatisfaction;
        State previousState = historical.getState(this._year - 1);
        float previousRegistrationFees = previousState.getLRegistrationFees();
        //si les frais d'inscription ont augmentés
        if(this._lRegistrationFees > previousRegistrationFees){
            nextStudentSatisfaction = this._iStudentSatisfaction * (0 - (this._lRegistrationFees/previousRegistrationFees));
            // dans ce cas la satisfaction baisse proportionellement à l'augmentation des frais d'inscriptions
            //on peut procéder autrement, à discuter
        }
        //si les frais d'inscriptions ont diminués
        if(this._lRegistrationFees < previousRegistrationFees){
            nextStudentSatisfaction = this._iStudentSatisfaction * (previousRegistrationFees/this._lRegistrationFees);
            // dans ce cas la satisfaction augmente proportionellement à la diminution des frais d'inscriptions
            //on peut procéder autrement, à discuter
        }
        return nextStudentSatisfaction;
    }
    
    public int generateNextStudentNumber(float nextStudentSatisfaction){
        int nextStudentNumber = this._iStudentNumber;
        // formule pas du tout valide : exemple dan ce cas si on a 100 étudiants à l'an 0
        //et que la satisfaction est de 80% , à l'an 1 on aura 180 étudiants
        //deuxièmement , si la satisfaction est de 40% , on aura 60 étudiants à l'an 1
        //mais si la satisfaction est de 1% on aura 99 étudiants à l'an 1
        // BREF j'ai juste mis des formules pour l'exemple
        if(nextStudentSatisfaction > 0.5){
            nextStudentNumber += nextStudentSatisfaction * this._iStudentNumber;
        }
        if(nextStudentSatisfaction < 0.5){
            nextStudentNumber -= nextStudentSatisfaction * this._iStudentNumber;
        }
        return nextStudentNumber;
    }
}