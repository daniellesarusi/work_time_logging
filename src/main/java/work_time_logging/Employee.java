package work_time_logging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 * Employee class  represents an employee who enters and exits a work place at different times of the day.
 */
public class Employee {
	/*In Java, it is a best practice to make instance variables private and
	 provide public getter and setter methods to access and modify those variables.
	 This is known as encapsulation*/
	
	
    private int id; //integer that uniquely identifies the employee.
    private List<Date[]> enterExitTimes; // a list of arrays where each array contains two elements, the entry and exit times of the employee.

    
    //constructor
    public Employee(int id) {
        this.id = id;
        this.enterExitTimes = new ArrayList<Date[]>();
    }

    // getters setters
    
    public int getId() {
        return id;
    }

    public List<Date[]> getEnterExitTimes() {
        return enterExitTimes;
    }

    
    /*
     * This method is used to add an enter time for an employee.
     * input: enter time for an employee.
     * output: none.
     */
    public void addEnterTime(Date enterTime) {
        if (!enterExitTimes.isEmpty() && enterExitTimes.get(enterExitTimes.size() - 1)[1] == null) {
            // Last entry in list has no exit time yet
            throw new IllegalStateException("Cannot add enter time, exit time has not been set for previous entry.");
        }
        enterExitTimes.add(new Date[] { enterTime, null });
    }

    
    
    /*
     * This method adds an exit time to the last date pair in the list.
     * input: exit time for an employee.
     * output: none.
     */
    public void addExitTime(Date exitTime) {
        if (enterExitTimes.isEmpty() || enterExitTimes.get(enterExitTimes.size() - 1)[1] != null) {
            // No previous entry or last entry already has exit time
            throw new IllegalStateException("Cannot add exit time, no previous entry or exit time already set for last entry.");
        }
        enterExitTimes.get(enterExitTimes.size() - 1)[1] = exitTime;
    }

}

