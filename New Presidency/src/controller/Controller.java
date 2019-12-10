package controller;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Collection;

import static java.lang.System.exit;

import java.util.HashMap;
import java.util.Map;

public class Controller {
    private Integer _year;
    private Integer _maxYear;
    private Budget _budget;

    private final StateList _stateList;
    private final IndicatorList _indicatorList;
    private final LeverList _leverList;
   
    private File _weightIndicatorFile = new File("coefficient.csv");
    private Matrix _weightForEachIndicator;
    
    private File _leversFile = new File("lever.txt");
    private File _indicatorsFile = new File("indicator.txt");

    public Controller(){
        _budget=new Budget();
        _indicatorList= new IndicatorList();
        _leverList= new LeverList();
        _stateList= new StateList();
        _year=1;
        _maxYear=8;

    }

    public void init() throws IOException{
        //initialiation of levers
        initLevers();

        //initialisation of indicators
        initIndicators();
        
        initState();

        initWeightForEachIndicator();
    }

    public void initLevers() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(_leversFile));
        String line;
        String[] lineTab;
        
        ArrayList<String> infos = new ArrayList<String>();
        String name = "";
        String abreviation = "";
        String category = "";
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
                case "category":
                    category = lineTab[1];
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
                Lever l = _leverList.createLever(name, abreviation , category, value, maxValue, infos);
                _budget.setRemainingBudget(_budget.getRemainingBudget()-l.getBudget());
                end = false;
            }
            else{
                empty = false;
            }
                
        }
        br.close();
    }
    
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
                else{
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

    public Integer setLeverBudget(Lever lever, Double val){
        double diff=val-lever.getBudget();
        if(diff<=_budget.getRemainingBudget() && val<=lever.getMaxBudget()) {

            lever.setBudget(val);
            _budget.setRemainingBudget(_budget.getRemainingBudget() - diff);
            State thisYearState = _stateList.getState(_year);
            thisYearState.setLever(lever.getAbreviation(), val);
            return 0;

        }
        else{
            return -1;
        }
    }

    public boolean endOfRound(){
        //tour de jeu...
        boolean budgetIsSufficient = true;
            
        Double sumBudgetInLevers = 0.0;
        for(Lever l : _leverList.getLevers()){
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

    public Collection<Lever> getLevers(){
        return _leverList.getLevers();
    }

    public Collection<Indicator> getIndicators(){
        return _indicatorList.getIndicators();
    }

    public Budget getBudget(){
        return _budget;
    }

    public int getYear(){
        return _year;
    }

    public int getMaxYear(){
        return _maxYear;
    }

    public StateList getStateList() {
        return _stateList;
    }

    public void updateAll(){
        State thisYearState = _stateList.getState(_year);
        State lastYearState = _stateList.getState((_year - 1));
        
        HashMap<String, Double> indicatorsForNextYearState = new HashMap<String, Double>(_indicatorList.getIndicators().size(), (float)1.0);
        HashMap<String, Double> leversForNextYearState = new HashMap<String, Double>(_leverList.getLevers().size(), (float)1.0);
        
        HashMap<String, Double> dLevers = new HashMap<String, Double>(_leverList.getLevers().size(), (float)1.0);
        HashMap<String, Double> dIndicators = new HashMap<String, Double>(_indicatorList.getIndicators().size(), (float)1.0);
        
        //calculation of ratio for each indicators
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            Double lastYearIndicatorValue = lastYearState.getIndicator(abreviation);
            Double thisYearIndicatorValue = thisYearState.getIndicator(abreviation);
            
            if(lastYearIndicatorValue == 0.0)//prevents division by 0
                lastYearIndicatorValue = 1.0;
            
            Double value = (thisYearIndicatorValue - lastYearIndicatorValue) / lastYearIndicatorValue;
            dIndicators.put(abreviation, value);
        }
        
        //calculation of ratio for each levers
        for(Lever l : _leverList.getLevers()){
            String abreviation = l.getAbreviation();
            Double lastYearLeverValue = lastYearState.getLever(abreviation);
            Double thisYearLeverValue = thisYearState.getLever(abreviation);
            
            if(lastYearLeverValue == 0.0)//prevents division by 0
                lastYearLeverValue = 1.0;
            
            Double ratio = (thisYearLeverValue - lastYearLeverValue) / lastYearLeverValue;
            dLevers.put(abreviation, ratio);
        }

        //création de la matrice contenant les indicateurs
        Matrix indicators = new Matrix(_indicatorList.getIndicators().size(),1);
        
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            indicators.setCell(_weightForEachIndicator.getLine(abreviation), 0, thisYearState.getIndicator(abreviation));
        }

        //matrixRatio's creation
        Matrix matrixRatio = new Matrix(_weightForEachIndicator.getColumnSize(), 1);
        
        //matrixRatio's initialisation
        for(Lever l : _leverList.getLevers()){
            String abreviation = l.getAbreviation();
            matrixRatio.setCell(_weightForEachIndicator.getColumn("d" + abreviation), 0, dLevers.get(abreviation));
        }
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            matrixRatio.setCell(_weightForEachIndicator.getColumn("d" + abreviation), 0, dIndicators.get(abreviation));
        }

        matrixRatio.print();
        System.out.println("");

        Matrix result = _weightForEachIndicator.times(matrixRatio);
        result.print();
        System.out.println("");
        result = result.add(indicators);

        result.print();
        System.out.println("");

        //update indicators
        for(Indicator i : _indicatorList.getIndicators()){
            String abreviation = i.getAbreviation();
            Double value = result.getCell(_weightForEachIndicator.getLine(abreviation),0);
            i.setValue(value);
            
            indicatorsForNextYearState.put(abreviation, value);//enregistrement de la valeur de l'indicateur dans le dictionnaire servant à générer l'état State suivant
        }

        //passage à l'année suivante
        int year = _year + 1;
        //calcul du budget de l'année suivante
        _budget.setRemainingBudget(_budget.getRemainingBudget()+10000.0);
        for(Lever l : _leverList.getLevers()){
            Double leverBudget = l.getBudget();
            _budget.setRemainingBudget(_budget.getRemainingBudget()-l.getBudget());
            
            leversForNextYearState.put(l.getAbreviation(), leverBudget);//enregistrement de la valeur du levier dans le dictionnaire servant à générer l'état State suivant
        }
        
        //checking that value in indicatorsForNextYearState are valid 
        for(Map.Entry<String, Double> e : indicatorsForNextYearState.entrySet()){
            String abreviation = e.getKey();
            Double value = e.getValue();
            
            Indicator i = _indicatorList.getIndicatorByAbreviation(abreviation);
            
            if(value > i.getMaxValue()){
                e.setValue(i.getMaxValue());
            }
            if(value < 0.0){
                e.setValue(0.0);
            }
        }
        
        //création de l'état(State) suivant
        State nextYearState = new State(year, _budget.getRemainingBudget(), leversForNextYearState, indicatorsForNextYearState);
        _stateList.addState(nextYearState);
    }
}
