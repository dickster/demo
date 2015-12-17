package forms.config;

import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

public class FormConfig<T> extends GroupConfig<WorkflowForm> {

    private @DontSendInJson IFormValidator validator;
    private String url;
    private @DontSendInJson FeedbackPanelConfig feedbackConfig;

    public FormConfig(String name) {
        super(name, WidgetTypeEnum.FORM);
        withRenderBodyOnly(false);
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

    public FeedbackPanelConfig getFeedbackConfig() {
        return feedbackConfig;
    }

//    public void updateIdToMarkupId(WorkflowForm form) {
//        idToMarkupId = Maps.newHashMap();
//        form.visitChildren(Component.class, new IVisitor<Component, Void>() {
//            @Override
//            public void component(Component component, IVisit<Void> visit) {
//                if (component instanceof HasConfig) {
//                    String name = ((HasConfig)component).getConfig().getId();
//                    System.out.println("adding " + name + " --> " + component.getMarkupId());
//                    idToMarkupId.put(name, component.getMarkupId());
//                }
//            }
//        });
//    }

}
