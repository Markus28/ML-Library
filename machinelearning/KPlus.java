package machinelearning;

import java.util.concurrent.ThreadLocalRandom;


public class KPlus extends KMeans
{
    public KPlus(int k)
    {
        super(k);
    }
    
    @Override
    protected double[][] seed(double[][] x) throws Exception
    {
        int index = ThreadLocalRandom.current().nextInt(0, x.length);
        double[][] c = new double[getK()][];
        c[0] = x[index];
        double[] p = new double[x.length];
        double minVal;
        int minIndex;
        
        for(int n = 1; n < getK(); n++)
        {
           for(int m = 0; m < x.length; m++)
           {
               minVal = Utils.distance(x[m], c[0]);
               minIndex = 0;
               
               for(int r = 0; r < n; r++)
               {
                   if(Utils.distance(x[m], c[r]) < minVal)
                   {
                       minVal = Utils.distance(x[m], c[r]);
                       minIndex = r;
                    }
                }
               
               p[m] = Math.pow(Utils.distance(c[minIndex],x[m]), 2);
           }
           
           c[n] = x[Utils.indexRandom(p)];              //TODO throws java.lang.IllegalArgumentException: bound must be greater than origin
        }
        
        return c;
    }
}
