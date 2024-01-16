package compute;
import edge.Edge;
import graph.Graph;
import node.HeurNode;
import node.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * 
 * @author Say Chaleon Vang
 * 
 * Comuputation_C
 * 
 * *** IMPORTANT MUST USE MY Prog340, I UPDATED LINE 200, in Prog340, TO WORK WITH HeurNode Class ***
 * 
 * When prog340 runs, it reads in the weighted graph
 *  and then the heuristic graph. Essentially making two graphs.
 *  One with the actual cost and one with the heuristic cost (h) value.
 * I need code to interpret both graphs. To keep a list of all unexplored,
 *  discovered/explored nodes and nodes on the frontier. 
 *  I can implement the frontier nodes list with a priority queue, FOR SOME REASON COULDN'T GET THAT TO WORK,
 *  I INSTEAD USED AN ARRAYLIST WITH COMPARATOR TO SORT,
 *  using the heuristics graph in ascending order, FIFO.
 * Implement the discovered/explored and unexplored list each,
 *  as an ArrayLists.
 * This constructor will call super(gr); The super class constructor will
 *  run. It will set the start(S) and goal(G) Nodes.
 * This constructor will also create the priority queue(I used ArrayList instead) and the 2 ArrayLists 
 *  to hold type Node. It will also call the Initialize method to set the
 *  unexplored h(p) values to a very large number, largest integer.
 *  
 */

public class Computation_C extends Computation_B{
	
	/* I was unable to get the PrioritQueue to insert Nodes in  ascending order by f Value.
	 * Implemented the Comparator interface and override the compare method(). See MyComparatorF Class
	 * For some reason it didn't work, I used MyComparatorF with ArrayList to sort. It works.
	 */
	private ArrayList<HeurNode> frontier; //Nodes currently on the frontier.
	private ArrayList<HeurNode> explored; //Nodes that have been explored
	private ArrayList<HeurNode> unExplored; //Nodes that are yet to be explored
	private ArrayList<Node> heurLs;
	
	/**
	 * constructor
	 * Initializes all the lists.
	 * Initializes the graph nodes.
	 * @param Graph gr
	 * @param Graph gHeur
	 */
	public Computation_C(Graph gr, Graph gHeur) {
		super(gr);
		this.frontier = new ArrayList<>();
		this.explored = new ArrayList<>();
		this.unExplored = new ArrayList<>();
		this.heurLs = gHeur.getNodeList();
		/* Initialize the graphs */
		//Initialize(super.nodeList, gHeur.getNodeList(), super.goal, unExplored);
	}
	
	/**
	 * A_Star Search
	 * Finds a shortest path from start(S) Node to goal(G) Node
	 */
	public void A_Star() {
		/*****  Where A* star starts   ****/
		System.out.println("*** A_Star ***");
		frontier.add((HeurNode) start);
		do{     ///process the sorted frontiers' list
			HeurNode currentNode = frontier.remove(0);  ///remove first item, smallest f_value
			unExplored.remove(currentNode);  //remove current node from unexplored list
			if(currentNode.areWeThereYet()) {
				System.out.println("*****************\nReached the goal!!!: " + goal.getName());
				explored.add(currentNode);
				break;
			}
			if(currentNode.getName() == start.getName()) {
				currentNode.setPrevious(null);
			}

			Discover(currentNode);  ///discovery the node
		}while(frontier.iterator().hasNext());
		//System.out.println("\n\nA* Line 100: Explored list**************");
		//nodeListToStringF(explored);
	}
	
	/**
	 * getUnExplored
	 * returns the unExplored list
	 * @return ArrayList<HeurNode> unExplored
	 */
	public ArrayList<HeurNode> getUnExplored(){
		return unExplored;
	}
	
	/**
	 * getExplored
	 * returns the Explored list
	 * @return ArrayList<HeurNode> explored
	 */
	public ArrayList<HeurNode> getExplored(){
		return explored;
	}
	
	/**
	 * getFrontier
	 * returns the frontier list
	 * @return ArrayList<HeurNode> frontier list
	 */
	public ArrayList<HeurNode> getFrontierList(){
		return frontier;
	}
		
	/**
	 * getFrontier
	 * Creates a Node list. Using the current nodes outgoing edge list.
	 * Adds each edge head node to this Node list. This is that Nodes frontier.
	 * @param HeurNode node
	 * @return ArrayList<Node> list
	 */
	public ArrayList<HeurNode> getFrontier(HeurNode node){
		ArrayList<Edge> els = node.getOutgoingEdges();
		ArrayList<HeurNode> nls = new ArrayList<>();
		for(Edge edge: els) {
			//edgNode.setF_Value(Relax(node, edgNode));
			nls.add((HeurNode) edge.getHead());
		}
		return nls;
	}
	
	/**
	 * Initialize
	 * Initializes all the lists.
	 * Sets f_Value to a max integer value.
	 * Sets the goal field and the heuristic value.
	 * @param ArrayList<Node> uninitialized list
	 * @param ArrayList<Node> heuristic list
	 * @param Node goal the goal node
	 * @param ArrayList<HeurNode> unExplored list
	 */
	public void Initialize() {
		for(int index = 0; index < heurLs.size(); index++) {
			HeurNode tmpNode = ((HeurNode)(nodeList.get(index)));  //Get the (Regular) node.
			tmpNode.setF_Value(Integer.MAX_VALUE);  //Set the node f_Value to the max integer value.
			tmpNode.setGoal(goal);                  //Set the node goal value to the goal node.
			HeurNode node = ((HeurNode)(heurLs.get(index)));     //Get the heuristic node.
			//System.out.println("Index = " + index + ": node.name = " + node.getName());
			ArrayList<Edge> hEdgeLs = node.getOutgoingEdges();     //Get the heuristic outgoing edge list
			for(Edge hedge: hEdgeLs) {         //Iterate through each heuristic edge
				/* If goal abbrev is equal to the heur edge head abbrev, 
				 * set the node heuristic value to the heur edge dist.             */
				if(goal.getAbbrev().compareTo(hedge.getHead().getAbbrev()) == 0) { 
					//System.out.println("Line 80:\n**edge val = " + hedge.getDist());
					tmpNode.setHeurValue(hedge.getDist());    //Set the node heuristic value equal heur edge dist,
					break;
				}
				else {
					tmpNode.setHeurValue(Integer.MAX_VALUE);
				}
				
			}
			unExplored.add(tmpNode);
		}
		((HeurNode) start).setCost(0);  //initialize start
		((HeurNode) start).setF_Value(((HeurNode) start).getHeurValue());
	}
	
	/**
	 * Discover
	 * Discover the node, by calling the Relax().
	 * Adds the node to the explored list.
	 * @param HeurNode dNode
	 */
	public void Discover(HeurNode dNode) {
		Relax(dNode);  //send the node, so the node/edges and can be relax
		explored.add(dNode); //Finish discovery by adding the node to the explored list.
	}
	
	/**
	 * Relax
	 * Relaxes the Nodes' edges, 
	 * with their corresponding head nodes.
	 * Calculates the new f_Value and update it if it is less than the current value.
	 * Calculates and sets the cost. Distance from the Start node to this node.
	 * Sets the previous node.
	 * @param HeurNode node
	 */
	public void Relax(HeurNode node) {
		//System.out.println("\n*******Relax Line 173: node name = " + node.getName());
		ArrayList<Edge> edgeLs = node.getOutgoingEdges();  //gets the nodes outgoing edge list
		for(Iterator<Edge> iterator =
				edgeLs.iterator(); iterator.hasNext();) {  //iterator to iterate the list
			Edge edge = iterator.next();  //gets the next edge
			HeurNode tmpNode = (HeurNode) edge.getHead();  //gets that edges head node
			//System.out.println("\n*Relax Line 179: tmpNode name = " + tmpNode.getName());
			if(!explored.contains(tmpNode)) {
				int tmp_fv = node.getCost() + edge.getDist() + tmpNode.getHeurValue();  //calculate new f value
				//System.out.println("Relax Line 182: node.cost=" + node.getCost() + ", edge.dist=" 
						//+ edge.getDist() + ", tmpNode.f_value=" + tmpNode.getF_Value());
				//System.out.println("Relax Line 184: tmp_fv = " + tmp_fv 
						//+ " : tmpNode.heur = " + tmpNode.getHeurValue());
				if(tmp_fv < tmpNode.getF_Value()) {   //Check to see if new f value is less than current f value
					tmpNode.setF_Value(tmp_fv);  //If less, set the f value to new f value
					//System.out.println("Relax Line 188: new tmpNode f_value = " + tmpNode.getF_Value());
					tmpNode.setCost(node.getCost() + edge.getDist());  //calculate and set the new cost to that node
					//System.out.println("Relax Line 190: new tmpNode cost = " + tmpNode.getCost());
					tmpNode.setPrevious(node);  //set the previous node
					if(unExplored.remove(tmpNode)) {   //remove node from the unexplored list
						frontier.add(tmpNode);  //add the node to the frontier
					}
					
				}
				else {
					//A shorter path to the node exists, do nothing
					//System.out.println("Relax Line 199: old tmpNode f_value = " + tmpNode.getF_Value());
					//System.out.println("Relax Line 200: old tmpNode cost = " + tmpNode.getCost());
				}
			}
			else {
				//System.out.println(tmpNode.getName() + " aready been explored, do nothing.");
			}
		}
		/* I wasn't able to get PriorityQueue working so I used an ArrayList and sorted it */
		Collections.sort(frontier, new MyComparatorF());
		//System.out.println("\nRealx Line 203: frontier list:");
		//nodeListToStringF(frontier);
		//System.out.println("*******Realx Line 205: End Relax");
	}
	
	/**
	 * Method to return a list of node, its incoming and outgoing
	 * edges and it's value/(S) or (G).
	 * @param ArrayList list
	 */
	@Override
	public void nodeListToString(ArrayList<Node> list) {
		int j = 0;
		for(Iterator<Node> iterator = 
				list.iterator(); iterator.hasNext();) {
			HeurNode node = (HeurNode)iterator.next();
			System.out.println(j + ". " + node.getAbbrev() + " value: (" + 
					node.getVal()+ ") name: " + node.getName() + " \nh: " + 
					node.getHeurValue() + " to(G): " + node.getGoal().getName());
			j++;
			System.out.println("Out going edges:");
			super.edgeListToString(node.getOutgoingEdges());
			System.out.println("In going edges:");
			super.edgeListToString(node.getIncomingEdges());
		}
	}//End edgeListToString
	
	/**
	 * Method to return String representation of a list.
	 * @param unExplored2
	 */
	public void nodeListToStringH(ArrayList<HeurNode> unExplored2) {
		int j = 0;
		for(Iterator<HeurNode> iterator = 
				unExplored2.iterator(); iterator.hasNext();) {
			HeurNode node = (HeurNode)iterator.next();
			System.out.println(j + ". " + node.getAbbrev() + " value: (" + 
					node.getVal()+ ") name: " + node.getName() + " \nh: " + 
					node.getHeurValue() + " to(G): " + node.getGoal().getName());
			j++;
			System.out.println("Out going edges:");
			super.edgeListToString(node.getOutgoingEdges());
			System.out.println("In going edges:");
			super.edgeListToString(node.getIncomingEdges());
		}
	}//End edgeListToString
	
	/*
	private void pullQueue(Queue<HeurNode> frontier2) {
		int j = 0;
		while(!frontier2.isEmpty()) {
			HeurNode node = frontier2.remove();
			System.out.println(j + ". " + node.getAbbrev() + " value: (" + 
					node.getVal()+ ") name: " + node.getName() + " \nh_value: " + 
					node.getHeurValue() + " to(G): " + node.getGoal().getName() +
					" f_value: " + node.getF_Value());
			j++;
		}
	}
	*/
	
	/**
	 * Method to return a list of node, its incoming and outgoing
	 * edges and it's value/(S) or (G).
	 * @param ArrayList list
	 */
	public void nodeListToStringF(ArrayList<HeurNode> list) {
		int j = 0;
		for(Iterator<HeurNode> iterator = 
				list.iterator(); iterator.hasNext();) {
			HeurNode node = iterator.next();
			System.out.println("*****\n("+ j + "). (" + node.getAbbrev() + "), value: (" + 
					node.getVal()+ ") name: " + node.getName() + " \n     Dist: " + node.getCost() +
					", Heur: " + node.getHeurValue() + ", f_value: " + node.getF_Value()
					+ "\n     Previous node: " + node.getPrevious());
			j++;
			//System.out.println("Out going edges:");
			//super.edgeListToString(node.getOutgoingEdges());
			//System.out.println("In going edges:");
			//super.edgeListToString(node.getIncomingEdges());
		}
	}
	
}
