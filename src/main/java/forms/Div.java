package forms;

import forms.config.*;
import forms.util.WfUtil;
import org.apache.wicket.Component;
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
        // if DEBUG MODE!
        Component refresh = workflow.createWidget("refresh", new DebugRefreshButtonConfig("REFRESH"));
        add(refresh);
        refresh.setVisible(config instanceof FormConfig);
        // TODO : just add webmarkupcontainer if debug||!formConfig.
        // OR!! add a refresh button to the WorkflowForm page itself!!
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
