package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Controller {
    List_Indicators _listIndicators;
    List_Levers _listLevers;

    public void init(){
        //Création des indicateurs

        Indicator SE = Indicator.createIndicator("Satisfaction étudiante", 0,null);
        Indicator SP = Indicator.createIndicator("Satisfaction du personnel", 0,null);
        Indicator NR = Indicator.createIndicator("Niveau de recherche", 0,null);
        Indicator TS = Indicator.createIndicator("Taux de succès au diplôme", 0,null);
        Indicator RU = Indicator.createIndicator("Réputation de l'université", 0,null);
        Indicator TI = Indicator.createIndicator("Taux d'insertion professionnelle", 0,null);
        
        //Création des leviers
        
        //Communication
        HashMap<Indicator,Integer> dicoCom= new HashMap<Indicator,Integer>();
        dicoCom.put(RU,10); //très bien vu Nath!
        dicoCom.put(SE, 5);
        ArrayList<String> infosComm = new ArrayList<>(); //oui bon j'aime bien le nom de variable XD
        infosComm.add("Ceci est un exemple d'infos sur un levier");
        infosComm.add("Bonsoir");
        Lever Com = Lever.createLever("Communication", dicoCom, 0,infosComm);
        
        //Subventions des chercheurs
        HashMap<Indicator,Integer> dicoSubCher= new HashMap<Indicator,Integer>();
        dicoSubCher.put(NR,10);
        dicoSubCher.put(RU, 5);
        Lever SubCher = Lever.createLever("Subvention des chercheurs", dicoSubCher, 0,null);
    }
    
    public void addToBudget(Lever lever, Integer val){
        lever.addToBudget(val);
    }

    public void removeFromBudget(Lever lever, Integer val){
        lever.removeFromBudget(val);
    }

    public void endOfRound(){
        //tour de jeu...
        List_Indicators.updateAll();
    }

    public Collection<String> listInfos(Informative_Object obj){ //à remplacer par une classe abstraite commune aux leviers et aux indicateurs (voire autre chose?)
         return obj.getInfos();                                           // Ce qui permettra de lister n'importe quelles infos
    }                                                           //done

    public Collection<Lever> getLevers(){
        return _listLevers.getLevers();
    }

    public Collection<Indicator> getIndicators(){
        return _listIndicators.getIndicators();
    }
}