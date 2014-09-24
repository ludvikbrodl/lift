package lift;

public class Simulation extends Thread {
	private static final int MAX_PEOPLE = 20;
	private LiftView lv;
	private Monitor monitor;
	private Person[] people;
	
	public Simulation() {
		lv = new LiftView();
		monitor = new Monitor();
		Lift lift = new Lift(lv, monitor);
		lift.start();
		
	}
	
	public void run() {
		for (int i = 0; i < MAX_PEOPLE; i++) {
			people[i] = new Person();
		}
		while (true) {
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < MAX_PEOPLE; i++) {
				if (!people[i].isAlive()) {
					people[i] = new Person();
				}
			}
		
		}
	}
}
