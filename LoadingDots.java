import java.util.concurrent.TimeUnit;

public class LoadingDots implements Runnable
{
    private int i = 0;
    private final double wait;
    private volatile boolean running;
    
    public LoadingDots(double wait)
    {
        this.wait = wait;
        running = true;
    }

    @Override
    public void run()
    {
        while(running)
        {
            try{
                Thread.sleep((int)(wait*1000));
            }
            catch(InterruptedException e){
                System.err.println(e);
            }
        
            if(i<3)
            {
                System.out.print(".");
                i++;
            }
            
            else{
                System.out.print("\b\b\b   \b\b\b");
                i = 0;
            }
        }
        
        System.out.print(new String(new char[i]).replace("\0", "\b") + " [DONE]\n");
    }
    
    public void terminate()
    {
        running = false;
    }
}
