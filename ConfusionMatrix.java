
/**
 * ConfusionMatrix.
 * 
 * @Markus K
 * @1.0
 */
public class ConfusionMatrix
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private int TP;
    private int FP;
    private int TN;
    private int FN;

    public ConfusionMatrix(int TP, int FP, int TN, int FN)
    {
        this.TP = TP;
        this.FP = FP;
        this.TN = TN;
        this.FN = FN;
    }
    
    public ConfusionMatrix()
    {
        TP = 0;
        FP = 0;
        TN = 0;
        FN = 0;
    }
    
    public Matrix getMatrix() throws Exception
    {
        double[][] content = {{TP,FP},{FN,TN}};
        return new Matrix(content);
    }
    
    public Matrix getRateMatrix() throws Exception
    {
        double[][] content = {{((double) TP)/(TP + FN), ((double) FP)/(FP + TN)},{((double) FN)/(FN + TP), ((double) TN)/(TN + FP)}};
        return new Matrix(content);
    }
    
    public double getMCC()
    {
        return (TP*TN - FP*FN)/Math.sqrt(((TP+FP)*(TP+FN)*(TN+FP)*(TN+FN)));
    }
    
    public double getAC()
    {
        return ((double)(TP + TN))/(TP + TN + FN + FP);
    }
    
    public double getP()
    {
        return ((double) TP)/(FP + TP);
    }
    
    public void addTP()
    {
        TP++;
    }
    
    public void addFP()
    {
        FP++;
    }
    
    public void addTN()
    {
        TN++;
    }
    
    public void addFN()
    {
        FN++;
    }
}
