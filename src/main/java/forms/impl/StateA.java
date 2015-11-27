package forms.impl;

import forms.WfAjaxEvent;
import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class StateA extends WfFormState {

    private @Inject @Named("stateAx") WfFormState stateAx;
    private @Inject @Named("stateAy") WfFormState stateAy;
    private @Inject @Named("stateB") WfFormState stateB;
    private @Inject @Named("stateC") WfFormState stateC;
    private @Inject @Named("stateError") WfFormState stateError;

    public StateA() {
        super(new FormAConfig());
    }

    @Override
    public void handleAjaxEvent(WfAjaxEvent event) {
        super.handleAjaxEvent(event);
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if ("next".equals(event.getName())) {
            return stateAx;
        }
//        with(new ButtonConfig("next"));
//        with(new ButtonConfig("formAx"));
//        with(new ButtonConfig("formAy"));
//        with(new ButtonConfig("formB"));
//        with(new ButtonConfig("formC"));
//        with(new ButtonConfig("formError"));

        if ("formAy".equals(event.getName())) {
            return stateAy;
        }
        if ("formB".equals(event.getName())) {
            return stateB;
        }
        if ("formC".equals(event.getName())) {
            return stateC;
        }
        if ("formError".equals(event.getName())) {
            return stateAx;
        }
        return this;
    }
}
