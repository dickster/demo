package demo;

import com.google.common.collect.Lists;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import java.util.List;

public class KeysPanel extends Panel {

    private List<String> keys = Lists.newArrayList("September", "August", "October", "January");
    private Boolean filter = true;
    private String key=null;

    public KeysPanel(String id) {
        super(id);
        add(new Form("form")
                .add(new DropDownChoice<String>("keys", new PropertyModel<String>(this,"key"), keys ))
                .add(new CheckBox("filter", new PropertyModel<Boolean>(this,"filter")))
                .add(new AjaxSubmitLink("activate") {
                    @Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        // do stuff...
                    }
                })
                .add(new AjaxSubmitLink("create") {
                    @Override protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                        super.onSubmit(target, form);
                        // show dialog here...
                    }
                }));

    }


}
