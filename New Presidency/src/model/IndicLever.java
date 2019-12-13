package model;

import java.util.Collection;

/**
 * <b><i>IndicLever</i> est la super classe commune aux objets Indicator et Lever</b>
 * <p>
 *     Elle contient, pour chacun des objets <b>Indicator</b> ou <b>Lever</b>:
 *     <ul>
 *         <li>Le <b>nom</b> de l'objet</li>
 *         <li>L'<b>abréviation</b> de l'objet</li>
 *         <li>La <b>liste des informations</b> de l'objet</li>
 *     </ul>
 * </p>
 *
 * @see Indicator
 * @see Lever
 *
 * @author yoanv
 */
public abstract class IndicLever {
    private String _name;
    private String _abreviation;
    private Collection<String> _infos;

    /**
     * Constructeur de la classe <b>IndicLever</b>, appelé à chaque instanciation d'objet <b>Indicator</b> ou <b>Lever</b>
     * @param name <b>Nom</b> de l'objet
     * @param abreviation <b>Abréviation</b> de l'objet
     * @param infos <b>Liste des informations</b> de l'objet
     */
    public IndicLever(String name, String abreviation, Collection<String> infos){
        _name=name;
        _abreviation = abreviation;
        _infos=infos;
    }

    //Accesseurs

    /**
     * Accesseur <b>getteur</b> du <b>nom</b> de l'objet <b>IndicLever</b>
     * @return <b>Nom</b> de l'objet <b>IndicLever</b>
     */
    public String getName(){
        return _name;
    }

    /**
     * Accesseur <b>getteur</b> de l'<b>abréviation</b> de l'objet <b>IndicLever</b>
     * @return <b>Abréviation</b> de l'objet <b>IndicLever</b>
     */
    public String getAbreviation() {
        return _abreviation;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>liste d'informations</b> de l'objet <b>IndicLever</b>
     * @return <b>Liste d'informations</b> de l'objet <b>IndicLever</b>
     */
    public Collection<String> getInfos(){
        return _infos;
    }
}
