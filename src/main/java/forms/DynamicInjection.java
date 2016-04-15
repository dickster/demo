package forms;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DynamicInjection {

    // look in this property for the name of the bean.
    // e.g. property="myCustomBeanId"
    String property();

    // if none found, use this bean name.   "" will yield null bean.
    String dflt() default "";
}
