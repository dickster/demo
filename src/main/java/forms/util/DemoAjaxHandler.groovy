package forms.util
import forms.WfAjaxHandler
import org.apache.wicket.ajax.AjaxRequestTarget

class DemoAjaxHandler extends WfAjaxHandler {

    DemoAjaxHandler(String event) {
        super(event)
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        println('groovy ajax handler!!!');
    }
}
