package forms;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.annotation.Resource;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private @Resource(name="commercialStartState")
    WfState startState;

    public CommercialWorkflow() {
        super();
        withStartingState(startState);
        withValue("defaultCountry", Locale.US);
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

    @Override
    protected IModel createModel() {
        return Model.of("hi");
    }

}
