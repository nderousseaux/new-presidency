package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * <b><i>ScenarioList</i> est la classe contenant l'ensemble des objets Scenario du jeu</b>
 * <p>
 *     Cette classe contient une <b>Collection de Scenario</b>
 * </p>
 *
 * @see Scenario
 * @author nathanaeld
 */
public class ScenarioList {

    //Attributs
    private Collection<Scenario> _scenarios;


    /**
     * Constructeur de la classe <b>ScenarioList</b>, initialisant la liste des objets <b>Scenario</b> à une <b>ArrayList</b> vide
     */
    public ScenarioList(){
        _scenarios=new ArrayList<>();
    }

    //Accesseurs

    /**
     * Fonction instanciant un nouveau objet de type <b>Scenario</b>, le stockant dans la liste et le retournant
     * @param name <b>Nom</b> de l'objet <b>Scenario</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Scenario</b>
     * @param start <b>Situation de départ</b> de l'objet <b>Scenario</b>
     * @param victory <b>Situation de victoire</b> de l'objet <b>Scenario</b>
     * @param defeat <b>Situation de défaite</b> de l'objet <b>Scenario</b>
     * @param infos <b>Liste des informations</b> de l'objet <b>Scenario</b>
     * @return Objet <b>Scenario</b> instancié
     */
    public Scenario createScenario(String name, String abreviation, HashMap<String, Double> start, HashMap<String, Double> victory, HashMap<String, Double> defeat, Collection<String> infos){
        Scenario s = new Scenario(name, abreviation, start, victory, defeat, infos);
        _scenarios.add(s);
        return s;
    }

    /**
     * Fonction retournant la liste des objets <b>Scenario</b> contenu dans l'instance de <b>ScenarioList</b>
     * @return <b>Collection</b> des objets <b>Scenario</b>
     */
    public Collection<Scenario> getScenarios(){
        return _scenarios;
    }

    /**
     * Fonction retournant l'objet <b>Scenario</b> indiqué par son <b>nom</b>
     * @param name <b>Nom</b> de l'objet <b>Scenario</b> demandé
     * @return Objet <b>Scenario</b> demandé
     */
    public Scenario getScenario(String name){
        Scenario result = null;
        for(Scenario s : _scenarios){
            if(s.getName().equals(name)){
                result = s;
            }
        }
        return result;
    }

    /**
     * Fonction retournant l'objet <b>Scenario</b> indiqué par son <b>abréviation</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Scenario</b> demandé
     * @return Objet <b>Scenario</b> demandé
     */
    public Scenario getScenarioByAbreviation(String abreviation){
        Scenario result = null;
        for(Scenario s : _scenarios){
            if(s.getAbreviation().equals(abreviation)){
                result = s;
            }
        }
        return result;
    }
    
}
