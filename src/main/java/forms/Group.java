package forms;

import com.google.common.collect.Lists;
import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.WidgetConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

public class Group extends Panel {

    private final IModel<?> model;
    private GroupConfig config;
    private @SpringBean WidgetFactory factory;

    public Group(String id, GroupConfig config, final IModel<?> model) {
        super(id);
        this.config = config;
        this.model = model;
        //setOutputMarkupId(!config.getRenderBodyOnly());
       // setRenderBodyOnly(config.getRenderBodyOnly());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<Component> components = createComponents("component");
//        RepeatingView repeating = new RepeatingView("components");
//        add(repeating);
       // add(new IndicatingAjaxSubmitLink("link", "hello"));

//        for (Component component:components) {
//            AbstractItem item = new AbstractItem(repeating.newChildId());
//            repeating.add(item);
//            item.add(component);
//        }

        add(new ListView<Component>("components", components) {
            @Override
            protected void populateItem(ListItem<Component> item) {
                item.add(item.getModelObject());
            }
        }.setReuseItems(true));
    }

    private List<Component> createComponents(String id) {
        List<Component> result = Lists.newArrayList();
        for (Config c:config.getConfigs()) {
            System.out.println("adding " + c);
            if (c instanceof WidgetConfig) {
                result.add(getFactory().createWidget(id, (WidgetConfig) c, model));
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
        return new Group(id, config, model);
    }

    protected WidgetFactory getFactory() {
        return factory;
    }
}
