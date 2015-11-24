package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.WfState;
import forms.WfSubmitEvent;
import forms.Workflow;
import forms.config.FormAAnotherNewLayoutConfig;
import forms.config.FormAConfig;
import forms.config.FormANewLayoutConfig;
import forms.config.FormBConfig;
import forms.config.FormCConfig;
import forms.config.FormErrorConfig;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

public class StateError implements Serializable {// ------------------------------------------------------------------------------

    // states.  typically declared in another file?   whatevs.
    // ------------------------------------------------------------------------------
    public static class StateA extends WfFormState {
        private
        @Inject
        @Named("stateAx")
        WfFormState stateAx;
        private
        @Inject
        @Named("stateB")
        WfFormState stateB;

        StateA() {
            null.WfFormState(new FormAConfig());
        }

        @Override
        public WfFormState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAx;
            }
            if ("formB".equals(event.getName())) {
                return stateB;
            }
            return null;
        }
    }

    public static class StateAx extends WfFormState {
        private
        @Inject
        @Named("stateAy")
        WfFormState stateAy;

        StateAx() {
            null.WfFormState(new FormANewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateAy;
            }
            return null;
        }
    }

    public static class StateAy extends WfFormState {
        private
        @Inject
        @Named("stateB")
        WfFormState stateB;
        private
        @Inject
        @Named("stateError")
        WfFormState stateError;

        StateAy() {
            null.WfFormState(new FormAAnotherNewLayoutConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateB;
            }
            return null;
        }

        @Nonnull
        @Override
        public WfState handleError(Workflow workflow, WfSubmitEvent event) {
            return stateError;
        }
    }

    public static class StateB extends WfFormState {
        private
        @Inject
        @Named("stateC")
        WfFormState stateC;

        StateB() {
            null.WfFormState(new FormBConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("next".equals(event.getName())) {
                return stateC;
            }
            return null;
        }

    }

    public static class StateC extends WfFormState {
        StateC() {
            null.WfFormState(new FormCConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                workflow.end();
            }
            return null;
        }
    }

    public static class StateError extends WfFormState {
        private
        @Inject
        @Named("stateA")
        WfFormState stateA;

        public StateError() {
            null.WfFormState(new FormErrorConfig());
        }

        @Override
        public WfState handleEvent(Workflow workflow, WfEvent event) {
            if ("ok".equals(event.getName())) {
                return stateA;
            }
            return null;
        }
    }
}