package work_time_logging;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * This class used to mark a class as a RESTful web service.
 * contains handle HTTP requests and also return the response in a format that is suitable for the web,
 *  such as JSON or XML. 
 */
@RestController
public class EmployeeController {

    private Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();

    /*
     * This function mapped to the HTTP POST request at the URL path "/enter".
     * input: integer parameter "id" to be passed in as a request parameter.
     * output:  returns a ResponseEntity object with an HTTP status
     * 			 code of 200 (OK) to indicate that the request was successful.
     */
    @PostMapping("/enter")
    public ResponseEntity<Void> addEnterTime(@RequestParam int id) {
        Employee employee = employeeMap.getOrDefault(id, new Employee(id));
        employee.addEnterTime(new Date());
        employeeMap.put(id, employee);

        return ResponseEntity.ok().build();
    }

    /*
     * This function handles a POST request to add an exit time for an employee with a specified ID.
     * input: integer parameter "id".
     * output: returns an HTTP response with a status code of 200 OK to indicate that the operation was successful.
     */
    @PostMapping("/exit")
    public ResponseEntity<Void> addExitTime(@RequestParam int id) {
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            throw new IllegalArgumentException("No employee found with ID " + id);
        }
        employee.addExitTime(new Date());
        employeeMap.put(id, employee);

        return ResponseEntity.ok().build();
    }
    
    
    /*
     * HTTP GET request, it retrieves information about all employees that are currently in the
     *  system and returns it in the form of a map.
     *  input: none.
     *  output: returns the responseMap HashMap. This response map will contain a list of maps, 
     *  		where each map represents an employee and their information.
     *
     */
    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> responseMap = new HashMap<String, Object>();
        List<Map<String, Object>> employeeInfoList = new ArrayList<Map<String, Object>>();
        for (Employee employee : employeeMap.values()) {
            employeeInfoList.add(getEmployeeInfoMap(employee));
        }
        responseMap.put("employees", employeeInfoList);
        return responseMap;
    }

    
    /*
     * The method is a GET request handler for retrieving the information of a specific employee based on their ID.
     * input: integer parameter "id".
     * output: information of the employee .
     */
    @GetMapping("/info/{id}")
    public Map<String, Object> getInfoById(@PathVariable int id) {
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            throw new IllegalArgumentException("No employee found with ID " + id);
        }
        Map<String, Object> responseMap = new HashMap<String, Object>();
        List<Map<String, Object>> employeeInfoList = new ArrayList<Map<String, Object>>();
        employeeInfoList.add(getEmployeeInfoMap(employee));
        responseMap.put("employees", employeeInfoList);
        return responseMap;
    }

    
    
    /*
     * The `getEmployeeInfoMap` method takes an instance of the `Employee` class as a parameter
     *  and returns a `Map<String, Object>` object that contains information about that employee.
     * input: instance of the `Employee` class.
     * output: returns a `Map<String, Object>` object that contains information about that employee.
     */
    private Map<String, Object> getEmployeeInfoMap(Employee employee) {
        Map<String, Object> employeeInfo = new HashMap<String, Object>();
        employeeInfo.put("employee_id", employee.getId());
        
        List<List<String>> dates = new ArrayList<List<String>>();
        for (Date[] enterExitTimes : employee.getEnterExitTimes()) {
            List<String> datePair = new ArrayList<String>();
            if (enterExitTimes[1] == null) {
                datePair.add(enterExitTimes[0].toString() + " # Only enter time was received");
                dates.add(datePair);
            } else {
                datePair.add(enterExitTimes[0].toString());
                datePair.add(enterExitTimes[1].toString());
                dates.add(datePair);
            }
        }
        employeeInfo.put("dates", dates);

        return employeeInfo;
    }
}
