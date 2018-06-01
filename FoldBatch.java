public class FoldBatch<T>
{
    private double[][] xTrain;
    private double[][] xTest;
    private T[] yTrain;
    private T[] yTest;

    public FoldBatch(double[][] xTrain, double[][] xTest, T[] yTrain, T[] yTest)
    {
        this.xTrain = xTrain;
        this.xTest = xTest;
        this.yTrain = yTrain;
        this.yTest = yTest;
    }
    
    public T[] getYTrain()
    {
        return yTrain;
    }

    public T[] getYTest()
    {
        return yTest;
    }
    
    public double[][] getXTrain()
    {
        return xTrain;
    }
    
    public double[][] getXTest()
    {
        return xTest;
    }
}
