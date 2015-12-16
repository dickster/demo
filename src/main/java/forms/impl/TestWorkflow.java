package forms.impl;

import com.google.common.base.Preconditions;
import forms.FormBasedWorkflow;
import forms.StartingPoint;
import forms.WfAjaxEvent;
import forms.WfFormState;
import forms.WidgetFactory;
import forms.WorkflowException;
import forms.model.GenericInsuranceObject;
import forms.model.WfCompoundPropertyModel;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

public class TestWorkflow extends FormBasedWorkflow<GenericInsuranceObject> {

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
    public void handleAjaxEvent(@Nonnull WfAjaxEvent event) throws WorkflowException {
        super.handleAjaxEvent(event);
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
    protected WfCompoundPropertyModel createModel() {
        return new WfCompoundPropertyModel(new GenericInsuranceObject());
    }

    @Override
    protected void clearErrors() {
        getObject().clearErrors();
    }

    @Override
    public void addError(Object error) {
        Preconditions.checkArgument(error!=null,"can't use null as an error.");
        getObject().getErrors().add(error==null?"": error.toString());
    }
}
