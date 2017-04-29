/* Class BTNode */
class BTNode {
    private BTNode left, right;
    private char data;

    /* Constructor */
    public BTNode() {
        this.left = null;
        this.right = null;
        this.data = ' ';
    }

    /* Constructor */
    public BTNode(char data) {
        this.left = null;
        this.right = null;
        this.data = data;
    }

    /* Function to set left node */
    public void setLeft(BTNode n) {
        this.left = n;
    }

    /* Function to set right node */
    public void setRight(BTNode n) {
        this.right = n;
    }

    /* Function to get left node */
    public BTNode getLeft() {
        return left;
    }

    /* Function to get right node */
    public BTNode getRight() {
        return right;
    }

    /* Function to set data to node */
    public void setData(char d) {
        this.data = d;
    }

    /* Function to get data from node */
    public int getData() {
        return data;
    }
}