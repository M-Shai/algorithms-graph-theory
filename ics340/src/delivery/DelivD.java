package delivery;
import compute.Computation_D;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import graph.Graph;

/**
 * @author Say Chaleon Vang
 * 
 * *************************** IMPORTANT NOTES MUST READ ***********************************************
 * 			MUST USE MY Prog340, I UPDATED LINE 200 TO WORK WITH HeurNode Class.
 * 			I also updated it so that it can read in signed integers, Prog340 line 223
 * 
 * 		All class files needed for all DelivA to DelivD to work properly along with previous
 * 		files not listed here to run previous versions. I would recommend importing
 * 		package to eclipse. I have either created them previously or updated them to work with DelivD.
 * 			Computation_B.java
 * 			Computation_C.java
 * 			DelivB.java
 * 			DelivC.java
 * 			DelivD.java
 */

/**
 * Handles the graph input and outputs.
 * Calls Computation_D to do the graph computations.
 * Does graph validity checks.
 * Outputs to the console and text file.
 */
public class DelivD {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	/**
	 * DelivD()
	 * Constructor, Check to see if graph is valid.
	 * Creates Computation_D object for gr computations.
	 * Gets the resulting value matrices list.
	 * Gets the resulting previous matrices list.
	 * Prints out the result to the console.
	 * Prints results to a text file.
	 * @param File in
	 * @param Graph gr
	 */
	public DelivD( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		/* Check to see if graph is valid. */
		if(isGraphValid(gr)) {
			/* Create a Computation_D object. */
			Computation_D comp_d = new Computation_D(gr);
			/* Call to start the Floyd-Warshall Algorithm. */
			comp_d.Floyd_Warshall();
			/* Get the value array and the previous array. */
			List<Integer[][]> values = comp_d.getV_Matrix();
			List<String[][]> previous = comp_d.getP_Matrix();
			
			/* Loop to print out the results. */
			/*
			while(index < values.size()) {
				Integer [][] vm = values.get(index);
				String [][] pm = previous.get(index);
				comp_d.toString(index, comp_d.getSolutions(vm, pm), null);
				index++;
			}
			*/
			//ArrayList<ArrayList<String>> solutions = comp_d.getSolutions(vm, pm);
			
			
			// Get output file name.
			String inputFileName = inputFile.toString();
			String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
			String outputFileName = baseFileName.concat( "_out.txt" );
			outputFile = new File( outputFileName );
			if ( outputFile.exists() ) {    // For retests
				outputFile.delete();
			}
			
			try {
				output = new PrintWriter(outputFile);
				/* Iterates through the list arrays. Sends one of each value and previous arrays to be printed.
				 * Calls the getSolutions method. What that method does is formats a pair the pair of double arrays,
				 * the value and previous arrays, into one corresponding String double arrays. It then returns both in
				 * an ArrayList with both of them being the only two elements. Kind of like a tuple in Python.
				 * Calls the toString method, sends in the ArrayList/tuple, the k index and the PrintWrter object.
				 * All output will be handled in Computation_D. Output to console and to a text file.
				 * See Computation_D toString Method doc.
				*/
				for(int k = 0; k < values.size(); k++) {
					Integer [][] vm = values.get(k);
					String [][] pm = previous.get(k);
					if(comp_d.getNumVertices() >= 10) {
						k = comp_d.getNumVertices();
					}
					comp_d.toString(k, comp_d.getSolutions(vm, pm), output);
				}
			}
			catch (Exception x ) { 
				System.err.format("Exception: %s%n", x);
				System.exit(0);
			}
			System.out.println( "DelivD: implementation.");
			output.println( "DelivD: implementation.");
			output.flush();
		}
	}
	
	/**
	 * Method to check if graph is valid.
	 * Checks: If graph is null or node list is empty.
	 * Checks for empty edge list.
	 * or if the number of nodes of each graph is equal.
	 * Returns false if any of these are false.
	 * @param Graph g
	 * @return boolean
	 */
	public boolean isGraphValid(Graph g) {
		if(g == null || g.getNodeList().isEmpty()) {
			System.out.println("Error! The graph is not valid.");
			return false;
		}
		else if(g.getEdgeList().isEmpty()) {
			System.out.println("There are no edges in the graph.");
			return false;
		}
		else {
			return true;
		}
	}
}
