import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Random;


public class Matrix implements java.io.Serializable
{
    private double[][] matrix;
    int rows;
    int columns;


    public Matrix(double[][] matrix) throws Exception
    {
        rows = matrix.length;
        columns = matrix[0].length;
        for(int i = 1; i < matrix.length; i++)
        {
            if(matrix[i].length != columns)
            {
                throw new Exception("Dimensionen inkonsistent...");
            }
        }
        this.matrix = matrix;
    }
    
    public Matrix(int m, int n)
    {
        rows = m;
        columns = n;
        matrix = new double[m][n];
    }
    
    public static Matrix eye(int m)
    {
        Matrix returnMatrix = new Matrix(m, m);
        
        for(int index = 0; index < m; index++)
        {
            returnMatrix.setValue(index, index, 1);
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
        return Matrix.arrayToRow(arr).transpose();
    }
    
    public static Matrix random(int m, int n, double min, double max)
    {
        Matrix returnMatrix = new Matrix(m, n);
        
        for(int k = 0; k < m; k++)
        {
            for(int i = 0; i < n; i++)
            {
                returnMatrix.setValue(k, i, ThreadLocalRandom.current().nextInt((int)(min*10000.0), (int)(max*10000.0 + 1.0))/10000.0);
            }
        }
        
        return returnMatrix;
    }
    
    public static Matrix randomGaussian(int m, int n, double mean, double var)
    {
        Matrix returnMatrix = new Matrix(m, n);
        Random r = new Random();
        
        for(int k = 0; k < m; k++)
        {
            for(int i = 0; i < n; i++)
            {
                returnMatrix.setValue(k, i, r.nextGaussian()*var + mean);
            }
        }
        
        return returnMatrix;
    }
    
    public double getValue(int m, int n)
    {
        return matrix[m][n];
    }
    
    public void setValue(int m, int n, double value)
    {
        matrix[m][n] = value;
    }

    public Matrix transpose()
    {
        Matrix returnMatrix = new Matrix(columns, rows);
        
        for(int k = 0; k < rows; k++)
        {
            for(int i = 0; i < columns; i++)
            {
                returnMatrix.setValue(i, k, matrix[k][i]);
            }
        }
        
        return returnMatrix;
    }
    
    public static Matrix matMul(Matrix a, Matrix b) throws Exception
    {
        Matrix result = new Matrix(a.getDimensions()[0], b.getDimensions()[1]);
        double[] vector = new double[b.getDimensions()[0]];
        for(int n = 0; n < b.getDimensions()[1]; n++)
        {
            for(int k = 0; k < b.getDimensions()[0]; k++)
            {
                vector[k] = b.getValue(k, n);
            }
            
            double[] resultVector = a.vectorMul(vector);
            
            for(int i = 0; i < resultVector.length; i++)
            {
                result.setValue(i, n, resultVector[i]);
            }
        }
        
        return result;
    }
    
    public double[] vectorMul(double[] x) throws Exception
    {
        if(x.length != matrix[0].length)
        {
            throw new Exception("Dimensionen inkonsistent...");
        }
        
        double[] returnVector = new double[matrix.length];
        
        for(int n = 0; n < x.length; n++)
        {
            for(int k = 0; k < matrix.length; k++)
            {
                returnVector[k] += x[n]*matrix[k][n];
            }
        }
        
        return returnVector;
    }
    
    public void matrixAddInPlace(Matrix x) throws InconsistentDimensionsException
    {
        if(x.getDimensions()[0] != rows || x.getDimensions()[1] != columns)
        {
            throw new InconsistentDimensionsException("Matrices must have same dimensions...");
        }
        
        for(int n = 0; n < rows; n++)
        {
            for(int k = 0; k< columns; k++)
            {
                setValue(n, k, this.getValue(n, k) + x.getValue(n, k));
            }
        }
    }
    
    public void matrixSubInPlace(Matrix x) throws Exception
    {
        x.scalarMulInPlace(-1);
        matrixAddInPlace(x);
        x.scalarMulInPlace(-1);
    }
    
    public void scalarMulInPlace(double x)
    {
        for(int n = 0; n < rows; n++)
        {
            for(int k = 0; k< columns; k++)
            {
                setValue(n, k, this.getValue(n, k)*x);
            }
        }
    }
    
    public Matrix scalarMul(double x) throws Exception
    {
        Matrix returnMatrix = new Matrix(rows, columns);
        for(int n = 0; n < rows; n++)
        {
            for(int k = 0; k < columns; k++)
            {
                returnMatrix.setValue(n, k, getValue(n, k));
            }
        }

        returnMatrix.scalarMulInPlace(x);
        return returnMatrix;
    }

    
    public int[] getDimensions()
    {
        int[] result = {rows, columns};
        return result;
    }
    
    public String toString()
    {
        String a = "";
        
        for(int n = 0; n < rows; n++)
        {
            a += Arrays.toString(matrix[n]);
            a += "\n";
        }
        
        return a;
    }
}
