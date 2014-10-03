package lift;

public class Monitor {
	int here; // If here !=next , here (floor number) tells from which floor
	// the lift is moving and next to which floor it is moving.
	int next; // If here ==next , the lift is standing still on the floor
	// given by here.
	int[] waitEntry;// The number of persons waiting to enter the lift at the
	// various floors.
	int[] waitExit; // The number of persons (inside the lift) waiting to leave
	// the lift at the various floors.
	int load; // The number of people currently occupying the lift.
	private boolean movingUp;
	private LiftView lv;

	public Monitor(LiftView lv) {
		waitEntry = new int[LiftView.NO_OF_FLOORS];
		waitExit = new int[LiftView.NO_OF_FLOORS];
		movingUp = true;
		this.lv = lv;
	}

	synchronized public void movePerson(int startFloor, int goalFloor) {
		callLift(startFloor);
        enterLiftAndPressButton(startFloor, goalFloor);
		waitForRightFloorAndExit(goalFloor);
	}
	
	/*
	 * notify is called because waitEntry is modified, which could
	 * cause the while statement in moveLift to change to false;
	 */
	private void callLift(int startFloor) {
		waitEntry[startFloor]++;
		lv.drawLevel(startFloor, waitEntry[startFloor]);
		notifyAll();
	}

	/*
	 * notify is called because the lift can now be at maximum
	 * capacity inducing a move or be the last person to enter
	 * the lift which would also induce a move of the lift.
	 */
	private void enterLiftAndPressButton(int startFloor, int goalFloor) {
		while (startFloor != here || load >= 4 || here != next) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitEntry[here]--;
		waitExit[goalFloor]++;
		load++;
		lv.drawLevel(startFloor, waitEntry[startFloor]);
		lv.drawLift(here, load);
		notifyAll();
	}

	/*
	 * The number of people in the elevator decreases thus 
	 * creating room for another person to enter, which is why notify
	 * is called.
	 */
	private void waitForRightFloorAndExit(int goalFloor) {
		while (goalFloor != here || here != next) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitExit[goalFloor]--;
		load--;
		lv.drawLift(here, load);
		notifyAll();
	}

	/*
	 * Notify person threads that we are at new floor and thusly allowing
	 * them to enter or leave the lift.
	 */
	synchronized public int moveLift() {
		here = next;
		notifyAll();
		while (waitEntry[here] > 0 && load < 4 || waitExit[here] > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		next = nextFloor(here);
		return next;
	}

	/*
	 * Lift AI.
	 */
	synchronized private int nextFloor(int current) {
		if (movingUp) {
			if (current >= LiftView.NO_OF_FLOORS - 1) {
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
