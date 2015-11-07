package forms.config;

import com.google.common.collect.Lists;
import org.apache.wicket.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class SectionConfig implements Serializable {

    private String name;
    private Boolean renderContainer = null;
    private List<RowConfig> rows = Lists.newArrayList();

    public SectionConfig() {
    }

    public SectionConfig withContainerRendered() {
        renderContainer = true;
        return this;
    }

    public SectionConfig(String name) {
        withName(name);
    }

    public SectionConfig withName(String name) {
        this.name = name;
        return this;
    }

    public SectionConfig withRow(Component... comps) {
        this.rows.add(new RowConfig(comps));
        return this;
    }

    public SectionConfig withRow(RowConfig rowConfig) {
        this.rows.add(rowConfig);
        return this;
    }

    public SectionConfig withRows(List<RowConfig> rows) {
        this.rows.addAll(rows);
        return this;
    }

    public Collection<Component> getComponents() {
        List<Component> components = Lists.newArrayList();
        for (RowConfig config:rows) {
            components.addAll(config.getComponents());
        }
        return components;
    }

}
