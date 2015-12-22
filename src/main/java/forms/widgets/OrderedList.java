package forms.widgets;

import com.google.common.base.Preconditions;
import forms.WidgetFactory;
import forms.Workflow;
import forms.config.Config;
import forms.config.HasConfig;
import forms.config.ListConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractPropertyModel;
import org.apache.wicket.model.IModel;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;

public class OrderedList extends RepeatingView implements HasConfig {

    private ListConfig config;

    private final static String nestedPropertyFormat = "%s[%d]";

    public OrderedList(String id, ListConfig config) {
        super(id);
        this.config = config;
    }

    @Override
    protected IModel<?> initModel() {
        return super.initModel();
        // assert that this is an array/list type.
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkArgument(getDefaultModel() instanceof AbstractPropertyModel, "this component only to be used within workflow with model of type " + AbstractPropertyModel.class.getSimpleName());
        super.onInitialize();
        for (int i=0;i<getModelSize();i++) {
            add(new ListPanel(newChildId(), i, config.getConfigs()));
        }
    }

    private int getModelSize() {
        int size = 0;
        // TODO : put this in assertion at beginning of method.
        if (getDefaultModelObject() instanceof Collection) {
            size = ((Collection)getDefaultModelObject()).size();
        }
        else if (getDefaultModelObject().getClass().isArray()) {
            size = Array.getLength(getDefaultModelObject());
        }
        return size;
    }

    class ListPanel extends RepeatingView {

        public ListPanel(String id, int index, List<Config> configs) {
            super(id);
            final Workflow workflow = WfUtil.getWorkflow(OrderedList.this);
            String property = ((AbstractPropertyModel)OrderedList.this.getDefaultModel()).getPropertyExpression();
            for (int i=0; i<configs.size();i++) {
                // need to clone configs??? or augment widget factory....
                Config config = configs.get(i);
                Component widget = workflow.createWidget(newChildId(), config);
                // give factory a clue as to who the parent is.  will generate the correct ognl in compound property models.
                String prefix = String.format(String.format(nestedPropertyFormat, property, index));
                widget.setMetaData(WidgetFactory.MODEL_PREFIX, prefix);
                add(widget);
            }
        }

    }


}
