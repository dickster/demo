package forms.spring;

import com.google.common.base.Preconditions;
import forms.DynamicInjection;
import forms.WorkflowException;
import forms.widgets.config.Config;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DynamicInjector implements Serializable {

    private @Inject BeanFactory beanFactory;

    // register scopes here....i.e. behaviors are always prototype.

    public DynamicInjector() {
    }

    public void inject(Object target, Config config) {
        // assert target!=null!
        List<Field> fields = getFieldsToInject(target.getClass());
        for (Field field:fields) {
            injectField(field, target, config);
        }
    }

    private void injectField(Field field, Object target, Config config) {
        Class<?> type = field.getType();
        DynamicInjection injection = field.getAnnotation(DynamicInjection.class);
        String property = injection.property();
        try {
            Object name = PropertyUtils.getProperty(config, property);
            // assert name!=null
            Object bean = create(name.toString(), type);
            //ensureScope(name, scope);
            field.setAccessible(true);
            field.set(target, bean);
        } catch (Exception e) {
            // oops, can't read configuration property...
            e.printStackTrace();
        }
    }

    private List<Field> getFieldsToInject(Class clazz) {
        List<Field> result = new ArrayList<Field>();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (final Field field : fields) {
                if (field.isAnnotationPresent(DynamicInjection.class)) {
                    result.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private @Nonnull Object create(String name, Class clazz) {
        if (name==null) {
            throw new UnsupportedOperationException("null bean name specified. you must give a non-null name for the class of type " + clazz.getSimpleName());
        }
        try {
            return clazz==null ?
                    beanFactory.getBean(name) :
                    beanFactory.getBean(name, clazz);
        } catch (BeansException e) {
            ;// not sure how to handle error handling here...
            // if DEBUG. add debug error message?  print message.  else fail hard!
            throw new WorkflowException("can't find "+clazz.getSimpleName()+" with name " + name);
        }
    }

    private void ensureScope(String name, String scope) {
        // if you pass null/empty scope, does nothing (which could be dangerous...)
        if ("prototype".equalsIgnoreCase(scope)) {
            Preconditions.checkState(beanFactory.isPrototype(name));
        }
        else if ("singleton".equalsIgnoreCase(scope)){
            Preconditions.checkState(beanFactory.isSingleton(name));
        }
    }

}

