package forms.behavior;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import forms.Workflow;
import forms.model.GenericInsuranceObject;
import forms.model.Name;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class NameTypeAheadBehavior extends TypeAheadBehavior {

    // silly example that gets names currently in workflow object as suggested answers.
    public NameTypeAheadBehavior() {
        super();
        uncached();
    }

    @Override
    protected String getJson() {
        Workflow<GenericInsuranceObject> workflow = getWorkflow();
        GenericInsuranceObject obj = workflow.getObject();
        Set<Result> result = Sets.newHashSet();
            for (Name name: obj.getNames()) {
            if (StringUtils.isNotBlank(name.last)) result.add(new Result(name.last));
            if (StringUtils.isNotBlank(name.first)) result.add(new Result(name.first));
            if (StringUtils.isNotBlank(name.middle)) result.add(new Result(name.middle));
        }
        return new Gson().toJson(result);
    }


}
