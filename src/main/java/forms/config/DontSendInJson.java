package forms.config;


public @interface DontSendInJson {
    // marker annotation.  all fields in config will be serialized unless you add this annotation.
    // as a measure to reduce payload size of json, you should only include configuration options relevant to
    // the browser.

    // for example, including things like css class, html attributes, plugin options are required.
    // ...but things like validators, wicket or java based options should be exluded.  (e.g. the "required" configuration is
    // handled by wicket and will never be used by .js on browser side so @DontSendInJson.
}
