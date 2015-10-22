package forms;

import com.google.common.base.Preconditions;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import javax.annotation.Nonnull;

public class Group extends Panel {

    private final IModel<?> model;
    private GroupConfig config;
    private WidgetFactory factory;

    public Group(String id, GroupConfig config, final IModel<?> model) {
        super(id);
        this.config = config;
        this.model = model;
        setOutputMarkupId(true);
        setRenderBodyOnly(config.getRenderBodyOnly());
    }

    @Override
    protected void onInitialize() {
        Preconditions.checkState(factory!=null);
        super.onInitialize();
        add(new ListView<Config>("items", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                Config config = item.getModelObject();
                if (config instanceof WidgetConfig) {
                    item.add(factory.createWidget("item", (WidgetConfig) config, model));
                } else if (config instanceof GroupConfig) {
                    item.add(new Group("item", (GroupConfig) config, model).withWidgetFactory(factory));
                }
            }
        });

    }

    public Group withWidgetFactory(@Nonnull WidgetFactory factory) {
        this.factory = factory;
        return this;
    }
}
