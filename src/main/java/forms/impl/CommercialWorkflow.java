package forms.impl;

import forms.FormBasedWorkflow;
import forms.StartingPoint;
import forms.WfAjaxEvent;
import forms.WfAjaxHandler;
import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;
import forms.WorkflowException;
import forms.config.FormAAnotherNewLayoutConfig;
import forms.config.FormAConfig;
import forms.config.FormANewLayoutConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.config.FormErrorConfig;
import forms.model.GermanInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private @Inject @Named("demoHandler")
    WfAjaxHandler handler;

    private @Inject @Named("stateA")
    WfFormState stateA;
    private @Inject @Named("stateAx") WfFormState stateAx;
    private @Inject @Named("stateAy") WfFormState stateAy;
    private @Inject @Named("stateB") WfFormState stateB;
    private @Inject @Named("stateC") WfFormState stateC;
    private @Inject @Named("stateError") WfFormState stateError;

    public CommercialWorkflow() {
        super();
        withStartingState(stateA);
        withValue("defaultCountry", Locale.US);
    }

    protected void initialize() {
        ; //override if you want some workflow startup stuff to happen.
    }

    @Override
    public void handleAjaxEvent(@Nonnull WfAjaxEvent event) throws WorkflowException {
        if (handler.handleAjax(event)) {
            return;
        }
        super.handleAjaxEvent(event);
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
    public static class StateA extends WfFormState {
        private @Inject @Named("stateAx") WfFormState stateAx;
        private @Inject @Named("stateB") WfFormState stateB;

        StateA() {
            super(new FormAConfig());
        }

        @Override
        public WfFormState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAx;
            }
            if ("formB".equals(event.getName())) {
                return stateB;
            }
            return this;
        }
    }

    public static class StateAx extends WfFormState {
        private @Inject @Named("stateAy") WfFormState stateAy;

        StateAx() {
            super(new FormANewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAy;
            }
            return this;
        }
    }

    public static class StateAy extends WfFormState {
        private @Inject @Named("stateB") WfFormState stateB;
        private @Inject @Named("stateError") WfFormState stateError;

        StateAy() {
            super(new FormAAnotherNewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
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

    public static class StateB extends WfFormState {
        private @Inject @Named("stateC") WfFormState stateC;

        StateB() {
            super(new FormBConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateC;
            }
            return this;
        }

    }

    public static class StateC extends WfFormState {
        StateC() {
            super(new FormCConfig());
        }
        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                workflow.end();
            }
            return this;
        }
    }

    public static class StateError extends WfFormState {
        private @Inject @Named("stateA") WfFormState stateA;

        public StateError() {
            super(new FormErrorConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                return stateA;
            }
            return this;
        }
    }

}
