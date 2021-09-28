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
		backLog = new LinkedList<Job>(); //O(1)

	}

	@Override
	public void registerRobots(List<Robot> robots) { //O(2n) -> O(n)
		this.robots = new ArrayList<Robot>(robots); //O(n)
		available = new ArrayList<>(robots); //O(n)
	}

	@Override
	public void registerNewJob(Job job) { //O(n*k)
		backLog.add(job); //O(1)
		doJobs();//O(n*k)
	}

	@Override
	public void registerJobAsFulfilled(Job job, List<Robot> robots) { //O(n*k)
		available.addAll(robots); ///O(n)
		doJobs(); //O(n*k)
	}

	/**
	 * Finds jobs in backLog and assigns robots
	 */
	protected void doJobs() { //O(n*k)

		while (!backLog.isEmpty()) {//O(1) er average. Worst case O(m)
			Job job = selectJob(); //O(1)
			List<Robot> selected = selectRobots(job); // O()

			if(assignRobots(selected, job)) //O((k^2)*n*logm)
				removeJob(job);//O(n)

			else
				break;
		}
		if(backLog.isEmpty())
			moveFreeRobots(); //O(1)
	}

	/**
	 * Selects a Job from the list of available jobs
	 * Currently selects the job at the top of the list
	 *
	 * @return most appropriate job
	 */
	protected Job selectJob() {
		return backLog.peek(); //O(1)
	}

	protected void removeJob(Job job) { //O(n)
		if(backLog.peek().equals(job))
			backLog.poll();
		else
			backLog.remove(job); //O(n)
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
	boolean assignRobots(List<Robot> selected, Job job) { // O(k) * O(k*n*log m) + (O(k) * O(log m)) -> O(k * log m) * n

		if (selected == null) //O(1)
			return false;
		if (selected.isEmpty()) //O(1)
			return false;

		boolean canDo = selected.size() >= job.robotsNeeded; //O(1)
		for(Robot r : selected) { //O(k)
			if(r.isBusy()) { //O(1)
				System.out.println("You selected a robot that was busy."); //O(1)
				canDo = false; //O(1)
			}
		}
		if(canDo) { //O(1)
			for(Robot robot : selected) { //O(k)
				robot.move(job);//O(log m)
				available.remove(robot); //O(n)
			}
		}
		else {
			for(Robot r : selected) { // O(k) * O(log m)
				if(!r.isBusy()) {
					r.move(job.location); // O(log m)
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


