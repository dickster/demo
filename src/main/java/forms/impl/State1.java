package forms.impl;

import forms.WfFormState;
import forms.WfSubmitEvent;
import forms.Workflow;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

public class State1 extends WfFormState {

    private @Inject @Named("stateA") WfFormState stateA;
    //private @Inject FormDAO formDAO;

    public State1() {
        super(new Form1Config());
        //super(formDAO.getForm("myFormName", workflow);    this is the long term code to read from DAO.
    }

    @Nonnull
    @Override
    public WfFormState handleEvent(Workflow workflow, WfSubmitEvent event) {
        if ("next".equals(event.getName())) {
            return stateA;
        }
        return this;
    }
}
