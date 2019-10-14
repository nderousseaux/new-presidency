package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class IndicatorList {

    //Attributs
    private static Collection<Indicator> _indicators;
    private static IndicatorList _instance;

    //Accesseurs
    public static Indicator createIndicator(String name, double value, HashMap<Lever, Double> effects, Collection<String> infos){
        Indicator i = new Indicator(name,value,effects, infos);
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
            //Pour l'indicateur courant, on parcourt tout les levies qui l'influe
            for (Lever l: i.getEffects().keySet()) {
                //On y ajoute le pourcentage d'effet fois le pourcentage de budget
                newValue+=100*i.getEffects().get(l)*l.getBudget();
            }

            //On met à jour l'indicateur
            i.setValue(newValue);
        }
    }


}
