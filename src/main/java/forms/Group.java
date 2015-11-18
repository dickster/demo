package forms;

import com.google.common.collect.Lists;
import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.WidgetConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.List;

public class Group extends Panel {

    private GroupConfig config;
    private WidgetFactory factory;

    public Group(String id, GroupConfig config) {
        super(id);
        this.config = config;
        WfUtil.setComponentName(this, config.getName());
        setOutputMarkupId(false);
        setOutputMarkupId(!config.getRenderBodyOnly());
        setRenderBodyOnly(config.getRenderBodyOnly());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        // TODO : refactor this to iterate over list of configs.
        List<Component> components = createComponents("component");

        add(new ListView<Component>("components", components) {
            @Override
            protected void populateItem(ListItem<Component> item) {
                item.add(item.getModelObject());
                item.setRenderBodyOnly(true);
            }
        }.setReuseItems(true));

    }

    private List<Component> createComponents(String id) {
        List<Component> result = Lists.newArrayList();
        for (Config c:config.getConfigs()) {
            if (c instanceof WidgetConfig) {
                result.add(getFactory().createWidget(id, (WidgetConfig) c));
            }
            else if (c instanceof GroupConfig) {
                result.add(newGroup(id, (GroupConfig) c));
            }
        }
        return result;
    }

    // override these if you really want custom behaviors (different widget factory?)
    // 99% of the time these will be fine.

    protected Group newGroup(String id, GroupConfig config) {
        return new Group(id, config);
    }

    protected WidgetFactory getFactory() {
        if (factory==null) {
            visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, Object>() {
                @Override public void component(MarkupContainer c, IVisit<Object> visit) {
                    if (c instanceof HasWorkflow) {
                        factory = ((HasWorkflow)c).getWorkflow().getWidgetFactory();
                        visit.stop();
                    }
                }
            });
        }
        return factory;
    }
}
