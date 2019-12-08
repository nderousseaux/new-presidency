package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Collection;

import java.util.HashMap;

import java.util.Map;

import model.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class ControllerTest {
    
    @Test
    public void testIndicatorInferiorOrEqualTo100() throws FileNotFoundException, IOException{
        Controller c = new Controller();
        c.init();
        Collection<Lever> levers = c.getLevers();
        Collection<Indicator> indicators = c.getIndicators();
        StateList states = c.getStateList();
        
        for(Lever l : levers){
            c.setLeverBudget(l, 0.0);
        }
        
        c.endOfRound();
        
        c.getBudget().setRemainingBudget(1000000.0 * levers.size());
        for(Lever l : levers){
            l.setMaxBudget(1000000.0);
            c.setLeverBudget(l,1000000.0);
        }
        c.endOfRound();
        
        State result = states.getState(3);
        HashMap<String, Double> resultIndicators = result.getIndicators();
        
        for(Map.Entry<String,Double> e : resultIndicators.entrySet()){
            Double res = e.getValue();
            
            assertTrue( res <= 100.0);
        }
    }
    
    @Test
    public void testBudgetSpreadLeverNotSuperiorBudget() throws FileNotFoundException, IOException{
        Controller c = new Controller();
        c.init();
        Collection<Lever> levers = c.getLevers();
        Budget b = c.getBudget();
        
        for(Lever l : levers){
            c.setLeverBudget(l, 0.0);
        }
        
        b.setRemainingBudget(-1000.0);
        
        boolean result = c.endOfRound();
        assertTrue(result == false);
    }
}
