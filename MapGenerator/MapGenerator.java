package me.declan.holiday.MapGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MapGenerator {

	public static void main(String[] args) {
		//HolidayRunner.main(args);

		Scanner s = new Scanner(System.in);
		
		System.out.println("File name: ");
		String filename = s.nextLine();

		System.out.println("Map dimensions: ");
		int sz = s.nextInt();

		System.out.println("Number of gifts: ");
		int gifts = s.nextInt();
		int carrots = (int) (gifts / 2);

		System.out.println("Obstacle distribution (n>0.3 and it might be impossible...): ");
		double distribution = s.nextDouble();

		s.close();

		char[][] map = new char[sz][sz];
		for (int x = 0; x < sz; x++)
			for (int y = 0; y < sz; y++)
				if ((x == 0 || x == sz - 1) || (y == 0 || y == sz - 1)) {
					map[x][y] = 'X';
				} else {
					map[x][y] = ' ';
				}

		while (carrots >= 0) {
			int x = (int) (Math.random() * sz);
			int y = (int) (Math.random() * sz);
			
			if (map[x][y] != 'X' || map[x][y] != 'C') {
				carrots--;
				map[x][y] = 'C';
			}
		}
		
		int increasing_counter = 1;
		while (gifts >= 0) {
			int x = (int) (Math.random() * sz);
			int y = (int) (Math.random() * sz);
			try {
				if (map[x][y] != 'X' || map[x][y] != 'C' || Integer.parseInt(map[x][y] + "") > 0) {
					gifts--;
					map[x][y] = (char) (increasing_counter + '0');
					increasing_counter++;
				}
			} catch (NumberFormatException e) {
				continue;
			}
		}
		
		for (int x = 0; x < sz; x++) {
			for (int y = 0; y < sz; y++) {
				if (Math.random() < distribution) {
					try {
						if (map[x][y] != 'X' || map[x][y] != 'C' || Integer.parseInt(map[x][y] + "") > 0) {
							map[x][y] = 'X';
						}
					} catch (NumberFormatException e) {
						continue;
					}
				}
			}
		}
		
		// print map
		for (int y = 0; y < sz; y++) {
			for (int x = 0; x < sz; x++) {
				System.out.print(map[x][y]);
			}
			System.out.println();
		}
		
		// write to file
		try {
			File save = new File("src/me/declan/holiday/" + filename + ".txt");
			if (save.createNewFile()) {
				FileWriter fw = new FileWriter("src/me/declan/holiday/" + filename + ".txt");
				char[] buf = {(char) (sz + '0'), (char) (sz + '0'), (char) (increasing_counter + '0')};
				fw.write(buf);
				for (int x = 0; x < sz; x++) 
					fw.write(map[x]);
				fw.close();
				System.out.println("Wrote to file.");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("Could not write file.");
		}
	}
}
