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


        monitor.requestToEnter(startFloor);

        while (true) {

            if (!inLift) {
                while (monitor.currentFloor() != startFloor && !monitor.okToEnter()) {
                    try { //Väntar om hissen inte är på vår våning alt. full. Måste också på något sätt göra så att Person lämnar hissen innan monitor.okToEnter() kallas.
                        synchronized (this) { //Får ej göra såhär
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Entering on floor: " + startFloor);
                inLift = true;

            } else if (inLift) {
                while (monitor.currentFloor() != goalFloor) {
                    System.out.println("Waiting to leave");
                    try {
                        synchronized (this) {
                            wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                inLift = false;
                System.out.println("Leaving on floor: " + goalFloor);
                startFloor = rand.nextInt(6);
                goalFloor = rand.nextInt(6);
                tempFixSameRandomNbr();
            }


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

