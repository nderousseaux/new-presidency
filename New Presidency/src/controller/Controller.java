package controller;

import model.*;

import java.lang.reflect.Array;
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
        _stateList.createState(2000,10000,50,50,50,100,100,100,100,100);
        _year=1;
        _maxYear=8;
    }

    public void init(){
        //Création des leviers
        //Recherche

        //Lever recTitulaire =  _leverList.createLever("Titulaires en recherche", 100, new ArrayList<String>());
        //Lever recContractuel = _leverList.createLever("Contractuels en recherche", 100, new ArrayList<String>());
        //Lever recDotRec = _leverList.createLever("Dotation récurante en recherche", 100, new ArrayList<String>());
        //Lever recDotSpe = _leverList.createLever("Dotation spécifique en recherche", 100, new ArrayList<String>());
        //Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100, new ArrayList<String>());
        //Lever rechPrime = _leverList.createLever("Primes donnée à la recherche", 100, new ArrayList<String>());


        //Lever recTitulaire =  _leverList.createLever("Titulaires en recherche", 100, new ArrayList<String>());
        //Lever recContractuel = _leverList.createLever("Contractuels en recherche", 100, new ArrayList<String>());
        //Lever recDotRec = _leverList.createLever("Dotation récurante en recherche", 100, new ArrayList<String>());
        //Lever recDotSpe = _leverList.createLever("Dotation spécifique en recherche", 100, new ArrayList<String>());
        //Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100, new ArrayList<String>());
        //Lever rechPrime = _leverList.createLever("Primes donnée à la recherche", 100, new ArrayList<String>());



        //Formation
        //Lever formTitulaire = _leverList.createLever("Titulaires en formation", 100, new ArrayList<String>());
        //Lever formContractuel = _leverList.createLever("Contractuels en formation", 100, new ArrayList<String>());
        ArrayList<String> infosFormDotRec=new ArrayList<>();
        infosFormDotRec.add("Budget récurrent alloué aux formations");
        Lever formDotRec = _leverList.createLever("Dotation récurente pour la formation", 100, infosFormDotRec);

        ArrayList<String> infosFormDotSpe= new ArrayList<>();
        infosFormDotSpe.add("Budget spécifique alloué aux formations");
        Lever formDotSpe = _leverList.createLever("Dotation spécifique pour la formation", 100, infosFormDotSpe);
        //Lever formDotPed = _leverList.createLever("Dotation pour les projets pédagogiques", 100, new ArrayList<String>());
        //Lever formPartenariat = _leverList.createLever("Partenariat pour la formation", 100, new ArrayList<String>());
        //Lever formFraiIns = _leverList.createLever("Frais d'inscription", 100, new ArrayList<String>());

        ArrayList<String> infosFormPrime= new ArrayList<>();
        infosFormPrime.add("Budget alloué à la prime de formation des enseignants");
        infosFormPrime.add("Des enseignants plus formés donnent de meilleurs cours!");
        Lever formPrime= _leverList.createLever("Prime de formation", 100, infosFormPrime);
        //Central
        //Lever cCom = _leverList.createLever("Communication générale", 100, new ArrayList<String>());

        ArrayList<String> infosSubEtu=new ArrayList<>();
        infosSubEtu.add("Budget alloué aux associations étudiantes");
        infosSubEtu.add("L'augmenter augmentera l'humeur des élèves dans l'université, mais attention!");
        infosSubEtu.add("Buvez toujours avec Modération (très bon ami des programmeurs)!");
        Lever cSubE = _leverList.createLever("Subventions aux associations étudiantes", 100, infosSubEtu);
        //Immobilier

        ArrayList<String> infosCons=new ArrayList<>();
        infosCons.add("Budget général de l'immobilier");
        infosCons.add("De meilleurs bâtiments apportent un niveau d'enseignement amélioré");
        Lever iCons = _leverList.createLever("Investissement en construction", 100, infosCons);
        //Lever iEnt = _leverList.createLever("Investissement en entretient des bâtiments", 100, new ArrayList<String>());
        //Lever iReno = _leverList.createLever("Investissement en rénovation des bâtiments", 100, new ArrayList<String>());


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

        /*
        //Article publiés
        HashMap<Lever, Double> aP = new HashMap<Lever, Double>();
        aP.put(recTitulaire, sixieme);
        aP.put(recContractuel, sixieme);
        aP.put(recDotRec, sixieme);
        aP.put(recDotSpe, sixieme);
        aP.put(recValorisation, sixieme);
        aP.put(rechPrime, sixieme);
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiés", 50, aP, new ArrayList<String>());
         */
        /*
        //Nombre de professeurs
        HashMap<Lever, Double> nP = new HashMap<Lever, Double>();
        nP.put(recContractuel, quart);
        nP.put(recTitulaire, quart);
        nP.put(formTitulaire, quart);
        nP.put(formContractuel, quart);
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'université", 50, nP, new ArrayList<String>());
        */
        /*
        //Reputation de la formation
        HashMap<Lever, Double> rF = new HashMap<Lever, Double>();
        rF.put(formDotRec, sixieme);
        rF.put(formDotSpe, sixieme);
        rF.put(cCom, tier);
        rF.put(formDotPed,tier);
        Indicator repFormation = _indicatorList.createIndicator("Réputation de la formation", 50, rF, new ArrayList<String>());
        */

        /*
        //Article publiés
        ArrayList<String> infosArticles=new ArrayList<>();
        infosArticles.add("Les articles publiés par les chercheurs");
        infosArticles.add("Représentent la visibilité de l'université, l'intérêt qu'on lui porte");
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiés", 50, infosArticles);

        //Nombre de professeurs
        ArrayList<String> infosProfs = new ArrayList<>();
        infosProfs.add("Nombre de professeurs enseignants à l'université");
        infosProfs.add("Représente le niveau d'enseignement de l'université, et par conséquent son niveau de réussite est présupposé meilleur");
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'université", 50, infosProfs);

        //Reputation de la formation
        ArrayList<String> infosRep=new ArrayList<>();
        infosRep.add("Réputation générale du niveau d'enseignement");
        infosRep.add("Représente la visibilité et l'appréciation publique de la formation");
        Indicator repFormation = _indicatorList.createIndicator("Réputation de la formation", 50, infosRep);
        */


        //Taux de réussite
        ArrayList<String> infosReu=new ArrayList<>();
        infosReu.add("Niveau de réussite de la formation");
        infosReu.add("Représente par extension le niveau d'enseignement de la formation et l'encadrement des élèves");
        Indicator tauxReussite = _indicatorList.createIndicator("Taux de réussite du diplôme", 50,infosReu);

        //Satisfaction etudiante

        ArrayList<String> infosSatisEtu=new ArrayList<>();
        infosSatisEtu.add("Représente le niveau d'appréciations des étudiants");
        infosSatisEtu.add("Attention à ne pas oublier qu'un élève qui s'amuse trop travaille moins...");
        Indicator satisEtu = _indicatorList.createIndicator("Satisfaction etudiante", 50, infosSatisEtu);



        //Satisfaction personnel

        ArrayList<String> infosSatisPers=new ArrayList<>();
        infosSatisPers.add("Représente le niveau d'appréciation des professeur");
        infosSatisPers.add("Attention à ne pas oublier qu'un professeur qui ne se plait pas dans son travail enseigne moins bien...");


        Indicator satisPers = _indicatorList.createIndicator("Satisfaction personnel",50,infosSatisPers);
        /*
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
        */
        //Indicator nbEtu = _indicatorList.createIndicator("Nombre d'étudiant", 50, new ArrayList<String>());


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
                //res += _leverList.getLever(a).getBudget()*0.2;
              //  res += _leverList.getLever(b).getBudget()*0.4;

                i.setValue(res);
            }


        }
    }
}
