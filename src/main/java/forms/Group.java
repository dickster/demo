package forms;

import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.annotation.Nonnull;
import javax.inject.Inject;

// rename this to....? DIV? BasicPanel, Container, ?? dunno.
public class Group extends Panel implements HasConfig {
  
    private GroupConfig config;
    private @Inject Toolkit toolkit; // change this to get factory from workflow.
    private @Inject WfUtil wfUtil;

    public Group(String id, @Nonnull GroupConfig config) {
        super(id);
        this.config = config;

        setOutputMarkupId(false);
        setOutputMarkupId(!config.getRenderBodyOnly());
        setRenderBodyOnly(config.getRenderBodyOnly());
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final WidgetFactory factory = wfUtil.getWidgetFactoryFor(this);
        add(new ListView<Config>("div", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                System.out.println(config.getName() + "[" +item.getIndex() + "] - " + item.getModelObject().getName());
                item.add(factory.createWidget("el", item.getModelObject()));
                item.setRenderBodyOnly(true);
            }
        }.setReuseItems(true));

    }

    @Override
    public Config getConfig() {
        return config;
    }
}
