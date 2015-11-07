package forms;

import com.google.common.collect.Maps;
import demo.wicketTest.WicketHeadlessTest;
import forms.model.MappedModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.junit.Test;

import java.util.Map;

public class MappedModelTest extends WicketHeadlessTest {

    @Test
    public void test_form() {
        Map<String, Object> obj = Maps.newHashMap();
        MappedModel model = new MappedModel(obj);

        Form form = new Form("form", model);
        TextField<String> foo = new TextField<String>("foo");
        form.add(foo);
        TextField<String> abc = new TextField<String>("a.b.c");
        form.add(abc);
        TextField<String> hw = new TextField<String>("hello.world");
        form.add(hw);

        foo.getModel().setObject("foo");
        abc.getModel().setObject("ABC");
        hw.getModel().setObject("HELLO!");

        System.out.println(model);
    }
}
