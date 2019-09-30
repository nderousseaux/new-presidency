import java.util.ArrayList;
import java.util.Collection;

public class G_Indicator {
    private static Collection<Indicator> _indicators = new ArrayList<>();

    static void addIndicator(Indicator indic){
        _indicators.add(indic);
    }

    static public void updateAll(){
        //On parcourt les indicateurs
        for (Indicator i:_indicators) {
            //Pour l'indicateur courant
            for (Lever l: G_Lever.getLevers()) {
                //On parcourt tous les leviers du jeu
                if(l.getEffects().containsKey(i)){
                    //si le levier possède un effet sur l'indicateur courant, on le met à jour
                    i.setValue(i.getValue()+l.getEffects().get(i));
                }
            }
        }
    }

    static public Collection<Indicator> getIndicators(){
        return _indicators;
    }
}
