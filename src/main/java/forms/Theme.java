package forms;


import com.google.common.collect.Lists;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;

import java.util.List;
import java.util.Locale;

public interface Theme {

    // widget factory/form will create widgets and apply theme to them.
    //   widget = factory.create(....);
    //   theme.apply(widget);

    List<CssHeaderItem> getCss();

    String getCssClass();

    String getName();

    Locale getDefaultLocale();

    String getFlavour();  //could be something like "cozy, roomy, bigger, brighter, +5, Extreme, or whatever theme extensions you have.

    void apply(Form form);

    void renderHead(IHeaderResponse response);
}
