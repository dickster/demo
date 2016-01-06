package forms.spring;

import org.apache.wicket.behavior.Behavior;

public class BehaviorFactory extends WfBeanFactory<Behavior> {

    // NOTE : behaviors MUST be prototype because you can't add the same one to multiple components.
    //  (i.e. you need a new instance each time).
    public BehaviorFactory() {
        super(Behavior.class, "prototype");
    }

}
