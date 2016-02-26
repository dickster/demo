package forms;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import demo.wicketTest.WicketHeadlessTest;
import forms.model.MappedModel;
import forms.model.WfCompoundPropertyModel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.junit.Test;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class MappedModelTest extends WicketHeadlessTest {

    @Test
    public void test_WfCompound() {
        CompoundPropertyModel<Foo> model = new CompoundPropertyModel<Foo>(new Foo());

        Form form = new Form("form", model);
        TextField<String> hello = new TextField<String>("hello");
        form.add(hello);
        TextField<String> stuff0 = new TextField<String>("stuff", new PropertyModel(model, "stuff[0]"));
        form.add(stuff0);
        PropertyModel stuff = new PropertyModel(model, "stuff");
        TextField<String> stuff1 = new TextField<String>("stuff1", new PropertyModel<String>(stuff, "[1]"));
        form.add(stuff1);

        hello.getModel().setObject("hello value");
        stuff0.getModel().setObject("S0 value!");
        stuff1.getModel().setObject("S1 value");

        System.out.println(model.getObject());

    }

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

        foo.getModel().setObject("foo value");
        abc.getModel().setObject("ABC value");
        hw.getModel().setObject("HELLO value!");

        System.out.println(model);
    }

    class  Foo {
        private String hello = "xxxx";
        private Integer world = 4;
        private List<String> stuff = Lists.newArrayList("apple", "orange", "banana", "cherry", "peach", "pear");

        @Override
        public String toString() {
            return "Foo{" +
                    "hello='" + hello + '\'' +
                    ", stuff=" + stuff +
                    ", world=" + world +
                    '}';
        }
    }
}
