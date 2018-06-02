package machinelearning;

import java.lang.reflect.Array;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.ArrayList;


public class KMeans
{
    private boolean seeded = false;
    private double[][] centroids;
    private int k;
    private int dim;
    
    public KMeans(int k)
    {
        centroids = new double[k][];
        this.k = k;
    }
    
    public int getK()
    {
        return k;
    }
    
    protected double[][] seed(double[][] x) throws Exception
    {
        ArrayList<Integer> used = new ArrayList<>();
        double[][] c = new double[k][];
        
        for(int n = 0; n < k; n++)
        {
            int index = ThreadLocalRandom.current().nextInt(0, x.length);
            if(! used.contains(index))
            {
                c[n] = x[index];
                used.add(index);
            }
        }
        
        return c;
    }

    public void train(double[][] x) throws Exception
    {
        double[][] oldCentroids = new double[centroids.length][];
        int[] predicted;
        int[] centroidCount;
        
        if(x.length < k && !seeded)
        {
            throw new Exception("More training data required...");
        }
        
        if(!seeded)
        {
            centroids = seed(x);
            dim = x[0].length;
        }
        
        while(! Arrays.deepEquals(oldCentroids, centroids))
        {
            oldCentroids = centroids.clone();
            predicted = predictBatch(x);
            centroidCount = new int[k];
            
            for(int n = 0; n < k; n++)
            {
                centroids[n] = new double[dim];
            }
            
            for(int n = 0; n < predicted.length; n++)
            {
                centroids[predicted[n]] = Utils.addArrays(centroids[predicted[n]], x[n]);
                centroidCount[predicted[n]]++;
            }
            
            for(int n = 0; n < k; n++)
            {
                for(int m = 0; m < centroids[n].length; m++)
                {
                    centroids[n][m] /= centroidCount[n];
                }
            }
        }
    }
    
    public int predict(double[] x) throws Exception
    {
        double minDistance = Utils.distance(centroids[0], x);
        int minIndex = 0;

        
        for(int n = 1; n < k; n++)
        {
            if(Utils.distance(centroids[n], x) < minDistance)
            {
                minIndex = n;
                minDistance = Utils.distance(centroids[n], x);
            }
        }
        
        return minIndex;
    }
    
    public int[] predictBatch(double[][] x) throws Exception
    {
        int[] result = new int[x.length];
        
        for(int n = 0; n < x.length; n++)
        {
            result[n] = predict(x[n]);
        }
        
        return result;
    }

    public double[][] getCentroids() {
        return centroids;
    }
}
