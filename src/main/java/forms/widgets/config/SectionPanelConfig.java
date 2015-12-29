package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.SectionPanel;

import javax.annotation.Nonnull;
import java.util.List;

public class SectionPanelConfig extends GroupConfig<SectionPanel> {

    public Boolean mandatory = true;
    public List<String> titleInputs = Lists.newArrayList();
    public String addTooltip = null;
    public boolean canAdd = true;
    public boolean collapsed;
    public boolean tooltipOnAdd;
    public int current = 0;

    private int min=1, max = Integer.MAX_VALUE;

    public SectionPanelConfig(@Nonnull String name) {
        super(name, WidgetTypeEnum.SECTION);
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

}
