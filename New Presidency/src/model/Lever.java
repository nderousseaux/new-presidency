package model;

import controller.Controller;

import java.util.ArrayList;
/**<b><i>Lever</i> est la classe représentant les leviers de gestion du jeu</b>
 * <p>
 *     Cette classe contient
 *     <ul>
 *         <li>Un <b>budget alloué</b> au levier</li>
 *         <li>Un <b>budget minimal</b></li>
 *         <li>Un <b>budget maximal</b></li>
 *         <li>Une <b>catégorie de levier de gestion</b></li>
 *     </ul>
 * </p>
 *
 * @see IndicLever
 * @see LeverList
 * @see Indicator
 * @see Controller
 * @author yoanv, lucast
 */
public class Lever extends IndicLever {
    private double _budget;
    private double _maxBudget;
    private double _minBudget;
    private String _category;

    //Constructeur

    /** Constructeur de la classe, instanciant les champs de la classe et faisant remonter les informations pour la super classe <b>IndicLever</b>
     *
     * @param name <b>Nom</b> du levier (stocké dans la super classe)
     * @param abreviation <b>Abréviation</b> du levier (stockée dans la super classe)
     * @param category <b>Catégorie</b> du levier
     * @param initBudget <b>Budget initial</b> du levier
     * @param minBudget <b>Budget minimal</b> du levier
     * @param maxBudget <b>Budget maximal</b> du levier
     * @param infos <b>Informations</b> relatives au levier (stocké dans la super classe)
     */
    public Lever(String name, String abreviation, String category, double initBudget, double minBudget, double maxBudget, ArrayList<String> infos){
        super(name, abreviation, infos);

        _category = category;
        _budget=initBudget;
        _minBudget=minBudget; 
        _maxBudget=maxBudget;
    }

    /**
     * Accesseur <b>getteur</b> du <b>budget alloué</b> au levier
     * @return <b>Budget alloué</b> au levier
     *
     * @see Lever#setBudget(double)
     */
    public double getBudget(){
        return _budget;
    }

    /**
     * Accesseur <b>setteur</b> du <b>budget alloué</b> au levier
     * @param budget <b>Budget alloué</b> au levier
     *
     * @see Lever#getBudget()
     */
    public void setBudget(double budget){
        if(budget > _maxBudget){
            throw new IllegalArgumentException("try to set Lever budget with a value superior to the Lever's max value");
        }
        else if(budget < _minBudget){
            throw new IllegalArgumentException("try to set Lever budget with a value inferior to the Lever's min value");
        }
        else{
            _budget=budget;   
        }
    }

    /**
     * Accesseur <b>getteur</b> du <b>budget minimal</b> du levier
     * @return <b>Budget minimal</b> du levier
     *
     * @see Lever#setMinBudget(double)
     */
    public double getMinBudget() {
        return _minBudget;
    }

    /**
     * Accesseur <b>getteur</b> du <b>budget maximal</b> du levier
     * @return <b>Budget maximal</b> du levier
     *
     * @see Lever#setMaxBudget(double)
     */
    public double getMaxBudget() {
        return _maxBudget;
    }


    /**
     * Accesseur <b>setteur</b> du <b>budget minimal</b> du levier
     * @param minBudget <b>Budget minimal</b> du levier
     *
     * @see Lever#getMinBudget()
     */
    public void setMinBudget(double minBudget) {
        _minBudget = minBudget;
    }

    /**
     * Accesseur <b>setteur</b> du <b>budget maximal</b> du levier
     * @param maxBudget <b>Budget maximal</b> du levier
     *
     * @see Lever#getMaxBudget()
     */
    public void setMaxBudget(double maxBudget) {
        _maxBudget = maxBudget;
    }

    /**
     * Accesseur <b>setteur</b> de la <b>catégorie</b> du levier
     * @return <b>Catégorie</b> du budget
     */
    public String getCategory() {
        return _category;
    }
}
