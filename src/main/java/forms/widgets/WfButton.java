package forms.widgets;

import forms.WfSubmitErrorEvent;
import forms.WfSubmitEvent;
import forms.spring.StringLoader;
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

public class WfButton extends AjaxButton implements IAjaxIndicatorAware, HasConfig {

    private @Inject StringLoader stringLoader;

    private final Config config;
    private String ajaxIndicatorMarkupId = null;

    public WfButton(String id, ButtonConfig config) {
        super(id);
        setDefaultModel(Model.of(stringLoader.get(config.getId())));
        this.config = config;
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form <?> form) {
        WfUtil.post(form, new WfSubmitEvent(target, this, form));
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        WfUtil.post(form, new WfSubmitErrorEvent(target, this, form));
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
            ajaxIndicatorMarkupId="";
            visitParents(MarkupContainer.class, new IVisitor<MarkupContainer, Object>() {
                @Override public void component(MarkupContainer container, IVisit<Object> visit) {
                    if (container instanceof IAjaxIndicatorAware) {
                        String id = ((IAjaxIndicatorAware)container).getAjaxIndicatorMarkupId();
                        if (StringUtils.isNotBlank(id)) {
                            ajaxIndicatorMarkupId = id;
                            visit.stop();
                        }
                    }
                }
            });
        }
        return ajaxIndicatorMarkupId;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
