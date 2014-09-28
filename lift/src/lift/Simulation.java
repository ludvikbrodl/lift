package lift;

public class Simulation extends Thread {
    private static final int MAX_PEOPLE = 20;
    private LiftView lv;
    private Monitor monitor;
    private Person[] people;

    public Simulation() {
        lv = new LiftView();
        monitor = new Monitor();
        Lift lift = new Lift(lv, monitor);
        lift.start();
        people = new Person[20];
        for (int i = 0; i < MAX_PEOPLE; i++) {
            people[i] = new Person(monitor);
            people[i].start();
        }
    }

    public void run() {
        for (int i = 0; i < MAX_PEOPLE; i++) {
            people[i] = new Person(monitor);
        }
        while (true) {
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Är detta nödvändigt? Vi återanvänder väl samma 20 objekt hela tiden och på så sätt dör de aldrig (run metoden terminerar aldrig)
            for (int i = 0; i < MAX_PEOPLE; i++) {
                if (!people[i].isAlive()) {
                    people[i] = new Person(monitor);
                }
            }

        }
    }
}
