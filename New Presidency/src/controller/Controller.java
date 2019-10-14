package controller;

import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.lang.System.exit;

public class Controller {
    IndicatorList _indicatorList;
    LeverList _leverList;
    Budget _budget;
    State _state;
    StateList _stateList;

    public Controller(){
        _state=new State(0,0,0,0,0);
        _budget=new Budget();
        _indicatorList=IndicatorList.getInstance();
        _leverList=LeverList.getInstance();
        _stateList=StateList.getInstance();
    }

    public void init(){
        //Création des indicateurs

        Indicator SE = _indicatorList.createIndicator("Satisfaction étudiante", 0,null);
        Indicator SP = _indicatorList.createIndicator("Satisfaction du personnel", 0,null);
        Indicator NR = _indicatorList.createIndicator("Niveau de recherche", 0,null);
        Indicator TS = _indicatorList.createIndicator("Taux de succès au diplôme", 0,null);
        Indicator RU = _indicatorList.createIndicator("Réputation de l'université", 0,null);
        Indicator TI = _indicatorList.createIndicator("Taux d'insertion professionnelle", 0,null);
        
        //Création des leviers
        
        //Communication
        HashMap<IndicLever,Integer> dicoCom= new HashMap<IndicLever,Integer>();
        dicoCom.put(RU,10); //très bien vu Nath!
        dicoCom.put(SE, 5);
        ArrayList<String> infosComm = new ArrayList<>(); //oui bon j'aime bien le nom de variable XD
        infosComm.add("Ceci est un exemple d'infos sur un levier");
        infosComm.add("Bonsoir");
        Lever Com = _leverList.createLever("Communication", dicoCom, 0,infosComm);
        
        //Subventions des chercheurs
        HashMap<IndicLever,Integer> dicoSubCher= new HashMap<IndicLever,Integer>();
        dicoSubCher.put(NR,10);
        dicoSubCher.put(RU, 5);
        Lever SubCher = _leverList.createLever("Subvention des chercheurs", dicoSubCher, 0,null);
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
        IndicatorList.updateAll();
    }

    public Collection<String> listInfos(IndicLever obj){
         return obj.getInfos();
    }

    public Collection<Lever> getLevers(){
        return _leverList.getLevers();
    }

    public Collection<Indicator> getIndicators(){
        return _indicatorList.getIndicators();
    }

    public Budget getBudget(){
        return _budget;
    }

    public void exitGame(){
        exit(0);
    }
}
