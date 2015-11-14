package forms.widgets;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import javax.annotation.Nullable;

public class IndicatingAjaxSubmitLink extends AjaxButton implements IAjaxIndicatorAware {

    public IndicatingAjaxSubmitLink(String id, String label) {
        super(id, Model.of(label));
       // add(new Label("label", label ));
    }

    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "button");
        super.onComponentTag(tag);
    }

    @Override
    public @Nullable String getAjaxIndicatorMarkupId() {
        return null;// look for markup in parent hierarchy.
    }
}
