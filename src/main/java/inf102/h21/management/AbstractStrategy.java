package inf102.h21.management;

import java.util.*;

public abstract class AbstractStrategy implements IStrategy {

	/**
	 * List of all robots, both available and occupied
	 */
	protected List<Robot> robots;
	protected List<Robot> available;
	/**
	 * List of jobs not yet executed
	 */
	protected Queue<Job> backLog;

	public AbstractStrategy() {
		backLog = new LinkedList<Job>();

	}

	@Override
	public void registerRobots(List<Robot> robots) {
		this.robots = new ArrayList<>(robots);
		available = new ArrayList<>(robots);
	}

	@Override
	public void registerNewJob(Job job) {
		backLog.add(job);
		doJobs();
	}

	@Override
	public void registerJobAsFulfilled(Job job, List<Robot> robots) {
		available.addAll(robots);
		doJobs();
	}

	/**
	 * Finds jobs in backLog and assigns robots
	 */
	protected void doJobs() { //R: O(m)*(O(k*n)+O(k*n) -> O(m) * 2 *O(k*n) -> O(m*k*n) -> O(k*n)
							// C: O(m) * (O(n log n) + O(k*n)) -> O(m*k*n) -> O(k*n)

		while (!backLog.isEmpty()) {
			Job job = selectJob();
			List<Robot> selected = selectRobots(job);

			if(assignRobots(selected, job))
				removeJob(job);

			else
				break;
		}
		if(backLog.isEmpty())
			moveFreeRobots();
	}

	/**
	 * Selects a Job from the list of available jobs
	 * Currently selects the job at the top of the list
	 *
	 * @return most appropriate job
	 */
	protected Job selectJob() {
		return backLog.peek();
	}

	protected void removeJob(Job job) {
		if(backLog.peek().equals(job))
			backLog.poll();
		else
			backLog.remove(job);
	}

	/**
	 * Select robots for the job. Should select robots most appropriate for the job.
	 *
	 * @param job - The job to select robots for
	 * @param /available - The Robots to select among
	 * @return return list of selected robots if the job can be executed, else return empty list
	 */
	protected abstract List<Robot> selectRobots(Job job);

	/**
	 * When a Robot is not assigned to a Job it is just waiting
	 * We can then position these robots such that when a new job comes in
	 * the robots are already close to the job.
	 */
	protected void moveFreeRobots() { //O(1)
		// TODO: This method could be suited for task 3
	}

	/**
	 * Sends the selected Robots to their Job
	 *
	 * @return true if robots assigned to job, false if not
	 */
	boolean assignRobots(List<Robot> selected, Job job) { // O(k) * O(k*n) + (O(k) * O(log m)) -> O(k * n)

		if (selected == null)
			return false;
		if (selected.isEmpty())
			return false;

		boolean canDo = selected.size() >= job.robotsNeeded;
		for(Robot r : selected) {
			if(r.isBusy()) {
				System.out.println("You selected a robot that was busy.");
				canDo = false;
			}
		}
		if(canDo) {
			for(Robot robot : selected) {
				robot.move(job);
				available.remove(robot);
			}
		}
		else {
			for(Robot r : selected) {
				if(!r.isBusy()) {
					r.move(job.location);
				}
			}
		}
		return canDo;
	}



	/**
	 * Returns list of free robots
	 *
	 * @return list of all available robots
	 */
	public List<Robot> getAvailableRobots(){
		return available;
	}

}


