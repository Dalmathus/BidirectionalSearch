
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;

public class Tree {
	public List<Node> path;	
	public Hashtable<Node, Node> ht;
	private Hashtable<Node, Node> oppHt;
	private Queue<Node> masterQueue;
	private Queue<Node> frontierQueue;
	private TreeManager ptm;
	private int oppDirection;
	
	/**
	 * Create an initial tree to start branching from 
	 * 	 
	 * @param s = Start State of the tree, either the finished grid for the backwards tree or the original puzzle  
	 * @param d = Direction, 0 == forward 1 == backward 
	 */
	public Tree(Node s, TreeManager tm, int d){
		ptm = tm;
		path = new ArrayList<Node>();
		ht = new Hashtable<Node, Node>();		
		ht.put(s, s);
		masterQueue = new ArrayDeque<Node>();
		frontierQueue = new ArrayDeque<Node>();
		masterQueue.add(s);	
		oppDirection = (d ^= 1);			
		path.add(s);
	}
	
	/**
	 * Search next takes the first node from the masterQueue and expands all its possible configurations and adds them to the frontier queue
	 * so they can each be individually tested
	 */
	public void searchNext(){
		
		Node CurrentNode = masterQueue.poll();
				
		int o = 0;
		
		for(int i : CurrentNode.getConfiguration()) {
			if (i == 0) break;
			o++;
		}
		
		if (o < 20) { 			frontierQueue.add(createNode(CurrentNode, o, o + 5)); }
		if (o > 4) { 			frontierQueue.add(createNode(CurrentNode, o, o - 5)); }
		if (o != 0
			&& o % 5 != 0 ) { 	frontierQueue.add(createNode(CurrentNode, o, o - 1)); }
		if ((o + 1) % 5 != 0) { frontierQueue.add(createNode(CurrentNode, o, o + 1)); } 
		
		nodeTest(); 
	}

	/**
	 * nodeTest takes the first item from the frontier queue and checks for collisions in the hash tables
	 * 
	 * as long as the frontierQueue has elements left in it the loop will continue to check
	 * 		- if the current tree hash table has this configuration already generated at a shallower or equal depth
	 * 		- if the opposite tree hash table has this configuration already generated meaning I can trace back to the goal state
	 * 		- if the current tree hash doesn't contain this configuration yet in which case I add this node to the master Queue to be expanded
	 */
	public void nodeTest(){

		oppHt = ptm.TreeList.get(oppDirection).ht;
		
		while (frontierQueue.size() > 0){
			Node n = frontierQueue.poll();
			
			if (oppHt.containsKey(n)){			
				
				Node testNode = oppHt.get(n);		

				int direction = oppDirection;
				direction ^= 1;

				ptm.stopSearchingNode(n, direction , testNode, oppDirection);
				break;
				
			}
			else if (!ht.containsKey(n)){
				ht.put(n, n);
				masterQueue.add(n);
			}
		}
	}
	
	/**
	 * creates a new node with a parent from to given value to swap
	 * 
	 * @param n
	 * @param origin
	 * @param swap
	 * @return
	 */
	public Node createNode(Node n, int origin, int swap){
		
		int[] newConf = n.getConfiguration().clone();	
		newConf[origin] = newConf[swap];
		newConf[swap] = 0;		
		Node newNode = new Node(newConf, n);
		return newNode;
			
	}
		
	public void printHashtable(){
		
		Enumeration<Node> g = ht.elements();
		Enumeration<Node> h = ht.keys();
		while (g.hasMoreElements()){
			System.out.print(h.nextElement().toString() + " | ");
			
		}
	}
	
	public void printArray(int[] ia){
		
		System.out.print("{ ");
		
		for (int i : ia){
			if(i == ia[24]) { System.out.print(i); break; }
			System.out.print(i + ", ");
		}
		
		System.out.print("}");
		System.out.println();
	}	
}
