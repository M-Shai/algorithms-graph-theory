package graph;
import java.util.*;

import edge.Edge;
import node.Node;

public class Graph {

	ArrayList<Node> nodeList;
	ArrayList<Edge> edgeList;
	
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}
	
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
	
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	
	public void addNode( Node n ) {
		nodeList.add( n );
	}
	
	public void addEdge( Edge e ) {
		edgeList.add( e );
	}
	
}

