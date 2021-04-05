package me.declan.holiday.Pathfinding;

import java.util.ArrayList;
import java.util.List;

public class SmartTargeting {

	public static Node getNearestCarrot(char[][] map, Node position) {
		List<Node> nearestCarrots = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 'C') {
					nearestCarrots.add(new Node(y, x));
				}
			}
		}
		int highest = Integer.MAX_VALUE;
		Node bestNode = null;
		for (Node node : nearestCarrots) {
			int steps = getStepsToPosition(map, position, node);
			if (steps < highest) {
				bestNode = node;
				highest = steps;
			}
		}
		
		return bestNode;
	}
	
	public static Node getNearestPresent(char[][] map, Node position) {
		List<Node> nearestPresents = new ArrayList<>();
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				try {
					int parsed = Integer.parseInt(map[y][x] + "");
					if (parsed > 0) {
						nearestPresents.add(new Node(y, x));
					}
				} catch (NumberFormatException e) {}
			}
		}
		int highest = Integer.MAX_VALUE;
		Node bestNode = null;
		for (Node node : nearestPresents) {
			int steps = getStepsToPosition(map, position, node);
			if (steps < highest) {
				bestNode = node;
				highest = steps;
			}
		}
		
		return bestNode;
	}
	
	public static int getStepsToPosition(char[][] map, Node start, Node end) {
		if (start == null || end == null) return -1;
		PathFinding.updateMap(map);
		String path = PathFinding.aStar(start, end);
		return path == null ? -1 : path.length();
	}
}
