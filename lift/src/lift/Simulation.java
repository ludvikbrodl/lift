package lift;

public class Simulation {
	private static final int MAX_PEOPLE = 20;

	public Simulation() {
		LiftView liftView = new LiftView();
		Monitor monitor = new Monitor(liftView);
		for (int i = 0; i < MAX_PEOPLE; i++) {
			new Person(monitor).start();
		}
		new Lift(liftView, monitor).start();
	}
}
