package demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import forms.RowConfig;
import forms.SectionConfig;
import forms.WidgetConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
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

//    public static final MetaDataKey<String> COL_CSS_KEY = new MetaDataKey<String>() {};


    public static final String JS = "easy.layout(%s);";

    public static final String COL_MD_1 = "col_md_1";
    public static final String COL_MD_2 = "col_md_2";
    public static final String COL_MD_3 = "col_md_3";
    public static final String COL_MD_4 = "col_md_4";
    public static final String COL_MD_5 = "col_md_5";
    public static final String COL_MD_6 = "col_md_6";
    public static final String COL_MD_12 = "col_md_12";

    private final transient Gson gson = new Gson();
    // etc... add as needed.

    public PageLayout() {
    }

    private List<SectionConfig> sectionConfigs = Lists.newArrayList();
    private Map<String, String> idMap = Maps.newHashMap();

    protected PageLayout layout(SectionConfig... configs) {
        return this;
    }

    protected OnDomReadyHeaderItem asHeaderItem() {
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

    public PageLayout addInDefaltManner(List<Component> components, int compPerSec, int colsPerRow) {
        // arbitrarily divide page into N sections, X columns per row.
        int comps = 0;
        while (comps<components.size()) {
            List<Component> secComps = components.subList(comps, Math.min(comps+compPerSec, components.size()));
            addDefaultSection(secComps, colsPerRow);
            comps+=compPerSec;
        }
        return this;
    }


    private final void addDefaultSection(List<Component> components, int colsPerRow) {
        String css = getCss(colsPerRow);
        SectionConfig sectionConfig = new SectionConfig();
        int col = 0;
        RowConfig rowConfig = null;
        for (Component c:components) {
            if (col==0) {
                rowConfig = new RowConfig();
                sectionConfig.withRow(rowConfig);
            }
            rowConfig.withCol(c, css);
            col = (col+1)%colsPerRow;
        }
        add(sectionConfig);
    }

    private String getCss(int colsPerRow) {
        switch (colsPerRow) {
            case 1:
                return COL_MD_12;
            case 2:
                return COL_MD_6;
            case 3:
                return COL_MD_4;
            case 4:
                return COL_MD_5;
            case 6:
                return COL_MD_2;
            default:
                return COL_MD_1;
        }

    }

}
