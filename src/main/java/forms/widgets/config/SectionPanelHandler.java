package forms.widgets.config;

import forms.widgets.SectionPanel;
import org.apache.wicket.markup.Markup;

import java.util.List;

public interface SectionPanelHandler {

    int getInitialIndex();

    Markup getBlankSlate();

    String getTabTitle(Object  obj);

    // post an event?  every component can listen for it.
    int deleteTab(List<?> list, int index, int currentIndex);

    Object createNewTabData(Object obj);

    String getTooltip();

    String getName();

    String getIconCss();

    Integer getOrdinal();

    String getHref();

    void initialize(SectionPanel sectionPanel);
}
