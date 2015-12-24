package forms.spring;

import com.google.common.base.Preconditions;
import forms.WorkflowException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Nonnull;

public class WfBeanFactory<T> implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Class<T> clazz;
    private T defaultForNull;

    public WfBeanFactory(Class<T> clazz) {
        Preconditions.checkNotNull(clazz, "can't have null type for bean factory.");
        this.clazz = clazz;
    }


    public @Nonnull T create(String name) {
        if (name==null) {
            return defaultValue();
        }

        try {
            T bean = applicationContext.getBean(name, clazz);
            Preconditions.checkState(bean!=null, "factory can't find bean named " + name + " of type " + clazz.getSimpleName());
            return bean;
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard.!
            throw new WorkflowException("can't find "+clazz.getSimpleName()+" with name " + name);
        }
    }

    private T defaultValue() {
        if (defaultForNull==null) {
            throw new UnsupportedOperationException("can't return null bean...either specify a default value or use a bean name that exists");
        }
        return defaultForNull;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public T allowForNulls(T bean) {
        this.defaultForNull = bean;
        return (T) this;
    }
}
