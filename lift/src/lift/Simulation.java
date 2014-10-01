package lift;

public class Simulation {
    private static final int MAX_PEOPLE = 5;
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
}
