package forms;

import forms.config.FormAConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.model.MappedModel;
import org.apache.wicket.model.IModel;

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
    public void end() {
        updatePage(new StartingPoint());
    }

    @Override
    protected IModel createModel() {
        // e.g. return new GrenvilleObject();
        return new MappedModel();
    }

    class StateA extends WfFormState {
        StateA() {
            super(new FormAConfig());
        }

        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateB;
            }
            return this;
        }
    }

    class StateB extends WfFormState {
        StateB() {
            super(new FormBConfig());
        }
        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateC;
            }
            return this;
        }
    }

    class StateC extends WfFormState {
        StateC() {
            super(new FormCConfig());
        }
        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                workflow.end();
            }
            return this;
        }
    }

}
