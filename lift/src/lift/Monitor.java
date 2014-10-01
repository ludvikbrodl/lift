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


    public Monitor() {
        waitEntry = new int[7];
        waitExit = new int[7];
        movingUp = true;
    }



    synchronized public void setNextFloor(int nextFloor) {
        System.out.println("On floor: " + next);
        here = next;
        next = nextFloor;
        notifyAll();
    }


    //synchronized to avoid multiple Person entering a lift at the same time in this way fill the lift with more than 4 Person.
    synchronized public void okToEnter(int startFloor, int goalFloor) {
        waitEntry[startFloor]++;

        while (load >= 4 && startFloor != here) {

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(startFloor + " : " + here);
        waitEntry[here]--;
        waitExit[goalFloor]++;
        load++;
        if (waitEntry[here] != 0 || load >= 4) {
            okToMove = true;
            notifyAll();
        }

        while (goalFloor != here) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        waitExit[goalFloor]--;
        load--;
        if (waitEntry[here] != 0 || load >= 4) {
            okToMove = true;
            notifyAll();
        }
    }

    synchronized public int okToMoveLift() {
        here = next;
        notifyAll();
        if((waitEntry[here] == 0 && waitExit[here] == 0)){
            return nextFloor(next);
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

    private int nextFloor(int current) {
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
        System.out.println("Current " +  current);
        return current;
    }
}
