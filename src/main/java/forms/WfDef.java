package forms;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WfDef {
    String name();
    Class<?> requestClass() default Void.class;
}
