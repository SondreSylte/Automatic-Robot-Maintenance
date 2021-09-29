package inf102.h21.management;

import java.util.Comparator;

public class RobotComp implements Comparator<Robot> {
    private final Location workLocation;

    public RobotComp(Location workLocation) {
        this.workLocation = workLocation;
    }


    @Override
    public int compare(Robot o1, Robot o2) { //O(1)
        if (o1.getLocation().dist(workLocation) < o2.getLocation().dist(workLocation)) return -1;
        else if (o1.getLocation().dist(workLocation) > o2.getLocation().dist(workLocation)) return 1;

        return 0;
    }
}
