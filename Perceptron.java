import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;



public class Perceptron implements java.io.Serializable
{
    private Matrix[] weights;
    private double[][] bias;
    private double min = -1;
    private double max = 1;
    private int epochs;
    private int batchSize;
    private double eta;
    private OneDoubleArgOperator lambdaActivator = (x) -> 1.0/(1.0 + Math.exp(-x));


    public Perceptron(int[] layers)
    {
        weights = new Matrix[layers.length - 1];
        bias = new double[layers.length - 1][];
        
        for(int n = 0; n < layers.length - 1; n++)
        {
            weights[n] = Matrix.zufallGauss(layers[n+1], layers[n], 0, 1);
            bias[n] = new double[layers[n+1]];
            
            for(int k = 0; k< layers[n+1]; k++)
            {
                Random r = new Random();
                bias[n][k] = r.nextGaussian();
            }
        }
    }
    
    private double activationFunction(double x)
    {
        return 1.0/(1.0 + Math.exp(-x));
    }
    
    private double activationDerivative(double x)
    {
        return x*(1.0 - x);
    }
    
    //n ist schicht, aus der die Aktivierungen kommen -> Bei 0 wird die hidden layer nach input aktiviert
    double[] activivateVector(double[] gewichtSummen, int schicht) throws Exception
    {
        if(gewichtSummen.length != bias[schicht].length)
        {
            throw new Exception("Nicht gleich viele Biase und Summen...");
        }
        
        double[] returnVektor = new double[gewichtSummen.length];
        
        for(int n = 0; n < gewichtSummen.length; n++)
        {
            returnVektor[n] = activationFunction(gewichtSummen[n] + bias[schicht][n]);
        }
        
        return returnVektor;
    }


    public double[] predictProbability(double[] x) throws Exception
    {
        for(int n = 0; n < weights.length; n++)
        {
            x = activivateVector(weights[n].vektorMul(x), n);
        }
        
        return x;
    }
    
    protected double[][] activations(double[] x) throws Exception
    {
        double[][] akt = new double[weights.length + 1][];
        akt[0] = x;
        
        for(int n = 0; n < weights.length; n++)
        {
            akt[n + 1] = activivateVector(weights[n].vektorMul(akt[n]), n);      //Davor x anstatt akt[n]
        }
        
        return akt;
    }
    
    public void setParameters(int epochs, int batchSize, double eta)
    {
        this.epochs = epochs;
        this.batchSize = batchSize;
        this.eta = eta;
    }
    
    public void train(double[][] x, double[][] y) throws Exception
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
        
        int ticks = epochs * ((int) Math.ceil(((double)x.length)/batchSize));
            
        for(int index = 0; index < x.length; index++)
        {
            indexArray.add(index);
        }
        
        ProgressBar pgbar = new ProgressBar(ticks, 50);
        
        for(int e = 1; e < epochs + 1; e++)
        {
            if(e != 1)
            {
                System.out.print("\033[A");             //Go to beginning of previous line
            }
            
            System.out.println("Epoch " + e + "/" + epochs);
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
    
    private void updateBatch(double[][] batchEin, double[][] batchAus, double eta) throws Exception
    {
        Matrix[] nablaGewichte = new Matrix[weights.length];
        double[][] nablaBias = new double[bias.length][];
        
        for(int n = 0; n < weights.length; n++)
        {
            nablaGewichte[n] = new Matrix(weights[n].getDimensions()[0], weights[n].getDimensions()[1]);
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
        
        for(int n = 0; n < weights.length; n++)
        {
            weights[n].matrixSubInPlace(nablaGewichte[n].skalarMul(eta/batchEin.length));
            for(int k = 0; k < bias[n].length; k++)
            {
                bias[n][k] -= nablaBias[n][k]*eta/batchEin.length;
            }
        }
    }
    
    private BackpropNabla backprop(double[] ein, double[] aus) throws Exception
    {
        Matrix[] nablaGewichte = new Matrix[weights.length];
        double[][] nablaBias = new double[bias.length][];
        
        for(int n = 0; n < weights.length; n++)
        {
            nablaGewichte[n] = new Matrix(weights[n].getDimensions()[0], weights[n].getDimensions()[1]);
            nablaBias[n] = new double[bias[n].length];
        }
        
        double[][] akt = activations(ein);
        
        double[] delta = new double[aus.length];
        
        for(int n = 0; n < delta.length; n++)
        {
            delta[n] = (akt[akt.length - 1][n] - aus[n]) * (activationDerivative(akt[akt.length -1][n]));
        }
        
        
        nablaBias[bias.length - 1] = delta;
        nablaGewichte[weights.length - 1] = Matrix.matMul(Matrix.arrayToColumn(delta), Matrix.arrayToRow(akt[akt.length - 2]));
        
        for(int l = 2; l < weights.length + 1; l++)
        {
            double[] sp = Utils.applyToArr(akt[weights.length - l +1], lambdaActivator);
            delta = weights[weights.length - l + 1].transpose().vektorMul(delta);
            
            for(int i = 0; i < sp.length; i++)
            {
                delta[i] *= sp[i];
            }
            
            nablaBias[nablaBias.length - l] = delta;
            nablaGewichte[weights.length -l] = Matrix.matMul(Matrix.arrayToColumn(delta), Matrix.arrayToRow(akt[akt.length -l -1]));        //Nicht sicher bei .transpose()
        }
        
        return new BackpropNabla(nablaGewichte, nablaBias);
    }
    
    public int[] getLayers()
    {
        int[] layers = new int[weights.length + 1];
        
        for(int n = 0; n < weights.length; n++)
        {
            layers[n+1] = bias[n].length;
        }
        
        layers[0] = weights[0].getDimensions()[1];
        return layers;
    }
    
    public Matrix[] getWeights()
    {
        return weights;
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
