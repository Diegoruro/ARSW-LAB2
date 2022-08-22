package edu.eci.arsw.primefinder;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();

		List<PrimeFinderThread> threads = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			threads.add(new PrimeFinderThread(lock, i*10000000,(i+1)*10000000));
			threads.get(i).start();
		}
		Scanner scanner = new Scanner(System.in);
		while (Boolean.TRUE){
			String input = scanner.nextLine();
			if (input.isEmpty()){
				synchronized (lock){
					lock.notifyAll();
				}
			}
		}
	}
}
