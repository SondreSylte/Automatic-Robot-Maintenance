package inf102.h21.management;

import java.util.*;

public class ClosestStrategy extends AbstractStrategy {


    /**
     * This method will choose the robots that is closest to the cleaning job.
     * The comparator compares two robots' distance to its workplace and chooses the shortest distance.
     * The available robots will then be sorted with the RobotComp comparator as parameter and uses this to return a sorted list
     * of available robots. These robots will then be checked if they are available to work.
     * @param job - The job to select robots for
     * @return selectedRobots
     */
	@Override
	protected List<Robot> selectRobots(Job job) { //O(n) + O(n log n) -> O(n log n)
		List<Robot> selectedRobots = new LinkedList<>(); //O(1)
		int robotsNeeded = job.robotsNeeded; //O(1)
		int needed = 0; //O(1)

		if (robotsNeeded <= available.size()) {
			Location loc = job.location; //O(1)
			Collections.sort(available,new RobotComp(loc)); //O(n log n)
			for (Robot robot : available){ //O(n) * O(1) -> O(n)
				if (!robot.isBusy()){
					selectedRobots.add(robot); //O(1)
					needed++;
				}
				if (robotsNeeded == needed){ //O(1)
					break;
				}
			}
		}
		return selectedRobots;
	}


	@Override
	public String getName() {
		return "Closest strategy";
	}

}

