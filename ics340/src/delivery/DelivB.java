package delivery;
import java.io.*;
import java.util.ArrayList;
import compute.Computation_B;
import graph.Graph;



//Class DelivB does the work for deliverable DelivB of the Prog340

/**
 * @author Say Chaleon Vang
 * ICS 340-02 Algorithms and Data Structures
 * September 30, 2023
 * 
 * DelivB manages the input and output.
 * DelivB only works with DAG with no cycles!!!!!
 * Creates a Computation_B object initialized with the Graph data
 * Outputs to a text file and the console
 *
 */
public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	//Object to traverse the graph
	Computation_B computation_B;
	
	/**
	 * Constructor
	 * @param File object
	 * @param Graph object
	 */
	public DelivB( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		//Object to store paths
		ArrayList<String> paths = new ArrayList<>();
		String str0 = "***There are no edges in the graph***";
		//A check on graph opening                                    
		if(g == null) {
			System.out.println("***Error opening graph file***");    
		}
		//Check to see if graph has no edges                          
		if(!g.getEdgeList().isEmpty()) {
			
			/*** I simply could not get DelivB to work with the larger graphs ***/
			
			if(g.getNodeList().size()>14) {
				System.out.println("\n***Deliv_B will not work with graphs with\n"
						+ "with more than 14 nodes.Because out of memory error.");
				System.out.println("Please load new graph\n");
				//System.exit(0);
			}
			// Initialize Computation_B object with a Graph object
			else {
				computation_B = new Computation_B(g);
				if(computation_B.getStart() == null || computation_B.getGoal() == null) {
					System.out.println("Error, not able to set start and or goal node!\nRe-select graph file.");
				}
				else {
					//computation_B.nodeListToString(computation_B.getNodeList());
					/* 
					 * This is the call to traverse the graph bottom up.
					 * To get a string representation of the number of paths
					 * and all the paths from (S) to (G).
					 */
					paths = computation_B.getPath();
				
					String str1 = "There is " + paths.size() + " way to go from " 
							+ computation_B.getStart().getAbbrev() + " to " + computation_B.getGoal().getAbbrev() + ": ";
					String str2 = "\nThere are " + paths.size() + " ways to go from " 
							+ computation_B.getStart().getAbbrev() + " to " + computation_B.getGoal().getAbbrev() + ":\n";
					
					//If there is exactly one path
					if(paths.size() == 1){
						paths.add(0, str1);
					}
					//More than 1 path
					else {
						paths.add(0, str2);
					}
				}
			}
		}
		//If no edges
		else {
			paths.add(0, str0);
		}
	
		// Get output file name.
		String inputFileName = inputFile.toString();
		 // get the file name, Strip off ".txt"
		String baseFileName = 
				inputFileName.substring( 0, inputFileName.length()-4 );
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For re-tests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);
			for(String line: paths) {
				output.println(line);
				System.out.println(line);
			}
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		output.flush();
	}
}//end DelivB
