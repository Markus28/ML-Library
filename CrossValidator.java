import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public abstract class CrossValidator<T, S>
{
    private BinaryClassifier model;
    
    public CrossValidator(BinaryClassifier model)
    {
        this.model = model;
    }
    
    public abstract S validate(int folds, double[][] x, T[] y);
    
    public Fold<T>[] folds(int folds, double[][] x, T[] y)
    {
        int length = (int)((double)x.length/(double)folds);
        int end = 0;
        double[][] batchTestIn;
        boolean[] batchTestOut;
        double[][] batchTrainIn;
        boolean[] batchTrainOut;
        int prevBeginning = 0;
        Fold[] foldObjects = new Fold[folds];
        
        ArrayList<Integer> indexArray = new ArrayList<>();
            
        for(int index = 0; index < x.length; index++)
        {
            indexArray.add(index);
        }
        
        Collections.shuffle(indexArray);
        
        for(int index = 0; index < x.length; index++)
        {
            x[index] = x[indexArray.get(index)];
            y[index] = y[indexArray.get(index)];
        }
        
        
        for(int n = 0; n < folds; n++)
        {   
            if((n+1)*length < x.length)
            {
               end = (n+1)*length; 
            }
            
            else
            {
                end = x.length - 1;
            }
            
            batchTestIn = Arrays.copyOfRange(x, n*length, end);
            batchTestOut = Arrays.copyOfRange(y, n*length, end);
            
            batchTrainIn = Utils.addAll(Arrays.copyOfRange(x, prevBeginning ,n*length), Arrays.copyOfRange(x, (n+1)*length, x.length));
            batchTrainOut = Utils.addAll(Arrays.copyOfRange(y, prevBeginning ,n*length), Arrays.copyOfRange(y, (n+1)*length, y.length));
            
            foldObjects[n] = new Fold(batchTrainIn, batchTestIn, batchTrainOut, batchTestOut);
            
            prevBeginning = n*length;
        }
        
        return foldObjects;
    }
}
