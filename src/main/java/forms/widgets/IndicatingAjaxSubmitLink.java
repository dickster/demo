package forms.widgets;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;

import javax.annotation.Nullable;

public class IndicatingAjaxSubmitLink extends Panel implements IAjaxIndicatorAware {

    public IndicatingAjaxSubmitLink(String id, String label) {
        super(id);
        add(new AjaxSubmitLink("link"){
            @Override
            protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
                IndicatingAjaxSubmitLink.this.onAfterSubmit(target, form);
            }
        }.add(new Label("label", label )));
    }

    protected void onAfterSubmit(AjaxRequestTarget target, Form<?> form) {
    }

    @Override
    public @Nullable String getAjaxIndicatorMarkupId() {
        return null;// look for markup in parent hierarchy.
    }
}
