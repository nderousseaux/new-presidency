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
    StateList _stateList;
    Integer _year;
    Integer _maxYear;

    public Controller(){
        _budget=new Budget();
        _indicatorList=IndicatorList.getInstance();
        _leverList=LeverList.getInstance();
        _stateList=StateList.getInstance();
        _stateList.getStates().add(new State(2000,10000,50,50,50,50,50,50,50,50));
        _year=1;
        _maxYear=8;
    }

    public void init(){
        //Création des leviers
        //Recherche
        Lever recTitulaire =  _leverList.createLever("Titulaires en recherche", 100, new ArrayList<String>());
        Lever recContractuel = _leverList.createLever("Contractuels en recherche", 100, new ArrayList<String>());
        Lever recDotRec = _leverList.createLever("Dotation récurante en recherche", 100, new ArrayList<String>());
        Lever recDotSpe = _leverList.createLever("Dotation spécifique en recherche", 100, new ArrayList<String>());
        Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100, new ArrayList<String>());
        Lever rechPrime = _leverList.createLever("Primes donnée à la recherche", 100, new ArrayList<String>());
        //Formation
        Lever formTitulaire = _leverList.createLever("Titulaires en formation", 100, new ArrayList<String>());
        Lever formContractuel = _leverList.createLever("Contractuels en formation", 100, new ArrayList<String>());
        Lever formDotRec = _leverList.createLever("Dotation récurente pour la formation", 100, new ArrayList<String>());
        Lever formDotSpe = _leverList.createLever("Dotation spécifique pour la formation", 100, new ArrayList<String>());
        Lever formDotPed = _leverList.createLever("Dotation pour les projets pédagogiques", 100, new ArrayList<String>());
        Lever formPartenariat = _leverList.createLever("Partenariat pour la formation", 100, new ArrayList<String>());
        Lever formFraiIns = _leverList.createLever("Frais d'inscription", 100, new ArrayList<String>());
        Lever formPrime= _leverList.createLever("Prime de formation", 100, new ArrayList<String>());
        //Central
        Lever cCom = _leverList.createLever("Communication générale", 100, new ArrayList<String>());
        Lever cSubE = _leverList.createLever("Subventions aux associations étudiantes", 100, new ArrayList<String>());
        //Immobilier
        Lever iCons = _leverList.createLever("Investissement en construction", 100, new ArrayList<String>());
        Lever iEnt = _leverList.createLever("Investissement en entretient des bâtiments", 100, new ArrayList<String>());
        Lever iReno = _leverList.createLever("Investissement en rénovation des bâtiments", 100, new ArrayList<String>());


        //Création des indicateurs
        //Article publiés
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiés", 50, new ArrayList<String>());
        //Nombre de professeurs
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'université", 50, new ArrayList<String>());
        //Reputation de la formation
        Indicator repFormation = _indicatorList.createIndicator("Réputation de la formation", 50, new ArrayList<String>());
        //Taux de réussite
        Indicator tauxReussite = _indicatorList.createIndicator("Taux de réussite du diplôme", 50,new ArrayList<String>());
        //Satisfaction etudiante
        Indicator satisEtu = _indicatorList.createIndicator("Satisfaction etudiante", 50, new ArrayList<String>());

        //Nombre d'étudiant
        Indicator nbEtu = _indicatorList.createIndicator("Nombre d'étudiant", 50, new ArrayList<String>());
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
        updateAll();
        _year++;
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

    public Integer getYear(){
        return _year;
    }

    public Integer getMaxYear(){
        return _maxYear;
    }

    public void updateAll(){
        //On parcourt les indicateurs
        for (Indicator i:_indicatorList.getIndicators()) {

            //TODO:Coder les règles d'indicateurs selon le patern suivant

            //Exemple
            //Regles pour l'indicateur ''test'' (influé par le lever 'a' et 'b'
            if(i.getName() == "test"){
                int res = 0;
                res += i.getValue();
                res += _leverList.getLever(a).getBudget()*0.2;
                res += _leverList.getLever(b).getBudget()*0.4;

                i.setValue(res);
            }


        }
    }
}
