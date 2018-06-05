package machinelearning;

public class BinaryClassifierFactory extends StringBasedFactory<BinaryClassifier> {

    public BinaryClassifier get(String name)
    {
        if(name.equals("machinelearning.BinaryMLP"))
        {
            int[] shape = {28*28, 30, 1};
            double cutoff = 0.5;
            BinaryMLP model = new BinaryMLP(shape, cutoff);
            model.setParameters(10, 15, 0.04);
            return model;
        }

        return null;
    }
}
