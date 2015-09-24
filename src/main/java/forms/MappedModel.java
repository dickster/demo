package forms;

import com.google.common.base.Joiner;
import org.apache.wicket.Component;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;

import java.util.Map;

public class MappedModel extends CompoundPropertyModel<Map<String, Object>> {

    public MappedModel(Map<String, Object> object) {
        super(object);
    }

    private void setObject(String property, Object object) {
        getObject().put(property, object);
    }

    private Object getObject(String property) {
        return getObject().get(property);
    }

    @Override
    public <C> IWrapModel<C> wrapOnInheritance(Component component) {
        return new MappedPropertyModel(this, component.getId());
    }

    public class MappedPropertyModel implements IWrapModel {
        private MappedModel model;
        private String propertyExpression;

        public MappedPropertyModel(MappedModel model, String propertyExpression) {
            this.model = model;
            this.propertyExpression = propertyExpression;
        }

        @Override
        public Object getObject() {
            return model.getObject(propertyExpression);
        }

        @Override
        public void setObject(Object object) {
            model.setObject(propertyExpression, object);
        }

        @Override
        public void detach() {

        }

        @Override
        public IModel<?> getWrappedModel() {
            return model;
        }
    }

    @Override
    public String toString() {
        String values = Joiner.on(", ").withKeyValueSeparator("->").join(getObject());
        return "MappedModel:{" + values + "}";
    }
}