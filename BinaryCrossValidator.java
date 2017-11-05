import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * K-fache Kreuzvalidierung
 * 
 * @Markus K 
 * @1.0
 */
public class BinaryCrossValidator
{
    private BinaryClassifier model;

    /**
     * Konstruktor für Objekte der Klasse CrossValidator
     */
    public BinaryCrossValidator(BinaryClassifier model)
    {
        this.model = model;
    }


    public ConfusionMatrix[] validate(int folds, double[][] x, boolean[] y, List parameters) throws Exception
    {
        double[][] in = new double[x.length][];
        boolean[] out = new boolean[y.length];
        int length = (int)((double)x.length/(double)folds);
        int end = 0;
        double[][] batchTestIn;
        boolean[] batchTestOut;
        double[][] batchTrainIn;
        boolean[] batchTrainOut;
        int prevBeginning = 0;
        ConfusionMatrix[] matrices = new ConfusionMatrix[folds];
        
        List<Integer> indexArray = new ArrayList();
            
        for(int index = 0; index < x.length; index++)
        {
            indexArray.add(index);
        }
        
        Collections.shuffle(indexArray);
        
        for(int index = 0; index < x.length; index++)
        {
            in[index] = x[indexArray.get(index)];
            out[index] = y[indexArray.get(index)];
        }
        
        
        for(int n = 0; n < folds; n++)
        {
            matrices[n] = new ConfusionMatrix();
            
            if((n+1)*length < x.length)
            {
               end = (n+1)*length; 
            }
            
            else
            {
                end = x.length - 1;
            }
            
            batchTestIn = Arrays.copyOfRange(in, n*length, end);
            batchTestOut = Arrays.copyOfRange(out, n*length, end);
            
            batchTrainIn = Utils.addAll(Arrays.copyOfRange(in, prevBeginning ,n*length), Arrays.copyOfRange(in, (n+1)*length, in.length));
            batchTrainOut = Utils.addAll(Arrays.copyOfRange(out, prevBeginning ,n*length), Arrays.copyOfRange(out, (n+1)*length, out.length));
            
            model.lernen(batchTrainIn, batchTrainOut, parameters);
            
            for(int i = 0; i < batchTestIn.length; i++)
            {
                boolean res = model.vorhersage(batchTestIn[i]);
                
                if(!res && !batchTestOut[i])
                {
                    matrices[n].addTN();
                }
                else if(res && batchTestOut[i])
                {
                    matrices[n].addTP();
                }
                else if(!res && batchTestOut[i])
                {
                    matrices[n].addFN();
                }
                else
                {
                    matrices[n].addFP();
                }
            }
            prevBeginning = n*length;
        }
        
        return matrices;
    }
    
}
