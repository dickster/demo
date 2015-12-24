package forms.spring;

import forms.WfAjaxBehavior;
import forms.WorkflowException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
public class AjaxBehaviorFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public WfAjaxBehavior create(String handlerName) {
        try {
            WfAjaxBehavior handler = applicationContext.getBean(handlerName, WfAjaxBehavior.class);
            return handler;
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard.!
            throw new WorkflowException("can't find ajax handler with name " + handlerName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
