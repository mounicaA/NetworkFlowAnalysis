/**
 * TCSS543 Winter 2015 Empirical Study Project
 * The information associated with vertices
 * @author Xavier Xu
 */
public class FFVertexInfo{

    private boolean visited;

    public FFVertexInfo(boolean visited){
        this.visited = visited;
    }	
    
    public FFVertexInfo(){
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}