package machinelearning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Beschreiben Sie hier die Klasse machinelearning.OneHotCoder.
 * 
 * @Markus K
 * @1.0
 */
public class OneHotCoder<T> implements java.io.Serializable
{
    public ArrayList<T> classes = new ArrayList();
    public HashMap<T, int[]> hm = new HashMap();
    public int size;
    
    public OneHotCoder(ArrayList<T> classes)
    {
        HashSet<T> hs = new HashSet<>(classes);
        this.classes.addAll(hs);
        size = this.classes.size();
        
        for(int n = 0; n < this.classes.size(); n++)
        {
            int[] arr = new int[this.classes.size()];
            arr[n] = 1;
            hm.put(this.classes.get(n), arr);
        }
        
    }

    public int[] encode(T label)
    {
       return hm.get(label);
    }
    
    public T decode(int[] encoded) throws Exception
    {
        for(int n = 0; n < encoded.length; n++)
        {
            if(encoded[n]==1)
            {
                return classes.get(n);
            }
        }
        
        throw new Exception("No Hot element...");
    }
    
}
