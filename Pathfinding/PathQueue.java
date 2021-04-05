package me.declan.holiday.Pathfinding;

import java.util.ArrayList;
import java.util.List;

import me.declan.holiday.Misc.Debug;

public class PathQueue {

	private static List<String> queue = new ArrayList<>();

	public static void queue(String path) {
		if (path == null) {
			Debug.fatal(PathQueue.class, "Path was null...likely found unsuitable path.");
			return;
		}
		queue.add(path);
	}

	public static void clearQueue() {
		queue.clear();
	}
	
	public static String getNextMove() {
		if (queue.size() == 0) {
			Debug.debug(PathQueue.class, "Path queue size = 0");
			return "E";
		}
		
		String path = queue.get(0);

		Debug.debug(PathQueue.class, "Active path: " + path);

		String out = path.substring(0, 1);

		if (path.length() == 1) {
			Debug.debug(PathQueue.class, "Removing path from queue, we used it's last move.");
			queue.remove(0);
		} else {
			queue.remove(0);
			queue.add(0, path.substring(1));
		}

		return out;

	}

	public static int getQueueCount() {
		return queue.size();
	}
	
	public static int getCurrentPathSize() {
		return queue.size() == 0 ? -1 : queue.get(0).length();
	}
}
