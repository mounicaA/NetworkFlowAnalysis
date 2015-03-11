import java.util.Iterator;
import java.util.LinkedList;

/**
 * TCSS543 Winter 2015 Empirical Study Project
 * Ford-Fulkerson algorithm Implementation
 * @author Sven Berger
 * Based in large part on Ford_Fulkerson written by Xavier Xu
 */


public class ScalingMax {

    /**
     * Find the maximum flow using Ford-Fulkerson algorithm
     * @param graph input graph
     * @param s source
     * @param t sink
     * @return the max flow of the graph
     * @throws Exception
     */
    public static int maxFlow(SimpleGraph graph, Vertex s, Vertex t) throws Exception{

        int flow = 0;
        int current;
        int maxcap = 0;
        Vertex vert;
        Vertex prev;
        Iterator itr;
        Edge edge;
        FFVertexInfo vertInfo = new FFVertexInfo();
        FFEdgeInfo edgeInfo;
        SimpleGraph residualGraph;

        itr = graph.edges();
        while (itr.hasNext()) {
            edge = (Edge) itr.next();
            int edgeData = ((Double)edge.getData()).intValue();
            edgeInfo = new FFEdgeInfo(edgeData, 0);
            edge.setData(edgeInfo);
            maxcap = Math.max(maxcap, edgeInfo.getCapacity());
        }

        itr = graph.vertices();
        while (itr.hasNext()) {
            vert = (Vertex) itr.next();
            vertInfo.setVisited(false);
            vert.setData(vertInfo);
        }

        //delta starts at the largest power of 2 <= max capacity out of s
        int delta = maxIncidentFlow(s);

        while(delta>1){
            residualGraph = FFAugPath.findAngPath(graph, s, t, new SimpleGraph(), delta);
            while (residualGraph.numEdges() > 0) {

                current = maxcap;
                prev = s;
                itr = residualGraph.edges();

                while (itr.hasNext() ) {
                    edge = (Edge) itr.next();
                    edgeInfo = (FFEdgeInfo) edge.getData();
                    if (edge.getFirstEndpoint() == prev) {
                        current = Math.min(current, edgeInfo.getAvailable());
                        prev = edge.getSecondEndpoint();
                    } else {
                        current = Math.min(current, edgeInfo.getFlow());
                        prev = edge.getFirstEndpoint();
                    }
                }

                prev = s;
                itr = residualGraph.edges();
                while (itr.hasNext()) {
                    edge = (Edge) itr.next();
                    edgeInfo = (FFEdgeInfo) edge.getData();
                    if (edge.getFirstEndpoint() == prev){
                        edgeInfo.setFlow(edgeInfo.getFlow()+ current);
                        prev = edge.getSecondEndpoint();
                    } else {
                        edgeInfo.setFlow(edgeInfo.getFlow() - current);
                        prev = edge.getFirstEndpoint();
                    }
                }

                flow = flow + current;
                itr = graph.vertices();

                while (itr.hasNext()) {
                    vert = (Vertex) itr.next();
                    vertInfo = (FFVertexInfo) vert.getData();
                    vertInfo.setVisited(false);
                }

                residualGraph = FFAugPath.findAngPath(graph, s, t, new SimpleGraph(),0);
            }

            delta = delta/2;
        }
        return flow;
    }

    private static int maxIncidentFlow(Vertex s) throws Exception{
        int capacity = 2;
        FFEdgeInfo eInfo;
        Edge ed;
        LinkedList edgeList = s.incidentEdgeList;
        for (int i = 0; i < edgeList.size(); i++){
            ed = (Edge) edgeList.get(i);
            eInfo = (FFEdgeInfo) ed.getData();
            while(capacity < eInfo.getCapacity()) {
                capacity *= 2;
            }
            capacity /= 2;
        }
        return capacity;
    }


    public static void main(String[] args) throws Exception{
        SimpleGraph G = new SimpleGraph();
        Vertex s = null;
        Vertex t = null;
        Iterator itr;

        System.out.print("Please enter the full path of input graph: ");
        String userinput;
        userinput = KeyboardReader.readString();
        GraphInput.LoadSimpleGraph(G,userinput);

        itr= G.vertices();
        while (itr.hasNext()) {
            Vertex vert = (Vertex) itr.next();
            if (vert.getName().equals("s")) {
                s = vert;
            }else if (vert.getName().equals("t")) {
                t = vert;
            }
        }

        long startTime=System.currentTimeMillis();
        int flow = maxFlow(G, s, t);
        long endTime=System.currentTimeMillis();

        System.out.println("The max flow of this graph is " + flow);
        System.out.println("Scaling-Max running time is " + (endTime - startTime) + "ms");
    }

}