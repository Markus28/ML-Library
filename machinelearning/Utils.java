package machinelearning;

import machinelearning.OneDoubleArgOperator;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Utils
{
    private static OneDoubleArgOperator negate = (x) -> -1*x;

    public static <T> T[] addAll(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static boolean[] addAll(boolean[] a, boolean[] b)
    {
        boolean[] result = new boolean[a.length + b.length];
        
        for(int n = 0; n < a.length; n++)
        {
            result[n] = a[n];
        }
        
        for(int n = 0; n < b.length; n++)
        {
            result[n+a.length] = b[n];
        }
        
        return result;
    }
    
    public static double[][] addAll(double[][] a, double[][] b)
    {
        double[][] result = new double[a.length + b.length][];
        
        for(int n = 0; n < a.length; n++)
        {
            result[n] = a[n];
        }
        
        for(int n = 0; n < b.length; n++)
        {
            result[n+a.length] = b[n];
        }
        
        return result;
    }
    
    public static double distance(double[] a, double[] b) throws Exception
    {
        return pNorm(subArrays(a, b), 2);
    }
    
    public static double pNorm(double[] x, int p) throws Exception
    {
        double result = 0;
        
        if(p < 1)
        {
            throw new Exception("p must be greater than 1...");
        }
        
        for(int n = 0; n < x.length; n++)
        {
            result += Math.pow(Math.abs(x[n]), p);
        }
        
        return Math.pow(result, 1.0/p);
    }
    
    public static double[] applyToArr(double[] arr, OneDoubleArgOperator method)
    {
        double[] result = new double[arr.length];
        
        for(int n = 0; n < arr.length; n++)
        {
            result[n] = method.operate(arr[n]);
        }
        
        return result;
    }
    
    public static double[] subArrays(double[] a, double[] b) throws Exception
    {
        return addArrays(a, applyToArr(b, negate));
    }
    
    public static double[] addArrays(double[] a, double[] b) throws Exception
    {
        double[] result = new double[a.length];
        if(a.length != b.length)
        {
            throw new Exception("Inkonsistente Dimensionen...");
        }
        
        for(int n = 0; n < a.length; n++)
        {
            result[n] = a[n] + b[n];
        }
        
        return result;
    }
    
    public static double[][] intToDoubleArr(int[][] arr)
    {
        double[][] result = new double[arr.length][];
        
        for(int n = 0; n < arr.length; n++)
        {
            result[n] = new double[arr[n].length];
            
            for(int k = 0; k < arr[n].length; k++)
            {
                result[n][k] = (double) arr[n][k];
            }
        }
        
        return result;
    }
    
    public static int[] maxProb(double[] x)
    {
        double maxVal = x[0];
        int maxIndex = 0;
        int[] result = new int[x.length];
        for(int n = 0; n < x.length; n++)
        {
            if(x[n] > maxVal)
            {
                maxIndex = n;
                maxVal = x[n];
            }
        }
        
        result[maxIndex] = 1;
        return result;
    }
    
    public static int indexRandom(double[] distribution) throws Exception
    {
        double[] dist = distribution.clone();
        
        for(int n = 1; n < dist.length; n++)
        {
            if(dist[n] < 0 || dist[n-1] < 0)
            {
                throw new Exception("Negative values in dist not allowed...");
            }
            
            dist[n] += dist[n-1];
        }
        
        double val = ThreadLocalRandom.current().nextInt(0, (int)((dist[dist.length-1]*10000.0 + 1.0)/10000.0));
        
        for(int n = 0; n < dist.length; n++)
        {
            if(dist[n] > val)
            {
                return n;
            }
        }
        
        throw new Exception("Exception...");
    }
    
    /*
    public static void visualizeNet(Visualizable net)
    {
        int max = 0;
        int[] dimensions = net.getLayers();
        machinelearning.Matrix[] weights = net.getWeights();
        int[][][] neuronCoordinates;
        int neuronCount = 0;
        
        for(int n = 0; n < dimensions.length; n++)
        {
            if(dimensions[n] > max)
            {
                max = dimensions[n];
            }
        }
        
        neuronCoordinates = new int[dimensions.length][][];
        int distanceY = 400/(max + 1);
        int distanceX = 650/(dimensions.length + 1);
        
        for(int n = 0; n < dimensions.length; n++)
        {
            for(int k = 0; k < dimensions[n]; k++)
            {
                if(dimensions[n] % 2 == 1)
                {
                    int fromCenter = (k - (dimensions[n]-1)/2)*distanceY;
                    int[] coordinates = {(n+1)*distanceX, 200+fromCenter};
                    neuronCoordinates[n][k] = coordinates;
                }
                else
                {
                    //TODO
                }
            }
        }
    }
    */
}
