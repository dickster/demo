package forms.widgets.config;

import forms.Div;

import javax.annotation.Nonnull;
// todo : rename this to ??? ContainerConfig & new Container(id, this);???
public class DivConfig extends GroupConfig<Div> {

    public DivConfig(@Nonnull String name) {
        super(name);
    }

    @Override
    public Div create(String id) {
          throw new UnsupportedOperationException("don't create these via widget factory.");
        //return new Div(id, this);
    }

}
