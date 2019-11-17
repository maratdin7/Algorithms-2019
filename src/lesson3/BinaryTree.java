package lesson3;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    protected static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node<T> parent = null;

        Node(T value) {
            this.value = value;
        }

    }

    private Node<T> root = null;

    public Node<T> getRoot() {
        return root;
    }

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        newNode.parent = closest;
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        Node<T> remove = find(t);
        if (!contains(o)) return false;

        assert remove != null;
        Node<T> newNode;
        if (remove.left != null) {
            newNode = last(remove.left);

            parentNode(newNode, newNode.left);

            if (newNode.left != null) newNode.left.parent = newNode.parent;

            newNode.parent = remove.parent;

            if (remove.left == null || newNode.value.compareTo(remove.left.value) != 0) newNode.left = remove.left;

            newNode.right = remove.right;
            parentNode(remove, newNode);

        } else if (remove.right != null) {
            newNode = first(remove.right);

            parentNode(newNode, newNode.right);

            if (newNode.right != null) newNode.right.parent = newNode.parent;

            newNode.parent = remove.parent;
            newNode.left = remove.left;

            if (newNode.right == null || newNode.value.compareTo(remove.right.value) != 0) newNode.right = remove.right;

            parentNode(remove, newNode);
        } else parentNode(remove, null);
        size--;
        return true;
    }

    private void remove(Node<T> remove, Node<T> newNode, Node<T> n) {
        parentNode(newNode, n);
        if (n != null) n.parent = newNode.parent;
        newNode.parent = remove.parent;
        newNode.left = remove.left;
        newNode.right = remove.right;
        parentNode(remove, newNode);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        protected Node<T> current = root;

        private Integer previous = 0;

        Node<T> next;

        private Stack<Node<T>> stack = new Stack<>();

        private BinaryTreeIterator() {
        }


        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
//////////////////////////////////////
// Временнная сложность O(1)        //
// Сложность по памяти O(1)         //
//////////////////////////////////////
        @Override
        public boolean hasNext() {
            return !stack.empty() || current != null;
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
//////////////////////////////////////
// В обход всего дерева O(n)        // тк элементы в стеке получаются за O(1). Выполнение next() в худшем случаее O(n),
//                                  //    но тогда получение всех оставшихся элементов O(1)
// Сложность по памяти O(n)         //
//////////////////////////////////////
        @Override
        public T next() {
            while (hasNext()) {
                if (current != null) {
                    stack.push(current);
                    current = current.left;
                } else {
                    next = stack.pop();
                    current = next.right;
                    previous++;
                    return next.value;
                }
            }
            throw new NoSuchElementException();
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
//////////////////////////////////////
// Временнная сложность O(n)        //
// Сложность по памяти O(n)         //
//////////////////////////////////////
        @Override
        public void remove() {
            Integer prev = previous - 1;
            Node<T> remove = next;
            Node<T> parent = remove.parent;
            if (remove.left == null && remove.right == null) {
                parentNode(remove, null);
            } else if (remove.right != null) {

                if (remove.left != null) {
                    Node<T> first = first(remove.right);
                    first.left = remove.left;
                    remove.left.parent = first.left;
                }
                parentNode(remove, remove.right);
                remove.right.parent = parent;
            } else {
                parentNode(remove, remove.left);
                remove.left.parent = parent;
            }

            BinaryTreeIterator i = new BinaryTreeIterator();
            while (i.hasNext() && prev > i.previous) {
                i.next();

            }
            stack = i.stack;
            current = i.current;
            next = i.next;
            previous = i.previous;
            size--;
        }


    }

    private void parentNode(Node<T> remove, Node<T> val) {
        Node<T> parent = remove.parent;
        if (parent == null) root = val;
        else {
            if (parent.right != null && parent.right.value.compareTo(remove.value) == 0) parent.right = val;
            else parent.left = val;
        }
    }

    private Node<T> first(Node<T> newRoot) {
        if (newRoot == null) throw new NoSuchElementException();
        Node<T> current = newRoot;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node<T> last(Node<T> newRoot) {
        if (newRoot == null) throw new NoSuchElementException();
        Node<T> current = newRoot;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
//////////////////////////////////////
// Временнная сложность равна сл-ти //
// соответсвующих ф-ций BinaryTree  //
// Кроме size() O(n)                //
// Кроме обход всего SubSet O(n)    // тк просто обход дерева
// Сложность по памяти O(n)         //
//////////////////////////////////////
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return new SubSet<>(this, fromElement, toElement);
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return new SubSet<>(this, null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return new SubSet<>(this, fromElement, null);
    }

    @Override
    public T first() {
        return first(root).value;
    }

    @Override
    public T last() {
        return last(root).value;
    }
}
