package compute;

import java.util.Comparator;

import node.HeurNode;

/**
 * MyComparatorH
 * compares two HeurNodes by
 * the heurValue(h_value).
 * @param <T>
 */

public class MyComparatorH implements Comparator<HeurNode>{
	
	/* Compare HeurNode by heuristic value.    (n1,n2)->n1-n2;
	 * If the value is the same use the name.
	 * @param HeurNode n1
	 * @param HeurNode n2
	 * @return -1, 0, 1
	 */
	@Override
	public int compare(HeurNode n1, HeurNode n2) {
		HeurNode node1 = n1;
		HeurNode node2 = n2;
		/*System.out.println("\nComp Line 24: node1 " + node1.getName() + node1.getHeurValue() 
		+ ": node2 " + node2.getName() + node2.getHeurValue());*/
		if( node1.getHeurValue() == node2.getHeurValue()) {
			//System.out.println("Comp Line 27: Sort by name");
			return node1.getName().compareTo(node2.getName());
		}
		//System.out.println("Comp Line 30: Return " + (node1.getHeurValue() - node2.getHeurValue()));
		return node1.getHeurValue() - node2.getHeurValue();	
	}
}
