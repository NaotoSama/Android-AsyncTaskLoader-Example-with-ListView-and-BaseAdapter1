package com.concretepage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**The goal of this code is to show a list of employees in a ListView by loading the data from a background thread within AsyncTaskLoader.
 * AsyncTaskLoader inherits behavior from the loader class. It's also a generic class with the generic parameter D.
 * Parameter D is being used as the return value of the loadInBackground method.
 * This method is also abstract, meaning we need to define the behavior and we need to return a value with any data type of our choosing.
 */
public class EmployeeLoader extends AsyncTaskLoader<List<Employee>> {  // We extend from the AsyncTaskLoader class. For the generic parameter in brackets, we specify a list of employees. That means that the return value of the loadInBackground method is a list of employees.
    public EmployeeLoader(Context context) {						   // We have a constructor to create a new EmployeeLoader instance
		super(context);												   // Call the super class constructor and pass in the context
    }
	@Override 
    public List<Employee> loadInBackground() {
    	List<Employee> list = new ArrayList<Employee>();  			 //Create a new array list of employees
    	list.add(new Employee("emp1", "Brahma"));	 //Add three entries to the list
    	list.add(new Employee("emp2", "Vishnu"));
    	list.add(new Employee("emp3", "Mahesh"));
        return list;										 //Return the list
    }
}