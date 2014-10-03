package lift;

public class Lift extends Thread {
    private LiftView lv;
    private Monitor monitor;
    private int currentFloor;

    public Lift(LiftView lv, Monitor monitor) {
        this.lv = lv;
        this.monitor = monitor;
        currentFloor = 0;
    }

    public void run() {
        int moveTo = 0;
        while (true) {
            moveTo = monitor.moveLift();
            lv.moveLift(currentFloor, moveTo);
            currentFloor = moveTo;
        }
    }


}
