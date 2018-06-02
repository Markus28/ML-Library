package machinelearning;

public class MNISTFile extends CSVFile
{
    public MNISTFile(String path)
    {
        super(path);
    }
    
    @Override
    protected CSVDataRow getRow(String[] line)
    {
        return new MNISTDataRow(line);
    }
}
