
/**
 * Tragen Sie hier eine Beschreibung des Interface CSVDatapoint ein.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public interface CSVDataPoint
{
    Object fromLine(String[] line);
    String toLine();
}
