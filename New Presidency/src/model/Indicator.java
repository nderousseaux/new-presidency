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
 * @see IndicLever
 * @see IndicatorList
 * @see Lever
 * @see Controller
 * @author yoanv, lucast
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
     */
    public Indicator(String name, String abreviation, double initValue, double maxValue, Collection<String> infos){
        super(name, abreviation, infos);
        _value=initValue;
        _maxValue=maxValue;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>valeur de complétion</b> de l'indicateur
     * @return <b>Valeur de complétion</b> de l'indicateur
     *
     * @see Indicator#setValue(double)
     */
    public double getValue() {
        return _value;
    }

    /**
     * Accesseur <b>setteur</b> de la <b>valeur de la complétion</b> de l'indicateur
     * @param value <b>Valeur de complétion</b> de l'indicateur
     *
     * @see Indicator#getValue()
     */
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

    /**
     * Accesseur <b>getteur</b> de la <b>valeur de complétion maximale</b> de l'indicateur
     * @return <b>Valeur de complétion maximale</b> de l'indicateur
     */
    public double getMaxValue() {
        return _maxValue;
    }

    /**
     *  Redéfinition de la fonction <b>toString()</b>
     * @return <b>Nom</b> de l'indicateur
     */
    public String toString(){
        return super.getName();
    }
}
