package inf102.h21.management;

import java.util.*;

public class BetterStrategy extends AbstractStrategy {

	protected Location mean;

	public BetterStrategy() {
		Comparator<Job> comparator = new Comparator<Job>() {
			@Override
			public int compare(Job o1, Job o2) {
				double dist1 = distanceToRobots(o1);
				double dist2 = distanceToRobots(o2);
				int o1Needed = o1.robotsNeeded;
				int o2Needed = o2.robotsNeeded;
				int result = Integer.compare(o1Needed, o2Needed);
				if (result != 0) return result;
				return Double.compare(dist1,dist2);
				}
			};


		super.backLog = new PriorityQueue<>(comparator);
	}

	@Override
	protected List<Robot> selectRobots(Job job) {

		List<Robot> robotsReady = new LinkedList<>();
		int robotsNeeded = job.robotsNeeded;
		int needed = 0;

		if (robotsNeeded <= available.size()) {
			Location loc = job.location;
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
	public void moveFreeRobots(){
		this.available.forEach((Robot robot) -> {
			robot.move(this.findMeanLocation());
		});
	}

	public Location findMeanLocation(){
		return new Location(200,750);
	}



	private double distanceToRobots(Job job){
		Location loc = job.location;
		double mean = 0;
		for (Robot robots : available) {
			double distance = robots.getLocation().dist(loc);
			mean += distance;
		}
		mean = mean/available.size();

		return mean;
	}

/*
	protected void moveFreeRobots(Job job) {
		for (Robot robot : available) {
			robot.move(mean); //mean
		}
	}
*/
	@Override
	public String getName() {
		return "Better strategy";
	}
}


