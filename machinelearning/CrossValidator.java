package machinelearning;

public abstract class CrossValidator<T, S>
{
    private BinaryClassifier model;
    
    public CrossValidator(BinaryClassifier model)
    {
        this.model = model;
    }
    
    public abstract S validate(int folds, double[][] x, T[] y);

    public Iterable<FoldBatch<T>> folds(int folds, double[][] x, T[] y)
    {
        FoldBatchCollection<T> foldCollection = new FoldBatchCollection<>(folds, x, y);
        return foldCollection;
    }
}
