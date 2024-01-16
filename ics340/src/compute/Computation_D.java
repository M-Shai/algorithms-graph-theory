package compute;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edge.Edge;
import graph.Graph;
import node.Node;

/**
 * 
 * @author Say Chaleon Vang
 * 
 * Class to implement the Floyd-Warshall All Pairs Shortest path Algorithm.
 * Algorithm finds all paths through (k = 1) to (k = number of vertices) vertices.
 * Starts out by initializing the values(D^0) and previous(P^0) matrix.
 * For each k to n iteration, n = number of vertices.
 * Calculates all connected vertices values using:
 * 		d^(k)[i][j] = min(d^(k-1)[i][j], d^(k-1)[j][k] + d^(k-1)[k][j]).
 * Records the previous vertices into the p_matrix.
 * Adds the v_Matrix to the values ArrayList.
 * Adds the p_Matrix to the previous ArrayList.
 */
public class Computation_D extends Computation_B{
	
	private final int P_CONSTRAINT = 10;       //Determined by the specification of DelivD.
	private Integer vertices;                  //Number of vertices.
	private boolean neg_cycle = false;         //Used to indicate a negative cycle is found.
	private boolean printAll = true;           //To determine printing state.
	private ArrayList<Integer [][]> values;    //stores all value matrices.
	private ArrayList<String [][]> previous;   //stores all previous matrices.
	
	/**
	 * Constructor().
	 * Calls super class constructor.
	 * Initializes all fields.
	 * Calls initialize(), to initialize D^0 and P^0 matrices.
	 * @param Graph gr
	 */
	public Computation_D(Graph gr) {
		super(gr);                     //Initialize the super class.
		this.vertices = getNumNodes();
		values = new ArrayList<>();
		previous = new ArrayList<>();
		Initialize();                  //call to initialize the D^o and P^0 matrices.
	}
	
	/**
	 * Initialize(). 
	 * Creates 2 new double arrays.
	 * Initializes all value array elements to max Integer.
	 * Sets the i = j locations = 0.
	 * Sets all intersections to their distance values
	 * Initialize all the previous array elements to '-'.
	 * Sets the intersections to edge.tail.abrev.
	 */
	public void Initialize() {
		/* Creates 2 new matrices for storing values and previous vertices */
		Integer v_Matrix[][] = new Integer[vertices][vertices];
		String p_Matrix[][] = new String[vertices][vertices];
		/* loop through each i row. */
		for(int i = 0; i < vertices; i++) {
			Node node = nodeList.get(i);
			ArrayList<Edge> edgeLs = node.getOutgoingEdges();
			/* loop through each j column */
			for(int j = 0; j < vertices; j++) {
				if(i == j) {
					v_Matrix[i][j] = 0;     //set value matrix when i=j to 0; the middle diagonal.
					p_Matrix[i][j] = "-";   //set previous matrix when i=j to '-'.
				}
				else {
					v_Matrix[i][j] = Integer.MAX_VALUE/2;     //set all other value array elements to max.
					p_Matrix[i][j] = "-";     //set all other previous array elements to '-'.
				}
			}
			/* Set the intersecting vertices distance and previous */
			for(Iterator<Edge> iterator = edgeLs.iterator(); iterator.hasNext();) {
				Edge edge = iterator.next();
				Node head = edge.getHead();
				/* get the j value by finding the index of the vertices in the nodeList. */
				v_Matrix[i][nodeList.indexOf(head)] = edge.getDist();    //set the distance when i intersects j
				p_Matrix[i][nodeList.indexOf(head)] = edge.getTail().getAbbrev(); //set the previous vertices when i intersects j
			}
		}
		/* add both matrix to their matrix ArrayList. */
		values.add(v_Matrix);
		previous.add(p_Matrix);
	}
	
	/**
	 * Floyd_Warshall().
	 * Implements the Floyd-Warshall Algorithm.
	 * For each k to n iteration, n = number of vertices.
	 * Gets the previous value matrix and previous previous matrix.
	 * Create a deep copy of both previous value and previous previous matrices.
	 * Calculates all connected vertices values using:
	 * 		d^(k)[i][j] = min(d^(k-1)[i][j], d^(k-1)[j][k] + d^(k-1)[k][j]).
	 * Records the previous vertices into the p_matrix.
	 * Adds the v_Matrix to the values ArrayList.
	 * Adds the p_Matrix to the previous ArrayList.
	 */
	public void Floyd_Warshall() {
		//terminates when there are no more changes
		/* loop through each k iteration */
		for(int k = 0; k < vertices; k++) {
			String[][]pre_P = previous.get(k);    //get the previous previous matrix.
			Integer[][]pre_Val = values.get(k);   //get the previous value matrix.
			Integer[][]tmp_Val = copy(pre_Val);   //create a deep copy of previous value matrix.
			String[][]tmp_P = copy(pre_P);        //create a deep copy of previous previous matrix.
			/* loop through each i row */
			for(int i = 0; i < vertices; i++) {
				for(int j = 0; j < vertices; j++) {
					if(pre_Val[i][j] > pre_Val[i][k] + pre_Val[k][j]) {
						tmp_Val[i][j] = pre_Val[i][k] + pre_Val[k][j];    //Set tmp value to the new value. 
						tmp_P[i][j] = nodeList.get(k).getAbbrev();    //Set tmp previous vertices to node at i index.
					}
					else {
						tmp_Val[i][j] = pre_Val[i][j];    //Set to the same value as previous.
						tmp_P[i][j] = pre_P[i][j];     //Set to the same previous vertices as previous.
					}
					/* If a negative cycle is detected, sets the neg_cylcle to true */
					if(i == j && tmp_Val[i][j] < 0) {
						neg_cycle = true;
					}
				}
			}
			/* Add both matrices to the corresponding list*/
			values.add(tmp_Val);
			previous.add(tmp_P);
		}
		setPrintAll();    //Sets printAll. 
	}
	
	/**
	 * setPrintAll().
	 * Sets printAll to false if number of vertices is greater then
	 * P_CONSTRAINT. Else sets it to false.
	 * Used to decide on printing all solution matrices less then.
	 * the P_CONSTRAINT. See DelivD Spec. doc.
	 */
	public void setPrintAll() {
		if(vertices >= P_CONSTRAINT) {
			printAll = false;
		}
		else {
			printAll = true;
		}
	}
	
	/**
	 * copy(), performs a deep copy of an array.
	 * @param Integer[][] origArray.
	 * @return Integer[][] copy.
	 */
	public Integer[][] copy(Integer[][] orig){
		Integer[][] copy = new Integer[vertices][vertices];
		for(int i = 0; i < vertices; i++) {
			for(int j = 0; j < vertices; j++) {
				copy[i][j] = orig[i][j];
			}
		}
		return copy;
	}
	
	/**
	 * copy(), performs a deep copy of an array.
	 * @param String[][] origArray.
	 * @return String[][] copy.
	 */
	public String[][] copy(String[][] orig){
		String[][] copy = new String[vertices][vertices];
		for(int i = 0; i < vertices; i++) {
			for(int j = 0; j < vertices; j++) {
				copy[i][j] = orig[i][j];
			}
		}
		return copy;
	}
	
	/**
	 * minVal(),
	 * Gets the min of 2 int params.
	 * @param int dist1.
	 * @param int dist2.
	 * @return int min.
	 */
	public int minVal(int dist1, int dist2) {
		if(dist1 > dist2) {
			return dist2;
		}
		else {
			return dist1;
		}
	}
	
	/**
	 * getV_Matrix().
	 * gets the value matrix lists.
	 * If vertices printAll = false, only returns the last item in the list.
	 * @return ArrayList<Integer [][]> values.
	 */
	public List<Integer[][]> getV_Matrix(){
		if (printAll){
			//System.out.println("Line 194: vetices = " + vertices);
			return values;
		}
		else {
			List<Integer[][]> v_list 
				= values.subList(vertices, vertices + 1);
			//System.out.println("Line 200\nOriginal V vetices = " + vertices);
			//toString(vertices, values.get(vertices));
			//toStringV(values);
			//System.out.println("Line 202:\nsublist v_list.size = " + v_list.size());
			//toString(vertices, v_list.remove(0));
			return v_list;
		}
	}
	
	/**
	 * getP_Matrix().
	 * gets the previous matrix lists.
	 * If vertices printAll = false, only returns the last item in the list.
	 * @return ArrayList<String [][]> values.
	 */
	public List<String[][]> getP_Matrix(){
		if (printAll){
			//System.out.println("Line 215: vetices = " + vertices);
			return previous;
		}
		else {
			List<String[][]> p_list 
				= previous.subList(vertices, vertices + 1);
			//System.out.println("Line 221:\nOriginal P vetices = " + vertices);
			//toString(vertices, previous.get(vertices));
			//toStringP(previous);
			//System.out.println("Line 223:\nsublist p_list = " + p_list.size());
			//toString(vertices, p_list.remove(0));
			return p_list;
		}
	}
	
	/**
	 * getSolutions().
	 * Takes in two lists.
	 * Combines two matrices, formats and constructs an ArrayList of ArrayList<Strings>.
	 * It then returns that array
	 * @param Integer[][] vm
	 * @param String[][] pm
	 * @return ArrayList<ArrayList<String>> solution.
	 */
	public ArrayList<ArrayList<String>> getSolutions(Integer[][] vm, String[][] pm) {
		//System.out.println("*** Line 79: toString");
		ArrayList<ArrayList<String>> solution = new ArrayList<>();    //New array to store formated and combined array results
		ArrayList<String> p_Ls = new ArrayList<>();    //Stores the previous vertices as a string list.
		ArrayList<String> v_Ls = new ArrayList<>();    //Stores the values as a string list.
		StringBuilder strHeader = new StringBuilder();    //String builder for formatting and building strings.
		//StringBuilder strHeader2 = new StringBuilder();
		strHeader.append(String.format("%-7s", "Vertex"));    //Header
		/* Loop to iterate through the rows */
		for(int row = 0; row < vm.length; row++) {
			StringBuilder str1 = new StringBuilder();
			StringBuilder str2 = new StringBuilder();
			//System.out.println("Line 90: toString: row = " + row);
			strHeader.append(String.format("%-7s", nodeList.get(row).getAbbrev()));
			//strHeader2.append(String.format("%-7s", nodeList.get(row).getAbbrev()));
			str1.append(String.format("%-7s", nodeList.get(row).getAbbrev()));
			str2.append(String.format("%-7s", nodeList.get(row).getAbbrev()));
			/* Iterate through columns */
			for(int col = 0; col < vm.length; col++) {
				//System.out.println("Line 96: toString: col = " + col);
				if(vm[row][col] >= Integer.MAX_VALUE/2) {
					if(row == col) {
						//System.out.println("Line 98: toString");
						str1.append(String.format("%-7s", 0));
						str2.append(String.format("%-7s", pm[row][col]));
					}
					else {
						//System.out.println("Line 98: toString");
						str1.append(String.format("%-7s", '~'));
						str2.append(String.format("%-7s", pm[row][col]));
					}
				}
				else {
					//System.out.println("Line 103: toString");
					str1.append(String.format("%-7s", vm[row][col]));
					str2.append(String.format("%-7s", pm[row][col]));
				}
			}
			v_Ls.add(str1.toString());
			p_Ls.add(str2.toString());
			//System.out.println("Line 111: toString");
			//System.out.println(str1);
			//System.out.println(str2);
		}
		//System.out.println("Line 115: toString");
		v_Ls.add(0, strHeader.toString());
		p_Ls.add(0, strHeader.toString());
		solution.add(v_Ls);
		solution.add(p_Ls);
		//System.out.println("*** Line 120: toString");
		return solution;
	}
	
	/**
	 * getNumVertices().
	 * gets the vertices.
	 * @return int vertices.
	 */
	public int getNumVertices() {
		return vertices;
	}
	
	/**
	 * toString().
	 * Used to print a list of double arrays
	 * Used for testing
	 * @param ArrayList<Integer[][]> list
	 */
	public void toStringV(ArrayList<Integer[][]> list) {
		int k = 0;
		for(Integer[][] ls: list) {
			toString(k, ls);
			k++;
		}
	}
	
	/**
	 * toString().
	 * Used to print a list of double arrays
	 * Used for testing
	 * @param ArrayList<String[][]> list
	 */
	public void toStringP(ArrayList<String[][]> list) {
		int k = 0;
		for(String[][] ls: list) {
			toString(k, ls);
			k++;
		}
	}
	
	/**
	 * toString().
	 * For printing out one Integer[][] array.
	 * For testing purposes.
	 * @param int k.
	 * @param Integer[][] matrix
	 */
	public void toString(int k, Integer[][] matrix) {
		System.out.println("D^" + k + " --------------");
		for(Integer[] line: matrix) {
			for(int val: line) {
				if(val >= Integer.MAX_VALUE/2) {
					System.out.print("~, ");
				}
				else {
					System.out.print(val + ", ");
				}
			}
			System.out.println("\n");
		}
		System.out.println("__________________");
	}
	
	/**
	 * toString().
	 * For printing out one String[][] array.
	 * For testing purposes.
	 * @param int k.
	 * @param String[][] matrix
	 */
	public void toString(int k, String[][] matrix) {
		System.out.println("P^" + k + " --------------");
		for(String[] line: matrix) {
			for(String val: line) {
				System.out.print(val + ", ");
			}
			System.out.println("\n");
		}
		System.out.println("__________________");
	}
	
	/**
	 * toString().
	 * Used to print out a pair of double arrays.
	 * A value array and a previous array.
	 * Prints out to the console and or to a text file.
	 * @param int index.
	 * @param ArrayList<ArrayList<String>> solution.
	 * @param PrintWriter output.
	 */
	public void toString(int index, 
			ArrayList<ArrayList<String>> solution, PrintWriter output) {
		String strV = "\nValue Matrix D^" + index;
		String strP = "\nValue Matrix P^" + index;
		String strN = "\n*** A negative cylce exist in the graph. ***";
		ArrayList<String> v_Matrix = solution.get(0);
		v_Matrix.add(0, strV);
		ArrayList<String> p_Matrix = solution.get(1);
		p_Matrix.add(0, strP);
		/* If negative cycle exist, add negative cycle statement to first matrices */
		if(neg_cycle) {
			v_Matrix.add(0, strN);
			p_Matrix.add(0, strN);
		}
		//System.out.println("\nValue Matrix D^" + index);
		for(String line: v_Matrix) {
			System.out.println(line);
			if(output != null) {
				output.println(line);
			}
		}
		//System.out.println("\nPrevious Matrix P^" + index);
		for(String line: p_Matrix) {
			System.out.println(line);
			if(output != null) {
				output.println(line);
			}
		}
	}
}















