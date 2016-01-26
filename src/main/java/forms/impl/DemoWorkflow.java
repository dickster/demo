package forms.impl;

import forms.FormBasedWorkflow;
import forms.WfFormState;
import forms.WfState;
import forms.WidgetFactory;
import forms.model.GenericInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class DemoWorkflow  extends FormBasedWorkflow<GenericInsuranceObject> {

    private @Inject DemoState1 demoState1;
    private @Inject WidgetFactory widgetFactory;

    @Override
    public WfFormState getStartingState() {
        return demoState1;
    }

    @Nonnull
    @Override
    protected WfCompoundPropertyModel<GenericInsuranceObject> createModel() {
        return new WfCompoundPropertyModel<GenericInsuranceObject>(new GenericInsuranceObject());
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }

}


