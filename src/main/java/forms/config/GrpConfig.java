package forms.config;

import forms.Group;

import javax.annotation.Nonnull;

public class GrpConfig extends GroupConfig<Group> {

    public GrpConfig(@Nonnull String name) {
        super(name);
    }


    @Override
    public Group create(String id) {
        return new Group(id, this);
   }

}
