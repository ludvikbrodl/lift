package lift;

public class Lift extends Thread {
    private LiftView lv;
    private Monitor monitor;
    private boolean movingUp = true;
    private int currentFloor;

    public Lift(LiftView lv, Monitor monitor) {
        this.lv = lv;
        this.monitor = monitor;
        currentFloor = 0;
    }

    public void run() {
        lv.drawLift(0, 4);
        int moveTo = 0;
        while (true) {
            moveTo = monitor.okToMoveLift();
            System.out.println("Current Floor: " + currentFloor + " Next Floor: " + moveTo);

            lv.moveLift(currentFloor, moveTo);
            currentFloor = moveTo;
        }
    }


}
