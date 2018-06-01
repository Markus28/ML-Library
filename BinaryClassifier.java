import java.util.List;

/**
 * Interface BinaryClassifier
 * 
 * @Markus K
 * @1.0
 */

public interface BinaryClassifier
{
    void train(double[][] x, boolean[] y) throws Exception;
    boolean predict(double[] x) throws Exception;
}
