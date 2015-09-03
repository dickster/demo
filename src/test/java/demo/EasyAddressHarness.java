package demo;

import demo.wicketTest.WicketHarness;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.tester.FormTester;

public class EasyAddressHarness extends WicketHarness {
    private static final String TEXT_ID = "text";

    public EasyAddressHarness setAddress(String text) {
        FormTester formTester = getWicketTester().getFormTester(getAddress());
        formTester.setValue(getAddress(), text);
        return this;
    }

    private TextField getAddress() {
        return (TextField) get(TEXT_ID);
    }

    public Component getText() {
        return get(TEXT_ID);
    }

}
