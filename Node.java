import java.util.Arrays;

public class Node {
	
	public Node parent;
	private int[] configuration;
	
	public Node(int[] config){
		parent = null;
		configuration = config;			
	}
	
	public Node(int[] config, Node p){
		parent = p;
		configuration = config;		
	}
	
	public void PrintConfig(){
		
		for (int k = 0; k <= 24; k++){
			if (k % 5 == 0) { System.out.println("");
			System.out.println(""); }
			System.out.print(configuration[k] + "\t");
		}
		
		System.out.println("");
	}	
	
	public int hashCode() {		
		return Arrays.hashCode(this.configuration);
	}
	
	public boolean equals(Object o) { 
		return Arrays.equals(this.configuration, ((Node) o).configuration);
	}
	
	public boolean hasParent(){
		if (!this.parent.equals(null)){
			return true;
		}
		else {
			return false;
		}		
	}
	
	public int[] getConfiguration(){
		return this.configuration;
	}
}
