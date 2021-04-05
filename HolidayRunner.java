package me.declan.holiday;

import java.util.Scanner;

public class HolidayRunner {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		DeliveryMap d = new DeliveryMap("/Users/Declan/eclipse-workspace/Holiday/src/me/declan/holiday/mapA.txt");
		ScannerClaus sc = new ScannerClaus(d);
		Scanner s = new Scanner(System.in);
		
		int playMode;
		char choice;
		
		System.out.println("Automate (1) or Play (2): ");
		
		playMode = s.nextInt();
		
		boolean firstMove = true;
		
		if (playMode == 1) {
			
			do {
				
				if (firstMove) firstMove = false;
				
				System.out.println(d.printMap());
				sc.move(d, sc.chooseMove(d));
				
			} while (sc.getNumCarrots() > 0 && sc.getNumPresents() > 0);
			
		} else {
			
			do {
				System.out.println(d.printMap());
				System.out.println(sc);
				choice = s.next().toUpperCase().charAt(0);
				sc.move(d, choice);
				
			} while (choice != 'Q' && sc.getNumCarrots() > 0 && sc.getNumPresents() > 0);
			
		}
		
		if (sc.getNumCarrots() == 0)
			System.out.println("Your reindeer ran out of carrots and refuse to keep going!");
		else if (sc.getNumPresents() == 0)
			System.out.println("You delivered all of the presents! Ho ho!");
		System.out.println(sc.getPath());
	}
}