package model;

import java.util.ArrayList;
import java.util.Collection;

public class List_Indicators {

    //Attributs
    private static Collection<Indicator> _indicators = new ArrayList<>();

    //Accesseurs
    public static void addIndicator(Indicator indic){
        _indicators.add(indic);
    }
    static public Collection<Indicator> getIndicators(){
        return _indicators;
    }

    //Méthodes statiques
    static public void updateAll(){

        //On parcourt les indicateurs
        for (Indicator i:_indicators) {
            double newValue=0;
            //Pour l'indicateur courant, on parcourt tout les levier du jeu
            for (Lever l: List_Levers.getLevers()) {
                //si le levier possède un effet sur l'indicateur courant, on l'ajoute au total de la nouvelle valeur
                if(l.getEffects().containsKey(i)){
                    newValue+=l.getEffects().get(i) * l.getBudget();
                }
            }

            //On met à jour l'indicateur
            i.setValue(newValue);
        }
    }


}
