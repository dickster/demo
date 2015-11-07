package forms.model;

public class DefaultPropertyManager implements IPropertyManager {
    @Override
    public IPropertyHolder createPropertyHolder(Object parent) {
        return new DefaultPropertyHolder(parent);
    }
}
