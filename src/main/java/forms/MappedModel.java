package forms;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import java.util.Map;

public class MappedModel extends CompoundPropertyModel {

    private Map<String, Object> values;

    public MappedModel(Map<String, Object> object) {
        super(object);
    }

    public Map<String, Object> getMapObject() {
        return (Map<String, Object>) super.getObject();
    }

    private void setObject(String property, Object object) {
        values.put(property, object);
    }

    private Object getObject(String property) {
        return getMapObject().get(property);
    }

    public IModel bind(String property) {
        return new MappedPropertyModel(this, property);
    }

    public static class MappedPropertyModel implements IModel<Object> {
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
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}