package forms;

import forms.config.FormAAnotherNewLayoutConfig;
import forms.config.FormAConfig;
import forms.config.FormANewLayoutConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.config.FormErrorConfig;
import forms.model.GermanInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private WfState stateA = new StateA();
    private WfState stateAx = new StateAx();
    private WfState stateAy = new StateAy();
    private WfState stateB = new StateB();
    private WfState stateC = new StateC();
    private WfState stateError = new StateError();

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
    protected WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new GermanInsuranceObject());
    }

    // ------------------------------------------------------------------------------
    // states.  typically declared in another file?   whatevs.
    // ------------------------------------------------------------------------------
    class StateA extends WfFormState {
        StateA() {
            super(new FormAConfig());
        }

        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAx;
            }
            if ("formA3".equals(event.getName())) {
                return stateAy;
            }
            return this;
        }
    }

    class StateAx extends WfFormState {
        StateAx() {
            super(new FormANewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAy;
            }
            return this;
        }
    }

    class StateAy extends WfFormState {
        StateAy() {
            super(new FormAAnotherNewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateB;
            }
            return this;
        }

        @Nonnull @Override
        public WfState handleError(Workflow workflow, WfSubmitEvent event) {
            return stateError;
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

    class StateError extends WfFormState {
        public StateError() {
            super(new FormErrorConfig());
        }

        @Override
        public WfState handleEvent(Workflow<?> workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                return stateA;
            }
            return this;
        }
    }

}
