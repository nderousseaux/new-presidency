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
    Integer _year;
    Integer _maxYear;

    public Controller(){
        _state=new State(0,0,0,0,0);
        _budget=new Budget();
        _indicatorList=IndicatorList.getInstance();
        _leverList=LeverList.getInstance();
        _stateList=StateList.getInstance();
        _stateList.getStates().add(new State(2000,0,0,0,0));
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
        //Aides aux calculs
        double tier = 1/3;
        double quart = 1/4;
        double sixieme = 1/6;
        double neuvieme = 1/9;
        double dixhuitieme = 1/18;
        double vingetuneieme = 1/21;
        double vingtseptieme = 1/27;
        double soixantetroisieme = 1/63;


        //Article publiés
        HashMap<Lever, Double> aP = new HashMap<Lever, Double>();
        aP.put(recTitulaire, sixieme);
        aP.put(recContractuel, sixieme);
        aP.put(recDotRec, sixieme);
        aP.put(recDotSpe, sixieme);
        aP.put(recValorisation, sixieme);
        aP.put(rechPrime, sixieme);
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiés", 50, aP, new ArrayList<String>());

        //Nombre de professeurs
        HashMap<Lever, Double> nP = new HashMap<Lever, Double>();
        nP.put(recContractuel, quart);
        nP.put(recTitulaire, quart);
        nP.put(formTitulaire, quart);
        nP.put(formContractuel, quart);
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'université", 50, nP, new ArrayList<String>());


        //Reputation de la formation
        HashMap<Lever, Double> rF = new HashMap<Lever, Double>();
        rF.put(formDotRec, sixieme);
        rF.put(formDotSpe, sixieme);
        rF.put(cCom, tier);
        rF.put(formDotPed,tier);
        Indicator repFormation = _indicatorList.createIndicator("Réputation de la formation", 50, rF, new ArrayList<String>());

        //Taux de réussite
        HashMap<Lever, Double> tR = new HashMap<Lever, Double>();
        tR.put(formDotRec, 0.5);
        tR.put(formDotSpe, 0.5);
        Indicator tauxReussite = _indicatorList.createIndicator("Taux de réussite du diplôme", 50, tR, new ArrayList<String>());


        //Satisfaction etudiante
        HashMap<Lever, Double> sE = new HashMap<Lever, Double>();
        sE.put(iCons, neuvieme);
        sE.put(iEnt, neuvieme);
        sE.put(iReno, neuvieme);
        sE.put(cSubE, tier);
        sE.put(formTitulaire, vingetuneieme);
        sE.put(formContractuel, vingetuneieme);
        sE.put(formDotSpe, vingetuneieme);
        sE.put(formDotRec, vingetuneieme);
        sE.put(formFraiIns, vingetuneieme);
        sE.put(formPrime, vingetuneieme);
        sE.put(formPartenariat, vingetuneieme);
        Indicator satisEtu = _indicatorList.createIndicator("Satisfaction etudiante", 50, sE, new ArrayList<String>());

        //Nombre d'étudiant
        HashMap<Lever, Double> nE = new HashMap<Lever, Double>();
        nE.put(iCons,vingtseptieme);
        nE.put(iEnt,vingtseptieme);
        nE.put(iReno,vingtseptieme);
        nE.put(cSubE, neuvieme);
        nE.put(formTitulaire, soixantetroisieme);
        nE.put(formContractuel, soixantetroisieme);
        nE.put(formDotSpe, soixantetroisieme);
        nE.put(formDotRec, soixantetroisieme);
        nE.put(formFraiIns, soixantetroisieme);
        nE.put(formPrime, soixantetroisieme);
        nE.put(formPartenariat, soixantetroisieme);
        nE.put(formFraiIns, tier);
        nE.put(formDotRec, dixhuitieme);
        nE.put(formDotSpe, dixhuitieme);
        nE.put(cCom, neuvieme);
        nE.put(cSubE, neuvieme);
        Indicator nbEtu = _indicatorList.createIndicator("Nombre d'étudiant", 50, nE, new ArrayList<String>());
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
