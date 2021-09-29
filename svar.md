# Answer File - Mandatory 1
# Description of each Implementation
Briefly describe your implementation in the different strategies. What was your plan and how did you execute it? If there were any problems and/or issues failed implementations please add a description.


## Task 1 - RandomStrategy
My first plan was to assume that the given list already was random. Then I could have added the first, for instance 10 robots that I needed.
This would get way better results. Instead I tried to implement something a little more "advanced", and assume that the given list is not random.

The idea is to iterate through the available robots, and add the necessary  random robots to the selectedRobots list as long  as the amount of needed robots is less than available.size().
To avoid getting the same random robot multiple times, I have to remove it from the available list at the same time as I am adding it to the selectedRobots list.

One problem with this method is that by removing a robot from the list, it results in a "hole" in the list. 


## Task 2 - ClosestStrategy

The idea for this task is to choose the robot that is closest to the cleaning job. In order to do this, a comparator will be used, in combination with a sort method.
This comparator has it's own class (RobotComp) because it will be used by both ClosestStrategy and BetterStrategy.
The comparator compares two robots' distance to its workplace and chooses the shortest distance. 
Furthermore, in the selectRobot method, the available robots will then be sorted with the RobotComp comparator as parameter and uses this to return a sorted list
of available robots. These robots will then be checked if they are available to work.


## Task 3 - BetterStrategy

In BetterStrategy, the plan is to use the same selectRobots method as in ClosestStrategy, but to use priorityQueue to make it better.
In BetterStrategy, a PriorityQueue will take the comparator method from the RobotComp class and use this to sort the backlog (which houses the jobs not yet executed)
according to its natural ordering. This will make the jobs with least amount of work to be placed first in the queue. 

To make it even better, I tried to implement moveFreeRobots such that they moved towards a location where a job had been executed earlier. This did not work.
I did use a distanceToRobots  method to find a mean value to make the distance shorter.

# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well.**
## Task 1 - RandomStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(2n) -> O(n)
  
  public void registerRobots(List<Robot> robots) { O(1)
  this.robots = new ArrayList<Robot>(robots); O(n)
  available = new ArrayList<>(robots); O(n)
  }
* ``registerNewJob(Job job)``: O(k*n*log m)
  
  public void registerNewJob(Job job) {
  backLog.add(job); //O(1)
  doJobs();//O(k*n*log m)
    
* ``registerJobAsFulfilled(Job job)``: O(k*n*log m)
  
  public void registerJobAsFulfilled(Job job, List<Robot> robots) { 
  available.addAll(robots); ///O(n)
  doJobs(); //O(k*n*log m)

### AbstractStrategy (if you use it)
* ``doJobs()``: O(k*n*log m)

  protected void doJobs() { //O(k*n*log m)

  	while (!backLog.isEmpty()) {//O(1) er average. Worst case O(m)
  		Job job = selectJob(); //O(1)
  		List<Robot> selected = selectRobots(job); // O(k*n)

  		if(assignRobots(selected, job)) //O(k*n*logm) + O(n) -> O(k*n*logm)
  			removeJob(job);//O(n)

  		else
  			break;
  	}
  	if(backLog.isEmpty())
  		moveFreeRobots(); //O(1)
  }

The reason why doJobs() in this case is O(k*n*log m) is because it uses the selectRobot method from RandomStrategy, 
which has the running time complexity of O(k*n). This is expected to change when the method is using the selectRobots method 
from ClosestStrategy, because it has a different running time complexity.

* ``selectJob()``: O(1)
  
  protected Job selectJob() {
  return backLog.peek(); //O(1)
  }
  
* ``removeJob(Job job)``: O(n)
  
  protected void removeJob(Job job) { //O(n)
  if(backLog.peek().equals(job))
  backLog.poll();
  else
  backLog.remove(job); //O(n)
  }
  
* ``assignRobots(List<Robot> selected, Job job)``: O(k * log m) * n
  
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

In order to make assignRobots faster, it could have been a solution to use LinkedList instead of ArrayList for available robots, and then remove
the robots by index. If I had an iterator at the location that I want to remove, then the remove function would have constant time, O(1). 

* ``getAvailableRobots()``: O(1)
  public List<Robot> getAvailableRobots(){
  return available; // O(1)
  }

### RandomStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(n*k)

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

## Task 2 - ClosestStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(?)
    Same as in RandomStrategy
* ``registerNewJob(Job job)``: O(?)
    Same as in RandomStrategy
* ``registerJobAsFulfilled(Job job)``: O(?)
    Same as in RandomStrategy

### AbstractStrategy (if you use it)
* ``doJobs()``: O(k*n*logm)

  protected void doJobs() { //O(n log n)

  	while (!backLog.isEmpty()) {//O(1) er average. Worst case O(m)
  		Job job = selectJob(); //O(1)
  		List<Robot> selected = selectRobots(job); // O(n log n)

  		if(assignRobots(selected, job)) //O(k*n*logm) + O(n) -> O(k*n*logm)
  			removeJob(job);//O(n)

  		else
  			break;
  	}
  	if(backLog.isEmpty())
  		moveFreeRobots(); //O(1)
  }
  
* ``selectJob()``: O(1)
  
  protected Job selectJob() {
  return backLog.peek(); //O(1)
  }
  
* ``removeJob(Job job)``: O(n)
  
  protected void removeJob(Job job) { //O(n)
  if(backLog.peek().equals(job))
  backLog.poll();
  else
  backLog.remove(job); //O(n)
  }
  
* ``assignRobots(List<Robot> selected, Job job)``: O(k * log m) * n
  Same time complexity as in RandomStrategy.
  
* ``getAvailableRobots()``: O(1)
  
  public List<Robot> getAvailableRobots(){
  return available; //O(1)
  }

### ClosestStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(n) + O(n log n) -> O(n log n)

  protected List<Robot> selectRobots(Job job) { //O(n log n)
  List<Robot> selectedRobots = new LinkedList<>(); //O(1)
  int robotsNeeded = job.robotsNeeded; //O(1)
  int needed = 0; //O(1)

  	if (robotsNeeded <= available.size()) {
  		Location loc = job.location; //O(1)
  		Collections.sort(available,new RobotComp(loc)); //O(n log n)
  		for (Robot robot : available){ //O(n) 
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


## Task 3 - BetterStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(?)
    Same as in closestStrategy and randomStrategy
* ``registerNewJob(Job job)``: O(?)
    Same as in closestStrategy and randomStrategy
* ``registerJobAsFulfilled(Job job)``: O(?)
    Same as in closestStrategy and randomStrategy 

### AbstractStrategy (if you use it)
* ``doJobs()``: O(n log n) - same as in CloserStrategy since the selectRobots method is the same.

  protected void doJobs() { //O(n log n)

  	while (!backLog.isEmpty()) {//O(1) er average. Worst case O(m)
  		Job job = selectJob(); //O(1)
  		List<Robot> selected = selectRobots(job); // O(n log n)

  		if(assignRobots(selected, job)) //O(k*n*logm) + O(n) -> O(k*n*logm)
  			removeJob(job);//O(n)

  		else
  			break;
  	}
  	if(backLog.isEmpty())
  		moveFreeRobots(); //O(1)
  }

* ``BetterStrategy()``: O(n)

  	public BetterStrategy() { //O(n)
  	Comparator<Job> comparator = new Comparator<Job>() {
  		@Override
  		public int compare(Job o1, Job o2) { //O(n)
  			double dist1 = distanceToRobots(o1); //n
  			double dist2 = distanceToRobots(o2); // + n
  			int result = Integer.compare(o1.robotsNeeded, o2.robotsNeeded);
  			if (result != 0) return result;
  			return Double.compare(dist1,dist2);
  			}
  		};

  	super.backLog = new PriorityQueue<>(comparator);
  }

* ``distanceToRobots(Job job)``: O(n)

  private double distanceToRobots(Job job){ //O(n)
  Location loc = job.location;
  double mean = 0;
  for (Robot robots : available) { //O(n)
  double distance = robots.getLocation().dist(loc);
  mean += distance;
  }
  mean = mean/available.size();

  	return mean;
  }

* ``selectJob()``: O(?)
    Does not use it. Same as I wrote in ClosestStrategy.
* ``removeJob(Job job)``: O(?)
    Does not use it. Same as I wrote in ClosestStrategy.
* ``assignRobots(List<Robot> selected, Job job)``: O(?)
    Does not use it. Same as I wrote in ClosestStrategy.
* ``getAvailableRobots()``: O(?)
    Does not use it. Same as I wrote in ClosestStrategy.

### BetterStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(n log n)
    
  protected List<Robot> selectRobots(Job job) { //O(n) + O(n log n) -> O(n log n)
  	List<Robot> selectedRobots = new LinkedList<>(); //O(1)
  	int robotsNeeded = job.robotsNeeded; //O(1)
  	int needed = 0; //O(1)

  	if (robotsNeeded <= available.size()) {
  		Location loc = job.location; //O(1)
  		Collections.sort(available,new RobotComp(loc)); //O(n log n)
  		for (Robot robot : available){ //O(n) 
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
  
### Added class

public class RobotComp implements Comparator<Robot> {
private final Location workLocation;

    public RobotComp(Location workLocation) {
        this.workLocation = workLocation;
    }


    @Override
    public int compare(Robot o1, Robot o2) { //O(1)
        if (o1.getLocation().dist(workLocation) < o2.getLocation().dist(workLocation)) return 1;
        else if (o1.getLocation().dist(workLocation) > o2.getLocation().dist(workLocation)) return -1;

        return 0;
    }
}
