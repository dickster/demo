package demo;


import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serializable;

public abstract class Tab<T extends Serializable> implements Serializable {

    protected IModel<T> model;
    protected WebMarkupContainer panel;
    private transient boolean current = false;
    private String panelId;
    private String titleInput;

    public Tab(T value) {
        this.model = Model.of(value);
    }

    public Tab<T> withDefault(T defaultData) {
        return this;
    }

    public String getTitle() {
        // not null safe. typically you will implement this yourself.
        return model.getObject().toString();
    }

    protected FormComponent usingAsTitle(FormComponent input) {
        titleInput = "#"+input.setOutputMarkupId(true).getMarkupId();  // set the jquery selector of this component.
        return input;
    }

    protected abstract WebMarkupContainer createPanel(String id);

    public String getTitleInput() {
        return titleInput;
    }

}
