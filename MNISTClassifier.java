import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class MNISTClassifier
{
    private static MultiClassMLP<Integer> model;
    
    public static void main(String[] args) throws Exception, IOException
    {
        int[] shape = {28*28, 30, 10};
        if(shape[0] != 28*28 || shape[shape.length - 1] != 10)
        {
            throw new Exception("In- or Output layer dimensions wrong...");
        }
        
        try{
            FileInputStream fileIn = new FileInputStream("model.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            model = (MultiClassMLP) in.readObject();
        }
        
        catch(Exception e){
            System.err.println("Couldn't read .ser file, creating new model...");
            model = new MultiClassMLP<>(shape);
        }
        
        System.out.print("Loading data");
        LoadingDots runnable = new LoadingDots(0.5);
        Thread thread = new Thread(runnable);
        thread.start();
        MNISTFile f = new MNISTFile("out/Data/train.csv");
        f.load(0);
        List<CSVDataRow> fields = f.getContent();
        double[][] x = new double[fields.size()][];
        ArrayList<Integer> y = new ArrayList<>();
        
        for(int n = 0; n < fields.size(); n++)
        {
            x[n] = ((MNISTDataRow)fields.get(n)).getFeatures();
            y.add(((MNISTDataRow)fields.get(n)).getLabel());
        }
        
        runnable.terminate();
        thread.join();

        System.out.println("Training:\n");
        model.setParameters(15,90,0.03);
        model.train(x, y);
        
        f = new MNISTFile("out/Data/test.csv");
        f.load(0);
        fields = f.getContent();
        int correct = 0;
        for(CSVDataRow field: fields)
        {
            if(model.predict(((MNISTDataRow) field).getFeatures()) == ((MNISTDataRow) field).getLabel())
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
