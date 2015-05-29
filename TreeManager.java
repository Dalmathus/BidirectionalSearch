import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TreeManager {

	public List<Tree> TreeList;
	public List<Node> finalpath;
	public boolean testBool = true;
	
	public TreeManager(){
		TreeList = new ArrayList<Tree>();
		finalpath = new ArrayList<Node>();
	}
	
	/**
	 * 
	 * @param i is a record of which board the search is running on currently
	 */
	public void BiSearch(int i){
		
		int direction = 0;
		
		final long startTime = System.currentTimeMillis();
		
		while(testBool){
		
			direction ^= 1;
			
			TreeList.get(direction).searchNext();
			long endTime = System.currentTimeMillis();
			long timePassedInSeconds = TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.MILLISECONDS);
			
			if (timePassedInSeconds >= 60){
				testBool = false;
			}
		}		
		
		long endTime = System.currentTimeMillis();
		long timePassedInSeconds = TimeUnit.SECONDS.convert((endTime - startTime), TimeUnit.MILLISECONDS);
		
		long timePassedInMilSeconds = endTime - startTime;
		
		if (timePassedInSeconds >= 60){
			System.out.println("Board state: " + i + " solution not found in time");
		}		
		else if (timePassedInSeconds == 0){
			System.out.println("Board state: " + i + " "
					+ "Depth solution found at: " + (finalpath.size() - 1) 
					+ " after " + nodes() + " unique configurations were created after " 
					+ timePassedInMilSeconds + " milliseconds.");	
			//printList();			
		}
		else{
			System.out.println("Board state: " + i + " "
					+ "Depth solution found at: " + (finalpath.size() - 1) 
					+ " after " + nodes() + " unique configurations were created after " 
					+ timePassedInSeconds + " seconds.");	
			//printList();			
		}
		
		//remove the comment if you want to see the path solution to the board state in the above conditionals 
	}
	
	public void createTree(Node n, int d){
		Tree t = new Tree(n, this, d);
		TreeList.add(t);
	}	
	
	public void stopSearchingNode(Node x, int xd, Node y, int yd){
		
		testBool = false;

		if (xd == 0){				
			finalpath.add(x);
			createList(x, xd);
			reverseList();
			createList(y, yd);
		}
		else {
			finalpath.add(y);
			createList(y, yd);
			reverseList();			
			createList(x, xd);			
		}
	}
	
	public void reverseList(){
		
		List<Node> tempList = new ArrayList<Node>();
		
		for (int i = (finalpath.size() - 1); i >= 0; i--){
			tempList.add(finalpath.get(i));
		}		
		finalpath = tempList;
	}
	
	public void createList(Node n, int d){
		
		while (true){
			if (n.equals(TreeList.get(d).path.get(0))){
				break;
			}
			finalpath.add(n.parent);
			n = n.parent;			
		}
	}
	
	public void stopSearching(int hash){
		
		testBool = false;
		
		Node n = TreeList.get(0).ht.get(hash);	
		
		while (true){
			if (n.equals(TreeList.get(0).path.get(0))){
				finalpath.add(n);
				break;
			}
			finalpath.add(n.parent);
			n = n.parent;			
		}	
		
		n = TreeList.get(1).ht.get(hash);
		
		while (true){
			if (n.equals(TreeList.get(1).path.get(0))){
				break;
			}
			finalpath.add(n.parent);
			n = n.parent;			
		}		
	}
	
	public void printList(){
		for (Node n : finalpath){
			n.PrintConfig();
		}
		System.out.println();
	}
	
	public int nodes(){
		return (TreeList.get(0).ht.size() + TreeList.get(1).ht.size());
	}
	
	public void appendLists(){
		for (Node n : TreeList.get(0).path){
			finalpath.add(n);
		}		
		for (Node n : TreeList.get(1).path){
			finalpath.add(n);
		}
	}
}
