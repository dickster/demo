package forms.impl;


import forms.WfFormState;
import forms.WfState;
import forms.WidgetFactory;
import forms.Workflow;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class PizzaWorkflow extends Workflow {

    private @Inject PizzaState1 pizza1;
    private @Inject @Named("customWidgetFactory") WidgetFactory widgetFactory;

    @Override
    public WfFormState getStartingState() {
        return pizza1;
    }

    @Override
    protected @Nonnull WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new PizzaModel());
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }
}
