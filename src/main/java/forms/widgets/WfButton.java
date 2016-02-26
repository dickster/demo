package forms.widgets;

import forms.WfSubmitErrorEvent;
import forms.WfSubmitEvent;
import forms.spring.StringLoader;
import forms.spring.WfNavigator;
import forms.util.WfUtil;
import forms.widgets.config.ButtonConfig;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nullable;
import javax.inject.Inject;

// TODO : extend this and make dialog & submit versions of button.
public class WfButton extends AjaxButton implements IAjaxIndicatorAware, HasConfig {

    private @Inject StringLoader stringLoader;
    private @Inject WfNavigator wfNavigator;

    private final ButtonConfig config;
    private String ajaxIndicatorMarkupId = null;

    public WfButton(String id, ButtonConfig config) {
        super(id);
        setDefaultModel(Model.of(stringLoader.get(config.getId())));
        this.config = config;
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form <?> form) {
        wfNavigator.getWorkflow(form)
                .post(new WfSubmitEvent(target, this, form));
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        wfNavigator.getWorkflow(form)
                .post(new WfSubmitErrorEvent(target, this, form));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        tag.setName("input");
        tag.getAttributes().put("type", "button");
        super.onComponentTag(tag);
    }

    @Override
    public @Nullable String getAjaxIndicatorMarkupId() {
        if (ajaxIndicatorMarkupId==null) {
            ajaxIndicatorMarkupId= WfUtil.getAjaxIndicatorMarkupId(this);
        }
        return ajaxIndicatorMarkupId;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
