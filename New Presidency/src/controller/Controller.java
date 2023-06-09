package controller;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Collection;


import java.util.HashMap;
import java.util.Random;

/**
 * <b><i>Controller</i> est la classe du controlleur principal du jeu</b>
 * <p>
 *     Cette classe s'occupe de manipuler l'ensemble des objets du jeu, notamment:
 *     <ul>
 *         <li>Les <b>indicateurs et leviers</b></li>
 *         <li>Le <b>budget à attribuer à chaque tour</b></li>
 *         <li>Le <b>tour actuel et le nombre de tours total</b></li>
 *         <li>Les <b>fichiers d'initialisation</b> des <b>indicateurs</b>, <b>leviers</b> et <b>coefficients de calcul</b></li>
 *     </ul>
 * </p>
 *
 * @see Lever
 * @see Indicator
 * @see Budget
 * @see GraphicalView
 * @author yoanv lucast
 */
public class Controller {
    private Integer _year;
    private Integer _maxYear;
    private Budget _budget;

    private final StateList _stateList;
    private final ScenarioList _scenarioList;

    private final IndicatorList _indicatorList;
    private final LeverList _leverList;
   
    private File _weightIndicatorFile = new File("coefficient.csv");
    private Matrix _weightForEachIndicator;
    
    private File _leversFile = new File("lever.txt");
    private File _indicatorsFile = new File("indicator.txt");

    /**
     * Constructeur de la classe, instanciant chacun des champs de la classe
     */
    public Controller() throws IOException{
        _budget = new Budget(10000.0);
        _indicatorList= new IndicatorList();
        _leverList= new LeverList();
        _stateList= new StateList();
        _scenarioList = new ScenarioList();
        _year=1;
        _maxYear=8;

        this.init();
    }

    /**
     * Procédure d'<b>initialisation principale</b> appelant d'autres <b>sous-procédures d'initialisation des variables du Controller</b>
     * @throws IOException Lancé en cas d'exceptions d'<b>erreur d'ouverture des fichiers de variables</b>
     *
     * @see Controller#initLevers()
     * @see Controller#initIndicators()
     * @see Controller#initScenario()
     * @see Controller#initState()
     * @see Controller#initWeightForEachIndicator()
     */
    public void init() throws IOException{
        //initialiation des levuers
        initLevers();

        //initialisation des indicateurs
        initIndicators();

        //initialisation des scenarios
        initScenario();

        //initialisation des états
        initState();

        //initialisation de la matrice contenant les coefficients à aplliquer pour chaque indicateur
        initWeightForEachIndicator();
    }

    /**
     * Sous-procédure d'<b>initialisation des leviers</b>
     * @throws IOException Lancé en cas d'exceptions d'<b>erreur d'ouverture des fichiers de variables</b>
     */
    public void initLevers() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(_leversFile));
        String line;
        String[] lineTab;
        
        ArrayList<String> infos = new ArrayList<String>();
        String name = "";
        String abreviation = "";
        String category = "";
        Double value = 0.0;
        Double minValue = 0.0;
        Double maxValue = 0.0;
        
        boolean empty = false;
        boolean end = false;
        

        while ((line = br.readLine()) != null) {
            lineTab = line.split(";");

            switch(lineTab[0]){
                case "New":
                    infos = new ArrayList<String>();
                    break;
                case "info":
                    infos.add(lineTab[1]);
                    break;
                case "name":
                    name = lineTab[1];
                    break;
                case "abreviation":
                    abreviation = lineTab[1];
                    break;
                case "category":
                    category = lineTab[1];
                    break;
                case "value":
                    value = Double.parseDouble(lineTab[1]);
                    break;
                case "min value":
                    minValue = Double.parseDouble(lineTab[1]);
                    break;
                case "max value":
                    maxValue = Double.parseDouble(lineTab[1]);
                    break;
                case "End":
                    end = true;
                    break;
                default:
                    empty = true;
            }
            
            if(!empty && end){
                Lever l = _leverList.createLever(name, abreviation , category, value, minValue, maxValue, infos);
                _budget.setRemainingBudget(_budget.getRemainingBudget()-l.getBudget());
                minValue = 0.0;
                end = false;
            }
            else{
                empty = false;
            }
                
        }
        br.close();
    }

    /**
     * Sous-procédure d'<b>initialisation des indicateurs</b>
     * @throws IOException Lancé en cas d'exceptions d'<b>erreur d'ouverture des fichiers de variables</b>
     */
    public void initIndicators() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(_indicatorsFile));
        String line;
        String[] lineTab;
        
        ArrayList<String> infos = new ArrayList<String>();
        String name = "";
        String abreviation = "";
        Double value = 0.0;
        Double maxValue = 0.0;
        
        boolean empty = false;
        boolean end = false;
        

        while ((line = br.readLine()) != null) {
            lineTab = line.split(";");

            switch(lineTab[0]){
                case "New":
                    infos = new ArrayList<String>();
                    break;
                case "info":
                    infos.add(lineTab[1]);
                    break;
                case "name":
                    name = lineTab[1];
                    break;
                case "abreviation":
                    abreviation = lineTab[1];
                    break;
                case "value":
                    value = Double.parseDouble(lineTab[1]);
                    break;
                case "max value":
                    maxValue = Double.parseDouble(lineTab[1]);
                    break;
                case "End":
                    end = true;
                    break;
                default:
                    empty = true;
            }
            
            if(!empty && end){
                Indicator i = _indicatorList.createIndicator(name, abreviation, value, maxValue, infos);
                end = false;
            }
            else{
                empty = false;
            }
                
        }
        br.close();
    }

    /**
     * Sous-procédure d'<b>initialisation de deux objets State</b> de départ
     * @see State
     */
    public void initState(){
        Collection<Lever> levers = _leverList.getLevers();
        Collection<Indicator> indicators = _indicatorList.getIndicators();
        
        HashMap<String, Double> leversForState0 = new HashMap<String, Double>(levers.size(),(float)1.0);
        HashMap<String,Double> indicatorsForState0 = new HashMap<String, Double>(indicators.size(), (float)1.0);
        
        HashMap<String, Double> leversForState1 = new HashMap<String, Double>(levers.size(),(float)1.0);
        HashMap<String,Double> indicatorsForState1 = new HashMap<String, Double>(indicators.size(), (float)1.0);
        
        for(Lever l : levers){
            leversForState0.put(l.getAbreviation(), l.getBudget());
            leversForState1.put(l.getAbreviation(), l.getBudget());
        }
        
        for(Indicator i : indicators){
            indicatorsForState0.put(i.getAbreviation(), i.getValue());
            indicatorsForState1.put(i.getAbreviation(), i.getValue());
        }
        
        _stateList.createState(0, 10000.0, leversForState0, indicatorsForState0);
        _stateList.createState(1, 10000.0, leversForState1, indicatorsForState1);
    }

    /**
     * Sous-procédure d'<b>initialisation des coefficients pour chaque indicateur</b>
     * @throws IOException Lancé en cas d'exceptions d'<b>erreur d'ouverture des fichiers de variables</b>
     */
    public void initWeightForEachIndicator() throws IOException{
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
                    if(lineTab[i].charAt(0) == 'I'){//if the substring is the name of an indicator
                        linesTitles.put(lineTab[i], orderIndicator);
                        orderIndicator++;
                    }
                    else if(lineTab[i].charAt(0) == 'd'){
                        columnsTitles.put(lineTab[i], orderDifference);
                        orderDifference++;
                    }
                }
                else if(lineTab[i].matches("\\-?[0-9]*\\.?[0-9]*")){//regex for a decimal number
                    _weightForEachIndicator.setCell(matrixLineIndex, matrixColumnIndex, Double.parseDouble(lineTab[i]));
                    if(matrixColumnIndex == _weightForEachIndicator.getColumnSize() -1){
                        matrixColumnIndex = 0;
                        matrixLineIndex++;
                    }
                    else{
                        matrixColumnIndex++;
                    }
                }
                else{
                    //value not process
                }
            }
        }
        br.close();
        _weightForEachIndicator.createTitles(new MatrixTitle(linesTitles, columnsTitles));
    }

    /**
     * Fonction mettant à jour le <b>budget alloué</b> à un <b>levier donné</b>
     * @param lever <b>Levier</b> dont le budget est mis à jour
     * @param val <b>Nouveau budget</b> du levier
     * @return En cas d'<b>erreur de mise à jour</b>:
     * <ul>
     *     <li>Si la valeur est <b>supérieure au budget maximal</b>: retourne <b>"more than max"</b></li>
     *     <li>Si la valeur est <b>inférieure au budget minimal</b>: retourne <b>"less than min"</b></li>
     *     <li>Si le <b>budget restant</b> est insuffisant à la modification: retourne <b>"insufficient budget"</b></li>
     * </ul>
     * En cas d'<b>d'absence d'erreur de mise à jour</b>: retourne <b>"Ok"</b>
     */
    public String setLeverBudget(Lever lever, Double val){
        String result = "Ok";
        String abreviation = lever.getAbreviation();
        double diff=val-lever.getBudget();
        
        if(val>lever.getMaxBudget()){
            result = "more than max";
            return result;
        }
        else if(val<lever.getMinBudget()){
            result = "less than min";
            return result;
        }
        else if(abreviation.equals("LFraisInscr")){
            lever.setBudget(val);
            State thisYearState = _stateList.getState(_year);
            thisYearState.setLever(abreviation, val);
            return result;
        }
        else if(diff>_budget.getRemainingBudget()){
            result = "insufficient budget";
            return result;
        }
        else{
            if(abreviation.contains("Titu") || abreviation.contains("Contr")){
                String category = lever.getCategory().substring(0, 4);
                switch(abreviation.substring(0,abreviation.length()- 4)){
                    case "LSalTitu":
                        Double nbEmployee = _leverList.getLeverByAbreviation("LNbTitu"+category).getBudget();
                        Double totalSalary = nbEmployee * val;
                        Double exTotalSalary = nbEmployee * lever.getBudget();
                        diff = totalSalary - exTotalSalary;
                        if(diff>_budget.getRemainingBudget())
                            return "insufficient budget (salary * number employees)";
                        break;
                    case "LSalContr":
                        nbEmployee = _leverList.getLeverByAbreviation("LNbContr"+category).getBudget();
                        totalSalary = nbEmployee * val;
                        exTotalSalary = nbEmployee * lever.getBudget();
                        diff = totalSalary - exTotalSalary;
                        if(diff>_budget.getRemainingBudget())
                            return "insufficient budget (salary * number employees)";
                        break;
                    case "LNbTitu":
                        Double employeesSalary = _leverList.getLeverByAbreviation("LSalTitu"+category).getBudget();
                        totalSalary = employeesSalary * val;
                        exTotalSalary = employeesSalary * lever.getBudget();
                        diff = totalSalary - exTotalSalary; 
                        if(diff>_budget.getRemainingBudget())
                            return "insufficient budget (salary * number employees)";
                        lever.setMinBudget(val);
                        break;
                    case "LNbContr":
                        employeesSalary = _leverList.getLeverByAbreviation("LSalContr"+category).getBudget();
                        totalSalary = employeesSalary * val;
                        exTotalSalary = employeesSalary * lever.getBudget();
                        diff = totalSalary - exTotalSalary;
                        if(diff>_budget.getRemainingBudget())
                            return "insufficient budget (salary * number employees)";
                        break;
                    default:
                }
            }

            lever.setBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() - diff);
            State thisYearState = _stateList.getState(_year);
            thisYearState.setLever(lever.getAbreviation(), val);
            return result;
        }
    }

    /**
     * Fonction testant la <b>répartition du budget du tour</b>: empêche de valider le tour si <b>l'utilisateur a dépensé plus que disponible</b>
     * @return Booléen <b>vrai</b> si le <b>budget réparti est inférieur ou égal à celui disponnible</b>, <b>faux</b> sinon
     */
    public boolean endOfRound(){
        //tour de jeu...
        boolean budgetIsSufficient = true;
            
        Double sumBudgetInLevers = 0.0;
        for(Lever l : _leverList.getLevers()){
            if(l.getAbreviation() != "LFraisInscr")
                sumBudgetInLevers += l.getBudget();
        }
        
        if(_budget.getRemainingBudget() + sumBudgetInLevers < 0.0){
            budgetIsSufficient = false;
        }
        else{
            updateAll();
            _year++;
        }
        return budgetIsSufficient;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>liste des leviers</b>
     * @return <b>Liste des leviers</b>
     */
    public Collection<Lever> getLevers(){
        return _leverList.getLevers();
    }

    /**
     * Accesseur <b>getteur</b> de la <b>liste des indicateurs</b>
     * @return <b>Liste des indicateurs</b>
     */
    public Collection<Indicator> getIndicators(){
        return _indicatorList.getIndicators();
    }
    public Indicator getIndicator(String abbreviation){

        return _indicatorList.getIndicatorByAbreviation(abbreviation);
    }

    /**
     * Accesseur <b>getteur</b> du <b>budget disponible</b>
     * @return <b>Budget disponible</b>
     */
    public Budget getBudget(){
        return _budget;
    }

    /**
     * Accesseur <b>getteur</b> de l'<b>an actuel</b>
     * @return <b>An actuel</b>
     */
    public int getYear(){
        return _year;
    }

    /**
     * Accesseur <b>getteur</b> de l'<b>année de fin du jeu</b>
     * @return <b>Année de fin du jeu</b>
     */
    public int getMaxYear(){
        return _maxYear;
    }

    /**
     * Accesseur <b>getteur</b> de la <b>liste des objets State de la partie en cours</b>
     * @return <b>Liste des objets State</b> de la partie
     *
     * @see State
     */
    public StateList getStateList() {
        return _stateList;
    }

    /**
     * Fonction de calcul du <b>ratio d'évolution</b> entre l'année précédente et cette année pour un <b>budget de levier</b> donné
     * @param lastYearValue Valeur du <b>budget du levier</b> à l'<b>année précédente</b>
     * @param thisYearValue Valeur du <b>budget du levier</b> à l'<b>année courante</b>
     * @return <b>Ratio d'évolution</b>
     */
    public Double calculRatio(Double lastYearValue, Double thisYearValue){
        if(thisYearValue >= lastYearValue){
            if(thisYearValue == 0.0)//prevents division by 0
                thisYearValue = 1.0;

            Double ratio = (thisYearValue - lastYearValue) / thisYearValue;
            return ratio;
        }
        else{
            if(lastYearValue == 0.0)//prevents division by 0
                lastYearValue = 1.0;

            Double ratio = (thisYearValue - lastYearValue) / lastYearValue;
            return ratio;
        }
    }

    /**
     * Fonction de calcul pour <b>certains indicateurs particuliers</b>
     * @param indic <b>Indicateur</b> dont la valeur est calculé
     * @return <b>Valeur de l'indicateur</b>
     */
    public Double calculValueForInfluencedIndicator(Indicator indic){
        String abreviation = indic.getAbreviation();
        Matrix m = _weightForEachIndicator.copy(_weightForEachIndicator.getLine(abreviation), 0, _weightForEachIndicator.getLine(abreviation)+1, _weightForEachIndicator.getColumnSize());
        Double value = indic.getValue();
        Indicator influencer = null;
        for(int j=0; j<m.getColumnSize(); j++){
            String columnAbreviation =m.getColumnTitle(j).substring(1);
            if(columnAbreviation.charAt(0) == 'I'){
                influencer = _indicatorList.getIndicatorByAbreviation(columnAbreviation);
                if(influencer.getValue() < 50.0){
                    value -= m.getCell(0, j) * (((1-(influencer.getValue()/influencer.getMaxValue())))-0.5) * indic.getValue();
                }
                if(influencer.getValue() > 50.0){
                    value += m.getCell(0, j) * (((influencer.getValue()/influencer.getMaxValue()))-0.5) * indic.getValue();
                }
            }
        }
        return value;
    }

    /**
     * Fonction de <b>génération</b> des valeurs des <b>indicateurs</b> au tour suivant
     */
    public void updateAll(){
        State thisYearState = _stateList.getState(_year);
        State lastYearState = _stateList.getState((_year - 1));
        
        HashMap<String, Double> indicatorsForNextYearState = new HashMap<String, Double>(_indicatorList.getIndicators().size());
        HashMap<String, Double> leversForNextYearState = new HashMap<String, Double>(_leverList.getLevers().size());
        
        HashMap<String, Double> dLevers = new HashMap<String, Double>(_leverList.getLevers().size());
        HashMap<String, Double> dIndicators = new HashMap<String, Double>(_indicatorList.getIndicators().size());
        
        //calculation of ratio for each indicators
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            Double lastYearIndicatorValue = lastYearState.getIndicator(abreviation);
            Double thisYearIndicatorValue = thisYearState.getIndicator(abreviation);
            
            if(abreviation.equals("INbPrNob")){
                    if(lastYearIndicatorValue == 0.0)//prevents division by 0
                        dIndicators.put(abreviation, 0.0);
                    else{
                        Double value = (thisYearIndicatorValue - lastYearIndicatorValue) / lastYearIndicatorValue;
                        dIndicators.put(abreviation, value);
                    }
            }
            else{
                dIndicators.put(abreviation, calculRatio(lastYearIndicatorValue, thisYearIndicatorValue));
            }
        }


        //calculation of ratio for each levers
        for(Lever l : _leverList.getLevers()){
            String abreviation = l.getAbreviation();
            Double lastYearLeverValue = lastYearState.getLever(abreviation);
            Double thisYearLeverValue = thisYearState.getLever(abreviation);
            
            if(abreviation.equals("LFraisInscr")){
                if(thisYearLeverValue <= lastYearLeverValue){
                    if(thisYearLeverValue == 0.0)//prevents division by 0
                    thisYearLeverValue = 1.0;
                
                    Double ratio = (lastYearLeverValue - thisYearLeverValue) / thisYearLeverValue;
                    dLevers.put(abreviation, ratio);
                }
                else{
                    if(lastYearLeverValue == 0.0)//prevents division by 0
                    lastYearLeverValue = 1.0;
                    
                    Double ratio = -1.0*(thisYearLeverValue - lastYearLeverValue) / lastYearLeverValue;
                    dLevers.put(abreviation, ratio);
                }
            }
            
            else{
                dLevers.put(abreviation, calculRatio(lastYearLeverValue, thisYearLeverValue));
            }
        }

        //création de la matrice contenant les indicateurs : indicators
        Matrix indicators = new Matrix(_indicatorList.getIndicators().size(),1);
        //initialisation de la matrice indicators
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            indicators.setCell(_weightForEachIndicator.getLine(abreviation), 0, thisYearState.getIndicator(abreviation));
        }

        //création de la matrice contenant les ratios de chaque indicateur et de chaque levier
        Matrix matrixRatio = new Matrix(_weightForEachIndicator.getColumnSize(), 1);
        //initialisation de la matrice matrixRatio
        for(Lever l : _leverList.getLevers()){
            String abreviation = l.getAbreviation();
            matrixRatio.setCell(_weightForEachIndicator.getColumn("d" + abreviation), 0, dLevers.get(abreviation));
        }
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            matrixRatio.setCell(_weightForEachIndicator.getColumn("d" + abreviation), 0, dIndicators.get(abreviation));
        }

        //création de la matrice contenant le résultat : result
        //calcul des valeurs de chaque indicateur au prochain tour
        Matrix result = _weightForEachIndicator.times(matrixRatio);
        result = result.add(indicators);


        //mise à jour des indicateurs et enregistrement du résultat dans indicatorsForNextYearState
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            Double value = result.getCell(_weightForEachIndicator.getLine(abreviation),0);
            
            //Certains indicateurs ont besoin des valeurs des autres indicateurs après calcul, pour être calculés eux-mêmes
            //pour ces indicateurs particuliers, nous appelons la méthode calculValueForInfluencedIndicator pour calculer
            //leur valeur avant de pouvoir la mettre à jour
            if(abreviation.equals("INbEtu") || abreviation.equals("INbArticles")){
                value = calculValueForInfluencedIndicator(i);
            }
            
            if(abreviation.equals("INbPrNob")){
                if(_year >= 4){
                    Double moyenneRechFond = 0.0;
                    boolean rechFondLessThan50 = false;
                    Double rechFondValue;
                    for(int j=1; j<4; j++){
                        rechFondValue = _stateList.getState(_year - j).getIndicator("IRechFond");
                        if(rechFondValue < 50.0){
                            rechFondLessThan50 = true;
                        }
                        moyenneRechFond += rechFondValue;
                    }
                    moyenneRechFond += _indicatorList.getIndicatorByAbreviation("IRechFond").getValue();
                    moyenneRechFond /= 5.0;
                    
                    if(!rechFondLessThan50){
                        if(moyenneRechFond<50.0){
                            value += 0.0;
                        }
                        else if(moyenneRechFond<65){
                            Random r = new Random();
                            value += Double.valueOf(r.nextInt((1 - 0) + 1) + 0);
                        }
                        else if(moyenneRechFond<90){
                            Random r = new Random();
                            value += Double.valueOf(r.nextInt((3 - 0) + 3) + 0);
                        }
                        else if(moyenneRechFond<99){
                            Random r = new Random();
                            value += Double.valueOf(r.nextInt((5 - 0) + 5) + 0);
                        }
                        else {
                            Random r = new Random();
                            value += Double.valueOf(r.nextInt((7 - 0) + 7) + 0);
                        }
                    }
                    else{
                        value += 0.0;
                    }
                }
                else{
                    value += 0.0;
                }
            }
            
            if(abreviation.equals("IChargeTrav")){
                Double sumEmployeeForm = _leverList.getLeverByAbreviation("LNbTituForm").getBudget() + _leverList.getLeverByAbreviation("LNbContrForm").getBudget();
                Double sumEmployeeRech = _leverList.getLeverByAbreviation("LNbTituRech").getBudget() + _leverList.getLeverByAbreviation("LNbContrRech").getBudget();
                Double workloadForm = (_indicatorList.getIndicatorByAbreviation("INbEtu").getValue())/ sumEmployeeForm;
                Double workloadRech = (_indicatorList.getIndicatorByAbreviation("INbArticles").getValue())/ sumEmployeeRech;

                value = (workloadForm + workloadRech)/2.0;
            }

            //Vérification de la validité du résultat
            if(value > i.getMaxValue())
                value=i.getMaxValue();
            if(value < 0.0)
                value = 0.0;

            //mise à jour de la valeur de l'indicateur
            i.setValue(value);
            
            //enregistrement de la valeur de l'indicateur dans le dictionnaire servant à générer l'état State suivant
            indicatorsForNextYearState.put(abreviation, value);
        }

        //passage à l'année suivante
        int year = _year + 1;
        //calcul du budget de l'année suivante
        _budget.setRemainingBudget(_budget.getRemainingBudget()+10000.0);
        for(Lever l : _leverList.getLevers()){
            Double leverBudget = l.getBudget();
            _budget.setRemainingBudget(_budget.getRemainingBudget()-l.getBudget());
            
            //enregistrement de la valeur du levier dans le dictionnaire servant à générer l'état State suivant
            leversForNextYearState.put(l.getAbreviation(), leverBudget);
        }

        
        //On ajoute le produit des frais d'inscription et du nombre d'élèves de l'année prochaine au budget
        _budget.setRemainingBudget(_budget.getRemainingBudget() + indicatorsForNextYearState.get("INbEtu") * leversForNextYearState.get("LFraisInscr"));
        //On met à jour la valeur maximale de chaque indicateur avec la nouvelle valeur du budget disponible. Sauf pour les leviers ne représentant pas une part du budget
        for(Lever l : _leverList.getLevers()){
            if(l.getAbreviation().equals("LNbTituForm") || l.getAbreviation().equals("LSalTituForm") || l.getAbreviation().equals("LNbContrForm") || l.getAbreviation().equals("LSalContrForm") || l.getAbreviation().equals("LFraisInscr") || l.getAbreviation().equals("LNbContrRech") || l.getAbreviation().equals("LSalContrRech") || l.getAbreviation().equals("LNbTituRech") || l.getAbreviation().equals("LSalTituRech")){
                
            }
            else{
                l.setMaxBudget(_budget.getRemainingBudget());
            }
        }
        
        //création de l'état(State) suivant
        State nextYearState = new State(year, _budget.getRemainingBudget(), leversForNextYearState, indicatorsForNextYearState);
        _stateList.addState(nextYearState);
    }

    /**
     * Fonction d'<b>initialisation du scénario</b> de la partie en cours
     * @throws IOException Lancé en cas d'exceptions d'<b>erreur d'ouverture des fichiers de variables</b>
     */
    public void initScenario() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("scenarios.txt"));
        String line;
        String[] lineTab;

        ArrayList<String> infos = new ArrayList<String>();
        String name = "";
        String abreviation = "";
        HashMap<String, Double> depart = new HashMap<>();
        HashMap<String, Double> victoire = new HashMap<>();
        HashMap<String, Double> defaite = new HashMap<>();

        boolean empty = false;
        boolean end = false;


        while ((line = br.readLine()) != null) {
            lineTab = line.split(";");

            switch(lineTab[0]){
                case "New":
                    infos = new ArrayList<String>();
                    depart = new HashMap<>();
                    victoire = new HashMap<>();
                    defaite = new HashMap<>();
                    break;
                case "info":
                    infos.add(lineTab[1]);
                    break;
                case "name":
                    name = lineTab[1];
                    break;
                case "abreviation":
                    abreviation = lineTab[1];
                    break;
                case "dep":
                    String[] dep = lineTab[1].split("=");
                    depart.put(dep[0], Double.parseDouble(dep[1]));
                    break;
                case "vic":
                    String[] vic = lineTab[1].split(">");
                    victoire.put(vic[0], Double.parseDouble(vic[1]));
                    break;
                case "def":
                    String[] def = lineTab[1].split("<");
                    defaite.put(def[0], Double.parseDouble(def[1]));
                    break;
                case "End":
                    end = true;
                    break;
                default:
                    empty = true;
            }

            if(!empty && end){
                Scenario s = _scenarioList.createScenario(name, abreviation,depart, victoire, defaite, infos);
                end = false;
            }
            else{
                empty = false;
            }

        }
        br.close();



    }

    /**
     * Accesseur <b>getteur</b> des <b>noms des scénarios</b> proposés
     * @return <b>Noms des scénarios</b> proposés
     */
    public ArrayList<String> getScenarioNames(){
        ArrayList<String> res = new ArrayList<>();
        for (Scenario s:_scenarioList.getScenarios()) {
            res.add(s.getName());
        }
        return res;
    }

    /**
     * Accesseur <b>getteur</b> des <b>informations du scénario</b> de la partie actuelle
     * @param name <b>Nom</b> du scénario de la partie
     * @return <b>Informations du scénario</b> de la partie actuelle
     */
    public Collection<String> getInfoScenario(String name){
        return _scenarioList.getScenario(name).getInfos();
    }

    /**
     * Accesseur <b>setteur</b> permettant le <b>choix du scénario</b>, en mettant à jour les <b>valeurs des indicateurs</b>
     * @param name <b>Nom</b> du <b>scénario choisi</b>
     */
    public void setIndicatorFunctScenar(String name){
        Scenario scenar = _scenarioList.getScenario(name);

        //Maintenant, on met à jour les valeurs de départ des indicateurs
        for (HashMap.Entry<String, Double> entry : scenar.getStart().entrySet())
        {
            _indicatorList.getIndicatorByAbreviation(entry.getKey()).setValue(entry.getValue());
        }
        //Maintenant, on met à jour les valeurs de départ des indicateurs
        for (HashMap.Entry<String, Double> entry : scenar.getStart().entrySet())
        {
            _stateList.getState(0).setIndicator(entry.getKey(),entry.getValue());
            _stateList.getState(1).setIndicator(entry.getKey(),entry.getValue());
        }



    }

    /**
     * Fonction vérifiant la <b>complétion des conditions de victoire</b> du <b>scénario choisi</b>
     * @param scenario <b>Scénario choisi</b>
     * @return Booléen: <b>vrai</b> si les conditions de <b>victoire</b> sont <b>complétées</b>, <b>faux</b> sinon
     */
    public boolean victoryValidated(String scenario){
        Boolean res = true;
        for (HashMap.Entry<String, Double> entry :_scenarioList.getScenario(scenario).getVictory().entrySet()) {
            if(_indicatorList.getIndicatorByAbreviation(entry.getKey()).getValue() < entry.getValue()){
                res = false;
                break;
            }
        }

        return res;
    }

    /**
     * Fonction vérifiant la <b>complétion des conditions de défaite</b> du <b>scénario choisi</b>
     * @param scenario <b>Scénario choisi</b>
     * @return Booléen: <b>vrai</b> si les conditions de <b>défaite</b> sont <b>complétées</b>, <b>faux</b> sinon
     */
    public ArrayList<String> defeatValidated(String scenario){
        ArrayList<String> res = new ArrayList<>();
        for (HashMap.Entry<String, Double> entry :_scenarioList.getScenario(scenario).getDefeat().entrySet()) {
            if(_indicatorList.getIndicatorByAbreviation(entry.getKey()).getValue() <= entry.getValue()){
                res.add("D"+entry.getKey());
                break;
            }
        }
        for (Indicator i:_indicatorList.getIndicators()) {
            /*if(i.getValue()<10){
                if(!i.getAbreviation().equals("INbEtu") && !i.getAbreviation().equals("INbPrNob") && !i.getAbreviation().equals("INbArticles") && !i.getAbreviation().equals("IChargeTrav")){
                    res.add("d"+i.getAbreviation());
                }

            }*/
        }
        if(_indicatorList.getIndicatorByAbreviation("INbEtu").getValue() <= 0){
            res.add("d"+"INbEtu");
        }
        return res;
    }

    /**
     * Fonction appelant les fonctions de <b>vérification de victoire ou de défaite</b> du scénario, à chaque fin de tour
     * @param scenario <b>Scénario de la partie actuelle</b>
     * @return En cas de <b>victoire</b>: retourne <b>"VCondi"</b>, en cas de <b>défaite</b>: retourne l'<b>ensemble des indicateurs ayant conduit à la défaite</b>, sinon, renvoie une <b>liste vide</b>
     */
    public ArrayList<String> end(String scenario){
        ArrayList<String> res = new ArrayList<>();

        //On teste la victoire
        if(victoryValidated(scenario)){
            res.add("VCondi");
        }
        //On teste la defaite
        else if(_year>_maxYear){
            res.add("DYear");
        }
        else if(defeatValidated(scenario).size()!=0){
            res.addAll(defeatValidated(scenario));
        }


        return res;
    }
}
