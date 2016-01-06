package forms.ajax;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import forms.FormBasedWorkflow;
import forms.model.GenericInsuranceObject;
import forms.model.Name;
import forms.util.WfUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;

import java.util.List;

public class TypeAheadBehavior extends AbstractDefaultAjaxBehavior {

    public TypeAheadBehavior() {
        super();
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
        FormBasedWorkflow<GenericInsuranceObject> workflow = (FormBasedWorkflow<GenericInsuranceObject>) WfUtil.getWorkflow(getComponent());
        GenericInsuranceObject obj = workflow.getObject();
        List<Result> result = Lists.newArrayList();
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
    }

}
