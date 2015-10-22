package forms;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.wicket.Component;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class RowConfig implements Serializable {

    private List<String> cols = Lists.newArrayList();
    private Map<Key, String> styles = Maps.newHashMap();

    public RowConfig() {
    }

    public RowConfig(Component... comps) {
        for (Component c:comps) {
            c.setOutputMarkupId(true);
            add(c, getColClass(c));
        }
    }

    private void add(Component c, @Nullable String style) {
        Key key = new Key(c);
        cols.add(key.toString());
        styles.put(key, style);
    }

    private @Nullable String getColClass(Component comp) {
        return null;
    }

    public RowConfig withCol(Component c, String style) {
        c.setOutputMarkupId(true);
        add(c, style);
        return this;
    }

    public RowConfig withDefaultCols() {
        styles = Maps.newHashMap();
        return this;
    }


    public Collection<Component> getComponents() {
        List<Component> components = Lists.newArrayList();
        for (Key key:styles.keySet()) {
            components.add(key.getComponent());
        }
        return components;
    }

    // i need a wrapper just so when i serialize to json, i will output the name via toString().
    // this is because Gson's default map serializer uses toString() on keys.
    class Key implements Serializable {
        private Component c;

        Key() {
        }

        Key(Component c) {
            this.c = c;
        }

        Component getComponent () {
            return c;
        }

        @Override
        public String toString() {
            return c.getMetaData(Config.NAME);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            if (c != null ? !c.equals(key.c) : key.c != null) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return c != null ? c.hashCode() : 0;
        }
    }

}
