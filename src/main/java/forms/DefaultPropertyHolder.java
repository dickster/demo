package forms;

import com.google.gson.internal.Primitives;

import java.lang.reflect.Method;

public class DefaultPropertyHolder implements IPropertyHolder {

    private Object target;

    public DefaultPropertyHolder(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public <T> T set(String property, T value) {
        String name = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        try {
            Method method = target.getClass().getMethod("set" + name, (Class[]) null);
            method.invoke(target, value);
            return value;
        }
        catch (Exception ignored) {
        }
        throw new IllegalStateException("cant' find setter for " +property + " in class " + target.getClass().getSimpleName());
    }

    @Override
    public Object safeGet(String child) {
        Object result = get(child);
        return result==null && !isPrimitive(child) ?
                createAndSet(child) :
                result;
    }

    protected boolean isPrimitive(String property) {
        Class<?> type = getPropertyType(property);
        return isPrimitive(type);
    }

    protected Object newPropertyInstance(String property) throws Exception {
        Class<?> type = getPropertyType(property);
        return type.getConstructor().newInstance();
    }

    protected Object createAndSet(String property) {
        try {
            return set(property, newPropertyInstance(property));
        } catch (Exception oops) {
            throw new IllegalArgumentException("can't create an empty property for " + property + " in " + target.getClass().getSimpleName());
        }
    }

    protected boolean isPrimitive(Class<?> type) {
        return Primitives.isPrimitive(type) || Primitives.isWrapperType(type);
    }

    protected Class<?> getPropertyType(String property) {
        try {
            return getGetter(property).getReturnType();
        }
        catch (Exception ignored) {
        }
        throw new IllegalStateException("cant' find property type for " + property + " in class " +target.getClass().getSimpleName());
    }

    public Object get(final String property) {
        try {
            return getGetter(property).invoke(target);
        } catch (Exception ignored) {
        }
        throw new IllegalStateException("cant' find getter for " + property + " in class " +target.getClass().getSimpleName());
    }

    private final Method getGetter(String property) throws NoSuchMethodException {
        String name = Character.toUpperCase(property.charAt(0)) + property.substring(1);
        Method method = target.getClass().getMethod("get" + name, (Class[]) null);
        return method;
    }

}
