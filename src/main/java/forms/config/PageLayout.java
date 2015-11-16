package forms.config;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
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

}