package forms;

import com.google.common.collect.Lists;
import forms.spring.WfAjaxBehavior;
import forms.widgets.config.FormConfig;

import javax.annotation.Nonnull;
import java.util.List;

// TODO : maybe add generic so it knows what type of workflow it's attached to.
// i.e. WfFormState<DemoWorkflow> extends WfState....
public abstract class WfFormState extends WfState {

    private FormConfig formConfig;
//    private List<WfAjaxBehavior> handlers = Lists.newArrayList();

    public WfFormState(@Nonnull FormConfig formConfig) {
        super();
        this.formConfig = formConfig;
    }

    public @Nonnull FormConfig getFormConfig() {
        return formConfig;
    }

//    public WfFormState withAjaxBehaviors(WfAjaxBehavior... behaviors) {
//        this.handlers.addAll(Lists.newArrayList(behaviors));
//        return this;
//    }

}

