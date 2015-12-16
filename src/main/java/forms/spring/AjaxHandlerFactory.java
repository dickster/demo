package forms.spring;

import com.google.common.base.Preconditions;
import forms.WfAjaxHandler;
import forms.WorkflowException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
public class AjaxHandlerFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public WfAjaxHandler create(String handlerName) {
        try {
            WfAjaxHandler handler = applicationContext.getBean(handlerName, WfAjaxHandler.class);
            boolean prototype = applicationContext.isPrototype(handlerName);
            Preconditions.checkState(prototype, "ajax handlers must be @Scope('prototype').   i.e. a new instance created each time.");
            return handler;
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard.!
            throw new WorkflowException("can't register ajax handler with name " + handlerName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
