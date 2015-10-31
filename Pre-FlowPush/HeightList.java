import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Mounica
 */
public class HeightList {
    private int itemCount;
    private int maxHeightIndex;
    private HashMap<Integer, LinkedList<Vertex>> list;

    public int getMaxHeightIndex() {
        return maxHeightIndex;
    }

    public HeightList(int size){
        this.itemCount = 0;
        this.maxHeightIndex = 0;

        list = new HashMap<Integer, LinkedList<Vertex>>();
    }

    public boolean isEmpty(){
        return itemCount == 0;
    }

    public void add(int index, Vertex v){
        if(!list.containsKey(index)) {
            LinkedList<Vertex> linkedList = new LinkedList<Vertex>();
            linkedList.add(v);
            list.put(index, linkedList);
        } else{
            LinkedList<Vertex> linkedList = list.get(index);
            linkedList.add(v);
            list.put(index, linkedList);
        }
        if(index > maxHeightIndex){
            maxHeightIndex = index;
        }
        itemCount++;
    }

    public void remove(int index, Vertex v){
        if(list.containsKey(index)) {
            LinkedList<Vertex> linkedList = list.get(index);
            linkedList.remove(v);

            if(maxHeightIndex == index && list.get(index).size() == 0) {
                int max = 0;
                for(int i: list.keySet()){
                    if(i > max){
                        max = i;
                    }
                }

                maxHeightIndex = max;

            }
            itemCount--;
        }
    }

    public boolean contains(int index, Vertex v){

        if(!list.containsKey(index)){
            return false;
        }

        LinkedList<Vertex> linkedList = list.get(index);
        return linkedList.contains(v);
    }

    public Vertex getFirst(int index){

        LinkedList<Vertex> linkedList = list.get(index);
        return linkedList.getFirst();
    }

    public int getListSize(int index){
        LinkedList<Vertex> linkedList = list.get(index);
        return linkedList.size();
    }

}
