package me.declan.holiday.Misc;

public class Debug {

	static final boolean DEBUGGING = false;
	static final boolean FATAL = false;
	static final boolean PRINT = false;
	
	public static void debug(Class<?> cl, String msg) {
		if (!DEBUGGING) return;
		System.out.println("[DEBUG] " + cl.getSimpleName() + ": " + msg);
	}
	
	public static void fatal(Class<?> cl, String msg) {
		if (!FATAL) return;
		System.out.println("[FATAL] " + cl.getSimpleName() + ": " + msg);
	}
	
	public static void print(Class<?> cl, String msg) {
		if (!PRINT) return;
		System.out.println("[INFO] " + cl.getSimpleName() + ": " + msg);
	}
}
