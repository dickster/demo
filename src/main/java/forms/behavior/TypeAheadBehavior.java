package forms.behavior;

import com.google.common.base.Preconditions;
import forms.Workflow;
import forms.spring.WfNavigator;
import forms.widgets.config.Config;
import forms.widgets.config.HasConfig;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxChannel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;

import javax.inject.Inject;

/**
 TODO : using Typeahead's might be better suited for ResourceReference
  UNLESS the response needs access to the workflow & it's data.  in that case you
  need to use a behavior - & it's getComponet() method.

 @see org.apache.wicket.request.resource.ResourceReference
 */
public abstract class TypeAheadBehavior extends AbstractDefaultAjaxBehavior {

    private @Inject WfNavigator wfNavigator;
    private boolean cached = true;

    public TypeAheadBehavior() {
        super();
    }

    public TypeAheadBehavior uncached() {
        this.cached = false;
        return this;
    }

    @Override
    public void onConfigure(Component component) {
        Preconditions.checkArgument(component instanceof HasConfig);
        Config config = ((HasConfig)component).getConfig();
        config.withOption("url", getCallbackUrl().toString());
        if (!cached) {
            config.withOption("cache", false);
        }
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

    protected abstract String getJson();

    protected Workflow getWorkflow() {
        return wfNavigator.getWorkflow(getComponent());
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
