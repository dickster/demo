package forms.util;

import forms.WfPage;
import forms.WidgetFactory;
import forms.Workflow;
import forms.config.Config;
import org.apache.wicket.Component;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WfUtil {

    public static @Nullable String getComponentName(@Nonnull Component component) {
        Config config = component.getMetaData(Config.KEY);
        return config==null ? null : config.getName();
    }

    public static String getComponentProperty(@Nonnull Component component) {
        Config config = component.getMetaData(Config.KEY);
        return config.getProperty();
    }

    public static WidgetFactory getWidgetFactoryFor(Component component) {

        Workflow workflow = component.visitParents(WfPage.class, new IVisitor<WfPage, Workflow>() {
            @Override
            public void component(WfPage wf, IVisit visit) {
                visit.stop(wf.getWorkflow());
            }
        });
        if (workflow!=null) {
            return workflow.getWidgetFactory();
        }
        throw new IllegalStateException("can't find workflow required to get widget factory for component " + component.getId() + WfUtil.getComponentName(component));

    }

    // TODO : put *post* method here.

}
