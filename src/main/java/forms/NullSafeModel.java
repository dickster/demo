package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;



public class NullSafeModel<T> extends CompoundPropertyModel {

    private IPropertyManager propertyManager = new DefaultPropertyManager();

    public NullSafeModel(T object) {
        super(object);
    }

    public NullSafeModel(IModel<T> model) {
        super(model);
    }

    public NullSafeModel withPropertyManager(IPropertyManager propertyManager) {
        this.propertyManager = propertyManager;
        return this;
    }

    @Override
    public Object getObject() {
        return super.getObject();
    }

    private Object getObject(String property) {
        Preconditions.checkArgument(org.apache.commons.lang3.StringUtils.isNotBlank(property));
        Property prop = new Property(property);
        Object parent = getParent(prop.getParentProperty());
        IPropertyHolder propertyHolder = createPropertyHolder(parent);
        return propertyHolder.safeGet(prop.getChildProperty());
    }

    protected Object getParent(String property) {
        return org.apache.commons.lang3.StringUtils.isBlank(property) ?
                super.getObject() :
                getObject(property);
    }

    private void setObject(String property, Object object) {
        Property prop = new Property(property);
        Object parent = getParent(prop.getParentProperty());
        createPropertyHolder(parent).set(prop.getChildProperty(), object);
    }

    private IPropertyHolder createPropertyHolder(Object parent) {
        return propertyManager.createPropertyHolder(parent);
    }

    public IModel bind(String property) {
        return new NullSafePropertyModel(this, property);
    }



    class NullSafePropertyModel extends PropertyModel {

        private NullSafeModel safeModel;

        public NullSafePropertyModel(NullSafeModel model, String propertyExpression) {
            super(model, propertyExpression);
            this.safeModel = model;
        }

        @Override
        public Object getObject() {
            return safeModel.getObject(getPropertyExpression());
        }

        @Override
        public void setObject(Object object) {
            NullSafeModel.this.setObject(getPropertyExpression(), object);
        }

    }

}