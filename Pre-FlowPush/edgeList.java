/**
 * Created by Mounica on 3/9/15.
 */
public class edgeList {
    private Node head;

    /**
     * Gets the Node  for a given Edge
     *
     * @param Edge
     * @return  for a search hit or null for a search miss
     */
    public Node get(Edge Edge) {
        Node current = head;

        while (current != null) {
            if (Edge.equals(current.Edge)) {
                return current;
            }

            current = current.next;
        }

        return null;
    }

    public Node getFirst(){
        return head;
    }

    /**
     * puts a Edge- pair at the beginning of the list
     *
     * @param Edge
     */
    public void put(Edge Edge) {
        for (Node current = head; current != null; current = current.next) {
            if (Edge.equals(current.Edge)) {
                return;
            }
        }
        head = new Node(Edge, head);
    }

    /**
     * Lazy deletion of the Node with a given Edge
     *
     * @param Edge given Edge
     * @throws NullPointerException if the given Edge is not found in the list
     */
    public void delete(Edge Edge) {

        Node currentPrevious = null;
        Node current = head;

        while (current != null) {
            if (Edge.equals(current.Edge)) {


                if (current == head) {
                    head = head.next;
                    current.next = null;
                    return;
                } else {

                    currentPrevious.next = current.next;
                    current.next = null;

                    return;
                }
            }

            currentPrevious = current;
            current = current.next;
        }

        throw new NullPointerException("The given Edge is not present in the list!");
    }


}
