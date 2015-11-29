package forms.impl;

import forms.FormBasedWorkflow;
import forms.WfState;
import forms.model.GermanInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class PizzaWorkflow extends FormBasedWorkflow {

    private @Inject PizzaState1 pizza1;

    @Override
    public WfState getStartingState() {
        return pizza1;
    }


    @Override
    protected WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new PizzaModel());
    }
}
