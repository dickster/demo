package forms;

import forms.validation.ValidationResult;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;

public class WfValidationEvent<T> extends WfErrorEvent<ValidationResult<T>> {

    private ValidationResult<T> result;

    public WfValidationEvent(ValidationResult<T> result, AjaxRequestTarget target, Form form) {
        super(result, target, form);
    }

    public Form getForm() {
        return (Form)getComponent();
    }

    public ValidationResult<T> getResult() {
        return result;
    }

}