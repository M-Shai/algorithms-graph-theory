package delivery;
import java.io.*;

import compute.Computation_A;
import graph.Graph;

//Class DelivA does the work for deliverable DelivA of the Prog340
/**
 * modified by Say Chaleon Vang
 * ics340-02
 * 4/11/23
 * First programming assignment
 * Modify this program to analyze graphs
 * Finds the number of nodes and edges
 * Finds the longest and shortest edges
 * Writes to an output text file
 * Writes to the console
 */
public class DelivA {
	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	
	/*My code ***/
	//Declare Computation_A object
	Computation_A computation_A;
	/*End my code ***/

	@SuppressWarnings("unused")
	public DelivA( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		int eSize = gr.getEdgeList().size();
		int nSize = gr.getNodeList().size();
		String string1 = null;
		String string2 = null;
		StringBuilder string3 = new StringBuilder();
		StringBuilder string4 = new StringBuilder();
		String stringZ = null;
		
		/*My code ***/
		//initialize Computation_A
		//Get edge data as strings
		//System.out.println(eSize);
		//System.out.println(nSize);
		if(eSize > 0) {
			//initialize Computation_A
			//passe a Graph object into the constructor
			computation_A = new Computation_A(gr);
			string1 = computation_A.getNumberOfNodes();
			string2 = computation_A.getNumberOfEdges();
			string3 = computation_A.getMaxNode();
			string4 = computation_A.getMinNode();
		}
		else {
			stringZ = "There are zero edges in the grapth";
		}
		/*End my code ***/
		
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
			
			/*My code ***/
			//code to write data to output file
			if(eSize > 0) {
				output.println(string1);
				output.println(string2);
				output.println(string3);
				output.println(string4);
				//write to console
				System.out.println(string1);
				System.out.println(string2);
				System.out.println(string3);
				System.out.println(string4);
			}
			else {
				output.println(stringZ);
				//write to console
				System.out.println(stringZ);
			}
			/*End my Code ***/
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		//System.out.println( "DelivA:  To be implemented");
		//output.println( "DelivA:  To be implemented");
		output.flush();
	}

}



