package forms.impl;

import forms.FormBasedWorkflow;
import forms.StartingPoint;
import forms.WfAjaxEvent;
import forms.WfFormState;
import forms.WfState;
import forms.WorkflowException;
import forms.model.GermanInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

public class CommercialWorkflow extends FormBasedWorkflow {

    private @Inject @Named("stateA") WfFormState stateA;

    public CommercialWorkflow() {
        super();
        withValue("defaultCountry", Locale.US);
    }

    @Override
    protected void init() {
        //override if you want some workflow startup stuff to happen.
    }

    @Override
    public WfState getStartingState() {
        return stateA;
    }

    @Override
    public void handleAjaxEvent(@Nonnull WfAjaxEvent event) throws WorkflowException {
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

}
