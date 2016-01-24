package forms;

import com.google.common.eventbus.Subscribe;
import demo.resources.Resource;
import forms.model.WfCompoundPropertyModel;
import forms.widgets.WfPostalCodeChangedEvent;
import org.apache.wicket.Application;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
    private static final JavaScriptResourceReference WORKFLOW_JS = new JavaScriptResourceReference(Resource.class, "workflow.js");
    private static final JavaScriptResourceReference LAYOUT_JS = new JavaScriptResourceReference(Resource.class, "layout.js");

    private static final String INIT = "wf.init();";
    private static final String FORM_ID = "form";

    private FormBasedWorkflow workflow;
    private @Inject WfFactory wfFactory;

    public WfPage(PageParameters params) {
        this(params.get(WORKFLOW_PARAM).toString(), null);
    }

    public WfPage(String workflowType) {
        this(workflowType, null);
    }

    public WfPage(String workflowType, Object obj) {
        super();
        // NOTE : what to do if page times out? is workflow saved as a draft?
        // do i throw it away?
        this.workflow = wfFactory.create(workflowType);
        workflow.register(this);
        if (obj!=null) {
            this.workflow.withModel(new WfCompoundPropertyModel(obj));
        }
        this.workflow.initialize();
        setDefaultModel(workflow.getModel());
        add(workflow.createForm(FORM_ID, workflow.getCurrentFormConfig()));

        add(new DebugBar("debugBar"));

        add(new Label("subheader", getSubHeader()));
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
        response.render(OnDomReadyHeaderItem.forScript(INIT));
        response.render(JavaScriptReferenceHeaderItem.forReference(WORKFLOW_JS));
        response.render(JavaScriptReferenceHeaderItem.forReference(LAYOUT_JS));
    }

    public IModel<?> getSubHeader() {
        return Model.of(getWorkflowForm().getSubHeader());
    }

    @Subscribe
    public void onPostalCodeChange(WfPostalCodeChangedEvent event) {

    }
}
