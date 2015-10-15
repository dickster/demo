package demo.wicketTest;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import org.apache.wicket.Component;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Set;

import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withTypeAssignableTo;

public abstract class WicketTest<T extends WicketHarness,F extends Component>  extends AbstractWicketTest implements IFixtureFactory<F> {
	
    private T harness;


	public abstract F createFixture(String id);

	protected T createHarness() {
        Class<T> harnessClass = getHarnessClass();
        return makeHarness(harnessClass, null, getPath());
    }

    private <H extends WicketHarness> H makeHarness(Class<H> harnessClass, @Nullable WicketHarness parent, String path) {
        H instance = null;
        try {
            instance = harnessClass.newInstance();
            instance.withPath(path).withWicketTester(wicketTester);
            return inject(instance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(parent==null ?
                "can't create harness @ " + path + " of type " + harnessClass.getSimpleName() :
                "can't create harness @ " + path + " of type " + harnessClass.getSimpleName() + " in parent harness " + parent.getClass().getSimpleName());
    }

    private final <H extends WicketHarness> H inject(H parent) {
        Set<Field> harnesses = getAllFields(parent.getClass(), withAnnotation(Harness.class), withTypeAssignableTo(WicketHarness.class));
        for (Field harnessField:harnesses) {
            injectField(harnessField, parent);
        }
        return parent;
    }

    private WicketHarness injectField(Field harnessField, WicketHarness parent) {
        Preconditions.checkArgument(harnessField.getAnnotation(Harness.class)!=null, "must have @"+Harness.class.getSimpleName() + " annotated field for harnesses");
        Harness annotation = harnessField.getAnnotation(Harness.class);
        String path = parent.getPathFor(annotation.value());
        Class<? extends WicketHarness> type = (Class<T>) harnessField.getType();
        WicketHarness harness = makeHarness(type, parent, path);
        harnessField.setAccessible(true);
        try {
            harnessField.set(parent, harness);
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return harness;
    }


    protected abstract Class<T> getHarnessClass();

	public void renderFixture() {
        renderFixture(this);
    }

	public void renderFixture(IFixtureFactory<F> factory) {
        renderFixture(factory.createFixture(getFixtureId()));
    }

    public abstract void renderFixture(F fixture);

	protected T getHarness() {
        // page must be rendered first for harness to work!
		if (harness ==null) {
			harness = createHarness();
		}
		return harness;
	}			

    protected void setPath(String... paths) {
        String path = Joiner.on(":").join(paths);
        setPath(path);
    }

	protected void assertInvisible(String path) {
		assertInvisible(getHarness().get(path));
	}

	protected void assertVisible(String path) {
		assertVisible(getHarness().get(path));
	}


}
