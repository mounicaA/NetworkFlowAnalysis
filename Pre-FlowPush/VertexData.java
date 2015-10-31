import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Mounica on 3/6/15.
 */
public class VertexData {
    private int height;
    private int excess;
    public Node current;
    //Is this needed?
    public edgeList edgeLinkedList;


    public VertexData(Iterator<Edge> edgeIterator){
        this.height = 0;
        this.excess = 0;

        edgeLinkedList = new edgeList();



        while(edgeIterator.hasNext()){
            Edge e = edgeIterator.next();
            edgeLinkedList.put(e);
        }

        this.current = edgeLinkedList.getFirst();
    }

//    public Iterator residualEdges(){
//        return edgeLinkedList.iterator();
//    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getExcess() {
        return excess;
    }

    public void setExcess(int excess) {
        this.excess = excess;
    }

    public boolean hasExcess(){
        return excess > 0;
    }
}
