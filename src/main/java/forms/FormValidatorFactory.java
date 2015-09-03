package forms;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.validation.IFormValidator;

import javax.annotation.Nonnull;

public interface FormValidatorFactory {

    IFormValidator create(@Nonnull String key, @Nonnull Form form);
}
