package machinelearning;

import machinelearning.CSVDataRow;


public class MNISTDataRow implements CSVDataRow
{
    private double[] features;
    private int label;
    
    public MNISTDataRow(String[] line)
    {
        this.label = Integer.valueOf(line[0]);
        this.features = new double[line.length - 1];
        for(int n = 0; n < line.length-1; n++)
        {
            this.features[n] = Double.valueOf(line[n+1])/255.0;
        }
    }
    
    public String toLine()
    {
        String result = "";
        result += label + ",";
        
        for(int n = 0; n < features.length; n++)
        {
            result += features[n];
            if(n != features.length - 1)
            {
                result += ",";
            }
        }
        
        return result;
    }

    public double[] getFeatures()
    {
        return features;
    }
    
    public int getLabel()
    {
        return label;
    }
}
