import java.util.List;

/**
 * Interface BinaryClassifier
 * 
 * @Markus K
 * @1.0
 */

public interface BinaryClassifier
{
    void lernen(double[][] x, boolean[] y, List parameters) throws Exception;
    boolean vorhersage(double[] x) throws Exception;
    void setThreshold(double x) throws Exception;
    double[] getThresholdBounds();
}
