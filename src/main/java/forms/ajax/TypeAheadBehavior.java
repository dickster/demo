package forms.ajax;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import forms.FormBasedWorkflow;
import forms.model.GenericInsuranceObject;
import forms.model.Name;
import forms.spring.WfNavigator;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;

import javax.inject.Inject;
import java.util.Set;

public class TypeAheadBehavior extends AbstractDefaultAjaxBehavior {

    private @Inject WfNavigator wfNavigator;

    public TypeAheadBehavior() {
        super();
    }

    @Override
    public void onConfigure(Component component) {
        Preconditions.checkArgument(component instanceof HasConfig);
        Config config = ((HasConfig)component).getConfig();
        config.withOption("url", getCallbackUrl().toString());
    }

    @Override
    protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {
        super.updateAjaxAttributes(attributes);
        attributes.setChannel(new AjaxChannel("json"));
    }

    @Override
    protected void respond(AjaxRequestTarget target) {
        RequestCycle rc = RequestCycle.get();
        TextRequestHandler handler = new TextRequestHandler("application/json","UTF-8", getJson());
        rc.replaceAllRequestHandlers(handler);
    }

    protected String getJson() {
        // TODO : this is implementation specific stuff...should not be in core behavior.
        FormBasedWorkflow<GenericInsuranceObject> workflow = (FormBasedWorkflow<GenericInsuranceObject>) wfNavigator.getWorkflow(getComponent());
        GenericInsuranceObject obj = workflow.getObject();
        Set<Result> result = Sets.newHashSet();
            for (Name name: obj.getNames()) {
            if (StringUtils.isNotBlank(name.last))result.add(new Result(name.last));
            if (StringUtils.isNotBlank(name.first))result.add(new Result(name.first));
            if (StringUtils.isNotBlank(name.middle)) result.add(new Result(name.middle));
        }
        return new Gson().toJson(result);
    }

    public class Result {
        String name="";  // make sure you don't return nulls!

        public Result(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            if (name != null ? !name.equals(result.name) : result.name != null) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }
    }

}
