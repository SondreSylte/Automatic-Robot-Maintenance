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
		this.robots = new ArrayList<Robot>(robots);
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
	protected void doJobs() {

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
	protected void moveFreeRobots() {
		// TODO: This method could be suited for task 3
	}

	/**
	 * Sends the selected Robots to their Job
	 *
	 * @return true if robots assigned to job, false if not
	 */
	boolean assignRobots(List<Robot> selected, Job job) {
		if (selected == null || selected.size() < job.robotsNeeded) {
			return false;
		}
		boolean canDo = selected.size() >= job.robotsNeeded;
		for (Iterator<Robot> iterator = selected.iterator(); iterator.hasNext(); ) {
			Robot robot = iterator.next();
			if (canDo) {
				if (!robot.isBusy()) {
					robot.move(job);
					iterator.remove();
				} else {
					canDo = false;
				}
			} else if (!robot.isBusy()) {
				robot.move(job.location);
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


