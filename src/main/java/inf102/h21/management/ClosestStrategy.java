package inf102.h21.management;

import java.util.*;

public class ClosestStrategy extends AbstractStrategy {


	@Override
	protected List<Robot> selectRobots(Job job) { //O(n^2) + O(m log m)
		List<Robot> selectedRobots = new LinkedList<>(); //O(1)
		int robotsNeeded = job.robotsNeeded; //O(1)
		int needed = 0; //O(1)

		if (robotsNeeded <= available.size()) {
			Location loc = job.location; //O(1)
			Collections.sort(available,new RobotComp(loc)); //O(m log m)
			for (Robot robot : available){ //O(n) * O(n) -> O(n^2)
				if (!robot.isBusy()){
					selectedRobots.add(robot); //O(n)
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

