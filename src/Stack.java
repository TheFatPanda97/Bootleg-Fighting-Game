public class Stack {

    int size = 0;
    Node top;

    boolean isEmpty() {

        return top == null;

    }


    void push(String n, int s) {

        size++;
        Node node = new Node(n, s, top);
        top = node;

    }

//    void push(Node node) {
//
//        size++;
//        node.next = top;
//        top = node;
//
//    }

    void clear() {

        for (int i = size; i > 0; i--) {

            pop();

        }

    }

    void pop() {

        if (!isEmpty()) {

            size--;
            top = top.next;

        }

    }

//    String peep() {
//
//        if (!isEmpty()) {
//
//            return top.cargo;
//
//
//        }
//
//        return null;
//
//    }

}
