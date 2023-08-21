package Lab_2;

class Node {
    int value;
    Node left;
    Node right;

    Node(int value) {
        this.value = value;
        right = null;
        left = null;
    }

    public int seeInto() {
        if (left == null && right == null) {
            return 0;
        }
        if (right != null && left != null) {
            return 2;
        } else return 1;
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
