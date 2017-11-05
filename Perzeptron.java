import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

/**
 * Mehrschichtiger Perzeptron
 * 
 * @Markus Krimmel
 * @1.0
 */
public class Perzeptron implements java.io.Serializable
{
    private Matrix[] gewichte;
    private double[][] bias;
    private double min = -1;
    private double max = 1;
    private OneDoubleArgOperator lambdaActivator = (x) -> 1.0/(1.0 + Math.exp(-x));

    /**
     * Konstruktor für Objekte der Klasse Perzeptron
     */
    public Perzeptron(int[] schichten)
    {
        gewichte = new Matrix[schichten.length - 1];
        bias = new double[schichten.length - 1][];
        
        for(int n = 0; n < schichten.length - 1; n++)
        {
            gewichte[n] = Matrix.zufallGauss(schichten[n+1], schichten[n], 0, 1);
            bias[n] = new double[schichten[n+1]];
            
            for(int k = 0; k<schichten[n+1]; k++)
            {
                Random r = new Random();
                bias[n][k] = r.nextGaussian();
            }
        }
    }
    
    public double aktivierungsFkt(double x)
    {
        return 1.0/(1.0 + Math.exp(-x));
    }
    
    public double aktivierungsDeriv(double x)
    {
        return x*(1.0 - x);
    }
    
    //n ist schicht, aus der die Aktivierungen kommen -> Bei 0 wird die hidden layer nach input aktiviert
    public double[] aktiviereVektor(double[] gewichtSummen, int schicht) throws Exception
    {
        if(gewichtSummen.length != bias[schicht].length)
        {
            throw new Exception("Nicht gleich viele Biase und Summen...");
        }
        
        double[] returnVektor = new double[gewichtSummen.length];
        
        for(int n = 0; n < gewichtSummen.length; n++)
        {
            returnVektor[n] = aktivierungsFkt(gewichtSummen[n] + bias[schicht][n]);
        }
        
        return returnVektor;
    }


    public double[] vorhersageWahrscheinlichkeit(double[] x) throws Exception
    {
        for(int n = 0; n < gewichte.length; n++)
        {
            x = aktiviereVektor(gewichte[n].vektorMul(x), n);
        }
        
        return x;
    }
    
    public double[][] aktivierungen(double[] x) throws Exception
    {
        double[][] akt = new double[gewichte.length + 1][];
        akt[0] = x;
        
        for(int n = 0; n < gewichte.length; n++)
        {
            akt[n + 1] = aktiviereVektor(gewichte[n].vektorMul(akt[n]), n);      //Davor x anstatt akt[n]
        }
        
        return akt;
    }
    
    public void lernen(double[][] x, double[][] y, int epochen, int batchSize, double eta) throws Exception
    {
        if(x.length != y.length)
        {
            throw new Exception("Dimensionen inkonsistent...");
        }
        
        double[][] ein = new double[x.length][];
        double[][] aus = new double[y.length][];
        double[][] batchEin;
        double[][] batchAus;
        
        List<Integer> indexArray = new ArrayList();
        
        int ticks = epochen * ((int) Math.ceil(((double)x.length)/batchSize));
            
        for(int index = 0; index < x.length; index++)
        {
            indexArray.add(index);
        }
        
        ProgressBar pgbar = new ProgressBar(ticks, 50);
        
        for(int e = 1; e < epochen + 1; e++)
        {
            if(e != 1)
            {
                System.out.print("\033[A");             //Go to beginning of previous line
            }
            
            System.out.println("Epoch " + e + "/" + epochen);
            pgbar.print();
            Collections.shuffle(indexArray);
            
            for(int index = 0; index < x.length; index++)
            {
                ein[index] = x[indexArray.get(index)];
                aus[index] = y[indexArray.get(index)];
            }
            
            
            for(int b = 0; b < ein.length; b += batchSize)
            {
               if(b + batchSize > ein.length)
               {
                   batchEin = Arrays.copyOfRange(ein, b, ein.length);
                   batchAus = Arrays.copyOfRange(aus, b, ein.length);
                }
                else
                {
                   batchEin = Arrays.copyOfRange(ein, b, b+batchSize);
                   batchAus = Arrays.copyOfRange(aus, b, b+batchSize);
                }
                
               updateBatch(batchEin, batchAus, eta);
               pgbar.tick();
            }
        }
    }
    
    public void updateBatch(double[][] batchEin, double[][] batchAus, double eta) throws Exception
    {
        Matrix[] nablaGewichte = new Matrix[gewichte.length];
        double[][] nablaBias = new double[bias.length][];
        
        for(int n = 0; n < gewichte.length; n++)
        {
            nablaGewichte[n] = new Matrix(gewichte[n].dimensionen()[0], gewichte[n].dimensionen()[1]);
            nablaBias[n] = new double[bias[n].length];
        }
        
        for(int index = 0; index < batchEin.length; index++)
        {
            BackpropNabla result = backprop(batchEin[index], batchAus[index]);
            Matrix[] deltaNablaGewichte = result.getGewichte();
            double[][] deltaNablaBias = result.getBias();
            
            for(int n = 0; n < deltaNablaGewichte.length; n++)
            {
                nablaGewichte[n].matrixAddInPlace(deltaNablaGewichte[n]);
                
                for(int i = 0; i < deltaNablaBias[n].length; i++)
                {
                    nablaBias[n][i] += deltaNablaBias[n][i];
                }
            }
        }
        
        for(int n = 0; n < gewichte.length; n++)
        {
            gewichte[n].matrixSubInPlace(nablaGewichte[n].skalarMul(eta/batchEin.length));
            for(int k = 0; k < bias[n].length; k++)
            {
                bias[n][k] -= nablaBias[n][k]*eta/batchEin.length;
            }
        }
    }
    
    public BackpropNabla backprop(double[] ein, double[] aus) throws Exception
    {
        Matrix[] nablaGewichte = new Matrix[gewichte.length];
        double[][] nablaBias = new double[bias.length][];
        
        for(int n = 0; n < gewichte.length; n++)
        {
            nablaGewichte[n] = new Matrix(gewichte[n].dimensionen()[0], gewichte[n].dimensionen()[1]);
            nablaBias[n] = new double[bias[n].length];
        }
        
        double[][] akt = aktivierungen(ein);
        
        double[] delta = new double[aus.length];
        
        for(int n = 0; n < delta.length; n++)
        {
            delta[n] = (akt[akt.length - 1][n] - aus[n]) * (aktivierungsDeriv(akt[akt.length -1][n]));
        }
        
        
        nablaBias[bias.length - 1] = delta;
        nablaGewichte[gewichte.length - 1] = Matrix.matMul(Matrix.arrayToColumn(delta), Matrix.arrayToRow(akt[akt.length - 2]));
        
        for(int l = 2; l < gewichte.length + 1; l++)
        {
            double[] sp = Utils.applyToArr(akt[gewichte.length - l +1], lambdaActivator);
            delta = gewichte[gewichte.length - l + 1].transponiert().vektorMul(delta);
            
            for(int i = 0; i < sp.length; i++)
            {
                delta[i] *= sp[i];
            }
            
            nablaBias[nablaBias.length - l] = delta;
            nablaGewichte[gewichte.length -l] = Matrix.matMul(Matrix.arrayToColumn(delta), Matrix.arrayToRow(akt[akt.length -l -1]));        //Nicht sicher bei .transponiert()
        }
        
        return new BackpropNabla(nablaGewichte, nablaBias);
    }
    
    public int[] getLayers()
    {
        int[] schichten = new int[gewichte.length + 1];
        
        for(int n = 0; n < gewichte.length; n++)
        {
            schichten[n+1] = bias[n].length;
        }
        
        schichten[0] = gewichte[0].dimensionen()[1];
        return schichten;
    }
    
    public Matrix[] getWeights()
    {
        return gewichte;
    }
    
    public String toString()
    {   
        return getDescription() + " of Shape: "+Arrays.toString(getLayers());
    }
    
    public String getDescription()
    {
        return "Simple Multi Layer Perceptron";
    }
}
