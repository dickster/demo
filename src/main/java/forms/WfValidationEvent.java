package forms;

import forms.validation.ValidationResult;
import org.apache.wicket.ajax.AjaxRequestTarget;

public class WfValidationEvent<T> extends WfErrorEvent<ValidationResult<T>> {

    private final AjaxRequestTarget target;

    public WfValidationEvent(ValidationResult<T> result, AjaxRequestTarget target) {
        super(result);
        this.target = target;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }
}
