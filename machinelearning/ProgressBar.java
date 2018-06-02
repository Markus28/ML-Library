package machinelearning;

public class ProgressBar
{
    private int maxPrintCount;
    private int printCount;
    private int maxTickCount;
    private int ticks;
    private int skip;
    private int fields = 37;
    
    public ProgressBar(int ticks, int skip)
    {
        maxTickCount = ticks;
        maxPrintCount = (int)((double) ticks)/skip;
        this.skip = skip;
    }
    
    
    public void tick()
    {
        ticks++;
        if(ticks % skip == 0)
        {
            printCount++;
            print();
        }
    }
    
    public void print()
    {
        String s = "|";
        double ratio = ((double) printCount)/maxPrintCount;
        int hashes = (int) (fields*ratio);
        
        for(int n = 0; n < fields; n++)
        {
            if(n < hashes)
            {
                s += "#";
            }
            else
            {
                s += " ";
            }
        }
        
        s += "|";
        s +=  String.format("%.2f", ratio*100);
        s += "%";
        
        if(printCount != maxPrintCount)
        {
            s += "\r";
        }
        
        else{
            s += "\n";
        }
        
        System.out.print(s);
    }
}
