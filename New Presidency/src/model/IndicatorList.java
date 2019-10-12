package model;

import java.util.ArrayList;
import java.util.Collection;

public class IndicatorList {

    //Attributs
    private static Collection<Indicator> _indicators;
    private static IndicatorList _instance;

    //Accesseurs
    public Indicator createIndicator(String name, double value, Collection<String> infos){
        Indicator i = new Indicator(name,value,infos);
        _indicators.add(i);
        return i;
    }
    public Collection<Indicator> getIndicators(){
        return _indicators;
    }
    public static IndicatorList getInstance(){
        if(_instance==null)
            _instance=new IndicatorList();
        return _instance;
    }
    private IndicatorList(){
        _indicators=new ArrayList<>();
    }

    //Méthodes statiques
    public static void updateAll(){

        //On parcourt les indicateurs
        for (Indicator i:_indicators) {
            double newValue=0;
            //Pour l'indicateur courant, on parcourt tout les levier du jeu
            for (Lever l: LeverList.getLevers()) {
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
