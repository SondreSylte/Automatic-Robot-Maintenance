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
		List<Robot> selectedRobots = new LinkedList<>();
		int robotsNeeded = job.robotsNeeded;
		int needed = 0;

		if (robotsNeeded <= available.size()) {
			Location loc = job.location;
			Collections.sort(available,new RobotComp(loc));
			for (Robot robot : available){
				if (!robot.isBusy()){
					selectedRobots.add(robot);
					needed++;
				}
				if (robotsNeeded == needed){
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

