package hu.test;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class InvokedMethodWithParameters extends Statement
{
    private final FrameworkMethod fTestMethod;
	private Object fTarget;
    private Object[] fParams;

    public InvokedMethodWithParameters(FrameworkMethod testMethod, Object target, Object... params)
    {
        fTestMethod = testMethod;
        fTarget = target;
        fParams = params;
    }
    
    @Override
	public void evaluate() throws Throwable {
		fTestMethod.invokeExplosively(fTarget, fParams);
	}

}
