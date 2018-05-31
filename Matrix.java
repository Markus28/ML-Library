import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Matrix
 * 
 * @Markus K 
 * @1.0
 */
public class Matrix implements java.io.Serializable
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private double[][] matrix;
    int zeilen;
    int spalten;

    /**
     * Konstruktor für Objekte der Klasse Matrix
     */
    public Matrix(double[][] matrix) throws Exception
    {
        zeilen = matrix.length;
        spalten = matrix[0].length;
        for(int i = 1; i < matrix.length; i++)
        {
            if(matrix[i].length != spalten)
            {
                throw new Exception("Dimensionen inkonsistent...");
            }
        }
        this.matrix = matrix;
    }
    
    public Matrix(int m, int n)
    {
        zeilen = m;
        spalten = n;
        matrix = new double[m][n];
    }
    
    public static Matrix eye(int m)
    {
        Matrix returnMatrix = new Matrix(m, m);
        
        for(int index = 0; index < m; index++)
        {
            returnMatrix.setzeWert(index, index, 1);
        }
        
        return returnMatrix;
    }
    
    public static Matrix arrayToRow(double[] arr) throws Exception
    {
        double[][] mat = new double[1][];
        mat[0] = arr;
        return new Matrix(mat);
    }
    
    public static Matrix arrayToColumn(double[] arr) throws Exception
    {
        return Matrix.arrayToRow(arr).transponiert();
    }
    
    public static Matrix zufall(int m, int n, double min, double max)
    {
        Matrix returnMatrix = new Matrix(m, n);
        
        for(int k = 0; k < m; k++)
        {
            for(int i = 0; i < n; i++)
            {
                returnMatrix.setzeWert(k, i, ThreadLocalRandom.current().nextInt((int)(min*10000.0), (int)(max*10000.0 + 1.0))/10000.0);
            }
        }
        
        return returnMatrix;
    }
    
    public static Matrix zufallGauss(int m, int n, double mean, double var)
    {
        Matrix returnMatrix = new Matrix(m, n);
        Random r = new Random();
        
        for(int k = 0; k < m; k++)
        {
            for(int i = 0; i < n; i++)
            {
                returnMatrix.setzeWert(k, i, r.nextGaussian()*var + mean);
            }
        }
        
        return returnMatrix;
    }
    
    public double wert(int m, int n)
    {
        return matrix[m][n];
    }
    
    public void setzeWert(int m, int n, double value)
    {
        matrix[m][n] = value;
    }
    
    public double eintrag(int m, int n)
    {
        return matrix[m][n];
    }
    
    public Matrix transponiert()
    {
        Matrix returnMatrix = new Matrix(spalten, zeilen);
        
        for(int k = 0; k < zeilen; k++)
        {
            for(int i = 0; i < spalten; i++)
            {
                returnMatrix.setzeWert(i, k, matrix[k][i]);
            }
        }
        
        return returnMatrix;
    }
    
    public static Matrix matMul(Matrix a, Matrix b) throws Exception
    {
        Matrix result = new Matrix(a.dimensionen()[0], b.dimensionen()[1]);
        double[] vektor = new double[b.dimensionen()[0]];
        for(int n = 0; n < b.dimensionen()[1]; n++)
        {
            for(int k = 0; k < b.dimensionen()[0]; k++)
            {
                vektor[k] = b.wert(k, n);
            }
            
            double[] resultVektor = a.vektorMul(vektor);
            
            for(int i = 0; i < resultVektor.length; i++)
            {
                result.setzeWert(i, n, resultVektor[i]);
            }
        }
        
        return result;
    }
    
    public double[] vektorMul(double[] x) throws Exception
    {
        if(x.length != matrix[0].length)
        {
            throw new Exception("Dimensionen inkonsistent...");
        }
        
        double[] returnVektor = new double[matrix.length];
        
        for(int n = 0; n < x.length; n++)
        {
            for(int k = 0; k < matrix.length; k++)
            {
                returnVektor[k] += x[n]*matrix[k][n];
            }
        }
        
        return returnVektor;
    }
    
    public void matrixAddInPlace(Matrix x) throws Exception
    {
        if(x.dimensionen()[0] != zeilen || x.dimensionen()[1] != spalten)
        {
            throw new Exception("Dimensionen inkonsistent...");
        }
        
        for(int n = 0; n < zeilen; n++)
        {
            for(int k = 0; k<spalten; k++)
            {
                setzeWert(n, k, this.wert(n, k) + x.wert(n, k));
            }
        }
    }
    
    public void matrixSubInPlace(Matrix x) throws Exception
    {
        x.skalarMulInPlace(-1);
        matrixAddInPlace(x);
        x.skalarMulInPlace(-1);
    }
    
    public void skalarMulInPlace(double x)
    {
        for(int n = 0; n < zeilen; n++)
        {
            for(int k = 0; k<spalten; k++)
            {
                setzeWert(n, k, this.wert(n, k)*x);
            }
        }
    }
    
    public Matrix skalarMul(double x) throws Exception
    {
        Matrix returnMatrix = new Matrix(matrix);
        returnMatrix.skalarMulInPlace(x);
        return returnMatrix;
    }
    
    public void skalarDivInPlace(double x)
    {
        skalarMulInPlace(1.0/x);
    }
    
    
    public int[] dimensionen()
    {
        int[] result = {zeilen, spalten};
        return result;
    }
    
    public String toString()
    {
        String a = "";
        
        for(int n = 0; n < zeilen; n++)
        {
            a += Arrays.toString(matrix[n]);
            a += "\n";
        }
        
        return a;
    }
}
