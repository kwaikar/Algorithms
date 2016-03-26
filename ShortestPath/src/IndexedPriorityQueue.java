import java.util.ArrayList;
import java.util.List;


public class IndexedPriorityQueue {
	List<Vertex> queue=new ArrayList<Vertex>();
	IndexedPriorityQueue(){
	}
	public void insert(Vertex v){
		queue.add(v);
		percolateUp(queue.size()-1);
		//System.out.println(queue.get(queue.size()-1));
		}
		
	
		public void percolateUp(int i){
		Vertex element=queue.get(i);
		while(queue.get((i-1)/2).distanceObj.getDistance()<=element.distanceObj.getDistance()){
		queue.set(i, queue.get((i-1)/2));
		i=(i-1)/2;
		}
		queue.set(i, element);
	}
	
	public Vertex decreaseKey(){
	 	Vertex key=queue.get(0);
		return key;
	}
	public void percolateDown(int i){
		
	}
	
	public static void main(String args[]){
	}
	}
