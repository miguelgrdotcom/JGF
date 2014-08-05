package hu.test;

import hu.dsl.HUSetRecipe;
import hu.test.annotations.method.HUAfterClass;
import hu.test.annotations.method.HUBeforeClass;
import hu.test.annotations.method.HUDistributedTest;
import hu.test.annotations.method.HUGatheredTest;
import hu.test.annotations.method.HUTest;
import hu.test.classloader.HUWovenClassLoader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.internal.runners.statements.RunBefores;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class HUTestRunner extends BlockJUnit4ClassRunner
{
	/** Test class for HPCUnit Test. this is loaded from HUWovenClassLoader **/
    private final TestClass huTestCase;
    
    /** HPCUnit annotations (e.g. @HUTest) **/
    private List<Class<? extends Annotation>> huTestAnnotations;
    
    /** CalculationAreas that were got from test programs **/
    private Map<FrameworkMethod, List<HUSetRecipe>> huTestParameters;

    public HUTestRunner(Class<?> klass) throws InitializationError, ClassNotFoundException, IOException
    {
        super(klass);
        makeHUTestAnnotions();
        weaveHUTrace();
        huTestCase = new TestClass(HUWovenClassLoader.getInstance().loadClass(klass.getCanonicalName()));
    }
    
    /**
     * define annotations which are used in HUTest
     */
    private void makeHUTestAnnotions() {
        huTestAnnotations = new ArrayList<Class<? extends Annotation>>();
        huTestAnnotations.add(HUTest.class           );
        huTestAnnotations.add(HUDistributedTest.class);
        huTestAnnotations.add(HUGatheredTest.class   );
    }
    
    /**
     * weave Aspects to an application 
     * @throws IOException 
     */
    private void weaveHUTrace() throws IOException {
        huTestParameters = HUWovenClassLoader.weaveHUTrace(getHUTestMethods());
    }

    /**
     * @return a list of HUTest methods
     */
    private List<FrameworkMethod> getHUTestMethods () {
        List<FrameworkMethod> tests = new ArrayList<FrameworkMethod>();
        for (Class<? extends Annotation> annotation: huTestAnnotations)
            tests.addAll(getTestClass().getAnnotatedMethods(annotation));
        return tests;
    }

    /**
     * verify test methods
     * normal test methods must be public and have no parameters.
     * hutest methods ... TODO
     */
    @Override
    protected void validateTestMethods(List<Throwable> errors) {
		validatePublicVoidNoArgMethods(Test.class, false, errors);
		//validatePublicVoidNoArgMethods(HUTest.class, false, errors);
		//validatePublicVoidNoArgMethods(HUGatheredTest.class, false, errors);
		//validatePublicVoidNoArgMethods(HUDistributedTest.class, false, errors);
	}
    
    /**
     * define the order of methods
     * add HUBeforeClass and HUAfterClass methods
     */
    @Override
    protected Statement classBlock(final RunNotifier notifier) {
		Statement statement= childrenInvoker(notifier);
		statement= withHUBeforeClasses(statement);
		statement= withBeforeClasses(statement);
		statement= withHUAfterClasses(statement);
		statement= withAfterClasses(statement);
		statement= withClassRules(statement);
		return statement;
	}

    /**
	 * Returns a {@link Statement}: run all non-overridden {@code @HUBeforeClass} methods on this class
	 * and superclasses before executing {@code statement}; if any throws an
	 * Exception, stop execution and pass the exception on.
	 */
    private Statement withHUBeforeClasses(Statement statement) {
		List<FrameworkMethod> befores= huTestCase
				.getAnnotatedMethods(HUBeforeClass.class);
		return befores.isEmpty() ? statement :
			new RunBefores(statement, befores, null);
	}
    
	/**
	 * Returns a {@link Statement}: run all non-overridden {@code @HUAfterClass} methods on this class
	 * and superclasses before executing {@code statement}; all AfterClass methods are
	 * always executed: exceptions thrown by previous steps are combined, if
	 * necessary, with exceptions from AfterClass methods into a
	 * {@link MultipleFailureException}.
	 */
    private Statement withHUAfterClasses(Statement statement) {
		List<FrameworkMethod> afters= huTestCase
				.getAnnotatedMethods(HUAfterClass.class);
		return afters.isEmpty() ? statement : 
			new RunAfters(statement, afters, null);
	}

    /**
	 * Returns a {@link Statement}: apply all 
	 * static fields assignable to {@link TestRule}
	 * annotated with {@link ClassRule}.
	 *
	 * @param statement
	 *            the base statement
	 * @return a RunRules statement if any class-level {@link Rule}s are
	 *         found, or the base statement
	 */
	private Statement withClassRules(Statement statement) {
		List<TestRule> classRules= classRules();
		return classRules.isEmpty() ? statement :
		    new RunRules(statement, classRules, getDescription());
	}
    
	/**
	 * @return a list of test methods
	 */
    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> tests = new ArrayList<FrameworkMethod>();
        tests.addAll(getTestClass().getAnnotatedMethods(Test.class));
        tests.addAll(getTestClass().getAnnotatedMethods(HUTest.class));
        tests.addAll(getTestClass().getAnnotatedMethods(HUDistributedTest.class));
        tests.addAll(getTestClass().getAnnotatedMethods(HUGatheredTest.class));
        return tests;
    }
    
    /**
     * hook HPCUnit test methods 
     */
    @Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description= describeChild(method);
		if (method.getAnnotation(Ignore.class) != null) {
		    notifier.fireTestIgnored(description);
		} 
		else if(isHPCUnitest(method)) {
		    runHUTest(method, notifier, description);
		}
		else {
		    runLeaf(methodBlock(method), description, notifier);
		}
	}
    
    /**
     * @param method
     * @return If method is HPCUnit test method, return true
     */
    private boolean isHPCUnitest(FrameworkMethod method) {
    	return method.getAnnotation(HUGatheredTest.class) != null || 
		        method.getAnnotation(HUDistributedTest.class) != null || 
		        method.getAnnotation(HUTest.class) != null;
    }
    
    /**
     * weave aspect code to a test program
     * @param method 
     * @return 
     */
    private FrameworkMethod getAspectWovenMethod(FrameworkMethod method) {
        Method rawMethod = method.getMethod();
        FrameworkMethod wovenMethod = null;
        try
        {
            wovenMethod = new FrameworkMethod(huTestCase.getJavaClass().getDeclaredMethod(rawMethod.getName(), rawMethod.getParameterTypes()));
        }
        catch (NoSuchMethodException e1) { throw new RuntimeException(e1);}
        catch (SecurityException e1){ throw new RuntimeException(e1);}
        return wovenMethod;
    }
    
    /**
     * @param method
     * @param notifier
     * @param description
     */
    private void runHUTest(final FrameworkMethod method, RunNotifier notifier, Description description) {
        FrameworkMethod wovenMethod = getAspectWovenMethod(method);
        List<HUSetRecipe> recipes = huTestParameters.get(method);
        
        Class<?>[] paramTypes = new Class<?>[]{Object.class, Method.class, HUSetRecipe[].class};
        HUSetRecipe[] params = recipes.toArray(new HUSetRecipe[recipes.size()]);
       
        if(wovenMethod.getAnnotation(HUTest.class) != null) {
            runLeaf(methodBlockWithStaticMethod(true, wovenMethod,"hu.test.HUTestWrapper","$huTest", paramTypes, params), description, notifier);
        }
        else if(wovenMethod.getAnnotation(HUGatheredTest.class) != null) {
            runLeaf(methodBlockWithStaticMethod(true, wovenMethod,"hu.test.HUTestWrapper","$huGatheredTest", paramTypes, params), description, notifier);
        }
        else if(wovenMethod.getAnnotation(HUDistributedTest.class) != null) {
            runLeaf(methodBlockWithStaticMethod(true, wovenMethod,"hu.test.HUTestWrapper","$huDistributedTest", paramTypes, params), description, notifier);
        }
    }
    
    protected Statement methodBlockWithSuperMethod(FrameworkMethod method, String superClass, String superMethod)
    {
        Method superM;
        try
        {
            Class<?> superC = Class.forName(superClass);
            superM = superC.getDeclaredMethod(superMethod, new Class<?>[]{Method.class});
        }
        catch (NoSuchMethodException e) {throw new RuntimeException(e);}
        catch (SecurityException e)     {throw new RuntimeException(e);}
        catch (ClassNotFoundException e){throw new RuntimeException(e);}
        FrameworkMethod fm = new FrameworkMethod(superM);
        return methodBlockWithParameters(fm, method.getMethod()); 
    }
    
    protected Statement methodBlockWithStaticMethod(final boolean isWoven, FrameworkMethod method, String className, String methodName, Class<?>[] paramTypes, Object[] params)
    {
        Method staticM;
        try
        {
            Class<?> staticC = Class.forName(className);
            staticM = staticC.getDeclaredMethod(methodName, paramTypes);
        }
        catch (NoSuchMethodException e) {throw new RuntimeException(e);}
        catch (SecurityException e)     {throw new RuntimeException(e);}
        catch (ClassNotFoundException e){throw new RuntimeException(e);}
        FrameworkMethod fm = new FrameworkMethod(staticM);

        Object test;
        try {
            test= new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    if(isWoven)
                        return createWovenTest();
                    else
                        return createTest();
                }
            }.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return methodBlockWithTargetParameters(fm, null, new Object[]{test,method.getMethod(),params}); 
    }
    
	private Object createWovenTest() throws Exception {
		return huTestCase.getOnlyConstructor().newInstance();
    }

    protected Statement methodBlockWithParameters(FrameworkMethod method, Object... params) {
        Object test;
        try {
            test= new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return methodBlockWithTargetParameters(method, test, params);
    }
    
    @SuppressWarnings("deprecation")
    protected Statement methodBlockWithTargetParameters(FrameworkMethod method, Object target, Object... params) {
        Statement statement= methodInvokerWithParameters(method, target, params);
        statement= possiblyExpectingExceptions(method, target, statement);
        statement= withPotentialTimeout(method, target, statement);
        statement= withBefores(method, target, statement);
        statement= withAfters(method, target, statement);
        statement= withRules(method, target, statement);
        return statement;
    }

    protected Statement methodInvokerWithParameters(FrameworkMethod method, Object test, Object... params) {
        return new InvokedMethodWithParameters(method, test, params);
    }
    
    private Statement withRules(FrameworkMethod method, Object target,
            Statement statement) {
        Statement result= statement;
        result= withMethodRules(method, target, result);
        result= withTestRules(method, target, result);
        return result;
    }

    @SuppressWarnings("deprecation")
    private Statement withMethodRules(FrameworkMethod method, Object target,
            Statement result) {
        List<TestRule> testRules= getTestRules(target);
        for (org.junit.rules.MethodRule each : getMethodRules(target))
            if (! testRules.contains(each))
                result= each.apply(result, method, target);
        return result;
    }

    @SuppressWarnings("deprecation")
    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        return rules(target);
    }
    
    private Statement withTestRules(FrameworkMethod method, Object target,
            Statement statement) {
        List<TestRule> testRules= getTestRules(target);
        return testRules.isEmpty() ? statement :
            new RunRules(statement, testRules, describeChild(method));
    }
}
