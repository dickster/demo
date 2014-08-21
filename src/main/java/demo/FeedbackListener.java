package demo;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;

public interface FeedbackListener {
    public void hasError(AjaxRequestTarget target, FeedbackMessage msg);
    public void resetErrors(AjaxRequestTarget target);
}
