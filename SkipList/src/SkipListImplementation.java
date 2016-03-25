import java.util.Random;

public class SkipListImplementation {

	public int size, maxLevel;
	Node head;

	SkipListImplementation(int size) {
		this.size = size;
		this.maxLevel = (int) Math.log(size);
	}

	public class Node {
		public int value;
		public Node[] next;
		Node(int value, int level){
			this.value=value;
			this.next=new Node[level];
		}
	}

	public Node[] find(int x) {
		Node p = head;
		Node[] prev = new Node[maxLevel];
		for (int i = maxLevel; i > 0; i--) {
			while (p.next[i].value > x) {
				p = p.next[i];
			}
			prev[i] = p;
		}
		return prev;
	}
	public boolean contains(int x){
		Node[] prev=find(x);
		return prev[0].next[0].value==x ?  true : false;
	}
	
	public int choice(int maxLevel){
		int i=0;
		boolean b=false;
		Random random=new Random();
		while(i<maxLevel){
			b=random.nextBoolean();
			if(b){
				i++;
			}else{
				break;
			}
		}
		return i;
	}
	public void add(int x){
		Node prev[]=find(x);
		if(prev[0].next[0]==null){
			prev[0].next[0].value=x;
		}
		else{
			int lev=choice(maxLevel);
			Node newNode=new Node(x,lev);
			for(int i=0; i<=lev;i++){
				newNode.next[i]=prev[i].next[i];
				prev[i].next[i]=newNode;
			}
		}
	}
	
	
	
	public Node remove(int x){
		Node prev[]=find(x);
		Node n=prev[0].next[0];
		if(!(n.value==x)){
			return null;
		}else{
			for(int i=0;i<maxLevel;i++){
				if(prev[i].next[i]==n){
					prev[i].next[i]=n.next[i];
				}else{
					break;
				}
			}
			return n;
		}
	}
	public static void main(String[] args) {
	SkipListImplementation obj=new SkipListImplementation(4);
	}

}
