package controller;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;

import model.*;
import view.*;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.exit;

import java.util.HashMap;

public class Controller {
    private GraphicLine _graphicLine;
    private GraphicPie _graphicPie;
    private IndicatorList _indicatorList;
    private LeverList _leverList;
    private Budget _budget;
    private StateList _stateList;
    private Integer _year;
    private Integer _maxYear;


    File _weightIndicatorFile = new File("coefficient.txt");
    private Matrix _weightForEachIndicator;

    public Controller() throws FileNotFoundException, IOException{
        _budget=new Budget();
        _indicatorList=IndicatorList.getInstance();
        _leverList=LeverList.getInstance();
        _stateList=StateList.getInstance();
        _stateList.createState(0,10000.0,50.0,50.0,50.0,100.0,100.0,100.0,100.0,100.0,100.0);
        _stateList.createState(1,10000.0,50.0,50.0,50.0,100.0,100.0,100.0,100.0,100.0,100.0);
        _year=1;
        _maxYear=8;

    }

    public StateList getStateList() {
        return _stateList;
    }


    public void init() throws FileNotFoundException,IOException{
        //CrÃ©ation des leviers
        //Recherche

        //Lever recTitulaire =  _leverList.createLever("Titulaires en recherche", 100.0, new ArrayList<String>());
        //Lever recContractuel = _leverList.createLever("Contractuels en recherche", 100.0, new ArrayList<String>());
        //Lever recDotRec = _leverList.createLever("Dotation rÃ©curante en recherche", 100.0, new ArrayList<String>());
        //Lever recDotSpe = _leverList.createLever("Dotation spÃ©cifique en recherche", 100.0, new ArrayList<String>());
        //Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100.0, new ArrayList<String>());
        //Lever rechPrime = _leverList.createLever("Primes donnÃ©e Ã  la recherche", 100.0, new ArrayList<String>());


        //Lever recTitulaire =  _leverList.createLever("Titulaires en recherche", 100.0, new ArrayList<String>());
        //Lever recContractuel = _leverList.createLever("Contractuels en recherche", 100.0, new ArrayList<String>());
        ArrayList<String> infosRecDotRec=new ArrayList<>();
        infosRecDotRec.add("Budget récurrent alloué à la recherche");
        Lever recDotRec = _leverList.createLever("Dotation récurante en recherche", 100.0,10000.0, infosRecDotRec);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-recDotRec.getBudget());
        //Lever recDotSpe = _leverList.createLever("Dotation spÃ©cifique en recherche", 100.0, new ArrayList<String>());
        //Lever recValorisation = _leverList.createLever("Valorisation de la recherche", 100.0, new ArrayList<String>());
        //Lever rechPrime = _leverList.createLever("Primes donnÃ©e Ã  la recherche", 100.0, new ArrayList<String>());



        //Formation
        //Lever formTitulaire = _leverList.createLever("Titulaires en formation", 100.0, new ArrayList<String>());
        //Lever formContractuel = _leverList.createLever("Contractuels en formation", 100.0, new ArrayList<String>());
        ArrayList<String> infosFormDotRec=new ArrayList<>();
        infosFormDotRec.add("Budget récurrent alloué aux formations");
        Lever formDotRec = _leverList.createLever("Dotation récurente pour la formation", 100.0,10000.0, infosFormDotRec);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-formDotRec.getBudget());

        ArrayList<String> infosFormDotSpe= new ArrayList<>();
        infosFormDotSpe.add("Budget spécifique alloué aux formations");
        Lever formDotSpe = _leverList.createLever("Dotation spécifique pour la formation", 100.0,10000.0, infosFormDotSpe);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-formDotSpe.getBudget());
        //Lever formDotPed = _leverList.createLever("Dotation pour les projets pÃ©dagogiques", 100.0, new ArrayList<String>());
        //Lever formPartenariat = _leverList.createLever("Partenariat pour la formation", 100.0, new ArrayList<String>());
        //Lever formFraiIns = _leverList.createLever("Frais d'inscription", 100.0, new ArrayList<String>());

        ArrayList<String> infosFormPrime= new ArrayList<>();
        infosFormPrime.add("Budget alloué à  la prime de formation des enseignants");
        infosFormPrime.add("Des enseignants plus formés donnent de meilleurs cours!");
        Lever formPrime= _leverList.createLever("Prime de formation", 100.0,10000.0, infosFormPrime);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-formPrime.getBudget());
        //Central
        //Lever cCom = _leverList.createLever("Communication gÃ©nÃ©rale", 100.0, new ArrayList<String>());

        ArrayList<String> infosSubEtu=new ArrayList<>();
        infosSubEtu.add("Budget alloué aux associations étudiantes");
        infosSubEtu.add("L'augmenter augmentera l'humeur des étudiants dans l'université, mais attention!");
        infosSubEtu.add("Buvez toujours avec Modération (très bon ami des programmeurs)!");
        Lever subEtu = _leverList.createLever("Subventions aux associations étudiantes", 100.0,10000.0, infosSubEtu);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-subEtu.getBudget());
        //Immobilier

        ArrayList<String> infosCons=new ArrayList<>();
        infosCons.add("Budget général de l'immobilier");
        infosCons.add("De meilleurs batiments apportent un niveau d'enseignement amélioré");
        Lever constr = _leverList.createLever("Investissement en construction", 100.0,10000.0, infosCons);
        _budget.setRemainingBudget(_budget.getRemainingBudget()-constr.getBudget());
        //Lever iEnt = _leverList.createLever("Investissement en entretient des bÃ¢timents", 100.0, new ArrayList<String>());
        //Lever iReno = _leverList.createLever("Investissement en rÃ©novation des bÃ¢timents", 100.0, new ArrayList<String>());


        //CrÃ©ation des indicateurs

        /*
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiÃ©s", 50.0, aP, new ArrayList<String>());
         */
        /*
        //Nombre de professeurs
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'universitÃ©", 50.0, nP, new ArrayList<String>());
        */
        /*
        //Reputation de la formation
        Indicator repFormation = _indicatorList.createIndicator("RÃ©putation de la formation", 50.0, rF, new ArrayList<String>());
        */

        /*
        //Article publiÃ©s
        ArrayList<String> infosArticles=new ArrayList<>();
        infosArticles.add("Les articles publiÃ©s par les chercheurs");
        infosArticles.add("ReprÃ©sentent la visibilitÃ© de l'universitÃ©, l'intÃ©rÃªt qu'on lui porte");
        Indicator articlesPublies = _indicatorList.createIndicator("Nombre d'articles publiÃ©s", 50.0, infosArticles);

        //Nombre de professeurs
        ArrayList<String> infosProfs = new ArrayList<>();
        infosProfs.add("Nombre de professeurs enseignants Ã  l'universitÃ©");
        infosProfs.add("ReprÃ©sente le niveau d'enseignement de l'universitÃ©, et par consÃ©quent son niveau de rÃ©ussite est prÃ©supposÃ© meilleur");
        Indicator nbProfesseurs = _indicatorList.createIndicator("Nombre de professeur de l'universitÃ©", 50.0, infosProfs);

        //Reputation de la formation
        ArrayList<String> infosRep=new ArrayList<>();
        infosRep.add("RÃ©putation gÃ©nÃ©rale du niveau d'enseignement");
        infosRep.add("ReprÃ©sente la visibilitÃ© et l'apprÃ©ciation publique de la formation");
        Indicator repFormation = _indicatorList.createIndicator("RÃ©putation de la formation", 50.0, infosRep);
        */


        //Taux de rÃ©ussite
        ArrayList<String> infosReu=new ArrayList<>();
        infosReu.add("Niveau de réussite de la formation");
        infosReu.add("Représente par extension le niveau d'enseignement de la formation et l'encadrement des étudiants");
        Indicator tauxReussite = _indicatorList.createIndicator("Taux de réussite du diplôme", 50.0,100.0,infosReu);

        //Satisfaction etudiante

        ArrayList<String> infosSatisEtu=new ArrayList<>();
        infosSatisEtu.add("Représente le niveau d'appréciations des étudiants");
        infosSatisEtu.add("Attention à  ne pas oublier qu'un étudiant qui s'amuse trop travaille moins...");
        Indicator satisEtu = _indicatorList.createIndicator("Satisfaction etudiante", 50.0,100.0, infosSatisEtu);



        //Satisfaction personnel

        ArrayList<String> infosSatisPers=new ArrayList<>();
        infosSatisPers.add("Représente le niveau d'appréciation des professeur");
        infosSatisPers.add("Attention à  ne pas oublier qu'un professeur qui ne se plait pas dans son travail enseigne moins bien...");


        Indicator satisPers = _indicatorList.createIndicator("Satisfaction personnel",50.0,100.0,infosSatisPers);
        /*
        //Nombre d'Ã©tudiant
        Indicator nbEtu = _indicatorList.createIndicator("Nombre d'Ã©tudiant", 50.0, nE, new ArrayList<String>());
        */
        //Indicator nbEtu = _indicatorList.createIndicator("Nombre d'Ã©tudiant", 50.0, new ArrayList<String>());


        initWeightForEachIndicator();
    }


    public void initWeightForEachIndicator() throws FileNotFoundException,IOException{
        int leverListSize = _leverList.getLevers().size();
        int indicatorListSize = _indicatorList.getIndicators().size();

        _weightForEachIndicator = new Matrix(indicatorListSize, leverListSize+indicatorListSize);
        HashMap<String, Integer> linesTitles = new HashMap<String, Integer>(indicatorListSize,(float)1.0);
        HashMap<String, Integer> columnsTitles = new HashMap<String, Integer>(leverListSize+indicatorListSize,(float)1.0);

        BufferedReader br = new BufferedReader(new FileReader(_weightIndicatorFile));
        String line;
        String[] lineTab;
        int matrixLineIndex = 0;
        int matrixColumnIndex = 0;

        int orderIndicator = 0;
        int orderDifference = 0;

        while ((line = br.readLine()) != null) {
            line = line.replaceAll("\\s+","");//remove all space
            lineTab = line.split(";");

            for(int i=0; i < lineTab.length; i++){
                if(lineTab[i].matches("[a-zA-Z]*")){//if the substring starts with a letter
                    if(lineTab[i].charAt(0) == 'i'){//if the substring is the name of an indicator
                        System.out.println(lineTab[i]);
                        linesTitles.put(lineTab[i], orderIndicator);
                        orderIndicator++;
                    }
                    else if(lineTab[i].charAt(0) == 'd'){
                        System.out.println(lineTab[i]);
                        columnsTitles.put(lineTab[i], orderDifference);
                        orderDifference++;
                    }
                }
                else{
                    //System.out.println(lineTab[i]);
                    _weightForEachIndicator.setCell(matrixLineIndex, matrixColumnIndex, Double.parseDouble(lineTab[i]));
                    if(matrixColumnIndex == _weightForEachIndicator.getColumnSize() -1){
                        matrixColumnIndex = 0;
                        matrixLineIndex++;
                    }
                    else{
                        matrixColumnIndex++;
                    }
                }
            }
        }
        br.close();
        _weightForEachIndicator.createTitles(new MatrixTitle(linesTitles, columnsTitles));
    }

    public Integer addToBudget(Lever lever, Double val){
        if(val<=_budget.getRemainingBudget()) {
            lever.addToBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() - val);
            return 0;
        }
        else{
            return -1;
        }
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
        //On ferme le graphiqueLine si il existe
        if (_graphicLine != null) {
            _graphicLine.close();
        }

        //On ferme le graphiquePie si il existe
        if (_graphicPie != null) {
            _graphicPie.close();
        }
        //On ouvre le graphicpie
        _graphicPie = new GraphicPie(getLevers(), _budget.getRemainingBudget());
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
        State thisYearState = _stateList.getState(_year);
        State lastYearState = _stateList.getState((_year - 1));

        double resSatEtu = lastYearState.getISatEtu();
        double resSatPers = lastYearState.getISatPers();
        double resTauxReu = lastYearState.getITauxReu();

        double dLSubAssoEtu = (thisYearState.getLSubAssoEtu() - lastYearState.getLSubAssoEtu()) / lastYearState.getLSubAssoEtu();
        double dLDotRecForm = (thisYearState.getLDotRecForm() - lastYearState.getLDotRecForm()) / lastYearState.getLDotRecForm();
        double dLDotRecRech = (thisYearState.getLDotRecRech() - lastYearState.getLDotRecRech()) / lastYearState.getLDotRecRech();
        double dLDotSpeForm = (thisYearState.getLDotSpeForm() - lastYearState.getLDotSpeForm()) / lastYearState.getLDotSpeForm();
        double dLImmo = (thisYearState.getLImmo() - lastYearState.getLImmo()) / lastYearState.getLImmo();
        double dLPrime = (thisYearState.getLPrime() - lastYearState.getLPrime()) / lastYearState.getLPrime();

        //Pour chaque indicateur on calcule la valeur au tour suivant

        //calcul de la satisfaction etudiante
        Indicator i = _indicatorList.getIndicator("Satisfaction etudiante");

        resSatEtu += dLSubAssoEtu*(1.0/3.0)*(lastYearState.getISatEtu()/i.getMaxValue());

        resSatEtu += dLImmo * (1.0/3.0) * (lastYearState.getISatEtu()/i.getMaxValue());

        i.setValue(resSatEtu);


        // calcul de la satisfaction du personel
        i = _indicatorList.getIndicator("Satisfaction personnel");

        resSatPers += dLPrime*(1.0/5.0)*(lastYearState.getISatPers()/i.getMaxValue());

        resSatPers += dLDotRecForm*(1.0/5.0)*(lastYearState.getISatPers()/i.getMaxValue());

        resSatPers += dLDotRecRech*(1.0/5.0)*(lastYearState.getISatPers()/i.getMaxValue());

        resSatPers += dLImmo * (1.0/5.0) * (lastYearState.getISatPers()/i.getMaxValue());

        i.setValue(resSatPers);

        //creation de la variable contenant la différence entre la satisfaction du personel de l'année dernière et de cette année
        double dISatPers = (resSatPers - lastYearState.getISatPers()) / lastYearState.getISatPers();

        //calcul du taux de reussite du diplome
        i = _indicatorList.getIndicator("Taux de réussite du diplôme");

        resTauxReu += dLDotSpeForm*(1.0/3.0)*(lastYearState.getITauxReu()/i.getMaxValue());

        resTauxReu += dLDotRecForm*(1.0/3.0)*(lastYearState.getITauxReu()/i.getMaxValue());

        resTauxReu += dISatPers*(1.0/3.0)*(lastYearState.getITauxReu()/i.getMaxValue());

        i.setValue(resTauxReu);

        //passage à l'année suivante
        int year = _year + 1;
        //calcul du budget de l'année suivante
        _budget.setRemainingBudget(_budget.getRemainingBudget()+10000.0);
        for(Lever l : _leverList.getLevers()){
            _budget.setRemainingBudget(_budget.getRemainingBudget()-l.getBudget());
        }
        //création de l'état(State) suivant
        State nextYearState = new State(year, _budget.getRemainingBudget(), resTauxReu, resSatPers, resSatEtu, thisYearState.getLDotRecForm(), thisYearState.getLDotSpeForm(), thisYearState.getLDotRecRech(), thisYearState.getLPrime(), thisYearState.getLImmo(), thisYearState.getLSubAssoEtu());
        _stateList.addState(nextYearState);
    }

    public void showGraphicLine(){

        if(_year!=1)
            _graphicLine=new GraphicLine(_stateList);
        else
            System.out.println("Impossible d'afficher les graphiques, vous êtes au premier tour !");
    }

}
