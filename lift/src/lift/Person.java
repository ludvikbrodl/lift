package lift;

import java.util.Random;

public class Person extends Thread {
    private final Random rand;
    private boolean inLift;
    private Monitor monitor;

    public Person(Monitor monitor) {
        this.monitor = monitor;
        rand = new Random();
    }

    public void run() {

        while (true) {
            int startFloor = rand.nextInt(6);
            int goalFloor = rand.nextInt();

            if (goalFloor == startFloor) {
                if (goalFloor == 0) {
                    goalFloor++;
                } else {
                    goalFloor--;
                }
            }

            if (!inLift) {
                while (monitor.currentFloor() != startFloor && monitor.isLiftFull()) try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            monitor.enterLift?
                inLift = true;


            } else if (inLift) {
                while (monitor.currentFloor() != goalFloor) try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            monitor.leaveLift?
                inLift = false;
            }


            try {
                sleep(1000 * ((int) (Math.random() * 46.0)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
