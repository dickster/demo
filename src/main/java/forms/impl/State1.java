package forms.impl;

import forms.WfEvent;
import forms.WfFormState;
import forms.Workflow;

import javax.inject.Inject;
import javax.inject.Named;

public class State1 extends WfFormState {

    private @Inject @Named("stateA") WfFormState stateA;
    //private @Inject FormDAO formDAO;

    public State1() {
        super(new Form1Config());
        //super(formDAO.getForm("myFormName", workflow);    this is the long term code to read from DAO.
    }

    @Override
    public WfFormState handleEvent(Workflow workflow, WfEvent event) {
        if ("next".equals(event.getName())) {
            return stateA;
        }
        return this;
    }
}
