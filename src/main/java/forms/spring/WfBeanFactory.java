package forms.spring;

import com.google.common.base.Preconditions;
import forms.WorkflowException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WfBeanFactory<T> implements Serializable {

    private @Inject BeanFactory beanFactory;

    private final String scope;
    private Class<T> clazz;

    // TODO : make this more liberal with names.
    // if can't find name, look for name+"Behavior"/"Behaviour"/"AjaxBehavior" etc...

    public WfBeanFactory(Class<T> clazz) {
        this(clazz, null);
    }

    public WfBeanFactory(Class<T> clazz, String scope) {
        Preconditions.checkNotNull(clazz, "can't have null type for bean factory.");
        this.clazz = clazz;
        this.scope = scope;
    }

    public @Nonnull T create(String name, Class<? extends T> subClass) {
        if (name==null) {
            throw new UnsupportedOperationException("null bean name specified. you must give a non-null name for the class of type " + subClass.getSimpleName());
        }
        T bean = getBean(name, subClass);
        ensureScope(name);
        return bean;
    }

    private T getBean(String name, Class<? extends T> clazz) {
        try {
            return clazz==null ?
                    (T) beanFactory.getBean(name) :
                    (T) beanFactory.getBean(name, clazz);
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard!
            throw new WorkflowException("can't find "+this.clazz.getSimpleName()+" with name " + name);
        }
    }

    public @Nonnull T create(String name) {
        return create(name, null);
    }

    private void ensureScope(String name) {
        if ("prototype".equalsIgnoreCase(scope)) {
            Preconditions.checkState(beanFactory.isPrototype(name));
        }
        else if ("singleton".equalsIgnoreCase(scope)){
            Preconditions.checkState(beanFactory.isSingleton(name));
        }
    }

}
