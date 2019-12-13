package model;

import java.util.HashMap;

/**
 * <b><i>State</i> est la classe contenant les valeurs à une année donnée des indicateurs et des leviers</b>
 * <p>
 *     Elle contient:
 *     <ul>
 *         <li>Une <b>année donnée</b></li>
 *         <li>Un <b>budget disponible</b> lors de l'année donnée</li>
 *         <li>Un <b>ensemble de valeurs des indicateurs</b> lors de l'année donnée </li>
 *         <li>Un <b>ensemble de valeurs des leviers</b> lors de l'année donnée</li>
 *     </ul>
 * </p>
 *
 * @author yoanv, lucast
 */
public class State{
    //attributs
    private int _year;
    private double _remainingBudget;
    private HashMap<String, Double> _levers;
    private HashMap<String, Double> _indicators;

    /**
     * Constructeur de la classe <b>State</b>
     * @param year <b>Année de l'instance State</b>
     * @param remainingBudget <b>Budget disponible lors de l'année</b>
     * @param levers <b>Ensemble des valeurs des budgets des leviers</b>
     * @param indicators <b>Ensemble des valeurs de complétion des indicateurs</b>
     */
    public State(Integer year, double remainingBudget, HashMap<String, Double> levers, HashMap<String, Double> indicators){
        _year = year;
        _remainingBudget=remainingBudget;
        _levers = levers;
        _indicators = indicators;
    }

    /**
     * Accesseur <b>getteur</b> de l'<b>année de l'objet State</b>
     * @return <b>Année de l'objet</b>
     */
    public Integer getYear(){return _year;}

    /**
     * Accesseur <b>getteur</b> du <b>budget disponible de l'objet State</b>
     * @return <b>Budget disponible de l'objet</b>
     */
    public Double getRemainingBudget(){return _remainingBudget;}

    /**
     * Accesseur <b>getteur</b> de la valeur d'un <b>levier donné par son abréviation</b>
     * @param abreviation <b>Abréviation du levier demandé</b>
     * @return <b>Valeur du levier demandé</b>
     * @throws IllegalArgumentException Lancé si l'<b>abréviation</b> n'existe pas
     */
    public Double getLever(String abreviation) throws IllegalArgumentException{
        if(!_levers.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        return _levers.get(abreviation);
    }

    /**
     * Accesseur <b>getteur</b> de la valeur d'un <b>indicateur donné par son abréviation</b>
     * @param abreviation <b>Abréviation de l'indicateur demandé</b>
     * @return <b>Valeur de l'indicateur demandé</b>
     * @throws IllegalArgumentException Lancé si l'<b>abréviation</b> n'existe pas
     */
    public Double getIndicator(String abreviation) throws IllegalArgumentException{
        if(!_indicators.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        return _indicators.get(abreviation);
    }

    /**
     * Accesseur <b>setteur</b> de la valeur d'un <b>indicateur donné par son abréviation</b>
     * @param abreviation <b>Abréviation de l'indicateur à modifier</b>
     * @param val <b>Valeur à insérer</b>
     * @throws IllegalArgumentException Lancé si l'<b>abréviation n'existe pas</b>
     */
    public void setIndicator(String abreviation, Double val) throws IllegalArgumentException{
        if(!_indicators.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");

        _indicators.replace(abreviation, val);
    }

    /**
     * Accesseur <b>getteur</b> de l'ensemble des <b>valeurs des leviers</b>
     * @return <b>Ensemble des valeurs des leviers</b>
     */
    public HashMap<String, Double> getLevers(){
        return _levers;
    }

    /**
     * Accesseur <b>getteur</b> de l'ensemble des <b>valeurs des indicateurs</b>
     * @return <b>Ensemble des valeurs des indicateurs</b>
     */
    public HashMap<String, Double> getIndicators() {
        return _indicators;
    }

    /**
     * Accesseur <b>setteur</b> de la valeur d'un <b>levier donné par son abréviation</b>
     * @param abreviation <b>Abréviation du levier à modifier</b>
     * @param value <b>Valeur à insérer</b>
     * @throws IllegalArgumentException Lancé si l'<b>abréviation n'existe pas</b>
     */
    public void setLever(String abreviation, Double value) throws IllegalArgumentException{
        if(!_levers.containsKey(abreviation))
            throw new IllegalArgumentException("The abreviation given in arguments doesn't exist in this State");
        
        _levers.replace(abreviation, value);
    }
}
