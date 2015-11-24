package forms.widgets;

import forms.Group;
import forms.Toolkit;
import forms.WidgetFactory;
import forms.config.DialogConfig;
import forms.config.DialogSubmitButtonConfig;
import forms.util.WfUtil;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import javax.inject.Inject;

public class Dialog extends Panel {

    private final DialogConfig config;
    private @Inject Toolkit toolkit;
    private @Inject WfUtil wfUtil;

    public Dialog(String id, DialogConfig config) {
        super(id);
        // TODO add options for fade, close button. ajax handlers?
        setMarkupId(config.getName());
        this.config = config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("title", config.getTitle()));
        add(new Form("form")
                .add(new Group("contents", config))
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
    }

    protected Component createButton(String id, DialogSubmitButtonConfig config) {
        WidgetFactory factory = wfUtil.getWidgetFactoryFor(this);
        return factory.create(id, config);
    }
}
