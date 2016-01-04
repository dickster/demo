package forms.spring;

import java.util.List;

public interface SelectOptionsProvider<T> {
    List<T> getOptions();
}
