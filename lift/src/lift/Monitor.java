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
	private boolean okToMove;
	private boolean movingUp;
	private LiftView lv;

	public Monitor(LiftView lv) {
		waitEntry = new int[7];
		waitExit = new int[7];
		movingUp = true;
		this.lv = lv;
	}

	// synchronized to avoid multiple Person entering a lift at the same time in
	// this way fill the lift with more than 4 Person.
	synchronized public void okToEnter(int startFloor, int goalFloor) {
		waitEntry[startFloor]++;
		lv.drawLevel(startFloor, waitEntry[startFloor]);
		while (startFloor != here || load >= 4 || here != next) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("to floor " + goalFloor);
		waitEntry[here]--;
		waitExit[goalFloor]++;
		load++;
		lv.drawLevel(startFloor, waitEntry[startFloor]);
		lv.drawLift(here, load);
		// System.out.println(load);
		boolean ingenVillIn = waitEntry[here] == 0;
		boolean ingenVillUt = waitExit[here] == 0;
	    boolean hissenFull = load >= 4;
	    System.out.println(ingenVillUt && hissenFull);
		if (ingenVillIn && ingenVillUt || ingenVillUt && hissenFull) {
			okToMove = true;
		}
		notifyAll();
		while (goalFloor != here || here != next) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		waitExit[goalFloor]--;
		load--;
		System.out.println("exit");
		lv.drawLift(here, load);
		ingenVillIn = waitEntry[here] == 0;
		ingenVillUt = waitExit[here] == 0;
	    hissenFull = load >= 4;
		if (ingenVillIn && ingenVillUt || ingenVillUt && hissenFull) {
			okToMove = true;
		}
		notifyAll();
	}

	synchronized public int okToMoveLift() {
		here = next;
		notifyAll();
		if ((waitEntry[here] == 0 && waitExit[here] == 0)) {
			next = nextFloor(here);
			return next;
		}
		while (!okToMove) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		okToMove = false;
		next = nextFloor(here);
		return next;
	}

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
