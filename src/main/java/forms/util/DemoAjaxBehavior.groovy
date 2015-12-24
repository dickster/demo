package forms.util
import forms.spring.WfAjaxBehavior
import org.apache.wicket.ajax.AjaxRequestTarget

class DemoAjaxBehavior extends WfAjaxBehavior {

    DemoAjaxBehavior(String event) {
        super(event)
    }

    @Override
    protected void onUpdate(AjaxRequestTarget target) {
        println('groovy ajax handler!!!');
    }
}
