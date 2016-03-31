package forms.widgets.config;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.google.gson.annotations.Expose;
import forms.HasWorkflow;
import forms.WidgetTypeEnum;
import forms.Workflow;
import forms.spring.SelectChoicesProvider;
import forms.spring.SelectOptionsProvider;
import forms.util.ConfigGson;
import forms.widgets.BootstrapSelectPicker;
import forms.widgets.SelectPicker;
import org.apache.wicket.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectConfig<T>  {

    public static final String MULTIPLE_ATTR = "multiple";

    private transient SelectChoicesProvider<T> provider;
    private boolean required;

    private String id;
    private final Map<String, Object> options = Maps.newHashMap();
    private final Map<String, String> attributes = Maps.newHashMap();


    public SelectConfig() {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getOptions() {
        return ImmutableMap.copyOf(options);
    }

    public SelectConfig<T> withOption(String key, Object value) {
        options.put(key, value);
        return this;
    }

    public SelectChoicesProvider<T> getChoicesProvider() {
        return provider;
    }

    // TODO : rename this to "withChoices" so it's not confused with other options.
    public SelectConfig withChoices(final List<T> choices) {
        this.provider = new SelectChoicesProvider() {
            @Override public List getChoices() {
                return choices;
            }
        };
        return this;
    }

    public SelectConfig<T> withAttribute(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    public SelectConfig withJsChoices(String jsOptions) {
        // return empty list in wicket...   let .js provide the values.
        this.provider = new SelectChoicesProvider() {
            @Override public List getChoices() {
                return Lists.newArrayList();
            }
        };
        withOption("data", jsOptions);
        return this;
    }

    public SelectConfig withTitle(String value) {
        withOption("title", value);
        return this;
    }

    public SelectConfig allowMultiple() {
        withAttribute(MULTIPLE_ATTR,"");
        return this;
    }

    public boolean allowsMultiple() {
        return getAttributes().get(MULTIPLE_ATTR)!=null;
    }

    public SelectConfig<T> allowSearch() {
        withOption("liveSearch", "true");
        return this;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public SelectConfig withKeywordSearching() {
        withOption("keywordSearch","y");
        return this;
    }

    public SelectConfig withKeysDisplayed() {
        withOption("keysDisplayed","y");
        return this;
    }

    public SelectConfig<T> required() {
        this.required = true;
        return this;
    }

    public boolean isRequired() {
        return required;
    }
}
