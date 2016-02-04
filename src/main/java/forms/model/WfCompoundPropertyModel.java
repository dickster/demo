package forms.model;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;

// model that bases on component workflow id instead of wicket id.
public class WfCompoundPropertyModel<T> extends CompoundPropertyModel<T> {

    // TODO : add constructor for model as opposed to object.

    public WfCompoundPropertyModel(T object) {
        super(object);
    }

    @Override
    protected String propertyExpression(Component component) {
        return WfUtil.getComponentFullProperty(component);
    }

}
