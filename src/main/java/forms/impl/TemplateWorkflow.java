package forms.impl;

import forms.StartingPoint;
import forms.WfFormState;
import forms.WidgetFactory;
import forms.Workflow;
import forms.model.GenericInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class TemplateWorkflow extends Workflow<GenericInsuranceObject> {

    private @Inject TemplateState1 s1;
    private @Inject WidgetFactory widgetFactory;

    @Override
    public WfFormState getStartingState() {
        return s1;
    }

    @Override
    public void end() {
        updatePage(new StartingPoint());
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


