package forms.spring;

import java.util.List;

public interface SelectChoicesService<T> {
    List<T> getChoices();
}
