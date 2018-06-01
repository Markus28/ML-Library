import java.util.List;
import java.util.ArrayList;

/**
 * Tragen Sie hier eine Beschreibung des Interface MultiClassClassifier ein.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public interface MultiClassClassifier<T>
{
    void train(double[][] x, ArrayList<T> y) throws Exception;
    T predict(double[] x) throws Exception;
}
