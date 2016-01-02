package forms.spring;

import com.google.common.base.Preconditions;
import forms.WorkflowException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;

public abstract class WfBeanFactory<T> implements Serializable {

    private final String scope;
    private Class<T> clazz;
    private @Inject BeanFactory beanFactory;

    public WfBeanFactory(Class<T> clazz) {
        this(clazz, null);
    }

    public WfBeanFactory(Class<T> clazz, String scope) {
        Preconditions.checkNotNull(clazz, "can't have null type for bean factory.");
        this.clazz = clazz;
        this.scope = scope;
    }

    public @Nonnull T create(String name) {
        if (name==null) {
            throw new UnsupportedOperationException("null bean name specified. you must give a non-null name for the class of type " + clazz.getSimpleName());
        }

        try {
            T bean = beanFactory.getBean(name, clazz);
            ensureScope(name);
            return bean;
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard!
            throw new WorkflowException("can't find "+clazz.getSimpleName()+" with name " + name);
        }
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
