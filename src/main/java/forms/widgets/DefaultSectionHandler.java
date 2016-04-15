package forms.widgets;

import forms.spring.WfNavigator;
import forms.widgets.config.SectionPanelHandler;
import org.apache.wicket.markup.Markup;

import javax.inject.Inject;
import java.util.List;

public class DefaultSectionHandler implements SectionPanelHandler {

    private @Inject WfNavigator wfNavigator;

    private SectionPanel sectionPanel;

    public DefaultSectionHandler() {
    }

    @Override
    public int getInitialIndex() {
        return 0;
    }

    @Override
    public Markup getBlankSlate() {
        return Markup.of("<div>blank slate-override this method to provide your own markup</div>");
    }

    @Override
    public String getTabTitle(Object obj) {
        return obj==null ? "" : obj.toString();
    }

    @Override
    public int deleteTab(List<?> list, int index, int currentIndex) {
        list.remove(index);
        if (index==currentIndex && list.size()>0) {
            return(Math.max(0, index-1));
        }
        // put this in the sectionPanel itself.
//        wfNavigator.getWorkflow(sectionPanel).post(new SectionDeleteEvent());
        return currentIndex;
    }

    // TODO : replace this with a spring bean = "sectionPanelHandler".
    // it will handle data creation, status, ajax, etc...
    @Override
    public Object createNewTabData(Object data) {
        try {
            //if (data.getClass().isInstance(SectionPanelData.class))  ((SectionPanelData).createNew(data, workflow);
            return data.getClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("can't create new tab for class " + data.getClass().getSimpleName());
        }
    }

    @Override
    public String getTooltip() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getIconCss() {
        return null;
    }

    @Override
    public Integer getOrdinal() {
        return null;
    }

    @Override
    public String getHref() {
        return null;
    }

    @Override
    public void initialize(SectionPanel sectionPanel) {
        this.sectionPanel = sectionPanel;
    }
}
