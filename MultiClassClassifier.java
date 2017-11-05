import java.util.List;

/**
 * Tragen Sie hier eine Beschreibung des Interface MultiClassClassifier ein.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public interface MultiClassClassifier
{
    void lernen(double[][] x, List y, List parameters) throws Exception;
    Object vorhersage(double[] x) throws Exception;
}
