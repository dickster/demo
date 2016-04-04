package forms;

import com.google.common.collect.Maps;
import forms.spring.WfNavigator;
import forms.widgets.config.Config;
import forms.widgets.config.GroupConfig;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Map;

// rename this to....? DIV? BasicPanel, Container, ?? dunno.  FormContent??
// just move all this into WorkflowForm for now.
@Deprecated   //replaced by new template code.
public abstract class Div extends Panel implements HasConfig {

    private @Inject WfNavigator wfNavigator;

    private GroupConfig config;
    private Map<String, Component> contents = Maps.newHashMap();

    public Div(String id, @Nonnull GroupConfig config) {
        super(id);
        this.config = config;
        setVisible(config.isInitialyHidden());
        setOutputMarkupPlaceholderTag(true);
        add(new ListView<Config>("div", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                Config c = item.getModelObject();
                Component component = createWidget("el", c);
                register(component, c);
                item.add(component).setRenderBodyOnly(true);
            }
        }.setReuseItems(true));

    }

    protected abstract Component createWidget(String el, Config c);

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    private void register(Component component, Config c) {
        contents.put(c.getId(), component);
    }

    public Component getWfComponent(String id) {
        return contents.get(id);
    }

    @Override
    public Config getConfig() {
        return config;
    }

}
