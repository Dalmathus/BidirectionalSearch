import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) throws IOException {
		
		// Reading into a file the board state at the provided number
		
		String absoluteFilePath = "";
		String workingDirectory = System.getProperty("user.dir");
		absoluteFilePath = workingDirectory + File.separator + "Positions.txt";				
		String file = readFile(absoluteFilePath, Charset.defaultCharset());		
		String[] sa = file.split("\\s+");
		
		int[] masterSet = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24};
		
		int i = 1;
		
		try {
			i = Integer.parseInt(args[0]);
		}
		catch(Exception ex){
			System.out.println("Please run the script script.bs only showing first board solution");
		}
		// Loop through all board states from 1..100 creating a new TreeManager each time
										
				Node n = new Node(getConf(sa, i));
				Node e = new Node(masterSet);		
				TreeManager tm = new TreeManager();		
				tm.createTree(n, 0);
				tm.createTree(e, 1);		
				tm.BiSearch(i);		// The board number is just passed here to print it our in the final report of the solution, it has no functionality inside the BiSearch Method
		}
		
	
	static int[] getConf(String[] ss, int board){
		
		int [] ia = new int[25];
		int o = 0;
				
		int boardNum = (27 * board) - 27;
		
		for (int i = (boardNum + 2) ; i <= (boardNum + 2) + 24; i++){
			ia[o] = (Integer.parseInt(ss[i]));
			o++;
		}						
		
		return ia;
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
}
