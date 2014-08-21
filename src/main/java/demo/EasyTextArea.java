package demo;

import com.google.gson.Gson;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class EasyTextArea extends Panel {

    private static final String INIT_JS = "easy.textarea().init(%s)";

    private final JavaScriptHeaderItem JS = JavaScriptReferenceHeaderItem.forReference(new JavaScriptResourceReference(EasyTextArea.class, "easyTextArea.js"));
    private final CssHeaderItem CSS = CssHeaderItem.forReference(new CssResourceReference(EasyTextArea.class,"easyTextArea.css"));

    private WebMarkupContainer container;
    private boolean floating;
    private Integer rows;

    public EasyTextArea(String id, IModel<String> model) {
        super(id, model);
        add(new TextArea("textarea", model));
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JS);
        response.render(CSS);
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_JS, new Gson().toJson(getOptions()))));
//        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
    }

    protected EasyTextAreaOptions getOptions() {
        return new EasyTextAreaOptions();
    }

    public EasyTextArea floating() {
        floating = true;
        return this;
    }

    public EasyTextArea withRows(int rows) {
        this.rows = rows;
        return this;
    }

    // ------------------------------------------------------------------------------------------

    public class EasyTextAreaOptions {
        String id = getMarkupId();
        Boolean floating = EasyTextArea.this.floating;
        Integer rows = EasyTextArea.this.rows;
    }

}
