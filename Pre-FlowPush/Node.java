/**
 * Class definition for a Node to store the Edge pairs
 */
public class Node {
    Edge Edge;
    Node next;

    public Node(Edge Edge, Node nextNode) {
        this.Edge = Edge;
        this.next = nextNode;
    }

    public Edge getEdge(){
        return this.Edge;
    }

    public Node getNext(){
        return this.next;
    }
}
