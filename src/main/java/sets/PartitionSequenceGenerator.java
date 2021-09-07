package sets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class PartitionSequenceGenerator implements Iterable<int[]> {

    private int[] partitionSequence;
    private int[] support;

    public PartitionSequenceGenerator(int n) {
        partitionSequence = new int[n];
        support = new int[n];
        startFill();
    }

    private final void startFill() {
        for (int i = 0; i < partitionSequence.length; i++) {
            partitionSequence[i] = 0;
            support[i] = 1;
        }
    }

    private void nextStep() {
        int n = partitionSequence.length;
        if (partitionSequence[n - 1] != support[n - 1]) {
            partitionSequence[n - 1] += 1;
        } else {
            int j = n - 2;
            for (; partitionSequence[j] == support[j] && j >= 0; ) {
                j = j - 1;
            }
            if (j == 0) {
                partitionSequence = null;
                return;
            }
            partitionSequence[j] += 1;
            support[n - 1] = support[j] + (partitionSequence[j] == support[j] ? 1 : 0);
            for (int i = j + 1; i < partitionSequence.length; i++) {
                partitionSequence[i] = 0;
                support[i] = support[n - 1];
            }
        }
    }

    private class partitionSetIterator implements Iterator<int[]> {
        @Override
        public boolean hasNext() {
            return partitionSequence != null;
        }

        @Override
        public int[] next() {
            if (partitionSequence == null) {
                throw new NoSuchElementException();
            }
            int[] result = Arrays.copyOf(partitionSequence, partitionSequence.length);
            nextStep();
            return result;
        }
    }

    @Override
    public Iterator<int[]> iterator() {
        return new partitionSetIterator();
    }

}
