package forms.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.SectionPanel;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.List;

public class SectionPanelConfig extends GroupConfig<SectionPanel> {

    public Boolean mandatory = true;
    public List<String> titleInputs = Lists.newArrayList();
    public String addTooltip = null;
    public boolean canAdd = true;
    public boolean collapsed;
    public boolean tooltipOnAdd;
    public int current = 0;//getIndexedModel().getIndex();
    public HeaderOptions header = new HeaderOptions();

    // TODO : put widget factory access here.
    private int min=1, max = Integer.MAX_VALUE;

    private @DontSendInJson GroupConfig panelConfig;

    public SectionPanelConfig(@Nonnull String name, GroupConfig panel) {
        super(name, WidgetTypeEnum.SECTION);
        this.panelConfig = panel;
    }

    public GroupConfig getPanelConfig() {
        return panelConfig;
    }

    // blank slate?? whaaaa....?

    @Override
    public SectionPanel create(String id) {
        return new SectionPanel(id, this);
    }

    public SectionPanelConfig withMin(int min) {
        this.min = min;
        return this;
    }

    public SectionPanelConfig withMax(int max) {
        this.max = max;
        return this;
    }

    public SectionPanelConfig withAtLeastOne() {
        this.min = 0;
        this.max = Integer.MAX_VALUE;
        return this;
    }

    public class HeaderOptions implements Serializable {
        public String minWidth = "7em";
        public String maxWidth = "10em";
    }



}
