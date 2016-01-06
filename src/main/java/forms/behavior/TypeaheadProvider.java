package forms.behavior;

import org.apache.wicket.request.resource.AbstractResource;

public abstract class TypeaheadProvider extends AbstractResource {

    // mount each one when created.
    // need to attach to component somehow?? need access to workflow
    // in order to get data??

    @Override
    protected ResourceResponse newResourceResponse(Attributes attributes) {
        ResourceResponse r = new ResourceResponse();
        r.setContentType("application/json");
        r.setWriteCallback(new WriteCallback() {
            public void writeData(Attributes a) {
                a.getResponse().write(getJsonResponse().getBytes()); }
        });
        return r;
    }

    protected abstract String getJsonResponse();

}
