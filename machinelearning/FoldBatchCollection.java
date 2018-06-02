package machinelearning;

import java.util.Iterator;
import java.util.Arrays;

public class FoldBatchCollection<T> implements Iterable<FoldBatch<T>>{

    private final double[][] x;
    private final T[] y;
    private final int folds;
    private final int end;
    private final int foldSize;

    public FoldBatchCollection(int folds, double[][]x, T[] y)
    {
        this.x = x;
        this.y = y;
        this.folds = folds;
        foldSize = x.length/folds;
        end = foldSize*folds;

    }

    @Override
    public Iterator<FoldBatch<T>> iterator()
    {
        return new FoldBatchIterator();
    }

    private class FoldBatchIterator implements Iterator<FoldBatch<T>>{

        private int currentFold = 0;

        @Override
        public boolean hasNext()
        {
            return currentFold < folds - 1;
        }

        @Override
        public FoldBatch<T> next()
        {
            int[] testBounds = getTestBounds();
            double[][] testX = Arrays.copyOfRange(x, testBounds[0], testBounds[1]);
            double[][] trainX = Utils.addAll(Arrays.copyOfRange(x, 0, testBounds[0]), Arrays.copyOfRange(x, testBounds[1], end));

            T[] testY = Arrays.copyOfRange(y, testBounds[0], testBounds[1]);
            T[] trainY = Utils.addAll(Arrays.copyOfRange(y, 0, testBounds[0]), Arrays.copyOfRange(y, testBounds[1], end));
            return new FoldBatch<>(trainX, testX, trainY, testY);
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        private int[] getTestBounds()       //Start inclusive, stop exclusive
        {
            int[] bounds = {currentFold*foldSize, (currentFold+1)*foldSize};
            return bounds;
        }
    }
}
