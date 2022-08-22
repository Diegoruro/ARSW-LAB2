package edu.eci.arsw.primefinder;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	private final Object lock;
	int a,b;

	private List<Integer> primes=new LinkedList<Integer>();
	
	public PrimeFinderThread(Object lock, int a, int b) {
		super();
		this.lock = lock;
		this.a = a;
		this.b = b;
	}

	public void run(){
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime then = now.plusSeconds(5);
		for (int i=a;i<=b;i++){
			synchronized (lock){
				now = LocalDateTime.now();
				if (now.isAfter(then)){
					try {
						System.out.println("Primes: " + primes.size());
						System.out.println("Presione Enter para reiniciar el hilo");
						lock.wait();
						now = LocalDateTime.now();
						then = now.plusSeconds(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (isPrime(i)) {
				primes.add(i);
				System.out.println(i);
			}
		}
	}
	
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

}
