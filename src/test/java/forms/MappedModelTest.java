package forms;

import com.google.common.collect.Maps;
import demo.wicketTest.WicketHeadlessTest;
import forms.config.Config;
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
        foo.setMetaData(Config.NAME, "foo");
        abc.setMetaData(Config.NAME, "a.b.c");
        hw.setMetaData(Config.NAME, "hello.world");

        foo.getModel().setObject("foo value");
        abc.getModel().setObject("ABC value");
        hw.getModel().setObject("HELLO value!");

        System.out.println(model);
    }
}
