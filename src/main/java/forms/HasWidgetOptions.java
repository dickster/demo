package forms;

// these are options that are rendered as a json object and consumed by the client.
//  an example would be options that you pass to jquery ui plugin when initializing.

public interface HasWidgetOptions {
    WidgetOptions getOptions();
}
