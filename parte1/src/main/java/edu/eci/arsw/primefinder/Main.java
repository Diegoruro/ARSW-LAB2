package edu.eci.arsw.primefinder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.logging.Logger;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws InterruptedException {
		List<PrimeFinderThread> threads = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			threads.add(new PrimeFinderThread(i*10000000,(i+1)*10000000));
			threads.get(i).start();
		}
		threads.forEach(thread-> {
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		Object lock = new Object();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.plusSeconds(5);
		while (!now.isAfter(then)){
			now = LocalDateTime.now();
		}
		this.stop(threads.ge);


		
	}

	public void stop(Thread thread) throws InterruptedException {
		Object lock = new Object();
		synchronized (lock){
			thread.wait();
		}
	}
	
}
