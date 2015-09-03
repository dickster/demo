package demo.wicketTest;

import com.google.common.base.CaseFormat;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import demo.wicketTest.WicketTest.BrovadaWicketTester;
import org.junit.Test;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;


public class BrovadaWicketTestRunner extends Suite {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface WithContext {
		public String[] value() default {};
	}
				
	private final ArrayList<Runner> runners = new ArrayList<Runner>();
	private String testContextOverride;
	private ThreadLocal<BrovadaWicketTester> wicketTester = new ThreadLocal<BrovadaWicketTester>();

	public BrovadaWicketTestRunner(Class<?> klass) throws Throwable {
		super(klass, Collections.<Runner>emptyList());
		testContextOverride = System.getProperty("testContext", null);
		for (BrovadaWicketTestContext context:getWicketTestContexts()) {
			runners.add(new TestClassRunner(getTestClass().getJavaClass(),context));
		}
	}

	private List<BrovadaWicketTestContext> getWicketTestContexts() {
        Set<Method> tests = getAllMethods(getTestClass().getJavaClass(), withAnnotation(Test.class));
		List<BrovadaWicketTestContext> result = Lists.newArrayList();
		for (Method test:tests) {
    		result.addAll(createWicketTestContexts(test, getContextForMethod(test)));
		}
		return result;
	}

	private List<String> getContextForMethod(Method method) {
		WithContext withContext = method.getAnnotation(WithContext.class);
        if (!getTestContextOverride().isEmpty()) {
            return getTestContextOverride();
        }
        return withContext==null || withContext.value().length==0 ?
                Lists.newArrayList(BrovadaWicketTestContext.DEFAULT) :
                Lists.newArrayList(withContext.value());
	}

	private List<String> getTestContextOverride() {
        return parseTestContextOverrides();
	}

	private List<String> parseTestContextOverrides() {
        List<String> result = Lists.newArrayList();
        if (testContextOverride!=null) {
            for (String context:testContextOverride.split(",")) {
                result.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, context.trim()));
            }
        }
        return result;
	}

	private List<BrovadaWicketTestContext> createWicketTestContexts(Method method, List <String> contexts) {
        ArrayList<BrovadaWicketTestContext> result = Lists.newArrayList();
		for (String context:contexts) {
			result.add(new BrovadaWicketTestContext(method, context));
		}
		return result;
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}


	
	private class TestClassRunner extends BlockJUnit4ClassRunner {

		private BrovadaWicketTestContext context;
		
		TestClassRunner(Class<?> type, BrovadaWicketTestContext context) throws InitializationError {
			super(type);
			this.context = context;
		}

		@Override  
	    protected Statement methodBlock(final FrameworkMethod method) {  
	        return new Statement() {  
	            @Override public void evaluate() throws Throwable {
                    TestClassRunner.super.methodBlock(method).evaluate();  
	            }
	        };
		}

		@Override
		protected List<FrameworkMethod> getChildren() {
			List<FrameworkMethod> children = super.getChildren();
			Predicate<FrameworkMethod> filter = new Predicate<FrameworkMethod>() {
		        @Override
		        public boolean apply(FrameworkMethod method) {
		            return method.getMethod().equals(context.getMethod());
		        }
		    };
		    return ImmutableList.copyOf(Iterables.filter(children, filter));
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public Object createTest() throws Exception {
			Object instance = getTestClass().getOnlyConstructor().newInstance();
			if (instance instanceof WicketTest) {
				WicketTest<?, ?> wicketTest = ((WicketTest<?,?>) instance);
				if (getWicketTester()==null) {   
					setWicketTester(wicketTest.createWicketTesterForRunner());
				}
				wicketTest.initializeForRunner(getWicketTester(), context);
				return instance;
			}
			throw new IllegalArgumentException("your test class must be of type " + WicketTest.class.getSimpleName() + " for this runner.");
		}

		private void setWicketTester(BrovadaWicketTester tester) {
			wicketTester.set(tester);
		}

		private BrovadaWicketTester getWicketTester() {			
			return wicketTester.get();
		}

		@Override
		protected String getName() {
			return String.format("[%s]", context);
		}

		@Override
		protected String testName(final FrameworkMethod method) {
			return String.format("%s[%s]", method.getName(),
					context);
		}

		@Override
		protected void validateConstructor(List<Throwable> errors) {
			validateOnlyOneConstructor(errors);
		}

		@Override
		protected Statement classBlock(RunNotifier notifier) {
			return childrenInvoker(notifier);
		}
	}

	
}
