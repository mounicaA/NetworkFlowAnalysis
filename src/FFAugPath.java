/**
 * TCSS543 Winter 2015 Empirical Study Project
 * Find a augmenting path from given graph
 * @author Xavier Xu
 */
import java.util.Iterator;

public class FFAugPath {
    static int maxFlow; 
    static boolean isPath = false;
   
    public FFAugPath() {}
    /**
     * Find the augment path from s to t using depth first search
     * @param graph SimpleGraph Input
     * @param s start point
     * @param t end point
     * @param path empty SimpleGraph to store path
     * @param d scaling parameter used in ScalingFF
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static SimpleGraph findAngPath (SimpleGraph graph, Vertex s, Vertex t, SimpleGraph path, int d) {
        Vertex vert;
        Edge edge;
        Iterator itr;
        SimpleGraph newPath;
        isPath = false;

        FFVertexInfo data = new FFVertexInfo(true);
        s.setData(data);	
        path.vertexList.add(s);
        itr = graph.incidentEdges(s);
        
        while (itr.hasNext()) {
            edge = (Edge) itr.next();
            vert = graph.opposite(s, edge);

            if (!( (FFVertexInfo) vert.getData()).isVisited() &&
                    (vert == edge.getSecondEndpoint() && 
                    ((FFEdgeInfo) edge.getData()).getAvailable() > d ||
                    vert == edge.getFirstEndpoint() && 
                    ((FFEdgeInfo) edge.getData()).getFlow() > d)) {

                if (vert == t){
                    path.edgeList.add(edge);
                    path.vertexList.add(vert);
                    return path;
                }

                path.edgeList.add(edge);
                newPath = findAngPath(graph, vert, t, path, d);
                if (newPath.vertexList.getLast() == t) {
                    findMaxFlow((FFEdgeInfo) ((Edge) newPath.edgeList.getFirst()).getData());
                    setPath();
                    return newPath;
                }
                path.edgeList.removeLast();
            }
            ((FFVertexInfo)s.getData()).setVisited(true);
        }

        path.vertexList.removeLast();
        return path;
    }
    
    private static int findMaxFlow(FFEdgeInfo data) {
        maxFlow = maxFlow + data.getFlow();
        return maxFlow;
    }
    
    private static Boolean setPath() {
        return isPath = true;
    }
}
