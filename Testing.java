import java.util.ArrayList;


public class Testing
{
    public static void main(String[] args) throws Exception
    {
        MNISTFile f = new MNISTFile("out/Data/train.csv");
        f.load(0);
        ArrayList<CSVDataRow> fields = f.getContent();
        MNISTImage img = new MNISTImage(((MNISTDataRow) fields.get(142)).getFeatures());
        System.out.println(((MNISTDataRow) fields.get(142)).getLabel());
        img.writeImage("out/Data/example.png");
    }
}
