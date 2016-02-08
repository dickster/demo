package forms.widgets;

import demo.resources.Resource;
import forms.model.ParentModel;
import forms.spring.StringLoader;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import forms.widgets.config.SelectPickerConfig;
import forms.widgets.config.YesNoConfig;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.inject.Inject;


public class YesNo extends FormComponentPanel<Boolean> implements HasConfig {

    private @Inject StringLoader stringLoader;

    private YesNoConfig config;
    private RadioGroup<Boolean> group;



    public YesNo(String id, YesNoConfig config) {
        super(id);
        this.config = config;

        group = new RadioGroup("group", new ParentModel(this));
        add(group);

        group.add(new Radio("yes", Model.of(Boolean.TRUE)));
        group.add(new Radio("no", Model.of(Boolean.FALSE)));

        setRenderBodyOnly(false);
        setOutputMarkupId(true);
    }

    private IModel<String> yesModel() {
        return Model.of(stringLoader.get(config.getYesLabel()));
    }

    private IModel<String> noModel() {
        return Model.of(stringLoader.get(config.getNoLabel()));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    protected void convertInput() {
        setConvertedInput(group.getConvertedInput());
    }

}
