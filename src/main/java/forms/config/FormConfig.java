package forms.config;

import com.google.common.collect.Maps;
import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.Map;

public class FormConfig<T> extends GroupConfig<WorkflowForm> {

    private IFormValidator validator;
    private String url;
    private Map<String, String> nameToId = Maps.newHashMap();

    public FormConfig(String name) {
        super(name, WidgetTypeEnum.FORM);
        withRenderBodyOnly(false);
        withCss("form");
    }

    @Override
    public WorkflowForm create(String id) {
        return new WorkflowForm(id, this);
    }

    public IFormValidator getValidator() {
        return validator;
    }

    public FormConfig<T> withValidator(IFormValidator validator) {
        this.validator = validator;
        return this;
    }

    public void setCallbackUrl(String url) {
        this.url = url;
    }

    public void updateNameToId(WorkflowForm form) {
        nameToId = Maps.newHashMap();
        form.visitChildren(Component.class, new IVisitor<Component, Void>() {
            @Override
            public void component(Component component, IVisit<Void> visit) {
                if (component instanceof HasConfig) {
                    String name = ((HasConfig)component).getConfig().getName();
                    System.out.println("adding " + name + " --> " + component.getMarkupId());
                    nameToId.put(name, component.getMarkupId());
                }
            }
        });
    }
}
