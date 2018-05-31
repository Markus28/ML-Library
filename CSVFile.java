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
public abstract class CSVFile
{
    private String path;
    private ArrayList<CSVDataRow> fields;
    
    public CSVFile(String path)
    {
        this.path = path;
    }

    public void load(int skip) throws Exception
    {
        fields = new ArrayList<>();
        int lineCount = 0;
        
        try(BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while((line = br.readLine()) != null){
                lineCount++;
                
                if(lineCount > skip)
                {
                    fields.add(getRow(line.split("\\,")));
                }
            }
        }
    }
    
    public void dump() throws Exception
    {
        String result = "";
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        
        for(int n = 0; n < fields.size(); n++)
        {
            writer.println(fields.get(n).toLine());
        }
        
        writer.close();
    }
    
    public ArrayList<CSVDataRow> getContent()
    {
        return fields;
    }
    
    protected abstract CSVDataRow getRow(String[] line);
}
