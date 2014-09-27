package lift;

public class Lift extends Thread {
	private LiftView lv;
	private Monitor monitor;
	private boolean movingUp = true;

	public Lift(LiftView lv, Monitor monitor) {
		this.lv = lv;
		this.monitor = monitor;
	}

	public void run() {
		lv.drawLift(0, 4);
		monitor.setNextFloor(nextFloor(monitor.currentFloor()));
		while (true) {
			int currentFloor = monitor.currentFloor();
			int nextFloor = monitor.nextFloor();
			if (currentFloor != nextFloor) {
				System.out.println("Current Floor: " + currentFloor + " Next Floor: " + nextFloor);
				lv.moveLift(currentFloor, nextFloor);
				monitor.setNextFloor(nextFloor(nextFloor));
			} 
		}
	}

	private int nextFloor(int current) {
		if (movingUp) {
			if (current >= LiftView.NO_OF_FLOORS-1) {
				movingUp = false;
			} else {
				current++;
			}
		}
		if (!movingUp) {
			if (current <= 0) {
				movingUp = true;
				current++;
			} else {
				current--;
			}
		}
		return current;
	}
}
