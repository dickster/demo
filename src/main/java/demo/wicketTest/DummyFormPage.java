package demo.wicketTest;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class DummyFormPage<C extends Component> extends WebPage {

    public DummyFormPage(final PageParameters parameters) {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Form("form")
                .add(createComponent("component-id")));
    }

    protected abstract C createComponent(String id);

}
