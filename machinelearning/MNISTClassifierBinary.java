package machinelearning;

import java.util.ArrayList;

public class MNISTClassifierBinary {

    public static void main(String[] args) throws Exception
    {
        MNISTFile f = new MNISTFile("out/Data/train.csv");
        f.load(0);
        ArrayList<CSVDataRow> fields = f.getContent();
        ArrayList<Integer> y = new ArrayList<>();

        double[][] x = new double[fields.size()][];

        for(int n = 0; n < fields.size(); n++)
        {
            x[n] = ((MNISTDataRow)fields.get(n)).getFeatures();
            y.add(((MNISTDataRow)fields.get(n)).getLabel());
        }

        MultiClassClassifier<Integer> model = new OneVsRest<>(new BinaryClassifierFactory(), "machinelearning.BinaryMLP");
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
    }
}
