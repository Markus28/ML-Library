
/**
 * Wrapper für Backpropagation Ergebnisse
 * 
 * @Markus K
 * @1.0
 */
public class BackpropNabla
{
    // Instanzvariablen - ersetzen Sie das folgende Beispiel mit Ihren Variablen
    private Matrix[] gewichteNabla;
    private double[][] biasNabla;

    /**
     * Konstruktor für Objekte der Klasse BackpropNabla
     */
    public BackpropNabla(Matrix[] gewichte, double[][] bias)
    {
        gewichteNabla = gewichte;
        biasNabla = bias;
    }

    public Matrix[] getGewichte()
    {
        return gewichteNabla;
    }
    
    public double[][] getBias()
    {
        return biasNabla;
    }
}
