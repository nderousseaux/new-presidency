package model;

import java.util.HashMap;
import java.util.Map;

public class MatrixTitle {
    HashMap<String, Integer> _linesTitles;
    HashMap<String, Integer> _columnsTitles;
    
    public MatrixTitle(HashMap<String, Integer> linesTitles, HashMap<String, Integer> columnsTitles){
        _linesTitles = linesTitles;
        _columnsTitles = columnsTitles;
    }
    
    public MatrixTitle copy(int lineStart, int columnStart, int lineEnd, int columnEnd){
        HashMap<String, Integer> resultLinesTitles = new HashMap<String, Integer>(lineEnd-lineStart,(float)1.0);
        HashMap<String, Integer> resultColumnsTitles = new HashMap<String, Integer>(columnEnd-columnStart, (float)1.0);
        
        for(Map.Entry<String,Integer> e : _linesTitles.entrySet()){
            String title = e.getKey();
            Integer index = e.getValue();
            
            if(index >= lineStart && index < lineEnd){
                resultLinesTitles.put(title, index-lineStart);
            }
        }
        
        for(Map.Entry<String,Integer> e : _columnsTitles.entrySet()){
            String title = e.getKey();
            Integer index = e.getValue();
            
            if(index >= columnStart && index < columnEnd){
                resultColumnsTitles.put(title, index-columnStart);
            }
        }
        
        return new MatrixTitle(resultLinesTitles, resultColumnsTitles);
    }
    
    public int getLinesSize(){
        return _linesTitles.size();
    }
    
    public int getColumnsSize(){
        return _columnsTitles.size();
    }

    int getLine(String line) {
        return _linesTitles.get(line);
    }

    int getColumn(String column) {
        return _columnsTitles.get(column);
    }
}
