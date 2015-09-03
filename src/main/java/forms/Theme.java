package forms;


import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.html.form.Form;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Locale;

public interface Theme {

//    @Nonnull List<CssHeaderItem> getCss();
//    @Nonnull List<JavaScriptReferenceHeaderItem> getJavaScript();
//    @Nonnull List<OnDomReadyHeaderItem> getDomReadyScript();
// above 3 replaced by single generic call.
    @Nonnull List<? extends HeaderItem> getHeaderItems();

    String getCssClass();

    @Nonnull String getName();

    Locale getDefaultLocale();

    //a lower level setting.  so the theme might be "high contrast" but there might be flavours like...
    //  "cozy, roomy, bigger, etc.."
    String getFlavour();

    void apply(Form form);

}
