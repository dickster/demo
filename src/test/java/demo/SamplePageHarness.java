package demo;

import demo.wicketTest.Harness;
import demo.wicketTest.WicketHarness;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;

@SuppressWarnings("unchecked")
public class SamplePageHarness extends WicketHarness {

    private static final String FORM_ID = "form";
    private static final String TEXT_ID = "text";
    private static final String LABEL_ID = "label";
    private static final String ADDRESS_ID = "address";

    private @Harness({FORM_ID, ADDRESS_ID}) EasyAddressHarness addressHarness;

    public EasyAddressHarness getAddressHarness() {
        return addressHarness;
    }

    public TextField getText() {
        return (TextField)get(getTextPath());
    }

    public Component getLabel() {
        return get(getLabelPath());
    }

    public TextField setText(String s) {
        TextField tf = getText();
        FormTester formTester = getWicketTester().getFormTester(tf);
        formTester.setValue(tf, s);
        return tf;
    }

    public String getTextValue() {
        return getText().getValue();
    }

    public void clickText() {
        getWicketTester().executeAjaxEvent(getText(), "onclick");
    }

    public String getTextPath() {
        return getPathFor(FORM_ID, TEXT_ID);
    }

    public String getLabelPath() {
        return getPathFor(FORM_ID, LABEL_ID);
    }

    public String getAddressPath() {
        return getPathFor(FORM_ID, ADDRESS_ID);
    }

}
