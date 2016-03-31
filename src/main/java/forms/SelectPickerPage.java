package forms;

import com.google.common.collect.Lists;

import demo.resources.Resource;
import forms.widgets.BootstrapSelectPicker;
import forms.widgets.config.SelectConfig;
import org.apache.wicket.Application;
import org.apache.wicket.markup.head.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import java.util.List;

public class SelectPickerPage extends WebPage {

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-select.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");

    private String fruit, fruit2, veggie;

    private List<String> meat = Lists.newArrayList();


    private final List veggies = Lists.newArrayList("potato", "carrot", "spinach", "turnip", "beet", "squash", "aspargus", "pea", "corn");
    private final List fruits = Lists.newArrayList("apple", "orange", "strawberry", "kiwi", "mango", "blueberry", "banana", "lime", "lemon");
    private final List meats = Lists.newArrayList("lamb", "ostrich", "horse", "venison", "t-bone", "pork", "chicken", "turkey", "mmm...sausages", "moose", "whale", "spam", "ham", "bacon");

    private List<? extends HeaderItem> headerItems = Lists.newArrayList(
            JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()),
            JavaScriptHeaderItem.forReference(JQUERY_UI_JS),
            CssHeaderItem.forReference(JQUERY_UI_CSS),
            JavaScriptHeaderItem.forReference(BOOTSTRAP_JS),
            CssHeaderItem.forReference(BOOTSTRAP_CSS)
    );

    public SelectPickerPage(final PageParameters parameters) {
        setDefaultModel(new CompoundPropertyModel(this));

        add(new BootstrapSelectPicker("fruit",
                new SelectConfig()
                        .withChoices(fruits)
                        .withTitle("fruits, fruits, fruits")
                        .withOption("width", "454px"))
            .withModel(new PropertyModel(this, "fruit"))
        );
//                        .withOption("header", "Les Fruits")));


        add(new BootstrapSelectPicker("veggie",
                new SelectConfig()
                        .withTitle("eat your veggies!")
                        .withJsChoices("jsVeggies")
                        .withKeywordSearching()
                        .withKeysDisplayed()
                                //.withOptions(veggies)
                        .allowSearch())
                .withModel(new PropertyModel(this, "veggie"))
        );


        add(new BootstrapSelectPicker("meat",
                new SelectConfig()
                        .withChoices(meats)
                        .allowMultiple()
                        .withOption("actionsBox", true)
                        //.withOption("noneSelectedText", "pick one!")
                        )
                .withModel(new PropertyModel(this, "meat"))
        );

        add(new BootstrapSelectPicker<String>("fruit2",
                new SelectConfig()
                        .withChoices(fruits)
                        .withTitle("fruits, fruits, fruits")
                        .withOption("header", "The Fruits"))
                .withModel(new PropertyModel(this, "fruit2"))
        );

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        for (HeaderItem item:headerItems) {
            response.render(item);
        }
    }

}
