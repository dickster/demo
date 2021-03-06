package forms.impl;

import forms.*;
import forms.model.GenericInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

public class CommercialWorkflow extends Workflow {

    @Inject @Named("stateA") private WfFormState stateA;
    @Inject @Named("widgetFactory") private WidgetFactory widgetFactory;

    public CommercialWorkflow() {
        super();
        withValue("defaultCountry", Locale.US);
    }

    @Override
    protected void init() {
        //override if you want some workflow startup stuff to happen.
    }

    @Override
    public WfFormState getStartingState() {
        // if (somePreval) return stateB else..
        return stateA;
    }

    @Override
    public void end() {
        updatePage(new StartingPoint());
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }

    @Override
    protected @Nonnull WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new GenericInsuranceObject());
    }

}
