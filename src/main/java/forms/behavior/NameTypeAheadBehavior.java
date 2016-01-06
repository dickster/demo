package forms.behavior;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import forms.FormBasedWorkflow;
import forms.model.GenericInsuranceObject;
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
        // TODO : this is implementation specific stuff...should not be in core behavior.
        FormBasedWorkflow<GenericInsuranceObject> workflow = (FormBasedWorkflow<GenericInsuranceObject>) getWorkflow();
        GenericInsuranceObject obj = workflow.getObject();
        Set<Result> result = Sets.newHashSet();
            for (demo.Name name: obj.getNames()) {
            if (StringUtils.isNotBlank(name.getLast()))result.add(new Result(name.getLast()));
            if (StringUtils.isNotBlank(name.getFirst()))result.add(new Result(name.getFirst()));
            if (StringUtils.isNotBlank(name.getMiddle())) result.add(new Result(name.getMiddle()));
        }
        return new Gson().toJson(result);
    }


}
