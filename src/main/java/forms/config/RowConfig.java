package forms.config;

import com.google.common.collect.Maps;
import org.apache.wicket.Component;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

public class RowConfig implements Serializable {

    private Map<String, String> cols = Maps.newHashMap();

    public RowConfig() {
    }

    public RowConfig(Component... comps) {
        for (Component c:comps) {
            //c.setOutputMarkupId(true);
            add(c, getColClass(c));
        }
    }

    private void add(Component c, @Nullable String style) {
        String name = c.getMetaData(Config.NAME);
        if (name!=null) {
            cols.put(name, style);
        }
    }

    private @Nullable String getColClass(Component comp) {
        return null;
    }

    public RowConfig withCol(Component c, String style) {
        //c.setOutputMarkupId(true);
        add(c, style);
        return this;
    }


}
