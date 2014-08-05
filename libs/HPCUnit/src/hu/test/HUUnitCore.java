package hu.test;

import hu.test.classloader.HUNormalClassLoader;

import java.util.ArrayList;
import java.util.List;

import junit.runner.Version;

import org.junit.internal.JUnitSystem;
import org.junit.internal.RealSystem;
import org.junit.internal.TextListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class HUUnitCore extends JUnitCore
{
	/**
	 * Run the tests contained in the classes named in the <code>args</code>.
	 * If all tests run successfully, exit with a status of 0. Otherwise exit with a status of 1.
	 * Write feedback while tests are running and write
	 * stack traces for all failed tests after the tests all complete.
	 * @param args names of classes in which to find tests to run
	 */
	public static void main(String... args) {
		runMainAndExit(new RealSystem(), args);
	}

    /**
	 * Do not use. Testing purposes only.
	 * @param system 
	 */
	public static void runMainAndExit(JUnitSystem system, String... args) {
		Result result= new HUUnitCore().runMain(system, args);
		system.exit(result.wasSuccessful() ? 0 : 1);
	}

	/**
	 * Do not use. Testing purposes only.
	 * @param system 
	 */
    @Override
	public Result runMain(JUnitSystem system, String... args) {
		system.out().println("JUnit version " + Version.id());
		List<Class<?>> classes= new ArrayList<Class<?>>();
		List<Failure> missingClasses= new ArrayList<Failure>();
		for (String each : args)
			try {
			    // load classes from test.jar or app.jar and then load classes from class path
			    HUNormalClassLoader loader = new HUNormalClassLoader();
			    classes.add(loader.loadClass(each));
			} catch (ClassNotFoundException e) {
				system.out().println("Could not find class: " + each);
				Description description= Description.createSuiteDescription(each);
				Failure failure= new Failure(description, e);
				missingClasses.add(failure);
			}
		RunListener listener= new TextListener(system);
		addListener(listener);
		Result result= run(classes.toArray(new Class[0]));
		for (Failure each : missingClasses)
			result.getFailures().add(each);
		return result;
	}
}
