package me.declan.holiday;

import me.declan.holiday.Misc.Debug;
import me.declan.holiday.Pathfinding.Node;
import me.declan.holiday.Pathfinding.PathFinding;
import me.declan.holiday.Pathfinding.PathQueue;
import me.declan.holiday.Pathfinding.SmartTargeting;

public class ScannerClaus {
	private int numPresents;
	private int numCarrots;
	private int x, y;
	private String path;

	public ScannerClaus(DeliveryMap d) {
		this.numPresents = d.getNumPresents();
		this.numCarrots = 5 * this.numPresents;
		int[] pos = d.find('S');
		this.x = pos[1];
		this.y = pos[0];
		this.path = "";

	}

	/** @return the number of carrots left for the reindeers */
	public int getNumCarrots() {
		return numCarrots;
	}

	/** @return the number of presents left to deliver */
	public int getNumPresents() {
		return numPresents;
	}

	/**
	 * @return the list of selected moves as a single String with no spaces followed
	 *         by a new line, "Steps: " and then the number of moves made.
	 */
	public String getPath() {

		// store a history of prior moves
		// then return count of moves

		return path + "\nSteps: " + path.length();
	}

	/**
	 * Returns the character determining the next move for Scanner Claus * @param d
	 * the map Scanner Claus is navigating
	 * 
	 * @return the character representing the single next move for Scanner Claus to
	 *         make in delivering all the presents
	 */

	public char chooseMove(DeliveryMap d) {
		Node position = new Node(d.find('S')[0], d.find('S')[1]);
		PathFinding.updateMap(d.getMap());

		int stepsToNearestCarrot = SmartTargeting.getStepsToPosition(d.getMap(), position,
				SmartTargeting.getNearestCarrot(d.getMap(), position));
//		int stepsToNearestPresent = SmartTargeting.getStepsToPosition(d.getMap(), position, SmartTargeting.getNearestPresent(d.getMap(), position));

		Node nearestCarrot = SmartTargeting.getNearestCarrot(d.getMap(), position);
		Node nearestPresent = SmartTargeting.getNearestPresent(d.getMap(), position);

		if (this.getNumCarrots() - stepsToNearestCarrot < 3) {
			if (stepsToNearestCarrot == -1) {
				Debug.fatal(ScannerClaus.class, "We ate all the carrots...");
			} else {
				PathQueue.clearQueue();
				PathQueue.queue(PathFinding.aStar(position, nearestCarrot));
				return PathQueue.getNextMove().charAt(0);
			}
		}

		if (PathQueue.getQueueCount() == 0) {
			PathQueue.queue(PathFinding.aStar(position, nearestPresent));
			return PathQueue.getNextMove().charAt(0);
		}

		return PathQueue.getNextMove().charAt(0);

		// DeliveryMap.find() will find nearest carrot/obstacle/present sorted TOP LEFT
		// to BOTTOM RIGHT
		// it's inefficient!
		// find all of them and pathfind to the closest...

		// IF CARROTS < 10, PRIORITIZE CARROTS!!!!
		// IF CARROTS < 10 BUT X MOVES FROM PRESENT AND Y MOVES FROM PRESENT->CARROT AND
		// X+Y < 10, PRESENT THEN CARROT
	}

	public void move(DeliveryMap d, char dir) {
		/* added */
		path += dir;
		/*       */

		int nextX = this.x;
		int nextY = this.y;
		switch (dir) {
		case 'W':
			nextY = this.y - 1;
			break;
		case 'A':
			nextX = this.x - 1;
			break;
		case 'S':
			nextY = this.y + 1;
			break;
		case 'D':
			nextX = this.x + 1;
			break;
		default:
			return;
		}
		char target = d.getPosition(nextY, nextX);
		if (target == 'C')
			this.numCarrots += 20;
		else if ((int) target > 48 && (int) target < 58)
			this.numPresents--;
		if (d.setPosition(nextY, nextX, false)) {
			d.setPosition(this.y, this.x, true);
			this.x = nextX;
		}
		this.y = nextY;
		this.path += dir;
		this.numCarrots--;
	}

	public String toString() {
		String msg = "Ho ho ho! (" + x + "," + y + ")\n";
		msg += "Carrots = " + this.numCarrots + "; Presents = " + this.numPresents + "\n";
		msg += "Press W (up), A (left), S (down), D (right), Q (quit)\n";
		return msg;
	}
}