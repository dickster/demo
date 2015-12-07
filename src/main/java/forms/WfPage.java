package forms;

import demo.resources.Resource;
import forms.config.Config;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import javax.inject.Inject;

public class WfPage extends WebPage implements HasWorkflow, IAjaxIndicatorAware {

    public static final String WORKFLOW_PARAM = "_workflow_";

    private static final JavaScriptResourceReference BOOTSTRAP_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap.min.js");
    private static final CssResourceReference BOOTSTRAP_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap.min.css");
    private static final JavaScriptResourceReference JQUERY_UI_JS = new JavaScriptResourceReference(Resource.class, "jquery-ui-1.10.4.custom/js/jquery-ui-1.10.4.custom.js");
    private static final CssResourceReference JQUERY_UI_CSS = new CssResourceReference(Resource.class, "jquery-ui-1.10.4.custom/css/ui-lightness/jquery-ui-1.10.4.custom.min.css");
    private static final JavaScriptResourceReference SELECT_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/bootstrap-multiselect.js");
    private static final CssResourceReference SELECT_CSS = new CssResourceReference(Resource.class, "bootstrap-3.1.1-dist/css/bootstrap-select.css");
    private static final JavaScriptResourceReference TYPEAHEAD_JS = new JavaScriptResourceReference(Resource.class, "bootstrap-3.1.1-dist/js/typeahead.bundle.js");
    private static final JavaScriptResourceReference LAYOUTDEF_JS = new JavaScriptResourceReference(Resource.class, "layoutDef.js");
    private static final CssResourceReference TYPEAHEAD_CSS = new CssResourceReference(Resource.class,"bootstrap-3.1.1-dist/css/typeahead.bootstrap.css");

    private static final String INIT = "workflow.init();";
    private static final String FORM_ID = "form";

    private FormBasedWorkflow workflow;

    private @Inject WfFactory wfFactory;

    public WfPage(PageParameters params) {
//        Preconditions.checkArgument(StringUtils.isNotBlank(params.get(WORKFLOW_PARAM).toString()));
        this(params.get(WORKFLOW_PARAM).toString(), false);
    }

    public WfPage(String workflowType, boolean customWidgets) {
        super();
        this.workflow = wfFactory.create(workflowType);
        if (customWidgets) {
            workflow.withWidgetFactory( new DefaultWidgetFactory() {
                @Override
                public Component create(String id, Config config) {
                    if ("name.first".equals(config.getName())) {
                        return new Label(id, config.getName() + " (custom widget)");
                    }
                    return super.create(id, config);
                }
                }
            );
        }
        setDefaultModel(workflow.getModel());
        add( workflow.createForm(FORM_ID, workflow.getCurrentFormConfig()) );
    }

    private WorkflowForm getWorkflowForm() {
        return (WorkflowForm) get("form");
    }

    @Override
    public Workflow getWorkflow() {
        return workflow;
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
        return "progress";
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(Application.get().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptReferenceHeaderItem.forReference(JQUERY_UI_JS));
        response.render(CssHeaderItem.forReference(JQUERY_UI_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(BOOTSTRAP_JS));
        response.render(CssHeaderItem.forReference(BOOTSTRAP_CSS));
        response.render(JavaScriptReferenceHeaderItem.forReference(LAYOUTDEF_JS));
        response.render(OnDomReadyHeaderItem.forScript(INIT));
    }


}




