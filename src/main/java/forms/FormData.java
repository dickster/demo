package forms;

import com.google.common.collect.Maps;
import forms.config.Config;
import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import java.util.Map;

public class FormData {
    private String formId;
    private String formName;
    private Map<String, String> plugins;
    private Map<String, String> nameToId = Maps.newHashMap();
    private Map<String, Object> idToData = Maps.newHashMap();

    public FormData(WorkflowForm form) {
        this.formId = form.getMarkupId();
        this.formName = form.getFormConfig().getName();
        init(form);
    }

    public void init(WorkflowForm form) {
        form.getForm().visitChildren(Component.class, new IVisitor<Component, Void>() {
            @Override
            public void component(Component widget, IVisit visit) {
                addOptions(widget);
            }
        });
    }

    private void addOptions(Component component) {
        Config data = component.getMetaData(Config.KEY);
        if (data!=null) {
            String markupId = component.getMarkupId();
            idToData.put(markupId, data);
            nameToId.put(data.getName(), markupId);
        }
    }

    public FormData withPlugins(Map<String, String> plugins) {
        this.plugins = plugins;
        return this;
    }

}

