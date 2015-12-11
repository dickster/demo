package forms.widgets;

import forms.config.Config;
import forms.config.DialogSubmitButtonConfig;
import forms.config.HasConfig;
import forms.util.ComponentFinder;
import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import javax.inject.Inject;

public class DialogSubmitButton extends AjaxSubmitLink implements HasConfig {

    private @Inject WfUtil wfUtil;

    private final DialogSubmitButtonConfig config;

    public DialogSubmitButton(String id, DialogSubmitButtonConfig config) {
        super(id);
        add(new Label("label", Model.of(config.getId())));
        this.config = config;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
        Dialog dialog = new ComponentFinder<Dialog>().find(target.getPage(), config.getDialogName());
        dialog.hide(target);
        // post ajax event to workflow.
//        form.visitParents(WorkflowForm.class, new IVisitor<WorkflowForm,Void>() {
//            @Override public void component(WorkflowForm wf, IVisit visit) {
//                target.add(wf);
//                wf.getForm().clearInput();
//                visit.stop();
//            }
//        });
    }


    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
    }

}
