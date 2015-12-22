package forms;

import forms.config.Config;
import forms.config.GroupConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.annotation.Nonnull;

// rename this to....? DIV? BasicPanel, Container, ?? dunno.
public class Div extends Panel implements HasConfig {

    private GroupConfig config;

    public Div(String id, @Nonnull GroupConfig config) {
        super(id);
        this.config = config;
        setVisible(config.isInitialyVisibile());
        setOutputMarkupPlaceholderTag(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        final Workflow workflow = WfUtil.getWorkflow(this);
        add(new ListView<Config>("div", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                item.add(workflow.createWidget("el", item.getModelObject()));
                item.setRenderBodyOnly(true);
            }
        }.setReuseItems(true));
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
