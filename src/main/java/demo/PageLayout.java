package demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import forms.SectionConfig;
import forms.WidgetConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

// pages
//  have sections
//      which have rows
//          which have columns
//              which have components in them.
public class PageLayout implements Serializable {

    public static final MetaDataKey<String> COL_CSS_KEY = new MetaDataKey<String>() {};



    public static final String JS = "easy.layout(%s);";

    public static final String COL_MD_1 = "col_md_1";
    public static final String COL_MD_2 = "col_md_2";
    public static final String COL_MD_3 = "col_md_3";
    public static final String COL_MD_4 = "col_md_4";
    public static final String COL_MD_5 = "col_md_5";

    private final transient Gson gson = new Gson();
    // etc... add as needed.

    public PageLayout() {
    }

    private List<SectionConfig> sectionConfigs = Lists.newArrayList();
    private Map<String, String> idMap = Maps.newHashMap();

    protected PageLayout layout(SectionConfig... configs) {
        return this;
    }

    protected OnDomReadyHeaderItem layoutItem() {
        return OnDomReadyHeaderItem.forScript(String.format(JS, gson.toJson(sectionConfigs)));
    }

    private Map<String, String> updateIdMap(SectionConfig sectionConfig) {
        for (Component c:sectionConfig.getComponents()) {
            String name = getName(c);
            if (StringUtils.isNotBlank(name)) {
                if (idMap.get(name)!=null) {
                    // "WARN: You have two components with the same name : " + name;
                    System.out.println("duplicate ids....! " );
                    idMap.put("ERROR OCCURRED - duplicate ids", name);
                    return idMap;
                }
                idMap.put(name, c.getMarkupId());
            }
        }
        return idMap;
    }

    protected String getName(Component c) {
        return c.getMetaData(WidgetConfig.NAME);
    }

    public PageLayout add(SectionConfig sectionConfig) {
        sectionConfigs.add(sectionConfig);
        updateIdMap(sectionConfig);
        return this;
    }


}
