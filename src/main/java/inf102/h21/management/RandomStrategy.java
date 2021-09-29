package inf102.h21.management;

import java.util.*;

public class RandomStrategy extends AbstractStrategy {

	private Random random = new Random(); //O(1)

	/**
	 * This method will return a list of random chosen robots that are available,
	 * based on how many robots that are needed for the job.
	 * The robots will be added to the selectedRobots for the job, and removed from the available list
	 * at the same time.
	 * @param job - The job to select robots for
	 * @return selectedRobots
	 */
	@Override
	protected List<Robot> selectRobots(Job job) { // O(k*2n) -> O(k*n)
		List<Robot> selectedRobots = new LinkedList<>(); //O(1)
	    int robotsNeeded = job.robotsNeeded; //O(1)

		if (robotsNeeded <= available.size()){ //O(1)
			for (int i = 0; i < robotsNeeded;i++){ //O(k)
				selectedRobots.add(available.remove(random.nextInt(available.size()))); // O(n) + O(n) -> O(2n)
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
