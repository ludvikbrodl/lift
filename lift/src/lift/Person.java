package lift;

import java.util.Random;

public class Person extends Thread {
    private final Random rand;
    private boolean inLift;
    private Monitor monitor;
    int startFloor;
    int goalFloor;

    public Person(Monitor monitor) {
        this.monitor = monitor;
        rand = new Random();
        startFloor = rand.nextInt(6);
        goalFloor = rand.nextInt(6);
        tempFixSameRandomNbr();
    }

    public void run() {

        while (true) {
            monitor.okToEnter(startFloor, goalFloor);

            System.out.println("Entering on floor: " + startFloor);
            inLift = true;

            System.out.println("Waiting to leave");


            inLift = false;
            System.out.println("Leaving on floor: " + goalFloor);
            goalFloor = rand.nextInt(6);
            tempFixSameRandomNbr();


            try {
                sleep(1000 * ((int) (Math.random() * 46.0)));
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

