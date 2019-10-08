package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.lang.System.exit;

public class Controller {
    List_Indicators _listIndicators;
    List_Levers _listLevers;
    Budget _budget;
    State _state;

    public void init(){
        //Instanciation du budget
        _state=new State(0,0,0,0,0);
        _budget=new Budget();

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
    
    public Integer addToBudget(Lever lever, Integer val){
        Integer r;
        if(val<_budget.getRemainingBudget()) {
            lever.addToBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() - val);
            r=0;
        }
        else{
            r=-1;
        }
        return r;
    }

    public Integer removeFromBudget(Lever lever, Integer val){
        Integer r;
        if(lever.getBudget()-val>=0) {
            lever.removeFromBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() + val);
            r = 0;
        }
        else{
            r=-1;
        }
        return r;
    }

    public void endOfRound(){
        //tour de jeu...
        List_Indicators.updateAll();
    }

    public Collection<String> listInfos(Informative_Object obj){ //à remplacer par une classe abstraite commune aux leviers et aux indicateurs (voire autre chose?)
         return obj.getInfos();                                           // Ce qui permettra de lister n'importe quelles infos
    }                                                           //done

    public Collection<String> getLevers(){
        return _state.getLevers();
    }

    public Collection<String> getIndicators(){
        return _state.getIndicators();
    }

    public float getValueOf(String obj){
        return 0;
    }

    public String getInfoOfIndicator(Integer index) { return ((ArrayList<String>)_state.getIndicatorsInfos()).get(index);}

    public String getInfoOfLever(Integer index) { return ((ArrayList<String>)_state.getLeversInfos()).get(index);}

    public Budget getBudget(){
        return _budget;
    }

    public void exitGame(){
        exit(0);
    }
}
