package inf102.h21.management;

import java.util.*;

public class ClosestStrategy extends AbstractStrategy {



	@Override
	protected List<Robot> selectRobots(Job job) {
		List<Robot> robotsReady = new LinkedList<>();
		int robotsNeeded = job.robotsNeeded;
		int needed = 0;

		if (robotsNeeded <= available.size()) {
			Location loc = job.location;
			//RobotComperator compare = new RobotComperator(loc);
			Collections.sort(available,new RobotComp(loc));
			for (Robot robot : available){
				if (!robot.isBusy()){
					robotsReady.add(robot);
					needed++;
				}
				if (robotsNeeded == needed){
					break;
				}
			}
		}
		return robotsReady;
	}


	@Override
	public String getName() {
		return "Closest strategy";
	}

}

