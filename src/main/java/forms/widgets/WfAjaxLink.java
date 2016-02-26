package forms.widgets;

import forms.spring.StringLoader;
import forms.spring.WfNavigator;
import forms.util.WfUtil;
import forms.widgets.config.AjaxLinkConfig;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

import javax.annotation.Nullable;
import javax.inject.Inject;


public class WfAjaxLink extends AjaxLink implements IAjaxIndicatorAware, ClickBehavior, HasConfig {

    private @Inject StringLoader stringLoader;
    private @Inject WfNavigator wfNavigator;

    private final AjaxLinkConfig config;
    private String ajaxIndicatorMarkupId = null;
    private ClickBehavior click = this;

    public WfAjaxLink(String id, AjaxLinkConfig config) {
        super(id);
        setDefaultModel(Model.of(stringLoader.get(config.getId())));
        this.config = config;
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        if (click!=null) {
            click.onClick(target);
            return;
        }
        throw new IllegalStateException("either supply a custom click handler or override onClick() in " + getClass().getSimpleName());
    }

    @Override
    public @Nullable String getAjaxIndicatorMarkupId() {
        return WfUtil.getAjaxIndicatorMarkupId(this);
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
