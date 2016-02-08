package demo;

import forms.util.WfUtil;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnAfterRenderListener;
import org.apache.wicket.request.Response;
import org.apache.wicket.util.string.Strings;

public class PlaceholderRenderer implements IComponentOnAfterRenderListener {

    @Override
    public void onAfterRender(Component component) {
        if (component instanceof HasConfig && !component.determineVisibility()) {
            renderPlaceholder(component);
        }
    }

    private void renderPlaceholder(Component component) {
        Response response = component.getResponse();
        String dataWf = WfUtil.getComponentId(component);
        response.write("<div id='");
        response.write(component.getMarkupId());
        response.write("' style='display:none' ");
        response.write(" data-wf='"+dataWf+"'>");
        response.write("</div>");
    }
}
