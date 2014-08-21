package demo;

import com.google.gson.Gson;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import java.util.List;

public class MultiSelect extends DropDownChoice {
    private static final String INIT_MULTISELECT_JS = "easy.multiselect.init(%s);";

    public MultiSelect(String id) {
        super(id);
    }

    public MultiSelect(String id, List choices) {
        super(id, choices);
    }

    public MultiSelect(String id, List choices, IChoiceRenderer renderer) {
        super(id, choices, renderer);
    }

    public MultiSelect(String id, IModel model, List choices) {
        super(id, model, choices);
    }

    public MultiSelect(String id, IModel model, List choices, IChoiceRenderer renderer) {
        super(id, model, choices, renderer);
    }

    public MultiSelect(String id, IModel choices) {
        super(id, choices);
    }

    public MultiSelect(String id, IModel model, IModel choices) {
        super(id, model, choices);
    }

    public MultiSelect(String id, IModel choices, IChoiceRenderer renderer) {
        super(id, choices, renderer);
    }

    public MultiSelect(String id, IModel model, IModel choices, IChoiceRenderer renderer) {
        super(id, model, choices, renderer);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(OnDomReadyHeaderItem.forScript(String.format(INIT_MULTISELECT_JS,new Gson().toJson(getOptions()))));
    }

    protected MultiSelectOptions getOptions() {
        return new MultiSelectOptions();
    }

    public class MultiSelectOptions {
        public String id = MultiSelect.this.getMarkupId();
    }
}
