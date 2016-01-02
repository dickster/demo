package forms.spring;

public class AjaxBehaviorFactory extends WfBeanFactory<WfAjaxBehavior> {

    public AjaxBehaviorFactory() {
        super(WfAjaxBehavior.class, "prototype");
    }

}
