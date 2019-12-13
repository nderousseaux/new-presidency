package model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <b><i>LeverList</i> est la classe contenant l'ensemble des objets Lever du jeu</b>
 * <p>
 *     Cette classe contient une <b>Collection de Lever</b>
 * </p>
 *
 * @see Lever
 * @see IndicLever
 * @see IndicatorList
 *
 * @author yoanv, lucast
 */
public class LeverList {

    //Attributs
    private Collection<Lever> _levers;


    /**
     * Constructeur de la classe <b>LeverList</b>, initialisant la liste des objets <b>Lever</b> à une <b>ArrayList</b> vide
     */
    public LeverList(){
        _levers=new ArrayList<>();
    }

    /**
     * Fonction initialisant un nouvel objet de type <b>Lever</b> et le stockant dans la liste et le retournant
     * @param name <b>Nom</b> de l'objet <b>Lever</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Lever</b>
     * @param category <b>Catégorie</b> de l'objet <b>Lever</b>
     * @param initBudget <b>Budget initial</b> de l'objet <b>Lever</b>
     * @param minBudget <b>Budget minimal</b> de l'objet <b>Lever</b>
     * @param maxBudget <b>Budget maximal</b> de l'objet <b>Lever</b>
     * @param infos <b>Liste des informations</b> de l'objet <b>Lever</b>
     * @return Objet <b>Lever</b> instancié
     */
    public Lever createLever(String name, String abreviation, String category, Double initBudget, Double minBudget, Double maxBudget, ArrayList<String> infos){
        Lever l = new Lever(name, abreviation, category, initBudget, minBudget, maxBudget,infos);
        _levers.add(l);
        return l;
    }

    /**
     * Fonction retournant la liste des objets <b>Lever</b> contenu dans l'instance de <b>LeverList</b>
     * @return <b>Collection</b> des objets <b>Lever</b>
     */
    public Collection<Lever> getLevers(){
        return _levers;
    }


    /**
     * Fonction retournant l'objet <b>Lever</b> indiqué par son <b>nom</b>
     * @param name <b>Nom</b> de l'objet <b>Lever</b>
     * @return Objet <b>Lever</b> demandé
     */
    public Lever getLever(String name){
        Lever retour=null;
        for (Lever l:_levers) {
            if( l.getName().equals(name)){
                retour=l;
            }
        }
        return retour;
    }

    /**
     * Fonction retournant l'objet <b>Lever</b> indiqué par son <b>abréviation</b>
     * @param abreviation <b>Abréviation</b> de l'objet <b>Lever</b> demandé
     * @return Objet <b>Lever</b> demandé
     */
    public Lever getLeverByAbreviation(String abreviation){
        Lever retour=null;
        for (Lever l:_levers) {
            if(l.getAbreviation().equals(abreviation)){
                retour=l;
            }
        }
        return retour;
    }

}
