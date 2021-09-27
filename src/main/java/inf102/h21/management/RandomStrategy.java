package inf102.h21.management;

import java.util.*;

public class RandomStrategy extends AbstractStrategy {

	private Random rand = new Random();

	/**
	 *
	 * @param job - The job to select robots for
	 * @return
	 */
	@Override
	protected List<Robot> selectRobots(Job job) {
		List<Robot> robotsReady = new LinkedList<>();
	    List<Robot> robotsAvailable = getAvailableRobots();
	    int robotsNeeded = job.robotsNeeded;

		if (robotsNeeded <= robotsAvailable.size()){
			for (int i = 0; i < robotsNeeded;i++){
				robotsReady.add(robotsAvailable.remove(rand.nextInt(robotsAvailable.size())));
			}
			return robotsReady;
		}
		return robotsAvailable;
	}


	@Override
	public String getName() {
		return "Random strategy";
	}
}
