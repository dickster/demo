package forms.impl;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import forms.*;
import forms.model.GenericInsuranceObject;
import forms.model.WfCompoundPropertyModel;
import forms.widgets.WfPostalCodeChangedEvent;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

public class TestWorkflow extends Workflow<GenericInsuranceObject> {

    @Inject @Named("infoState") private WfFormState infoState;
    @Inject @Named("rejectedState") private WfFormState rejectedState;
    @Inject @Named("widgetFactory") private WidgetFactory widgetFactory;

    private boolean rejected = false;

    public TestWorkflow() {
        super();
        withValue("defaultCountry", Locale.US);
    }

    @Override
    protected void init() {
        GenericInsuranceObject obj = getModel().getObject();
        rejected = "MEXICO".equalsIgnoreCase(obj.getInsured().getCountry());
    }

    @Override
    public WfFormState getStartingState() {
        if (rejected) {
            return rejectedState;
        }
        return infoState;
    }

    @Override
    public void end() {
        updatePage(new StartingPoint());
    }

    @Override
    public WidgetFactory getWidgetFactory() {
        return widgetFactory;
    }

    @Subscribe
    public void onPostalCodeChange(WfPostalCodeChangedEvent event) {
        event.getTarget().appendJavaScript("alert('the postal code changed!');");
        System.out.println(event.getName());
    }

    @Override
    protected @Nonnull WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new GenericInsuranceObject());
    }

    @Override
    protected void reset() {
        getObject().clearErrors();
    }

    @Override
    public void addError(Object error) {
        Preconditions.checkArgument(error!=null,"can't use null as an error.");
        getObject().getErrors().add(error==null?"": error.toString());
    }
}
