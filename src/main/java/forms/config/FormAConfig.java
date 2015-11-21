package forms.config;

public class FormAConfig extends FormConfig {

    public static final String FORM_CONFIG_A = "FORM-A";

    public FormAConfig() {
        super(FORM_CONFIG_A);
        addConfigs();
        withTitle("Form A");
    }

    private void addConfigs() {
        with(new GroupConfig("names")
            .withConfig(new LabelConfig("first name"))
            .withConfig(new TextFieldConfig("name.first"))
            .withConfig(new LabelConfig("middle name"))
            .withConfig(new TextFieldConfig("name.middle"))
            .withConfig(new LabelConfig("last name"))
            .withConfig(new TextFieldConfig("name.last")));


        with(new GroupConfig("2")
                    .withConfig(new LabelConfig("age"))
                    .withConfig(new TextFieldConfig("insured.age"))
                    .withConfig(new LabelConfig("occupation"))
                    .withConfig(new TextFieldConfig("insured.occupation")));

        with(new ButtonConfig("next"));
        with(new ButtonConfig("formB"));

        with(new DialogConfig("myDialog")
                .withButtons(new DialogSubmitButtonConfig("ok"),
                        new DialogSubmitButtonConfig("cancel"),
                        new DialogSubmitButtonConfig("save") )
                 .withTitle("Hello!")
                 .withConfigs(new LabelConfig("First Name").name("fnlbl"),
                           new TextFieldConfig("name.first").name("fname"),
                           new LabelConfig("Last Name").name("lnlbl"),
                           new TextFieldConfig("name.last").name("lname"))

        );
//        dialog.with(new AjaxButtonConfig("Ok"));
        with(new DialogButtonConfig("test", "myDialog"));
    }
}
