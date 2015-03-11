/**
 * TCSS543 Winter 2015 Empirical Study Project
 * The information associated with edges
 * @author Xavier Xu
 */
public class FFEdgeInfo {

    private int capacity;
    private int flow;

    public FFEdgeInfo(){
        this.capacity = 1;
        this.flow = 0;
    }

    public FFEdgeInfo(int capacity, int flow) throws Exception{
        if(capacity < flow || flow < 0 || capacity < 0)
            throw new IndexOutOfBoundsException();
        this.capacity = capacity;
        this.flow = flow;
    }

    public int getCapacity(){
        return this.capacity;
    }
    
    public int getAvailable() {
        return this.capacity - flow;
    }
    
    public int getFlow(){
        return this.flow;
    }
    
    public void setFlow(int flow) throws Exception{
        if(this.capacity < flow || flow < 0)
            throw new IndexOutOfBoundsException();
        this.flow = flow;
    }
}
