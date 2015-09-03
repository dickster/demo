package demo;

import demo.wicketTest.WicketFormComponentTest;
import org.apache.wicket.model.Model;
import org.junit.Before;
import org.junit.Test;

public class EasyAddressTest extends WicketFormComponentTest<EasyAddressHarness, EasyAddress> {

    private static final String FORM_ID = "form";

    @Before
    public void setup() {

    }

    @Test
    public void test() {
        renderFixture();

        assertModelValue(getHarness().getText(), null);

        getHarness().setAddress("100 main street");
        getHarness().submit();

        assertModelValue(getHarness().getText(), "100 main street");
    }

    @Override
    public EasyAddress createFixture(String id) {
        return new EasyAddress(id, Model.of(new Address()));
    }

    @Override
    protected Class<EasyAddressHarness> getHarnessClass() {
        return EasyAddressHarness.class;
    }


}