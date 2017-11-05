import java.util.List;
import java.util.ArrayList; 
import java.util.Arrays;

/**
 * Beschreiben Sie hier die Klasse Testing.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Testing
{
    public static void test() throws Exception
    {
        
        double[][] train = {{1.0,1.0}, {0.0,0.0}, {0.9,0.9}, {0,0.1}};
        KMeans model = new KPlus(2);
        model.train(train);
        System.out.println(Arrays.toString(model.predictBatch(train)));
    }
    
    public void a()
    {
        int[] i = {0};
        b(i);
        System.out.println(Arrays.toString(i));
    }
    
    public void b(int[] i)
    {
        i[0] = 1;
    }
    
}
