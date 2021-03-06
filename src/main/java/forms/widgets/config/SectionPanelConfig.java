package forms.widgets.config;

import com.google.common.collect.Lists;
import forms.WidgetTypeEnum;
import forms.widgets.SectionPanel;

import javax.annotation.Nonnull;

public class SectionPanelConfig extends GroupConfig<SectionPanel> {

    public static final String TITLEFOR_NEW_VALUES = "titleForNewValues";
    public static final String TITLE_INPUTS = "titleInputs";

    public Boolean mandatory = true;
    public String addTooltip = null;
    public boolean canAdd = true;
    public boolean collapsed;
    public int current = 0;
    public int min=1, max = Integer.MAX_VALUE;

    public SectionPanelConfig(@Nonnull String name) {
        super(name, WidgetTypeEnum.SECTION);
        withCss("section");
        withTitle(name);
    }

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

    public SectionPanelConfig withAddTooltip(String tooltip) {
        this.addTooltip = tooltip;
        return this;
    }

    public SectionPanelConfig withTitleForNewValues(String title) {
        return (SectionPanelConfig) withOption(TITLEFOR_NEW_VALUES, title);
    }

    public SectionPanelConfig withTitleInputs(String... inputs) {
        return (SectionPanelConfig) withOption(TITLE_INPUTS, Lists.newArrayList(inputs));
    }

    public String getTitleForNewValues() {
        return (String) getOptions().get(TITLEFOR_NEW_VALUES);
    }

}
