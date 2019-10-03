package controller;

import model.List_Indicators;
import model.List_Levers;
import model.Indicator;
import model.Lever;

import java.util.HashMap;

public class Controller {
    List_Indicators listIndicators;
    List_Levers listLevers;

    public void parametres(){
        //Création des indicateurs
        Indicator SE = Indicator.newIndicator("Satisfaction étudiante", 0);
        List_Indicators.addIndicator(SE);
        Indicator SP = Indicator.newIndicator("Satisfaction du personnel", 0);
        List_Indicators.addIndicator(SP);
        Indicator NR = Indicator.newIndicator("Niveau de recherche", 0);
        List_Indicators.addIndicator(NR);
        Indicator TS = Indicator.newIndicator("Taux de succès au diplôme", 0);
        List_Indicators.addIndicator(TS);
        Indicator RU = Indicator.newIndicator("Réputation de l'université", 0);
        List_Indicators.addIndicator(RU);
        Indicator TI = Indicator.newIndicator("Taux d'insertion professionnelle", 0);
        List_Indicators.addIndicator(TI);
        
        //Création des leviers
        
        //Communication
        HashMap<Indicator,Integer> dicoCom= new HashMap<Indicator,Integer>();
        dicoCom.put(RU,10);
        dicoCom.put(SE, 5);
        Lever Com = Lever.newLever("Communication", dicoCom, 0);
        List_Levers.addLever(Com);
        
        //Subventions des chercheurs
        HashMap<Indicator,Integer> dicoSubCher= new HashMap<Indicator,Integer>();
        dicoSubCher.put(NR,10);
        dicoSubCher.put(RU, 5);
        Lever SubCher = Lever.newLever("Subvention des chercheurs", dicoSubCher, 0);
        List_Levers.addLever(SubCher);
    }
    
    
    public void tour(){
        //tour de jeu...

        List_Indicators.updateAll();
    }
}
