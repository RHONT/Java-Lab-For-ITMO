package Lab_2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TreeNode implements PriorityQueue {
    Node root;
    private int size = 0;
    private int minElement = 0;

    private int[] array;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public TreeNode() {
    }

    public void AltInsert(int i) {
        this.root = getUpdateTree(root, i);
    }

    public int AltDeleteMax() {
        int result = root.value;
        this.root = getUpdateTreeAfterDelete(root);
        return result;
    }

    private Node getUpdateTreeAfterDelete(Node root) {
        // если в дереве только один элемент
        if (root.seeInto() == 0) {
            return null;
        }
        // если есть только один потомок
        if (root.seeInto() == 1) {
            return getNextOneNode(root);
        }
        // копируем ссылку root
        // создаем ноду для движения в сторону макс. вершин
        Node stepBackNode;
        Node tail = root;
        while (tail != null) {

            stepBackNode = tail;
            if (tail.seeInto() == 2) {
                if (tail.right.value > tail.left.value) {
                    if (swapValueAndCheckNext(tail, tail.right)) {
                        stepBackNode.right = null;
                        break;
                    } else tail = tail.right;

                } else {
                    if (swapValueAndCheckNext(tail, tail.left)) {
                        stepBackNode.left = null;
                        break;
                    } else tail = tail.left;
                }
                continue;
            }

            if (tail.left != null) {
                if (swapValueAndCheckNext(tail, tail.left)) {
                    stepBackNode.left = null;
                    break;
                } else tail = tail.left;
            } else {
                if (swapValueAndCheckNext(tail, tail.right)) {
                    stepBackNode.right = null;
                    break;
                } else tail = tail.right;
            }
        }
        return root;
    }

    private void swapValue(Node tail, Node current) {
        int temp;
        temp = tail.value;
        tail.value = current.value;
        current.value = temp;
    }

    private boolean swapValueAndCheckNext(Node tail, Node current) {
        int temp;
        temp = tail.value;
        tail.value = current.value;
        current.value = temp;
        return current.seeInto() == 0;
    }

    // Если при удалении только один потомок
    private Node getNextOneNode(Node root) {
        if (root.right == null) {
            size--;
            return root.left;
        } else {
            size--;
            return root.right;
        }
    }

    // определяем какой из детей больше. Знак "=" отдается в приоритет левой ветке.
    private Node getMaxNode(Node root) {
        if (root.right.value > root.left.value) return root.right;
        else return root.left;
    }


    private Node getUpdateTree(Node root, int i) {
        Node insertNode = new Node(i);

        Node tail = root;
        Node tempNodeX = tail;


        if (tail == null) {
            return new Node(i);
        }
        while (true) {
            tempNodeX = tail;

            if (tempNodeX.left != null && tempNodeX.right != null) {

                int toLeft = tempNodeX.left.value - insertNode.value;
                int toRigth = tempNodeX.right.value - insertNode.value;

                if (toLeft < 0 && toRigth < 0) {
                    findLeftNullPos(tempNodeX.left, insertNode);
                    return root;
                }

                if (toRigth >= 0 && toLeft > 0) {
                    if (toLeft <= toRigth) {
                        tail = tail.left;
                    } else tail = tail.right;
                } else if (toRigth > 0) {
                    tail = tail.right;
                } else tail = tail.left;

            } else break;

        }

        if (insertNode.value <= tempNodeX.value) {
            if (tempNodeX.right == null) tempNodeX.right = insertNode;
            else tempNodeX.left = insertNode;
        }
        return root;
    }

    // что делает этот метод?
    private void findLeftNullPos(Node nodeStart, Node insertNode) {
        int remember = nodeStart.value;
        nodeStart.value = insertNode.value;


        if (nodeStart.left == null) {
            nodeStart.left = new Node(remember);
            return;
        }
        while (!checkDeep(nodeStart)) {


            Node left = nodeStart.left;
            Node right = nodeStart.right;

            if (!checkDeep(left) && !checkDeep(right)) {
                int temp = left.value;
                left.value = remember;
                remember = temp;
            } else if (checkDeep(left)) {
                int temp = left.value;
                left.value = remember;
                left.left = new Node(temp);
                return;
            } else if (checkDeep(right)) {
                int temp = right.value;
                right.value = remember;
                right.left = new Node(temp);
                return;
            }
            nodeStart = nodeStart.left;

        }
        int temp = nodeStart.value;
        nodeStart.value = remember;
        nodeStart.left = new Node(temp);
    }

    private boolean checkDeep(Node nodeForCheck) {
        return nodeForCheck.left == null;
    }


    public void setRoot(Node root) {
        this.root = root;
    }

    public boolean full() {
        return size == 10;
    }

    public boolean empty() {
        return size == 0;
    }

    public void insert(int value) {
        lock.lock();
        try {
            while (size > 10) {
                condition.await();
            }
            root = addRecursive(root, value);
            condition.signalAll();
            size++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        // если новое значение больше текущей головы, создаем новую голову и цепляем к ней старую в правую позицию
        if (value > current.value) {
            Node head = new Node(value);
            head.right = current;
            return head;
        }

        //todo сделать сравнение вершин на больше-меньше
        if (current.right != null && current.left != null) {

            if (current.left.value != current.right.value) {

                Node compareNode = findMinNode(current.left, current.right, value);
                compareNode = addRecursive(compareNode, value);

            } else {
                if (current.left.seeInto() > current.right.seeInto()) {
                    current.right = addRecursive(current.right, value);
                } else current.left = addRecursive(current.left, value);
            }

        } else if (current.right != null) {
            current.left = addRecursive(null, value);

        } else
            current.right = addRecursive(null, value);


        return current;
    }

    private Node findMinNode(Node left, Node right, int value) {
        if (left.value > right.value && right.value > value) {
            return right;
        }
        return left;
    }

    public int deleteMax() {
        int result = -1;
        lock.lock();

        try {
            while (empty()) {
                condition.await();
            }
            result = afterDeleted(root);
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }

    private int afterDeleted(Node root) {
        int result = root.value;

        if (root.right == null && root.left == null) {
            this.setRoot(null);
            size--;
            return result;
        }
        if (root.right != null && root.left != null) {
            // находим минимальный элемент и кидаем его наверх
            this.root = traverseLevelOrder(this.root);
            this.root.value = minElement;
            this.root = rebaseTreeRecursive(root, minElement);
            return result;
        }

        if (root.right == null) {
            size--;
            this.setRoot(root.left);
        } else {
            size--;
            this.setRoot(root.right);
        }
        return result;

    }

    //todo
    private Node rebaseTreeRecursive(Node root, int element) {
        if (root.right == null && root.left == null) {
            return root;
        }

        if (root.right != null && root.left != null) {
            if (root.right.value >= root.left.value) {
                root.value = root.right.value;
                root.right.value = element;
                rebaseTreeRecursive(root.right, root.right.value);
            } else {
                root.value = root.left.value;
                root.left.value = element;
                rebaseTreeRecursive(root.left, root.left.value);
            }
        } else if (root.right != null) {
            root.value = root.right.value;
            root.right.value = element;
            rebaseTreeRecursive(root.right, root.right.value);
        } else {
            root.value = root.left.value;
            root.left.value = element;
            rebaseTreeRecursive(root.left, root.left.value);
        }

        return root;
    }

    // поиск по ширине возвращаем последнюю активную ноду
    // массив, что мы тут строим не актуален, так как устаревает.
    public Node traverseLevelOrder(Node root) {
        if (root == null) {
            return null;
        }
        int incr = size - 1;
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        Node node;


        while (!nodes.isEmpty()) {
            node = nodes.remove();


            if (node.left != null) {
                nodes.add(node.left);
                incr--;
                if (incr == 0) {
                    minElement = node.left.value;
                    node.left = null;
                }
            }

            if (node.right != null) {
                nodes.add(node.right);
                incr--;
                if (incr == 0) {
                    minElement = node.right.value;
                    node.right = null;
                }

            }
        }
        size--;
        return root;
    }

    public int[] buildResultArray() {
        if (size == 0) {
            return null;
        }

        this.array = new int[size];
        int incr = 0;
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        Node node;

        while (!nodes.isEmpty()) {

            node = nodes.remove();
            array[incr++] = node.value;

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }
        return array;
    }

    public int[] getArray() {
        return array;
    }


}
