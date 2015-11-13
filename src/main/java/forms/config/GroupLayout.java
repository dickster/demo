package forms.config;

import com.google.common.collect.Lists;
import org.apache.wicket.Component;

import java.io.Serializable;
import java.util.List;

public class GroupLayout implements Serializable {

    private String name;
    private Boolean renderContainer = null;
    private String css = "col_md_12";
    private List<RowLayout> rows = Lists.newArrayList();

    public GroupLayout() {
    }

    public GroupLayout withContainerRendered() {
        renderContainer = true;
        return this;
    }

    public GroupLayout(String name) {
        withName(name);
    }

    public GroupLayout withName(String name) {
        this.name = name;
        return this;
    }

    public GroupLayout withRow(Component... comps) {
        this.rows.add(new RowLayout(comps));
        return this;
    }

    public GroupLayout withRow(RowLayout rowConfig) {
        this.rows.add(rowConfig);
        return this;
    }

    public GroupLayout withRows(List<RowLayout> rows) {
        this.rows.addAll(rows);
        return this;
    }

    public String getCss() {
        return css;
    }

    public GroupLayout withCss(String css) {
        this.css = css;
        return this;
    }

    public boolean isEmpty() {
        return this.rows.isEmpty();
    }
}
