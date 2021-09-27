package inf102.h21.management;

import java.util.*;

public class RandomStrategy extends AbstractStrategy {

	private Random random = new Random();

	/**
	 *
	 * @param job - The job to select robots for
	 * @return
	 */
	@Override
	protected List<Robot> selectRobots(Job job) {
		List<Robot> selectedRobots = new LinkedList<>();
	    int robotsNeeded = job.robotsNeeded;

		if (robotsNeeded <= available.size()){
			for (int i = 0; i < robotsNeeded;i++){
				selectedRobots.add(available.remove(random.nextInt(available.size())));
			}
			return selectedRobots;
		}
		return available;
	}


	@Override
	public String getName() {
		return "Random strategy";
	}
}
