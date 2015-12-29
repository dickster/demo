package forms.widgets.config;

import org.apache.wicket.markup.Markup;

public class SectionPanelHandler {

    public int getInitialIndex() {
        return 0;
    }

    // getMarkup().add(new RawMarkup("<div...."));
    public Markup getBlankSlate() {
        return Markup.of("<div>blank slate</div>");
    }

}
