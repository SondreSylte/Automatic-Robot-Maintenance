package inf102.h21.management;

import java.util.*;

public class BetterStrategy extends AbstractStrategy {


	public BetterStrategy() { //O(n)
		Comparator<Job> comparator = new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {
				double dist1 = distanceToRobots(o1);
				double dist2 = distanceToRobots(o2);
				int result = Integer.compare(o1.robotsNeeded, o2.robotsNeeded);
				if (result != 0) return result;
				return Double.compare(dist1,dist2);
				}
			};

		super.backLog = new PriorityQueue<>(comparator);
	}


	@Override
	protected List<Robot> selectRobots(Job job) {

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


	private double distanceToRobots(Job job){ //O(n)
		Location loc = job.location;
		double mean = 0;
		for (Robot robots : available) {
			double distance = robots.getLocation().dist(loc);
			mean += distance;
		}
		mean = mean/available.size();

		return mean;
	}

	@Override
	public String getName() {
		return "Better strategy";
	}
}


