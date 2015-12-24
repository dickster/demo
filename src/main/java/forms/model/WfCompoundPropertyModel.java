package forms.model;

import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;

// model that bases on component meta data instead of id.
public class WfCompoundPropertyModel<T> extends CompoundPropertyModel<T> {

    public WfCompoundPropertyModel(T object) {
        super(object);
    }

    @Override
    protected String propertyExpression(Component component) {
        return new WfUtil().getComponentFullProperty(component);
    }

}
