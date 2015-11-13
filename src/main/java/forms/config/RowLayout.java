package forms.config;

import com.google.common.collect.Maps;
import forms.util.WfUtil;
import org.apache.wicket.Component;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class RowLayout implements Serializable {

    private Map<String, String> cols = Maps.newHashMap();

    public RowLayout() {
    }

    public RowLayout(Component... comps) {
        for (Component c:comps) {
            add(c, getColClass(c));
        }
    }

    private void add(Component c, @Nullable String style) {
        String name = WfUtil.getComponentName(c);
        if (name!=null) {
            cols.put(name, style);
        }
    }

    private @Nullable String getColClass(Component comp) {
        return null;
    }

    public RowLayout withCol(Component c, String style) {
        //c.setOutputMarkupId(true);
        add(c, style);
        return this;
    }


}
