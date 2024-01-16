package compute;
import java.util.ArrayList;
import java.util.Iterator;

import edge.Edge;
import graph.Graph;

/**
 * @author Say Vang
 * ics340-02
 * 4/11/23
 * Class Computation_A
 * computes number of nodes
 * computes number of edges
 * computes min and max edge distance
 */
public class Computation_A {	
//private fields
private Graph gr;
protected int numNodes;
private int numEdges;
private ArrayList<Edge> minEdgeList;
private ArrayList<Edge> maxEdgeList;
private ArrayList<Edge> edgeList;

/**
 * Constructor
 * Set this.gr
 * Compute min and max edges
 * @param gr
 */
public Computation_A(Graph gr){
		this.gr = gr;
		this.edgeList = gr.getEdgeList();
		this.numNodes = gr.getNodeList().size();
		findMinMaxEdge(edgeList);
		//edgeListToString(gr.getEdgeList());
	}//End Constructor
	
	/**
	 * @return String number of edges
	 */
	public String getNumberOfEdges() {
		numEdges = gr.getEdgeList().size();
		if(numEdges > 0) {
			return "There are " + numEdges + " edges in the graph.\n";
		}
		else {
			return "There no edges in this graph";
		}
	}//End getNumberOfEdges
	
	/**
	 * @return String number of nodes
	 */
	public String getNumberOfNodes() {
		//numNodes = gr.getNodeList().size();
		if(numNodes > 0) {
			return "There are " + numNodes + " nodes in the graph.\n";
		}
		else {
			return "There are no Nodes in this graph";
		}
	}//End getNumberOfNodes
	
	/**
	 * Creates a min edge string
	 * @return String
	 */
	public StringBuilder getMinNode() {
		StringBuilder string = new StringBuilder();
		if(minEdgeList.size() < 1) {
			return string.append("There are no edges");
		}
		//System.out.println("Line 75\nmin edge list");
		edgeListToString(minEdgeList);
		if(minEdgeList.size() > 1) {
			string.append("The shortest edges are ");
		}
		else {
			string.append("The shortest edge is ");
		}
		for (Iterator<Edge> iterator = 
				minEdgeList.iterator(); iterator.hasNext();) {
			Edge minEdge = iterator.next();
			string.append(minEdge.getTail().getAbbrev() 
					+ minEdge.getHead().getAbbrev());
			if(iterator.hasNext()) {
				string.append(" and ");
			}
		}
		string.append(" with distance " + minEdgeList.get(0).getDist() + ".");
		return string;
	}//End getMinNode
	
	/**
	 * Creates a max edge string
	 * @return String
	 */
	public StringBuilder getMaxNode() {
		
		StringBuilder string = new StringBuilder();
		if(maxEdgeList.size() < 1) {
			return string.append("There are no edges");
		}
		//System.out.println("Line 106\nmax edge list");
		edgeListToString(maxEdgeList);
		if(maxEdgeList.size() > 1) {
			string.append("The longest edges are ");
		}
		else {
			string.append("The longest edge is ");
		}
		for (Iterator<Edge> iterator = 
				maxEdgeList.iterator(); iterator.hasNext();) {
			Edge maxEdge = iterator.next();
			string.append(maxEdge.getTail().getAbbrev() 
					+ maxEdge.getHead().getAbbrev());
			if(iterator.hasNext()) {
				string.append(" and ");
			}
		}
		string.append(" with distance " + maxEdgeList.get(0).getDist() + ".");
		return string;
	}//End getMaxNode
	
	/**
	 * Calculates the min and max edges
	 * @param ArrayList<Edge> list
	 */
	public void findMinMaxEdge(ArrayList<Edge> list) {
		maxEdgeList = new ArrayList<Edge>();
		minEdgeList = new ArrayList<Edge>();
		if(list.size() > 1) {
			//sets the min and max edges
			if(list.get(0).getDist() < list.get(1).getDist()) {
				//minEdge = list.get(0);
				minEdgeList.add(list.get(0));
				//maxEdge = list.get(1);
				maxEdgeList.add(list.get(1));
			}
			else if(list.get(0).getDist() == list.get(1).getDist()) {
				minEdgeList.add(list.get(0));
				//minEdgeList.add(list.get(1));
				maxEdgeList.add(list.get(0));
				//minEdgeList.add(list.get(1));
			}
			else {
				//minEdge = list.get(1);
				minEdgeList.add(list.get(1));
				//maxEdge = list.get(0);
				maxEdgeList.add(list.get(0));
			}
			//iterate through list to find min and max edges
			for(int i = 2; i < list.size(); i++) {
				if(list.get(i).getDist() < minEdgeList.get(0).getDist()) {
					/*
					System.out.println("Line 162:new minEdge = " 
							+ list.get(i).getHead().getAbbrev() 
							+ list.get(i).getTail().getAbbrev());
					*/
					minEdgeList.add(list.get(i));
					minEdgeList.clear();
					minEdgeList.add(list.get(i));	
				}
				else if(list.get(i).getDist() == minEdgeList.get(0).getDist()) {
					/*
					System.out.println("Line 170: minEdge = " 
							+ list.get(i).getHead().getAbbrev() 
							+ list.get(i).getTail().getAbbrev());
					*/
					minEdgeList.add(list.get(i));
				}
				else if(list.get(i).getDist() > maxEdgeList.get(0).getDist()) {
					/*
					System.out.println("Line 177: maxEdge = " 
							+ list.get(i).getHead().getAbbrev() 
							+ list.get(i).getTail().getAbbrev());
					*/
					maxEdgeList.clear();
					maxEdgeList.add(list.get(i));
				}
				else if(list.get(i).getDist() == maxEdgeList.get(0).getDist()) {
					/*
					System.out.println("Line 184: maxEdge = " 
							+ list.get(i).getHead().getAbbrev() 
							+ list.get(i).getTail().getAbbrev());
					*/
					maxEdgeList.add(list.get(i));
				}
			}
		}
		
	}//End findMinMaxEdge

	/**
	 * @return the gr
	 */
	public Graph getGr() {
		return gr;
	}

	/**
	 * @param gr the gr to set
	 */
	public void setGr(Graph gr) {
		this.gr = gr;
	}

	/**
	 * @return the numNodes
	 */
	public int getNumNodes() {
		return numNodes;
	}

	/**
	 * @param numNodes the numNodes to set
	 */
	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	/**
	 * @return the numEdges
	 */
	public int getNumEdges() {
		return numEdges;
	}

	/**
	 * @param numEdges the numEdges to set
	 */
	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	/**
	 * @return the minEdgeList
	 */
	public ArrayList<Edge> getMinEdgeList() {
		return minEdgeList;
	}

	/**
	 * @param minEdgeList the minEdgeList to set
	 */
	public void setMinEdgeList(ArrayList<Edge> minEdgeList) {
		this.minEdgeList = minEdgeList;
	}

	/**
	 * @return the maxEdgeList
	 */
	public ArrayList<Edge> getMaxEdgeList() {
		return maxEdgeList;
	}

	/**
	 * @param maxEdgeList the maxEdgeList to set
	 */
	public void setMaxEdgeList(ArrayList<Edge> maxEdgeList) {
		this.maxEdgeList = maxEdgeList;
	}

	/**
	 * @return the edgeList
	 */
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}

	/**
	 * @param edgeList the edgeList to set
	 */
	public void setEdgeList(ArrayList<Edge> edgeList) {
		this.edgeList = edgeList;
	}

	/**
	 * Prints out the edge data in an ArrayList<Edge> list
	 * @param ArrayList<Edge> list
	 */
	public void edgeListToString(ArrayList<Edge> list) {
		int j = 0;
		for(Iterator<Edge> iterator = 
				list.iterator(); iterator.hasNext();) {
			Edge edge = iterator.next();
			System.out.println(j + ". " + edge.getHead().getAbbrev() 
					+ edge.getTail().getAbbrev() 
					+ " = " + edge.getDist());
			j++;
		}
	}//End edgeListToString

}
