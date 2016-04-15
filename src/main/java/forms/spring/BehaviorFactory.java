package forms.spring;

import org.apache.wicket.behavior.Behavior;
import org.springframework.beans.factory.ObjectFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;

public class BehaviorFactory extends WfBeanFactory<Behavior> {

    private @Inject ObjectFactory<Behavior> behaviorFactory;

    // NOTE : behaviors MUST be prototype because you can't add the same one to multiple components.
    //  (i.e. you need a new instance each time).
    public BehaviorFactory() {
        super(Behavior.class, "prototype");
    }

    @Nonnull
    @Override
    public Behavior create(String name) {
//        behaviorObjectFactory<MyPrototype>.create();
    }
}
