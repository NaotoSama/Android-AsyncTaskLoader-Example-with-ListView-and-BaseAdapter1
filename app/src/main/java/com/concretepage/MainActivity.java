package com.concretepage;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

// Video tutorial of loader:  https://youtu.be/Jc5S-dbL_uM
// Loader's pros and cons:  https://youtu.be/5THN9QTCzVU

/**When using a loader in an app, the MainActivity extends from the FragmentActivity. And then implements the LoaderManager LoaderCallbacks interface.
 * The generic type specified is a list of employees.
 * That means the activity must implement the three abstract callback methods, onCreateLoader, onLoaderFinished and onLoaderReset.
 * Notice that the List Employee data type is used throughout the callback methods.
 */
public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Employee>> {

	EmployeeAdapter empAdapter;  //Declare an EmployeeAdapter named empAdapter


    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      empAdapter = new EmployeeAdapter(this, new ArrayList<Employee>());
	      ListView employeeListView = (ListView) findViewById(R.id.employees);
	      employeeListView.setAdapter(empAdapter);
      getSupportLoaderManager().initLoader(1, null, this).forceLoad();
      //To kick off the loader in the first place, we get the LoaderManager and had called an itLoader on it.
      //By the way, getSupportLoaderManager is the same as getLoaderManager. But this version uses the support loader version of the LoaderManager.
      //Within the initLoader call, the manager first checks to see if that loader has already been created and passes back the existing copy if so.
      // If a loader instance doesn't exist, the onCreateLoader callback is triggered to create a new instance. Then we can create a new EmployeeLoader instance and return it.
    }


    @Override 
    public Loader<List<Employee>> onCreateLoader(int id, Bundle args) {  //The onCreateLoader method returns a loader that loads data in the form of a list of employees, the same blob we used above.
        return new EmployeeLoader(MainActivity.this);            //create a new EmployeeLoader, which extends from loader List Employee, so it matches the required return value. Then we return the loader instance.
    }

    @Override 
    public void onLoadFinished(Loader<List<Employee>> loader, List<Employee> data) { //The onLoadFinished method receives two inputs. A loader and a result. The result is of data type of List Employee and the loader load List Employee data.
        empAdapter.setEmployees(data);                                               //We take the list of employees and update the data set in the adapter which will update the list view that it's tied to.
    }

    @Override 
    public void onLoaderReset(Loader<List<Employee>> loader) {  //The onLoadReset method receives one input, the loader itself.
        empAdapter.setEmployees(new ArrayList<Employee>());  //Since we only have one loader in the activity, we can just clear out the previous data that the loader gave us last time. So we set the adapter data to be a new empty list of employees.
    }
}


/**Here's the full scenario, within our activity to kick off a loader in the first place, we get the LoaderManager and call initLoader on it.
 * By the way, getSupportLoaderManager is the same as getLoaderManager. But this version uses the support loader version of the LoaderManager.
 * So within the initLoader call, the manager first checks to see if that loader has already been created and passes back the existing copy if so.
 * If a loader instance doesn't exist, then the onCreateLoader callback is triggered to create a new instance.
 * Then we've created a new EmployeeLoader instance and return it. You notice that after we initialized the loader, the code calls forceLoad.
 * This is required to trigger the loader to start doing the background work. It's good practice though to put the forceLoad call within the loader subclass.
 * You override the onStartLoading method within the loader class and then call forceLoad from there.
 * The oStartLoading method is triggered automatically from initLoader. Okay, once we call forceLoad, then the loader started.
 * The loadInBackground method gets run on a background thread and the list of employees is created and returned as the result of the loader.
 * Now once the loader has finished loading data, it'll inform the LoaderManager which then pass the data to the onLoadFinished method.
 * When the onLloadFinished method is called with the list of employees, then we update the adapter with the new data, causing the list to update and redraw.
 * When our activity's being shut down, the LoaderManager will take care of destroying our loader, and will inform us when the data being provided by the loader is no longer valid.
 * In which case, we should remove it from the UI and stop using it. If the activity is destroyed before a load is finished, then this method won't be called.
 */