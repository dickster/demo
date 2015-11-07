package forms;

import forms.config.FormAConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.config.FormConfig;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import javax.annotation.Nonnull;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private WfState stateA = new StateA();
    private WfState stateB = new StateB();
    private WfState stateC = new StateC();

    public CommercialWorkflow() {
        super();
        withStartingState(stateA);
        withValue("defaultCountry", Locale.US);
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

    @Override
    protected IModel createModel() {
        return Model.of("hi");
    }


    class StateA extends WfFormState {
        private FormConfig config = new FormAConfig();
        @Nonnull @Override
        public FormConfig getFormConfig() {
            return config;
        }
    }

    class StateB extends WfFormState {
        private FormConfig config = new FormBConfig();
    }

    class StateC extends WfFormState {
        private FormConfig config = new FormCConfig();
    }

}
