package model;

import java.util.HashMap;
import java.util.Map;

/**
 * <b><i>MatrixTitle</i> est la classe contenant l'ensemble des titres de lignes et de colonnes d'un objet Matrix</b>
 *
 * @see Matrix
 * @author lucast
 */
public class MatrixTitle {
    HashMap<String, Integer> _linesTitles;
    HashMap<String, Integer> _columnsTitles;

    /**
     * Constructeur de la classe <b>MatrixTitle</b>
     * @param linesTitles <b>Ensemble des titres de lignes</b>
     * @param columnsTitles <b>Ensemble des titres de colonnes</b>
     */
    public MatrixTitle(HashMap<String, Integer> linesTitles, HashMap<String, Integer> columnsTitles){
        _linesTitles = linesTitles;
        _columnsTitles = columnsTitles;
    }

    /**
     * Fonction permettant de <b>copier une partie de la matrice de titres</b>
     * @param lineStart <b>Ligne de départ</b>
     * @param columnStart <b>Colonne de départ</b>
     * @param lineEnd <b>Ligne de fin</b>
     * @param columnEnd <b>Colonne de fin</b>
     * @return <b>Matrice de titres</b> obtenue
     */
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

    /**
     * Accesseur <b>getteur</b> de la <b>taille des lignes</b>
     * @return <b>Taille des lignes de la matrice de titres</b>
     */
    public int getLinesSize(){
        return _linesTitles.size();
    }

    /**
     * Accesseur <b>getteur</b> de la <b>taille des colonnes</b>
     * @return <b>Taille des colonnes de la matrice de titres</b>
     */
    public int getColumnsSize(){
        return _columnsTitles.size();
    }

    /**
     * Accesseur <b>getteur</b> d'une <b>ligne</b> de la <b>matrice de titres</b>
     * @param line <b>Nom de la ligne</b> à récupérer
     * @return <b>Ligne de la matrice de titres demandée</b>
     */
    public int getLine(String line) {
        return _linesTitles.get(line);
    }

    /**
     * Accesseur <b>getteur</b> d'une <b>colonne</b> de la <b>matrice de titres</b>
     * @param column <b>Nom de la colonne</b> à récupérer
     * @return <b>Colonne de la matrice de titres demandée</b>
     */
    public int getColumn(String column) {
        return _columnsTitles.get(column);
    }

    /**
     * Accesseur <b>getteur</b> du <b>titre d'une colonne de la matrice de titres donnée</b>
     * @param columnIndex <b>Index de la colonne demandée</b>
     * @return <b>Titre de la colonne demandée</b>
     * @throws IndexOutOfBoundsException Lancée si l'index est <b>en dehors des bornes de la matrice de titres</b>
     */
    public String getColumnTitle(Integer columnIndex) throws IndexOutOfBoundsException{
        String result = null;
        for(Map.Entry<String,Integer> e : _columnsTitles.entrySet()){
            String title = e.getKey();
            Integer index = e.getValue();
            
            if(index == columnIndex)
                result = title;
        }
        if(result == null){
            throw new IndexOutOfBoundsException("La matrice ne contient pas de titre correspondant à l'index donné en paramètre");
        }
        else{
            return result;
        }
    }
}
