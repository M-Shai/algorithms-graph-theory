package delivery;
import java.io.File;
import compute.Computation_C;
import java.io.PrintWriter;
import java.util.ArrayList;

import graph.Graph;
import node.HeurNode;

// Class DelivC does the work for deliverable DelivC of the Prog340

/**
 * 
 * @author Say Chaleon Vang
 * 
 * ICS 340-02 Algorithms and Data Structures
 * November 3, 2023
 * Deliverable C implementation
 * A Star Search
 * 
 * Implements the A Star Search Algorithm using two graphs. A regular distance graph and a heuristic graph.
 * See below and class docs.
 * 
 * ************************************* IMPORTANT NOTES MUST READ ***********************************************
 * 
 * 		I have created a new class: HeurNode extends Node.
 * 			It adds new fields, setters, getters, toString():
 * 				int heurValue   holds the heuristic value.
 * 				int f_value     holds the computed f value.
 * 				int cost        holds the distance/cost from the start.
 * 				Node goal       holds the target goal node.
 * 				Node previous   holds the previous node in the traversal.
 * 
 * 		I have updated the prog340 to work with this new class. Only two changes.
 * 			1. I added an import for HeurNode at line 12.
 * 			2. I updated line 200 to use HeurNode instead of Node.
 * 
 * 		Created MyComparatorF implements Comparator.
 * 			Compares Nodes using the field f_Value. This is the comparator I use for sorting
 * 			the frontier list.
 * 
 * 		Created MyComparatorH implements Comparator.
 * 			Compares based on heuristic value. Used only for testing. Not used in sorting
 * 			the frontier list.
 * 
 * 		Created a Computation_C class extends Computation_B.
 * 			Keeps three ArrayLists<HeurNode> collections.
 * 				1. frontier list, holds all nodes on the frontier.
 * 				2. explored list, holds all nodes that have been fully discovered.
 * 				3. unExplored list, holds all nodes not discovered yet.
 * 			The constructor initializes all the list using gHeur and gr Graphs.
 * 			Takes both graphs, initializes each nodes fields, and adds it to a unExplored list.
 * 				1. Calls the super class, Computation_B. initializes its fields.
 * 					a. The start and goal Nodes.
 * 					b. The ArrayList<Node> nodeList.
 * 				2. Sets all nodes' f_values to the largest integer value available.
 * 				3. Set each nodes heuristic value, and goal value.
 * 				4. Add each node to the unExplored list.
 * 			Methods:
 * 				1. A_Star(). The A* search begins here. Calls to appropriate methods for processing
 * 					unExplored nodes starting at the start node.
 * 				2. Discover(). Discovers the node passed in. It calls the Relax() method and
 * 					then adds the node to the explored list.
 * 				3. Relax(). It takes the passed in node, gets the the outgoing edge list, calculates a 
 * 					new f_value for each edge head node. If the new value is less then the old f_value, 
 * 					it sets it as the new value. It also sets the previous node and the cost. Adds each node 
 * 					to the frontier list. Does this for each node. Then sorts the list using MyComparatorF class.
 * 				4. Print/toString methods for testing and printing data to the console.
 * 
 * 		I have updated DelivB to recognize large Graphs and not run them. Graphs with more then 15 nodes
 * 			and n edges will not work. So I each graph is validated with a check.
 * 		
 * 		I have also updated Computation_B to recognize cycles. My previous implementation would run in an
 * 			infinite loop when a cycle is encountered. It would crash the program.
 * 			Computation_B lines 139-143. Checks to see if current node is already in the path list. If it
 * 				is don't expand it. There is a cycle.
 * 			Updated implementation now works with graphs with cycles.
 * 			
 */


 /** 
 * *** IMPORTANT MUST USE MY Prog340, I UPDATED LINE 200 TO WORK WITH HeurNode Class. ***
 * 
 * The driver for the work being done.
 * It handles the input graphs and output.
 * Takes in two graphs
 * Outputs the computed data to the console and a text file.
 * For more documentation see Computation_C
 *
 */
public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph distGraph, heurGraph;
	Computation_C compute;
	
	/**
	 * DelivC
	 * Manages the two graphs.
	 * Creates a Computation_C object.
	 * Computes the path using the object.
	 * Displays results on the console.
	 * Prints out to a text file.
	 * 
	 * @param in
	 * @param distGraph
	 * @param heurGraph
	 */
	public DelivC( File in, Graph distGraph, Graph heurGraph) {
		inputFile = in;
		this.distGraph = distGraph;
		this.heurGraph = heurGraph;
		
		//Object to build an output string
		ArrayList<String> strList = new ArrayList<>();
		//Check to see if both graphs are valid, See the method doc.
		if(isGraphValid(distGraph, heurGraph)) {
			//Computation object to setup A* search
			compute = new Computation_C(distGraph, heurGraph);
		
			//System.out.println("\n***UnExplored Graph");
			//compute.nodeListToStringF(compute.getUnExplored());
			
			//Start the search
			if(compute.getStart() == null || compute.getGoal() == null) {
				System.out.println("Error, not able to set start and or goal node!\nRe-select graph file.");
			}
			else {
				compute.Initialize();
				compute.A_Star();
				
				//get the computed explored nodes list
				ArrayList<HeurNode> explored = compute.getExplored();
				
				//System.out.println("\n***Frontier list");
				//compute.nodeListToStringF(compute.getFrontierList());
				
				//System.out.println("\n***Explored nodes");
				//compute.nodeListToStringF(explored);
				
				//Strings headers for out display
				String str1 = "\n\n**** SOLUTION:";
				String str2 = String.format("Shortest Path from " + compute.getStart().getName() 
						+ " (" + compute.getStart().getAbbrev() + ") to " + compute.getGoal().getName()
						+ " (" + compute.getGoal().getAbbrev() + ").\n");
				
				String str3 = String.format("%-32s%-15s%-15s%-15s", "PATH", "DIST", "HEUR", "F-VALUE\n");
				
				//Call to method to build a string list for output. 
				//Iterates through the list for every explored node.
				//Each pass writes one path, formatted to an output list, strList.
				//See the buildString() method doc.
				while(!explored.isEmpty()) {
					buildString(explored, strList);
				}
				//String str4 = "Explored " + strList.size() + " different paths.";
				
				//Add the headers to the front of the output list
				strList.add(0, str3);
				strList.add(0, str2);
				//strList.add(0, str4);
				strList.add(0, str1);
				
				/*
				for(String line: strList) {
					System.out.println(line);
				}*/
				
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
					for(String line: strList) {                   //Loop to output the data
						System.out.println(line);                 //Outputs to the console
						output.println(line);                     //Outputs to a text file
					}
				}
				catch (Exception x ) { 
					System.err.format("Exception: %s%n", x);
					System.exit(0);
				}
				System.out.println( "DelivC:  implementation.");
				output.println( "DelivC: implementation.");
				output.flush();
				
			}
		}
		else {
			System.out.println("Error! At least one graph is invalid. Please load graphs again.");
		}
	}
	
	/**
	 * Method to check if both graphs are valid.
	 * Checks: If they are not null, not Empty/no nodes,
	 * or if the number of nodes of each graph is equal.
	 * Returns false if any of these are false.
	 * @param Graph g
	 * @param Graph gh
	 * @return boolean
	 */
	public boolean isGraphValid(Graph g, Graph gh) {
		if((g == null || gh == null) || (g.getNodeList().isEmpty()) 
				|| gh.getNodeList().isEmpty() 
				|| g.getNodeList().size() != gh.getNodeList().size()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	/**
	 * Builds a string output list.
	 * Manages and formats the explored list data.
	 * Gets the last node in the list for formatting to new list.
	 * Adds it to the output, strList, lists.
	 * @param ArrayList<HeurNode> list, data being managed
	 * @param ArrayList<String> strList, the out put list
	 */
	public void buildString(ArrayList<HeurNode> list, 
			ArrayList<String> strList) {
		//gets the last item of the explored list
		HeurNode node = list.remove(list.size() - 1);
		//StringBuilder object for building each string
		StringBuilder strBuild = new StringBuilder();
		//String with the resulting data for each line
		String str1 = String.format("%-15d%-15d%-15d\n", node.getCost(), 
				node.getHeurValue(), node.getF_Value());
		//Loop to build the nodes traversed for each line
		while(node!= null) {
			//Decision to insert at the front of the list with formatting
			if(node.getPrevious() != null) {
				strBuild.insert(0, "-" + node.getAbbrev()); //Inserts to front w/'-' and abbreviation.
			}
			else {
				strBuild.insert(0, node.getAbbrev());  //Inserts to front.
			}
			//Sets the node to the previous node in the traversal.
			node = node.getPrevious();
		}
		//When the previous node is null. It has reached its root node
		//Formats the StringBuilder,strBuild, and the String data, str1
		String str2 = String.format("%-32s%-45s", strBuild, str1);
		//Adds the resulting string to the strList at the front.
		strList.add(0, str2);
	}
		
}


