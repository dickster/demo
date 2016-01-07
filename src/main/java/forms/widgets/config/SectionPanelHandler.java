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

    public String getTabTitle(Object /* <T> */ obj) {
        // will i know type of object?? nah.....
        return obj.toString();
    }

}
