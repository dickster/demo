package forms.util;

// sprinkle this on any HasConfig objects that you only want to
// show up during debug mode.
public @interface DebugModeConfig {
    boolean value()  default false;
}
