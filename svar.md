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



## Task 3 - BetterStrategy
*Enter description*


# Runtime Analysis
For each method of the different strategies give a runtime analysis in Big-O notation and a description of why it has this runtime.

**If you have implemented new methods not listed you must add these as well.**
## Task 1 - RandomStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(1)
  
  public void registerRobots(List<Robot> robots) { O(1)
  this.robots = new ArrayList<Robot>(robots); O(1)
  available = new ArrayList<>(robots); O(1)
  }
* ``registerNewJob(Job job)``: O(n*k)
  
  public void registerNewJob(Job job) {
  backLog.add(job); //O(1)
  doJobs();//O(n*k)
    
* ``registerJobAsFulfilled(Job job)``: O(n*k)
  
  public void registerJobAsFulfilled(Job job, List<Robot> robots) { //O(n*k)
  available.addAll(robots); ///O(n)
  doJobs(); //O(n*k)

### AbstractStrategy (if you use it)
* ``doJobs()``: O(n*k)    

  protected void doJobs() { //O(n*k)

  	while (!backLog.isEmpty()) {//O(m)
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
  
* ``getAvailableRobots()``: O(?)
    * *Insert description of why the method has the given runtime*

### RandomStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(n*k)

  	protected List<Robot> selectRobots(Job job) {
  	List<Robot> selectedRobots = new LinkedList<>();
      int robotsNeeded = job.robotsNeeded;

  	if (robotsNeeded <= available.size()){
  		for (int i = 0; i < robotsNeeded;i++){
  			selectedRobots.add(available.remove(random.nextInt(available.size())));
  		}
  		return selectedRobots;
  	}
  	return available;
  }

## Task 2 - ClosestStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``registerNewJob(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``registerJobAsFulfilled(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*

### AbstractStrategy (if you use it)
* ``doJobs()``: O(?)
  
* ``selectJob()``: O(?)
    * *Insert description of why the method has the given runtime*
* ``removeJob(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``assignRobots(List<Robot> selected, Job job)``: O(?)
  
* ``getAvailableRobots()``: O(?)
    * *Insert description of why the method has the given runtime*

### ClosestStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(n^2) + O(m log m)

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


## Task 3 - BetterStrategy
### IStrategy
* ``registerRobots(List<Robot> robots)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``registerNewJob(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``registerJobAsFulfilled(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*

### AbstractStrategy (if you use it)
* ``doJobs()``: O(?)
    * *Insert description of why the method has the given runtime*
* ``selectJob()``: O(?)
    * *Insert description of why the method has the given runtime*
* ``removeJob(Job job)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``assignRobots(List<Robot> selected, Job job)``: O(?)
    * *Insert description of why the method has the given runtime*
* ``getAvailableRobots()``: O(?)
    * *Insert description of why the method has the given runtime*

### BetterStrategy
* ``selectRobots(Job job, List<Robot> available)``: O(?)
    * *Insert description of why the method has the given runtime*
