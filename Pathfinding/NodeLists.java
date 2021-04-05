package me.declan.holiday.Pathfinding;

import java.util.ArrayList;

import me.declan.holiday.Misc.Debug;

@SuppressWarnings("serial")
public class NodeLists extends ArrayList<Node> {
	
	@Override
	public boolean contains(Object obj) {
		/*
		 * The default method, ArrayList.contains(), returns Array.indexOf(obj), which will not work for Nodes
		 */
		
		for (Object element : this.toArray()) {
			if (((Node) obj).equals((Node) element)) {
				Debug.debug(NodeLists.class, "Array contained our node.");
				return true;
			}
		}
		Debug.debug(NodeLists.class, "Could not find node in array.");
		return false;
	}
	
	public Node getSimilarNode(Node node) {
		for (Object element : this.toArray()) {
			if (node.equals((Node) element)) {
				Debug.debug(NodeLists.class, "Found similar node.");
				return (Node) element;
			}
		}
		Debug.debug(NodeLists.class, "Could not find similar node.");
		return null;
	}
}
