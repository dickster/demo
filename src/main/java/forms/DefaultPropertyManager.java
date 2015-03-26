package forms;

public class DefaultPropertyManager implements IPropertyManager {
    @Override
    public IPropertyHolder createPropertyHolder(Object parent) {
        return new DefaultPropertyHolder(parent);
    }
}
