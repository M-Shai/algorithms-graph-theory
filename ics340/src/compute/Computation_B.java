package compute;

import java.util.ArrayList;
import java.util.Iterator;

import edge.Edge;
import graph.Graph;
import node.Node;



/**
 * 
 * @author Say Chaleon Vang
 * ICS 340-02 Algorithms and Data Structures
 * September 30, 2023
 * 
 * Computation_B extends Computation_A.
 * Class to traverse the graph bottom up. 
 * Start with the goal(G) Node and perform a deep search 
 * for the start(S). Inside getPath() method, we call traverse 
 * recursively to traverse each incoming edge of the current node. 
 * We use an ArrayList named path to store each branch of the 
 * traversal. If the start(S) node is reached, we get the abbreviation 
 * and append it to a StringBuilder Object. The method getPath returns
 * a StringBuilder Object.
 * 
 * Since we traverse each k(edges) of n(nodes) for log(n) depth/levels
 * the recurrence of the traverse method is 
 * 		t(n)=k*t(n/k)+1, (k>0).
 * Since at each depth the work done is k*T(n/k) for k > 0. At the
 * bottom work is done in constant time O(1).
 * If k = o we don't do any work, so we don't include it.
 * The running time is 
 * 		Ɵ(k*lg⁡(n/k)). 
 * 
 */
public class Computation_B extends Computation_A{
	//private fields
	private ArrayList<Node> path;
	private ArrayList<String> paths;
	protected ArrayList<Node> nodeList;
	private ArrayList<Node> deadEnd;
	protected Node start;
	protected Node goal;
	
	/**
	 * constructor
	 * Super to initialize the Graph
	 * Initialize all collections
	 * Sets the start(S), goal(G) node
	 * @param gr
	 */
	public Computation_B(Graph gr) {
		super(gr);
		this.nodeList = gr.getNodeList();
		this.path = new ArrayList<Node>();
		this.paths = new ArrayList<String>();
		deadEnd = new ArrayList<Node>();
		setPoints();
	}//end constructor
	
	/**
	 * method to return the edge lists as a 
	 * String Builder object
	 * @return StringBuilder object
	 */
	public StringBuilder getNumberPath() {
		StringBuilder string = new StringBuilder();
		super.edgeListToString(super.getGr().getEdgeList());
		return string;
	}//getNumberPath
	
	/**
	 * Method call to return number of paths and
	 * all paths to get from start(S) to goal(G).
	 * It calls the traverse method
	 * It creates a StringBuilder object and appends the
	 * result, then returns it.
	 * @return StringBuilder object
	 */
	public ArrayList<String> getPath(){
		//StringBuilder string = new StringBuilder();
		//ArrayList<StringBuilder> strPath = new ArrayList<>();
		
		/*** The recursive method traverse() is called                    ***/
		/*** The main work is done here see traverse method for details   ***/
		traverse(goal, path);
		
		//Check to see if there are zero paths
		/*if(paths.isEmpty()) {
			string.append("There are no paths from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev());
			strPath.add(string);
		}
		//If there is exactly one path
		else if(paths.size() == 1){
			string.append("There is " + paths.size() + " way to go from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev() + ": " );
			for(Iterator<StringBuilder> iterator =
					paths.iterator(); iterator.hasNext();) {
				string.append(iterator.next());
			}
			strPath.add(string);
		}
		//More than 1 path
		else {
			string.append("\nThere are " + paths.size() + " ways to go from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev() + ":\n" );
			for(Iterator<StringBuilder> iterator =
					paths.iterator(); iterator.hasNext();) {
				string.append(iterator.next());
				if(iterator.hasNext()) {
					string.append("\n");
				}
			}
			strPath.add(string);
		}*/
		return paths;
	}//end getPath
	
	/**
	 * Method call to return number of paths and
	 * all paths to get from start(S) to goal(G).
	 * It calls the traverse method
	 * It creates a StringBuilder object and appends the
	 * result, then returns it.
	 * @return StringBuilder object
	 */
	/*public StringBuilder getPath(){
		StringBuilder string = new StringBuilder();
		
		/*** The recursive method traverse() is called                    
		/*** The main work is done here see traverse method for details   
		traverse(goal, path);
		
		//Check to see if there are zero paths
		if(paths.isEmpty()) {
			string.append("There are no paths from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev());
		}
		//If there is exactly one path
		else if(paths.size() == 1){
			string.append("There is " + paths.size() + " way to go from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev() + ": " );
			for(Iterator<StringBuilder> iterator =
					paths.iterator(); iterator.hasNext();) {
				string.append(iterator.next());
			}
		}
		//More than 1 path
		else {
			int i = 0;
			string.append("\nThere are " + paths.size() + " ways to go from " 
					+ start.getAbbrev() + " to " + goal.getAbbrev() + ":\n" );
			for(Iterator<StringBuilder> iterator =
					paths.iterator(); iterator.hasNext();) {
				string.append(iterator.next());
				if(iterator.hasNext()) {
					string.append("\n");
				}
			}
		}
		return string;
	}//end getPath
	*/
	
	/**
	 * Method to recursively traverse the graph bottom up.
	 * It traverses each edge a Node. Starting with the
	 * goal(G), ending with the start(S) node, or a dead end node.
	 * Which is a node that is not the (S) node and has no in coming edges.
	 * We add each node along the traversal to a path list. If we reach
	 * a dead end node, we store that node in a dead end list, to prevent 
	 * future traversals to that node. If it is the (S) node then we add
	 * the path to the paths collection. We then back up one level, remove
	 * the just visited node from the path, and take another edge. 
	 * If no edges exist we return to the (G) node, which means all nodes 
	 * from (G) to (S) have been discovered.
	 * @param Node object 
	 * @param ArrayList path, list to store current path
	 * @return boolean, used as a break method.
	 */
	@SuppressWarnings("unused") //used as a break
	public boolean traverse(Node node, ArrayList<Node> path) {
		boolean flag = false;
		//Check to see if node is already in the path. If so it's a cycle.
		if(path.contains(node)) {
			path.add(node);
			//System.out.println("There is a cylcle: " + pathToString(path).reverse());
			return false;
		}
		//add the node to the path.
		path.add(node);
		//check to see if reach goal.
		if(node.getAbbrev().equals(start.getAbbrev())) {
			paths.add(pathToString(path));
			return true;
		}
		//get incoming edges.
		if(node.getIncomingEdges().isEmpty()) {
			deadEnd.add(node);
			//System.out.println("Dead ends: " + pathToString(deadEnd));
		}
		//iterate through all edges
		if(!node.getIncomingEdges().isEmpty()){
			for(Iterator<Edge> iterator = 
					node.getIncomingEdges().iterator(); iterator.hasNext();) {
				Edge tmpEdge = iterator.next();
				if(!deadEnd.contains(tmpEdge.getTail())) {
					flag = traverse(tmpEdge.getTail(), path);
				}
				path.remove(tmpEdge.getTail());
			}
		}
		return false;
	}//end traverse
	
	/**
	 * Method to set the start(S) and goal(G) nodes
	 * Can only be and is called from the constructor.
	 */
	private void setPoints() {
		for(Iterator<Node> iterator = 
				nodeList.iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			if(node.getVal() != null) {
				if(node.getVal().equals("S")) {
					start = node;
				}
				else if(node.getVal().equals("G")){
					goal = node;
				}
			}
		}
		/*
		System.out.println("Start node: " + start.getAbbrev()
			+ " value=" + start.getVal());
		System.out.println("Goal node:  " + goal.getAbbrev()
			+ " value=" + goal.getVal());
		*/
	} //end setPoints
	
	/**
	 * Method to get a node list
	 * @return ArrayList nodeList, the nodeList
	 */
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}//end getNodeList

	/**
	 * @return the start
	 */
	public Node getStart() {
		return start;
	}//end getStart

	/**
	 * @return the goal
	 */
	public Node getGoal() {
		return goal;
	}//end getGoal
	
	/**
	 * Method convert a single path list to a string
	 * It returns it as a StringBuilder object
	 * @param ArrayList list
	 * @return StringBuilder object
	 */
	public String pathToString(ArrayList<Node> list) {
		StringBuilder string = new StringBuilder();
		for(Iterator<Node> iterator = 
				list.iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			string.insert(0,node.getAbbrev());
			if(iterator.hasNext()) {
				string.insert(0, "->");
			}
		}
		return string.toString();
	}//End edgeListToString
	
	/**
	 * Method to return a list of node, its incoming and outgoing
	 * edges and it's value/(S) or (G).
	 * @param ArrayList list
	 */
	public void nodeListToString(ArrayList<Node> list) {
		int j = 0;
		for(Iterator<Node> iterator = 
				list.iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			System.out.println(j + ". " + node.getAbbrev() + " value: " + node.getVal()
			+ " name: " + node.getName());
			j++;
			System.out.println("Out going edges:");
			super.edgeListToString(node.getOutgoingEdges());
			System.out.println("In going edges:");
			super.edgeListToString(node.getIncomingEdges());
		}
	}//End edgeListToString
}
