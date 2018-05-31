import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Beschreiben Sie hier die Klasse MNISTClassifier.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class MNISTClassifier
{
    static MultiClassMLP model;
    
    public static void main(String[] args) throws Exception, IOException
    {
        int[] shape = {28*28, 30, 10};
        if(shape[0] != 28*28 || shape[shape.length - 1] != 10)
        {
            throw new Exception("In- or Output layer dimensions wrong...");
        }
        
        //model = new MultiClassMLP(shape);
        FileInputStream fileIn = new FileInputStream("model.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        model = (MultiClassMLP) in.readObject();
        System.out.print("Loading data");
        LoadingDots runnable = new LoadingDots(0.5);
        Thread thread = new Thread(runnable);
        thread.start();
        CSVFile f = new CSVFile("train.csv", MNISTDataPoint.class);
        List<MNISTDataPoint> fields = f.load(0);
        double[][] x = new double[fields.size()][];
        List y = new ArrayList<Integer>();
        List params = new ArrayList();
        params.add(15);
        params.add(90);
        params.add(0.03);
        
        for(int n = 0; n < fields.size(); n++)
        {
            x[n] = fields.get(n).getFeatures();
            y.add(fields.get(n).getLabel());
        }
        
        runnable.terminate();
        thread.join();
        
        System.out.println("Training:\n");
        model.lernen(x, y, params);
        
        f = new CSVFile("test.csv", MNISTDataPoint.class);
        fields = f.load(0);
        int correct = 0;
        for(int n = 0; n < fields.size(); n++)
        {
            if((int) model.vorhersage(fields.get(n).getFeatures()) == fields.get(n).getLabel())
            {
                correct ++;
            }

        }
        
        System.out.println("Accuracy: " + ((double) correct*100)/fields.size()+"%");
        FileOutputStream fileOut = new FileOutputStream("model.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(model);
        out.close();
        fileOut.close();
    }

}
