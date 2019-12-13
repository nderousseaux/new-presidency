package model;

import controller.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * <b><i>Scenario</i> est la classe de chacun des scénarios proposés dans le jeu</b>
 * <p>
 *     Elle contient, pour chacune de ses instances:
 *     <ul>
 *          <li>Un <b>nom</b> de scénario</li>
 *          <li>Une <b>abréviation</b> du nom de scénario</li>
 *          <li>Une <b>situation de départ</b> du scénario sous la forme d'une <b>HashMap<String,Double</b></li>
 *          <li>Une <b>situation de victoire</b> du scénario sous la forme d'une <b>HashMap<String,Double</b></li>
 *          <li>Une <b>situation de défaite</b> du scénario sous la forme d'une <b>HashMap<String,Double</b></li>
 *          <li>Une <b>liste d'informations</b> relative au scénario, affichée à l'utilisateur pour lui indiquer comment jouer</li>
 *     </ul>
 * </p>
 *
 * @author nathanaeld
 */
public class Scenario{
    private String _name;
    private String _abreviation;
    private HashMap<String, Double> _start;
    private HashMap<String, Double> _victory;
    private HashMap<String, Double> _defeat;
    private Collection<String> _infos;

    /**
     * Constructeur de la classe <b>Scenario</b>, instanciant un nouveau scénario
     * @param name <b>Nom</b> du scénario
     * @param abreviation <b>Abréviation</b> du scénario
     * @param start <b>Situation de départ</b> du scénario
     * @param victory <b>Situation de victoire</b> du scénario
     * @param defeat <b>Situation de défaite</b> du scénario
     * @param infos <b>Liste d'informations</b> du scénario
     */
    public Scenario(String name, String abreviation, HashMap<String, Double> start, HashMap<String, Double> victory, HashMap<String, Double> defeat, Collection<String> infos){
        _name=name;
        _abreviation=abreviation;
        _defeat=defeat;
        _victory = victory;
        _start = start;
        _infos=infos;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>condition de départ</b> du scénario
     * @return <b>Condition de départ</b> du scénario
     */
    public HashMap<String, Double> getStart() {
        return _start;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>condition de victoire</b> du scénario
     * @return <b>Condition de victoire</b> du scénario
     */
    public HashMap<String, Double> getVictory() {
        return _victory;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>condition de défaite</b> du scénario
     * @return <b>Condition de défaite</b> du scénario
     */
    public HashMap<String, Double> getDefeat() {
        return _defeat;
    }

    /**
     * Accesseur <b>getteur</b> du <b>nom</b> du scénario
     * @return <b>Nom</b> du scénario
     */
    public String getName(){
        return _name;
    }

    /**
     * Accesseur <b>getteur</b> de l'<b>abréviation</b> du scénario
     * @return <b>Abréviation</b> du scénario
     */
    public String getAbreviation(){
        return _abreviation;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>liste d'informations</b> du scénario
     * @return <b>Liste d'informations</b> du scénario
     */
    public Collection<String> getInfos(){
        return _infos;
    }
}
