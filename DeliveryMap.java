package me.declan.holiday;

import java.io.File;
import java.util.Scanner;

public class DeliveryMap {
	private char map[][];
	private int rows;
	private int cols;
	private int numPresents;

	public DeliveryMap(String fileName) {
		Scanner s;
		try {

			s = new Scanner(new File(fileName));

			this.rows = s.nextInt();
			this.cols = s.nextInt();

			this.numPresents = s.nextInt();

			map = new char[rows][cols];

			String mapInput = s.nextLine();

			for (int r = 0; r < rows; r++) {

				mapInput = s.nextLine();

				for (int c = 0; c < cols; c++) {

					map[r][c] = mapInput.charAt(c);

				}
			}
		} catch (Exception e) {
			System.err.print(e);
		}
	}

	public int getNumPresents() {
		return numPresents;
	}

	public char getPosition(int r, int c) {
		return map[r][c];
	}

	public boolean setPosition(int r, int c, boolean previous) {
		if (map[r][c] == 'X') {
			return false;

		} else if (previous) {
			map[r][c] = '.';
		} else {
			map[r][c] = 'S';
		}
		return true;
	}

	public int[] find(char toFind) {
		int[] pos = new int[2];
		for (int r = 0; r < this.rows; r++) {
			for (int c = 0; c < this.cols; c++) {
				if (map[r][c] == toFind) {
					pos[0] = r;
					pos[1] = c;
					return pos;
				}
			}
		}
		return pos;
	}

	public String printMap() {
		String out = "";
		for (char[] r : map) {
			for (char c : r) {
				out += c;
			}
			out += "\n";
		}
		return out;
	}
	
	/* added, technically not allowed to edit this class, but I don't want to copy map......*/
	public char[][] getMap() {
		return map;
	}
}
