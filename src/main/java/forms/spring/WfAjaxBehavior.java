package forms.spring;

import com.google.common.base.Preconditions;
import forms.WfAjaxEvent;
import forms.Workflow;
import forms.WorkflowForm;
import forms.util.WfUtil;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;

public abstract class WfAjaxBehavior extends AjaxFormComponentUpdatingBehavior implements ApplicationContextAware, BeanNameAware {

    private transient ApplicationContext applicationContext;
    private transient String beanName;

    public WfAjaxBehavior(String event) {
        super(event);
    }

    @Override
    protected void onUpdate(final AjaxRequestTarget target) {
        postAjaxEvent(target);
    }

    protected final void postAjaxEvent(AjaxRequestTarget target) {
        Workflow workflow = WfUtil.getWorkflow(getComponent());
        workflow.post(createEvent(target));
    }

    protected WfAjaxEvent createEvent(AjaxRequestTarget target) {
        return new WfAjaxEvent(getEvent(), target, getComponent());
    }

    protected WorkflowForm getWorkflowForm() {
        return WfUtil.getWorkflowForm(getComponent());
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void ensurePrototypeBean() {
        Preconditions.checkState(applicationContext.isPrototype(beanName), "workflow beans must be of @Scope('prototype') " + getClass().getSimpleName());
        // note : don't use application context after this.  it's just a transient var used at construction time.
        // it is NOT meant to be serialized by Wicket.
        applicationContext = null; //invalidate this just to make the point clear.
    }}
