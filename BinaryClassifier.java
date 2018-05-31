import java.util.List;

/**
 * Interface BinaryClassifier
 * 
 * @Markus K
 * @1.0
 */

public interface BinaryClassifier
{
    void lernen(double[][] x, boolean[] y) throws Exception;
    boolean vorhersage(double[] x) throws Exception;
}
