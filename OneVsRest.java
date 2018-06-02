import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OneVsRest<T> implements  MultiClassClassifier<T>{
    private StringBasedFactory<BinaryClassifier> modelFactory;
    private String modelName;
    private T first;
    private HashMap<T, BinaryClassifier> hm;

    public OneVsRest(StringBasedFactory<BinaryClassifier> modelFactory, String modelName) throws UnsupportedOperationException
    {
        this.modelFactory = modelFactory;

        if(modelFactory.get(modelName) == null)
        {
            throw new UnsupportedOperationException("This model is not supported...");
        }

        this.modelName = modelName;
    }

    public void train(double[][] x, ArrayList<T> y) throws Exception
    {
        if(x.length != y.size())
        {
            throw new InconsistentDimensionsException("Features and labels must have same length");
        }

        first = y.get(0);

        hm = new HashMap<>();

        for(T output: y)
        {
            hm.put(output, modelFactory.get(modelName));
        }

        boolean[] one_y = new boolean[x.length];

        for(Map.Entry<T, BinaryClassifier> entry: hm.entrySet())
        {
            for(int n = 0; n < x.length; n++)
            {
                one_y[n] = entry.getKey().equals(y.get(n));
            }

            entry.getValue().train(x, one_y);
        }
    }

    public T predict(double[] x) throws Exception
    {
        T maxProbElement = first;
        double maxProb = hm.get(first).getTrueProbability(x);

        for(Map.Entry<T, BinaryClassifier> entry: hm.entrySet())
        {
            if(entry.getValue().getTrueProbability(x) > maxProb)
            {
                maxProbElement = entry.getKey();
                maxProb = entry.getValue().getTrueProbability(x);
            }
        }

        return maxProbElement;
    }
}
