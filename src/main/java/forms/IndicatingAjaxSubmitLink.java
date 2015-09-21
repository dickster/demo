package forms;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;

import javax.annotation.Nullable;

public class IndicatingAjaxSubmitLink extends AjaxSubmitLink implements IAjaxIndicatorAware {

    public IndicatingAjaxSubmitLink(String id) {
        super(id);
    }

    @Override
    public @Nullable String getAjaxIndicatorMarkupId() {
        return null;// look for markup in parent hierarchy.
    }
}
