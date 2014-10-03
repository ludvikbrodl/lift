package lift;

import java.util.Random;

public class Person extends Thread {
    private static final double SPAWN_DELAY = 4.5; //up to numebr of seconds
	private final Random rand;
    private Monitor monitor;
    int startFloor;
    int goalFloor;

    public Person(Monitor monitor) {
        this.monitor = monitor;
        rand = new Random();
        startFloor = rand.nextInt(LiftView.NO_OF_FLOORS);
        goalFloor = rand.nextInt(LiftView.NO_OF_FLOORS);
        fixSameRandomNbr();
    }

    public void run() {
        while (true) {
            monitor.movePerson(startFloor, goalFloor);
            startFloor = goalFloor;
            goalFloor = rand.nextInt(LiftView.NO_OF_FLOORS);
            fixSameRandomNbr();
            try {
                sleep(1000 * ((int) (Math.random() * SPAWN_DELAY)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Changes goalFloor if same as startFloor
     */
    private void fixSameRandomNbr() {
        if (goalFloor == startFloor) {
            if (goalFloor == 0) {
                goalFloor++;
            } else {
                goalFloor--;
            }
        }
    }
}

