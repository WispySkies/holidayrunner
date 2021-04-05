package me.declan.holiday.Pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import me.declan.holiday.Misc.Debug;

public class PathFinding {

	private static char[][] map;
	
	public static void updateMap(char[][] map) {
		PathFinding.map = map;
	}
	
	public static String aStar(Node origin, Node end) {
		NodeLists open = new NodeLists();
		NodeLists closed = new NodeLists();
		
		open.add(origin);
		
		while (!open.isEmpty()) {
			Debug.debug(PathFinding.class, "List size: " + open.size() + " Open, " + closed.size() + " Closed");
			Node current = new Node(-1, -1);
			float f = Float.MAX_VALUE;
			
			for (Node node : open) {
				if (node.g + node.heuristic(end.posX, end.posY) < f) {
					f = node.g + node.heuristic(end.posX, end.posY);
					current = node;
				}
			}
			
			if (current.equals(end)) {
				Node optimal = current;
				Debug.debug(PathFinding.class, "Found optimal path.");
//				Debug.fatal(PathFinding.class, "Current node position: " + current.posX + ", " + current.posY);
				int count = 1;
				while (current.parent != null) {
					count++;
//					Debug.fatal(PathFinding.class, "Parent node location: " + current.posX + ", " + current.posY);
					current = current.parent;
				}
				Debug.debug(PathFinding.class, "Expecting size: " + count);
				return buildPath(optimal);
			}
			
			open.remove(current);
			closed.add(current);
			
			for (Node node : getNeighbors(current)) {
				Debug.debug(PathFinding.class, "Found a neighbor.");
				if (!closed.contains(node)) {
					Debug.debug(PathFinding.class, "Node was not in closed list.");
					if (open.contains(node)) {
						Debug.debug(PathFinding.class, "Node already existed in open. Creating sibling with stats. g: " + node.g + ", heuristic: " + node.heuristic(end.posX, end.posY));
						Node sibling = open.getSimilarNode(node);
						if (node.g < sibling.g) {
							sibling.g = node.g;
							sibling.parent = node.parent;
						}
					} else {
						open.add(node);
						Debug.debug(PathFinding.class, "Adding an open node with the following stats. g: " + node.g + ", heuristic: " + node.heuristic(end.posX, end.posY));
					}
				} else Debug.debug(PathFinding.class, "Node was in closed list.");
			}
		}
		Debug.debug(PathFinding.class, "Couldn't find suitable path.");
		return null;
	}
	
	public static List<Node> getNeighbors(Node node) {
		List<Node> potentialNodes = new ArrayList<>();
		potentialNodes.add(new Node(node.posX - 1, node.posY));
		potentialNodes.add(new Node(node.posX + 1, node.posY));
		potentialNodes.add(new Node(node.posX, node.posY + 1));
		potentialNodes.add(new Node(node.posX, node.posY - 1));
		
//		for (Node n : potentialNodes) {
//			Debug.debug(PathFinding.class, n.posX + ", " + n.posY);
//		}
		
//		(
//				new Node(node.posX - 1, node.posY - 1), 
//				new Node(node.posX + 1, node.posY + 1), 
//				new Node(node.posX - 1, node.posY + 1), 
//				new Node(node.posX + 1, node.posY - 1));
//		
		
		List<Node> out = new ArrayList<>();
		
		for (Node n : potentialNodes) {
//			Debug.debug(PathFinding.class,"" + map[n.posX][n.posY]);
			if (map[n.posX][n.posY] != 'X')  {
				n.g = node.g + 1;
				n.parent = node;
				out.add(n);
				Debug.debug(PathFinding.class, "Added neighbor to node list.");
			}
		}
		Debug.debug(PathFinding.class, "List size: " + out.size() + " Out");
		return out;
	}
	
	public static String buildPath(Node node) {
		Node current = node;
		Stack<Node> path = new Stack<>();
		path.push(current);
		
		while (current.parent != null) {
			current = current.parent;
			path.push(current);
		}
		
//		Debug.debug(PathFinding.class, "Getting stack size of " + path.size());
		
		String out = "";
		
		Node previous = path.pop();
		while (!path.isEmpty()) {
			Node next = path.lastElement();
//			Debug.debug(PathFinding.class, "Equal? " + next.equals(previous) + " Previous pos: " + previous.posX + ", " + previous.posY + ". Next pos: " + next.posX + ", " + next.posY);
			
			if (next.posY - previous.posY == 0) {
				// change in x
				
				if (next.posX - previous.posX > 0) {
					out += "S";
				} else out += "W";
			} else {
				// change in y
				
				if (next.posY - previous.posY > 0) {
					out += "D";
				} else out += "A";
			}
			if (path.size() != 0) previous = path.pop();
		}
		
		Debug.print(PathFinding.class, "Path: " + out);
		return out;
	}
}
