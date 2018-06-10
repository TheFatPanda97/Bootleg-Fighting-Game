//used to store high scores
public class Stack {

    private int size = 0;
    public Node top;

    public boolean isEmpty() {

        return top == null;

    }

    public void push(String n, int s) {

        size++;
        Node node = new Node(n, s, top);
        top = node;

    }

    //pops every node out of the stack
    public void clear() {

        for (int i = size; i > 0; i--) {

            pop();

        }

    }

    private void pop() {

        if (!isEmpty()) {

            size--;
            top = top.next;

        }

    }

}
