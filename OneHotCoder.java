import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

/**
 * Beschreiben Sie hier die Klasse OneHotCoder.
 * 
 * @Markus K
 * @1.0
 */
public class OneHotCoder implements java.io.Serializable
{
    public List classes = new ArrayList<Object>();
    public HashMap<Object, int[]> hm = new HashMap();;
    public int size;
    
    public OneHotCoder(List classes)
    {
        Set hs = new HashSet();
        hs.addAll(classes);
        this.classes.addAll(hs);
        size = this.classes.size();
        
        for(int n = 0; n < this.classes.size(); n++)
        {
            int[] arr = new int[this.classes.size()];
            arr[n] = 1;
            hm.put(this.classes.get(n), arr);
        }
    }

    public int[] encode(Object label)
    {
       return hm.get(label);
    }
    
    public Object decode(int[] encoded) throws Exception
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
