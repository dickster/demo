package forms.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import forms.util.WfUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Map;

// pages
//  have sections
//      which have rows
//          which have columns
//              which have components in them.
public class PageLayout implements Serializable {

//    public static final MetaDataKey<String> COL_CSS_KEY = new MetaDataKey<String>() {};

//    public static final String JS = "easy.layout(%s);";

//    public static final String COL_MD_1 = "col_md_1";
//    public static final String COL_MD_2 = "col_md_2";
//    public static final String COL_MD_3 = "col_md_3";
//    public static final String COL_MD_4 = "col_md_4";
//    public static final String COL_MD_5 = "col_md_5";
//    public static final String COL_MD_6 = "col_md_6";
//    public static final String COL_MD_12 = "col_md_12";

    private final transient Gson gson = new Gson();

    private Map<String, String> nameToId = Maps.newHashMap();
    private Map<String, String> nameToCss = Maps.newHashMap();

    public PageLayout() {
    }

    public PageLayout(MarkupContainer parent, final GroupConfig formConfig) {
        parent.visitChildren(Component.class, new IVisitor<Component, Void>() {
            @Override
            public void component(Component widget, IVisit visit) {
                String name = WfUtil.getComponentName(widget);
                if (name != null) {
                    addNameToId(name, widget.getMarkupId());
                    addNameToCss(name, formConfig.getConfigWithName(name).getCss());
                }
            }
        });
    }

    private void addNameToCss(@Nonnull String name, @Nonnull String css) {
        Preconditions.checkArgument(StringUtils.isNotBlank(name) && StringUtils.isNotBlank(name));
        Preconditions.checkState(nameToCss.get(name)==null, " unique names for components are required! - " + name + " already has Css set : " + nameToCss.get(name));
        nameToCss.put(name, css);
    }

    public void addNameToId(@Nonnull String name, @Nonnull String id) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(name));
        Preconditions.checkState(nameToId.get(name)==null, " unique names for components are required! - " + name + " is already used by component " + nameToId.get(name));
        nameToId.put(name, id);
    }

//    protected PageLayout layout(GroupLayout... configs) {
//        return this;
//    }

//    protected OnDomReadyHeaderItem asHeaderItem() {
//        return OnDomReadyHeaderItem.forScript(String.format(JS, gson.toJson(sections)));
//    }
//
//    private @Nullable String compName(Component c) {
//        return WfUtil.getComponentName(c);
//    }

//    public PageLayout add(GroupLayout sectionConfig) {
//        sections.add(sectionConfig);
//        return this;
//    }

//    public PageLayout addInDefaltManner(List<Component> components, int compPerSec, int colsPerRow) {
//        // arbitrarily divide page into N sections, X columns per row.
//        int comps = 0;
//        while (comps<components.size()) {
//            List<Component> secComps = components.subList(comps, Math.min(comps+compPerSec, components.size()));
//            addDefaultSection(secComps, colsPerRow);
//            comps+=compPerSec;
//        }
//        return this;
//    }
//

//    private final void addDefaultSection(List<Component> components, int colsPerRow) {
//        String css = getCss(colsPerRow);
//        GroupLayout sectionConfig = new GroupLayout();
//        int col = 0;
//        RowLayout rowConfig = null;
//        for (Component c:components) {
//            if (col==0) {
//                rowConfig = new RowLayout();
//                sectionConfig.withRow(rowConfig);
//            }
//            rowConfig.withCol(c, css);
//            col = (col+1)%colsPerRow;
//        }
//        add(sectionConfig);
//    }


//    public @Nonnull PageLayout withDefaultLayout(WebMarkupContainer parent) {
//        clear();
//        final List<Component> components = Lists.newArrayList();
//        parent.visitChildren(Component.class, new IVisitor<Component, Void>() {
//            @Override
//            public void component(Component object, IVisit<Void> visit) {
//                components.add(object);
//            }
//        });
//        int count = components.size();
//        int colsPerRow = 3;
//        int compPerSec = (count+1)/2;
//        // mostly this is for reference so the dev will get an idea of what the layout json object should look like.
//        addInDefaltManner(components, compPerSec, colsPerRow);
//        return this;
//    }

//    private void clear() {
//        this.sections = Lists.newArrayList();
//    }

}
