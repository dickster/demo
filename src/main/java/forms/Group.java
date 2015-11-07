package forms;

import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.WidgetConfig;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class Group extends Panel {

    private final IModel<?> model;
    private GroupConfig config;
    private @SpringBean WidgetFactory factory;

    public Group(String id, GroupConfig config, final IModel<?> model) {
        super(id);
        this.config = config;
        this.model = model;
        setOutputMarkupId(!config.getRenderBodyOnly());
        setRenderBodyOnly(config.getRenderBodyOnly());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ListView<Config>("group", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                Config config = item.getModelObject();
                if (config instanceof WidgetConfig) {
                    item.add(getFactory().createWidget("item", (WidgetConfig) config, model));
                } else if (config instanceof GroupConfig) {
                    item.add(newGroup((GroupConfig) config));
                }
            }
        });
    }

    // override these if you really want custom behaviors (different widget factory?)
    // 99% of the time these will be fine.

    protected Group newGroup(GroupConfig config) {
        return new Group("item", (GroupConfig) config, model);
    }

    protected WidgetFactory getFactory() {
        return factory;
    }
}
