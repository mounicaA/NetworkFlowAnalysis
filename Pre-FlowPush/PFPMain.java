import java.util.Iterator;

/**
 * @author Mounica
 */
public class PFPMain {

    private SimpleGraph graph;
    private HeightList heights;

    public PFPMain(SimpleGraph graph){
        this.graph = graph;
        int n = graph.numVertices();
        heights = new HeightList(n*n+1);

        initialize();
    }

    public int preflow_push(){
        int valueOfFlow = 0;
        boolean canPush = false;
        boolean canRelabel = false;
        Vertex v = heights.getFirst(heights.getMaxHeightIndex());

        while(!heights.isEmpty()) {

            VertexData vData = (VertexData) v.getData();
            Node current = vData.current;

            while(vData.hasExcess()) {
                while (current != null) {
                    Edge e = current.getEdge();
                    EdgeData eData = (EdgeData) e.getData();
                    Vertex w = graph.opposite(v, e);
                    VertexData wData = (VertexData) w.getData();

                    canPush = push(v, vData, w, wData, e, eData);

                    if (canPush) {
                        if (!vData.hasExcess()) {
                            if (eData.getCapacity() != eData.getFlow()) {
                                break;
                            } else {
                                if (vData.current.next != null) {
                                    vData.current = vData.current.getNext();
                                }
                                break;
                            }
                        }
                    }

                    current = current.getNext();
                }

                if (!canPush) {
                    canRelabel = relabel(v, vData);
                    if (canRelabel) {
                        vData.current = vData.edgeLinkedList.getFirst();
                        current = vData.current;
                    } else {
                        break;
                    }
                }
            }

            if(heights.isEmpty()){
                break;
            }
            v = heights.getFirst(heights.getMaxHeightIndex());
        }

        Iterator vertexIterator = graph.vertices();
        while(vertexIterator.hasNext()){
            Vertex p = (Vertex) vertexIterator.next();
            if(p.getName().equals("s")){
                Iterator edgeIterator = graph.incidentEdges(p);
                while(edgeIterator.hasNext()){
                    Edge e = (Edge) edgeIterator.next();
                    EdgeData eData = (EdgeData) e.getData();
                    valueOfFlow += eData.getFlow();
                }
            }
        }

        return valueOfFlow;
    }

    private void addToHeightsList(int height, Vertex v){
        VertexData vData = (VertexData) v.getData();
        if(!v.getName().equals("t") && vData.hasExcess()) {
            heights.add(height, v);
        }
    }

    private void moveToNewHeight(int prevHeight, int newHeight, Vertex v){
        heights.remove(prevHeight, v);
        if(!v.getName().equals("t")) {
            heights.add(newHeight, v);
        }
    }

    private void modifyHeightsList(Vertex v, VertexData vData){
        int height = vData.getHeight();
        if(!vData.hasExcess() && heights.contains(height, v)){
            removeFromHeightsList(height, v);
        } else{
            if(!heights.contains(height, v)){
                addToHeightsList(height, v);
            }
        }

    }

    private void removeFromHeightsList(int height, Vertex v){
        heights.remove(height, v);
    }

    private boolean relabel(Vertex v, VertexData vData){
        if(vData.getExcess() > 0){
            Iterator<Edge> edgeIterator = (Iterator<Edge>) graph.incidentEdges(v);
            while(edgeIterator.hasNext()){
                Edge e = edgeIterator.next();
                EdgeData eData = (EdgeData) e.getData();
                Vertex w = graph.opposite(v, e);
                VertexData wData = (VertexData) w.getData();

                if(e.getSecondEndpoint().equals(v)){
                    if(!eData.hasBackwardEdge()){
                        continue;
                    }
                } else if(e.getFirstEndpoint().equals(v)){
                    if(!eData.hasForwardEdge()){
                        continue;
                    }
                }

                if(!(wData.getHeight() >= vData.getHeight())){
                    return false;
                }
            }

            moveToNewHeight(vData.getHeight(), vData.getHeight() + 1, v);
            vData.setHeight(vData.getHeight() + 1);
            return true;
        }
        return false;
    }

    private boolean push(Vertex v, VertexData vData, Vertex w, VertexData wData, Edge e, EdgeData eData){
        if(!(vData.getExcess() > 0 && wData.getHeight() < vData.getHeight())){
            return false;
        }
        int delta;
        if(e.getFirstEndpoint().equals(v)){
            if(eData.hasForwardEdge()){
                delta = Math.min(vData.getExcess(), eData.getForwardCapacity());
                eData.setFlow(eData.getFlow() + delta);
                modifyExcess(v, e, vData, eData);
                return true;
            }
        } else if(e.getSecondEndpoint().equals(v)){
            if(eData.hasBackwardEdge()){
                delta = Math.min(vData.getExcess(), eData.getFlow());
                eData.setFlow(eData.getFlow() - delta);
                modifyExcess(v, e, vData, eData);
                return true;
            }
        }

        return false;

    }

    private void initialize(){
        Iterator<Vertex> it = (Iterator<Vertex>) graph.vertices();
        Iterator<Edge> edgeIterator = (Iterator<Edge>) graph.edges();

        while(edgeIterator.hasNext()){
            Edge e = edgeIterator.next();
            Double data = (Double) e.getData();
            int capacity = (int) data.doubleValue();
            String name = (String) e.getName();
            EdgeData eData = new EdgeData(capacity, e.getFirstEndpoint(), e.getSecondEndpoint(), name);
            e.setData(eData);
        }

        while(it.hasNext()){
            Vertex v = it.next();
            Iterator<Edge> edgeIteratorV = (Iterator<Edge>) graph.incidentEdges(v);
            VertexData vData = new VertexData(edgeIteratorV);

            if(v.getName().equals("s")){
                continue;
            }

            vData.setHeight(0);
            v.setData(vData);
        }

        it = (Iterator<Vertex>) graph.vertices();
        while(it.hasNext()) {
            Vertex v = it.next();
            Iterator<Edge> edgeIteratorV = (Iterator<Edge>) graph.incidentEdges(v);
            VertexData vData = new VertexData(edgeIteratorV);

            if (v.getName().equals("s")) {
                v.setData(vData);
                initializeSource(v, vData);
                break;
            }
        }

        edgeIterator = (Iterator<Edge>) graph.edges();

        while(edgeIterator.hasNext()){
            Edge e = edgeIterator.next();
            EdgeData eData = (EdgeData) e.getData();

            if(!(e.getFirstEndpoint().getName().equals("s") || e.getSecondEndpoint().getName().equals("s"))){
                eData.setFlow(0);
            }
        }
    }

    private void modifyExcess(Vertex v, Edge e, VertexData vData, EdgeData eData){
        Vertex w = graph.opposite(v, e);

        int excess = eData.getFlow();

        VertexData wData = (VertexData) w.getData();

        vData.setExcess(vData.getExcess() - excess);
        wData.setExcess(wData.getExcess() + excess);

        modifyHeightsList(v, vData);
        modifyHeightsList(w, wData);
    }

    private void initializeSource(Vertex v, VertexData vData){
        vData.setHeight(graph.numVertices());
        int valueOfFlow = 0;

        Iterator<Edge> edgeIterator = (Iterator<Edge>) graph.incidentEdges(v);
        while(edgeIterator.hasNext()){
            Edge e = edgeIterator.next();
            EdgeData eData = (EdgeData) e.getData();
            eData.setFlow(eData.getCapacity());
            modifyExcess(v, e, vData, eData);
            valueOfFlow += eData.getFlow();
        }
        if(vData.hasExcess()) {
            addToHeightsList(graph.numVertices(), v);
        }
        valueOfFlow = -1*valueOfFlow;
        vData.setExcess(valueOfFlow);
        v.setData(vData);
    }

    public static void main(String[] args) {
        SimpleGraph G = new SimpleGraph();
        Vertex s = null;
        Vertex t = null;
        Iterator itr;

        System.out.print("Please enter the full path of input graph: ");
        String userinput;
        userinput = KeyboardReader.readString();
        GraphInput.LoadSimpleGraph(G,userinput);

        long startTime=System.currentTimeMillis();
        PFPMain runPFPMain = new PFPMain(G);
        int flow = runPFPMain.preflow_push();
        long endTime=System.currentTimeMillis();

        System.out.println("The max flow of this graph is " + flow);
        System.out.println("PFP's running time is " + (endTime - startTime) + "ms");

    }

}

