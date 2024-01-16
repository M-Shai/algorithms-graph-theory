package compute;

import java.util.Comparator;

import node.HeurNode;

/**
 * MyComparatorF
 * Originally designed to be used with PriorityQueue, but some reason does not work.
 * Now this class is used by ArrayList to sort.
 * Compares two HeurNodes by
 * the f value (f_Value). If f_Value is equal
 * sorts by name.
 * @param <T>
 */

public class MyComparatorF implements Comparator<HeurNode>{
	
	/* Compare HeurNode by the f_value.    
	 * lambda function (n1,n2)->n1.getHeurValue()-n2.getHeurValue();
	 * If the value is the same use the name.
	 * @param HeurNode n1
	 * @param HeurNode n2
	 * @return -1, 0, 1
	 */
	@Override
	public int compare(HeurNode n1, HeurNode n2) {
		
		if( n1.getF_Value() == n2.getF_Value()) {
			return n1.getName().compareTo(n2.getName());
		}
		return n1.getF_Value() - n2.getF_Value();	
	}
}
