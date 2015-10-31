/**
 * Created by Mounica on 2/25/15.
 */
public class EdgeData {
    private int flow = 0;
    private final int capacity;

    public Edge forwardEdge;
    public Edge backwardEdge;

    public EdgeData(int capacity, Vertex v, Vertex w, String name){

        this.capacity = capacity;

        this.forwardEdge = new Edge(v, w, capacity - flow, "forward");
        this.backwardEdge = new Edge(w, v, flow, "backward");
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
        setForward();
        setBackward();
    }

    public int getCapacity() {
        return capacity;
    }


    public void setForward(){
        forwardEdge.setData(capacity-flow);
    }

    public void setBackward(){
        backwardEdge.setData(flow);
    }

    public Integer getForwardCapacity(){
        return (Integer) forwardEdge.getData();
    }

    public Integer getBackwardCapacity() {
        return (Integer) backwardEdge.getData();
    }

    public boolean hasForwardEdge(){
        return !forwardEdge.getData().equals(0);
    }

    public boolean hasBackwardEdge(){
        return !backwardEdge.getData().equals(0);
    }
}
