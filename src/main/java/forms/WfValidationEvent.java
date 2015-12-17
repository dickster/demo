package forms;

import forms.validation.ValidationResult;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfValidationEvent<T> extends WfErrorEvent<ValidationResult<T>> {

    private final AjaxRequestTarget target;
    private ValidationResult<T> result;
    private Form form;

    public WfValidationEvent(ValidationResult<T> result, AjaxRequestTarget target, Form form) {
        super(result);
        this.target = target;
        this.result = result;
        this.form = form;
    }

    public Form getForm() {
        return form;
    }

    public ValidationResult<T> getResult() {
        return result;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
