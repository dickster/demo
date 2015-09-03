package forms;

import com.google.common.collect.Lists;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.form.validation.IFormValidator;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import javax.annotation.Nonnull;
import java.util.List;

// just a sample instance of the factory with some convenience methods.
//  you'll want to extend this to do the actual factory work.
// e.g. make one that calls a groovy script?
public class DefaultFormValidatorFactory implements FormValidatorFactory {

    @Override
    public @Nonnull IFormValidator create(String key, final Form form) {
        // if key==blah then return blargh. etc...
        // e.g. key = "cmf-validation.groovy"

        final FormComponent[] dependents = getDependentFormComponents(form);

        return new AbstractFormValidator() {
            @Override public FormComponent<?>[] getDependentFormComponents() {
                return dependents;
            }

            @Override public void validate(Form <?> form) {
                ;//do nothing.  this is just an example.
            }
        };

    }

    protected FormComponent[] getDependentFormComponents(Form form) {
        final List<FormComponent> components = Lists.newArrayList();
        form.visitFormComponents(new IVisitor<FormComponent,Void>() {
            @Override public void component(FormComponent component, IVisit visit) {
                if (component.isVisibleInHierarchy()) {
                    components.add(component);
                }
            }
        });
        return components.toArray(new FormComponent[components.size()]);
    }
}

