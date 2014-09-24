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
		while (true) {
			int currentFloor = monitor.currentFloor();
			int nextFloor = monitor.nextFloor();
			if (currentFloor != nextFloor) {
				System.out.println("Current Floor: " + currentFloor + " Next Floor: " + nextFloor);
				lv.moveLift(currentFloor, nextFloor);
				monitor.setNextFloor(nextFloor(currentFloor));
			} else {
				monitor.setNextFloor(nextFloor(currentFloor));
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
			} else {
				current--;
			}
		}
		return current;
	}
}
