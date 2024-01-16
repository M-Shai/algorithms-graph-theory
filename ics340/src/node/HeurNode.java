package node;

import java.util.ArrayList;

import edge.Edge;

/**
 * 
 * @author Say Chaleon Vang
 *
 * *** IMPORTANT MUST USE MY Prog340, I UPDATED LINE 200 TO WORK WITH HeurNode Class. ***
 *
 * HeurNode extends Node.
 * Used to store heuristic values, f values, previous node, and the goal node
 * Adds a few more fields, setters, getters
 * I updated Prog340 line 200 to utilize this Class instead of Node
 * Keeps track of F Value, Heuristic, and cost
 *
 */
public class HeurNode extends Node{
	
	private int heurValue;   //heuristic to goal node
	private int f_Value;     //cost(p) + h(p)
	private int cost;        //actual cost from previous node
	private Node goal;       //target goal node
	private Node previous;   //previous node
	

	/**
	 * Constructor
	 * @param theAbbrev
	 */
	public HeurNode(String theAbbrev) {
		super(theAbbrev);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return String abbreviation
	 */
	public String getAbbrev() {
		return abbrev;
	}
	
	/**
	 * @return String name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return String
	 */
	public String getVal() {
		return val;
	}
	
	/**
	 * @return ArrayList<Edge>
	 */
	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	/**
	 * @return ArrayList<Edge>
	 */
	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}
	
	/**
	 * @param String abbreviation
	 */
	public void setAbbrev( String theAbbrev ) {
		abbrev = theAbbrev;
	}
	
	/**
	 * @param String name
	 */
	public void setName( String theName ) {
		name = theName;
	}
	
	/**
	 * @param String value
	 */
	public void setVal( String theVal ) {
		val = theVal;
	}
	
	/**
	 * @param Edge e
	 */
	public void addOutgoingEdge( Edge e ) {
		outgoingEdges.add( e );
	}
	
	/**
	 * @param Edge e
	 */
	public void addIncomingEdge( Edge e ) {
		incomingEdges.add( e );
	}

	/**
	 * @return the huerValue
	 */
	public int getHeurValue() {
		return heurValue;
	}
	
	/**
	 * @param huerValue the huerValue to set
	 */
	public void setHeurValue(int huerValue) {
		this.heurValue = huerValue;
	}

	/**
	 * @return the previous
	 */
	public HeurNode getPrevious() {
		return (HeurNode) previous;
	}

	/**
	 * @param previous the previous to set
	 */
	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	/**
	 * @return the f_Value
	 */
	public int getF_Value() {
		return f_Value;
	}

	/**
	 * @param f_Value the f_Value to set
	 */
	public void setF_Value(int f_Value) {
		this.f_Value = f_Value;
	}

	/**
	 * @return the goal
	 */
	public Node getGoal() {
		return goal;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Node goal) {
		this.goal = goal;
	}

	/**
	 * @return the cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * areWeThereYet()
	 * Checks to see if this is the goal node.
	 * Each node will know whether they are the goal node or not.
	 * @return boolean, true if this is the goal node
	 */
	public boolean areWeThereYet() {
		if(goal.name == name) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * isStart
	 * returns true if this node is the start
	 * @param HeurNode
	 * @return boolean
	 */
	public boolean isStart(HeurNode node) {
		return (name == node.getName());
	}
	
	/**
	 * toString
	 * returns the data as a string.
	 * @return String data
	 */
	@Override
	public String toString() {
		return "Name: " + name + ", Dist: " + cost + ", Heur: " 
				+ heurValue + ", f-value: " + f_Value;
	}
	
	/**
	 * toStringBuilder
	 * returns the data as a string.
	 * @return StringBuilder data
	 */
	public StringBuilder toStringBuilder() {
		StringBuilder str = new StringBuilder();
		str.append("Name: " + name + ", Dist: " + cost + ", Heur: " 
				+ heurValue + ", f-value: " + f_Value);
		return str;
	}
}
