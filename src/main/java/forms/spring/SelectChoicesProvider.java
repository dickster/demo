package forms.spring;

import java.util.List;

public interface SelectChoicesProvider<T> {
    List<T> getChoices();
}
