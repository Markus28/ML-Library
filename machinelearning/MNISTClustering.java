package machinelearning;

import java.util.ArrayList;
import java.util.Arrays;

public class MNISTClustering {

    public static void main(String[] args) throws Exception
    {
        MNISTFile f = new MNISTFile("out/Data/train.csv");
        f.load(0);
        ArrayList<CSVDataRow> fields = f.getContent();

        double[][] x = new double[fields.size()][];

        for(int n = 0; n < fields.size(); n++)
        {
            x[n] = ((MNISTDataRow)fields.get(n)).getFeatures();
        }

        int[] halfLayer = {28*28, 25, 5};
        AutoEncoder encoder = new AutoEncoder(halfLayer);

        encoder.setParameters(3, 50, 0.1);
        encoder.train(x, x);

        KMeans clusterer = new KMeans(10);
        double[][] clusterData = new double[x.length][];

        for(int n = 0; n < x.length; n++)
        {
            clusterData[n] = encoder.encode(x[n]);
        }


        clusterer.train(clusterData);

        MNISTImage img;
        //double[][] centroids = clusterer.getCentroids();

        for(int n = 0; n < 10; n++)
        {
            System.out.println(Arrays.toString(encoder.encode(x[n])));
            img = new MNISTImage(encoder.decode(encoder.encode(x[n])));
            img.writeImage("out/Data/"+n+".png");
        }
    }
}
