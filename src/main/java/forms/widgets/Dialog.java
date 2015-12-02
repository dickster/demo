package forms.widgets;

import forms.Div;
import forms.Toolkit;
import forms.WidgetFactory;
import forms.config.Config;
import forms.config.DialogConfig;
import forms.config.DialogSubmitButtonConfig;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.inject.Inject;

public class Dialog extends Panel implements HasConfig {

    private String SHOW_JS = "$('#%s').modal('show');";
    private String HIDE_JS = "$('#%s').modal('hide');";

    private final DialogConfig config;
    private @Inject Toolkit toolkit;
    private @Inject WfUtil wfUtil;
    private Component form;

    public Dialog(String id, DialogConfig config) {
        super(id);
        // TODO add options for fade, close button. ajax handlers?
        setOutputMarkupId(true);
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", config.getTitle()));
        add(form = new Form("form")
                .add(new Div("contents", config))
                .add(new ListView<DialogSubmitButtonConfig>("buttons", config.getButtons()) {
                    @Override
                    protected void populateItem(ListItem<DialogSubmitButtonConfig> item) {
                        item.add(createButton("button", item.getModelObject()));
                        item.setRenderBodyOnly(true);
                    }
                }.setReuseItems(true))
        );
        add(new AttributeAppender("class", "modal"));
        add(new AttributeAppender("role", "dialog"));
    //    form.setVisible(false);
        form.setOutputMarkupPlaceholderTag(true);
    }

    protected Component createButton(String id, DialogSubmitButtonConfig buttonConfig) {
        WidgetFactory factory = wfUtil.getWidgetFactoryFor(this);
        return factory.create(id, buttonConfig);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    public Dialog show(AjaxRequestTarget target) {
        target.appendJavaScript(String.format(SHOW_JS, getMarkupId()));
//        target.add(this);
        form.setVisible(true);
        return this;
    }

    // refactor this to use workflow events.  post WfDialog.
    // that way everyone will get a crack at it...who knows what we want to add to target when dialog is closed.
    public Dialog hide(AjaxRequestTarget target) {
        target.appendJavaScript(String.format(HIDE_JS, getMarkupId()));
//        target.add(this);
//        form.setVisible(false);
        return this;
    }
}
