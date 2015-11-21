package forms.config;


import forms.WidgetTypeEnum;
import forms.WorkflowForm;
import forms.widgets.DialogSubmitButton;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

public class DialogSubmitButtonConfig extends WidgetConfig {

    private String HIDE_JS = "$('#%s').modal('hide');";
    private String dialogId;

    public DialogSubmitButtonConfig(String name) {
        super(name, WidgetTypeEnum.DIALOG_SUBMIT_BUTTON);
    }

    @Override
    public Component create(String id) {
        return new DialogSubmitButton(id, this) {
            @Override
            protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
                target.prependJavaScript(String.format(HIDE_JS, dialogId));
                // post ajax event to workflow.
                form.visitParents(WorkflowForm.class, new IVisitor<WorkflowForm,Void>() {
                    @Override public void component(WorkflowForm wf, IVisit visit) {
                       target.add(wf);
                        wf.getForm().clearInput();
                        visit.stop();
                    }
                });
            }
        };
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

}
