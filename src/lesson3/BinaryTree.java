package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;
        Node<T> left = null;
        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;
    private int size = 0;
    private boolean isLeftChild;

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
        size++;
        System.out.println("Add" + t.toString());
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        Node<T> current = root;
        Node<T> parent = root;
        isLeftChild = true;
        int compare;

        @SuppressWarnings("unchecked")
        T key = (T) o;

        while ((compare = current.value.compareTo(key)) != 0) {
            parent = current;
            if (compare > 0) {
                isLeftChild = true;
                current = current.left;
            } else {
                isLeftChild = false;
                current = current.right;
            }
            if (current == null)
                return false;
        }
        deleteThisNode(current, parent);
        size--;
        return true;
    }

    private void removeNode(Node<T> node) {
        remove(node.value);
    }

    private void deleteThisNode(Node<T> current, Node<T> parent) {
        if (current.left == null && current.right == null) {
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else
                parent.right = null;

        } else if (current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else
                parent.right = current.left;

        } else if (current.left == null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else
                parent.right = current.right;

        } else {
            Node successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else
                parent.right = successor;

            successor.left = current.left;
        }

    }

    private Node<T> getParent(Node<T> nodeChild) {
        Node<T> current = root;
        while (current.left.value == nodeChild.value || current.right.value == nodeChild.value) {
            if (nodeChild.equals(current))
                current = current.left;
            else current = current.right;
            if (current == null) return null;
        }
        return current;
    }

    private Node<T> getSuccessor(Node<T> SuccNode) {
        Node<T> successorParent = SuccNode;
        Node<T> successor = SuccNode;
        Node<T> currentNode = SuccNode.right;

        while (currentNode != null) {
            successorParent = successor;
            successor = currentNode;
            currentNode = currentNode.left;
        }

        if (successor != SuccNode.right) {
            successorParent.left = successor.right;
            successor.right = SuccNode.right;
        }

        return successor;
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
        private Node<T> current;
        private Stack<Node<T>> treeStack = new Stack<>();

        private BinaryTreeIterator() {
            current = root;
            while (current != null) {
                treeStack.push(current);
                current = current.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        private Node<T> findNext() {
            Node<T> lastInStack = treeStack.pop();
            current = lastInStack;
            if (lastInStack.right != null) {
                lastInStack = lastInStack.right;
                while (lastInStack != null) {
                    treeStack.push(lastInStack);
                    lastInStack = lastInStack.left;
                }
            }
            return current;
        }

        @Override
        public boolean hasNext() {
            //return findNext() != null;
            return !treeStack.isEmpty();
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            BinaryTree.this.removeNode(current);
        }
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
     * Трудоемкость: О(n)?(Здесь же if-else и не могут использоваться сразу 3 цикла)
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) throws IllegalArgumentException, NullPointerException {

        BinaryTree<T> newSet = new BinaryTree<>();
        BinaryTreeIterator treeIterator = new BinaryTreeIterator();

        if (fromElement == null) {
            while (treeIterator.hasNext()) {
                if (treeIterator.next().compareTo(toElement) < 0)
                    newSet.add(treeIterator.current.value);
            }
        }
        if (toElement == null) {
            while (treeIterator.hasNext()) {
                if (treeIterator.next().compareTo(fromElement) >= 0)
                    newSet.add(treeIterator.current.value);
            }
        } else {
            while (treeIterator.hasNext()) {
                if (treeIterator.next().compareTo(fromElement) >= 0 && treeIterator.next().compareTo(toElement) < 0)
                    newSet.add(treeIterator.current.value);
            }
        }
        return newSet;
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) throws IllegalArgumentException, NullPointerException {
        return subSet(null, toElement);
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement)throws IllegalArgumentException, NullPointerException {
        return subSet(fromElement, null);
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
