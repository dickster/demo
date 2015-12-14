package forms.spring;

import java.util.List;

public interface SelectOptionsService<T> {
    List<T> getOptions();
}
