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


    public Monitor() {
        waitEntry = new int[7];
        waitExit = new int[7];
    }

    synchronized int currentFloor() {
        return here;
    }

    public boolean readyToMove() {
        return !(here == next);
    }

    synchronized public void setNextFloor(int nextFloor) {
        waitExit[next] = 0;
        System.out.println("On floor: " + next);
        here = next;
        next = nextFloor;
        notifyAll();
    }

    public int nextFloor() {
        return next;
    }

    //synchronized to avoid multiple Person entering a lift at the same time in this way fill the lift with more than 4 Person.
    synchronized boolean okToEnter() {
        if (waitExit.length <= 4) {
            waitEntry[here]--;
            return true;
        } else {
            return false;
        }
    }

    synchronized void requestToEnter(int startFloor) {
        waitEntry[startFloor]++;
    }
}
