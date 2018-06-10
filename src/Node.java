//used to stores individual information about a high score
public class Node {

    public String name;//high score winner name
    public int score;//high score
    public Node next;


    public Node(String n, int s, Node ne) {

        name = n;
        score = s;
        next = ne;

    }

}
