package controller;

import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static java.lang.System.exit;

@SuppressWarnings("LossyEncoding")
public class Controller {
    IndicatorList _indicatorList;
    LeverList _leverList;
    Budget _budget;
    StateList _stateList;
    int _year;
    int _maxYear;

    public Controller(){
        _budget=new Budget();
        _indicatorList=IndicatorList.getInstance();
        _leverList=LeverList.getInstance();
        _stateList=StateList.getInstance();
        _stateList.createState(0,10000.0,50.0,50.0,50.0,100.0,100.0,100.0,100.0,100.0,100.0);
        _stateList.createState(1,10000.0,50.0,50.0,50.0,100.0,100.0,100.0,100.0,100.0,100.0);
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
        ArrayList<String> infosRecDotRec=new ArrayList<>();
        infosRecDotRec.add("Budget récurrent alloué � la recherche");
        Lever recDotRec = _leverList.createLever("Dotation récurante en recherche", 100.0, infosRecDotRec);
        //Lever recDotSpe = _leverList.createLever("Dotation spécifique en recherche", 100, new ArrayList<String>());
        //Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100, new ArrayList<String>());
        //Lever rechPrime = _leverList.createLever("Primes donnée à la recherche", 100, new ArrayList<String>());



        //Formation
        //Lever formTitulaire = _leverList.createLever("Titulaires en formation", 100, new ArrayList<String>());
        //Lever formContractuel = _leverList.createLever("Contractuels en formation", 100, new ArrayList<String>());
        ArrayList<String> infosFormDotRec=new ArrayList<>();
        infosFormDotRec.add("Budget récurrent alloué aux formations");
        Lever formDotRec = _leverList.createLever("Dotation récurente pour la formation", 100.0, infosFormDotRec);

        ArrayList<String> infosFormDotSpe= new ArrayList<>();
        infosFormDotSpe.add("Budget spécifique alloué aux formations");
        Lever formDotSpe = _leverList.createLever("Dotation spécifique pour la formation", 100.0, infosFormDotSpe);
        //Lever formDotPed = _leverList.createLever("Dotation pour les projets pédagogiques", 100, new ArrayList<String>());
        //Lever formPartenariat = _leverList.createLever("Partenariat pour la formation", 100, new ArrayList<String>());
        //Lever formFraiIns = _leverList.createLever("Frais d'inscription", 100, new ArrayList<String>());

        ArrayList<String> infosFormPrime= new ArrayList<>();
        infosFormPrime.add("Budget alloué à la prime de formation des enseignants");
        infosFormPrime.add("Des enseignants plus formés donnent de meilleurs cours!");
        Lever formPrime= _leverList.createLever("Prime de formation", 100.0, infosFormPrime);
        //Central
        //Lever cCom = _leverList.createLever("Communication générale", 100, new ArrayList<String>());

        ArrayList<String> infosSubEtu=new ArrayList<>();
        infosSubEtu.add("Budget alloué aux associations étudiantes");
        infosSubEtu.add("L'augmenter augmentera l'humeur des élèves dans l'université, mais attention!");
        infosSubEtu.add("Buvez toujours avec Modération (très bon ami des programmeurs)!");
        Lever cSubE = _leverList.createLever("Subventions aux associations étudiantes", 100.0, infosSubEtu);
        //Immobilier

        ArrayList<String> infosCons=new ArrayList<>();
        infosCons.add("Budget général de l'immobilier");
        infosCons.add("De meilleurs bâtiments apportent un niveau d'enseignement amélioré");
        Lever iCons = _leverList.createLever("Investissement en construction", 100.0, infosCons);
        //Lever iEnt = _leverList.createLever("Investissement en entretient des bâtiments", 100, new ArrayList<String>());
        //Lever iReno = _leverList.createLever("Investissement en rénovation des bâtiments", 100, new ArrayList<String>());


        //Création des indicateurs

        //Aides aux calculs
        double tier = 1.0/3.0;
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
        Indicator tauxReussite = _indicatorList.createIndicator("Taux de r�ussite du dipl�me", 50,infosReu);

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

    
    public Integer addToBudget(Lever lever, Double val){
        Integer r;
        if(val<=_budget.getRemainingBudget()) {
            lever.addToBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() - val);
            r=0;
        }
        else{
            r=-1;
        }
        return r;
    }

    public Integer removeFromBudget(Lever lever, Double val){
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
        State thisYearState = _stateList.getState(_year);
        for(Lever l : _leverList.getLevers()){
            
            if(l.getName() == "Dotation récurante en recherche"){
                thisYearState.setLDotRecRech(l.getBudget());
            }
            
            if(l.getName() == "Dotation récurente pour la formation"){
                thisYearState.setLDotRecForm(l.getBudget());
            }
            
            if(l.getName() == "Dotation spécifique pour la formation"){
                thisYearState.setLDotSpeForm(l.getBudget());
            }
            
            if(l.getName() == "Prime de formation"){
                thisYearState.setLPrime(l.getBudget());
            }
            
            if(l.getName() == "Subventions aux associations étudiantes"){
                thisYearState.setLSubAssoEtu(l.getBudget());
            }
            
            if(l.getName() == "Investissement en construction"){
                thisYearState.setLImmo(l.getBudget());
            }
        }
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

    public int getYear(){
        return _year;
    }

    public int getMaxYear(){
        return _maxYear;
    }

    public void updateAll(){
        State thisYearState = _stateList.getState((int)_year);
        State lastYearState = _stateList.getState((int)(_year - 1.0));
        
        double resSatEtu = lastYearState.getISatEtu();
        double resSatPers = lastYearState.getISatPers();
        double resTauxReu = lastYearState.getITauxReu();
        

        //On parcourt les indicateurs
        for (Indicator i:_indicatorList.getIndicators()) {
        
            //Pour chaque indicateur on calcule la valeur au tour suivant
        
            if(i.getName() == "Satisfaction etudiante"){
                
                
                if(lastYearState.getLSubAssoEtu() < thisYearState.getLSubAssoEtu()){
                      resSatEtu += (thisYearState.getLSubAssoEtu()/lastYearState.getLSubAssoEtu())*(1.0/3.0)*lastYearState.getISatEtu();
                    }
                    if(lastYearState.getLSubAssoEtu() > thisYearState.getLSubAssoEtu()){
                      resSatEtu -= (lastYearState.getLSubAssoEtu()/thisYearState.getLSubAssoEtu())*(1.0/3.0)*lastYearState.getISatEtu();
                    }
                    if(lastYearState.getLSubAssoEtu() == thisYearState.getLSubAssoEtu()){
                      //on ne fait rien, pas de changement donc pas d'influence
                    }

                    if(lastYearState.getLImmo() < thisYearState.getLImmo()){
                      resSatEtu += (thisYearState.getLImmo()/lastYearState.getLImmo()) * (1.0/3.0) * lastYearState.getISatEtu();
                    }
                    if(lastYearState.getLImmo() > thisYearState.getLImmo()){
                      resSatEtu -= (lastYearState.getLImmo()/thisYearState.getLImmo()) * (1.0/3.0) * lastYearState.getISatEtu();
                    }
                    if(lastYearState.getLImmo() == thisYearState.getLImmo()){
                      //on ne fait rien, pas de changement donc pas d'influence
                    }
            }

            if(i.getName() == "Satisfaction personnel"){
               

                  if(lastYearState.getLPrime() < thisYearState.getLPrime()){
                    resSatPers += (thisYearState.getLPrime()/lastYearState.getLPrime())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLPrime() > thisYearState.getLPrime()){
                    resSatPers -= (lastYearState.getLPrime()/thisYearState.getLPrime())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLPrime() == thisYearState.getLPrime()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }

                  if(lastYearState.getLDotRecForm() < thisYearState.getLDotRecForm()){
                    resSatPers += (thisYearState.getLDotRecForm()/lastYearState.getLDotRecForm())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLDotRecForm() > thisYearState.getLDotRecForm()){
                    resSatPers -= (lastYearState.getLDotRecForm()/thisYearState.getLDotRecForm())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLDotRecForm() == thisYearState.getLDotRecForm()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }

                  if(lastYearState.getLDotRecRech() < thisYearState.getLDotRecRech()){
                    resSatPers += (thisYearState.getLDotRecRech()/lastYearState.getLDotRecRech())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLDotRecRech() < thisYearState.getLDotRecRech()){
                    resSatPers -= (lastYearState.getLDotRecRech()/thisYearState.getLDotRecRech())*(1.0/5.0)*lastYearState.getISatPers();
                  }
                  if(lastYearState.getLDotRecRech() < thisYearState.getLDotRecRech()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }

                  if(lastYearState.getLImmo() < thisYearState.getLImmo()){
                    resSatPers += (thisYearState.getLImmo()/lastYearState.getLImmo()) * (1.0/5.0) * lastYearState.getISatPers();
                  }
                  if(lastYearState.getLImmo() > thisYearState.getLImmo()){
                    resSatPers -= (lastYearState.getLImmo()/thisYearState.getLImmo()) * (1.0/5.0) * lastYearState.getISatPers();
                  }
                  if(lastYearState.getLImmo() == thisYearState.getLImmo()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }
                  
                i.setValue(resSatPers);
            }
            
            if(i.getName() == "Taux de réussite du diplôme"){

                  if(lastYearState.getLDotSpeForm() < thisYearState.getLDotSpeForm()){
                    resTauxReu += (thisYearState.getLDotSpeForm()/lastYearState.getLDotSpeForm())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getLDotSpeForm() > thisYearState.getLDotSpeForm()){
                    resTauxReu -= (lastYearState.getLDotSpeForm()/thisYearState.getLDotSpeForm())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getLDotSpeForm() == thisYearState.getLDotSpeForm()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }


                  if(lastYearState.getLDotRecForm() < thisYearState.getLDotRecForm()){
                    resTauxReu += (thisYearState.getLDotRecForm()/lastYearState.getLDotRecForm())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getLDotRecForm() > thisYearState.getLDotRecForm()){
                    resTauxReu -= (lastYearState.getLDotRecForm()/thisYearState.getLDotRecForm())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getLDotRecForm() == thisYearState.getLDotRecForm()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }


                  if(lastYearState.getISatPers() < thisYearState.getISatPers()){
                    resTauxReu += (thisYearState.getISatPers()/lastYearState.getISatPers())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getISatPers() > thisYearState.getISatPers()){
                    resTauxReu -= (lastYearState.getISatPers()/thisYearState.getISatPers())*(1.0/3.0)*lastYearState.getITauxReu();
                  }
                  if(lastYearState.getISatPers() == thisYearState.getISatPers()){
                    //on ne fait rien, pas de changement donc pas d'influence
                  }
                  
                i.setValue(resTauxReu);
            }

        }
        int year = _year + 1;
        State nextYearState = new State(year, _budget.getRemainingBudget(), resTauxReu, resSatPers, resSatEtu, thisYearState.getLDotRecForm(), thisYearState.getLDotSpeForm(), thisYearState.getLDotRecRech(), thisYearState.getLPrime(), thisYearState.getLImmo(), thisYearState.getLSubAssoEtu());
        _stateList.addState(nextYearState);
    }
}
