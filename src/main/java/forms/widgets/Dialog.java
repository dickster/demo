package forms.widgets;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import forms.Div;
import forms.Toolkit;
import forms.Workflow;
import forms.spring.WfNavigator;
import forms.widgets.config.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import javax.inject.Inject;
import java.util.List;

// TODO : redo this whole dialog stuff from start...it's broken completely.
// need to build the dialog as needed (can't do it when page rendered because it will
//  cause bound input fields to exist which will screw up the model).
public class Dialog extends Panel implements HasConfig, HasTemplate {

    private @Inject WfNavigator wfNavigator;

    private String SHOW_JS = "$('#%s').modal('show');";
    private String HIDE_JS = "$('#%s').modal('hide');";

    private final DialogConfig config;
    private @Inject Toolkit toolkit;
    private Component form;

    public Dialog(String id, DialogConfig config) {
        // if parent is a section panel, then get it's "current model"
        super(id); // need access to parent model???
        // TODO add options for fade, close button. ajax handlers?
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        String title = config.getTitle();
        add(new Label("title", title), setVisible(StringUtils.isNotBlank(title)));
        add(form = new Form("form").add(content()));
        add(new AttributeAppender("class", config.getCss()));
    }

    private Component content() {
        ListView<Config> content = new ListView<Config>("content", config.getConfigs()) {
            @Override
            protected void populateItem(ListItem<Config> item) {
                Config c = item.getModelObject();
                Component component = getWorkflow().createWidget("el", c);
                item.add(component).setRenderBodyOnly(true);
            }
        }.setReuseItems(true);
        return content;
    }

    private Workflow getWorkflow() {
        return wfNavigator.getWorkflow(this);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public Dialog show(AjaxRequestTarget target) {
        target.appendJavaScript(String.format(SHOW_JS, getMarkupId()));
        target.add(this);
        return this;
    }

    // refactor this to use workflow events.  post WfDialog.
    // that way everyone will get a crack at it...who knows what we want to add to target when dialog is closed.
    public Dialog hide(AjaxRequestTarget target) {
        target.appendJavaScript(String.format(HIDE_JS, getMarkupId()));
        target.add(this);
        return this;
    }

    @Override
    public String getTemplateId() {
        throw new UnsupportedOperationException("TODO : need to implement this");
    }
}
