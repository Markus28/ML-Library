public class BinaryClassifierFactory implements StringBasedFactory<BinaryClassifier>{

    public BinaryClassifier get(String name)
    {
        if(name.equals("BinaryMLP"))
        {
            int[] shape = {28*28, 30, 1};
            double learningRate = 0.03;
            return new BinaryMLP(shape, learningRate);
        }

        return null;
    }
}
