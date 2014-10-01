package lift;

import java.util.Random;

public class Person extends Thread {
    private final Random rand;
    private Monitor monitor;
    int startFloor;
    int goalFloor;

    public Person(Monitor monitor) {
        this.monitor = monitor;
        rand = new Random();
        startFloor = rand.nextInt(7);
        goalFloor = rand.nextInt(7);
        tempFixSameRandomNbr();
    }

    public void run() {
        while (true) {
            monitor.okToEnter(startFloor, goalFloor);
            startFloor = goalFloor;
            goalFloor = rand.nextInt(7);
            tempFixSameRandomNbr();
            try {
                sleep(1000 * ((int) (Math.random() * 46.00000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tempFixSameRandomNbr() {
        if (goalFloor == startFloor) {
            if (goalFloor == 0) {
                goalFloor++;
            } else {
                goalFloor--;
            }
        }
    }
}

