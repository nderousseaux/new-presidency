package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.HashMap;

/**
 * <b><i>Matrix</i> est la classe contenant les matrices servant aux calculs de valeurs du jeu</b>
 *
 * @author lucast
 */
public class Matrix{
    private Integer _lineSize;
    private Integer _columnSize;
    private Double _values[][];

    private MatrixTitle _titles = null;


    /**
     * Constructeur de la classe <b>Matrix</b>
     * @param lineSize Taille des <b>lignes de la matrice</b>
     * @param columnSize Taille des <b>colonnes de la matrice</b>
     */
    public Matrix(Integer lineSize, Integer columnSize) {
        _lineSize = lineSize;
        _columnSize = columnSize;

        _values = new Double[lineSize][columnSize];
        setAllValuesTo0();
    }

    /**
     * Accesseur <b>getteur</b> de la taille des <b>lignes</b> de la matrice
     * @return Taille des <b>lignes</b> de la matrice
     */
    public Integer getLineSize() {
        return _lineSize;
    }

    /**
     * Accesseur <b>getteur</b> de la taille des <b>colonnes</b> de la matrice
     * @return Taille des <b>colonnes</b> de la matrice
     */
    public Integer getColumnSize() {
        return _columnSize;
    }

    /**
     * Accesseur <b>getteur</b> des <b>valeurs</b> contenues dans la matrice
     * @return <b>Valeurs</b> contenues dans la matrice
     */
    public Double[][] getValues() {
        return _values;
    }

    /**
     * Accesseur <b>setteur</b> des <b>valeurs</b> contenues dans la matrice
     * @param values <b>Valeurs</b> à insérer dans la matrice
     */
    protected void setValues(Double[][] values){
        _values = values;
    }

    /**
     * Accesseur <b>getteur</b> d'une valeur d'une <b>case donnée</b>
     * @param line <b>Ligne</b> de la case
     * @param column <b>Colonne</b> de la case
     * @return <b>Valeur</b> de la case
     */
    public Double getCell(int line, int column){
        return _values[line][column];
    }

    /**
     * Accesseur <b>getteur</b> d'une valeur d'une <b>case donnée</b>
     * @param line <b>Ligne</b> de la case
     * @param column <b>Colonne</b> de la case
     * @return <b>Valeur</b> de la case
     */
    public Double getCell(String line, String column){
        Double result;
        int lineIndex = _titles.getLine(line);
        int columnIndex = _titles.getColumn(column);
        result = getCell(lineIndex, columnIndex);
        return result;
    }

    /**
     * Accesseur <b>setteur</b> d'une valeur d'une <b>case donnée</b>
     * @param line <b>Ligne</b> de la case
     * @param column <b>Colonne</b> de la case
     */
    public void setCell(int line, int column, Double value){
        _values[line][column] = value;
    }

    /**
     * Fonction permettant d'initialiser la <b>totalité</b> des cases de la matrice à 0
     */
    public void setAllValuesTo0(){
        for(int i=0; i < _lineSize; i++){
            for(int j=0; j < _columnSize; j++){
                _values[i][j] = 0.0;
            }
        }
    }

    /**
     * Fonction permettant d'<b>additionner</b> à une matrice donnée une autre matrice
     * @param matrix <b>Matrice</b> à additionner
     * @return <b>Matrice résultat de l'addition</b>
     * @throws Error Lancé si les matrices ne sont pas de la même <b>taille</b>
     */
    public Matrix add(Matrix matrix) throws Error{
        if(_lineSize != matrix.getLineSize() && _columnSize != matrix.getColumnSize()){
            throw new Error("Two matrices must have an equal number of rows and columns to be added");
        }
        Matrix result = new Matrix(_lineSize, _columnSize);
        for(int i=0; i < _lineSize; i++){
            for(int j=0; j < _columnSize; j++){
                result.setCell(i, j, _values[i][j] + matrix.getCell(i, j));
            }
        }
        return result;
    }

    /**
     * Fonction permettant de <b>multiplier</b> à une matrice donnée une autre matrice
     * @param matrix <b>Matrice</b> à multiplier
     * @return <b>Matrice résultat de la multiplication</b>
     * @throws Error Lancé si les matrices ne sont pas <b>multipliables</b>
     */
    public Matrix times(Matrix matrix) throws Error{
        if(_columnSize != matrix.getLineSize()){
            throw new Error("The column size and the line size of respectively the first and respectively the second matrix are not equals");
        }
        Integer lineSize = _lineSize;
        Integer columnSize = matrix.getColumnSize();

        Matrix result = new Matrix(lineSize, columnSize);

        Double value = 0.0;
        int k = 0;

        for(int i=0; i < lineSize; i++){
            for(int j=0; j < columnSize; j++){
                while(k < _columnSize){
                    value+= _values[i][k] * matrix.getCell(k, j);
                    k++;
                }
                k = 0;
                result.setCell(i, j, value);
                value = 0.0;
            }
        }

        return result;
    }

    /**
     * Fonction permettant de <b>copier une partie de la matrice</b>
     * @param lineStart <b>Ligne de départ</b>
     * @param columnStart <b>Colonne de départ</b>
     * @param lineEnd <b>Ligne de fin</b>
     * @param columnEnd <b>Colonne de fin</b>
     * @return <b>Matrice copiée</b>
     */
    public Matrix copy(int lineStart, int columnStart, int lineEnd, int columnEnd) {
        if(lineStart<0 || columnStart<0 || lineEnd>_lineSize || columnEnd>_columnSize){
            throw new Error("the given indexes are out of the matrix bound");
        }
        int nbLineCopy = lineEnd - lineStart;
        int nbColumnCopy = columnEnd - columnStart;

        Matrix result = new Matrix(nbLineCopy, nbColumnCopy);

        for(int i=0; i<nbLineCopy; i++){
            for(int j=0; j<nbColumnCopy; j++){
                result.setCell(i, j, getCell(i+lineStart, j+columnStart));
                //System.out.println("copy cell(" +i+ ", " +j+ ") : " + result.getCell(i, j));
            }
        }

        if(_titles != null){
            MatrixTitle resultTitles = _titles.copy(lineStart, columnStart, lineEnd, columnEnd);
            result.createTitles(resultTitles);
        }

        return result;
    }

    /**
     * Fonction permettant de <b>changer les titres de la matrice</b>
     * @param title Ensemble des <b>titres à insérer</b>
     * @throws Error Lancé si le <b>nombre de titres avant et après</b> ne correspondent pas
     */
    public void changeTitles(MatrixTitle title) throws Error{
        if(title.getLinesSize()  == _titles.getLinesSize() && title.getColumnsSize() == _titles.getColumnsSize())
            this._titles = title;
        else{
            throw new Error("You can't change the size of a matrix, you should create a new one");
        }
    }

    /**
     * Fonction permettant d'<b>insérer les titres de la matrice</b>
     * @param title Ensemble des <b>titres à insérer</b>
     * @throws Error Lancé si le <b>nombre de titres</b> est incorrect
     */
    public void createTitles(MatrixTitle title) throws Error{
        if(_titles == null){
            if(title.getLinesSize()== _lineSize && title.getColumnsSize() == _columnSize){
                _titles = title;
            }
            else{
                throw new Error("The size of the titles don't match the size of the matrix");
            }
        }
        else{
            throw new Error("The Titles are already created, try to use changeTitles(MatrixTitle t) instead");
        }
    }

    /**
     * Accesseur <b>getteur</b> permettant de récupérer une <b>ligne de la matrice</b>
     * @param line <b>Nom de la ligne</b> à récupérer
     * @return <b>Ligne demandée</b>
     */
    public int getLine(String line) {
        return _titles.getLine(line);
    }

    /**
     * Accesseur <b>getteur</b> permettant de récupérer une <b>colonne de la matrice</b>
     * @param column <b>Nom de la colonne</b> à récupérer
     * @return <b>Colonne demandée</b>
     */
    public int getColumn(String column) {
        return _titles.getColumn(column);
    }

    /**
     * Accesseur <b>getteur</b> permettant de récupérer le <b>titre d'une colonne donnée</b>
     * @param columnIndex <b>Indice de la colonne</b> demandée
     * @return <b>Nom de la colonne demandée</b>
     * @throws IndexOutOfBoundsException Lancé si l'<b>index demandé est en dehors des bornes</b>
     */
    public String getColumnTitle(Integer columnIndex) throws IndexOutOfBoundsException{
        String result = _titles.getColumnTitle(columnIndex);
        return result;
    }

}
