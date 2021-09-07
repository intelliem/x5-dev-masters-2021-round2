package sets;

import java.util.*;

public class SetPartitionGenerator<T> implements Iterable<List<List<T>>> {

    private final List<T> setSequence;

    public SetPartitionGenerator(Collection<T> setSequence) {
        super();
        this.setSequence = new ArrayList<>(setSequence);
    }

    private class PartitionIterator implements Iterator<List<List<T>>> {

        private final Iterator<int[]> itr = new PartitionSequenceGenerator(setSequence.size()).iterator();

        @Override
        public boolean hasNext() {
            return itr.hasNext();
        }

        @Override
        public List<List<T>> next() {
            if (!itr.hasNext()) {
                throw new NoSuchElementException();
            }
            int[] part = itr.next();
            List<List<T>> result = new ArrayList<>();
            for (int i = 0; i < part.length; i++) {
                if (result.size() < part[i] + 1) {
                    result.add(new ArrayList<>());
                }
                result.get(part[i]).add(setSequence.get(i));
            }
            return result;
        }
    }

    @Override
    public Iterator<List<List<T>>> iterator() {
        return new PartitionIterator();
    }
}
