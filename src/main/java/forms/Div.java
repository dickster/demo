package forms;

import forms.spring.WfNavigator;
import forms.widgets.config.Config;
import forms.widgets.config.GroupConfig;
import forms.widgets.config.HasConfig;
import forms.widgets.config.HasTemplate;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.annotation.Nonnull;
import javax.inject.Inject;

// rename this to....? DIV? BasicPanel, Container, ?? dunno.
public class Div extends Panel implements HasConfig, HasTemplate {

    private @Inject WfNavigator wfNavigator;

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
        final Workflow workflow = wfNavigator.getWorkflow(this);
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

    @Override
    public String getTemplateId() {
        throw new UnsupportedOperationException("TODO : need to implement this");
    }
}
