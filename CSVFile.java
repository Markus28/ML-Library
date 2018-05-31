import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.PrintWriter;


/**
 * Beschreiben Sie hier die Klasse CSVFile.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class CSVFile
{
    Class dp;
    String path;
    
    public CSVFile(String path, Class<? extends CSVDataPoint> dp)
    {
        this.path = path;
        this.dp = dp;
    }

    public List load(int skip) throws Exception
    {
        List fields = new ArrayList<Object>();
        int lineCount = 0;
        
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null){
                lineCount++;
                
                if(lineCount > skip)
                {
                    CSVDataPoint a = (CSVDataPoint) dp.newInstance();
                    fields.add(a.fromLine(line.split("\\,")));
                }
            }
        }
        
        return fields;
    }
    
    public void dump(List<CSVDataPoint> lines) throws Exception
    {
        String result = "";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        for(int n = 0; n < lines.size(); n++)
        {
            writer.println(lines.get(n).toLine());
        }
        
        writer.close();
    }
}
