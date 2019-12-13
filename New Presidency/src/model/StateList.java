package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * <b><i>StateList</i> est la classe contenant l'ensemble des objets State du jeu</b>
 * <p>
 *     Cette classe contient une <b>Collection de State</b>
 * </p>
 *
 * @see State
 * @author yoanv, lucast
 */
public class StateList {
    private Collection<State> _states;
    /**
     * Constructeur de la classe <b>StateList</b>, initialisant la <b>liste de State</b> à une <b>ArrayList vide</b>
     */
    public StateList(){
        _states=new ArrayList<>();
    }

    /**
     * Fonction permettant d'<b>ajouter un objet State</b> à la liste
     * @param s <b>State à ajouter à la liste</b>
     */
    public void addState(State s){
        _states.add(s);
    }

    /**
     * Accesseur <b>getteur</b> permettant de récupérer un <b>State donné par une année</b>
     * @param year <b>Année du State à récupérer</b>
     * @return <b>State demandé</b>
     */
    public State getState(int year){ //YOAN: idem, rebmplacer par des semestres?
        State selectedState = null;
        for(State s : _states){
            if(s.getYear()== year){
                selectedState = s;
            }
        }
        return selectedState;
    }

    /**
     * Accesseur <b>getteur</b> permettant de récupérer l'<b>ensemble des objets State de la partie</b>
     * @return <b>Ensemble des objets State de la partie</b>
     */
    public Collection<State> getStates(){ return _states;}

    /**
     * Fonction permettant d'<b>initialiser un nouvel objet State</b>
     * @param year <b>Année</b> de l'objet State
     * @param remainingBudget <b>Budget disponible</b> de l'objet State
     * @param levers <b>Ensemble des leviers</b> de l'objet State
     * @param indicators <b>Ensemble des indicateurs</b> de l'objet State
     * @return <b>Objet State initialisé</b>
     */
    public State createState(int year, double remainingBudget, HashMap<String, Double> levers, HashMap<String, Double> indicators){
        State s = new State(year, remainingBudget, levers, indicators);
        _states.add(s);
        return s;
    }
}