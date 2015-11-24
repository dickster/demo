package forms;


import forms.impl.CustomTheme;

import javax.inject.Inject;

public class DefaultToolkit implements Toolkit {

    private /*@Inject*/ Object sessionUser;  //
    private @Inject Theme theme;

    private boolean customTheme;


    @Override
    public Theme getTheme() {
        if (isCustomTheme()) {
            return new CustomTheme();
        }
        return theme;
    }

    public boolean isCustomTheme() {
        return customTheme;
    }

    public void setCustomTheme(boolean custom) {
        this.customTheme = custom;
    }



}
