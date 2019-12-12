package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**<b><i>IndicatorList</i> est la classe contenant l'ensemble des objets Indicator du jeu</b>
 * <p>
 *     Cette classe contient une <b>Collection d'Indicator</b>
 * </p>
 *
 * @see Indicator
 * @see IndicLever
 * @see LeverList
 *
 * @author yoanv, lucast
 */
public class IndicatorList {

    //Attributs
    private Collection<Indicator> _indicators;

    /**
     * Constructeur de la classe <b>IndicatorList</b>, initialisant la liste des objets <b>Indicator</b> à une <b>Collection</b> vide
     */
    public IndicatorList(){
        _indicators=new ArrayList<>();
    }

    //Accesseurs

    /**
     * Fonction instanciant un nouveau objet de type <b>Indicator</b> et le stockant dans la liste
     * @param name <b>Nom</b> de l'objet <b>Indicator</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Indicator</b>
     * @param value <b>Valeur initiale</b> de l'objet <b>Indicator</b>
     * @param maxValue <b>Valeur maximale</b> de l'objet <b>Indicator</b>
     * @param infos <b>Liste des informations</b> de l'objet <b>Indicator</b>
     * @return Objet <b>Indicator</b> instancié
     */
    public Indicator createIndicator(String name, String abreviation, double value, double maxValue, Collection<String> infos){
        Indicator i = new Indicator(name, abreviation, value, maxValue, infos);
        _indicators.add(i);
        return i;
    }

    /**
     * Fonction retournant la liste des objets <b>Indicator</b> contenu dans l'instance de <b>IndicatorList</b>
     * @return <b>Collection</b> des objets <b>Indicator</b>
     */
    public Collection<Indicator> getIndicators(){
        return _indicators;
    }

    /**
     * Fonction retournant l'objet <b>Indicator</b> indiqué par son <b>nom</b>
     * @param name <b>Nom</b> de l'objet <b>Indicator</b> demandé
     * @return Objet <b>Indicator</b> demandé
     */
    public Indicator getIndicator(String name){
        Indicator result = null;
        for(Indicator i : _indicators){
            if(i.getName().equals(name)){
                result = i;
            }
        }
        return result;
    }

    /**
     * Fonction retournant l'objet <b>Indicator</b> indiqué par son <b>abréviation</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Indicator</b> demandé
     * @return Objet <b>Indicator</b> demandé
     */
    public Indicator getIndicatorByAbreviation(String abreviation){
        Indicator result = null;
        for(Indicator i : _indicators){
            if(i.getAbreviation().equals(abreviation)){
                result = i;
            }
        }
        return result;
    }
    
}
