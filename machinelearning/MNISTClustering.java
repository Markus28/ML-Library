package machinelearning;

import java.util.ArrayList;

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

        KMeans clusterer = new KMeans(10);

        clusterer.train(x);

        MNISTImage img;
        double[][] centroids = clusterer.getCentroids();
        int n = 0;

        for(double[] centroid: centroids)
        {
            img = new MNISTImage(centroid);
            img.writeImage("out/Data/"+n+".png");
            n++;
        }
    }
}
