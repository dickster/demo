package forms.impl;

import forms.FormBasedWorkflow;
import forms.WfState;
import forms.WidgetFactory;
import forms.model.WfCompoundPropertyModel;

import javax.inject.Inject;
import javax.inject.Named;

public class PizzaWorkflow extends FormBasedWorkflow {

    private @Inject PizzaState1 pizza1;
    private @Inject @Named("customWidgetFactory") WidgetFactory widgetFactory;

    @Override
    public WfState getStartingState() {
        return pizza1;
    }


    @Override
    protected WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new PizzaModel());
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }
}
