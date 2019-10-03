package controller;

import model.List_Indicators;
import model.List_Levers;
import model.Indicator;
import model.Lever;

import java.util.HashMap;

public class Controller {
    List_Indicators listIndicators;
    List_Levers listLevers;

    public void init(){
        //Création des indicateurs
        Indicator SE = Indicator.createIndicator("Satisfaction étudiante", 0);
        Indicator SP = Indicator.createIndicator("Satisfaction du personnel", 0);
        Indicator NR = Indicator.createIndicator("Niveau de recherche", 0);
        Indicator TS = Indicator.createIndicator("Taux de succès au diplôme", 0);
        Indicator RU = Indicator.createIndicator("Réputation de l'université", 0);
        Indicator TI = Indicator.createIndicator("Taux d'insertion professionnelle", 0);
        
        //Création des leviers
        
        //Communication
        HashMap<Indicator,Integer> dicoCom= new HashMap<Indicator,Integer>();
        dicoCom.put(RU,10); //très bien vu Nath!
        dicoCom.put(SE, 5);
        Lever Com = Lever.newLever("Communication", dicoCom, 0);
        
        //Subventions des chercheurs
        HashMap<Indicator,Integer> dicoSubCher= new HashMap<Indicator,Integer>();
        dicoSubCher.put(NR,10);
        dicoSubCher.put(RU, 5);
        Lever SubCher = Lever.newLever("Subvention des chercheurs", dicoSubCher, 0);
    }
    
    
    public void tour(){
        //tour de jeu...

        List_Indicators.updateAll();
    }
}
