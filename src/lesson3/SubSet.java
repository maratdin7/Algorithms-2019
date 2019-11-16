package lesson3;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class SubSet<T extends Comparable<T>> extends BinaryTree<T> {

    private T fromElement;
    private T toElement;

    private BinaryTree<T> tree;

    SubSet(BinaryTree<T> tree, T from, T to) {
        this.tree = tree;
        this.fromElement = from;
        this.toElement = to;
    }

    private boolean inRange(T t) {
        boolean to = true;
        if (toElement != null) to = t.compareTo(toElement) < 0;

        if (fromElement == null) return to;
        return to && t.compareTo(fromElement) >= 0;
    }

    @Override
    public boolean remove(Object o) {
        return inRange((T) o) && tree.remove(o);
    }

    private class SubSetIterator implements Iterator<T> {

        Iterator<T> i = tree.iterator();

        private SubSetIterator() {

        }

        @Override
        public boolean hasNext() {
            return i.hasNext();
        }

        @Override
        public T next() {
            while (i.hasNext()) {
                T t = i.next();
                if (inRange(t)) {
                    return t;
                }
            }
            return null;
        }
    }

    @Override
    public boolean contains(Object o) {
        T t = (T) o;
        if (inRange(t)) return tree.contains(o);
        else return false;
    }

    @Override
    public boolean add(T t) {
        if (inRange(t)) return tree.add(t);
        else throw new IllegalArgumentException();
    }

    @Override
    public int size() {
        int size = 0;
        for (T t : this) {
            if (t != null)
                size++;
        }
        return size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new SubSetIterator();
    }

}
