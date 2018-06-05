package machinelearning;

public class BinaryClassifierFactory extends StringBasedFactory<BinaryClassifier> {

    public BinaryClassifier get(String name)
    {
        if(name.equals("machinelearning.BinaryMLP"))
        {
            int[] shape = {28*28, 30, 1};
            double learningRate = 0.03;
            return new BinaryMLP(shape, learningRate);
        }

        return null;
    }
}
