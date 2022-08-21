package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private final Object lock;
	private Boolean isPaused = false;

	public Galgo(Carril carril, String name, RegistroLlegada reg, Object lock) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.lock = lock;
	}

	public void pause(){
		this.isPaused = true;
	}

	public void resumeGalgos(){
		this.isPaused = false;
	}

	public void corra() throws InterruptedException {
		while (paso < carril.size()) {

			Thread.sleep(100);
			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			synchronized (lock) {
				if (paso == carril.size()) {
					carril.finish();
					int ubicacion = regl.getUltimaPosicionAlcanzada();
					if (ubicacion == 1) {
						regl.setGanador(this.getName());
					}
					regl.setUltimaPosicionAlcanzada(ubicacion + 1);
					System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);

				}
				if (isPaused) {
					this.lock.wait();
				}
			}
		}
	}



	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
