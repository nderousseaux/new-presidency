public class Indicator {

    //Variables statiques
    static Indicator STUDENT_SATISFACTION;
    static Indicator STAFF_SATISFACTION;
    static Indicator RESEARCH_LEVEL;
    static Indicator DIPLOMA_SUCCESS_RATE;
    static Indicator FAME;
    static Indicator PROFESSIONAL_INSERTION_RATE;

    //Attributs
    private String _name;
    private double _value;

    //Constructeurs
    private Indicator(String name, double initValue){
        _name=name;
        _value=initValue;
        G_Indicator.addIndicator(this);
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
    public static void initIndicators() {
        //Les attributs name serviront à l'affichage de la console
        STUDENT_SATISFACTION = new Indicator("Satisfaction étudiante", 0);
        STAFF_SATISFACTION = new Indicator("Satisfaction du personnel", 0);
        RESEARCH_LEVEL = new Indicator("Niveau de recherche", 0);
        DIPLOMA_SUCCESS_RATE = new Indicator("Taux de succès au diplôme", 0);
        FAME = new Indicator("Réputation de l'université", 0);
        PROFESSIONAL_INSERTION_RATE = new Indicator("Taux d'insertion professionnelle", 0);
    }




}
