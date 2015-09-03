package demo.wicketTest;

import java.lang.reflect.Method;

public class BrovadaWicketTestContext {
    public static final String DEFAULT = "DEFAULT";
    private Method method;
    public String name;

	public BrovadaWicketTestContext(Method method, String name) {
		this.method = method;
        this.name = name;
	}

	public Method getMethod() {
		return method;
	}

    public String getName() {
        return name;
    }

    @Override
	public String toString() {
		return name;
	}
	
}
