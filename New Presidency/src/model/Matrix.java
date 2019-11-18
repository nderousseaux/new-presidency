package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;

public class Matrix{
    private Integer _lineSize;
    private Integer _columnSize;
    private Double _values[][];

    private MatrixTitle _titles = null;


    public Matrix(Integer lineSize, Integer columnSize) {
        _lineSize = lineSize;
        _columnSize = columnSize;

        _values = new Double[lineSize][columnSize];
        setAllValuesTo0();
    }


    public Integer getLineSize() {
        return _lineSize;
    }

    public Integer getColumnSize() {
        return _columnSize;
    }

    public Double[][] getValues() {
        return _values;
    }

    protected void setValues(Double[][] values){
        _values = values;
    }

    public Double getCell(int line, int column){
        return _values[line][column];
    }

    public Double getCell(String line, String column){
        Double result;
        int lineIndex = _titles.getLine(line);
        int columnIndex = _titles.getColumn(column);
        result = getCell(lineIndex, columnIndex);
        return result;
    }

    public void setCell(int line, int column, Double value){
        _values[line][column] = value;
    }

    public void setAllValuesTo0(){
        for(int i=0; i < _lineSize; i++){
            for(int j=0; j < _columnSize; j++){
                _values[i][j] = 0.0;
            }
        }
    }

    public Matrix add(Matrix m) throws Error{
        if(_lineSize != m.getLineSize() && _columnSize != m.getColumnSize()){
            throw new Error("Two matrices must have an equal number of rows and columns to be added");
        }
        Matrix result = new Matrix(_lineSize, _columnSize);
        for(int i=0; i < _lineSize; i++){
            for(int j=0; j < _columnSize; j++){
                result.setCell(i, j, _values[i][j] + m.getCell(i, j));
            }
        }
        return result;
    }

    public Matrix times(Matrix m) throws Error{
        if(_columnSize != m.getLineSize()){
            throw new Error("The column size and the line size of respectively the first and respectively the second matrix are not equals");
        }
        Integer lineSize = _lineSize;
        Integer columnSize = m.getColumnSize();

        Matrix result = new Matrix(lineSize, columnSize);

        Double value = 0.0;
        int k = 0;

        for(int i=0; i < lineSize; i++){
            for(int j=0; j < columnSize; j++){
                while(k < _columnSize){
                    value+= _values[i][k] * m.getCell(k, j);
                    k++;
                }
                k = 0;
                result.setCell(i, j, value);
                value = 0.0;
            }
        }

        return result;
    }

    public Matrix copy(int lineStart, int columnStart, int lineEnd, int columnEnd) throws Error,FileNotFoundException,IOException{
        if(lineStart<0 || columnStart<0 || lineEnd>_lineSize || columnEnd>_columnSize){
            throw new Error("the given indexes are out of the matrix bound");
        }
        int nbLineCopy = lineEnd - lineStart;
        int nbColumnCopy = columnEnd - columnStart;

        Matrix result = new Matrix(nbLineCopy, nbColumnCopy);

        for(int i=0; i<nbLineCopy; i++){
            for(int j=0; j<nbColumnCopy; j++){
                result.setCell(i, j, getCell(i+lineStart, j+columnStart));
                System.out.println("copy cell(" +i+ ", " +j+ ") : " + result.getCell(i, j));
            }
        }

        if(_titles != null){
            MatrixTitle resultTitles = _titles.copy(lineStart, columnStart, lineEnd, columnEnd);
            result.createTitles(resultTitles);
        }

        return result;
    }

    public void changeTitles(MatrixTitle t) throws Error{
        if(t.getLinesSize()  == _titles.getLinesSize() && t.getColumnsSize() == _titles.getColumnsSize())
            this._titles = t;
        else{
            throw new Error("You can't change the size of a matrix, you should create a new one");
        }
    }

    public void createTitles(MatrixTitle t) throws Error{
        if(_titles == null){
            if(t.getLinesSize()== _lineSize && t.getColumnsSize() == _columnSize){
                _titles = t;
            }
            else{
                throw new Error("The size of the titles don't match the size of the matrix");
            }
        }
        else{
            throw new Error("The Titles are already created, try to use changeTitles(MatrixTitle t) instead");
        }
    }

    public void print(){
        for(int i=0;i<_lineSize;i++){
            for(int j=0;j<_columnSize;j++){
                System.out.println("cell(" + i + ", " + j + ")" + "=" + _values[i][j]);
            }
        }
    }


}
