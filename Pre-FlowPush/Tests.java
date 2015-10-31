import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author Mounica Arroju
 * @version v1
 */
public class Tests {
    Vertex p, q, s, u, v, t;
    Edge e, su, sv, uv, ut, vt;
    EdgeData e1, e2, e3, e4, e5;
    SimpleGraph graph;
    PFPMain runPFPMain;

    public Tests(){

        graph = new SimpleGraph();

        s = graph.insertVertex(null, "s");
        u = graph.insertVertex(null, "u");
        e1 = new EdgeData(20, s, u, "su");
        su = graph.insertEdge(s, u, e1, "su");

        t = graph.insertVertex(null, "t");
        e2 = new EdgeData(10, u, t, "st");
        ut = graph.insertEdge(u, t, e2, "ut");

        v = graph.insertVertex(null, "v");
        e3 = new EdgeData(10, s, v, "sv");
        sv = graph.insertEdge(s, v, e3, "sv");

        e4 = new EdgeData(20, v, t, "vt");
        vt = graph.insertEdge(v, t, e4, "vt");

        e5 = new EdgeData(30, u, v, "uv");
        uv = graph.insertEdge(u, v, e5, "uv");

    }

//    public void testInitializeSource(){
//        Vertex vert;
//        while(graph.vertices().hasNext()){
//            vert = (Vertex) graph.vertices().next();
//            if(vert.getName() == "s"){
//                break;
//            }
//        }
//        //runPFP.initializeSource(v, new VertexData());
//
//    }
    @Test
    public void testPFP(){
        runPFPMain = new PFPMain(graph);
        assertEquals(30, runPFPMain.preflow_push());
    }
}
