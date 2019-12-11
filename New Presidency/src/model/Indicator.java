package model;

import controller.Controller;

import java.util.Collection;
import java.util.HashMap;

/**<b><i>Indicator</i> est la classe représentant les indicateurs de réussite du jeu</b>
 * <p>
 *     Cette classe contient
 *     <ul>
 *         <li>Une <b>valeur de complétion</b> de l'indicateur</li>
 *         <li>Une <b>valeur maximale de complétion</b>, utile pour les calculs au tour suivant</li>
 *     </ul>
 * </p>
 * @author yoanv
 * @author lucast
 */
public class Indicator extends IndicLever {
    private double _value;
    private double _maxValue;

    /**Constructeur de la classe, instanciant les champs de la classe et faisant remonter les informations pour la super classe <b>IndicLever</b>
     *
     * @param name <b>Nom</b> de l'indicateur (stocké dans la super classe)
     * @param abreviation <b>Abréviation</b> de l'indicateur (stockée dans la super classe)
     * @param initValue <b>Valeur initiale de complétion</b> de l'indicateur
     * @param maxValue <b>Valeur maximale de complétion</b> de l'indicateur
     * @param infos <b>Informations</b> relatives à l'indicateur (stockées dans la super classe)
     *
     * @see IndicLever
     * @see IndicatorList
     * @see Lever
     * @see Controller
     */
    public Indicator(String name, String abreviation, double initValue, double maxValue, Collection<String> infos){
        super(name, abreviation, infos);
        _value=initValue;
        _maxValue=maxValue;
    }

    //Méthodes de classe
    public double getValue() {
        return _value;
    }
    
    public void setValue(double value) {
        if(value > _maxValue){
            _value = _maxValue;
        }
        else if(value < 0.0){
            _value = 0.0;
        }
        else{
            _value = value; 
        }
    }
    
    public double getMaxValue() {
        return _maxValue;
    }

    public String toString(){
        return super.getName();
    }
}
