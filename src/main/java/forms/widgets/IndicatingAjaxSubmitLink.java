package forms.widgets;

import forms.config.Config;
import forms.config.HasConfig;
import forms.util.WfUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class IndicatingAjaxSubmitLink extends AjaxButton implements IAjaxIndicatorAware, HasConfig {

    private @Inject WfUtil wfUtil;

    private final Config config;
    private String ajaxIndicatorMarkupId = null;

    public IndicatingAjaxSubmitLink(String id, Config config) {
        super(id, Model.of(config.getName()));
        this.config = config;
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
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        wfUtil.render(this, response);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
