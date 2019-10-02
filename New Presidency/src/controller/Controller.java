package controller;

import model.G_Indicator;
import model.G_Lever;
import model.Indicator;

import java.util.HashMap;

public class Controller {
    G_Indicator g_indicator;
    G_Lever g_lever;

    public void parametres(){
        //Création des indicateurs
        Indicator SE = G_Indicator.createIndicator("Satisfaction étudiante", 0);
        Indicator SP = G_Indicator.createIndicator("Satisfaction du personnel", 0);
        Indicator NR = G_Indicator.createIndicator("Niveau de recherche", 0);
        Indicator TS = G_Indicator.createIndicator("Taux de succès au diplôme", 0);
        Indicator RU = G_Indicator.createIndicator("Réputation de l'université", 0);
        Indicator TI = G_Indicator.createIndicator("Taux d'insertion professionnelle", 0);
        
        //Création des leviers
        
        //Communication
        HashMap<Indicator,Integer> dicoCom= new HashMap<Indicator,Integer>();
        dicoCom.put(RU,10);
        dicoCom.put(SE, 5);
        G_Lever.createLever("Communication", dicoCom, 0);
        
        //Subventions des chercheurs
        HashMap<Indicator,Integer> dicoSubCher= new HashMap<Indicator,Integer>();
        dicoSubCher.put(NR,10);
        dicoSubCher.put(RU, 5);
        G_Lever.createLever("Subvention des chercheurs", dicoSubCher, 0);
    }
    
    
    public void tour(){
        //tour de jeu...

        G_Indicator.updateAll();
    }
}
